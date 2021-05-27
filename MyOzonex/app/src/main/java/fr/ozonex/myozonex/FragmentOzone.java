package fr.ozonex.myozonex;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class FragmentOzone extends Fragment implements View.OnClickListener {
    View view = null;

    ScrollView scrollView;

    LinearLayout viewBoutonMarche;
    ImageView imageBoutonMarche;
    TextView labelBoutonMarche;
    LinearLayout viewBoutonArret;
    ImageView imageBoutonArret;
    TextView labelBoutonArret;
    LinearLayout viewBoutonAuto;
    ImageView imageBoutonAuto;
    TextView labelBoutonAuto;

    LinearLayout viewInstallation;
    boolean spinnerIsOpen = false;
    Spinner spinnerTypeOzone;
    Spinner spinnerNombreVentilateurs;
    TextView labelTempoOzone;

    LinearLayout viewConsommations;
    TextView labelConsommations;

    LinearLayout viewGestionErreurs;
    TextView labelGestionErreurs;

    int typeModification = 0;
    LinearLayout viewQuestion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ozone_layout, container, false);

        scrollView = view.findViewById(R.id.scrollview);

        viewBoutonMarche = view.findViewById(R.id.layout_mode_marche);
        imageBoutonMarche = view.findViewById(R.id.image_mode_marche);
        labelBoutonMarche = view.findViewById(R.id.texte_mode_marche);

        viewBoutonArret = view.findViewById(R.id.layout_mode_arret);
        imageBoutonArret = view.findViewById(R.id.image_mode_arret);
        labelBoutonArret = view.findViewById(R.id.texte_mode_arret);

        viewBoutonAuto = view.findViewById(R.id.layout_mode_auto);
        imageBoutonAuto = view.findViewById(R.id.image_mode_auto);
        labelBoutonAuto = view.findViewById(R.id.texte_mode_auto);

        viewInstallation = view.findViewById(R.id.layout_installation);
        spinnerTypeOzone = view.findViewById(R.id.spinner_type_ozone);
        final ArrayAdapter adapterTypeOzone = ArrayAdapter.createFromResource(
                MainActivity.instance(),
                R.array.valeurs_type_ozone,
                R.layout.color_spinner_layout
        );
        adapterTypeOzone.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerTypeOzone.setAdapter(adapterTypeOzone);
        spinnerTypeOzone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spinnerIsOpen = true;
                return false;
            }
        });
        spinnerTypeOzone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerIsOpen) {
                    Donnees.instance().definirTypeOzone(adapterView.getSelectedItem().toString().equals("OXYGÈNE") ? 1 : 0);
                    installationAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "type_ozone=" + adapterView.getSelectedItem().toString(), false);
                    spinnerIsOpen = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerNombreVentilateurs = view.findViewById(R.id.spinner_nombre_ventilateurs);
        final ArrayAdapter adapterNombreVentilateurs = ArrayAdapter.createFromResource(
                MainActivity.instance(),
                R.array.valeurs_nombre_ventilateurs_ozone,
                R.layout.color_spinner_layout
        );
        adapterNombreVentilateurs.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerNombreVentilateurs.setAdapter(adapterNombreVentilateurs);
        spinnerNombreVentilateurs.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spinnerIsOpen = true;
                return false;
            }
        });
        spinnerNombreVentilateurs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerIsOpen) {
                    Donnees.instance().definirNombreVentilateursOzone(Integer.parseInt(adapterView.getSelectedItem().toString()));
                    installationAEteModifie();
                    gestionErreursAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "nombre_ventilateurs=" + Donnees.instance().obtenirNombreVentilateursOzone(), false);
                    spinnerIsOpen = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        labelTempoOzone = view.findViewById(R.id.texte_tempo_ozone);
        labelTempoOzone.setOnClickListener(this);

        viewConsommations = view.findViewById(R.id.layout_consommations);
        labelConsommations = view.findViewById(R.id.texte_consommations);

        viewGestionErreurs = view.findViewById(R.id.layout_gestion_erreurs);
        labelGestionErreurs = view.findViewById(R.id.texte_gestion_erreurs);

        viewBoutonMarche.setOnClickListener(this);
        viewBoutonArret.setOnClickListener(this);
        viewBoutonAuto.setOnClickListener(this);

        viewQuestion = view.findViewById(R.id.layout_question);
        ImageButton boutonAnnulerQuestion = view.findViewById(R.id.bouton_annuler_question);
        ImageButton boutonValiderQuestion = view.findViewById(R.id.bouton_valider_question);
        boutonAnnulerQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficherQuestion(false, "");
            }
        });
        boutonValiderQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validerQuestion();
            }
        });

        update();
        
        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            modeAEteModifie();

            viewInstallation.setVisibility(Donnees.instance().obtenirTypeAppareil() == Donnees.MYOZONEXMINI ? View.VISIBLE : View.GONE);
            installationAEteModifie();

            viewConsommations.setVisibility(Donnees.instance().obtenirTypeAppareil() == Donnees.MYOZONEX ? View.VISIBLE : View.GONE);
            labelConsommations.setText("Date : " + Donnees.instance().obtenirDateConso(Donnees.Equipement.Ozone) + "\nHeures pleines : " + Donnees.instance().obtenirConsoHP(Donnees.Equipement.Ozone) + " kWh \nHeures creuses : " + Donnees.instance().obtenirConsoHC(Donnees.Equipement.Ozone) + " kWh");

            gestionErreursAEteModifie();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_mode_marche:
            case R.id.layout_mode_arret:
            case R.id.layout_mode_auto:
                modifierMode((LinearLayout) v.findViewById(v.getId()));
                break;
            case R.id.texte_tempo_ozone:
                typeModification = 0;
                afficherQuestion(true, "Veuillez entrer la temporisation de démarrage (de 0 à 240 secondes)");
                break;
            default:
                break;
        }
    }

    private void afficherQuestion(Boolean visible, String question) {
        scrollView.setVisibility(!visible ? View.VISIBLE : View.GONE);
        viewQuestion.setVisibility(visible ? View.VISIBLE : View.GONE);

        EditText reponse = view.findViewById(R.id.edit_question);
        reponse.setText("");
        TextView textView = view.findViewById(R.id.texte_question);
        textView.setText(question);
    }

    private void validerQuestion() {
        EditText reponse = view.findViewById(R.id.edit_question);
        double valeur = Double.parseDouble(reponse.getText().toString());

        switch (typeModification) {
            case 0:
                if ((0 <= valeur) && (valeur <= 240)) {
                    Donnees.instance().definirTempoOzone((int) valeur);
                    installationAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "tempo_ozone=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            default:
                break;
        }
    }

    private void modifierMode(LinearLayout sender) {
        if (Donnees.instance().obtenirActiviteIHM()) {
            int etat = Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Ozone);

            if (sender == viewBoutonAuto) {
                etat = (etat == Donnees.AUTO_MARCHE) ? Donnees.AUTO_MARCHE : Donnees.AUTO_ARRET;
            } else if (sender == viewBoutonMarche) {
                etat = Donnees.MARCHE;
            } else {
                etat = Donnees.ARRET;
            }

            Donnees.instance().definirEtatEquipement(Donnees.Equipement.Ozone, etat);
            modeAEteModifie();
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "etat=" + etat, false);

            if (etat != Donnees.ARRET) {
                Donnees.instance().definirErreurOzone(0);
                gestionErreursAEteModifie();
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "erreurs_ozone=" + etat, false);
            }
        }
    }

    private void modeAEteModifie() {
        int mode = Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Ozone);

        viewBoutonMarche.setBackgroundTintList(ColorStateList.valueOf(mode == Donnees.MARCHE ? Color.rgb(255, 83, 13) : Color.rgb(245, 245, 220)));
        labelBoutonMarche.setTextColor(mode == Donnees.MARCHE ? Color.rgb(40, 40, 40) : Color.rgb(128, 128, 128));
        imageBoutonMarche.setColorFilter(mode == Donnees.MARCHE ? Color.rgb(40, 40, 40) : Color.rgb(128, 128, 128));

        viewBoutonArret.setBackgroundColor(mode == Donnees.ARRET ? Color.rgb(255, 83, 13) : Color.rgb(245, 245, 220));
        labelBoutonArret.setTextColor(mode == Donnees.ARRET ? Color.rgb(40, 40, 40) : Color.rgb(128, 128, 128));
        imageBoutonArret.setColorFilter(mode == Donnees.ARRET ? Color.rgb(40, 40, 40) : Color.rgb(128, 128, 128));

        viewBoutonAuto.setBackgroundTintList(ColorStateList.valueOf(mode > Donnees.AUTO ? Color.rgb(0, 174, 239) : Color.rgb(245, 245, 220)));
        labelBoutonAuto.setTextColor(mode > Donnees.AUTO ? Color.rgb(40, 40, 40) : Color.rgb(128, 128, 128));
        imageBoutonAuto.setColorFilter(mode > Donnees.AUTO ? Color.rgb(40, 40, 40) : Color.rgb(128, 128, 128));
    }

    private void installationAEteModifie() {
        spinnerTypeOzone.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        spinnerNombreVentilateurs.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelTempoOzone.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());

        if (!spinnerIsOpen) {
            spinnerTypeOzone.setSelection(Donnees.instance().obtenirTypeOzone());
            spinnerNombreVentilateurs.setSelection(Donnees.instance().obtenirNombreVentilateursOzone());
        }

        labelTempoOzone.setText("Temporisation : " + Donnees.instance().obtenirTempoOzone() + " secondes");
    }

    private void gestionErreursAEteModifie() {
        viewGestionErreurs.setVisibility(Donnees.instance().obtenirTypeAppareil() == Donnees.MYOZONEXMINI ? View.VISIBLE : View.GONE);

        String texte = "";

        if (Donnees.instance().obtenirNombreVentilateursOzone() > 0) {
            texte += "Ventilateur 1 : " + ((Donnees.instance().obtenirErreurOzone() & Donnees.ERR_FAN_1) == Donnees.ERR_FAN_1 ? "<font color=\"#FF0000\">ERREUR</font>" : "<font color=\"#00FF00\">OK</font>");
            texte += "<br />Vitesse de rotation : " + Donnees.instance().obtenirVitesseFan1Ozone() + " rpm";
        }

        if (Donnees.instance().obtenirNombreVentilateursOzone() > 1) {
            texte += "<br /><br />Ventilateur 2 : " + ((Donnees.instance().obtenirErreurOzone() & Donnees.ERR_FAN_2) == Donnees.ERR_FAN_2 ? "<font color=\"#FF0000\">ERREUR</font>" : "<font color=\"#00FF00\">OK</font>");
            texte += "<br />Vitesse de rotation : " + Donnees.instance().obtenirVitesseFan2Ozone() + " rpm";
        }

        texte += Donnees.instance().obtenirNombreVentilateursOzone() > 0 ? "<br /><br />" : "";
        texte += "Consommation courant : " + ((Donnees.instance().obtenirErreurOzone() & Donnees.ERR_OVER_CURRENT) == Donnees.ERR_OVER_CURRENT ? "<font color=\"#FF0000\">ERREUR</font>" : "<font color=\"#00FF00\">OK</font>") + (Donnees.instance().obtenirCodeInstallateur() ? " (" + Donnees.instance().obtenirCourantAlimOzone() + " mA)" : "");
        texte += Donnees.instance().obtenirCodeInstallateur() ? "<br />Tension d'alimentation : " + Donnees.instance().obtenirTensionAlimOzone() + " V" : "";

        texte += "<br /><br />Consommation générateur : " + ((Donnees.instance().obtenirErreurOzone() & Donnees.ERR_OUT_CC) == Donnees.ERR_OUT_CC ? "<font color=\"#FF0000\">ERREUR</font>" : "<font color=\"#00FF00\">OK</font>");
        texte += "<br />Présence générateur : " + ((Donnees.instance().obtenirErreurOzone() & Donnees.ERR_OUT_OPEN) == Donnees.ERR_OUT_OPEN ? "<font color=\"#FF0000\">ERREUR</font>" : "<font color=\"#00FF00\">OK</font>") + (Donnees.instance().obtenirCodeInstallateur() ? " (" + Donnees.instance().obtenirHauteTensionAlimOzone() + " Vrms)" : "");

        labelGestionErreurs.setText(Html.fromHtml(texte));
    }
}
