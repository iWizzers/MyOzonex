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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static android.support.v4.view.MotionEventCompat.getPointerCount;

public class FragmentChauffage extends Fragment implements View.OnClickListener {
    View view = null;

    // Tout orientations
    TextView texteConso;
    RadioButton radioButtonPlage;
    RadioButton radioButtonConsigne;
    Button boutonMoinsArret;
    Button boutonPlusArret;
    Button boutonMoinsEnclenchement;
    Button boutonPlusEnclenchement;
    Button boutonMoinsConsigne;
    Button boutonPlusConsigne;

    TextView texteArret;
    TextView texteEnclenchement;
    TextView texteConsigne;

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
        view = inflater.inflate(R.layout.chauffage_layout, container, false);


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
        radioButtonPlage = (RadioButton) view.findViewById(R.id.radio_button_plage);
        radioButtonConsigne = (RadioButton) view.findViewById(R.id.radio_button_consigne);
        boutonMoinsArret = (Button) view.findViewById(R.id.bouton_moins_arret);
        boutonPlusArret = (Button) view.findViewById(R.id.bouton_plus_arret);
        boutonMoinsEnclenchement = (Button) view.findViewById(R.id.bouton_moins_enclenchement);
        boutonPlusEnclenchement = (Button) view.findViewById(R.id.bouton_plus_enclenchement);
        boutonMoinsConsigne = (Button) view.findViewById(R.id.bouton_moins_consigne);
        boutonPlusConsigne = (Button) view.findViewById(R.id.bouton_plus_consigne);
        texteArret = (TextView)  view.findViewById(R.id.texte_arret);
        texteEnclenchement = (TextView)  view.findViewById(R.id.texte_enclenchement);
        texteConsigne = (TextView)  view.findViewById(R.id.texte_consigne);

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
            modeAEteModifie(Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Chauffage));

            consoAEteModifie(Donnees.instance().obtenirDateDebutConso(Donnees.Equipement.Chauffage),
                    Donnees.instance().obtenirConsoHP(Donnees.Equipement.Chauffage),
                    Donnees.instance().obtenirConsoHC(Donnees.Equipement.Chauffage));

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
        String data = "state=";

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
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.Heater),
                data + String.valueOf(etat));
    }

    private void modifierMode(Button bouton) {
        int etat = Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Chauffage);
        String data = "state=";

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
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.Heater),
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
        String data = "temperature_control=";

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
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.Heater),
                data + String.valueOf(valeurGestion));
    }

    private void modifierValeurGestionTemperature(int idButon) {
        String data = "";
        int temperature = 0;
        int minTemperature = 0;
        int maxTemperature = 0;

        if ((idButon == R.id.bouton_moins_arret) || (idButon == R.id.bouton_plus_arret)) {
            data = "stop_temperature=";
            temperature = Donnees.instance().obtenirTemperatureArret();
            minTemperature = Donnees.instance().obtenirTemperatureEnclenchement() + 1;
            maxTemperature = 40;
        } else if ((idButon == R.id.bouton_moins_enclenchement) || (idButon == R.id.bouton_plus_enclenchement)) {
            data = "start_temperature=";
            temperature = Donnees.instance().obtenirTemperatureEnclenchement();
            minTemperature = 0;
            maxTemperature = Donnees.instance().obtenirTemperatureArret() - 1;
        } else if ((idButon == R.id.bouton_moins_consigne) || (idButon == R.id.bouton_plus_consigne)) {
            data = "setpoint_temperature=";
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
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.Heater),
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
}
