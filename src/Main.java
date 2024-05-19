import javax.xml.xquery.XQException;
import java.util.Scanner;

public class Main {
    private Scanner scanner;
    private GestorBD gestorDB;

    public Main() throws XQException {
        scanner = new Scanner(System.in);
        gestorDB = new GestorBD("/db/IndividualIvan/empresa.xml");
    }

    public void run() throws Exception {
        boolean salir = false;
        int opcion;

        while (!salir) {
            mostrarMenu();
            System.out.print("Escribe una de las opciones: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    gestorDB.getDeptSenseEmp();
                    break;
                case 2:
                    gestorDB.getDeptAmbEmp();
                    break;
                case 3:
                    insertarDept();
                    break;
                case 4:
                    eliminarDept();
                    break;
                case 5:
                    reemplazarDept();
                    break;
                case 6:
                    salir = true;
                    gestorDB.tancarSessio();
                    break;
                default:
                    System.out.println("Solo números entre 1 y 6");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("###################################################################");
        System.out.println("##                            MENÚ                               ##");
        System.out.println("## 1. Obtener departamentos sin empleados                        ##");
        System.out.println("## 2. Obtener departamentos con empleados                        ##");
        System.out.println("## 3. Insertar departamento                                      ##");
        System.out.println("## 4. Eliminar departamento                                      ##");
        System.out.println("## 5. Reemplazar departamento                                    ##");
        System.out.println("## 6. Salir                                                      ##");
        System.out.println("###################################################################");
    }

    private void insertarDept() {
        scanner.nextLine();
        System.out.print("Introduce el código del departamento: ");
        String deptCode = scanner.nextLine();
        System.out.print("Introduce el nombre del departamento: ");
        String deptName = scanner.nextLine();
        System.out.print("Introduce la localización del departamento: ");
        String deptLoc = scanner.nextLine();

        Departament dept = new Departament(deptCode, deptName, deptLoc, null);
        gestorDB.insertDept(dept);
    }

    private void eliminarDept() {
        scanner.nextLine();
        System.out.print("Introduce el código del departamento a eliminar: ");
        String deptCode = scanner.nextLine();
        System.out.print("¿Quieres eliminar los empleados del departamento? (si/no): ");
        String eliminarEmpleados = scanner.nextLine();
        boolean deleteEmployees = eliminarEmpleados.equalsIgnoreCase("si");
        String reassignDeptCode = null;

        if (!deleteEmployees) {
            System.out.print("Introduce el código del departamento al que reasignar los empleados: ");
            reassignDeptCode = scanner.nextLine();
        }

        gestorDB.deleteDept(deptCode, deleteEmployees, reassignDeptCode);
    }

    private void reemplazarDept() {
        scanner.nextLine();
        System.out.print("Introduce el código del departamento a reemplazar: ");
        String oldDeptCode = scanner.nextLine();

        System.out.print("Introduce el nuevo código del departamento: ");
        String newDeptCode = scanner.nextLine();
        System.out.print("Introduce el nuevo nombre del departamento: ");
        String newDeptName = scanner.nextLine();
        System.out.print("Introduce la nueva localización del departamento: ");
        String newDeptLoc = scanner.nextLine();

        Departament oldDept = new Departament(oldDeptCode, null, null, null);
        Departament newDept = new Departament(newDeptCode, newDeptName, newDeptLoc, null);
        gestorDB.replaceDept(oldDept, newDept);
    }

    public static void main(String[] args) {
        try {
            Main menu = new Main();
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
