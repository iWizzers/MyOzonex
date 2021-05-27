package fr.ozonex.myozonex;

public class StructureNotificationID {
    private int id;
    private String contenu;

    public StructureNotificationID(int id, String contenu) {
        this.id = id;
        this.contenu = contenu;
    }

    public int getID() {
        return id;
    }

    public String getContenu() {
        return contenu;
    }
}
