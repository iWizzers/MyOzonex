package fr.ozonex.myozonex;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Donnees {
    private static Donnees inst = new Donnees();
    public static Donnees instance() {
        return inst;
    }

    // Equipement
    public enum Equipement {
        PompeFiltration,
        Surpresseur,
        Filtre,
        Chauffage,
        Ozone,
        LampesUV,
        Electrolyseur,
        PhMoins,
        PhPlus,
        Orp,
        Algicide
    }

    public enum Capteur {
        TemperatureBassin,
        TemperatureLocal,
        Ph,
        Redox,
        Pression,
        Ampero,
        _4_20_Libre
    }

    // Page source
    public static final int PAGE_SYNOPTIQUE = 0;
    public static final int PAGE_MENU = 1;

    // Mode
    public static final int ARRET = 0;
    public static final int MARCHE = 1;
    public static final int AUTO = 2;
    public static final int AUTO_ARRET = 3;
    public static final int AUTO_MARCHE = 4;

    public static final int CONTROLE_PAR_POMPE_FILTRATION = 0;
    public static final int CONTROLE_POMPE_FILTRATION = 1;

    public static final String ASSERVISSEMENT_TOR = "TOR";
    public static final String ASSERVISSEMENT_LIN = "LINEAIRE";

    boolean pompe_filtration_installe = false;
    boolean filtre_installe = false;
    boolean surpresseur_installe = false;
    boolean chauffage_installe = false;
    boolean lampes_uv_installe = false;
    boolean ozone_installe = false;
    boolean electrolyseur_installe = false;
    boolean regulateur_ph_moins_installe = false;
    boolean regulateur_ph_plus_installe = false;
    boolean regulateur_orp_installe = false;
    boolean regulateur_algicide_installe = false;

    private int modePompeFiltration = ARRET;
    private int modeSurpresseur = ARRET;
    private int modeChauffage = ARRET;
    private int modeLampesUV = ARRET;
    private int modeOzone = ARRET;
    private int modePhMoins = ARRET;
    private int modePhPlus = ARRET;
    private int modeOrp = ARRET;
    private int modeAlgicide = ARRET;

    private String dateDebutConsoPompeFiltration = null;
    private String dateDebutConsoSurpresseur = null;
    private String dateDebutConsoChauffage = null;
    private String dateDebutConsoLampesUV = null;
    private String dateDebutConsoOzone = null;
    private String dateDebutConsoPhMoins = null;
    private String dateDebutConsoPhPlus = null;
    private String dateDebutConsoOrp = null;
    private String dateDebutConsoAlgicide = null;

    private double consoHPPompeFiltration = 0;
    private double consoHPSurpresseur = 0;
    private double consoHPChauffage = 0;
    private double consoHPLampesUV = 0;
    private double consoHPOzone = 0;

    private double consoHCPompeFiltration = 0;
    private double consoHCSurpresseur = 0;
    private double consoHCChauffage = 0;
    private double consoHCLampesUV = 0;
    private double consoHCOzone = 0;

    private double volumePhMoins = 0;
    private double volumePhPlus = 0;
    private double volumeOrp = 0;
    private double volumeAlgicide = 0;

    private double volumeRestantPhMoins = 0;
    private double volumeRestantPhPlus = 0;
    private double volumeRestantOrp = 0;
    private double volumeRestantAlgicide = 0;

    private String dateDernierLavage = null;
    private double pressionApresLavage = 0;
    private double pressionProchainLavage = 0;
    private double seuilSecuriteSurpression = 0;
    private double seuilHautPression = 0;
    private double seuilBasPression = 0;

    private int controleTemperature = 0;
    private int temperatureArret = 0;
    private int temperatureEnclenchement = 0;
    private int temperatureConsigne = 0;

    private int volumeBassin = 0;
    private String typeAsservissement = null;

    private int traitementEnCoursPhMoins = 0;
    private int traitementEnCoursPhPlus = 0;
    private int traitementEnCoursOrp = 0;
    private int traitementEnCoursAlgicide = 0;

    private int reglageAutoPhMoins = 0;
    private int reglageAutoPhPlus = 0;
    private int reglageAutoOrp = 0;

    private int dureeCyclePhMoins = 0;
    private int dureeCyclePhPlus = 0;
    private int dureeCycleOrp = 0;

    private int multiplicateurDiffPhMoins = 0;
    private int multiplicateurDiffPhPlus = 0;
    private int multiplicateurDiffOrp = 0;

    private String dureeInjectionPhMoins = null;
    private String dureeInjectionPhPlus = null;
    private String dureeInjectionOrp = null;

    private String tempsReponsePhMoins = null;
    private String tempsReponsePhPlus = null;
    private String tempsReponseOrp = null;

    private int tempsInjectionJournalierMaxPhMoins = 0;
    private int tempsInjectionJournalierMaxPhPlus = 0;
    private int tempsInjectionJournalierMaxOrp = 0;

    private int tempsInjectionJournalierMaxRestantPhMoins = 0;
    private int tempsInjectionJournalierMaxRestantPhPlus = 0;
    private int tempsInjectionJournalierMaxRestantOrp = 0;

    private int etat_surchloration = 0;
    private String donnees_surchloration = null;

    private int etat_algicide = 0;
    private String donnees_algicide = null;

    private int presenceCapteurPt = 0;
    private int presenceCapteurLocal = 0;
    private int presenceCapteurPh = 0;
    private int presenceCapteurRedox = 0;
    private int presenceCapteurPression = 0;
    private int presenceCapteurAmpero = 0;
    private int presenceCapteur_4_20_Libre = 0;

    private double temperatureBassin;
    private double temperatureLocal;
    private double valeurPh;
    private double valeurRedox;
    private double valeurPression;
    private double valeurAmpero;
    private double valeur_4_20_Libre;

    private boolean defautPt;
    private boolean defautTempLocal;
    private boolean defautPh;
    private boolean defautRedox;
    private boolean defautPression;
    private boolean defautAmpero;
    private boolean defaut_4_20_Libre;

    public static final String ID_SYSTEME = "id system";
    public static final String MOTDEPASSE = "password";

    private int pageSource;

    static SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.instance());

    public static String getPreferences(String key) {
        return preferences.getString(key, "");
    }

    public static void setPreferences(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public int obtenirPageSource() {
        return pageSource;
    }

    public void definirPageSource(int index) {
        pageSource = index;
    }

    public  boolean obtenirEquipementInstalle(Equipement equipement) {
        boolean etat = false;

        if (equipement == Equipement.PompeFiltration) {
            etat = pompe_filtration_installe;
        } else if (equipement == Equipement.Filtre) {
            etat = filtre_installe;
        } else if (equipement == Equipement.Surpresseur) {
            etat = surpresseur_installe;
        } else if (equipement == Equipement.Chauffage) {
            etat = chauffage_installe;
        } else if (equipement == Equipement.LampesUV) {
            etat = lampes_uv_installe;
        } else if (equipement == Equipement.Ozone) {
            etat = ozone_installe;
        } else if (equipement == Equipement.Electrolyseur) {
            etat = electrolyseur_installe;
        } else if (equipement == Equipement.PhMoins) {
            etat = regulateur_ph_moins_installe;
        } else if (equipement == Equipement.PhPlus) {
            etat = regulateur_ph_plus_installe;
        } else if (equipement == Equipement.Orp) {
            etat = regulateur_orp_installe;
        } else if (equipement == Equipement.Algicide) {
            etat = regulateur_algicide_installe;
        }

        return etat;
    }

    public void definirEquipementInstalle(Equipement equipement, boolean etat) {
        if (equipement == Equipement.PompeFiltration) {
            pompe_filtration_installe = etat;
        } else if (equipement == Equipement.Filtre) {
            filtre_installe = etat;
        } else if (equipement == Equipement.Surpresseur) {
            surpresseur_installe = etat;
        } else if (equipement == Equipement.Chauffage) {
            chauffage_installe = etat;
        } else if (equipement == Equipement.LampesUV) {
            lampes_uv_installe = etat;
        } else if (equipement == Equipement.Ozone) {
            ozone_installe = etat;
        } else if (equipement == Equipement.Electrolyseur) {
            electrolyseur_installe = etat;
        } else if (equipement == Equipement.PhMoins) {
            regulateur_ph_moins_installe = etat;
        } else if (equipement == Equipement.PhPlus) {
            regulateur_ph_plus_installe = etat;
        } else if (equipement == Equipement.Orp) {
            regulateur_orp_installe = etat;
        } else if (equipement == Equipement.Algicide) {
            regulateur_algicide_installe = etat;
        }
    }

    public int obtenirModeFonctionnement(Equipement equipement) {
        int mode = ARRET;

        if (equipement == Equipement.PompeFiltration) {
            mode = modePompeFiltration;
        } else if (equipement == Equipement.Surpresseur) {
            mode = modeSurpresseur;
        } else if (equipement == Equipement.Chauffage) {
            mode = modeChauffage;
        } else if (equipement == Equipement.LampesUV) {
            mode = modeLampesUV;
        } else if (equipement == Equipement.Ozone) {
            mode = modeOzone;
        } else if (equipement == Equipement.PhMoins) {
            mode = modePhMoins;
        } else if (equipement == Equipement.PhPlus) {
            mode = modePhPlus;
        } else if (equipement == Equipement.Orp) {
            mode = modeOrp;
        } else if (equipement == Equipement.Algicide) {
            mode = modeAlgicide;
        }

        return mode;
    }

    public void definirModeFonctionnement(Equipement equipement, int mode) {
        boolean sendNotification = false;
        String titre = "";
        String contenu = "Son état a changé (";

        if (mode == AUTO_MARCHE) {
            contenu += "Auto marche";
        } else if (mode == AUTO_ARRET) {
            contenu += "Auto arrêt";
        } else if (mode == MARCHE) {
            contenu += "Marche";
        } else {
            contenu += "Arrêt";
        }

        contenu += ')';

        if (equipement == Equipement.PompeFiltration) {
            titre = "Pompe filtration";

            if (modePompeFiltration != mode) {
                modePompeFiltration = mode;
                sendNotification = true;
            }
        } else if (equipement == Equipement.Surpresseur) {
            titre = "Surpresseur";

            if (modeSurpresseur != mode) {
                modeSurpresseur = mode;
                sendNotification = true;
            }
        } else if (equipement == Equipement.Chauffage) {
            titre = "Chauffage";

            if (modeChauffage != mode) {
                modeChauffage = mode;
                sendNotification = true;
            }
        } else if (equipement == Equipement.LampesUV) {
            titre = "Lampes UV";

            if (modeLampesUV != mode) {
                modeLampesUV = mode;
                sendNotification = true;
            }
        } else if (equipement == Equipement.Ozone) {
            titre = "Ozone";

            if (modeOzone != mode) {
                modeOzone = mode;
                sendNotification = true;
            }
        } else if (equipement == Equipement.PhMoins) {
            titre = "Régulateur pH-";

            if (modePhMoins != mode) {
                modePhMoins = mode;
                sendNotification = true;
            }
        } else if (equipement == Equipement.PhPlus) {
            titre = "Régulateur pH+";

            if (modePhPlus != mode) {
                modePhPlus = mode;
                sendNotification = true;
            }
        } else if (equipement == Equipement.Orp) {
            titre = "Régulateur ORP";

            if (modeOrp != mode) {
                modeOrp = mode;
                sendNotification = true;
            }
        } else if (equipement == Equipement.Algicide) {
            titre = "Algicide";

            if (modeAlgicide != mode) {
                modeAlgicide = mode;
                sendNotification = true;
            }
        }

        if (sendNotification) {
            Notification.instance().ajouter(titre, contenu);
        }
    }

    public String obtenirDateDebutConso(Equipement equipement) {
        String date = null;

        if (equipement == Equipement.PompeFiltration) {
            date = dateDebutConsoPompeFiltration;
        } else if (equipement == Equipement.Surpresseur) {
            date = dateDebutConsoSurpresseur;
        } else if (equipement == Equipement.Chauffage) {
            date = dateDebutConsoChauffage;
        } else if (equipement == Equipement.LampesUV) {
            date = dateDebutConsoLampesUV;
        } else if (equipement == Equipement.Ozone) {
            date = dateDebutConsoOzone;
        } else if (equipement == Equipement.PhMoins) {
            date = dateDebutConsoPhMoins;
        } else if (equipement == Equipement.PhPlus) {
            date = dateDebutConsoPhPlus;
        } else if (equipement == Equipement.Orp) {
            date = dateDebutConsoOrp;
        } else if (equipement == Equipement.Algicide) {
            date = dateDebutConsoAlgicide;
        }

        return date;
    }

    public void definirDateDebutConso(Equipement equipement, String date) {
        if (equipement == Equipement.PompeFiltration) {
            dateDebutConsoPompeFiltration = date;
        } else if (equipement == Equipement.Surpresseur) {
            dateDebutConsoSurpresseur = date;
        } else if (equipement == Equipement.Chauffage) {
            dateDebutConsoChauffage = date;
        } else if (equipement == Equipement.LampesUV) {
            dateDebutConsoLampesUV = date;
        } else if (equipement == Equipement.Ozone) {
            dateDebutConsoOzone = date;
        } else if (equipement == Equipement.PhMoins) {
            dateDebutConsoPhMoins = date;
        } else if (equipement == Equipement.PhPlus) {
            dateDebutConsoPhPlus = date;
        } else if (equipement == Equipement.Orp) {
            dateDebutConsoOrp = date;
        } else if (equipement == Equipement.Algicide) {
            dateDebutConsoAlgicide = date;
        }
    }

    public double obtenirConsoHP(Equipement equipement) {
        double consoHP = 0;

        if (equipement == Equipement.PompeFiltration) {
            consoHP = consoHPPompeFiltration;
        } else if (equipement == Equipement.Surpresseur) {
            consoHP = consoHPSurpresseur;
        } else if (equipement == Equipement.Chauffage) {
            consoHP = consoHPChauffage;
        } else if (equipement == Equipement.LampesUV) {
            consoHP = consoHPLampesUV;
        } else if (equipement == Equipement.Ozone) {
            consoHP = consoHPOzone;
        }

        return consoHP;
    }

    public void definirConsoHP(Equipement equipement, double valeur) {
        if (equipement == Equipement.PompeFiltration) {
            consoHPPompeFiltration = valeur;
        } else if (equipement == Equipement.Surpresseur) {
            consoHPSurpresseur = valeur;
        } else if (equipement == Equipement.Chauffage) {
            consoHPChauffage = valeur;
        } else if (equipement == Equipement.LampesUV) {
            consoHPLampesUV = valeur;
        } else if (equipement == Equipement.Ozone) {
            consoHPOzone = valeur;
        }
    }

    public double obtenirConsoHC(Equipement equipement) {
        double consoHC = 0;

        if (equipement == Equipement.PompeFiltration) {
            consoHC = consoHCPompeFiltration;
        } else if (equipement == Equipement.Surpresseur) {
            consoHC = consoHCSurpresseur;
        } else if (equipement == Equipement.Chauffage) {
            consoHC = consoHCChauffage;
        } else if (equipement == Equipement.LampesUV) {
            consoHC = consoHCLampesUV;
        } else if (equipement == Equipement.Ozone) {
            consoHC = consoHCOzone;
        }

        return consoHC;
    }

    public void definirConsoHC(Equipement equipement, double valeur) {
        if (equipement == Equipement.PompeFiltration) {
            consoHCPompeFiltration = valeur;
        } else if (equipement == Equipement.Surpresseur) {
            consoHCSurpresseur = valeur;
        } else if (equipement == Equipement.Chauffage) {
            consoHCChauffage = valeur;
        } else if (equipement == Equipement.LampesUV) {
            consoHCLampesUV = valeur;
        } else if (equipement == Equipement.Ozone) {
            consoHCOzone = valeur;
        }
    }

    public double obtenirConsoVolume(Equipement equipement) {
        double volume = 0;

        switch (equipement) {
            case PhMoins:
                volume = volumePhMoins;
                break;
            case PhPlus:
                volume = volumePhPlus;
                break;
            case Orp:
                volume = volumeOrp;
                break;
            case Algicide:
                volume = volumeAlgicide;
                break;
            default:
                break;
        }

        return volume;
    }

    public void definirConsoVolume(Equipement equipement, double volume) {
        switch (equipement) {
            case PhMoins:
                volumePhMoins = volume;
                break;
            case PhPlus:
                volumePhPlus = volume;
                break;
            case Orp:
                volumeOrp = volume;
                break;
            case Algicide:
                volumeAlgicide = volume;
                break;
            default:
                break;
        }
    }

    public double obtenirConsoVolumeRestant(Equipement equipement) {
        double volume = 0;

        switch (equipement) {
            case PhMoins:
                volume = volumeRestantPhMoins;
                break;
            case PhPlus:
                volume = volumeRestantPhPlus;
                break;
            case Orp:
                volume = volumeRestantOrp;
                break;
            case Algicide:
                volume = volumeRestantAlgicide;
                break;
            default:
                break;
        }

        return volume;
    }

    public void definirConsoVolumeRestant(Equipement equipement, double volume) {
        switch (equipement) {
            case PhMoins:
                volumeRestantPhMoins = volume;
                break;
            case PhPlus:
                volumeRestantPhPlus = volume;
                break;
            case Orp:
                volumeRestantOrp = volume;
                break;
            case Algicide:
                volumeRestantAlgicide = volume;
                break;
            default:
                break;
        }
    }

    public String obtenirDateDernierLavage() {
        return dateDernierLavage;
    }

    public void definirDateDernierLavage(String date) {
        dateDernierLavage = date;
    }

    public double obtenirPressionApresLavage() {
        return pressionApresLavage;
    }

    public void definirPressionApresLavage(double valeur) {
        pressionApresLavage = valeur;
    }

    public double obtenirPressionProchainLavage() {
        return pressionProchainLavage;
    }

    public void definirPressionProchainLavage(double valeur) {
        pressionProchainLavage = valeur;
    }

    public double obtenirSeuilSecuriteSurpression() {
        return seuilSecuriteSurpression;
    }

    public void definirSeuilSecuriteSurpression(double valeur) {
        seuilSecuriteSurpression = valeur;
    }

    public double obtenirSeuilHautPression() {
        return seuilHautPression;
    }

    public void definirSeuilHautPression(double valeur) {
        seuilHautPression = valeur;
    }

    public double obtenirSeuilBasPression() {
        return seuilBasPression;
    }

    public void definirSeuilBasPression(double valeur) {
        seuilBasPression = valeur;
    }

    public int obtenirControlePompeFiltration() {
        return controleTemperature;
    }

    public void definirControlePompeFiltration(int type) {
        if (type == 0) {
            controleTemperature = CONTROLE_PAR_POMPE_FILTRATION;
        } else {
            controleTemperature = CONTROLE_POMPE_FILTRATION;
        }
    }

    public int obtenirTemperatureArret() {
        return temperatureArret;
    }

    public void definirTemperatureArret(int valeur) {
        temperatureArret = valeur;
    }

    public int obtenirTemperatureEnclenchement() {
        return temperatureEnclenchement;
    }

    public void definirTemperatureEnclenchement(int valeur) {
        temperatureEnclenchement = valeur;
    }

    public int obtenirTemperatureConsigne() {
        return temperatureConsigne;
    }

    public void definirTemperatureConsigne(int valeur) {
        temperatureConsigne = valeur;
    }

    public int obtenirVolumeBassin() {
        return volumeBassin;
    }

    public void definirVolumeBassin(int volume) {
        volumeBassin = volume;
    }

    public String obtenirTypeAsservissement() {
        return typeAsservissement;
    }

    public void definirTypeAsservissement(String type) {
        typeAsservissement = type;
    }

    public boolean obtenirTraitementEnCours(Equipement equipement) {
        int traitementEnCours = 0;

        switch (equipement) {
            case PhMoins:
                traitementEnCours = traitementEnCoursPhMoins;
                break;
            case PhPlus:
                traitementEnCours = traitementEnCoursPhPlus;
                break;
            case Orp:
                traitementEnCours = traitementEnCoursOrp;
                break;
            case Algicide:
                traitementEnCours = traitementEnCoursAlgicide;
                break;
            default:
                break;
        }

        return (traitementEnCours == 1);
    }

    public void definirTraitementEnCours(Equipement equipement, int traitementEnCours) {
        switch (equipement) {
            case PhMoins:
                traitementEnCoursPhMoins = traitementEnCours;
                break;
            case PhPlus:
                traitementEnCoursPhPlus = traitementEnCours;
                break;
            case Orp:
                traitementEnCoursOrp = traitementEnCours;
                break;
            case Algicide:
                traitementEnCoursAlgicide = traitementEnCours;
                break;
            default:
                break;
        }
    }

    public boolean obtenirReglageAuto(Equipement equipement) {
        int reglageAuto = 0;

        switch (equipement) {
            case PhMoins:
                reglageAuto = reglageAutoPhMoins;
                break;
            case PhPlus:
                reglageAuto = reglageAutoPhPlus;
                break;
            case Orp:
                reglageAuto = reglageAutoOrp;
                break;
            default:
                break;
        }

        return (reglageAuto == 1);
    }

    public void definirReglageAuto(Equipement equipement, int reglageAuto) {
        switch (equipement) {
            case PhMoins:
                reglageAutoPhMoins = reglageAuto;
                break;
            case PhPlus:
                reglageAutoPhPlus = reglageAuto;
                break;
            case Orp:
                reglageAutoOrp = reglageAuto;
                break;
            default:
                break;
        }
    }

    public int obtenirDureeCycle(Equipement equipement) {
        int duree = 0;

        switch (equipement) {
            case PhMoins:
                duree = dureeCyclePhMoins;
                break;
            case PhPlus:
                duree = dureeCyclePhPlus;
                break;
            case Orp:
                duree = dureeCycleOrp;
                break;
            default:
                break;
        }

        return duree;
    }

    public void definirDureeCycle(Equipement equipement, int duree) {
        switch (equipement) {
            case PhMoins:
                dureeCyclePhMoins = duree;
                break;
            case PhPlus:
                dureeCyclePhPlus = duree;
                break;
            case Orp:
                dureeCycleOrp = duree;
                break;
            default:
                break;
        }
    }

    public int obtenirMultiplicateurDifference(Equipement equipement) {
        int duree = 0;

        switch (equipement) {
            case PhMoins:
                duree = multiplicateurDiffPhMoins;
                break;
            case PhPlus:
                duree = multiplicateurDiffPhPlus;
                break;
            case Orp:
                duree = multiplicateurDiffOrp;
                break;
            default:
                break;
        }

        return duree;
    }

    public void definirMultiplicateurDifference(Equipement equipement, int duree) {
        switch (equipement) {
            case PhMoins:
                multiplicateurDiffPhMoins = duree;
                break;
            case PhPlus:
                multiplicateurDiffPhPlus = duree;
                break;
            case Orp:
                multiplicateurDiffOrp = duree;
                break;
            default:
                break;
        }
    }

    public String obtenirDureeInjection(Equipement equipement) {
        String duree = null;

        switch (equipement) {
            case PhMoins:
                duree = dureeInjectionPhMoins;
                break;
            case PhPlus:
                duree = dureeInjectionPhPlus;
                break;
            case Orp:
                duree = dureeInjectionOrp;
                break;
            default:
                break;
        }

        return duree;
    }

    public void definirDureeInjection(Equipement equipement, String duree) {
        switch (equipement) {
            case PhMoins:
                dureeInjectionPhMoins = duree;
                break;
            case PhPlus:
                dureeInjectionPhPlus = duree;
                break;
            case Orp:
                dureeInjectionOrp = duree;
                break;
            default:
                break;
        }
    }

    public String obtenirTempsReponse(Equipement equipement) {
        String duree = null;

        switch (equipement) {
            case PhMoins:
                duree = tempsReponsePhMoins;
                break;
            case PhPlus:
                duree = tempsReponsePhPlus;
                break;
            case Orp:
                duree = tempsReponseOrp;
                break;
            default:
                break;
        }

        return duree;
    }

    public void definirTempsReponse(Equipement equipement, String duree) {
        switch (equipement) {
            case PhMoins:
                tempsReponsePhMoins = duree;
                break;
            case PhPlus:
                tempsReponsePhPlus = duree;
                break;
            case Orp:
                tempsReponseOrp = duree;
                break;
            default:
                break;
        }
    }

    public int obtenirTempsInjectionJournalierMax(Equipement equipement) {
        int duree = 0;

        switch (equipement) {
            case PhMoins:
                duree = tempsInjectionJournalierMaxPhMoins;
                break;
            case PhPlus:
                duree = tempsInjectionJournalierMaxPhPlus;
                break;
            case Orp:
                duree = tempsInjectionJournalierMaxOrp;
                break;
            default:
                break;
        }

        return duree;
    }

    public void definirTempsInjectionJournalierMax(Equipement equipement, int duree) {
        switch (equipement) {
            case PhMoins:
                tempsInjectionJournalierMaxPhMoins = duree;
                break;
            case PhPlus:
                tempsInjectionJournalierMaxPhPlus = duree;
                break;
            case Orp:
                tempsInjectionJournalierMaxOrp = duree;
                break;
            default:
                break;
        }
    }

    public int obtenirTempsInjectionJournalierMaxRestant(Equipement equipement) {
        int duree = 0;

        switch (equipement) {
            case PhMoins:
                duree = tempsInjectionJournalierMaxRestantPhMoins;
                break;
            case PhPlus:
                duree = tempsInjectionJournalierMaxRestantPhPlus;
                break;
            case Orp:
                duree = tempsInjectionJournalierMaxRestantOrp;
                break;
            default:
                break;
        }

        return duree;
    }

    public void definirTempsInjectionJournalierMaxRestant(Equipement equipement, int duree) {
        switch (equipement) {
            case PhMoins:
                tempsInjectionJournalierMaxRestantPhMoins = duree;
                break;
            case PhPlus:
                tempsInjectionJournalierMaxRestantPhPlus = duree;
                break;
            case Orp:
                tempsInjectionJournalierMaxRestantOrp = duree;
                break;
            default:
                break;
        }
    }

    public boolean obtenirEtat(Equipement equipement) {
        int etat = 0;

        switch (equipement) {
            case Orp:
                etat = etat_surchloration;
                break;
            case Algicide:
                etat = etat_algicide;
                break;
            default:
                break;
        }

        return (etat == 1);
    }

    public void definirEtat(Equipement equipement, int etat) {
        switch (equipement) {
            case Orp:
                etat_surchloration = etat;
                break;
            case Algicide:
                etat_algicide = etat;
                break;
            default:
                break;
        }
    }

    public String obtenirDonneesSurchloration() {
        return donnees_surchloration;
    }

    public void definirDonneesSurchloration(String donnees) {
        donnees_surchloration = donnees;
    }

    public String obtenirDonneesAlgicide() {
        return donnees_algicide;
    }

    public void definirDonneesAlgicide(String donnees) {
        donnees_algicide = donnees;
    }

    public boolean presence(Capteur capteur) {
        boolean capteurPresent = false;

        if (capteur == Capteur.TemperatureBassin) {
            capteurPresent = presenceCapteurPt > 0;
        } else if (capteur == Capteur.TemperatureLocal) {
            capteurPresent = presenceCapteurLocal > 0;
        } else if (capteur == Capteur.Ph) {
            capteurPresent = presenceCapteurPh > 0;
        } else if (capteur == Capteur.Redox) {
            capteurPresent = presenceCapteurRedox > 0;
        } else if (capteur == Capteur.Pression) {
            capteurPresent = presenceCapteurPression > 0;
        } else if (capteur == Capteur.Ampero) {
            capteurPresent = presenceCapteurAmpero > 0;
        } else if (capteur == Capteur._4_20_Libre) {
            capteurPresent = presenceCapteur_4_20_Libre > 0;
        }

        return capteurPresent;
    }

    public void definirPresence(Capteur capteur, int available) {
        if (capteur == Capteur.TemperatureBassin) {
            presenceCapteurPt = available;
        } else if (capteur == Capteur.TemperatureLocal) {
            presenceCapteurLocal = available;
        } else if (capteur == Capteur.Ph) {
            presenceCapteurPh = available;
        } else if (capteur == Capteur.Redox) {
            presenceCapteurRedox = available;
        } else if (capteur == Capteur.Pression) {
            presenceCapteurPression = available;
        } else if (capteur == Capteur.Ampero) {
            presenceCapteurAmpero = available;
        } else if (capteur == Capteur._4_20_Libre) {
            presenceCapteur_4_20_Libre = available;
        }
    }

    public boolean obtenirEtat(Capteur capteur) {
        boolean etat = false;

        if (capteur == Capteur.TemperatureBassin) {
            etat = defautPt;
        } else if (capteur == Capteur.TemperatureLocal) {
            etat = defautTempLocal;
        } else if (capteur == Capteur.Ph) {
            etat = defautPh;
        } else if (capteur == Capteur.Redox) {
            etat = defautRedox;
        } else if (capteur == Capteur.Pression) {
            etat = defautPression;
        } else if (capteur == Capteur.Ampero) {
            etat = defautAmpero;
        } else if (capteur == Capteur._4_20_Libre) {
            etat = defaut_4_20_Libre;
        }

        return etat;
    }

    public void definirEtat(Capteur capteur, String etat) {
        if (capteur == Capteur.TemperatureBassin) {
            defautPt = etat.contains("ERR");
        } else if (capteur == Capteur.TemperatureLocal) {
            defautTempLocal = etat.contains("ERR");
        } else if (capteur == Capteur.Ph) {
            defautPh = etat.contains("ERR");
        } else if (capteur == Capteur.Redox) {
            defautRedox = etat.contains("ERR");
        } else if (capteur == Capteur.Pression) {
            defautPression = etat.contains("ERR");
        } else if (capteur == Capteur.Ampero) {
            defautAmpero = etat.contains("ERR");
        } else if (capteur == Capteur._4_20_Libre) {
            defaut_4_20_Libre = etat.contains("ERR");
        }
    }

    public String obtenirValeur(Capteur capteur) {
        double valeur = 0;

        if (capteur == Capteur.TemperatureBassin) {
            valeur = temperatureBassin;
        } else if (capteur == Capteur.TemperatureLocal) {
            valeur = temperatureLocal;
        } else if (capteur == Capteur.Ph) {
            valeur = valeurPh;
        } else if (capteur == Capteur.Redox) {
            valeur = valeurRedox;
        } else if (capteur == Capteur.Pression) {
            valeur = valeurPression;
        } else if (capteur == Capteur.Ampero) {
            valeur = valeurAmpero;
        } else if (capteur == Capteur._4_20_Libre) {
            valeur = valeur_4_20_Libre;
        }

        return formatValeur(valeur);
    }

    public void definirValeur(Capteur capteur, double valeur) {
        if (capteur == Capteur.TemperatureBassin) {
            temperatureBassin = valeur;
        } else if (capteur == Capteur.TemperatureLocal) {
            temperatureLocal = valeur;
        } else if (capteur == Capteur.Ph) {
            valeurPh = valeur;
        } else if (capteur == Capteur.Redox) {
            valeurRedox = valeur;
        } else if (capteur == Capteur.Pression) {
            valeurPression = valeur;
        } else if (capteur == Capteur.Ampero) {
            valeurAmpero = valeur;
        } else if (capteur == Capteur._4_20_Libre) {
            valeur_4_20_Libre = valeur;
        }
    }

    private String formatValeur(double valeur) {
        if(valeur == (long)valeur) {
            return String.format("%d", (long)valeur);
        } else {
            return String.format("%s", valeur);
        }
    }
}
