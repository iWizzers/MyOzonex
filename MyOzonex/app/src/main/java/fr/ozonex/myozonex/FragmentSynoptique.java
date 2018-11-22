package fr.ozonex.myozonex;

import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static android.support.v4.view.MotionEventCompat.getPointerCount;

public class FragmentSynoptique extends Fragment implements View.OnClickListener {
    View view = null;

    AnimationInjection animationInjectionOzone;
    AnimationInjection animationInjectionRegPhPlus;
    AnimationInjection animationInjectionRegPhMoins;
    AnimationInjection animationInjectionRegORP;
    AnimationInjection animationInjectionAlgicide;
    AnimationRefoulement animationRefoulement;

    // Tout orientations
    ProgressBar contenuBidonAlgicide;
    ProgressBar contenuBidonOrp;
    ProgressBar contenuBidonPhMoins;
    ProgressBar contenuBidonPhPlus;
    View ligneCapteurPt1;
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
    ImageView tuyauEntreeSurpresseur2;
    ImageView tuyauEntreeSurpresseur3;
    ImageView tuyauEntreeChauffage1;
    ImageView tuyauEntreeChauffage2;
    ImageView tuyauEntreeChauffage3;
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
    ImageButton boutonPompeFiltration;
    TextView texteModePompeFiltration;
    ImageButton boutonOzonateur;
    TextView texteModeOzonateur;
    ImageButton boutonSurpresseur;
    TextView texteModeSurpresseur;
    ImageButton boutonFiltre;
    TextView texteCapteurPression;
    ImageView helicePac;
    Rotate rotateHelicePac;
    ImageButton boutonPac;
    TextView texteModePac;
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
    Button boutonAccueil;
    Button boutonMenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.synoptique_layout, container, false);


        contenuBidonAlgicide = (ProgressBar) view.findViewById(R.id.contenu_bidon_algicide);
        contenuBidonOrp = (ProgressBar) view.findViewById(R.id.contenu_bidon_orp);
        contenuBidonPhMoins = (ProgressBar) view.findViewById(R.id.contenu_bidon_ph_moins);
        contenuBidonPhPlus = (ProgressBar) view.findViewById(R.id.contenu_bidon_ph_plus);
        ligneCapteurPt1 = view.findViewById(R.id.ligne_capteur_pt_1);
        texteCapteurPt = (TextView) view.findViewById(R.id.texte_capteur_pt);
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
        conteneurDonneesCapteurs = (LinearLayout) view.findViewById(R.id.conteneur_donnees_capteurs);
        texteTypeRegulation = (TextView) view.findViewById(R.id.texte_type_regulation);
        texteCapteurPh = (TextView) view.findViewById(R.id.texte_capteur_ph);
        texteCapteurOrp = (TextView) view.findViewById(R.id.texte_capteur_orp);
        texteCapteurAmpero = (TextView) view.findViewById(R.id.texte_capteur_ampero);
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
        tuyauSkimmer1 = (ImageView) view.findViewById(R.id.tuyau_skimmer_1);
        tuyauSkimmer2 = (ImageView) view.findViewById(R.id.tuyau_skimmer_2);
        tuyauSkimmer3 = (ImageView) view.findViewById(R.id.tuyau_skimmer_3);
        tuyauSkimmer4 = (ImageView) view.findViewById(R.id.tuyau_skimmer_4);
        tuyauSkimmer5 = (ImageView) view.findViewById(R.id.tuyau_skimmer_5);
        tuyauBondeFond1 = (ImageView) view.findViewById(R.id.tuyau_bonde_fond_1);
        tuyauBondeFond2 = (ImageView) view.findViewById(R.id.tuyau_bonde_fond_2);
        tuyauBondeFond3 = (ImageView) view.findViewById(R.id.tuyau_bonde_fond_3);
        tuyauBondeFond4 = (ImageView) view.findViewById(R.id.tuyau_bonde_fond_4);
        tuyauBondeFond5 = (ImageView) view.findViewById(R.id.tuyau_bonde_fond_5);
        tuyauPriseBalais1 = (ImageView) view.findViewById(R.id.tuyau_prise_balais_1);
        tuyauPriseBalais2 = (ImageView) view.findViewById(R.id.tuyau_prise_balais_2);
        tuyauPriseBalais3 = (ImageView) view.findViewById(R.id.tuyau_prise_balais_3);
        tuyauPriseBalais4 = (ImageView) view.findViewById(R.id.tuyau_prise_balais_4);
        tuyauPriseBalais5 = (ImageView) view.findViewById(R.id.tuyau_prise_balais_5);
        tuyauPriseBalais6 = (ImageView) view.findViewById(R.id.tuyau_prise_balais_6);
        tuyauSortieSurpresseur = (ImageView) view.findViewById(R.id.tuyau_sortie_surpresseur);
        tuyauTPriseBalais = (ImageView) view.findViewById(R.id.tuyau_T_prise_balais);
        tuyauEntreePompeFiltration1 = (ImageView) view.findViewById(R.id.tuyau_entree_pompe_filtration_1);
        tuyauEntreePompeFiltration2 = (ImageView) view.findViewById(R.id.tuyau_entree_pompe_filtration_2);
        tuyauEntreeCroixPompeFiltration = (ImageView) view.findViewById(R.id.tuyau_entree_croix_pompe_filtration);
        tuyauEntreePompeFiltration3 = (ImageView) view.findViewById(R.id.tuyau_entree_pompe_filtration_3);
        tuyauSortiePompeFiltration1 = (ImageView) view.findViewById(R.id.tuyau_sortie_pompe_filtration_1);
        tuyauSortiePompeFiltration2 = (ImageView) view.findViewById(R.id.tuyau_sortie_pompe_filtration_2);
        tuyauSortiePompeFiltration3 = (ImageView) view.findViewById(R.id.tuyau_sortie_pompe_filtration_3);
        tuyauSortieFiltre1 = (ImageView) view.findViewById(R.id.tuyau_sortie_filtre_1);
        tuyauSortieFiltre2 = (ImageView) view.findViewById(R.id.tuyau_sortie_filtre_2);
        tuyauSortieTFiltre = (ImageView) view.findViewById(R.id.tuyau_sortie_t_filtre);
        tuyauSortieFiltreSurpresseur1 = (ImageView) view.findViewById(R.id.tuyau_sortie_filtre_surpresseur_1);
        tuyauSortieFiltreChauffage1 = (ImageView) view.findViewById(R.id.tuyau_sortie_filtre_chauffage_1);
        tuyauSortieFiltreChauffage2 = (ImageView) view.findViewById(R.id.tuyau_sortie_filtre_chauffage_2);
        tuyauSortieFiltreChauffage3 = (ImageView) view.findViewById(R.id.tuyau_sortie_filtre_chauffage_3);
        tuyauSortieFiltreChauffage4 = (ImageView) view.findViewById(R.id.tuyau_sortie_filtre_chauffage_4);
        tuyauSortieTFiltreChauffage = (ImageView) view.findViewById(R.id.tuyau_sortie_t_filtre_chauffage);
        tuyauEntreeSurpresseur2 = (ImageView) view.findViewById(R.id.tuyau_entree_surpresseur_2);
        tuyauEntreeSurpresseur3 = (ImageView) view.findViewById(R.id.tuyau_entree_surpresseur_3);
        tuyauEntreeChauffage1 = (ImageView) view.findViewById(R.id.tuyau_entree_chauffage_1);
        tuyauEntreeChauffage2 = (ImageView) view.findViewById(R.id.tuyau_entree_chauffage_2);
        tuyauEntreeChauffage3 = (ImageView) view.findViewById(R.id.tuyau_entree_chauffage_3);
        tuyauSortieChauffage1 = (ImageView) view.findViewById(R.id.tuyau_sortie_chauffage_1);
        tuyauSortieChauffage2 = (ImageView) view.findViewById(R.id.tuyau_sortie_chauffage_2);
        tuyauSortieChauffage3 = (ImageView) view.findViewById(R.id.tuyau_sortie_chauffage_3);
        tuyauPassageUv = (ImageView) view.findViewById(R.id.tuyau_passage_uv);
        tuyauEntreeUv1 = (ImageView) view.findViewById(R.id.tuyau_entree_uv_1);
        tuyauEntreeUv2 = (ImageView) view.findViewById(R.id.tuyau_entree_uv_2);
        tuyauSortieUv1 = (ImageView) view.findViewById(R.id.tuyau_sortie_uv_1);
        tuyauSortieUv2 = (ImageView) view.findViewById(R.id.tuyau_sortie_uv_2);
        tuyauEntreeElectrolyseur = (ImageView) view.findViewById(R.id.tuyau_entree_electrolyseur);
        tuyauSortieElectrolyseur = (ImageView) view.findViewById(R.id.tuyau_sortie_electrolyseur);
        tuyauInjections = (ImageView) view.findViewById(R.id.tuyau_injections);
        tuyauRefoulements1 = (ImageView) view.findViewById(R.id.tuyau_refoulements_1);
        tuyauRefoulements2 = (ImageView) view.findViewById(R.id.tuyau_refoulements_2);
        tuyauRefoulements3 = (ImageView) view.findViewById(R.id.tuyau_refoulements_3);
        vannePriseBalais = (ImageView) view.findViewById(R.id.vanne_prise_balais);
        vanneSortieBondeFond = (ImageView) view.findViewById(R.id.vanne_sortie_bonde_fond);
        vanneSortieSkimmer = (ImageView) view.findViewById(R.id.vanne_sortie_skimmer);
        vanneEntreeSurpresseur = (ImageView) view.findViewById(R.id.vanne_entree_surpresseur);
        vanneSortieSurpresseur = (ImageView) view.findViewById(R.id.vanne_sortie_surpresseur);
        vanneSortieFiltre = (ImageView) view.findViewById(R.id.vanne_sortie_filtre);
        vanneEntreeChauffage = (ImageView) view.findViewById(R.id.vanne_entree_chauffage);
        vanneSortieChauffage = (ImageView) view.findViewById(R.id.vanne_sortie_chauffage);
        sondeOzone = (ImageView) view.findViewById(R.id.sonde_ozone);
        sondePt = (ImageView) view.findViewById(R.id.sonde_pt);
        sondePh = (ImageView) view.findViewById(R.id.sonde_ph);
        sondeOrp = (ImageView) view.findViewById(R.id.sonde_orp);
        sondeAmpero = (ImageView) view.findViewById(R.id.sonde_ampero);
        layoutCapteurInterne = view.findViewById(R.id.layout_capteur_interne);
        texteValeurTempInterne = (TextView) view.findViewById(R.id.texte_valeur_temperature_interne);
        texteValeurHumiditeInterne = (TextView) view.findViewById(R.id.texte_valeur_humidite_interne);
        texteValeurPressionAtmInterne = (TextView) view.findViewById(R.id.texte_valeur_pression_atm_interne);
        layoutCapteurExterne = view.findViewById(R.id.layout_capteur_externe);
        texteValeurTempExterne = (TextView) view.findViewById(R.id.texte_valeur_temperature_externe);
        texteValeurHumiditeExterne = (TextView) view.findViewById(R.id.texte_valeur_humidite_externe);
        texteValeurPressionAtmExterne = (TextView) view.findViewById(R.id.texte_valeur_pression_atm_externe);
        boutonPompeFiltration = (ImageButton) view.findViewById(R.id.pompe_filtration);
        texteModePompeFiltration = (TextView) view.findViewById(R.id.texte_mode_pompe_filtration);
        boutonOzonateur = (ImageButton) view.findViewById(R.id.ozonateur);
        texteModeOzonateur = (TextView) view.findViewById(R.id.texte_mode_ozonateur);
        boutonSurpresseur = (ImageButton) view.findViewById(R.id.surpresseur);
        texteModeSurpresseur = (TextView) view.findViewById(R.id.texte_mode_surpresseur);
        boutonFiltre = (ImageButton) view.findViewById(R.id.filtre);
        texteCapteurPression = (TextView) view.findViewById(R.id.texte_capteur_pression);
        helicePac = (ImageView) view.findViewById(R.id.helice_pac);
        rotateHelicePac = new Rotate(helicePac, 120);
        boutonPac = (ImageButton) view.findViewById(R.id.pac);
        texteModePac = (TextView) view.findViewById(R.id.texte_mode_pac);
        boutonLampesUv = (ImageButton) view.findViewById(R.id.lampes_uv);
        texteModeLampesUV = (TextView) view.findViewById(R.id.texte_mode_lampes_uv);
        boutonAlgicide = (ImageButton) view.findViewById(R.id.algicide);
        bidonAlgicide = (ImageView) view.findViewById(R.id.bidon_algicide);
        texteModeAlgicide = (TextView) view.findViewById(R.id.texte_mode_algicide);
        texteAlgicide = (TextView) view.findViewById(R.id.texte_algicide);
        texteConsoAlgicide = (TextView) view.findViewById(R.id.texte_conso_algicide);
        boutonRegulateurOrp = (ImageButton) view.findViewById(R.id.regulateur_orp);
        bidonOrp = (ImageView) view.findViewById(R.id.bidon_orp);
        texteModeRegulateurOrp = (TextView) view.findViewById(R.id.texte_mode_regulateur_orp);
        texteRegulateurOrp = (TextView) view.findViewById(R.id.texte_regulateur_orp);
        texteConsoOrp = (TextView) view.findViewById(R.id.texte_conso_orp);
        boutonRegulateurPhMoins = (ImageButton) view.findViewById(R.id.regulateur_ph_moins);
        bidonPhMoins = (ImageView) view.findViewById(R.id.bidon_ph_moins);
        texteModeRegulateurPhMoins = (TextView) view.findViewById(R.id.texte_mode_regulateur_ph_moins);
        texteRegulateurPhMoins = (TextView) view.findViewById(R.id.texte_regulateur_ph_moins);
        texteConsoPhMoins = (TextView) view.findViewById(R.id.texte_conso_ph_moins);
        boutonRegulateurPhPlus = (ImageButton) view.findViewById(R.id.regulateur_ph_plus);
        bidonPhPlus = (ImageView) view.findViewById(R.id.bidon_ph_plus);
        texteModeRegulateurPhPlus = (TextView) view.findViewById(R.id.texte_mode_regulateur_ph_plus);
        texteRegulateurPhPlus = (TextView) view.findViewById(R.id.texte_regulateur_ph_plus);
        texteConsoPhPlus = (TextView) view.findViewById(R.id.texte_conso_ph_plus);
        boutonAccueil = (Button) view.findViewById(R.id.bouton_accueil);
        boutonMenu = (Button) view.findViewById(R.id.bouton_menu);

        boutonAccueil.setVisibility(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? View.VISIBLE : View.GONE);
        boutonMenu.setVisibility(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? View.VISIBLE : View.GONE);

        boutonPompeFiltration.setOnClickListener(this);
        boutonFiltre.setOnClickListener(this);
        boutonOzonateur.setOnClickListener(this);
        boutonSurpresseur.setOnClickListener(this);
        boutonPac.setOnClickListener(this);
        boutonLampesUv.setOnClickListener(this);
        boutonAlgicide.setOnClickListener(this);
        boutonRegulateurOrp.setOnClickListener(this);
        boutonRegulateurPhMoins.setOnClickListener(this);
        boutonRegulateurPhPlus.setOnClickListener(this);
        boutonAccueil.setOnClickListener(this);
        boutonMenu.setOnClickListener(this);

        animationInjectionOzone = new AnimationInjection((RelativeLayout) view.findViewById(R.id.layout_sortie_ozone), ligneSortieOzone, true, Donnees.Equipement.Ozone, R.color.ozone);
        animationInjectionRegPhPlus = new AnimationInjection((RelativeLayout) view.findViewById(R.id.layout_sortie_reg_ph_plus), ligneInjectionPhPlus, false, Donnees.Equipement.PhPlus, R.color.phPlus);
        animationInjectionRegPhMoins = new AnimationInjection((RelativeLayout) view.findViewById(R.id.layout_sortie_reg_ph_moins), ligneInjectionPhMoins, false, Donnees.Equipement.PhMoins, R.color.phMoins);
        animationInjectionRegORP = new AnimationInjection((RelativeLayout) view.findViewById(R.id.layout_sortie_reg_orp), ligneInjectionOrp, false, Donnees.Equipement.Orp, R.color.orp);
        animationInjectionAlgicide = new AnimationInjection((RelativeLayout) view.findViewById(R.id.layout_sortie_algicide), ligneInjectionAlgicide, false, Donnees.Equipement.Algicide, R.color.algicide);
        animationRefoulement = new AnimationRefoulement((RelativeLayout) view.findViewById(R.id.layout_contenu_refoulement));

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            new ScaleListener((HorizontalScrollView) view.findViewById(R.id.horizontal_scroll),
                    (ScrollView) view.findViewById(R.id.vertical_scroll),
                    (AbsoluteLayout) view.findViewById(R.id.layout));
        }


        update();


        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            boolean pompeFiltrationActive = (Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.PompeFiltration) == Donnees.AUTO_MARCHE) || (Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.PompeFiltration) == Donnees.MARCHE);
            boolean surpresseurActive = (Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Surpresseur) == Donnees.AUTO_MARCHE) || (Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Surpresseur) == Donnees.MARCHE);
            boolean chauffageActive = (Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Chauffage) == Donnees.AUTO_MARCHE) || (Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Chauffage) == Donnees.MARCHE);
            boolean ozoneActive = (Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Ozone) == Donnees.AUTO_MARCHE) || (Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Ozone) == Donnees.MARCHE);
            int couleurLigneInjection = pompeFiltrationActive ? ((Donnees.instance().obtenirTypeAsservissement().equals(Donnees.ASSERVISSEMENT_TOR) && chauffageActive) ? getResources().getColor(R.color.ligneInjectionActiveChaud) : getResources().getColor(R.color.ligneInjectionActive)) : getResources().getColor(R.color.ligneInjectionInactive);

            layoutCapteurInterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.CapteurInterne) ? View.VISIBLE : View.GONE);
            texteValeurTempInterne.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.TemperatureInterne) ? Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureInterne) + " °C" : "Err");
            texteValeurHumiditeInterne.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.HumiditeInterne) ? Donnees.instance().obtenirValeur(Donnees.Capteur.HumiditeInterne) + " %" : "Err");
            texteValeurPressionAtmInterne.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.PressionAtmospheriqueInterne) ? Donnees.instance().obtenirValeur(Donnees.Capteur.PressionAtmospheriqueInterne) + " hPa" : "Err");

            layoutCapteurExterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.CapteurExterne) ? View.VISIBLE : View.GONE);
            texteValeurTempExterne.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.TemperatureExterne) ? Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureExterne) + " °C" : "Err");
            texteValeurHumiditeExterne.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.HumiditeExterne) ? Donnees.instance().obtenirValeur(Donnees.Capteur.HumiditeExterne) + " %" : "Err");
            texteValeurPressionAtmExterne.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.PressionAtmospheriqueExterne) ? Donnees.instance().obtenirValeur(Donnees.Capteur.PressionAtmospheriqueExterne) + " hPa" : "Err");

            boutonPompeFiltration.setEnabled(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PompeFiltration));
            definirImageBouton(boutonPompeFiltration, Donnees.Equipement.PompeFiltration);
            definirTexteMode(texteModePompeFiltration, Donnees.Equipement.PompeFiltration);

            boutonFiltre.setEnabled(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Filtre));
            texteCapteurPression.setVisibility(Donnees.instance().presence(Donnees.Capteur.Pression) ? View.VISIBLE : View.GONE);
            texteCapteurPression.setText("Pression : " + (Donnees.instance().obtenirEtat(Donnees.Capteur.Pression) ? Donnees.instance().obtenirValeur(Donnees.Capteur.Pression) + " bar" : "Err"));

            afficherElementsEquipement(Donnees.Equipement.Surpresseur,
                    texteModeSurpresseur,
                    boutonSurpresseur,
                    tuyauSortieFiltreSurpresseur1,
                    vanneEntreeSurpresseur,
                    tuyauEntreeSurpresseur2,
                    tuyauEntreeSurpresseur3,
                    tuyauSortieSurpresseur,
                    vanneSortieSurpresseur,
                    tuyauTPriseBalais);
            tuyauPriseBalais2.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur) ? View.GONE : View.VISIBLE);
            vannePriseBalais.setImageResource(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur) ? R.drawable.vanne_horizontal_inactif : R.drawable.vanne_horizontal_actif);
            definirImageBouton(boutonSurpresseur, Donnees.Equipement.Surpresseur);
            definirTexteMode(texteModeSurpresseur, Donnees.Equipement.Surpresseur);

            afficherElementsEquipement(Donnees.Equipement.Chauffage,
                    texteModePac,
                    helicePac,
                    boutonPac,
                    tuyauSortieTFiltre,
                    tuyauSortieFiltre2,
                    vanneEntreeChauffage,
                    tuyauEntreeChauffage1,
                    tuyauEntreeChauffage2,
                    tuyauEntreeChauffage3,
                    tuyauSortieChauffage1,
                    tuyauSortieChauffage2,
                    tuyauSortieChauffage3,
                    vanneSortieChauffage,
                    tuyauSortieFiltreChauffage4,
                    tuyauSortieTFiltreChauffage);
            tuyauSortieFiltreChauffage2.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? View.GONE : View.VISIBLE);
            definirImageBouton(boutonPac, Donnees.Equipement.Chauffage);
            definirTexteMode(texteModePac, Donnees.Equipement.Chauffage);

            afficherElementsEquipement(Donnees.Equipement.Ozone,
                    texteModeOzonateur,
                    boutonOzonateur,
                    ligneSortieOzone,
                    sondeOzone);
            definirImageBouton(boutonOzonateur, Donnees.Equipement.Ozone);
            definirTexteMode(texteModeOzonateur, Donnees.Equipement.Ozone);
            definirInjection(Donnees.Equipement.Ozone);

            afficherElementsEquipement(Donnees.Equipement.LampesUV,
                    texteModeLampesUV,
                    boutonLampesUv,
                    tuyauEntreeUv1,
                    tuyauEntreeUv2,
                    tuyauSortieUv1,
                    tuyauSortieUv2);
            tuyauPassageUv.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.LampesUV) ? View.GONE : View.VISIBLE);
            definirImageBouton(boutonLampesUv, Donnees.Equipement.LampesUV);
            definirTexteMode(texteModeLampesUV, Donnees.Equipement.LampesUV);

            // ELECTROLYSEUR

            afficherElementsEquipement(Donnees.Equipement.Algicide,
                    texteModeAlgicide,
                    texteAlgicide,
                    boutonAlgicide,
                    bidonAlgicide,
                    texteConsoAlgicide,
                    contenuBidonAlgicide,
                    ligneInjectionAlgicide);
            definirImageBouton(boutonAlgicide, Donnees.Equipement.Algicide);
            definirTexteMode(texteModeAlgicide, Donnees.Equipement.Algicide);
            definirConsoRegulation(texteConsoAlgicide, contenuBidonAlgicide, Donnees.Equipement.Algicide);
            definirInjection(Donnees.Equipement.Algicide);

            afficherElementsEquipement(Donnees.Equipement.Orp,
                    texteModeRegulateurOrp,
                    texteRegulateurOrp,
                    boutonRegulateurOrp,
                    bidonOrp,
                    texteConsoOrp,
                    contenuBidonOrp,
                    ligneInjectionOrp);
            definirImageBouton(boutonRegulateurOrp, Donnees.Equipement.Orp);
            definirTexteMode(texteModeRegulateurOrp, Donnees.Equipement.Orp);
            definirConsoRegulation(texteConsoOrp, contenuBidonOrp, Donnees.Equipement.Orp);
            definirInjection(Donnees.Equipement.Orp);

            afficherElementsEquipement(Donnees.Equipement.PhMoins,
                    texteModeRegulateurPhMoins,
                    texteRegulateurPhMoins,
                    boutonRegulateurPhMoins,
                    bidonPhMoins,
                    texteConsoPhMoins,
                    contenuBidonPhMoins,
                    ligneInjectionPhMoins);
            definirImageBouton(boutonRegulateurPhMoins, Donnees.Equipement.PhMoins);
            definirTexteMode(texteModeRegulateurPhMoins, Donnees.Equipement.PhMoins);
            definirConsoRegulation(texteConsoPhMoins, contenuBidonPhMoins, Donnees.Equipement.PhMoins);
            definirInjection(Donnees.Equipement.PhMoins);

            afficherElementsEquipement(Donnees.Equipement.PhPlus,
                    texteModeRegulateurPhPlus,
                    texteRegulateurPhPlus,
                    boutonRegulateurPhPlus,
                    bidonPhPlus,
                    texteConsoPhPlus,
                    contenuBidonPhPlus,
                    ligneInjectionPhPlus);
            definirImageBouton(boutonRegulateurPhPlus, Donnees.Equipement.PhPlus);
            definirTexteMode(texteModeRegulateurPhPlus, Donnees.Equipement.PhPlus);
            definirConsoRegulation(texteConsoPhPlus, contenuBidonPhPlus, Donnees.Equipement.PhPlus);
            definirInjection(Donnees.Equipement.PhPlus);

            definirRefoulement();

            sondePt.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
            ligneCapteurPt1.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
            texteCapteurPt.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
            texteCapteurPt.setText("Température bassin : " + (Donnees.instance().obtenirEtat(Donnees.Capteur.TemperatureBassin) ? Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin) + " °C" : "Err"));

            sondePh.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ph) ? View.VISIBLE : View.GONE);
            ligneCapteurPh.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ph) && (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) || Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus)) ? View.VISIBLE : View.GONE);
            ligneCapteurPhMoins1.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ph) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? View.VISIBLE : View.GONE);
            ligneCapteurPhMoins2.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ph) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? View.VISIBLE : View.GONE);
            ligneCapteurPhPlus1.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ph) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) ? View.VISIBLE : View.GONE);
            ligneCapteurPhPlus2.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ph) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) ? View.VISIBLE : View.GONE);

            sondeOrp.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) ? View.VISIBLE : View.GONE);
            ligneCapteurOrp1.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);
            ligneCapteurOrp2.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);
            ligneCapteurOrp3.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);
            ligneCapteurOrp4.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);

            sondeAmpero.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
            ligneCapteurAmpero.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ampero) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);

            if (Donnees.instance().presence(Donnees.Capteur.Ph)
                    || Donnees.instance().presence(Donnees.Capteur.Redox)
                    || Donnees.instance().presence(Donnees.Capteur.Ampero)) {
                ligneEntreeTor.setVisibility(Donnees.instance().obtenirTypeAsservissement().equals(Donnees.ASSERVISSEMENT_TOR) ? View.VISIBLE : View.GONE);
                ligneEntreeLineaire1.setVisibility(Donnees.instance().obtenirTypeAsservissement().equals(Donnees.ASSERVISSEMENT_LIN) ? View.VISIBLE : View.GONE);
                ligneEntreeLineaire2.setVisibility(Donnees.instance().obtenirTypeAsservissement().equals(Donnees.ASSERVISSEMENT_LIN) ? View.VISIBLE : View.GONE);
                ligneEntreePorteSonde.setVisibility(View.VISIBLE);
                ligneSortiePorteSonde1.setVisibility(View.VISIBLE);
                ligneSortiePorteSonde2.setVisibility(View.VISIBLE);
                ligneSortieTor1.setVisibility(Donnees.instance().obtenirTypeAsservissement().equals(Donnees.ASSERVISSEMENT_TOR) ? View.VISIBLE : View.GONE);
                ligneSortieTor2.setVisibility(Donnees.instance().obtenirTypeAsservissement().equals(Donnees.ASSERVISSEMENT_TOR) ? View.VISIBLE : View.GONE);
                ligneSortieTor3.setVisibility(Donnees.instance().obtenirTypeAsservissement().equals(Donnees.ASSERVISSEMENT_TOR) ? View.VISIBLE : View.GONE);

                conteneurDonneesCapteurs.setVisibility(View.VISIBLE);
                texteTypeRegulation.setVisibility(View.VISIBLE);
                texteTypeRegulation.setText("Type de régulation : " + Donnees.instance().obtenirTypeAsservissement());
                texteCapteurPh.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ph) ? View.VISIBLE : View.GONE);
                texteCapteurPh.setText("pH : " + (Donnees.instance().obtenirEtat(Donnees.Capteur.Ph) ? Double.toString(Donnees.instance().obtenirValeur(Donnees.Capteur.Ph)) : "Err"));
                texteCapteurOrp.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) ? View.VISIBLE : View.GONE);
                texteCapteurOrp.setText("ORP : " + (Donnees.instance().obtenirEtat(Donnees.Capteur.Redox) ? Donnees.instance().obtenirValeur(Donnees.Capteur.Redox) + " mV" : "Err"));
                texteCapteurAmpero.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
                texteCapteurAmpero.setText("Ampéro : " + (Donnees.instance().obtenirEtat(Donnees.Capteur.Ampero) ? Donnees.instance().obtenirValeur(Donnees.Capteur.Ampero) + " ppm" : "Err"));

                if (Donnees.instance().presence(Donnees.Capteur.Ph)) {
                    conteneurDonneesCapteurs.setX(sondePh.getX() + sondePh.getWidth() / 2);
                } else if (Donnees.instance().presence(Donnees.Capteur.Redox)) {
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
            texteValeurTempInterne.setTextColor(Donnees.instance().obtenirEtat(Donnees.Capteur.TemperatureInterne) ? Color.BLACK : Color.RED);
            texteValeurHumiditeInterne.setTextColor(Donnees.instance().obtenirEtat(Donnees.Capteur.HumiditeInterne) ? Color.BLACK : Color.RED);
            texteValeurPressionAtmInterne.setTextColor(Donnees.instance().obtenirEtat(Donnees.Capteur.PressionAtmospheriqueInterne) ? Color.BLACK : Color.RED);

            texteValeurTempExterne.setTextColor(Donnees.instance().obtenirEtat(Donnees.Capteur.TemperatureExterne) ? Color.BLACK : Color.RED);
            texteValeurHumiditeExterne.setTextColor(Donnees.instance().obtenirEtat(Donnees.Capteur.HumiditeExterne) ? Color.BLACK : Color.RED);
            texteValeurPressionAtmExterne.setTextColor(Donnees.instance().obtenirEtat(Donnees.Capteur.PressionAtmospheriqueExterne) ? Color.BLACK : Color.RED);

            if (Donnees.instance().obtenirEtatLectureCapteurs()) {
                if (Donnees.instance().obtenirEtat(Donnees.Capteur.Pression)) {
                    if ((Donnees.instance().obtenirSeuilBasPression() <= Donnees.instance().obtenirValeur(Donnees.Capteur.Pression))
                            && (Donnees.instance().obtenirValeur(Donnees.Capteur.Pression) <= Donnees.instance().obtenirSeuilHautPression())) {
                        texteCapteurPression.setTextColor(Color.GREEN);
                    } else {
                        texteCapteurPression.setTextColor(Color.RED);
                    }
                } else {
                    texteCapteurPression.setTextColor(Color.RED);
                }

                if (Donnees.instance().obtenirEtat(Donnees.Capteur.Ph)) {
                    if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins)) {
                        if (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.PhPlus)
                                || Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.PhMoins)) {
                            texteCapteurPh.setTextColor(Color.parseColor("#FFAA00"));
                        } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.Ph) < (Donnees.instance().obtenirConsignePh() - Donnees.instance().obtenirHysteresisPhPlus())) {
                            texteCapteurPh.setTextColor(Color.RED);
                        } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.Ph) > (Donnees.instance().obtenirConsignePh() + Donnees.instance().obtenirHysteresisPhMoins())) {
                            texteCapteurPh.setTextColor(Color.RED);
                        } else {
                            texteCapteurPh.setTextColor(Color.GREEN);
                        }
                    } else if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus)) {
                        if (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.PhPlus)) {
                            texteCapteurPh.setTextColor(Color.parseColor("#FFAA00"));
                        } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.Ph) < (Donnees.instance().obtenirConsignePh() - Donnees.instance().obtenirHysteresisPhPlus())) {
                            texteCapteurPh.setTextColor(Color.RED);
                        } else {
                            texteCapteurPh.setTextColor(Color.GREEN);
                        }
                    } else if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins)) {
                        if (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.PhMoins)) {
                            texteCapteurPh.setTextColor(Color.parseColor("#FFAA00"));
                        } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.Ph) > (Donnees.instance().obtenirConsignePh() + Donnees.instance().obtenirHysteresisPhMoins())) {
                            texteCapteurPh.setTextColor(Color.RED);
                        } else {
                            texteCapteurPh.setTextColor(Color.GREEN);
                        }
                    } else {
                        texteCapteurPh.setTextColor(Color.BLACK);
                    }
                } else {
                    texteCapteurPh.setTextColor(Color.RED);
                }

                if (Donnees.instance().presence(Donnees.Capteur.Ampero)) {
                    if (Donnees.instance().obtenirEtat(Donnees.Capteur.Ampero)) {
                        if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp)) {
                            if (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.Orp)) {
                                texteCapteurAmpero.setTextColor(Color.parseColor("#FFAA00"));
                            } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.Ampero) < (Donnees.instance().obtenirConsigneAmpero() - Donnees.instance().obtenirHysteresisAmpero())) {
                                texteCapteurAmpero.setTextColor(Color.RED);
                            } else {
                                texteCapteurAmpero.setTextColor(Color.GREEN);
                            }
                        } else {
                            texteCapteurAmpero.setTextColor(Color.BLACK);
                        }
                    } else {
                        texteCapteurAmpero.setTextColor(Color.RED);
                    }

                    if (Donnees.instance().obtenirEtat(Donnees.Capteur.Redox)) {
                        if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp)) {
                            if (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.Orp)
                                    && !Donnees.instance().obtenirEtat(Donnees.Capteur.Ampero)) {
                                texteCapteurOrp.setTextColor(Color.parseColor("#FFAA00"));
                            } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.Redox) < (Donnees.instance().obtenirConsigneOrp() - Donnees.instance().obtenirHysteresisOrp())) {
                                texteCapteurOrp.setTextColor(Color.RED);
                            } else {
                                texteCapteurOrp.setTextColor(Color.GREEN);
                            }
                        } else {
                            texteCapteurOrp.setTextColor(Color.BLACK);
                        }
                    } else {
                        texteCapteurOrp.setTextColor(Color.RED);
                    }
                } else {
                    if (Donnees.instance().obtenirEtat(Donnees.Capteur.Redox)) {
                        if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp)) {
                            if (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.Orp)) {
                                texteCapteurOrp.setTextColor(Color.parseColor("#FFAA00"));
                            } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.Redox) < (Donnees.instance().obtenirConsigneOrp() - Donnees.instance().obtenirHysteresisOrp())) {
                                texteCapteurOrp.setTextColor(Color.RED);
                            } else {
                                texteCapteurOrp.setTextColor(Color.GREEN);
                            }
                        } else {
                            texteCapteurOrp.setTextColor(Color.BLACK);
                        }
                    } else {
                        texteCapteurOrp.setTextColor(Color.RED);
                    }
                }

                if (Donnees.instance().obtenirEtat(Donnees.Capteur.TemperatureBassin)) {
                    if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage)) {
                        if ((Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Chauffage) == Donnees.MARCHE)
                                || (Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Chauffage) == Donnees.AUTO_MARCHE)) {
                            texteCapteurPt.setTextColor(Color.parseColor("#FFAA00"));
                        } else if (Donnees.instance().obtenirControlePompeFiltration() == Donnees.CONTROLE_PAR_POMPE_FILTRATION) {
                            if ((Donnees.instance().obtenirTemperatureArret() <= Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin))
                                    && (Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin) <= Donnees.instance().obtenirTemperatureEnclenchement())) {
                                texteCapteurPt.setTextColor(Color.GREEN);
                            } else {
                                texteCapteurPt.setTextColor(Color.RED);
                            }
                        } else {
                            if (Donnees.instance().obtenirTemperatureConsigne() <= Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin)) {
                                texteCapteurPt.setTextColor(Color.GREEN);
                            } else {
                                texteCapteurPt.setTextColor(Color.RED);
                            }
                        }
                    } else {
                        texteCapteurPt.setTextColor(Color.BLACK);
                    }
                } else {
                    texteCapteurPt.setTextColor(Color.RED);
                }
            } else {
                texteCapteurPression.setTextColor(Color.BLACK);
            }

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
            tuyauEntreeSurpresseur2.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_6_9_actif : R.drawable.tuyau_6_9_inactif);
            tuyauEntreeSurpresseur3.setImageResource(pompeFiltrationActive ? R.drawable.tuyau_horizontal_actif : R.drawable.tuyau_horizontal_inactif);
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
            tuyauSortieChauffage1.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_horizontal_actif_chaud : R.drawable.tuyau_horizontal_actif) : R.drawable.tuyau_horizontal_inactif);
            tuyauSortieChauffage2.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_9_12_actif_chaud : R.drawable.tuyau_9_12_actif) : R.drawable.tuyau_9_12_inactif);
            tuyauSortieChauffage3.setImageResource(pompeFiltrationActive ? (chauffageActive ? R.drawable.tuyau_vertical_actif_chaud : R.drawable.tuyau_vertical_actif) : R.drawable.tuyau_vertical_inactif);
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
            sondePh.setImageResource(pompeFiltrationActive ? ((Donnees.instance().obtenirTypeAsservissement().equals(Donnees.ASSERVISSEMENT_TOR) && chauffageActive) ? R.drawable.sonde_actif_chaud : R.drawable.sonde_actif) : R.drawable.sonde_inactif);
            sondeOrp.setImageResource(pompeFiltrationActive ? ((Donnees.instance().obtenirTypeAsservissement().equals(Donnees.ASSERVISSEMENT_TOR) && chauffageActive) ? R.drawable.sonde_actif_chaud : R.drawable.sonde_actif) : R.drawable.sonde_inactif);
            sondeAmpero.setImageResource(pompeFiltrationActive ? ((Donnees.instance().obtenirTypeAsservissement().equals(Donnees.ASSERVISSEMENT_TOR) && chauffageActive) ? R.drawable.sonde_actif_chaud : R.drawable.sonde_actif) : R.drawable.sonde_inactif);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.bouton_accueil:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_donnees_layout));
                break;
            case R.id.bouton_menu:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_menu_layout));
                break;
            default:
                break;
        }
    }

    private void definirImageBouton(ImageButton imageButton, Donnees.Equipement equipement) {
        int mode = Donnees.instance().obtenirModeFonctionnement(equipement);
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
                imageActif = R.drawable.pac_actif;
                imageInactif = R.drawable.pac_inactif;
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
            default:
                imageActif = -1;
                imageInactif = -1;
                break;
        }

        if ((imageActif > -1) && (imageInactif > -1)) {
            if ((mode == Donnees.AUTO_MARCHE) || (mode == Donnees.MARCHE)) {
                imageButton.setImageResource(imageActif);

                if (equipement == Donnees.Equipement.Chauffage) {
                    rotateHelicePac.startAnimation();
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
        int mode = Donnees.instance().obtenirModeFonctionnement(equipement);
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
                && ((Donnees.instance().obtenirModeFonctionnement(equipement) == Donnees.AUTO_MARCHE)
                || (Donnees.instance().obtenirModeFonctionnement(equipement) == Donnees.MARCHE))) {
            animationInjection.ajouterInjection();
        } else {
            animationInjection.arreterTimer();
        }
    }

    private void definirRefoulement() {
        if ((Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.PompeFiltration) == Donnees.AUTO_MARCHE)
                || (Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.PompeFiltration) == Donnees.MARCHE)) {
            animationRefoulement.ajouterInjection();
        } else {
            animationRefoulement.arreterTimer();
        }
    }

    private void afficherElementsEquipement(Donnees.Equipement equipement, View... views) {
        boolean equipementInstalle = Donnees.instance().obtenirEquipementInstalle(equipement);

        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(equipementInstalle ? View.VISIBLE : View.GONE);
        }
    }

    private void definirConsoRegulation(TextView textView, ProgressBar progressBar, Donnees.Equipement equipement) {
        double conso = Donnees.instance().obtenirConsoVolumeRestant(equipement);
        double consoMax = Donnees.instance().obtenirConsoVolume(equipement);
        int couleurTexte;

        if (conso < (consoMax * Global.HYSTERESIS_BIDON_VIDE / 100.0)) {
            couleurTexte = getResources().getColor(android.R.color.holo_red_light);
        } else if (conso < (consoMax * Global.HYSTERESIS_BIDON_PRESQUE_VIDE / 100.0)) {
            couleurTexte = getResources().getColor(android.R.color.holo_orange_light);
        } else {
            couleurTexte = getResources().getColor(android.R.color.holo_green_light);
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

}
