import javax.xml.xquery.*;
import net.xqj.exist.ExistXQDataSource;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class GestorBD {
    private XQConnection conn;
    private String collectionPath = "/db/IndividualIvan/empresa.xml";

    public GestorBD(String s) throws XQException {
        XQDataSource xqs = new ExistXQDataSource();
        xqs.setProperty("serverName", "localhost");
        xqs.setProperty("port", "8080");
        conn = xqs.getConnection();
    }

    private Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    public void getDeptSenseEmp() {
        try {
            String query = "for $d in doc('" + collectionPath + "')//dept " +
                    "return $d";
            XQPreparedExpression expr = conn.prepareExpression(query);
            XQResultSequence result = expr.executeQuery();
            List<String> departments = new ArrayList<>();
            while (result.next()) {
                departments.add(result.getItemAsString(null));
            }
            if (departments.isEmpty()) {
                System.out.println("No hay departamentos.");
            } else {
                System.out.println("Todos los departamentos:");
                for (String dept : departments) {
                    System.out.println(dept);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener departamentos: " + e.getMessage());
        }
    }


    public void getDeptAmbEmp() {
        try {
            String query = "for $d in doc('" + collectionPath + "')//dept " +
                    "return <dept>{$d}<empleados>{for $e in doc('" + collectionPath + "')//emp[@dept = $d/@codi] return $e}</empleados></dept>";
            XQPreparedExpression expr = conn.prepareExpression(query);
            XQResultSequence result = expr.executeQuery();

            while (result.next()) {
                XQItem item = result.getItem();
                String deptWithEmp = item.getItemAsString(null);
                System.out.println(deptWithEmp);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener departamentos con empleados: " + e.getMessage());
        }
    }


    public void insertDept(Departament dept) {
        try {
            String query = "update insert " + deptToXMLString(dept) + " into doc('" + collectionPath + "')/empresa/departaments";
            XQExpression expr = conn.createExpression();
            expr.executeCommand(query);
            System.out.println("Departamento insertado correctamente.");
        } catch (XQException e) {
            System.out.println("Error al insertar el departamento en la base de datos: " + e.getMessage());
        }
    }

    public void deleteDept(String deptCode, boolean deleteEmployees, String reassignDeptCode) {
        try {
            if (deleteEmployees) {
                String deleteQuery = "update delete doc('" + collectionPath + "')//emp[@dept='" + deptCode + "']";
                XQExpression deleteExpr = conn.createExpression();
                deleteExpr.executeCommand(deleteQuery);
            } else if (reassignDeptCode != null) {
                String reassignQuery = "update value doc('" + collectionPath + "')//emp[@dept='" + deptCode + "']/@dept with data('" + reassignDeptCode + "')";
                XQExpression reassignExpr = conn.createExpression();
                reassignExpr.executeCommand(reassignQuery);
            }
            String query = "update delete doc('" + collectionPath + "')//dept[@codi='" + deptCode + "']";
            XQExpression expr = conn.createExpression();
            expr.executeCommand(query);
            System.out.println("Departamento eliminado correctamente.");
        } catch (XQException e) {
            System.out.println("Error al eliminar el departamento en la base de datos: " + e.getMessage());
        }
    }

    public void replaceDept(Departament oldDept, Departament newDept) {
        try {
            deleteDept(oldDept.getCodi(), true, null);
            insertDept(newDept);
        } catch (Exception e) {
            System.out.println("Error al reemplazar el departamento en la base de datos: " + e.getMessage());
        }
    }

    private void insertEmp(Empleat emp) {
        try {
            String query = "update insert " + empToXMLString(emp) + " into doc('" + collectionPath + "')/empresa/empleats";
            XQExpression expr = conn.createExpression();
            expr.executeCommand(query);
            System.out.println("Empleado insertado correctamente.");
        } catch (XQException e) {
            System.out.println("Error al insertar el empleado en la base de datos: " + e.getMessage());
        }
    }

    private String deptToXMLString(Departament dept) {
        return "<dept codi=\"" + dept.getCodi() + "\"><nom>" + dept.getNom() + "</nom><localitat>" + dept.getLocalitat() + "</localitat></dept>";
    }


    private String empToXMLString(Empleat emp) {
        return "<emp codi=\"" + emp.getCodi() + "\" dept=\"" + emp.getDept() + "\"><nom>" + emp.getNom() + "</nom><sal>" + emp.getSal() + "</sal></emp>";
    }

    public void tancarSessio() {
        try {
            conn.close();
            System.out.println("Sesión cerrada correctamente.");
        } catch (XQException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
