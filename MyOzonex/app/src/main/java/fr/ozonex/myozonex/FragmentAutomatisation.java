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
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class FragmentAutomatisation extends Fragment implements View.OnClickListener {
    View view = null;

    // Tout orientations
    CheckBox checkBoxHeuresCreuses;
    CheckBox checkBoxDonneesEquipements;
    CheckBox checkBoxPlagesAuto;
    TextView texteIndicPlagesAuto;
    CheckBox checkBoxAsservissementPhPlus;
    CheckBox checkBoxAsservissementPhMoins;
    CheckBox checkBoxAsservissementOrp;
    TextView texteIndicAsservissements;
    CheckBox checkBoxConsigne;
    TextView texteIndicConsigne;

    // Orientation portrait
    LinearLayout globalLayoutPortrait;

    // Orientation paysage
    HorizontalScrollView globalLayoutPaysage;
    ImageButton boutonRetour;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.automatisation_layout, container, false);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            globalLayoutPortrait = view.findViewById(R.id.global_layout);
        } else {
            new ScaleListener((HorizontalScrollView) view.findViewById(R.id.horizontal_scroll),
                    (ScrollView) view.findViewById(R.id.vertical_scroll),
                    (AbsoluteLayout) view.findViewById(R.id.layout));

            globalLayoutPaysage = view.findViewById(R.id.horizontal_scroll);
            boutonRetour = (ImageButton) view.findViewById(R.id.bouton_retour);

            boutonRetour.setOnClickListener(this);
        }

        checkBoxHeuresCreuses = view.findViewById(R.id.checkbox_heures_creuses);
        checkBoxDonneesEquipements = view.findViewById(R.id.checkbox_donnees_equipements);
        checkBoxPlagesAuto = view.findViewById(R.id.checkbox_plages_auto);
        texteIndicPlagesAuto = view.findViewById(R.id.texte_indic_plage_auto);
        checkBoxAsservissementPhPlus = view.findViewById(R.id.checkbox_asservissement_ph_plus);
        checkBoxAsservissementPhMoins = view.findViewById(R.id.checkbox_asservissement_ph_moins);
        checkBoxAsservissementOrp = view.findViewById(R.id.checkbox_asservissement_orp);
        texteIndicAsservissements = view.findViewById(R.id.texte_indic_asservissements);
        checkBoxConsigne = view.findViewById(R.id.checkbox_consigne);
        texteIndicConsigne = view.findViewById(R.id.texte_indic_consigne);

        checkBoxHeuresCreuses.setOnClickListener(this);
        checkBoxDonneesEquipements.setOnClickListener(this);
        checkBoxPlagesAuto.setOnClickListener(this);
        checkBoxAsservissementPhPlus.setOnClickListener(this);
        checkBoxAsservissementPhMoins.setOnClickListener(this);
        checkBoxAsservissementOrp.setOnClickListener(this);
        checkBoxConsigne.setOnClickListener(this);


        update();


        return view;
    }

    public void update() {
        calculConsigneAuto();

        if ((view != null) && isAdded()) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                globalLayoutPortrait.setBackgroundResource(Donnees.instance().obtenirBackground());
            } else {
                globalLayoutPaysage.setBackgroundResource(Donnees.instance().obtenirBackground());
            }

            checkBoxHeuresCreuses.setClickable(false);
            checkBoxHeuresCreuses.setChecked(Donnees.instance().obtenirHeuresCreusesAuto());
            checkBoxDonneesEquipements.setClickable(Donnees.instance().obtenirActiviteIHM());
            checkBoxDonneesEquipements.setChecked(Donnees.instance().obtenirDonneesEquipementsAuto());
            checkBoxPlagesAuto.setClickable(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().presence(Donnees.Capteur.TemperatureBassin));
            checkBoxPlagesAuto.setChecked(Donnees.instance().obtenirPlagesAuto());
            updateTexteTempsFiltrationJour();
            checkBoxAsservissementPhPlus.setClickable(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus));
            checkBoxAsservissementPhPlus.setChecked(Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.PhPlus));
            checkBoxAsservissementPhMoins.setClickable(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins));
            checkBoxAsservissementPhMoins.setChecked(Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.PhMoins));
            checkBoxAsservissementOrp.setClickable(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp));
            checkBoxAsservissementOrp.setChecked(Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.Orp));
            updateTexteAsservissements();
            checkBoxConsigne.setClickable(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp));
            checkBoxConsigne.setChecked(Donnees.instance().obtenirConsigneOrpAuto());
            updateTexteConsigne();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_retour:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_menu_layout));
                break;
            case R.id.checkbox_heures_creuses:
                activerHeuresCreuses();
                break;
            case R.id.checkbox_donnees_equipements:
                activerDonnees();
                break;
            case R.id.checkbox_plages_auto:
                activerPlageAuto();
            case R.id.checkbox_asservissement_ph_plus:
                activerAsservissementPhPlus();
                break;
            case R.id.checkbox_asservissement_ph_moins:
                activerAsservissementPhMoins();
                break;
            case R.id.checkbox_asservissement_orp:
                activerAsservissementOrp();
                break;
            case R.id.checkbox_consigne:
                activerConsigneOrp();
                break;
            default:
                break;
        }
    }

    private void activerHeuresCreuses() {
        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageAutomatisation),
                "heures_creuses=" + String.valueOf(checkBoxHeuresCreuses.isChecked() ? 1 : 0));
    }

    private void activerDonnees() {
        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageAutomatisation),
                "donnees_equipement=" + String.valueOf(checkBoxDonneesEquipements.isChecked() ? 1 : 0));
    }

    private void activerPlageAuto() {
        Donnees.instance().definirModifPlagesAuto(checkBoxPlagesAuto.isChecked());
        updateTexteTempsFiltrationJour();

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageAutomatisation),
                "plages_auto=" + String.valueOf(checkBoxPlagesAuto.isChecked() ? 1 : 0) + "&modif_plage_auto=1");
    }

    private void updateTexteTempsFiltrationJour() {
        if (checkBoxPlagesAuto.isEnabled()) {
            if (checkBoxPlagesAuto.isChecked()) {
                if (Donnees.instance().obtenirModifPlagesAuto()) {
                    MainActivity.instance().setHtmlText(texteIndicPlagesAuto, "<i>Calcul en cours...</i>");
                } else {
                    MainActivity.instance().setHtmlText(texteIndicPlagesAuto, "<i>Temps filtration / jour : <b>" + Donnees.instance().obtenirTempsFiltrationJour() + "</b></i>");
                }
            } else {
                MainActivity.instance().setHtmlText(texteIndicPlagesAuto, "<i>Estimation en fonction de la température du bassin</i>");
            }
        } else {
            MainActivity.instance().setHtmlText(texteIndicPlagesAuto, "<i>Nécessite la température du bassin</i>");
        }
    }

    private void activerAsservissementPhPlus() {
        updateTexteAsservissements();

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageAutomatisation),
                "asservissement_ph_plus=" + String.valueOf(checkBoxAsservissementPhPlus.isChecked() ? 1 : 0));
    }

    private void activerAsservissementPhMoins() {
        updateTexteAsservissements();

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageAutomatisation),
                "asservissement_ph_moins=" + String.valueOf(checkBoxAsservissementPhMoins.isChecked() ? 1 : 0));
    }

    private void activerAsservissementOrp() {
        updateTexteAsservissements();

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageAutomatisation),
                "asservissement_orp=" + String.valueOf(checkBoxAsservissementOrp.isChecked() ? 1 : 0));
    }

    private void updateTexteAsservissements() {
        if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) || Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) || Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp)) {
            MainActivity.instance().setHtmlText(texteIndicAsservissements, "<i>Estimation des valeurs en fonction du volume du bassin</i>");
        } else {
            MainActivity.instance().setHtmlText(texteIndicAsservissements, "<i>Nécessite l'installation d'au moins un régulateur</i>");
        }
    }

    private void activerConsigneOrp() {
        calculConsigneAuto();
        updateTexteConsigne();

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageAutomatisation),
                "consigne_orp_auto=" + String.valueOf(checkBoxConsigne.isChecked() ? 1 : 0));
    }

    private void calculConsigneAuto() {
        if (Donnees.instance().obtenirConsigneOrpAuto()) {
            if (Donnees.instance().obtenirEtat(Donnees.Capteur.TemperatureBassin)) {
                if (Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin) <= Global.TEMPERATURE_MINIMUM_CONSIGNE_ORP) {
                    Donnees.instance().definirConsigneOrp(Donnees.instance().obtenirConsigneOrp());
                    Donnees.instance().definirConsigneAmpero(Donnees.instance().obtenirConsigneAmpero());
                } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin) <= Global.TEMPERATURE_MAXIMUM_CONSIGNE_ORP) {
                    Donnees.instance().definirConsigneOrp(Donnees.instance().obtenirConsigneOrp() + ((int)Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin) - Global.TEMPERATURE_MINIMUM_CONSIGNE_ORP) * Global.AJOUT_CONSIGNE_ORP);
                    Donnees.instance().definirConsigneAmpero(Donnees.instance().obtenirConsigneAmpero() + ((int)Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin) - Global.TEMPERATURE_MINIMUM_CONSIGNE_ORP) * Global.AJOUT_CONSIGNE_AMPERO);
                } else {
                    Donnees.instance().definirConsigneOrp(Donnees.instance().obtenirConsigneOrp() + (Global.TEMPERATURE_MAXIMUM_CONSIGNE_ORP - Global.TEMPERATURE_MINIMUM_CONSIGNE_ORP) * Global.AJOUT_CONSIGNE_ORP);
                    Donnees.instance().definirConsigneAmpero(Donnees.instance().obtenirConsigneAmpero() + (Global.TEMPERATURE_MAXIMUM_CONSIGNE_ORP - Global.TEMPERATURE_MINIMUM_CONSIGNE_ORP) * Global.AJOUT_CONSIGNE_AMPERO);
                }
            } else {
                Donnees.instance().definirConsigneOrp(Donnees.instance().obtenirConsigneOrp());
                Donnees.instance().definirConsigneAmpero(Donnees.instance().obtenirConsigneAmpero());
            }
        } else {
            Donnees.instance().definirConsigneOrp(Donnees.instance().obtenirConsigneOrp());
            Donnees.instance().definirConsigneAmpero(Donnees.instance().obtenirConsigneAmpero());
        }
    }

    private void updateTexteConsigne() {
        if (checkBoxConsigne.isEnabled()) {
            if (checkBoxConsigne.isChecked()) {
                if (Donnees.instance().presence(Donnees.Capteur.Redox) && Donnees.instance().presence(Donnees.Capteur.Ampero)) {
                    MainActivity.instance().setHtmlText(texteIndicConsigne, "<i>Nouveaux points de consigne : <b>" + Donnees.instance().obtenirConsigneOrp() + " mV</b> / <b>" + Donnees.instance().obtenirConsigneAmpero() + " ppm</b></i>");
                } else if (Donnees.instance().presence(Donnees.Capteur.Ampero)) {
                    MainActivity.instance().setHtmlText(texteIndicConsigne, "<i>Nouveau point de consigne : <b>" + Donnees.instance().obtenirConsigneAmpero() + " ppm</b></i>");
                } else {
                    MainActivity.instance().setHtmlText(texteIndicConsigne, "<i>Nouveau point de consigne : <b>" + Donnees.instance().obtenirConsigneOrp() + " mV</b></i>");
                }
            } else {
                MainActivity.instance().setHtmlText(texteIndicConsigne, "<i>Estimation en fonction de la température du bassin</i>");
            }
        } else {
            if (Donnees.instance().presence(Donnees.Capteur.TemperatureBassin)) {
                MainActivity.instance().setHtmlText(texteIndicConsigne, "<i>Nécessite le régulateur ORP</i>");
            } else if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp)) {
                MainActivity.instance().setHtmlText(texteIndicConsigne, "<i>Nécessite la température du bassin</i>");
            } else {
                MainActivity.instance().setHtmlText(texteIndicConsigne, "<i>Nécessite la température du bassin et le régulateur ORP</i>");
            }
        }
    }
}
