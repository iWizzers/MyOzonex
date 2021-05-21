package fr.ozonex.myozonex;

public class StructurePlage {
    private Boolean etat;
    private String plage;

    public StructurePlage() {
        etat = false;
        plage = "00h00 - 00h00";
    }

    public void setEtat(Boolean valeur) {
        etat = valeur;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setPlage(String valeur) {
        plage = valeur;
    }

    public String getPlage() {
        return plage;
    }
}
