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

public class FragmentChauffage extends Fragment implements View.OnClickListener {
    View view = null;

    // Tout orientations
    TextView texteConso;

    TextView texteAlarmes;

    LinearLayout layoutGestionTemp;
    RadioButton radioButtonPlage;
    RadioButton radioButtonConsigne;
    TextView texteArret;
    Button boutonMoinsArret;
    Button boutonPlusArret;
    TextView texteEnclenchement;
    Button boutonMoinsEnclenchement;
    Button boutonPlusEnclenchement;
    TextView texteConsigne;
    Button boutonMoinsConsigne;
    Button boutonPlusConsigne;

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
        view = inflater.inflate(R.layout.chauffage_layout, container, false);


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
        texteAlarmes = (TextView) view.findViewById(R.id.texte_alarmes);
        layoutGestionTemp = view.findViewById(R.id.layout_gestion_temp);
        radioButtonPlage = (RadioButton) view.findViewById(R.id.radio_button_plage);
        radioButtonConsigne = (RadioButton) view.findViewById(R.id.radio_button_consigne);
        texteArret = (TextView)  view.findViewById(R.id.texte_arret);
        boutonMoinsArret = (Button) view.findViewById(R.id.bouton_moins_arret);
        boutonPlusArret = (Button) view.findViewById(R.id.bouton_plus_arret);
        texteEnclenchement = (TextView)  view.findViewById(R.id.texte_enclenchement);
        boutonMoinsEnclenchement = (Button) view.findViewById(R.id.bouton_moins_enclenchement);
        boutonPlusEnclenchement = (Button) view.findViewById(R.id.bouton_plus_enclenchement);
        texteConsigne = (TextView)  view.findViewById(R.id.texte_consigne);
        boutonMoinsConsigne = (Button) view.findViewById(R.id.bouton_moins_consigne);
        boutonPlusConsigne = (Button) view.findViewById(R.id.bouton_plus_consigne);

        radioButtonPlage.setOnClickListener(this);
        radioButtonConsigne.setOnClickListener(this);
        boutonMoinsArret.setOnClickListener(this);
        boutonPlusArret.setOnClickListener(this);
        boutonMoinsEnclenchement.setOnClickListener(this);
        boutonPlusEnclenchement.setOnClickListener(this);
        boutonMoinsConsigne.setOnClickListener(this);
        boutonPlusConsigne.setOnClickListener(this);


