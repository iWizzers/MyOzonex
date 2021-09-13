package fr.ozonex.myozonex;

import android.content.SharedPreferences;
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

    static final int MYOZONEX = 1;
    static final int MYOZONEXESSENTIEL = 2;
    static final int MYOZONEXMINI = 3;
    static final int SMARTOZO = 4;

    static final int ARRET = 0;
    static final int MARCHE = 1;
    static final int AUTO = 2;
    static final int AUTO_ARRET = 3;
    static final int AUTO_MARCHE = 4;

    static final int CONTROLE_PAR_POMPE_FILTRATION = 0;
    static final int CONTROLE_POMPE_FILTRATION = 1;

    static final int ASSERVISSEMENT_TOR = 0;
    static final int ASSERVISSEMENT_LIN = 1;

    static final int PAC = 0;
    static final int RECHAUFFEUR_CHAUDIERE = 1;
    static final int RECHAUFFEUR_ELECTRIQUE = 2;
    static final int PANNEAU_SOLAIRE = 3;
    static final int PANNEAU_ET_POMPE = 4;
    static final int PANNEAU_ET_CHAUDIERE = 5;
    static final int PANNEAU_ET_ELECTRIQUE = 6;

    static final int ERR_NONE = 0x0000;
    static final int ERR_OUT_CC = 0x0001;
    static final int ERR_OUT_OPEN = 0x0002;
    static final int ERR_FAN_1 = 0x0004;
    static final int ERR_FAN_2 = 0x0008;
    static final int ERR_OVER_CURRENT = 0x0010;

    private boolean pompe_filtration_installe = false;
    private boolean filtre_installe = false;
    private boolean surpresseur_installe = false;
    private boolean chauffage_installe = false;
    private boolean lampes_uv_installe = false;
    private boolean ozone_installe = false;
    private boolean electrolyseur_installe = false;
    private boolean regulateur_ph_moins_installe = false;
    private boolean regulateur_ph_plus_installe = false;
    private boolean regulateur_orp_installe = false;
    private boolean regulateur_algicide_installe = false;
    private boolean eclairage_installe = false;

    private double debitRegulateurPhMoins = 0;
    private double debitRegulateurPhPlus = 0;
    private double debitRegulateurOrp = 0;
    private double debitRegulateurAlgicide = 0;

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

    private String dateConsoPompeFiltration = null;
    private String dateConsoSurpresseur = null;
    private String dateConsoChauffage = null;
    private String dateConsoLampesUV = null;
    private String dateConsoOzone = null;
    private String dateConsoElectrolyseur = null;
    private String dateConsoPhMoins = null;
    private String dateConsoPhPlus = null;
    private String dateConsoOrp = null;
    private String dateConsoAlgicide = null;

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
    private int enclHorsGel = 0;
    private int arretHorsGel = 0;
    private int freqHorsGel = 0;

    private int typeOzone = 0;
    private int nombreVentilateursOzone = 0;
    private int tempoOzone = 0;
    private int erreurOzone = 0;
    private int vitesseFan1Ozone = 0;
    private int vitesseFan2Ozone = 0;
    private int courantAlimOzone = 0;
    private double tensionAlimOzone = 0;
    private int hauteTensionAlimOzone = 0;

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

    private StructurePlage[] listePlagePompeFiltration = new StructurePlage[Global.MAX_PLAGES_EQUIPEMENTS];
    private StructurePlage[] listePlageSurpresseur = new StructurePlage[Global.MAX_PLAGES_EQUIPEMENTS];
    private StructurePlage[] listePlageHeuresCreuses = new StructurePlage[Global.MAX_PLAGES_HEURES_CREUSES];
    private StructurePlage[] listePlageEclairage = new StructurePlage[Global.MAX_PLAGES_EQUIPEMENTS];

    private String dateDernierLavage = null;
    private double pressionApresLavage = 0;
    private double pressionProchainLavage = 0;
    private int seuilRincage = 0;
    private double seuilSecuriteSurpression = 0;
    private double seuilHautPression = 0;
    private double seuilBasPression = 0;

    private int typeChauffage = 0;
    private int controleTemperature = 0;
    private int temperatureConsigne = 0;
    private int gestionReversible = 0;
    private int temperatureReversible = 0;

    private double volumeBassin = 0.0;
    private int tempoDemarrage = 0;
    private int typeRefoulement = 0;
    private int typeRegulation = 0;
    private int tempsSecuriteInjection = 0;
    private double hystInjectionPh = 0;
    private int hystInjectionChloreOrp = 0;
    private double hystInjectionChloreAmpero = 0;

    private double pointConsignePh;
    private double hysteresisPhPlus;
    private double hysteresisPhMoins;
    private int pointConsigneOrp;
    private int hysteresisOrp;
    private double pointConsigneAmpero;
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

    private int dureeInjectionMinimumPhMoins = 0;
    private int dureeInjectionMinimumPhPlus = 0;
    private int dureeInjectionMinimumOrp = 0;

    private double dureeInjectionPhMoins = 0.0;
    private double dureeInjectionPhPlus = 0.0;
    private double dureeInjectionOrp = 0.0;

    private int tempsReponsePhMoins = 0;
    private int tempsReponsePhPlus = 0;
    private int tempsReponseOrp = 0;

    private int tempsInjectionJournalierMaxPhMoins = 0;
    private int tempsInjectionJournalierMaxPhPlus = 0;
    private int tempsInjectionJournalierMaxOrp = 0;

    private int tempsInjectionJournalierMaxRestantPhMoins = 0;
    private int tempsInjectionJournalierMaxRestantPhPlus = 0;
    private int tempsInjectionJournalierMaxRestantOrp = 0;

    private int etat_surchloration = 0;
    private int jourSurchloration = 0;
    private String frequenceSurchloration = null;
    private int valeurSurchloration = 0;
    private int prochaineSurchloration = 0;

    private int etat_algicide = 0;
    private int jourAlgicide = 0;
    private String frequenceAlgicide = null;
    private int dureeAlgicide = 0;
    private int dureeRestantAlgicide = 0;
    private int prochaineAlgicide = 0;

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

    private double etalonnageBassin = 0;
    private double etalonnagePh = 0;
    private double etalonnageRedox = 0;
    private double etalonnagePression = 0;
    private double etalonnageAmpero = 0;

    private boolean etatEtalonnageBassin = false;
    private boolean etatEtalonnagePh = false;
    private boolean etatEtalonnageRedox = false;
    private boolean etatEtalonnagePression = false;
    private boolean etatEtalonnageAmpero = false;

    static final String ID_SYSTEME = "id system";
    static final String ID_SYSTEME_X = "id systeme x";
    static final String BT_PAIRED_DEVICE = "bt paired device";

    private List<String> listeAppareils = new ArrayList<>();

    private int typeAppareil = 0;
    private boolean codeInstallateur = false;

    private String gmt = "GMT+02:00";
    private String dateDerniereConnexion = "";
    private String heureDerniereConnexion = "";
    private boolean IHMactive = false;
    private boolean etatLectureCapteurs = false;

    private boolean firstRead = true;
    private List<StructureEvenement> events = new ArrayList<>();
    private List<StructureEvenement> eventsTmp = new ArrayList<>();

    private boolean heuresCreusesAuto = false;
    private boolean donneesEquipementsAuto = false;
    private boolean modifPlagesAuto = false;
    private boolean plagesAuto = false;
    private String debutPlageAuto = null;
    private String tempsFiltrationJour = null;
    private String plageAuto = null;
    private boolean asservissementPhPlusAuto = false;
    private boolean asservissementPhMoinsAuto = false;
    private boolean asservissementOrpAuto = false;
    private boolean pointConsigneOrpAuto = false;

    private double alarmeSeuilBasChauffage = 0;
    private double alarmeSeuilHautChauffage = 0;
    private double alarmeSeuilBasPh = 0;
    private double alarmeSeuilHautPh = 0;
    private double alarmeSeuilBasAmpero = 0;
    private double alarmeSeuilHautAmpero = 0;
    private double alarmeSeuilBasOrp = 0;
    private double alarmeSeuilHautOrp = 0;

    private List<StructureHttp> listeHttp = new ArrayList<>();
    private List<StructureBt> listeBt = new ArrayList<>();
    private int wifiState = 0;
    private boolean timerState = true;

    private static SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.instance());

    public static String getPreferences(String key) {
        return preferences.getString(key, "");
    }

    public static void setPreferences(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public List<String> obtenirListeAppareils() {
        return listeAppareils;
    }

    public void ajouterAppareil(String id) {
        listeAppareils.add(id);
    }

    public void supprimerAppareil(String id) {
        listeAppareils.remove(id);
    }

    public void definirTypeAppareil(int valeur) {
        typeAppareil = valeur;
    }

    public int obtenirTypeAppareil() {
        return typeAppareil;
    }

    public void definirCodeInstallateur(boolean valeur) {
        codeInstallateur = valeur;
    }

    public boolean obtenirCodeInstallateur() {
        return codeInstallateur;
    }

    public void definirGMT(String str) {
        gmt = str;
    }

    public boolean obtenirActiviteIHM() {
        return IHMactive;
    }

    public String obtenirDateDerniereConnexion() {
        return dateDerniereConnexion;
    }

    public String obtenirHeureDerniereConnexion() {
        return heureDerniereConnexion;
    }

    public void definirActiviteIHM(String strDateHeure) {
        Calendar calendarIHM = Calendar.getInstance();
        TimeZone tz = calendarIHM.getTimeZone();

        float ONE_HOUR_MILLIS = 60 * 60 * 1000;
        double offsetGMTIHM = Integer.parseInt(gmt.replace("GMT", "").split(":")[0]) + Integer.parseInt(gmt.replace("GMT", "").split(":")[1]) / 60.0;
        double offsetFromUtc = tz.getOffset(calendarIHM.getTime().getTime()) / ONE_HOUR_MILLIS;
        int offsetHrs = 0;
        int offsetMins = 0;

        if (offsetGMTIHM != offsetFromUtc) {
            offsetHrs = (int)(offsetFromUtc - offsetGMTIHM);
            offsetMins = (int)(((offsetFromUtc - (int)offsetFromUtc) - (offsetGMTIHM - (int)offsetGMTIHM)) * 60);
        }

        dateDerniereConnexion = strDateHeure.split("-")[0];
        heureDerniereConnexion = strDateHeure.split("-")[1];

        calendarIHM.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateDerniereConnexion.split("/")[0]));
        calendarIHM.set(Calendar.MONTH, Integer.parseInt(dateDerniereConnexion.split("/")[1]) - 1);
        calendarIHM.set(Calendar.YEAR, Integer.parseInt(dateDerniereConnexion.split("/")[2]));
        calendarIHM.set(Calendar.HOUR_OF_DAY, Integer.parseInt(heureDerniereConnexion.split(":")[0]));
        calendarIHM.set(Calendar.MINUTE, Integer.parseInt(heureDerniereConnexion.split(":")[1]));
        calendarIHM.set(Calendar.SECOND, 0);

        calendarIHM.add(Calendar.HOUR_OF_DAY, offsetHrs);
        calendarIHM.add(Calendar.MINUTE, offsetMins - 5);

        Calendar calendarIHMPlus = (Calendar) calendarIHM.clone();
        calendarIHMPlus.add(Calendar.MINUTE, 10);

        Date dateAndroid = Calendar.getInstance().getTime();
        Date dateIHM = calendarIHM.getTime();
        Date dateIHMPlus = calendarIHMPlus.getTime();

        if ((dateAndroid.after(dateIHM) && dateAndroid.before(dateIHMPlus)) || (BluetoothLe.instance().connected == BluetoothLe.Connected.True)) {
            IHMactive = true;
        } else {
            IHMactive = false;
            Donnees.instance().definirEtatLectureCapteurs(false);
            Donnees.instance().definirEtatEquipement(Equipement.PompeFiltration, ARRET);
            Donnees.instance().definirEtatEquipement(Equipement.Surpresseur, ARRET);
            Donnees.instance().definirEtatEquipement(Equipement.Chauffage, ARRET);
            Donnees.instance().definirEtatEquipement(Equipement.LampesUV, ARRET);
            Donnees.instance().definirEtatEquipement(Equipement.Ozone, ARRET);
            Donnees.instance().definirEtatEquipement(Equipement.Electrolyseur, ARRET);
            Donnees.instance().definirEtatEquipement(Equipement.PhMoins, ARRET);
            Donnees.instance().definirEtatEquipement(Equipement.PhPlus, ARRET);
            Donnees.instance().definirEtatEquipement(Equipement.Orp, ARRET);
            Donnees.instance().definirEtatEquipement(Equipement.Algicide, ARRET);
        }
    }

    public List<StructureEvenement> obtenirListeEvents() {
        return events;
    }

    public void configurationEvents() {
        eventsTmp.clear();
    }

    public void ajouterEvent(String texte, int couleur, String dateHeure) {
        boolean exists = false;

        String[] split = dateHeure.split("-");
        String body = split.length == 2 ? "Le " + split[0] + " à " + split[1] : "Erreur";

        StructureEvenement message = new StructureEvenement(texte, couleur, body);
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
                CustomNotification.instance().ajouter(texte);
            }
        }
    }

    public void supprimerEvents() {
        events.clear();
    }

    public void supprimerEventsTmp() {
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

    public double obtenirDebitEquipement(Equipement equipement) {
        double valeur = 0;

        if (equipement == Equipement.PhMoins) {
            valeur = debitRegulateurPhMoins;
        } else if (equipement == Equipement.PhPlus) {
            valeur = debitRegulateurPhPlus;
        } else if (equipement == Equipement.Orp) {
            valeur = debitRegulateurOrp;
        } else if (equipement == Equipement.Algicide) {
            valeur = debitRegulateurAlgicide;
        }

        return valeur;
    }

    public void definirDebitEquipement(Equipement equipement, double valeur) {
        if (equipement == Equipement.PhMoins) {
            debitRegulateurPhMoins = valeur;
        } else if (equipement == Equipement.PhPlus) {
            debitRegulateurPhPlus = valeur;
        } else if (equipement == Equipement.Orp) {
            debitRegulateurOrp = valeur;
        } else if (equipement == Equipement.Algicide) {
            debitRegulateurAlgicide = valeur;
        }
    }

    public int obtenirEtatEquipement(Equipement equipement) {
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

    public void definirEtatEquipement(Equipement equipement, int mode) {
        if (equipement == Equipement.PompeFiltration) {
            modePompeFiltration = mode;
        } else if (equipement == Equipement.Surpresseur) {
            modeSurpresseur = mode;
        } else if (equipement == Equipement.Chauffage) {
            modeChauffage = mode;
        } else if (equipement == Equipement.LampesUV) {
            modeLampesUV = mode;
        } else if (equipement == Equipement.Ozone) {
            modeOzone = mode;
        } else if (equipement == Equipement.Electrolyseur) {
            modeElectrolyseur = mode;
        } else if (equipement == Equipement.PhMoins) {
            modePhMoins = mode;
        } else if (equipement == Equipement.PhPlus) {
            modePhPlus = mode;
        } else if (equipement == Equipement.Orp) {
            modeOrp = mode;
        } else if (equipement == Equipement.Algicide) {
            modeAlgicide = mode;
        } else if (equipement == Equipement.Eclairage) {
            modeEclairage = mode;
        }
    }

    public String obtenirDateConso(Equipement equipement) {
        String date = null;

        if (equipement == Equipement.PompeFiltration) {
            date = dateConsoPompeFiltration;
        } else if (equipement == Equipement.Surpresseur) {
            date = dateConsoSurpresseur;
        } else if (equipement == Equipement.Chauffage) {
            date = dateConsoChauffage;
        } else if (equipement == Equipement.LampesUV) {
            date = dateConsoLampesUV;
        } else if (equipement == Equipement.Ozone) {
            date = dateConsoOzone;
        } else if (equipement == Equipement.Electrolyseur) {
            date = dateConsoElectrolyseur;
        } else if (equipement == Equipement.PhMoins) {
            date = dateConsoPhMoins;
        } else if (equipement == Equipement.PhPlus) {
            date = dateConsoPhPlus;
        } else if (equipement == Equipement.Orp) {
            date = dateConsoOrp;
        } else if (equipement == Equipement.Algicide) {
            date = dateConsoAlgicide;
        }

        return date;
    }

    public void definirDateConso(Equipement equipement, String date) {
        if (equipement == Equipement.PompeFiltration) {
            dateConsoPompeFiltration = date;
        } else if (equipement == Equipement.Surpresseur) {
            dateConsoSurpresseur = date;
        } else if (equipement == Equipement.Chauffage) {
            dateConsoChauffage = date;
        } else if (equipement == Equipement.LampesUV) {
            dateConsoLampesUV = date;
        } else if (equipement == Equipement.Ozone) {
            dateConsoOzone = date;
        } else if (equipement == Equipement.Electrolyseur) {
            dateConsoElectrolyseur = date;
        } else if (equipement == Equipement.PhMoins) {
            dateConsoPhMoins = date;
        } else if (equipement == Equipement.PhPlus) {
            dateConsoPhPlus = date;
        } else if (equipement == Equipement.Orp) {
            dateConsoOrp = date;
        } else if (equipement == Equipement.Algicide) {
            dateConsoAlgicide = date;
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

    public int obtenirEnclHorsGel() {
        return enclHorsGel;
    }

    public void definirEnclHorsGel(int index) {
        enclHorsGel = index;
    }

    public int obtenirArretHorsGel() {
        return arretHorsGel;
    }

    public void definirArretHorsGel(int index) {
        arretHorsGel = index;
    }

    public int obtenirFreqHorsGel() {
        return freqHorsGel;
    }

    public void definirFreqHorsGel(int index) {
        freqHorsGel = index;
    }

    public int obtenirTypeOzone() {
        return typeOzone;
    }

    public void definirTypeOzone(int valeur) {
        typeOzone = valeur;
    }

    public int obtenirNombreVentilateursOzone() {
        return nombreVentilateursOzone;
    }

    public void definirNombreVentilateursOzone(int valeur) {
        nombreVentilateursOzone = valeur;
    }

    public int obtenirTempoOzone() {
        return tempoOzone;
    }

    public void definirTempoOzone(int valeur) {
        tempoOzone = valeur;
    }

    public int obtenirErreurOzone() {
        return erreurOzone;
    }

    public void definirErreurOzone(int valeur) {
        erreurOzone = valeur;
    }

    public int obtenirVitesseFan1Ozone() {
        return vitesseFan1Ozone;
    }

    public void definirVitesseFan1Ozone(int valeur) {
        vitesseFan1Ozone = valeur;
    }

    public int obtenirVitesseFan2Ozone() {
        return vitesseFan2Ozone;
    }

    public void definirVitesseFan2Ozone(int valeur) {
        vitesseFan2Ozone = valeur;
    }

    public int obtenirCourantAlimOzone() {
        return courantAlimOzone;
    }

    public void definirCourantAlimOzone(int valeur) {
        courantAlimOzone = valeur;
    }

    public double obtenirTensionAlimOzone() {
        return tensionAlimOzone;
    }

    public void definirTensionAlimOzone(double valeur) {
        tensionAlimOzone = valeur;
    }


    public int obtenirHauteTensionAlimOzone() {
        return hauteTensionAlimOzone;
    }

    public void definirHauteTensionAlimOzone(int valeur) {
        hauteTensionAlimOzone = valeur;
    }

    public double obtenirVolume(Equipement equipement) {
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

    public void definirVolume(Equipement equipement, double volume) {
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

    public double obtenirVolumeRestant(Equipement equipement) {
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

    public void definirVolumeRestant(Equipement equipement, double volume) {
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

    public StructurePlage obtenirPlageFonctionnement(Equipement equipement, int index) {
        StructurePlage plage = null;

        switch (equipement) {
            case PompeFiltration:
                plage = listePlagePompeFiltration[index];
                break;
            case Surpresseur:
                plage = listePlageSurpresseur[index];
                break;
            case HeuresCreuses:
                plage = listePlageHeuresCreuses[index];
                break;
            case Eclairage:
                plage = listePlageEclairage[index];
                break;
            default:
                break;
        }

        return plage;
    }

    public void definirPlageFonctionnement(Equipement equipement, int index, String plage) {
        StructurePlage structurePlage = null;

        switch (equipement) {
            case PompeFiltration:
                structurePlage = listePlagePompeFiltration[index];
                break;
            case Surpresseur:
                structurePlage = listePlageSurpresseur[index];
                break;
            case HeuresCreuses:
                structurePlage = listePlageHeuresCreuses[index];
                break;
            case Eclairage:
                structurePlage = listePlageEclairage[index];
                break;
            default:
                return;
        }

        if (structurePlage == null) {
            structurePlage = new StructurePlage();
        }
        structurePlage.setEtat(plage.split(" - ").length == 2 ? !plage.split(" - ")[0].equals(plage.split(" - ")[1]) : false);
        structurePlage.setPlage(plage);

        switch (equipement) {
            case PompeFiltration:
                listePlagePompeFiltration[index] = structurePlage;
                break;
            case Surpresseur:
                listePlageSurpresseur[index] = structurePlage;
                break;
            case HeuresCreuses:
                listePlageHeuresCreuses[index] = structurePlage;
                break;
            case Eclairage:
                listePlageEclairage[index] = structurePlage;
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
        controleTemperature = type;
    }

    public int obtenirTemperatureConsigne() {
        return temperatureConsigne;
    }

    public void definirTemperatureConsigne(int valeur) {
        temperatureConsigne = valeur;
    }

    public int obtenirGestionReversible() {
        return gestionReversible;
    }

    public void definirGestionReversible(int valeur) {
        gestionReversible = valeur;
    }

    public int obtenirTemperatureReversible() {
        return temperatureReversible;
    }

    public void definirTemperatureReversible(int valeur) {
        temperatureReversible = valeur;
    }

    public double obtenirVolumeBassin() {
        return volumeBassin;
    }

    public void definirVolumeBassin(double volume) {
        volumeBassin = volume;
    }

    public int obtenirTempoDemarrage() {
        return tempoDemarrage;
    }

    public void definirTempoDemarrage(int valeur) {
        tempoDemarrage = valeur;
    }

    public int obtenirTypeRefoulement() {
        return typeRefoulement;
    }

    public void definirTypeRefoulement(int type) {
        typeRefoulement = type;
    }

    public int obtenirTypeRegulation() {
        return typeRegulation;
    }

    public void definirTypeRegulation(int type) {
        typeRegulation = type;
    }

    public int obtenirTempsSecuriteInjection() {
        return tempsSecuriteInjection;
    }

    public void definirTempsSecuriteInjection(int valeur) {
        tempsSecuriteInjection = valeur;
    }

    public double obtenirHystInjectionPh() {
        return hystInjectionPh;
    }

    public void definirHystInjectionPh(double valeur) {
        hystInjectionPh = valeur;
    }

    public int obtenirHystInjectionChloreOrp() {
        return hystInjectionChloreOrp;
    }

    public void definirHystInjectionChloreOrp(int valeur) {
        hystInjectionChloreOrp = valeur;
    }

    public double obtenirHystInjectionChloreAmpero() {
        return hystInjectionChloreAmpero;
    }

    public void definirHystInjectionChloreAmpero(double valeur) {
        hystInjectionChloreAmpero = valeur;
    }

    public double obtenirPointConsignePh() {
        return pointConsignePh;
    }

    public void definirPointConsignePh(double valeur) {
        pointConsignePh = valeur;
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

    public int obtenirPointConsigneOrp() {
        return pointConsigneOrp;
    }

    public void definirPointConsigneOrp(int valeur) {
        pointConsigneOrp = valeur;
    }

    public int obtenirHysteresisOrp() {
        return hysteresisOrp;
    }

    public void definirHysteresisOrp(int valeur) {
        hysteresisOrp = valeur;
    }

    public double obtenirPointConsigneAmpero() {
        return pointConsigneAmpero;
    }

    public void definirPointConsigneAmpero(double valeur) {
        pointConsigneAmpero = valeur;
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

    public int obtenirDureeInjectionMinimum(Equipement equipement) {
        int duree = 0;

        switch (equipement) {
            case PhMoins:
                duree = dureeInjectionMinimumPhMoins;
                break;
            case PhPlus:
                duree = dureeInjectionMinimumPhPlus;
                break;
            case Orp:
                duree = dureeInjectionMinimumOrp;
                break;
            default:
                break;
        }

        return duree;
    }

    public void definirDureeInjectionMinimum(Equipement equipement, int duree) {
        switch (equipement) {
            case PhMoins:
                dureeInjectionMinimumPhMoins = duree;
                break;
            case PhPlus:
                dureeInjectionMinimumPhPlus = duree;
                break;
            case Orp:
                dureeInjectionMinimumOrp = duree;
                break;
            default:
                break;
        }
    }

    public double obtenirDureeInjection(Equipement equipement) {
        double duree = 0;

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

    public void definirDureeInjection(Equipement equipement, double duree) {
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

    public int obtenirTempsReponse(Equipement equipement) {
        int duree = 0;

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

    public void definirTempsReponse(Equipement equipement, int duree) {
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

    public int obtenirJourSurchloration() {
        return jourSurchloration;
    }

    public void definirJourSurchloration(int valeur) {
        jourSurchloration = valeur;
    }

    public String obtenirFrequenceSurchloration() {
        return frequenceSurchloration;
    }

    public void definirFrequenceSurchloration(String valeur) {
        frequenceSurchloration = valeur;
    }

    public int obtenirValeurSurchloration() {
        return valeurSurchloration;
    }

    public void definirValeurSurchloration(int valeur) {
        valeurSurchloration = valeur;
    }

    public int obtenirProchaineSurchloration() {
        return prochaineSurchloration;
    }

    public void definirProchaineSurchloration(int valeur) {
        prochaineSurchloration = valeur;
    }

    public int obtenirJourAlgicide() {
        return jourAlgicide;
    }

    public void definirJourAlgicide(int valeur) {
        jourAlgicide = valeur;
    }

    public String obtenirFrequenceAlgicide() {
        return frequenceAlgicide;
    }

    public void definirFrequenceAlgicide(String valeur) {
        frequenceAlgicide = valeur;
    }

    public int obtenirDureeAlgicide() {
        return dureeAlgicide;
    }

    public void definirDureeAlgicide(int valeur) {
        dureeAlgicide = valeur;
    }

    public int obtenirDureeRestantAlgicide() {
        return dureeRestantAlgicide;
    }

    public void definirDureeRestantAlgicide(int valeur) {
        dureeRestantAlgicide = valeur;
    }

    public int obtenirProchaineAlgicide() {
        return prochaineAlgicide;
    }

    public void definirProchaineAlgicide(int valeur) {
        prochaineAlgicide = valeur;
    }

    public boolean obtenirCapteurInstalle(Capteur capteur) {
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

    public void definirCapteurInstalle(Capteur capteur, boolean etat) {
        if (capteur == Capteur.TemperatureBassin) {
            presenceCapteurPt = etat;
        } else if (capteur == Capteur.CapteurInterne) {
            presenceCapteurInterne = etat;
        } else if (capteur == Capteur.CapteurExterne) {
            presenceCapteurExterne = etat;
        } else if (capteur == Capteur.Ph) {
            presenceCapteurPh = etat;
        } else if (capteur == Capteur.Redox) {
            presenceCapteurRedox = etat;
        } else if (capteur == Capteur.Pression) {
            presenceCapteurPression = etat;
        } else if (capteur == Capteur.Ampero) {
            presenceCapteurAmpero = etat;
        } else if (capteur == Capteur._4_20_Libre) {
            presenceCapteur_4_20_Libre = etat;
        }
    }

    public boolean obtenirEtatCapteur(Capteur capteur) {
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

    public void definirEtatCapteur(Capteur capteur, String etat) {
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

    public double obtenirValeurCapteur(Capteur capteur) {
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

    public void definirValeurCapteur(Capteur capteur, double valeur) {
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

    public void definirEtalonnageCapteur(Capteur capteur, boolean etat, double valeur) {
        if (capteur == Capteur.TemperatureBassin) {
            etatEtalonnageBassin = etat;
            etalonnageBassin = valeur;
        } else if (capteur == Capteur.Ph) {
            etatEtalonnagePh = etat;
            etalonnagePh = valeur;
        } else if (capteur == Capteur.Redox) {
            etatEtalonnageRedox = etat;
            etalonnageRedox = valeur;
        } else if (capteur == Capteur.Pression) {
            etatEtalonnagePression = etat;
            etalonnagePression = valeur;
        } else if (capteur == Capteur.Ampero) {
            etatEtalonnageAmpero = etat;
            etalonnageAmpero = valeur;
        }
    }

    public double obtenirValeurEtalonnageCapteur(Capteur capteur) {
        double valeur = 0;

        if (capteur == Capteur.TemperatureBassin) {
            valeur = etalonnageBassin;
        } else if (capteur == Capteur.Ph) {
            valeur = etalonnagePh;
        } else if (capteur == Capteur.Redox) {
            valeur = etalonnageRedox;
        } else if (capteur == Capteur.Pression) {
            valeur = etalonnagePression;
        } else if (capteur == Capteur.Ampero) {
            valeur = etalonnageAmpero;
        }

        return valeur;
    }

    public boolean obtenirEtatEtalonnageCapteur(Capteur capteur) {
        boolean valeur = false;

        if (capteur == Capteur.TemperatureBassin) {
            valeur = etatEtalonnageBassin;
        } else if (capteur == Capteur.Ph) {
            valeur = etatEtalonnagePh;
        } else if (capteur == Capteur.Redox) {
            valeur = etatEtalonnageRedox;
        } else if (capteur == Capteur.Pression) {
            valeur = etatEtalonnagePression;
        } else if (capteur == Capteur.Ampero) {
            valeur = etatEtalonnageAmpero;
        }

        return valeur;
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

    public boolean obtenirPointConsigneOrpAuto() {
        return pointConsigneOrpAuto;
    }

    public void definirPointConsigneOrpAuto(boolean actif) {
        pointConsigneOrpAuto = actif;
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

    public Boolean contientRequeteHttp() {
        return listeHttp.size() > 0;
    }

    public Boolean contientRequeteHttp(StructureHttp.RequestHTTP request, StructureHttp.PageHTTP page, String data) {
        Boolean ret = false;

        for (int i = 0; i < listeHttp.size(); i++) {
            if (i >= listeHttp.size()) {
                break;
            }
            if ((listeHttp.get(i).getRequest() == request) && (listeHttp.get(i).getPage() == page) && (listeHttp.get(i).getData().equals(data))) {
                ret = true;
                break;
            }
        }

        return ret;
    }

    public StructureHttp obtenirStructureHttp() {
        return listeHttp.get(0);
    }

    public void ajouterRequeteHttp(StructureHttp.RequestHTTP request, StructureHttp.PageHTTP page, String data, boolean fromBt) {
        listeHttp.add(new StructureHttp(request, page, data, fromBt));
    }

    public void supprimerRequeteHttp() {
        listeHttp.remove(0);
    }

    public Boolean contientRequeteBt() {
        return listeBt.size() > 0;
    }

    public Boolean contientRequeteBt(String data) {
        Boolean ret = false;

        for (int i = 0; i < listeBt.size(); i++) {
            if (i >= listeBt.size()) {
                break;
            }
            if (listeBt.get(i).getData().equals(data)) {
                ret = true;
                break;
            }
        }

        return ret;
    }

    public StructureBt obtenirStructureBt() {
        return listeBt.get(0);
    }

    public void ajouterRequeteBt(String data, boolean progressDialogVisible) {
        listeBt.add(new StructureBt(data, progressDialogVisible));
    }

    public void supprimerRequeteBt() {
        listeBt.remove(0);
    }

    public void setWiFiState(int state) {
        wifiState = state;
    }

    public int getWiFiState() {
        return wifiState;
    }

    public void stopTimer() {
        timerState = false;
    }

    public void startTimer() {
        timerState = true;
    }

    public boolean getTimerState() {
        return timerState;
    }
}
