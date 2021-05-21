package fr.ozonex.myozonex;

public class StructureEvenement {
    private String texte;
    private int couleur;
    private String dateHeure;

    public StructureEvenement(String texte, int couleur, String dateHeure) {
        this.texte = texte;
        this.couleur = couleur;
        this.dateHeure = dateHeure;
    }

    public String getTexte() {
        return texte;
    }

    public int getCouleur() {
        return couleur;
    }

    public String getDateHeure() {
        return dateHeure;
    }
}
