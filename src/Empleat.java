public class Empleat {
    private String codi; // Código del empleado
    private String nom;  // Nombre del empleado
    private String dept; // Código del departamento al que pertenece el empleado
    private double sal;  // Salario del empleado

    public Empleat(String codi, String nom, String dept, double sal) {
        this.codi = codi;
        this.nom = nom;
        this.dept = dept;
        this.sal = sal;
    }

    // Getters y setters
    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "codi='" + codi + '\'' +
                ", nom='" + nom + '\'' +
                ", dept='" + dept + '\'' +
                ", sal=" + sal +
                '}';
    }
}
