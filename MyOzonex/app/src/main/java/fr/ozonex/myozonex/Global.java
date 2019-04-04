package fr.ozonex.myozonex;

public class Global {
    public static final int TEMPO_RAFRAICHISSEMENT = 5000; // millisecondes
    public static final int TEMPO_CLIGNOTEMENT_LED = 500; // millisecondes
    public static final int TEMPO_INJECTION_TUYAU = 500; // millisecondes

    public static final int MAX_PLAGES_HEURES_CREUSES = 3;
    public static final int MAX_PLAGES_EQUIPEMENTS = 4;

    public static final double HYSTERESIS_BIDON_PRESQUE_VIDE = 20.0; // en %
    public static final double HYSTERESIS_BIDON_VIDE = 10.0; // en %

    public static final int TEMPERATURE_MINIMUM_CONSIGNE_ORP = 25; // en °C
    public static final int TEMPERATURE_MAXIMUM_CONSIGNE_ORP = 30; // en °C
    public static final int AJOUT_CONSIGNE_ORP = 10;            // mV/°C
    public static final double AJOUT_CONSIGNE_AMPERO = 0.10;    // ppm/°C
}