        update();


        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            if (!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage)) {
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_synoptique_layout));
            }

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                globalLayoutPortrait.setBackgroundResource(Donnees.instance().obtenirBackground());
                rbAuto.setClickable(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().presence(Donnees.Capteur.TemperatureBassin));
                rbArret.setClickable(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().presence(Donnees.Capteur.TemperatureBassin));
                rbMarche.setClickable(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().presence(Donnees.Capteur.TemperatureBassin));
            } else {
                globalLayoutPaysage.setBackgroundResource(Donnees.instance().obtenirBackground());
                boutonAuto.setClickable(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().presence(Donnees.Capteur.TemperatureBassin));
                boutonArret.setClickable(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().presence(Donnees.Capteur.TemperatureBassin));
                boutonMarche.setClickable(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().presence(Donnees.Capteur.TemperatureBassin));
                view.findViewById(R.id.ligne_sep_donnees).setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
            }

            layoutGestionTemp.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
            radioButtonPlage.setClickable(Donnees.instance().obtenirActiviteIHM());
            boutonMoinsArret.setClickable(Donnees.instance().obtenirActiviteIHM());
            boutonPlusArret.setClickable(Donnees.instance().obtenirActiviteIHM());
            boutonMoinsEnclenchement.setClickable(Donnees.instance().obtenirActiviteIHM());
            boutonPlusEnclenchement.setClickable(Donnees.instance().obtenirActiviteIHM());
            radioButtonConsigne.setClickable(Donnees.instance().obtenirActiviteIHM());
            boutonMoinsConsigne.setClickable(Donnees.instance().obtenirActiviteIHM());
            boutonPlusConsigne.setClickable(Donnees.instance().obtenirActiviteIHM());

            modeAEteModifie(Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Chauffage));

            consoAEteModifie(Donnees.instance().obtenirDateDebutConso(Donnees.Equipement.Chauffage),
                    Donnees.instance().obtenirConsoHP(Donnees.Equipement.Chauffage),
                    Donnees.instance().obtenirConsoHC(Donnees.Equipement.Chauffage));

            alarmesAEteModifie();

            gestionTemperatureAEteModifie();
            valeurGestionTemperatureAEteModifie();
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
            case R.id.radio_button_plage:
            case R.id.radio_button_consigne:
                modifierGestionTemperature(v.getId());
                break;
            case R.id.bouton_moins_arret:
            case R.id.bouton_plus_arret:
            case R.id.bouton_moins_enclenchement:
            case R.id.bouton_plus_enclenchement:
            case R.id.bouton_moins_consigne:
            case R.id.bouton_plus_consigne:
                modifierValeurGestionTemperature(v.getId());
                break;
            default:
                break;
        }
    }

    private void modifierMode(RadioButton rb) {
        int etat = Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Chauffage);
        String data = "etat=";

        if (rb == rbAuto) {
            etat = (etat == Donnees.AUTO_MARCHE) ? Donnees.AUTO_MARCHE : Donnees.AUTO_ARRET;
        } else if (rb == rbMarche) {
            etat = Donnees.MARCHE;
        } else {
            etat = Donnees.ARRET;
        }

        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Chauffage, etat);
        modeAEteModifie(etat);

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageChauffage),
                data + String.valueOf(etat));
    }

    private void modifierMode(Button bouton) {
        int etat = Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Chauffage);
        String data = "etat=";

        if (bouton == boutonAuto) {
            etat = (etat == Donnees.AUTO_MARCHE) ? Donnees.AUTO_MARCHE : Donnees.AUTO_ARRET;
        } else if (bouton == boutonMarche) {
            etat = Donnees.MARCHE;
        } else {
            etat = Donnees.ARRET;
        }

        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Chauffage, etat);
        modeAEteModifie(etat);

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageChauffage),
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

    private void modifierGestionTemperature(int idRadioButton) {
        int valeurGestion;
        String data = "gestion_temperature=";

        if (idRadioButton == R.id.radio_button_plage) {
            valeurGestion = Donnees.CONTROLE_PAR_POMPE_FILTRATION;
        } else {
            valeurGestion = Donnees.CONTROLE_POMPE_FILTRATION;
        }

        Donnees.instance().definirControlePompeFiltration(valeurGestion);
        gestionTemperatureAEteModifie();

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageChauffage),
                data + String.valueOf(valeurGestion));
    }

    private void modifierValeurGestionTemperature(int idButon) {
        String data = "";
        int temperature = 0;
        int minTemperature = 0;
        int maxTemperature = 0;

        if ((idButon == R.id.bouton_moins_arret) || (idButon == R.id.bouton_plus_arret)) {
            data = "temperature_arret=";
            temperature = Donnees.instance().obtenirTemperatureArret();
            minTemperature = Donnees.instance().obtenirTemperatureEnclenchement() + 1;
            maxTemperature = 40;
        } else if ((idButon == R.id.bouton_moins_enclenchement) || (idButon == R.id.bouton_plus_enclenchement)) {
            data = "temperature_encl=";
            temperature = Donnees.instance().obtenirTemperatureEnclenchement();
            minTemperature = 0;
            maxTemperature = Donnees.instance().obtenirTemperatureArret() - 1;
        } else if ((idButon == R.id.bouton_moins_consigne) || (idButon == R.id.bouton_plus_consigne)) {
            data = "temperature_consigne=";
            temperature = Donnees.instance().obtenirTemperatureConsigne();
            minTemperature = 0;
            maxTemperature = 40;
        }

        if ((idButon == R.id.bouton_moins_arret) || (idButon == R.id.bouton_moins_enclenchement) || (idButon == R.id.bouton_moins_consigne)) {
            if (minTemperature < temperature) {
                temperature--;
            } else {
                return;
            }
        } else if ((idButon == R.id.bouton_plus_arret) || (idButon == R.id.bouton_plus_enclenchement) || (idButon == R.id.bouton_plus_consigne)) {
            if (temperature < maxTemperature) {
                temperature++;
            } else {
                return;
            }
        }

        if ((idButon == R.id.bouton_moins_arret) || (idButon == R.id.bouton_plus_arret)) {
            Donnees.instance().definirTemperatureArret(temperature);
        } else if ((idButon == R.id.bouton_moins_enclenchement) || (idButon == R.id.bouton_plus_enclenchement)) {
            Donnees.instance().definirTemperatureEnclenchement(temperature);
        } else if ((idButon == R.id.bouton_moins_consigne) || (idButon == R.id.bouton_plus_consigne)) {
            Donnees.instance().definirTemperatureConsigne(temperature);
        }

        valeurGestionTemperatureAEteModifie();

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageChauffage),
                data + String.valueOf(temperature));
    }

    private void gestionTemperatureAEteModifie() {
        radioButtonPlage.setChecked(Donnees.instance().obtenirControlePompeFiltration() == Donnees.CONTROLE_PAR_POMPE_FILTRATION);
        radioButtonConsigne.setChecked(Donnees.instance().obtenirControlePompeFiltration() == Donnees.CONTROLE_POMPE_FILTRATION);

        boutonMoinsArret.setEnabled(radioButtonPlage.isChecked());
        boutonPlusArret.setEnabled(radioButtonPlage.isChecked());
        boutonMoinsEnclenchement.setEnabled(radioButtonPlage.isChecked());
        boutonPlusEnclenchement.setEnabled(radioButtonPlage.isChecked());
        boutonMoinsConsigne.setEnabled(radioButtonConsigne.isChecked());
        boutonPlusConsigne.setEnabled(radioButtonConsigne.isChecked());
    }

    private void valeurGestionTemperatureAEteModifie() {
        texteArret.setText("Température d'arrêt : " + String.valueOf(Donnees.instance().obtenirTemperatureArret()) + " °C");
        texteEnclenchement.setText("Température d'enclenchement : " + String.valueOf(Donnees.instance().obtenirTemperatureEnclenchement()) + " °C");
        texteConsigne.setText("Température de consigne : " + String.valueOf(Donnees.instance().obtenirTemperatureConsigne()) + " °C");
    }

    private void consoAEteModifie(String date, double consoHP, double consoHC) {
        texteConso.setText("Depuis : " + date +
                "\nHeures pleines : " + consoHP + " kWh" +
                "\nHeures creuses : " + consoHC + " kWh");
    }

    private void alarmesAEteModifie() {
        texteAlarmes.setText("Seuil min : " + Donnees.instance().obtenirAlarmeSeuilBas(Donnees.Equipement.Chauffage, null) + "°C\nSeuil max : " + Donnees.instance().obtenirAlarmeSeuilHaut(Donnees.Equipement.Chauffage, null) + "°C");
    }
}
