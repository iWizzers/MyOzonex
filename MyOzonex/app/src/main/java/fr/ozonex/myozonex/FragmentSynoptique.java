package fr.ozonex.myozonex;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentSynoptique extends Fragment implements View.OnClickListener {
    View view = null;

    AnimationInjection animationInjectionOzone;
    AnimationInjection animationInjectionRegPhPlus;
    AnimationInjection animationInjectionRegPhMoins;
    AnimationInjection animationInjectionRegORP;
    AnimationInjection animationInjectionAlgicide;
    AnimationRefoulement animationRefoulement;

    ProgressBar contenuBidonAlgicide;
    ProgressBar contenuBidonOrp;
    ProgressBar contenuBidonPhMoins;
    ProgressBar contenuBidonPhPlus;
    View ligneCapteurPt;
    TextView texteCapteurPt;
    View ligneCapteurPh;
    View ligneCapteurPhMoins1;
    View ligneCapteurPhMoins2;
    View ligneCapteurPhPlus1;
    View ligneCapteurPhPlus2;
    View ligneCapteurOrp1;
    View ligneCapteurOrp2;
    View ligneCapteurOrp3;
    View ligneCapteurOrp4;
    View ligneCapteurAmpero;
    LinearLayout conteneurDonneesCapteurs;
    TextView texteTypeRegulation;
    TextView texteCapteurPh;
    TextView texteCapteurOrp;
    TextView texteCapteurAmpero;
    View ligneSortieOzone;
    View ligneInjectionAlgicide;
    View ligneInjectionOrp;
    View ligneInjectionPhMoins;
    View ligneInjectionPhPlus;
    View ligneEntreeTor;
    View ligneEntreeLineaire1;
    View ligneEntreeLineaire2;
    View ligneEntreePorteSonde;
    View ligneSortiePorteSonde1;
    View ligneSortiePorteSonde2;
    View ligneSortieTor1;
    View ligneSortieTor2;
    View ligneSortieTor3;
    ImageView tuyauSkimmer1;
    ImageView tuyauSkimmer2;
    ImageView tuyauSkimmer3;
    ImageView tuyauSkimmer4;
    ImageView tuyauSkimmer5;
    ImageView tuyauBondeFond1;
    ImageView tuyauBondeFond2;
    ImageView tuyauBondeFond3;
    ImageView tuyauBondeFond4;
    ImageView tuyauBondeFond5;
    ImageView tuyauPriseBalais1;
    ImageView tuyauPriseBalais2;
    ImageView tuyauPriseBalais3;
    ImageView tuyauPriseBalais4;
    ImageView tuyauPriseBalais5;
    ImageView tuyauPriseBalais6;
    ImageView tuyauSortieSurpresseur;
    ImageView tuyauTPriseBalais;
    ImageView tuyauEntreePompeFiltration1;
    ImageView tuyauEntreePompeFiltration2;
    ImageView tuyauEntreeCroixPompeFiltration;
    ImageView tuyauEntreePompeFiltration3;
    ImageView tuyauSortiePompeFiltration1;
    ImageView tuyauSortiePompeFiltration2;
    ImageView tuyauSortiePompeFiltration3;
    ImageView tuyauSortieFiltre1;
    ImageView tuyauSortieFiltre2;
    ImageView tuyauSortieTFiltre;
    ImageView tuyauSortieFiltreSurpresseur1;
    ImageView tuyauSortieFiltreChauffage1;
    ImageView tuyauSortieFiltreChauffage2;
    ImageView tuyauSortieFiltreChauffage3;
    ImageView tuyauSortieFiltreChauffage4;
    ImageView tuyauSortieTFiltreChauffage;
    ImageView tuyauEntreeSurpresseur1;
    ImageView tuyauEntreeSurpresseur2;
    ImageView tuyauEntreeChauffage1;
    ImageView tuyauEntreeChauffage2;
    ImageView tuyauEntreeChauffage3;
    ImageView tuyauEntreePanneauSolaire1;
    ImageView tuyauEntreePanneauSolaire2;
    ImageView tuyauEntreePanneauSolaire3;
    ImageView tuyauEntreePanneauSolaire4;
    ImageView tuyauSortiePanneauSolaire1;
    ImageView tuyauSortiePanneauSolaire2;
    ImageView tuyauSortiePanneauSolaire3;
    ImageView tuyauSortieChauffage1;
    ImageView tuyauSortieChauffage2;
    ImageView tuyauSortieChauffage3;
    ImageView tuyauPassageUv;
    ImageView tuyauEntreeUv1;
    ImageView tuyauEntreeUv2;
    ImageView tuyauSortieUv1;
    ImageView tuyauSortieUv2;
    ImageView tuyauEntreeElectrolyseur;
    ImageView tuyauSortieElectrolyseur;
    ImageView tuyauInjections;
    ImageView tuyauRefoulements1;
    ImageView tuyauRefoulements2;
    ImageView tuyauRefoulements3;
    ImageView vannePriseBalais;
    ImageView vanneSortieBondeFond;
    ImageView vanneSortieSkimmer;
    ImageView vanneEntreeSurpresseur;
    ImageView vanneSortieSurpresseur;
    ImageView vanneSortieFiltre;
    ImageView vanneEntreeChauffage;
    ImageView vanneSortieChauffage;
    ImageView sondeOzone;
    ImageView sondePt;
    ImageView sondePh;
    ImageView sondeOrp;
    ImageView sondeAmpero;
    GridLayout layoutCapteurInterne;
    TextView texteValeurTempInterne;
    TextView texteValeurHumiditeInterne;
    TextView texteValeurPressionAtmInterne;
    GridLayout layoutCapteurExterne;
    TextView texteValeurTempExterne;
    TextView texteValeurHumiditeExterne;
    TextView texteValeurPressionAtmExterne;
    ImageButton boutonEclairage1;
    ImageButton boutonEclairage2;
    TextView texteModeEclairage;
    ImageButton boutonPompeFiltration;
    TextView texteModePompeFiltration;
    ImageButton boutonOzonateur;
    TextView texteModeOzonateur;
    ImageButton boutonSurpresseur;
    TextView texteModeSurpresseur;
    ImageButton boutonFiltre;
    TextView texteCapteurPression;
    TextView texteRincageFiltre;
    ImageView helicePac;
    View backgroundHelicePac;
    Rotate rotateHelicePac;
    ImageButton boutonChauffage;
    TextView texteModeChauffage;
    ImageButton boutonLampesUv;
    TextView texteModeLampesUV;
    ImageButton boutonAlgicide;
    ImageView bidonAlgicide;
    TextView texteModeAlgicide;
    TextView texteAlgicide;
    TextView texteConsoAlgicide;
    ImageButton boutonRegulateurOrp;
    ImageView bidonOrp;
    TextView texteModeRegulateurOrp;
    TextView texteRegulateurOrp;
    TextView texteConsoOrp;
    ImageButton boutonRegulateurPhMoins;
    ImageView bidonPhMoins;
    TextView texteModeRegulateurPhMoins;
    TextView texteRegulateurPhMoins;
    TextView texteConsoPhMoins;
    ImageButton boutonRegulateurPhPlus;
    ImageView bidonPhPlus;
    TextView texteModeRegulateurPhPlus;
    TextView texteRegulateurPhPlus;
    TextView texteConsoPhPlus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.synoptique_layout, container, false);

        contenuBidonAlgicide = view.findViewById(R.id.contenu_bidon_algicide);
        contenuBidonOrp = view.findViewById(R.id.contenu_bidon_orp);
        contenuBidonPhMoins = view.findViewById(R.id.contenu_bidon_ph_moins);
        contenuBidonPhPlus = view.findViewById(R.id.contenu_bidon_ph_plus);
        ligneCapteurPt = view.findViewById(R.id.ligne_capteur_pt);
        texteCapteurPt = view.findViewById(R.id.texte_capteur_pt);
        ligneCapteurPh = view.findViewById(R.id.ligne_capteur_ph);
        ligneCapteurPhMoins1 = view.findViewById(R.id.ligne_capteur_ph_moins_1);
        ligneCapteurPhMoins2 = view.findViewById(R.id.ligne_capteur_ph_moins_2);
        ligneCapteurPhPlus1 = view.findViewById(R.id.ligne_capteur_ph_plus_1);
        ligneCapteurPhPlus2 = view.findViewById(R.id.ligne_capteur_ph_plus_2);
        ligneCapteurOrp1 = view.findViewById(R.id.ligne_capteur_orp_1);
        ligneCapteurOrp2 = view.findViewById(R.id.ligne_capteur_orp_2);
        ligneCapteurOrp3 = view.findViewById(R.id.ligne_capteur_orp_3);
        ligneCapteurOrp4 = view.findViewById(R.id.ligne_capteur_orp_4);
        ligneCapteurAmpero = view.findViewById(R.id.ligne_capteur_ampero);
        conteneurDonneesCapteurs = view.findViewById(R.id.conteneur_donnees_capteurs);
        texteTypeRegulation = view.findViewById(R.id.texte_type_regulation);
        texteCapteurPh = view.findViewById(R.id.texte_capteur_ph);
        texteCapteurOrp = view.findViewById(R.id.texte_capteur_orp);
        texteCapteurAmpero = view.findViewById(R.id.texte_capteur_ampero);
        ligneSortieOzone = view.findViewById(R.id.ligne_sortie_ozone);
        ligneInjectionAlgicide = view.findViewById(R.id.ligne_injection_algicide);
        ligneInjectionOrp = view.findViewById(R.id.ligne_injection_orp);
        ligneInjectionPhMoins = view.findViewById(R.id.ligne_injection_ph_moins);
        ligneInjectionPhPlus = view.findViewById(R.id.ligne_injection_ph_plus);
        ligneEntreeTor = view.findViewById(R.id.ligne_entree_tor);
        ligneEntreeLineaire1 = view.findViewById(R.id.ligne_entree_lineaire_1);
        ligneEntreeLineaire2 = view.findViewById(R.id.ligne_entree_lineaire_2);
        ligneEntreePorteSonde = view.findViewById(R.id.ligne_entree_porte_sonde);
        ligneSortiePorteSonde1 = view.findViewById(R.id.ligne_sortie_porte_sonde_1);
        ligneSortiePorteSonde2 = view.findViewById(R.id.ligne_sortie_porte_sonde_2);
        ligneSortieTor1 = view.findViewById(R.id.ligne_sortie_tor_1);
        ligneSortieTor2 = view.findViewById(R.id.ligne_sortie_tor_2);
        ligneSortieTor3 = view.findViewById(R.id.ligne_sortie_tor_3);
        tuyauSkimmer1 = view.findViewById(R.id.tuyau_skimmer_1);
        tuyauSkimmer2 = view.findViewById(R.id.tuyau_skimmer_2);
        tuyauSkimmer3 = view.findViewById(R.id.tuyau_skimmer_3);
        tuyauSkimmer4 = view.findViewById(R.id.tuyau_skimmer_4);
        tuyauSkimmer5 = view.findViewById(R.id.tuyau_skimmer_5);
        tuyauBondeFond1 = view.findViewById(R.id.tuyau_bonde_fond_1);
        tuyauBondeFond2 = view.findViewById(R.id.tuyau_bonde_fond_2);
        tuyauBondeFond3 = view.findViewById(R.id.tuyau_bonde_fond_3);
        tuyauBondeFond4 = view.findViewById(R.id.tuyau_bonde_fond_4);
        tuyauBondeFond5 = view.findViewById(R.id.tuyau_bonde_fond_5);
        tuyauPriseBalais1 = view.findViewById(R.id.tuyau_prise_balais_1);
        tuyauPriseBalais2 = view.findViewById(R.id.tuyau_prise_balais_2);
        tuyauPriseBalais3 = view.findViewById(R.id.tuyau_prise_balais_3);
        tuyauPriseBalais4 = view.findViewById(R.id.tuyau_prise_balais_4);
        tuyauPriseBalais5 = view.findViewById(R.id.tuyau_prise_balais_5);
        tuyauPriseBalais6 = view.findViewById(R.id.tuyau_prise_balais_6);
        tuyauSortieSurpresseur = view.findViewById(R.id.tuyau_sortie_surpresseur);
        tuyauTPriseBalais = view.findViewById(R.id.tuyau_T_prise_balais);
        tuyauEntreePompeFiltration1 = view.findViewById(R.id.tuyau_entree_pompe_filtration_1);
        tuyauEntreePompeFiltration2 = view.findViewById(R.id.tuyau_entree_pompe_filtration_2);
        tuyauEntreeCroixPompeFiltration = view.findViewById(R.id.tuyau_entree_croix_pompe_filtration);
        tuyauEntreePompeFiltration3 = view.findViewById(R.id.tuyau_entree_pompe_filtration_3);
        tuyauSortiePompeFiltration1 = view.findViewById(R.id.tuyau_sortie_pompe_filtration_1);
        tuyauSortiePompeFiltration2 = view.findViewById(R.id.tuyau_sortie_pompe_filtration_2);
        tuyauSortiePompeFiltration3 = view.findViewById(R.id.tuyau_sortie_pompe_filtration_3);
        tuyauSortieFiltre1 = view.findViewById(R.id.tuyau_sortie_filtre_1);
        tuyauSortieFiltre2 = view.findViewById(R.id.tuyau_sortie_filtre_2);
        tuyauSortieTFiltre = view.findViewById(R.id.tuyau_sortie_t_filtre);
        tuyauSortieFiltreSurpresseur1 = view.findViewById(R.id.tuyau_sortie_filtre_surpresseur_1);
        tuyauSortieFiltreChauffage1 = view.findViewById(R.id.tuyau_sortie_filtre_chauffage_1);
        tuyauSortieFiltreChauffage2 = view.findViewById(R.id.tuyau_sortie_filtre_chauffage_2);
        tuyauSortieFiltreChauffage3 = view.findViewById(R.id.tuyau_sortie_filtre_chauffage_3);
        tuyauSortieFiltreChauffage4 = view.findViewById(R.id.tuyau_sortie_filtre_chauffage_4);
        tuyauSortieTFiltreChauffage = view.findViewById(R.id.tuyau_sortie_t_filtre_chauffage);
        tuyauEntreeSurpresseur1 = view.findViewById(R.id.tuyau_entree_surpresseur_1);
        tuyauEntreeSurpresseur2 = view.findViewById(R.id.tuyau_entree_surpresseur_2);
        tuyauEntreeChauffage1 = view.findViewById(R.id.tuyau_entree_chauffage_1);
        tuyauEntreeChauffage2 = view.findViewById(R.id.tuyau_entree_chauffage_2);
        tuyauEntreeChauffage3 = view.findViewById(R.id.tuyau_entree_chauffage_3);
        tuyauEntreePanneauSolaire1 = view.findViewById(R.id.tuyau_entree_panneau_solaire);
        tuyauEntreePanneauSolaire2 = view.findViewById(R.id.tuyau_entree_panneau_solaire_2);
        tuyauEntreePanneauSolaire3 = view.findViewById(R.id.tuyau_entree_panneau_solaire_3);
        tuyauEntreePanneauSolaire4 = view.findViewById(R.id.tuyau_entree_panneau_solaire_4);
        tuyauSortiePanneauSolaire1 = view.findViewById(R.id.tuyau_sortie_panneau_solaire);
        tuyauSortiePanneauSolaire2 = view.findViewById(R.id.tuyau_sortie_panneau_solaire_2);
        tuyauSortiePanneauSolaire3 = view.findViewById(R.id.tuyau_sortie_panneau_solaire_3);
        tuyauSortieChauffage1 = view.findViewById(R.id.tuyau_sortie_chauffage_1);
        tuyauSortieChauffage2 = view.findViewById(R.id.tuyau_sortie_chauffage_2);
        tuyauSortieChauffage3 = view.findViewById(R.id.tuyau_sortie_chauffage_3);
        tuyauPassageUv = view.findViewById(R.id.tuyau_passage_uv);
        tuyauEntreeUv1 = view.findViewById(R.id.tuyau_entree_uv_1);
        tuyauEntreeUv2 = view.findViewById(R.id.tuyau_entree_uv_2);
        tuyauSortieUv1 = view.findViewById(R.id.tuyau_sortie_uv_1);
        tuyauSortieUv2 = view.findViewById(R.id.tuyau_sortie_uv_2);
        tuyauEntreeElectrolyseur = view.findViewById(R.id.tuyau_entree_electrolyseur);
        tuyauSortieElectrolyseur = view.findViewById(R.id.tuyau_sortie_electrolyseur);
        tuyauInjections = view.findViewById(R.id.tuyau_injections);
        tuyauRefoulements1 = view.findViewById(R.id.tuyau_refoulements_1);
        tuyauRefoulements2 = view.findViewById(R.id.tuyau_refoulements_2);
        tuyauRefoulements3 = view.findViewById(R.id.tuyau_refoulements_3);
        vannePriseBalais = view.findViewById(R.id.vanne_prise_balais);
        vanneSortieBondeFond = view.findViewById(R.id.vanne_sortie_bonde_fond);
        vanneSortieSkimmer = view.findViewById(R.id.vanne_sortie_skimmer);
        vanneEntreeSurpresseur = view.findViewById(R.id.vanne_entree_surpresseur);
        vanneSortieSurpresseur = view.findViewById(R.id.vanne_sortie_surpresseur);
        vanneSortieFiltre = view.findViewById(R.id.vanne_sortie_filtre);
        vanneEntreeChauffage = view.findViewById(R.id.vanne_entree_chauffage);
        vanneSortieChauffage = view.findViewById(R.id.vanne_sortie_chauffage);
        sondeOzone = view.findViewById(R.id.sonde_ozone);
        sondePt = view.findViewById(R.id.sonde_pt);
        sondePh = view.findViewById(R.id.sonde_ph);
        sondeOrp = view.findViewById(R.id.sonde_orp);
        sondeAmpero = view.findViewById(R.id.sonde_ampero);
        layoutCapteurInterne = view.findViewById(R.id.layout_capteur_interne);
        texteValeurTempInterne = view.findViewById(R.id.texte_valeur_temperature_interne);
        texteValeurHumiditeInterne = view.findViewById(R.id.texte_valeur_humidite_interne);
        texteValeurPressionAtmInterne = view.findViewById(R.id.texte_valeur_pression_atm_interne);
        layoutCapteurExterne = view.findViewById(R.id.layout_capteur_externe);
        texteValeurTempExterne = view.findViewById(R.id.texte_valeur_temperature_externe);
        texteValeurHumiditeExterne = view.findViewById(R.id.texte_valeur_humidite_externe);
        texteValeurPressionAtmExterne = view.findViewById(R.id.texte_valeur_pression_atm_externe);
        boutonEclairage1 = view.findViewById(R.id.eclairage_1);
        boutonEclairage2 = view.findViewById(R.id.eclairage_2);
        texteModeEclairage = view.findViewById(R.id.texte_mode_eclairage);
        boutonPompeFiltration = view.findViewById(R.id.pompe_filtration);
        texteModePompeFiltration = view.findViewById(R.id.texte_mode_pompe_filtration);
        boutonOzonateur = view.findViewById(R.id.ozonateur);
        texteModeOzonateur = view.findViewById(R.id.texte_mode_ozonateur);
        boutonSurpresseur = view.findViewById(R.id.surpresseur);
        texteModeSurpresseur = view.findViewById(R.id.texte_mode_surpresseur);
        boutonFiltre = view.findViewById(R.id.filtre);
        texteCapteurPression = view.findViewById(R.id.texte_capteur_pression);
        texteRincageFiltre = view.findViewById(R.id.texte_rincage_filtre);
        helicePac = view.findViewById(R.id.helice_pac);
        backgroundHelicePac = view.findViewById(R.id.background_helice_pac);
        rotateHelicePac = new Rotate(helicePac, 120);
        boutonChauffage = view.findViewById(R.id.pac);
        texteModeChauffage = view.findViewById(R.id.texte_mode_pac);
        boutonLampesUv = view.findViewById(R.id.lampes_uv);
        texteModeLampesUV = view.findViewById(R.id.texte_mode_lampes_uv);
        boutonAlgicide = view.findViewById(R.id.algicide);
        bidonAlgicide = view.findViewById(R.id.bidon_algicide);
        texteModeAlgicide = view.findViewById(R.id.texte_mode_algicide);
        texteAlgicide = view.findViewById(R.id.texte_algicide);
        texteConsoAlgicide = view.findViewById(R.id.texte_conso_algicide);
        boutonRegulateurOrp = view.findViewById(R.id.regulateur_orp);
        bidonOrp = view.findViewById(R.id.bidon_orp);
        texteModeRegulateurOrp = view.findViewById(R.id.texte_mode_regulateur_orp);
        texteRegulateurOrp = view.findViewById(R.id.texte_regulateur_orp);
        texteConsoOrp = view.findViewById(R.id.texte_conso_orp);
        boutonRegulateurPhMoins = view.findViewById(R.id.regulateur_ph_moins);
        bidonPhMoins = view.findViewById(R.id.bidon_ph_moins);
        texteModeRegulateurPhMoins = view.findViewById(R.id.texte_mode_regulateur_ph_moins);
        texteRegulateurPhMoins = view.findViewById(R.id.texte_regulateur_ph_moins);
        texteConsoPhMoins = view.findViewById(R.id.texte_conso_ph_moins);
        boutonRegulateurPhPlus = view.findViewById(R.id.regulateur_ph_plus);
        bidonPhPlus = view.findViewById(R.id.bidon_ph_plus);
        texteModeRegulateurPhPlus = view.findViewById(R.id.texte_mode_regulateur_ph_plus);
        texteRegulateurPhPlus = view.findViewById(R.id.texte_regulateur_ph_plus);
        texteConsoPhPlus = view.findViewById(R.id.texte_conso_ph_plus);

        boutonEclairage1.setOnClickListener(this);
        boutonEclairage2.setOnClickListener(this);
        boutonPompeFiltration.setOnClickListener(this);
        boutonFiltre.setOnClickListener(this);
        boutonOzonateur.setOnClickListener(this);
        boutonSurpresseur.setOnClickListener(this);
        boutonChauffage.setOnClickListener(this);
        boutonLampesUv.setOnClickListener(this);
        boutonAlgicide.setOnClickListener(this);
        boutonRegulateurOrp.setOnClickListener(this);
        boutonRegulateurPhMoins.setOnClickListener(this);
        boutonRegulateurPhPlus.setOnClickListener(this);

        animationInjectionOzone = new AnimationInjection((RelativeLayout) view.findViewById(R.id.layout_sortie_ozone), ligneSortieOzone, true, Donnees.Equipement.Ozone, R.color.ozone);
        animationInjectionRegPhPlus = new AnimationInjection((RelativeLayout) view.findViewById(R.id.layout_sortie_reg_ph_plus), ligneInjectionPhPlus, false, Donnees.Equipement.PhPlus, R.color.phPlus);
        animationInjectionRegPhMoins = new AnimationInjection((RelativeLayout) view.findViewById(R.id.layout_sortie_reg_ph_moins), ligneInjectionPhMoins, false, Donnees.Equipement.PhMoins, R.color.phMoins);
        animationInjectionRegORP = new AnimationInjection((RelativeLayout) view.findViewById(R.id.layout_sortie_reg_orp), ligneInjectionOrp, false, Donnees.Equipement.Orp, R.color.orp);
        animationInjectionAlgicide = new AnimationInjection((RelativeLayout) view.findViewById(R.id.layout_sortie_algicide), ligneInjectionAlgicide, false, Donnees.Equipement.Algicide, R.color.algicide);
        animationRefoulement = new AnimationRefoulement((RelativeLayout) view.findViewById(R.id.layout_contenu_refoulement));

        Display display = MainActivity.instance().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        scale((AbsoluteLayout) view.findViewById(R.id.layout), Convertisseur.convertPixelsToDp(size.x), Convertisseur.convertPixelsToDp(size.y), Convertisseur.convertPixelsToDp(MainActivity.instance().toolbar.getHeight()));

        update();

        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            boolean pompeFiltrationActive = (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PompeFiltration) == Donnees.AUTO_MARCHE) || (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PompeFiltration) == Donnees.MARCHE);
            boolean surpresseurActive = (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Surpresseur) == Donnees.AUTO_MARCHE) || (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Surpresseur) == Donnees.MARCHE);
            boolean chauffageActive = (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Chauffage) == Donnees.AUTO_MARCHE) || (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Chauffage) == Donnees.MARCHE);
            boolean ozoneActive = (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Ozone) == Donnees.AUTO_MARCHE) || (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Ozone) == Donnees.MARCHE);
            int couleurLigneInjection = pompeFiltrationActive ? (((Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR) && chauffageActive) ? getResources().getColor(R.color.ligneInjectionActiveChaud) : getResources().getColor(R.color.ligneInjectionActive)) : getResources().getColor(R.color.ligneInjectionInactive);

            layoutCapteurInterne.setVisibility(!Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.CapteurInterne) ? View.GONE : View.VISIBLE);
            texteValeurTempInterne.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.TemperatureInterne) ? String.format("%.1f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.TemperatureInterne)) + " °C" : "Err");
            texteValeurHumiditeInterne.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.HumiditeInterne) ? String.format("%.1f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.HumiditeInterne)) + " %" : "Err");
            texteValeurPressionAtmInterne.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.PressionAtmospheriqueInterne) ? Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.PressionAtmospheriqueInterne) + " hPa" : "Err");

            layoutCapteurExterne.setVisibility(!Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.CapteurExterne) ? View.GONE : View.VISIBLE);
            texteValeurTempExterne.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.TemperatureExterne) ? String.format("%.1f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.TemperatureExterne)) + " °C" : "Err");
            texteValeurHumiditeExterne.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.HumiditeExterne) ? String.format("%.1f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.HumiditeExterne)) + " %" : "Err");
            texteValeurPressionAtmExterne.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.PressionAtmospheriqueExterne) ? Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.PressionAtmospheriqueExterne) + " hPa" : "Err");

            definirImageBouton(boutonEclairage1, Donnees.Equipement.Eclairage);
            definirImageBouton(boutonEclairage2, Donnees.Equipement.Eclairage);
            definirTexteMode(texteModeEclairage, Donnees.Equipement.Eclairage);

            definirImageBouton(boutonPompeFiltration, Donnees.Equipement.PompeFiltration);
            definirTexteMode(texteModePompeFiltration, Donnees.Equipement.PompeFiltration);

            texteCapteurPression.setVisibility(!Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Pression) ? View.GONE : View.VISIBLE);
            texteCapteurPression.setText("Pression : " + (Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Pression) ? String.format("%.2f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Pression)) + " bar" : "Err"));
            texteRincageFiltre.setVisibility(!(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Pression) && Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Pression) && (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Pression) >= Donnees.instance().obtenirPressionProchainLavage())) ? View.GONE : View.VISIBLE);

            texteModeSurpresseur.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur) ? View.GONE : View.VISIBLE);
            boutonSurpresseur.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur) ? View.GONE : View.VISIBLE);
            tuyauSortieFiltreSurpresseur1.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur) ? View.GONE : View.VISIBLE);
            vanneEntreeSurpresseur.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur) ? View.GONE : View.VISIBLE);
            tuyauEntreeSurpresseur1.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur) ? View.GONE : View.VISIBLE);
            tuyauEntreeSurpresseur2.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur) ? View.GONE : View.VISIBLE);
            tuyauSortieSurpresseur.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur) ? View.GONE : View.VISIBLE);
            vanneSortieSurpresseur.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur) ? View.GONE : View.VISIBLE);
            tuyauTPriseBalais.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur) ? View.GONE : View.VISIBLE);
            tuyauPriseBalais2.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur) ? View.GONE : View.VISIBLE);
            vannePriseBalais.setImageResource(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur) ? R.drawable.vanne_horizontal_inactif : R.drawable.vanne_horizontal_actif);
            definirImageBouton(boutonSurpresseur, Donnees.Equipement.Surpresseur);
            definirTexteMode(texteModeSurpresseur, Donnees.Equipement.Surpresseur);

            texteModeChauffage.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            helicePac.setVisibility(!(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) && (Donnees.instance().obtenirTypeChauffage() == Donnees.PAC)) ? View.GONE : View.VISIBLE);
            backgroundHelicePac.setVisibility(!(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) && (Donnees.instance().obtenirTypeChauffage() == Donnees.PAC)) ? View.GONE : View.VISIBLE);
            boutonChauffage.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            tuyauSortieTFiltre.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            tuyauSortieFiltre2.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            vanneEntreeChauffage.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            tuyauEntreeChauffage1.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            tuyauEntreeChauffage2.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            tuyauEntreeChauffage3.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            tuyauEntreePanneauSolaire1.setVisibility(!(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) && (Donnees.instance().obtenirTypeChauffage() >= Donnees.PANNEAU_SOLAIRE)) ? View.GONE : View.VISIBLE);
            tuyauEntreePanneauSolaire2.setVisibility(!(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) && (Donnees.instance().obtenirTypeChauffage() >= Donnees.PANNEAU_SOLAIRE)) ? View.GONE : View.VISIBLE);
            tuyauEntreePanneauSolaire3.setVisibility(!(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) && (Donnees.instance().obtenirTypeChauffage() >= Donnees.PANNEAU_SOLAIRE)) ? View.GONE : View.VISIBLE);
            tuyauEntreePanneauSolaire4.setVisibility(!(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) && (Donnees.instance().obtenirTypeChauffage() >= Donnees.PANNEAU_SOLAIRE)) ? View.GONE : View.VISIBLE);
            tuyauSortiePanneauSolaire1.setVisibility(!(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) && (Donnees.instance().obtenirTypeChauffage() == Donnees.PANNEAU_SOLAIRE)) ? View.GONE : View.VISIBLE);
            tuyauSortiePanneauSolaire2.setVisibility(!(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) && (Donnees.instance().obtenirTypeChauffage() == Donnees.PANNEAU_SOLAIRE)) ? View.GONE : View.VISIBLE);
            tuyauSortiePanneauSolaire3.setVisibility(!(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) && (Donnees.instance().obtenirTypeChauffage() == Donnees.PANNEAU_SOLAIRE)) ? View.GONE : View.VISIBLE);
            tuyauSortieChauffage1.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            tuyauSortieChauffage2.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            tuyauSortieChauffage3.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            vanneSortieChauffage.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            tuyauSortieFiltreChauffage4.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            tuyauSortieTFiltreChauffage.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            tuyauSortieFiltreChauffage2.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            definirImageBouton(boutonChauffage, Donnees.Equipement.Chauffage);
            definirTexteMode(texteModeChauffage, Donnees.Equipement.Chauffage);

            texteModeOzonateur.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Ozone) ? View.GONE : View.VISIBLE);
            boutonOzonateur.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Ozone) ? View.GONE : View.VISIBLE);
            ligneSortieOzone.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Ozone) ? View.GONE : View.VISIBLE);
            sondeOzone.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Ozone) ? View.GONE : View.VISIBLE);
            definirImageBouton(boutonOzonateur, Donnees.Equipement.Ozone);
            definirTexteMode(texteModeOzonateur, Donnees.Equipement.Ozone);
            definirInjection(Donnees.Equipement.Ozone);

            texteModeLampesUV.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.LampesUV) ? View.GONE : View.VISIBLE);
            boutonLampesUv.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.LampesUV) ? View.GONE : View.VISIBLE);
            tuyauEntreeUv1.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.LampesUV) ? View.GONE : View.VISIBLE);
            tuyauEntreeUv2.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.LampesUV) ? View.GONE : View.VISIBLE);
            tuyauSortieUv1.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.LampesUV) ? View.GONE : View.VISIBLE);
            tuyauSortieUv2.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.LampesUV) ? View.GONE : View.VISIBLE);
            tuyauPassageUv.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.LampesUV) ? View.GONE : View.VISIBLE);
            definirImageBouton(boutonLampesUv, Donnees.Equipement.LampesUV);
            definirTexteMode(texteModeLampesUV, Donnees.Equipement.LampesUV);

            // ELECTROLYSEUR

            texteModeAlgicide.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Algicide) ? View.GONE : View.VISIBLE);
            texteAlgicide.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Algicide) ? View.GONE : View.VISIBLE);
            boutonAlgicide.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Algicide) ? View.GONE : View.VISIBLE);
            bidonAlgicide.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Algicide) ? View.GONE : View.VISIBLE);
            texteConsoAlgicide.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Algicide) ? View.GONE : View.VISIBLE);
            contenuBidonAlgicide.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Algicide) ? View.GONE : View.VISIBLE);
            ligneInjectionAlgicide.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Algicide) ? View.GONE : View.VISIBLE);
            definirImageBouton(boutonAlgicide, Donnees.Equipement.Algicide);
            definirTexteMode(texteModeAlgicide, Donnees.Equipement.Algicide);
            definirConsoRegulation(texteConsoAlgicide, contenuBidonAlgicide, Donnees.Equipement.Algicide);
            definirInjection(Donnees.Equipement.Algicide);

            texteModeRegulateurOrp.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.GONE : View.VISIBLE);
            texteRegulateurOrp.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.GONE : View.VISIBLE);
            boutonRegulateurOrp.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.GONE : View.VISIBLE);
            bidonOrp.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.GONE : View.VISIBLE);
            texteConsoOrp.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.GONE : View.VISIBLE);
            contenuBidonOrp.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.GONE : View.VISIBLE);
            ligneInjectionOrp.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.GONE : View.VISIBLE);
            definirImageBouton(boutonRegulateurOrp, Donnees.Equipement.Orp);
            definirTexteMode(texteModeRegulateurOrp, Donnees.Equipement.Orp);
            definirConsoRegulation(texteConsoOrp, contenuBidonOrp, Donnees.Equipement.Orp);
            definirInjection(Donnees.Equipement.Orp);

            texteModeRegulateurPhMoins.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? View.GONE : View.VISIBLE);
            texteRegulateurPhMoins.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? View.GONE : View.VISIBLE);
            boutonRegulateurPhMoins.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? View.GONE : View.VISIBLE);
            bidonPhMoins.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? View.GONE : View.VISIBLE);
            texteConsoPhMoins.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? View.GONE : View.VISIBLE);
            contenuBidonPhMoins.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? View.GONE : View.VISIBLE);
            ligneInjectionPhMoins.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? View.GONE : View.VISIBLE);
            definirImageBouton(boutonRegulateurPhMoins, Donnees.Equipement.PhMoins);
            definirTexteMode(texteModeRegulateurPhMoins, Donnees.Equipement.PhMoins);
            definirConsoRegulation(texteConsoPhMoins, contenuBidonPhMoins, Donnees.Equipement.PhMoins);
            definirInjection(Donnees.Equipement.PhMoins);

            texteModeRegulateurPhPlus.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) ? View.GONE : View.VISIBLE);
            texteRegulateurPhPlus.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) ? View.GONE : View.VISIBLE);
            boutonRegulateurPhPlus.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) ? View.GONE : View.VISIBLE);
            bidonPhPlus.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) ? View.GONE : View.VISIBLE);
            texteConsoPhPlus.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) ? View.GONE : View.VISIBLE);
            contenuBidonPhPlus.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) ? View.GONE : View.VISIBLE);
            ligneInjectionPhPlus.setVisibility(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) ? View.GONE : View.VISIBLE);
            definirImageBouton(boutonRegulateurPhPlus, Donnees.Equipement.PhPlus);
            definirTexteMode(texteModeRegulateurPhPlus, Donnees.Equipement.PhPlus);
            definirConsoRegulation(texteConsoPhPlus, contenuBidonPhPlus, Donnees.Equipement.PhPlus);
            definirInjection(Donnees.Equipement.PhPlus);

            definirRefoulement();

            sondePt.setVisibility(!Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.TemperatureBassin) ? View.GONE : View.VISIBLE);
            ligneCapteurPt.setVisibility(!Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.TemperatureBassin) ? View.GONE : View.VISIBLE);
            texteCapteurPt.setVisibility(!Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.TemperatureBassin) ? View.GONE : View.VISIBLE);
            texteCapteurPt.setText("Température bassin : " + (Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.TemperatureBassin) ? String.format("%.1f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.TemperatureBassin)) + " °C" : "Err"));

            sondePh.setVisibility(!Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) ? View.GONE : View.VISIBLE);
            ligneCapteurPh.setVisibility(!(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) && (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) || Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus))) ? View.GONE : View.VISIBLE);
            ligneCapteurPhMoins1.setVisibility(!(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins)) ? View.GONE : View.VISIBLE);
            ligneCapteurPhMoins2.setVisibility(!(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins)) ? View.GONE : View.VISIBLE);
            ligneCapteurPhPlus1.setVisibility(!(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus)) ? View.GONE : View.VISIBLE);
            ligneCapteurPhPlus2.setVisibility(!(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus)) ? View.GONE : View.VISIBLE);

            sondeOrp.setVisibility(!Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) ? View.GONE : View.VISIBLE);
            ligneCapteurOrp1.setVisibility(!(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp)) ? View.GONE : View.VISIBLE);
            ligneCapteurOrp2.setVisibility(!(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp)) ? View.GONE : View.VISIBLE);
            ligneCapteurOrp3.setVisibility(!(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp)) ? View.GONE : View.VISIBLE);
            ligneCapteurOrp4.setVisibility(!(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp)) ? View.GONE : View.VISIBLE);

            sondeAmpero.setVisibility(!Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ampero) ? View.GONE : View.VISIBLE);
            ligneCapteurAmpero.setVisibility(!(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ampero) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp)) ? View.GONE : View.VISIBLE);

            if (Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) || Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) || Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ampero)) {
                ligneEntreeTor.setVisibility(!(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR) ? View.GONE : View.VISIBLE);
                ligneEntreeLineaire1.setVisibility(!(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_LIN) ? View.GONE : View.VISIBLE);
                ligneEntreeLineaire2.setVisibility(!(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_LIN) ? View.GONE : View.VISIBLE);
                ligneEntreePorteSonde.setVisibility(View.VISIBLE);
                ligneSortiePorteSonde1.setVisibility(View.VISIBLE);
                ligneSortiePorteSonde2.setVisibility(View.VISIBLE);
                ligneSortieTor1.setVisibility(!(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR) ? View.GONE : View.VISIBLE);
                ligneSortieTor2.setVisibility(!(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR) ? View.GONE : View.VISIBLE);
                ligneSortieTor3.setVisibility(!(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR) ? View.GONE : View.VISIBLE);

                conteneurDonneesCapteurs.setVisibility(View.VISIBLE);
                texteTypeRegulation.setVisibility(View.VISIBLE);
                texteTypeRegulation.setText("Type de régulation : " + Donnees.instance().obtenirTypeRegulation());
                texteCapteurPh.setVisibility(!Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) ? View.GONE : View.VISIBLE);
                texteCapteurPh.setText("pH : " + (Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Ph) ? String.format("%.2f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Ph)) : "Err"));
                texteCapteurOrp.setVisibility(!Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) ? View.GONE : View.VISIBLE);
                texteCapteurOrp.setText("ORP : " + (Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Redox) ? String.format("%.0f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Redox)) + " mV" : "Err"));
                texteCapteurAmpero.setVisibility(!Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ampero) ? View.GONE : View.VISIBLE);
                texteCapteurAmpero.setText("Ampéro : " + (Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Ampero) ? String.format("%.2f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Ampero)) + " ppm" : "Err"));

                conteneurDonneesCapteurs.setY(sondePh.getY() + sondePh.getHeight() / 2 - conteneurDonneesCapteurs.getHeight() / 2);
                if (Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph)) {
                    conteneurDonneesCapteurs.setX(sondePh.getX() + sondePh.getWidth() / 2);
                } else if (Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox)) {
                    conteneurDonneesCapteurs.setX(sondeOrp.getX() + sondeOrp.getWidth() / 2);
                } else {
                    conteneurDonneesCapteurs.setX(sondeAmpero.getX() + sondeAmpero.getWidth() / 2);
                }
            } else {
                ligneEntreeTor.setVisibility(View.GONE);
                ligneEntreeLineaire1.setVisibility(View.GONE);
                ligneEntreeLineaire2.setVisibility(View.GONE);
                ligneEntreePorteSonde.setVisibility(View.GONE);
                ligneSortiePorteSonde1.setVisibility(View.GONE);
                ligneSortiePorteSonde2.setVisibility(View.GONE);
                ligneSortieTor1.setVisibility(View.GONE);
                ligneSortieTor2.setVisibility(View.GONE);
                ligneSortieTor3.setVisibility(View.GONE);

                conteneurDonneesCapteurs.setVisibility(View.GONE);
            }

            // Couleur capteurs
            texteValeurTempInterne.setTextColor(!Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.TemperatureInterne) ? Color.RED : Color.BLACK);
            texteValeurHumiditeInterne.setTextColor(!Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.HumiditeInterne) ? Color.RED : Color.BLACK);
            texteValeurPressionAtmInterne.setTextColor(!Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.PressionAtmospheriqueInterne) ? Color.RED : Color.BLACK);

            texteValeurTempExterne.setTextColor(!Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.TemperatureExterne) ? Color.RED : Color.BLACK);
            texteValeurHumiditeExterne.setTextColor(!Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.HumiditeExterne) ? Color.RED : Color.BLACK);
            texteValeurPressionAtmExterne.setTextColor(!Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.PressionAtmospheriqueExterne) ? Color.RED : Color.BLACK);

            texteCapteurPression.setTextColor(Donnees.instance().obtenirEtatLectureCapteurs() ? !Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Pression) || (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Pression) < Donnees.instance().obtenirSeuilBasPression()) || (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Pression) > Donnees.instance().obtenirSeuilHautPression()) ? Color.RED : Color.GREEN : Color.BLACK);

            texteCapteurPh.setTextColor(Donnees.instance().obtenirEtatLectureCapteurs() ? Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.PhPlus) || Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.PhMoins) ? Color.rgb(255, 127, 0) : !Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Ph) || (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) && (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Ph) < (Donnees.instance().obtenirPointConsignePh() - Donnees.instance().obtenirHysteresisPhPlus()))) || (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) && (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Ph) > (Donnees.instance().obtenirPointConsignePh() + Donnees.instance().obtenirHysteresisPhMoins()))) ? Color.RED : Color.GREEN : Color.BLACK);

            texteCapteurAmpero.setTextColor(Donnees.instance().obtenirEtatLectureCapteurs() ? Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.Orp) ? Color.rgb(255, 127, 0) : !Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Ampero) || (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) && (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Ampero) < (Donnees.instance().obtenirPointConsigneAmpero() - Donnees.instance().obtenirHysteresisAmpero()))) ? Color.RED : Color.GREEN : Color.BLACK);

            texteCapteurOrp.setTextColor(Donnees.instance().obtenirEtatLectureCapteurs() ? Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.Orp) ? Color.rgb(255, 127, 0) : !Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Redox) || (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) && (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Redox) < (Donnees.instance().obtenirPointConsigneOrp() - Donnees.instance().obtenirHysteresisOrp()))) ? Color.RED : Color.GREEN : Color.BLACK);

            texteCapteurPt.setTextColor(Donnees.instance().obtenirEtatLectureCapteurs() ? (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Chauffage) == Donnees.MARCHE) || (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Chauffage) == Donnees.AUTO_MARCHE) ? Color.rgb(255, 127, 0) : !Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.TemperatureBassin) || (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) && (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.TemperatureBassin) < Donnees.instance().obtenirTemperatureConsigne())) ? Color.RED : Color.GREEN : Color.BLACK);

            // Tuyauterie
            tuyauSkimmer1.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_horizontal_sale : R.drawable.tuyau_horizontal_inactif);
            tuyauSkimmer2.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_3_6_sale : R.drawable.tuyau_3_6_inactif);
            tuyauSkimmer3.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_vertical_sale : R.drawable.tuyau_vertical_inactif);
            tuyauSkimmer4.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_3_12_sale : R.drawable.tuyau_3_12_inactif);
            tuyauSkimmer5.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_horizontal_sale : R.drawable.tuyau_horizontal_inactif);
            tuyauBondeFond1.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_horizontal_sale : R.drawable.tuyau_horizontal_inactif);
            tuyauBondeFond2.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_3_6_sale : R.drawable.tuyau_3_6_inactif);
            tuyauBondeFond3.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_vertical_sale : R.drawable.tuyau_vertical_inactif);
            tuyauBondeFond4.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_3_12_sale : R.drawable.tuyau_3_12_inactif);
            tuyauBondeFond5.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_horizontal_sale : R.drawable.tuyau_horizontal_inactif);
            tuyauEntreePompeFiltration1.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_6_9_sale : R.drawable.tuyau_6_9_inactif);
            tuyauEntreePompeFiltration2.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_9_12_sale : R.drawable.tuyau_9_12_inactif);
            tuyauEntreePompeFiltration3.setImageResource((pompeFiltrationActive && ozoneActive) ? R.drawable.tuyau_horizontal_ozone : (pompeFiltrationActive ? R.drawable.tuyau_horizontal_sale : R.drawable.tuyau_horizontal_inactif));
            tuyauEntreeCroixPompeFiltration.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_3_6_9_12_sale : R.drawable.tuyau_3_6_9_12_inactif);
            tuyauSortiePompeFiltration1.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_vertical_sale : R.drawable.tuyau_vertical_inactif);
            tuyauSortiePompeFiltration2.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_3_6_sale : R.drawable.tuyau_3_6_inactif);
            tuyauSortiePompeFiltration3.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_horizontal_sale : R.drawable.tuyau_horizontal_inactif);
            tuyauSortieFiltre1.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_horizontal_actif : R.drawable.tuyau_horizontal_inactif);
            tuyauSortieFiltre2.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_vertical_actif : R.drawable.tuyau_vertical_inactif);
            tuyauSortieTFiltre.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_3_6_9_actif : R.drawable.tuyau_3_6_9_inactif);
            tuyauSortieFiltreSurpresseur1.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_3_9_12_actif : R.drawable.tuyau_3_9_12_inactif);
            tuyauEntreeSurpresseur1.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_6_9_actif : R.drawable.tuyau_6_9_inactif);
            tuyauEntreeSurpresseur2.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_horizontal_actif : R.drawable.tuyau_horizontal_inactif);
            tuyauSortieSurpresseur.setImageResource((pompeFiltrationActive && surpresseurActive) ? R.drawable.tuyau_vertical_actif : R.drawable.tuyau_vertical_inactif);
            if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur)) {
                tuyauPriseBalais1.setImageResource((pompeFiltrationActive && surpresseurActive) ? R.drawable.tuyau_vertical_actif : R.drawable.tuyau_vertical_inactif);
                tuyauPriseBalais3.setImageResource((pompeFiltrationActive && surpresseurActive) ? R.drawable.tuyau_horizontal_actif : R.drawable.tuyau_horizontal_inactif);
                tuyauPriseBalais4.setImageResource((pompeFiltrationActive && surpresseurActive) ? R.drawable.tuyau_3_6_actif : R.drawable.tuyau_3_6_inactif);
                tuyauPriseBalais5.setImageResource((pompeFiltrationActive && surpresseurActive) ? R.drawable.tuyau_vertical_actif : R.drawable.tuyau_vertical_inactif);
                tuyauPriseBalais6.setImageResource((pompeFiltrationActive && surpresseurActive) ? R.drawable.tuyau_3_12_actif : R.drawable.tuyau_3_12_inactif);
                tuyauTPriseBalais.setImageResource((pompeFiltrationActive && surpresseurActive) ? R.drawable.tuyau_6_9_12_actif : R.drawable.tuyau_6_9_12_inactif);
            } else {
                tuyauPriseBalais1.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_vertical_sale : R.drawable.tuyau_vertical_inactif);
                tuyauPriseBalais2.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_9_12_sale : R.drawable.tuyau_9_12_inactif);
                tuyauPriseBalais3.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_horizontal_sale : R.drawable.tuyau_horizontal_inactif);
                tuyauPriseBalais4.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_3_6_sale : R.drawable.tuyau_3_6_inactif);
                tuyauPriseBalais5.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_vertical_sale : R.drawable.tuyau_vertical_inactif);
                tuyauPriseBalais6.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_3_12_sale : R.drawable.tuyau_3_12_inactif);
            }
            tuyauEntreeChauffage1.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_vertical_actif : R.drawable.tuyau_vertical_inactif);
            tuyauEntreeChauffage2.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_3_12_actif : R.drawable.tuyau_3_12_inactif);
            tuyauEntreeChauffage3.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_horizontal_actif : R.drawable.tuyau_horizontal_inactif);
            tuyauEntreePanneauSolaire1.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_9_12_actif : R.drawable.tuyau_9_12_inactif);
            tuyauEntreePanneauSolaire2.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_vertical_actif : R.drawable.tuyau_vertical_inactif);
            tuyauEntreePanneauSolaire3.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_3_6_actif : R.drawable.tuyau_3_6_inactif);
            tuyauEntreePanneauSolaire4.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_horizontal_actif : R.drawable.tuyau_horizontal_inactif);
            tuyauSortieChauffage1.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_horizontal_actif_chaud : R.drawable.tuyau_horizontal_actif) : R.drawable.tuyau_horizontal_inactif);
            tuyauSortieChauffage2.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_9_12_actif_chaud : R.drawable.tuyau_9_12_actif) : R.drawable.tuyau_9_12_inactif);
            tuyauSortieChauffage3.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_vertical_actif_chaud : R.drawable.tuyau_vertical_actif) : R.drawable.tuyau_vertical_inactif);
            tuyauSortiePanneauSolaire1.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_9_12_actif_chaud : R.drawable.tuyau_9_12_actif) : R.drawable.tuyau_9_12_inactif);
            tuyauSortiePanneauSolaire2.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_3_6_actif_chaud : R.drawable.tuyau_3_6_actif) : R.drawable.tuyau_3_6_inactif);
            tuyauSortiePanneauSolaire3.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_horizontal_actif_chaud : R.drawable.tuyau_horizontal_actif) : R.drawable.tuyau_horizontal_inactif);
            tuyauSortieFiltreChauffage1.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_horizontal_actif_chaud : R.drawable.tuyau_horizontal_actif) : R.drawable.tuyau_horizontal_inactif);
            tuyauSortieFiltreChauffage2.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_9_12_actif_chaud : R.drawable.tuyau_9_12_actif) : R.drawable.tuyau_9_12_inactif);
            tuyauSortieFiltreChauffage3.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_vertical_actif_chaud : R.drawable.tuyau_vertical_actif) : R.drawable.tuyau_vertical_inactif);
            tuyauSortieFiltreChauffage4.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_vertical_actif_chaud : R.drawable.tuyau_vertical_actif) : R.drawable.tuyau_vertical_inactif);
            tuyauSortieTFiltreChauffage.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_6_9_12_actif_chaud : R.drawable.tuyau_6_9_12_actif) : R.drawable.tuyau_6_9_12_inactif);
            tuyauEntreeUv1.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_3_6_actif_chaud : R.drawable.tuyau_3_6_actif) : R.drawable.tuyau_3_6_inactif);
            tuyauEntreeUv2.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_horizontal_actif_chaud : R.drawable.tuyau_horizontal_actif) : R.drawable.tuyau_horizontal_inactif);
            tuyauSortieUv1.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_horizontal_actif_chaud : R.drawable.tuyau_horizontal_actif) : R.drawable.tuyau_horizontal_inactif);
            tuyauSortieUv2.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_3_12_actif_chaud : R.drawable.tuyau_3_12_actif) : R.drawable.tuyau_3_12_inactif);
            tuyauPassageUv.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_vertical_actif_chaud : R.drawable.tuyau_vertical_actif) : R.drawable.tuyau_vertical_inactif);
            tuyauEntreeElectrolyseur.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_vertical_actif_chaud : R.drawable.tuyau_vertical_actif) : R.drawable.tuyau_vertical_inactif);
            tuyauSortieElectrolyseur.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_6_9_actif_chaud : R.drawable.tuyau_6_9_actif) : R.drawable.tuyau_6_9_inactif);
            tuyauInjections.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_horizontal_actif_chaud : R.drawable.tuyau_horizontal_actif) : R.drawable.tuyau_horizontal_inactif);
            tuyauRefoulements1.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_3_6_actif_chaud : R.drawable.tuyau_3_6_actif) : R.drawable.tuyau_3_6_inactif);
            tuyauRefoulements2.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_vertical_actif_chaud : R.drawable.tuyau_vertical_actif) : R.drawable.tuyau_vertical_inactif);
            tuyauRefoulements3.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_9_12_actif_chaud : R.drawable.tuyau_9_12_actif) : R.drawable.tuyau_9_12_inactif);
            ligneEntreeTor.setBackgroundColor(couleurLigneInjection);
            ligneEntreeLineaire1.setBackgroundColor(couleurLigneInjection);
            ligneEntreeLineaire2.setBackgroundColor(couleurLigneInjection);
            ligneEntreePorteSonde.setBackgroundColor(couleurLigneInjection);
            ligneSortiePorteSonde1.setBackgroundColor(couleurLigneInjection);
            ligneSortiePorteSonde2.setBackgroundColor(couleurLigneInjection);
            ligneSortieTor1.setBackgroundColor(couleurLigneInjection);
            ligneSortieTor2.setBackgroundColor(couleurLigneInjection);
            ligneSortieTor3.setBackgroundColor(couleurLigneInjection);
            sondeOzone.setImageResource(pompeFiltrationActive ? (ozoneActive ? R.drawable.sonde_ozone : R.drawable.sonde_sale) : R.drawable.sonde_inactif);
            sondePt.setImageResource(pompeFiltrationActive ? R.drawable.sonde_sale : R.drawable.sonde_inactif);
            sondePh.setImageResource(pompeFiltrationActive ? (((Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR) && chauffageActive) ? R.drawable.sonde_actif_chaud : R.drawable.sonde_actif) : R.drawable.sonde_inactif);
            sondeOrp.setImageResource(pompeFiltrationActive ? (((Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR) && chauffageActive) ? R.drawable.sonde_actif_chaud : R.drawable.sonde_actif) : R.drawable.sonde_inactif);
            sondeAmpero.setImageResource(pompeFiltrationActive ? (((Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR) && chauffageActive) ? R.drawable.sonde_actif_chaud : R.drawable.sonde_actif) : R.drawable.sonde_inactif);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eclairage_1: case R.id.eclairage_2:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_eclairage_layout));
                break;
            case R.id.pompe_filtration:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_pompe_filtration_layout));
                break;
            case R.id.filtre:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_filtre_layout));
                break;
            case R.id.ozonateur:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_ozone_layout));
                break;
            case R.id.surpresseur:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_surpresseur_layout));
                break;
            case R.id.pac:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_chauffage_layout));
                break;
            case R.id.lampes_uv:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_lampes_uv_layout));
                break;
            case R.id.algicide:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_algicide_layout));
                break;
            case R.id.regulateur_orp:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_regulateur_orp_layout));
                break;
            case R.id.regulateur_ph_moins:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_regulateur_ph_moins_layout));
                break;
            case R.id.regulateur_ph_plus:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_regulateur_ph_plus_layout));
                break;
            default:
                break;
        }
    }

    private void definirImageBouton(ImageButton imageButton, Donnees.Equipement equipement) {
        int mode = Donnees.instance().obtenirEtatEquipement(equipement);
        int imageActif;
        int imageInactif;

        switch (equipement) {
            case PompeFiltration:
                imageActif = R.drawable.pompe_filtration_actif;
                imageInactif = R.drawable.pompe_filtration_inactif;
                break;
            case Surpresseur:
                imageActif = R.drawable.surpresseur_actif;
                imageInactif = R.drawable.surpresseur_inactif;
                break;
            case Chauffage:
                if (Donnees.instance().obtenirTypeChauffage() == Donnees.RECHAUFFEUR_CHAUDIERE) {
                    imageActif = R.drawable.rechauffeur_chaudiere_actif;
                    imageInactif = R.drawable.rechauffeur_chaudiere_inactif;
                } else if (Donnees.instance().obtenirTypeChauffage() == Donnees.RECHAUFFEUR_ELECTRIQUE) {
                    imageActif = R.drawable.rechauffeur_electrique_actif;
                    imageInactif = R.drawable.rechauffeur_electrique_inactif;
                } else if (Donnees.instance().obtenirTypeChauffage() == Donnees.PANNEAU_SOLAIRE) {
                    imageActif = R.drawable.panneau_solaire_actif;
                    imageInactif = R.drawable.panneau_solaire_inactif;
                } else if (Donnees.instance().obtenirTypeChauffage() == Donnees.PANNEAU_ET_POMPE) {
                    imageActif = R.drawable.panneau_pac_actif;
                    imageInactif = R.drawable.panneau_pac_inactif;
                } else if (Donnees.instance().obtenirTypeChauffage() == Donnees.PANNEAU_ET_CHAUDIERE) {
                    imageActif = R.drawable.panneau_chaudiere_actif;
                    imageInactif = R.drawable.panneau_chaudiere_inactif;
                } else if (Donnees.instance().obtenirTypeChauffage() == Donnees.PANNEAU_ET_ELECTRIQUE) {
                    imageActif = R.drawable.panneau_electrique_actif;
                    imageInactif = R.drawable.panneau_electrique_inactif;
                } else {
                    imageActif = R.drawable.pac_actif;
                    imageInactif = R.drawable.pac_inactif;
                }
                break;
            case LampesUV:
                imageActif = R.drawable.lampes_uv_actif;
                imageInactif = R.drawable.lampes_uv_inactif;
                break;
            case Ozone:
                imageActif = R.drawable.ozone_actif;
                imageInactif = R.drawable.ozone_inactif;
                break;
            case Algicide: case Orp: case PhMoins: case PhPlus:
                imageActif = R.drawable.pomperegulation_actif;
                imageInactif = R.drawable.pomperegulation_inactif;
                break;
            case Eclairage:
                imageActif = R.drawable.lumiere_actif;
                imageInactif = R.drawable.lumiere_inactif;
                break;
            default:
                imageActif = -1;
                imageInactif = -1;
                break;
        }

        if ((imageActif > -1) && (imageInactif > -1)) {
            if ((mode == Donnees.AUTO_MARCHE) || (mode == Donnees.MARCHE)) {
                imageButton.setImageResource(imageActif);

                if (equipement == Donnees.Equipement.Chauffage) {
                    if (Donnees.instance().obtenirTypeChauffage() == Donnees.PAC) {
                        rotateHelicePac.startAnimation();
                    } else {
                        rotateHelicePac.stopAnimation();
                    }
                }
            } else {
                imageButton.setImageResource(imageInactif);

                if (equipement == Donnees.Equipement.Chauffage) {
                    rotateHelicePac.stopAnimation();
                }
            }
        }
    }

    private void definirTexteMode(TextView textView, Donnees.Equipement equipement) {
        int mode = Donnees.instance().obtenirEtatEquipement(equipement);
        int couleurTexte;
        int couleurContour;

        if (mode >= Donnees.AUTO) {
            textView.setText("AUTO");
        } else {
            textView.setText("MANUEL");
        }

        if (mode == Donnees.AUTO_MARCHE) {
            couleurContour = R.drawable.back_auto_marche;
            couleurTexte = getResources().getColor(R.color.autoMarche);
        } else if (mode == Donnees.AUTO_ARRET) {
            couleurContour = R.drawable.back_auto_arret;
            couleurTexte = getResources().getColor(R.color.autoArret);
        } else {
            couleurContour = R.drawable.back_manuel;
            couleurTexte = getResources().getColor(R.color.manuel);
        }

        textView.setTextColor(couleurTexte);
        textView.setBackgroundResource(couleurContour);
    }

    private void definirInjection(Donnees.Equipement equipement) {
        AnimationInjection animationInjection = null;

        if (equipement == Donnees.Equipement.Ozone) {
            animationInjection = animationInjectionOzone;
        } else if (equipement == Donnees.Equipement.PhPlus) {
            animationInjection = animationInjectionRegPhPlus;
        } else if (equipement == Donnees.Equipement.PhMoins) {
            animationInjection = animationInjectionRegPhMoins;
        } else if (equipement == Donnees.Equipement.Orp) {
            animationInjection = animationInjectionRegORP;
        } else if (equipement == Donnees.Equipement.Algicide) {
            animationInjection = animationInjectionAlgicide;
        }

        if ((animationInjection != null)
                && ((Donnees.instance().obtenirEtatEquipement(equipement) == Donnees.AUTO_MARCHE)
                || (Donnees.instance().obtenirEtatEquipement(equipement) == Donnees.MARCHE))) {
            animationInjection.ajouterInjection();
        } else {
            animationInjection.arreterTimer();
        }
    }

    private void definirRefoulement() {
        if ((Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PompeFiltration) == Donnees.AUTO_MARCHE)
                || (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PompeFiltration) == Donnees.MARCHE)) {
            animationRefoulement.ajouterInjection();
        } else {
            animationRefoulement.arreterTimer();
        }
    }

    private void definirConsoRegulation(TextView textView, ProgressBar progressBar, Donnees.Equipement equipement) {
        double conso = Donnees.instance().obtenirVolumeRestant(equipement);
        double consoMax = Donnees.instance().obtenirVolume(equipement);
        int couleurTexte;

        if (conso < (consoMax * Global.HYSTERESIS_BIDON_VIDE / 100.0)) {
            couleurTexte = getResources().getColor(android.R.color.holo_red_light);
        } else if (conso < (consoMax * Global.HYSTERESIS_BIDON_PRESQUE_VIDE / 100.0)) {
            couleurTexte = getResources().getColor(android.R.color.holo_orange_light);
        } else {
            couleurTexte = Color.GREEN;
        }

        textView.setTextColor(couleurTexte);
        textView.setText(conso + " L");
        progressBar.setProgress((int)((conso * 100.0) / consoMax));
    }

    public void clignotement() {
        if ((view != null) && isAdded()) {
            if (!Donnees.instance().obtenirEtatLectureCapteurs()) {
                if (texteCapteurPh.getCurrentTextColor() == Color.BLACK) {
                    texteCapteurPh.setTextColor(Color.GRAY);
                    texteCapteurOrp.setTextColor(Color.GRAY);
                    texteCapteurAmpero.setTextColor(Color.GRAY);
                    texteCapteurPt.setTextColor(Color.GRAY);
                } else {
                    texteCapteurPh.setTextColor(Color.BLACK);
                    texteCapteurOrp.setTextColor(Color.BLACK);
                    texteCapteurAmpero.setTextColor(Color.BLACK);
                    texteCapteurPt.setTextColor(Color.BLACK);
                }
            }
        }
    }

    private void scale(AbsoluteLayout view, float width, float height, float offset) {
        float wScaleFactor = (float) (width / 800.0);
        float hScaleFactor = (float) ((height - offset) / 1280.0);

        view.setRotation(90);
        view.setScaleX(hScaleFactor);
        view.setScaleY(wScaleFactor);
        view.setTranslationX(Convertisseur.convertDpToPixel(-(1280 - 800 * wScaleFactor) / 2));
        view.setTranslationY(Convertisseur.convertDpToPixel(-(800 - 1280 * hScaleFactor) / 2));
    }
}
