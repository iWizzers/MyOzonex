package fr.ozonex.myozonex;

public class Global {
    public static final int TEMPO_RAFRAICHISSEMENT = 5000; // millisecondes
    public static final int TEMPO_CLIGNOTEMENT_LED = 500; // millisecondes
    public static final int TEMPO_INJECTION_TUYAU = 500; // millisecondes

    public static final int MAX_PLAGES_HEURES_CREUSES = 3;
    public static final int MAX_PLAGES_EQUIPEMENTS = 4;

    public static final double HYSTERESIS_BIDON_PRESQUE_VIDE = 20.0; // en %
    public static final double HYSTERESIS_BIDON_VIDE = 10.0; // en %
}
