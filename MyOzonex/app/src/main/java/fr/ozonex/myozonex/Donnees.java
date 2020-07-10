package fr.ozonex.myozonex;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
        PhGlobal,
        PhMoins,
        PhPlus,
        Orp,
        Algicide,
        HeuresCreuses,
        Eclairage,
        Bassin,
        Events
    }

    public enum Capteur {
        TemperatureBassin,
        Ph,
        Redox,
        Pression,
        Ampero,
        _4_20_Libre,
        CapteurInterne,
        TemperatureInterne,
        HumiditeInterne,
        PressionAtmospheriqueInterne,
        CapteurExterne,
        TemperatureExterne,
        HumiditeExterne,
        PressionAtmospheriqueExterne
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

    public static final int PAC	= 0;
    public static final int RECHAUFFEUR_CHAUDIERE = 1;
    public static final int RECHAUFFEUR_ELECTRIQUE = 2;
    public static final int PANNEAU_SOLAIRE = 3;
    public static final int PANNEAU_ET_POMPE = 4;
    public static final int PANNEAU_ET_CHAUDIERE = 5;
    public static final int PANNEAU_ET_ELECTRIQUE = 6;

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
    boolean eclairage_installe = false;

    private int modePompeFiltration = ARRET;
    private int modeSurpresseur = ARRET;
    private int modeChauffage = ARRET;
    private int modeLampesUV = ARRET;
    private int modeOzone = ARRET;
    private int modeElectrolyseur = ARRET;
    private int modePhMoins = ARRET;
    private int modePhPlus = ARRET;
    private int modeOrp = ARRET;
    private int modeAlgicide = ARRET;
    private int modeEclairage = ARRET;

    private String dateDebutConsoPompeFiltration = null;
    private String dateDebutConsoSurpresseur = null;
    private String dateDebutConsoChauffage = null;
    private String dateDebutConsoLampesUV = null;
    private String dateDebutConsoOzone = null;
    private String dateDebutConsoElectrolyseur = null;
    private String dateDebutConsoPhMoins = null;
    private String dateDebutConsoPhPlus = null;
    private String dateDebutConsoOrp = null;
    private String dateDebutConsoAlgicide = null;

    private double consoHPPompeFiltration = 0;
    private double consoHPSurpresseur = 0;
    private double consoHPChauffage = 0;
    private double consoHPLampesUV = 0;
    private double consoHPOzone = 0;
    private double consoHPElectrolyseur = 0;

    private double consoHCPompeFiltration = 0;
    private double consoHCSurpresseur = 0;
    private double consoHCChauffage = 0;
    private double consoHCLampesUV = 0;
    private double consoHCOzone = 0;
    private double consoHCElectrolyseur = 0;

    private boolean etatHorsGel = false;
    private int enclenchementHorsGel = 0;
    private int arretHorsGel = 0;
    private int frequenceHorsGel = 0;

    private double volumePhMoins = 0;
    private double volumePhPlus = 0;
    private double volumeOrp = 0;
    private double volumeAlgicide = 0;

    private double volumeRestantPhMoins = 0;
    private double volumeRestantPhPlus = 0;
    private double volumeRestantOrp = 0;
    private double volumeRestantAlgicide = 0;

    private double consoJourPhMoins = 0;
    private double consoJourPhPlus = 0;
    private double consoJourOrp = 0;
    private double consoSemainePhMoins = 0;
    private double consoSemainePhPlus = 0;
    private double consoSemaineOrp = 0;
    private double consoMoisPhMoins = 0;
    private double consoMoisPhPlus = 0;
    private double consoMoisOrp = 0;

    private List<Object> listePlagePompeFiltration = new ArrayList<Object>();
    private List<Object> listePlageSurpresseur = new ArrayList<Object>();
    private List<Object> listePlageHeuresCreuses = new ArrayList<Object>();
    private List<Object> listePlageEclairage = new ArrayList<Object>();

    private String dateDernierLavage = null;
    private double pressionApresLavage = 0;
    private double pressionProchainLavage = 0;
    private int seuilRincage = 0;
    private double seuilSecuriteSurpression = 0;
    private double seuilHautPression = 0;
    private double seuilBasPression = 0;

    private int typeChauffage = 0;
    private int controleTemperature = 0;
    private int temperatureArret = 0;
    private int temperatureEnclenchement = 0;
    private int temperatureConsigne = 0;

    private int volumeBassin = 0;
    private String typeRefoulement = null;
    private String typeAsservissement = null;
    private int tempsSecuriteInjection = 0;
    private double hysteresisInjectionPh = 0;
    private int hysteresisInjectionORP = 0;
    private double hysteresisInjectionAmpero = 0;

    private double consignePh;
    private double hysteresisPhPlus;
    private double hysteresisPhMoins;
    private int consigneOrp;
    private int hysteresisOrp;
    private double consigneAmpero;
    private double hysteresisAmpero;
    private double chloreLibreActif;

    private boolean etatRegulations = false;
    private int traitementEnCoursPhMoins = 0;
    private int traitementEnCoursPhPlus = 0;
    private int traitementEnCoursOrp = 0;
    private int traitementEnCoursAlgicide = 0;

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

    private boolean presenceCapteurPt = false;
    private boolean presenceCapteurInterne = false;
    private boolean presenceCapteurExterne = false;
    private boolean presenceCapteurPh = false;
    private boolean presenceCapteurRedox = false;
    private boolean presenceCapteurPression = false;
    private boolean presenceCapteurAmpero = false;
    private boolean presenceCapteur_4_20_Libre = false;

    private double temperatureBassin;
    private double temperatureInterne;
    private double humiditeInterne;
    private double pressionAtmInterne;
    private double temperatureExterne;
    private double humiditeExterne;
    private double pressionAtmExterne;
    private double valeurPh;
    private double valeurRedox;
    private double valeurPression;
    private double valeurAmpero;
    private double valeur_4_20_Libre;

    private boolean defautPt = false;
    private boolean defautTemperatureInterne = false;
    private boolean defautHumiditeInterne = false;
    private boolean defautPressionAtmInterne = false;
    private boolean defautTemperatureExterne = false;
    private boolean defautHumiditeExterne = false;
    private boolean defautPressionAtmExterne = false;
    private boolean defautPh = false;
    private boolean defautRedox = false;
    private boolean defautPression = false;
    private boolean defautAmpero = false;
    private boolean defaut_4_20_Libre = false;

    public static final String ID_SYSTEME = "id system";
    public static final String MOTDEPASSE = "password";

    private String gmt = "GMT+02:00";
    private String derniereConnexion = "";
    private boolean activiteIHM = false;
    private int background;
    private int pageSource;
    private boolean etatLectureCapteurs = false;

    private boolean firstRead = true;
    private List<Event> events = new ArrayList<Event>();
    private List<Event> eventsTmp = new ArrayList<Event>();

    private boolean heuresCreusesAuto = false;
    private boolean donneesEquipementsAuto = false;
    private boolean modifPlagesAuto = false;
    private boolean plagesAuto = false;
    private String debutPlageAuto = "00h00";
    private String tempsFiltrationJour = "00h00";
    private String plageAuto = "00h00 - 00h00";
    private boolean asservissementPhPlusAuto = false;
    private boolean asservissementPhMoinsAuto = false;
    private boolean asservissementOrpAuto = false;
    private boolean consigneOrpAuto = false;

    private double alarmeSeuilBasChauffage = 0;
    private double alarmeSeuilHautChauffage = 0;
    private double alarmeSeuilBasPh = 0;
    private double alarmeSeuilHautPh = 0;
    private double alarmeSeuilBasAmpero = 0;
    private double alarmeSeuilHautAmpero = 0;
    private double alarmeSeuilBasOrp = 0;
    private double alarmeSeuilHautOrp = 0;


static SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.instance());

    public static String getPreferences(String key) {
        return preferences.getString(key, "");
    }

    public static void setPreferences(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void definirGMT(String str) {
        gmt = str;
    }

    public boolean obtenirActiviteIHM() {
        return activiteIHM;
    }

    public String obtenirDerniereConnexion() {
        return derniereConnexion;
    }

    public void definirActiviteIHM(String strDateHeure) {
        Calendar calendarIHM = Calendar.getInstance();
        TimeZone tz = calendarIHM.getTimeZone();

        float ONE_HOUR_MILLIS = 60 * 60 * 1000;
        double offsetGMTIHM = Integer.parseInt(gmt.replace("GMT", "").split(":")[0]) + Integer.parseInt(gmt.replace("GMT", "").split(":")[1]) / 60.0;
        double offsetFromUtc = tz.getOffset(calendarIHM.getTime().getTime()) / ONE_HOUR_MILLIS;
        int offsetHrs = 0;
        int offsetMins = 0;

        /*if (tz.inDaylightTime(calendarIHM.getTime())) {
            offsetFromUtc = offsetFromUtc - tz.getDSTSavings() / ONE_HOUR_MILLIS;
        } else {
            offsetGMTIHM = offsetGMTIHM + 1;
        }*/

        if (offsetGMTIHM != offsetFromUtc) {
            offsetHrs = (int)(offsetFromUtc - offsetGMTIHM);
            offsetMins = (int)(((offsetFromUtc - (int)offsetFromUtc) - (offsetGMTIHM - (int)offsetGMTIHM)) * 60);
        }

        derniereConnexion = strDateHeure;

        String date = strDateHeure.split("-")[0];
        String heure = strDateHeure.split("-")[1];

        calendarIHM.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.split("/")[0]));
        calendarIHM.set(Calendar.MONTH, Integer.parseInt(date.split("/")[1]) - 1);
        calendarIHM.set(Calendar.YEAR, Integer.parseInt(date.split("/")[2]));
        calendarIHM.set(Calendar.HOUR_OF_DAY, Integer.parseInt(heure.split(":")[0]));
        calendarIHM.set(Calendar.MINUTE, Integer.parseInt(heure.split(":")[1]));
        calendarIHM.set(Calendar.SECOND, 0);

        calendarIHM.add(Calendar.HOUR_OF_DAY, offsetHrs);
        calendarIHM.add(Calendar.MINUTE, offsetMins - 5);

        Calendar calendarIHMPlus = (Calendar) calendarIHM.clone();
        calendarIHMPlus.add(Calendar.MINUTE, 10);

        Date dateAndroid = Calendar.getInstance().getTime();
        Date dateIHM = calendarIHM.getTime();
        Date dateIHMPlus = calendarIHMPlus.getTime();

        if (dateAndroid.after(dateIHM) && dateAndroid.before(dateIHMPlus)) {
            activiteIHM = true;
        } else {
            activiteIHM = false;
            Donnees.instance().definirEtatLectureCapteurs(false);
            Donnees.instance().definirModeFonctionnement(Equipement.PompeFiltration, ARRET);
            Donnees.instance().definirModeFonctionnement(Equipement.Surpresseur, ARRET);
            Donnees.instance().definirModeFonctionnement(Equipement.Chauffage, ARRET);
            Donnees.instance().definirModeFonctionnement(Equipement.LampesUV, ARRET);
            Donnees.instance().definirModeFonctionnement(Equipement.Ozone, ARRET);
            Donnees.instance().definirModeFonctionnement(Equipement.Electrolyseur, ARRET);
            Donnees.instance().definirModeFonctionnement(Equipement.PhMoins, ARRET);
            Donnees.instance().definirModeFonctionnement(Equipement.PhPlus, ARRET);
            Donnees.instance().definirModeFonctionnement(Equipement.Orp, ARRET);
            Donnees.instance().definirModeFonctionnement(Equipement.Algicide, ARRET);
        }
    }

    public int obtenirCouleurTexte() {
        int couleur;

        if ((background == 2)
                || (background == 3)
                || (background == 4)
                || (background == 6)) {
            couleur = Color.WHITE;
        } else {
            couleur = Color.BLACK;
        }

        return couleur;
    }

    public int obtenirBackground() {
        int drawable;

        if (MainActivity.instance().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (background == 1) {
                drawable = R.drawable.fond_portrait_1;
            } else if (background == 2) {
                drawable = R.drawable.fond_portrait_2;
            } else if (background == 3) {
                drawable = R.drawable.fond_portrait_3;
            } else if (background == 4) {
                drawable = R.drawable.fond_portrait_4;
            } else if (background == 5) {
                drawable = R.drawable.fond_portrait_5;
            } else if (background == 6) {
                drawable = R.drawable.fond_portrait_6;
            } else if (background == 7) {
                drawable = R.drawable.fond_portrait_7;
            } else {
                drawable = R.drawable.fond_portrait_1;
            }
        } else {
            if (background == 1) {
                drawable = R.drawable.fond_paysage_1;
            } else if (background == 2) {
                drawable = R.drawable.fond_paysage_2;
            } else if (background == 3) {
                drawable = R.drawable.fond_paysage_3;
            } else if (background == 4) {
                drawable = R.drawable.fond_paysage_4;
            } else if (background == 5) {
                drawable = R.drawable.fond_paysage_5;
            } else if (background == 6) {
                drawable = R.drawable.fond_paysage_6;
            } else if (background == 7) {
                drawable = R.drawable.fond_paysage_7;
            } else {
                drawable = R.drawable.fond_paysage_1;
            }
        }

        return drawable;
    }

    public void definirBackground(int index) {
        background = index;
    }

    public List<Event> obtenirListeEvents() {
        return events;
    }

    public void configurationEvents() {
        eventsTmp.clear();
    }

    public void ajouterEvent(String texte, int couleur, String dateHeure) {
        boolean exists = false;

        String[] split = dateHeure.split("-");
        String body = split.length == 2 ? "Le " + split[0] + " à " + split[1] : "Erreur";

        Event message = new Event(texte, couleur, body);
        eventsTmp.add(message);

        for (int i = 0; i < events.size(); i++) {
            if ((events.get(i).getTexte().equals(texte)) && (events.get(i).getCouleur() == couleur) && (events.get(i).getDateHeure().equals(body))) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            if (firstRead) {
                events.add(message);
            } else {
                events.add(0, message);
                Notification.instance().ajouter(texte, body);
            }
        }
    }

    public void supprimerEvents() {
        for (int i = 0; i < events.size(); i++) {
            boolean exists = false;

            for (int j = 0; j < eventsTmp.size(); j++) {
                if ((events.get(i).getTexte().equals(eventsTmp.get(j).getTexte())) && (events.get(i).getCouleur() == eventsTmp.get(j).getCouleur()) && (events.get(i).getDateHeure().equals(eventsTmp.get(j).getDateHeure()))) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                events.remove(i);
                i--;
            }
        }

        firstRead = false;
    }

    public int obtenirPageSource() {
        return pageSource;
    }

    public void definirPageSource(int index) {
        pageSource = index;
    }

    public boolean obtenirEtatLectureCapteurs() {
        return etatLectureCapteurs;
    }

    public void definirEtatLectureCapteurs(boolean etat) {
        etatLectureCapteurs = etat;
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
        } else if (equipement == Equipement.Eclairage) {
            etat = eclairage_installe;
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
        } else if (equipement == Equipement.Eclairage) {
            eclairage_installe = etat;
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
        } else if (equipement == Equipement.Electrolyseur) {
            mode = modeElectrolyseur;
        } else if (equipement == Equipement.PhMoins) {
            mode = modePhMoins;
        } else if (equipement == Equipement.PhPlus) {
            mode = modePhPlus;
        } else if (equipement == Equipement.Orp) {
            mode = modeOrp;
        } else if (equipement == Equipement.Algicide) {
            mode = modeAlgicide;
        } else if (equipement == Equipement.Eclairage) {
            mode = modeEclairage;
        }

        return mode;
    }

    public void definirModeFonctionnement(Equipement equipement, int mode) {
        if (equipement == Equipement.PompeFiltration) {
            if (modePompeFiltration != mode) {
                modePompeFiltration = mode;
            }
        } else if (equipement == Equipement.Surpresseur) {
            if (modeSurpresseur != mode) {
                modeSurpresseur = mode;
            }
        } else if (equipement == Equipement.Chauffage) {
            if (modeChauffage != mode) {
                modeChauffage = mode;
            }
        } else if (equipement == Equipement.LampesUV) {
            if (modeLampesUV != mode) {
                modeLampesUV = mode;
            }
        } else if (equipement == Equipement.Ozone) {
            if (modeOzone != mode) {
                modeOzone = mode;
            }
        } else if (equipement == Equipement.Electrolyseur) {
            if (modeElectrolyseur != mode) {
                modeElectrolyseur = mode;
            }
        } else if (equipement == Equipement.PhMoins) {
            if (modePhMoins != mode) {
                modePhMoins = mode;
            }
        } else if (equipement == Equipement.PhPlus) {
            if (modePhPlus != mode) {
                modePhPlus = mode;
            }
        } else if (equipement == Equipement.Orp) {
            if (modeOrp != mode) {
                modeOrp = mode;
            }
        } else if (equipement == Equipement.Algicide) {
            if (modeAlgicide != mode) {
                modeAlgicide = mode;
            }
        } else if (equipement == Equipement.Eclairage) {
            if (modeEclairage != mode) {
                modeEclairage = mode;
            }
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
        } else if (equipement == Equipement.Electrolyseur) {
            date = dateDebutConsoElectrolyseur;
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
        } else if (equipement == Equipement.Electrolyseur) {
            dateDebutConsoElectrolyseur = date;
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
        } else if (equipement == Equipement.Electrolyseur) {
            consoHP = consoHPElectrolyseur;
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
        } else if (equipement == Equipement.Electrolyseur) {
            consoHPElectrolyseur = valeur;
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
        } else if (equipement == Equipement.Electrolyseur) {
            consoHC = consoHCElectrolyseur;
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
        } else if (equipement == Equipement.Electrolyseur) {
            consoHCElectrolyseur = valeur;
        }
    }

    public boolean obtenirEtatHorsGel() {
        return etatHorsGel;
    }

    public void definirEtatHorsGel(boolean etat) {
        etatHorsGel = etat;
    }

    public int obtenirEnclenchementHorsGel() {
        return enclenchementHorsGel;
    }

    public void definirEnclenchementHorsGel(int index) {
        enclenchementHorsGel = index;
    }

    public int obtenirArretHorsGel() {
        return arretHorsGel;
    }

    public void definirArretHorsGel(int index) {
        arretHorsGel = index;
    }

    public int obtenirFrequenceHorsGel() {
        return frequenceHorsGel;
    }

    public void definirFrequenceHorsGel(int index) {
        frequenceHorsGel = index;
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

    public double obtenirConsoJour(Equipement equipement) {
        double conso = 0;

        switch (equipement) {
            case PhMoins:
                conso = consoJourPhMoins;
                break;
            case PhPlus:
                conso = consoJourPhPlus;
                break;
            case Orp:
                conso = consoJourOrp;
                break;
            default:
                break;
        }

        return conso;
    }

    public void definirConsoJour(Equipement equipement, double conso) {
        switch (equipement) {
            case PhMoins:
                consoJourPhMoins = conso;
                break;
            case PhPlus:
                consoJourPhPlus = conso;
                break;
            case Orp:
                consoJourOrp = conso;
                break;
            default:
                break;
        }
    }

    public double obtenirConsoSemaine(Equipement equipement) {
        double conso = 0;

        switch (equipement) {
            case PhMoins:
                conso = consoSemainePhMoins;
                break;
            case PhPlus:
                conso = consoSemainePhPlus;
                break;
            case Orp:
                conso = consoSemaineOrp;
                break;
            default:
                break;
        }

        return conso;
    }

    public void definirConsoSemaine(Equipement equipement, double conso) {
        switch (equipement) {
            case PhMoins:
                consoSemainePhMoins = conso;
                break;
            case PhPlus:
                consoSemainePhPlus = conso;
                break;
            case Orp:
                consoSemaineOrp = conso;
                break;
            default:
                break;
        }
    }

    public double obtenirConsoMois(Equipement equipement) {
        double conso = 0;

        switch (equipement) {
            case PhMoins:
                conso = consoMoisPhMoins;
                break;
            case PhPlus:
                conso = consoMoisPhPlus;
                break;
            case Orp:
                conso = consoMoisOrp;
                break;
            default:
                break;
        }

        return conso;
    }

    public void definirConsoMois(Equipement equipement, double conso) {
        switch (equipement) {
            case PhMoins:
                consoMoisPhMoins = conso;
                break;
            case PhPlus:
                consoMoisPhPlus = conso;
                break;
            case Orp:
                consoMoisOrp = conso;
                break;
            default:
                break;
        }
    }

    public boolean obtenirEtatPlage(Equipement equipement, int index) {
        boolean etat = false;

        index *= 2;

        switch (equipement) {
            case PompeFiltration:
                etat = (boolean) listePlagePompeFiltration.get(index);
                break;
            case Surpresseur:
                etat = (boolean) listePlageSurpresseur.get(index);
                break;
            case HeuresCreuses:
                etat = (boolean) listePlageHeuresCreuses.get(index);
                break;
            case Eclairage:
                etat = (boolean) listePlageEclairage.get(index);
                break;
            default:
                break;
        }

        return etat;
    }

    public String  obtenirPlage(Equipement equipement, int index) {
        String plage = "";

        index *= 2;

        switch (equipement) {
            case PompeFiltration:
                plage = (String) listePlagePompeFiltration.get(index + 1);
                break;
            case Surpresseur:
                plage = (String) listePlageSurpresseur.get(index + 1);
                break;
            case HeuresCreuses:
                plage = (String) listePlageHeuresCreuses.get(index + 1);
                break;
            case Eclairage:
                plage = (String) listePlageEclairage.get(index + 1);
                break;
            default:
                break;
        }

        return plage;
    }

    public void definirPlage(Equipement equipement, int index, String plage) {
        String[] split = plage.split(" - ");
        boolean etat = false;

        if (split.length == 2) {
            etat = !split[0].equals(split[1]);
        } else {
            plage = "00h00 - 00h00";
        }

        index *= 2;

        switch (equipement) {
            case PompeFiltration:
                if (index >= listePlagePompeFiltration.size()) {
                    listePlagePompeFiltration.add(etat);
                    listePlagePompeFiltration.add(plage);
                } else {
                    listePlagePompeFiltration.set(index, etat);
                    listePlagePompeFiltration.set(index + 1, plage);
                }
                break;
            case Surpresseur:
                if (index >= listePlageSurpresseur.size()) {
                    listePlageSurpresseur.add(etat);
                    listePlageSurpresseur.add(plage);
                } else {
                    listePlageSurpresseur.set(index, etat);
                    listePlageSurpresseur.set(index + 1, plage);
                }
                break;
            case HeuresCreuses:
                if (index >= listePlageHeuresCreuses.size()) {
                    listePlageHeuresCreuses.add(etat);
                    listePlageHeuresCreuses.add(plage);
                } else {
                    listePlageHeuresCreuses.set(index, etat);
                    listePlageHeuresCreuses.set(index + 1, plage);
                }
                break;
            case Eclairage:
                if (index >= listePlageEclairage.size()) {
                    listePlageEclairage.add(etat);
                    listePlageEclairage.add(plage);
                } else {
                    listePlageEclairage.set(index, etat);
                    listePlageEclairage.set(index + 1, plage);
                }
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

    public int obtenirSeuilRincage() {
        return seuilRincage;
    }

    public void definirSeuilRincage(int valeur) {
        seuilRincage = valeur;
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

    public int obtenirTypeChauffage() {
        return typeChauffage;
    }

    public void definirTypeChauffage(int type) {
        typeChauffage = type;
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

    public String obtenirTypeRefoulement() {
        return typeRefoulement;
    }

    public void definirTypeRefoulement(String type) {
        typeRefoulement = type;
    }

    public String obtenirTypeAsservissement() {
        return typeAsservissement;
    }

    public void definirTypeAsservissement(String type) {
        typeAsservissement = type;
    }

    public int obtenirTempsSecuriteInjection() {
        return tempsSecuriteInjection;
    }

    public void definirTempsSecuriteInjection(int valeur) {
        tempsSecuriteInjection = valeur;
    }

    public double obtenirHysteresisInjectionPh() {
        return hysteresisInjectionPh;
    }

    public void definirHysteresisInjectionPh(double valeur) {
        hysteresisInjectionPh = valeur;
    }

    public int obtenirHysteresisInjectionORP() {
        return hysteresisInjectionORP;
    }

    public void definirHysteresisInjectionORP(int valeur) {
        hysteresisInjectionORP = valeur;
    }

    public double obtenirHysteresisInjectionAmpero() {
        return hysteresisInjectionAmpero;
    }

    public void definirHysteresisInjectionAmpero(double valeur) {
        hysteresisInjectionAmpero = valeur;
    }

    public double obtenirConsignePh() {
        return consignePh;
    }

    public void definirConsignePh(double valeur) {
        consignePh = valeur;
    }

    public double obtenirHysteresisPhPlus() {
        return hysteresisPhPlus;
    }

    public void definirHysteresisPhPlus(double valeur) {
        hysteresisPhPlus = valeur;
    }

    public double obtenirHysteresisPhMoins() {
        return hysteresisPhMoins;
    }

    public void definirHysteresisPhMoins(double valeur) {
        hysteresisPhMoins = valeur;
    }

    public int obtenirConsigneOrp() {
        return consigneOrp;
    }

    public void definirConsigneOrp(int valeur) {
        consigneOrp = valeur;
    }

    public int obtenirHysteresisOrp() {
        return hysteresisOrp;
    }

    public void definirHysteresisOrp(int valeur) {
        hysteresisOrp = valeur;
    }

    public double obtenirConsigneAmpero() {
        return consigneAmpero;
    }

    public void definirConsigneAmpero(double valeur) {
        consigneAmpero = valeur;
    }

    public double obtenirHysteresisAmpero() {
        return hysteresisAmpero;
    }

    public void definirHysteresisAmpero(double valeur) {
        hysteresisAmpero = valeur;
    }

    public double obtenirChloreLibreActif() {
        return chloreLibreActif;
    }

    public void definirChloreLibreActif(double valeur) {
        chloreLibreActif = valeur;
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
            capteurPresent = presenceCapteurPt;
        } else if (capteur == Capteur.CapteurInterne) {
            capteurPresent = presenceCapteurInterne;
        } else if (capteur == Capteur.CapteurExterne) {
            capteurPresent = presenceCapteurExterne;
        } else if (capteur == Capteur.Ph) {
            capteurPresent = presenceCapteurPh;
        } else if (capteur == Capteur.Redox) {
            capteurPresent = presenceCapteurRedox;
        } else if (capteur == Capteur.Pression) {
            capteurPresent = presenceCapteurPression;
        } else if (capteur == Capteur.Ampero) {
            capteurPresent = presenceCapteurAmpero;
        } else if (capteur == Capteur._4_20_Libre) {
            capteurPresent = presenceCapteur_4_20_Libre;
        }

        return capteurPresent;
    }

    public void definirPresence(Capteur capteur, int available) {
        boolean presence = available > 0;

        if (capteur == Capteur.TemperatureBassin) {
            presenceCapteurPt = presence;
        } else if (capteur == Capteur.CapteurInterne) {
            presenceCapteurInterne = presence;
        } else if (capteur == Capteur.CapteurExterne) {
            presenceCapteurExterne = presence;
        } else if (capteur == Capteur.Ph) {
            presenceCapteurPh = presence;
        } else if (capteur == Capteur.Redox) {
            presenceCapteurRedox = presence;
        } else if (capteur == Capteur.Pression) {
            presenceCapteurPression = presence;
        } else if (capteur == Capteur.Ampero) {
            presenceCapteurAmpero = presence;
        } else if (capteur == Capteur._4_20_Libre) {
            presenceCapteur_4_20_Libre = presence;
        }
    }

    public boolean obtenirEtat(Capteur capteur) {
        boolean defaut = true;

        if (capteur == Capteur.TemperatureBassin) {
            defaut = defautPt;
        } else if (capteur == Capteur.TemperatureInterne) {
            defaut = defautTemperatureInterne;
        } else if (capteur == Capteur.HumiditeInterne) {
            defaut = defautHumiditeInterne;
        } else if (capteur == Capteur.PressionAtmospheriqueInterne) {
            defaut = defautPressionAtmInterne;
        } else if (capteur == Capteur.TemperatureExterne) {
            defaut = defautTemperatureExterne;
        } else if (capteur == Capteur.HumiditeExterne) {
            defaut = defautHumiditeExterne;
        } else if (capteur == Capteur.PressionAtmospheriqueExterne) {
            defaut = defautPressionAtmExterne;
        } else if (capteur == Capteur.Ph) {
            defaut = defautPh;
        } else if (capteur == Capteur.Redox) {
            defaut = defautRedox;
        } else if (capteur == Capteur.Pression) {
            defaut = defautPression;
        } else if (capteur == Capteur.Ampero) {
            defaut = defautAmpero;
        } else if (capteur == Capteur._4_20_Libre) {
            defaut = defaut_4_20_Libre;
        }

        return !defaut;
    }

    public void definirEtat(Capteur capteur, String etat) {
        if (capteur == Capteur.TemperatureBassin) {
            defautPt = etat.contains("ERR");
        } else if (capteur == Capteur.TemperatureInterne) {
            defautTemperatureInterne = etat.contains("ERR");
        } else if (capteur == Capteur.HumiditeInterne) {
            defautHumiditeInterne = etat.contains("ERR");
        } else if (capteur == Capteur.PressionAtmospheriqueInterne) {
            defautPressionAtmInterne = etat.contains("ERR");
        } else if (capteur == Capteur.TemperatureExterne) {
            defautTemperatureExterne = etat.contains("ERR");
        } else if (capteur == Capteur.HumiditeExterne) {
            defautHumiditeExterne = etat.contains("ERR");
        } else if (capteur == Capteur.PressionAtmospheriqueExterne) {
            defautPressionAtmExterne = etat.contains("ERR");
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

    public double obtenirValeur(Capteur capteur) {
        double valeur = 0;

        if (capteur == Capteur.TemperatureBassin) {
            valeur = temperatureBassin;
        } else if (capteur == Capteur.TemperatureInterne) {
            valeur = temperatureInterne;
        } else if (capteur == Capteur.HumiditeInterne) {
            valeur = humiditeInterne;
        } else if (capteur == Capteur.PressionAtmospheriqueInterne) {
            valeur = pressionAtmInterne;
        } else if (capteur == Capteur.TemperatureExterne) {
            valeur = temperatureExterne;
        } else if (capteur == Capteur.HumiditeExterne) {
            valeur = humiditeExterne;
        } else if (capteur == Capteur.PressionAtmospheriqueExterne) {
            valeur = pressionAtmExterne;
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

        return valeur;
    }

    public void definirValeur(Capteur capteur, double valeur) {
        if (capteur == Capteur.TemperatureBassin) {
            temperatureBassin = valeur;
        } else if (capteur == Capteur.TemperatureInterne) {
            temperatureInterne = valeur;
        } else if (capteur == Capteur.HumiditeInterne) {
            humiditeInterne = valeur;
        } else if (capteur == Capteur.PressionAtmospheriqueInterne) {
            pressionAtmInterne = valeur;
        } else if (capteur == Capteur.TemperatureExterne) {
            temperatureExterne = valeur;
        } else if (capteur == Capteur.HumiditeExterne) {
            humiditeExterne = valeur;
        } else if (capteur == Capteur.PressionAtmospheriqueExterne) {
            pressionAtmExterne = valeur;
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

    public boolean obtenirEtatRegulations() {
        return etatRegulations;
    }

    public void definirEtatRegulations(boolean etat) {
        etatRegulations = etat;
    }

    public boolean obtenirHeuresCreusesAuto() {
        return heuresCreusesAuto;
    }

    public void definirHeuresCreusesAuto(boolean actif) {
        heuresCreusesAuto = actif;
    }

    public boolean obtenirDonneesEquipementsAuto() {
        return donneesEquipementsAuto;
    }

    public void definirDonneesEquipementsAuto(boolean actif) {
        donneesEquipementsAuto = actif;
    }

    public boolean obtenirModifPlagesAuto() {
        return modifPlagesAuto;
    }

    public void definirModifPlagesAuto(boolean actif) {
        modifPlagesAuto = actif;
    }

    public boolean obtenirPlagesAuto() {
        return plagesAuto;
    }

    public void definirPlagesAuto(boolean actif) {
        plagesAuto = actif;
    }

    public String obtenirDebutPlageAuto() {
        return debutPlageAuto;
    }

    public void definirDebutPlageAuto(String heure) {
        debutPlageAuto = heure;
    }

    public String obtenirTempsFiltrationJour() {
        return tempsFiltrationJour;
    }

    public void definirTempsFiltrationJour(String heure) {
        tempsFiltrationJour = heure;
    }

    public String obtenirPlageAuto() {
        return plageAuto;
    }

    public void definirPlageAuto(String plage) {
        plageAuto = plage;
    }

    public boolean obtenirAsservissementAuto(Equipement equipement) {
        boolean ret = false;

        if (equipement == Equipement.PhPlus) {
            ret = asservissementPhPlusAuto;
        } else if (equipement == Equipement.PhMoins) {
            ret = asservissementPhMoinsAuto;
        } else if (equipement == Equipement.Orp) {
            ret = asservissementOrpAuto;
        }

        return ret;
    }

    public void definirAsservissementAuto(Equipement equipement, boolean actif) {
        if (equipement == Equipement.PhPlus) {
            asservissementPhPlusAuto = actif;
        } else if (equipement == Equipement.PhMoins) {
            asservissementPhMoinsAuto = actif;
        } else if (equipement == Equipement.Orp) {
            asservissementOrpAuto = actif;
        }
    }

    public boolean obtenirConsigneOrpAuto() {
        return consigneOrpAuto;
    }

    public void definirConsigneOrpAuto(boolean actif) {
        consigneOrpAuto = actif;
    }

    public double obtenirAlarmeSeuilBas(Equipement equipement, Capteur capteur) {
        double ret = 0;

        if (equipement == Equipement.Chauffage) {
            ret = alarmeSeuilBasChauffage;
        } else if (equipement == Equipement.PhGlobal) {
            ret = alarmeSeuilBasPh;
        } else if (equipement == Equipement.Orp) {
            if (capteur == Capteur.Ampero) {
                ret = alarmeSeuilBasAmpero;
            } else {
                ret = alarmeSeuilBasOrp;
            }
        }

        return ret;
    }

    public void definirAlarmeSeuilBas(Equipement equipement, double valeur, Capteur capteur) {
        if (equipement == Equipement.Chauffage) {
            alarmeSeuilBasChauffage = valeur;
        } else if (equipement == Equipement.PhGlobal) {
            alarmeSeuilBasPh = valeur;
        } else if (equipement == Equipement.Orp) {
            if (capteur == Capteur.Ampero) {
                alarmeSeuilBasAmpero = valeur;
            } else {
                alarmeSeuilBasOrp = valeur;
            }
        }
    }

    public double obtenirAlarmeSeuilHaut(Equipement equipement, Capteur capteur) {
        double ret = 0;

        if (equipement == Equipement.Chauffage) {
            ret = alarmeSeuilHautChauffage;
        } else if (equipement == Equipement.PhGlobal) {
            ret = alarmeSeuilHautPh;
        } else if (equipement == Equipement.Orp) {
            if (capteur == Capteur.Ampero) {
                ret = alarmeSeuilHautAmpero;
            } else {
                ret = alarmeSeuilHautOrp;
            }
        }

        return ret;
    }

    public void definirAlarmeSeuilHaut(Equipement equipement, double valeur, Capteur capteur) {
        if (equipement == Equipement.Chauffage) {
            alarmeSeuilHautChauffage = valeur;
        } else if (equipement == Equipement.PhGlobal) {
            alarmeSeuilHautPh = valeur;
        } else if (equipement == Equipement.Orp) {
            if (capteur == Capteur.Ampero) {
                alarmeSeuilHautAmpero = valeur;
            } else {
                alarmeSeuilHautOrp = valeur;
            }
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
