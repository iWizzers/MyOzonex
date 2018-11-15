package fr.ozonex.myozonex;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ScrollView;

import static android.support.v4.view.MotionEventCompat.getPointerCount;

public class FragmentMenu extends Fragment implements View.OnClickListener {
    View view = null;

    // Orientation paysage
    HorizontalScrollView globalLayoutPaysage;
    ImageButton boutonRetour;
    ImageButton boutonPompeFiltration;
    ImageButton boutonFiltre;
    ImageButton boutonSurpresseur;
    ImageButton boutonChauffage;
    ImageButton boutonLampesUV;
    ImageButton boutonElectrolyseur;
    ImageButton boutonOzonateur;
    ImageButton boutonEclairage;
    ImageButton boutonRegulateurPhPlus;
    ImageButton boutonRegulateurPhMoins;
    ImageButton boutonRegulateurORP;
    ImageButton boutonAlgicide;
    ImageButton boutonBassin;
    ImageButton boutonCapteurs;
    ImageButton boutonJournalEvenements;
    ImageButton boutonDeconnexion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu_layout, container, false);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            new ScaleListener((HorizontalScrollView) view.findViewById(R.id.horizontal_scroll),
                    (ScrollView) view.findViewById(R.id.vertical_scroll),
                    (AbsoluteLayout) view.findViewById(R.id.layout));

            globalLayoutPaysage = view.findViewById(R.id.horizontal_scroll);
            boutonRetour = (ImageButton) view.findViewById(R.id.bouton_retour);
            boutonPompeFiltration = (ImageButton) view.findViewById(R.id.bouton_pompe_filtration);
            boutonFiltre = (ImageButton) view.findViewById(R.id.bouton_filtre);
            boutonSurpresseur = (ImageButton) view.findViewById(R.id.bouton_surpresseur);
            boutonChauffage = (ImageButton) view.findViewById(R.id.bouton_chauffage);
            boutonLampesUV = (ImageButton) view.findViewById(R.id.bouton_lampes_uv);
            boutonElectrolyseur = (ImageButton) view.findViewById(R.id.bouton_electrolyseur);
            boutonOzonateur = (ImageButton) view.findViewById(R.id.bouton_ozonateur);
            boutonEclairage = (ImageButton) view.findViewById(R.id.bouton_eclairage);
            boutonRegulateurPhPlus = (ImageButton) view.findViewById(R.id.bouton_regulateur_ph_plus);
            boutonRegulateurPhMoins = (ImageButton) view.findViewById(R.id.bouton_regulateur_ph_moins);
            boutonRegulateurORP = (ImageButton) view.findViewById(R.id.bouton_regulateur_orp);
            boutonAlgicide = (ImageButton) view.findViewById(R.id.bouton_algicide);
            boutonBassin = (ImageButton) view.findViewById(R.id.bouton_bassin);
            boutonCapteurs = (ImageButton) view.findViewById(R.id.bouton_capteurs);
            boutonJournalEvenements = (ImageButton) view.findViewById(R.id.bouton_journal_evenements);
            boutonDeconnexion = (ImageButton) view.findViewById(R.id.bouton_deconnexion);

            boutonRetour.setOnClickListener(this);
            boutonPompeFiltration.setOnClickListener(this);
            boutonFiltre.setOnClickListener(this);
            boutonSurpresseur.setOnClickListener(this);
            boutonChauffage.setOnClickListener(this);
            boutonLampesUV.setOnClickListener(this);
            boutonElectrolyseur.setOnClickListener(this);
            boutonOzonateur.setOnClickListener(this);
            boutonEclairage.setOnClickListener(this);
            boutonRegulateurPhPlus.setOnClickListener(this);
            boutonRegulateurPhMoins.setOnClickListener(this);
            boutonRegulateurORP.setOnClickListener(this);
            boutonAlgicide.setOnClickListener(this);
            boutonBassin.setOnClickListener(this);
            boutonCapteurs.setOnClickListener(this);
            boutonJournalEvenements.setOnClickListener(this);
            boutonDeconnexion.setOnClickListener(this);
        }


        update();


        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                globalLayoutPaysage.setBackgroundResource(Donnees.instance().obtenirBackground());
                boutonPompeFiltration.setEnabled(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PompeFiltration));
                boutonFiltre.setEnabled(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Filtre));
                boutonSurpresseur.setEnabled(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur));
                boutonChauffage.setEnabled(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage));
                boutonLampesUV.setEnabled(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.LampesUV));
                boutonOzonateur.setEnabled(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Ozone));
                boutonElectrolyseur.setEnabled(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Electrolyseur));
                boutonRegulateurPhMoins.setEnabled(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins));
                boutonRegulateurPhPlus.setEnabled(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus));
                boutonRegulateurORP.setEnabled(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp));
                boutonAlgicide.setEnabled(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Algicide));
                boutonEclairage.setEnabled(false);
                boutonCapteurs.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_retour:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_synoptique_layout));
                break;
            case R.id.bouton_pompe_filtration:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_pompe_filtration_layout));
                break;
            case R.id.bouton_filtre:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_filtre_layout));
                break;
            case R.id.bouton_surpresseur:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_surpresseur_layout));
                break;
            case R.id.bouton_chauffage:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_chauffage_layout));
                break;
            case R.id.bouton_lampes_uv:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_lampes_uv_layout));
                break;
            case R.id.bouton_electrolyseur:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_electrolyseur_layout));
                break;
            case R.id.bouton_ozonateur:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_ozone_layout));
                break;
            case R.id.bouton_eclairage:
                //MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_pompe_filtration_layout));
                break;
            case R.id.bouton_regulateur_ph_plus:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_regulateur_ph_plus_layout));
                break;
            case R.id.bouton_regulateur_ph_moins:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_regulateur_ph_moins_layout));
                break;
            case R.id.bouton_regulateur_orp:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_regulateur_orp_layout));
                break;
            case R.id.bouton_algicide:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_algicide_layout));
                break;
            case R.id.bouton_bassin:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_bassin_layout));
                break;
            case R.id.bouton_capteurs:
                //MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_pompe_filtration_layout));
                break;
            case R.id.bouton_journal_evenements:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_events_layout));
                break;
            case R.id.bouton_deconnexion:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_deconnexion_layout));
                break;
            default:
                break;
        }
    }
}
