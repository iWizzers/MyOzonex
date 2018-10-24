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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static android.support.v4.view.MotionEventCompat.getPointerCount;

public class FragmentRegulateurORP extends Fragment implements View.OnClickListener {
    View view = null;

    // Tout orientations
    TextView texteConso;
    RadioButton rbTOR;
    RadioButton rbLineaire;
    TextView texteDonneesAsservissement;
    CheckBox checkBoxSurchloration;
    TextView texteDonneesSurchloration;

    // Orientation portrait
    RadioGroup rgBoutonsMode;
    RadioButton rbAuto;
    RadioButton rbArret;
    RadioButton rbMarche;

    // Orientation paysage
    private ScaleGestureDetector mScaleGestureDetector;
    AbsoluteLayout layout;
    ImageButton boutonRetour;
    ImageView bouton3Etats;
    Button boutonAuto;
    Button boutonArret;
    Button boutonMarche;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.regulateur_orp_layout, container, false);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rgBoutonsMode = (RadioGroup) view.findViewById(R.id.groupe_boutons_mode);
            rbAuto = (RadioButton) view.findViewById(R.id.radio_bouton_auto);
            rbArret = (RadioButton) view.findViewById(R.id.radio_bouton_arret);
            rbMarche = (RadioButton) view.findViewById(R.id.radio_bouton_marche);

            rbAuto.setOnClickListener(this);
            rbArret.setOnClickListener(this);
            rbMarche.setOnClickListener(this);
        } else {
            layout = (AbsoluteLayout) view.findViewById(R.id.layout);
            mScaleGestureDetector = new ScaleGestureDetector(MainActivity.instance(), new ScaleListener(layout));
            view.findViewById(R.id.horizontal_scroll).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (getPointerCount(event) == 2) {
                        mScaleGestureDetector.onTouchEvent(event);
                    } else {
                        return false;
                    }

                    return true;
                }
            });
            view.findViewById(R.id.vertical_scroll).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (getPointerCount(event) == 2) {
                        mScaleGestureDetector.onTouchEvent(event);
                    } else {
                        return false;
                    }

                    return true;
                }
            });

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
        rbTOR = (RadioButton) view.findViewById(R.id.radio_bouton_tor);
        rbLineaire = (RadioButton) view.findViewById(R.id.radio_bouton_lineaire);
        texteDonneesAsservissement = (TextView) view.findViewById(R.id.texte_donnees_asservissement);
        checkBoxSurchloration = (CheckBox) view.findViewById(R.id.checkbox_surchloration);
        texteDonneesSurchloration = (TextView) view.findViewById(R.id.texte_donnees_surchloration);


        update();


        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            modeAEteModifie(Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Orp));

            asservissementAEteModifie(Donnees.instance().obtenirTypeAsservissement(),
                    Donnees.instance().obtenirReglageAuto(Donnees.Equipement.Orp));

            consoAEteModifie(Donnees.instance().obtenirDateDebutConso(Donnees.Equipement.Orp),
                    Donnees.instance().obtenirConsoVolume(Donnees.Equipement.Orp),
                    Donnees.instance().obtenirConsoVolumeRestant(Donnees.Equipement.Orp));
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
        int etat = Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Orp);
        String data = "state=";

        if (rb == rbAuto) {
            etat = (etat == Donnees.AUTO_MARCHE) ? Donnees.AUTO_MARCHE : Donnees.AUTO_ARRET;
        } else if (rb == rbMarche) {
            etat = Donnees.MARCHE;
        } else {
            etat = Donnees.ARRET;
        }

        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Orp, etat);
        modeAEteModifie(etat);

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.Orp),
                data + String.valueOf(etat));
    }

    private void modifierMode(Button bouton) {
        int etat = Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Orp);
        String data = "state=";

        if (bouton == boutonAuto) {
            etat = (etat == Donnees.AUTO_MARCHE) ? Donnees.AUTO_MARCHE : Donnees.AUTO_ARRET;
        } else if (bouton == boutonMarche) {
            etat = Donnees.MARCHE;
        } else {
            etat = Donnees.ARRET;
        }

        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Orp, etat);
        modeAEteModifie(etat);

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.Orp),
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

    private void asservissementAEteModifie(String typeAsservissement, boolean auto) {
        String data = "";

        rbTOR.setChecked(typeAsservissement.equals(Donnees.ASSERVISSEMENT_TOR));
        rbLineaire.setChecked(typeAsservissement.equals(Donnees.ASSERVISSEMENT_LIN));

        if (typeAsservissement.equals(Donnees.ASSERVISSEMENT_TOR)) {
            data += "Durée injection : ";
            data += "<b>" + Donnees.instance().obtenirDureeInjection(Donnees.Equipement.Orp) + "</b>";
            data += "<br />Temps de réponse après injection : ";
            data += "<b>" + Donnees.instance().obtenirTempsReponse(Donnees.Equipement.Orp) + "</b>";
        } else {
            data += "Durée cycle : ";
            data += "<b>" + Donnees.instance().obtenirDureeCycle(Donnees.Equipement.Orp) + " seconde(s)</b>";
            data += "<br />Multiplicateur de différence : ";
            data += "<b>" + Donnees.instance().obtenirMultiplicateurDifference(Donnees.Equipement.Orp) + "</b>";

            if (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.Orp)) {
                data += "<br />Durée injection : ";
                data += "<b>" + Donnees.instance().obtenirDureeInjection(Donnees.Equipement.Orp) + "</b>";
                data += "<br />Temps de réponse après injection : ";
                data += "<b>" + Donnees.instance().obtenirTempsReponse(Donnees.Equipement.Orp) + "</b>";
            }
        }

        data += "<br />Temps d'injection journalier maximum : ";
        data += "<b>" + Donnees.instance().obtenirTempsInjectionJournalierMax(Donnees.Equipement.Orp) + " minute(s)</b>";
        data += "<br /><font color=\"" + (Donnees.instance().obtenirTempsInjectionJournalierMaxRestant(Donnees.Equipement.Orp) > 0 ? "#00FF00" : "orange") + "\"><i>Temps d'injection journalier maximum restant : ";
        data += "<b>" + Donnees.instance().obtenirTempsInjectionJournalierMaxRestant(Donnees.Equipement.Orp) + " minute(s)</b></i></font>";

        MainActivity.instance().setHtmlText(texteDonneesAsservissement, data);

        checkBoxSurchloration.setText("Surchloration : " + (Donnees.instance().obtenirEtat(Donnees.Equipement.Orp) ? "activée" : "désactivée"));
        checkBoxSurchloration.setChecked(Donnees.instance().obtenirEtat(Donnees.Equipement.Orp));
        texteDonneesSurchloration.setVisibility(Donnees.instance().obtenirEtat(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);
        MainActivity.instance().setHtmlText(texteDonneesSurchloration, Donnees.instance().obtenirDonneesSurchloration());
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
