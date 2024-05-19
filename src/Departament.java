import java.util.List;

public class Departament {
    private String codi;
    private String nom;
    private String localitat;
    private List<Empleat> empleats;

    public Departament(String codi, String nom, String localitat, List<Empleat> empleats) {
        this.codi = codi;
        this.nom = nom;
        this.localitat = localitat;
        this.empleats = empleats;
    }

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

    public String getLocalitat() {
        return localitat;
    }

    public void setLocalitat(String localitat) {
        this.localitat = localitat;
    }

    public List<Empleat> getEmpleats() {
        return empleats;
    }

    public void setEmpleats(List<Empleat> empleats) {
        this.empleats = empleats;
    }
}
