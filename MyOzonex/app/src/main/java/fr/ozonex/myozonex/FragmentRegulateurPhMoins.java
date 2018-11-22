package fr.ozonex.myozonex;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class FragmentRegulateurPhMoins extends Fragment implements View.OnClickListener {
    View view = null;

    // Tout orientations
    TextView texteConso;
    TextView texteConsoInjections;
    RadioButton rbTOR;
    RadioButton rbLineaire;
    TextView texteDonneesAsservissement;

    // Orientation portrait
    LinearLayout globalLayoutPortrait;
    RadioGroup rgBoutonsMode;
    RadioButton rbAuto;
    RadioButton rbArret;
    RadioButton rbMarche;

    // Orientation paysage
    HorizontalScrollView globalLayoutPaysage;
    ImageButton boutonRetour;
    ImageView bouton3Etats;
    Button boutonAuto;
    Button boutonArret;
    Button boutonMarche;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.regulateur_ph_moins_layout, container, false);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            globalLayoutPortrait = view.findViewById(R.id.global_layout);
            rgBoutonsMode = (RadioGroup) view.findViewById(R.id.groupe_boutons_mode);
            rbAuto = (RadioButton) view.findViewById(R.id.radio_bouton_auto);
            rbArret = (RadioButton) view.findViewById(R.id.radio_bouton_arret);
            rbMarche = (RadioButton) view.findViewById(R.id.radio_bouton_marche);

            rbAuto.setOnClickListener(this);
            rbArret.setOnClickListener(this);
            rbMarche.setOnClickListener(this);
        } else {
            new ScaleListener((HorizontalScrollView) view.findViewById(R.id.horizontal_scroll),
                    (ScrollView) view.findViewById(R.id.vertical_scroll),
                    (AbsoluteLayout) view.findViewById(R.id.layout));

            globalLayoutPaysage = view.findViewById(R.id.horizontal_scroll);
            boutonRetour = (ImageButton) view.findViewById(R.id.bouton_retour);
            bouton3Etats = (ImageView) view.findViewById(R.id.bouton_3_etats);
            boutonAuto = (Button) view.findViewById(R.id.bouton_auto);
            boutonArret = (Button) view.findViewById(R.id.bouton_arret);
            boutonMarche = (Button) view.findViewById(R.id.bouton_marche);

            boutonRetour.setOnClickListener(this);
            boutonAuto.setOnClickListener(this);
            boutonArret.setOnClickListener(this);
            boutonMarche.setOnClickListener(this);
        }

        texteConso = (TextView) view.findViewById(R.id.texte_donnees_conso);
        texteConsoInjections = view.findViewById(R.id.texte_conso_injections);
        rbTOR = (RadioButton) view.findViewById(R.id.radio_bouton_tor);
        rbLineaire = (RadioButton) view.findViewById(R.id.radio_bouton_lineaire);
        texteDonneesAsservissement = (TextView) view.findViewById(R.id.texte_donnees_asservissement);


        update();


        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            if (!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins)) {
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_synoptique_layout));
            }

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                globalLayoutPortrait.setBackgroundResource(Donnees.instance().obtenirBackground());
                rbAuto.setClickable(Donnees.instance().obtenirActiviteIHM());
                rbArret.setClickable(Donnees.instance().obtenirActiviteIHM());
                rbMarche.setClickable(Donnees.instance().obtenirActiviteIHM());
            } else {
                globalLayoutPaysage.setBackgroundResource(Donnees.instance().obtenirBackground());
                boutonAuto.setClickable(Donnees.instance().obtenirActiviteIHM());
                boutonArret.setClickable(Donnees.instance().obtenirActiviteIHM());
                boutonMarche.setClickable(Donnees.instance().obtenirActiviteIHM());
            }

            modeAEteModifie(Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.PhMoins));

            asservissementAEteModifie(Donnees.instance().obtenirTypeAsservissement());

            consoAEteModifie(Donnees.instance().obtenirDateDebutConso(Donnees.Equipement.PhMoins),
                    Donnees.instance().obtenirConsoVolume(Donnees.Equipement.PhMoins),
                    Donnees.instance().obtenirConsoVolumeRestant(Donnees.Equipement.PhMoins));
            MainActivity.instance().setHtmlText(texteConsoInjections, "Produit injecté sur 1 /7 / 28 jours : "
                    + "<b>" + Donnees.instance().obtenirConsoJour(Donnees.Equipement.PhMoins) + "</b> / "
                    + "<b>" + Donnees.instance().obtenirConsoSemaine(Donnees.Equipement.PhMoins) + "</b> / "
                    + "<b>" + Donnees.instance().obtenirConsoMois(Donnees.Equipement.PhMoins) + "</b>");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_retour:
                if (Donnees.instance().obtenirPageSource() == Donnees.PAGE_SYNOPTIQUE) {
                    MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_synoptique_layout));
                } else {
                    MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_menu_layout));
                }
                break;
            case R.id.radio_bouton_auto:
            case R.id.radio_bouton_arret:
            case R.id.radio_bouton_marche:
                modifierMode((RadioButton) view.findViewById(rgBoutonsMode.getCheckedRadioButtonId()));
                break;
            case R.id.bouton_auto:
            case R.id.bouton_arret:
            case R.id.bouton_marche:
                modifierMode((Button) v.findViewById(v.getId()));
                break;
            default:
                break;
        }
    }

    private void modifierMode(RadioButton rb) {
        int etat = Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.PhMoins);
        String data = "etat=";

        if (rb == rbAuto) {
            etat = (etat == Donnees.AUTO_MARCHE) ? Donnees.AUTO_MARCHE : Donnees.AUTO_ARRET;
        } else if (rb == rbMarche) {
            etat = Donnees.MARCHE;
        } else {
            etat = Donnees.ARRET;
        }

        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.PhMoins, etat);
        modeAEteModifie(etat);

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageRegulateurPhMoins),
                data + String.valueOf(etat));
    }

    private void modifierMode(Button bouton) {
        int etat = Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.PhMoins);
        String data = "etat=";

        if (bouton == boutonAuto) {
            etat = (etat == Donnees.AUTO_MARCHE) ? Donnees.AUTO_MARCHE : Donnees.AUTO_ARRET;
        } else if (bouton == boutonMarche) {
            etat = Donnees.MARCHE;
        } else {
            etat = Donnees.ARRET;
        }

        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.PhMoins, etat);
        modeAEteModifie(etat);

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageRegulateurPhMoins),
                data + String.valueOf(etat));
    }

    private void modeAEteModifie(int mode) {
        String autoText;
        LinearLayout.LayoutParams paramEtatOk = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1.0f
        );
        LinearLayout.LayoutParams paramEtatNok = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                2.0f
        );

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            autoText = "Auto (" + ((mode == Donnees.AUTO_MARCHE) ? "marche" : "arrêt") + ")";

            if (mode > Donnees.AUTO) {
                rbAuto.setChecked(true);
            } else if (mode == Donnees.MARCHE) {
                rbMarche.setChecked(true);
            } else {
                rbArret.setChecked(true);
            }

            rbAuto.setText(autoText);
        } else {
            if (mode > Donnees.AUTO) {
                bouton3Etats.setImageResource(R.drawable.bouton_haut_vertical);
                boutonAuto.setTextColor(getResources().getColor(R.color.bouton3EtatSelectionne));
                boutonArret.setTextColor(getResources().getColor(R.color.bouton3EtatNonSelectionne));
                boutonMarche.setTextColor(getResources().getColor(R.color.bouton3EtatNonSelectionne));
                boutonAuto.setLayoutParams(paramEtatOk);
                boutonArret.setLayoutParams(paramEtatNok);
                boutonMarche.setLayoutParams(paramEtatNok);
            } else if (mode == Donnees.MARCHE) {
                bouton3Etats.setImageResource(R.drawable.bouton_bas_vertical);
                boutonAuto.setTextColor(getResources().getColor(R.color.bouton3EtatNonSelectionne));
                boutonArret.setTextColor(getResources().getColor(R.color.bouton3EtatNonSelectionne));
                boutonMarche.setTextColor(getResources().getColor(R.color.bouton3EtatSelectionne));
                boutonAuto.setLayoutParams(paramEtatNok);
                boutonArret.setLayoutParams(paramEtatNok);
                boutonMarche.setLayoutParams(paramEtatOk);
            } else {
                bouton3Etats.setImageResource(R.drawable.bouton_off_vertical);
                boutonAuto.setTextColor(getResources().getColor(R.color.bouton3EtatNonSelectionne));
                boutonArret.setTextColor(getResources().getColor(R.color.bouton3EtatSelectionne));
                boutonMarche.setTextColor(getResources().getColor(R.color.bouton3EtatNonSelectionne));
                boutonAuto.setLayoutParams(paramEtatNok);
                boutonArret.setLayoutParams(paramEtatOk);
                boutonMarche.setLayoutParams(paramEtatNok);
            }
        }
    }

    private void asservissementAEteModifie(String typeAsservissement) {
        String data = "";

        rbTOR.setChecked(typeAsservissement.equals(Donnees.ASSERVISSEMENT_TOR));
        rbLineaire.setChecked(typeAsservissement.equals(Donnees.ASSERVISSEMENT_LIN));

        if (typeAsservissement.equals(Donnees.ASSERVISSEMENT_TOR)) {
            data += "Durée injection : ";
            data += "<b>" + Donnees.instance().obtenirDureeInjection(Donnees.Equipement.PhMoins) + "</b>";
            data += "<br />Temps de réponse après injection : ";
            data += "<b>" + Donnees.instance().obtenirTempsReponse(Donnees.Equipement.PhMoins) + "</b>";
        } else {
            data += "Durée cycle : ";
            data += "<b>" + Donnees.instance().obtenirDureeCycle(Donnees.Equipement.PhMoins) + " seconde(s)</b>";
            data += "<br />Multiplicateur de différence : ";
            data += "<b>" + Donnees.instance().obtenirMultiplicateurDifference(Donnees.Equipement.PhMoins) + "</b>";

            if (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.PhMoins)) {
                data += "<br />Durée injection : ";
                data += "<b>" + Donnees.instance().obtenirDureeInjection(Donnees.Equipement.PhMoins) + "</b>";
                data += "<br />Temps de réponse après injection : ";
                data += "<b>" + Donnees.instance().obtenirTempsReponse(Donnees.Equipement.PhMoins) + "</b>";
            }
        }

        data += "<br />Temps d'injection journalier maximum : ";
        data += "<b>" + Donnees.instance().obtenirTempsInjectionJournalierMax(Donnees.Equipement.PhMoins) + " minute(s)</b>";
        data += "<br /><font color=\"" + (Donnees.instance().obtenirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhMoins) > 0 ? "#00FF00" : "orange") + "\"><i>Temps d'injection journalier maximum restant : ";
        data += "<b>" + Donnees.instance().obtenirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhMoins) + " minute(s)</b></i></font>";

        MainActivity.instance().setHtmlText(texteDonneesAsservissement, data);
    }

    private void consoAEteModifie(String date, double consoVolume, double consoVolumeRestant) {
        String color;

        if (consoVolumeRestant < (consoVolume * Global.HYSTERESIS_BIDON_VIDE / 100.0)) {
            color = "red";
        } else if (consoVolumeRestant < (consoVolume * Global.HYSTERESIS_BIDON_PRESQUE_VIDE / 100.0)) {
            color = "orange";
        } else {
            color = "#00FF00";
        }

        MainActivity.instance().setHtmlText(texteConso, "Depuis : " + date +
                "<br />Volume : <b>" + consoVolume + " L</b>" +
                "<br /><font color=\"" + color + "\"><i>Volume restant : <b>" + consoVolumeRestant + " L</b></i></font>");
    }
}
