package fr.ozonex.myozonex;

public class Global {
    public static final int CODE_INSTALLATEUR = 318;

    public static final int TEMPO_RAFRAICHISSEMENT = 10000; // millisecondes
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

    // CALIBRATION DES CAPTEURS
    public static final int STEP_MIN_PT = 0;		// en °C
    public static final int STEP_MAX_PT = 40;		// en °C
    public static final int STEP_MIN_PH = 0;
    public static final int STEP_MAX_PH = 14;
    public static final int STEP_MIN_REDOX = 0;		// en mV
    public static final int STEP_MAX_REDOX = 1000;	// en mV

    public static final int BASE_RESISTANCE_PT = 100;	// en Ohms
    public static final double COEFFICIENT_PT = ((119.0 - 100.0) / (50.0 - 0.0));

    public static final int BASE_VOLTAGE_PH_MIN = 414;		// +414 mV <=> pH 0
    public static final int BASE_VOLTAGE_PH_MAX = (-414);	// -414 mV <=> pH 14

    public static final int BASE_VOLTAGE_REDOX_MIN = STEP_MIN_REDOX;
    public static final int BASE_VOLTAGE_REDOX_MAX = STEP_MAX_REDOX;
}
