package fr.ozonex.myozonex;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class FragmentBassin extends Fragment implements View.OnClickListener {
    View view = null;

    ScrollView scrollView;

    TextView labelDonneesBassin;
    TextView labelTempoDemarrage;

    boolean spinnerIsOpen = false;
    Spinner spinnerTypeRefoulement;
    Spinner spinnerTypeRegulation;
    Switch switchEtatRegulations;
    LinearLayout viewTempsSecuriteInjection;
    SeekBar sliderTempsSecuriteInjection;
    TextView labelTempsSecuriteInjection;
    LinearLayout viewHystInjectionPh;
    SeekBar sliderHystInjectionPh;
    TextView labelHystInjectionPh;
    LinearLayout viewHystInjectionOrp;
    SeekBar sliderHystInjectionOrp;
    TextView labelHystInjectionOrp;
    LinearLayout viewHystInjectionAmpero;
    SeekBar sliderHystInjectionAmpero;
    TextView labelHystInjectionAmpero;

    int typeModification = 0;
    LinearLayout viewQuestion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bassin_layout, container, false);

        scrollView = view.findViewById(R.id.scrollview);

        labelDonneesBassin = view.findViewById(R.id.texte_donnees_bassin);
        labelDonneesBassin.setOnClickListener(this);
        labelTempoDemarrage = view.findViewById(R.id.texte_tempo_demarrage);
        labelTempoDemarrage.setOnClickListener(this);

        spinnerTypeRefoulement = view.findViewById(R.id.spinner_type_refoulement);
        final ArrayAdapter adapterTypeRefoulement = ArrayAdapter.createFromResource(
                MainActivity.instance(),
                R.array.valeurs_type_refoulement,
                R.layout.color_spinner_layout
        );
        adapterTypeRefoulement.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerTypeRefoulement.setAdapter(adapterTypeRefoulement);
        spinnerTypeRefoulement.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spinnerIsOpen = true;
                return false;
            }
        });
        spinnerTypeRefoulement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerIsOpen) {
                    Donnees.instance().definirTypeRefoulement(adapterView.getSelectedItem().toString().equals("MULTIPLE") ? 1 : 0);
                    gestionRegulationsAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "type_refoulement=" + adapterView.getSelectedItem().toString(), false);
                    spinnerIsOpen = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerTypeRegulation = view.findViewById(R.id.spinner_type_regulation);
        final ArrayAdapter adapterTypeRegulation = ArrayAdapter.createFromResource(
                MainActivity.instance(),
                R.array.valeurs_type_regulation,
                R.layout.color_spinner_layout
        );
        adapterTypeRegulation.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerTypeRegulation.setAdapter(adapterTypeRegulation);
        spinnerTypeRegulation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spinnerIsOpen = true;
                return false;
            }
        });
        spinnerTypeRegulation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerIsOpen) {
                    Donnees.instance().definirTypeRegulation(adapterView.getSelectedItem().toString().equals("TOR") ? 0 : 1);
                    gestionRegulationsAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "type_regulation=" + adapterView.getSelectedItem().toString(), false);
                    spinnerIsOpen = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        switchEtatRegulations = view.findViewById(R.id.switch_etat_regulations);
        switchEtatRegulations.setOnClickListener(this);
        viewTempsSecuriteInjection = view.findViewById(R.id.layout_temps_securite_injection);
        sliderTempsSecuriteInjection = view.findViewById(R.id.seekbar_temps_securite_injection);
        labelTempsSecuriteInjection = view.findViewById(R.id.texte_temps_securite_injection);
        viewHystInjectionPh = view.findViewById(R.id.layout_hysteresis_injection_ph);
        sliderHystInjectionPh = view.findViewById(R.id.seekbar_hysteresis_injection_ph);
        labelHystInjectionPh = view.findViewById(R.id.texte_hysteresis_injection_ph);
        viewHystInjectionOrp = view.findViewById(R.id.layout_hysteresis_injection_chlore_orp);
        sliderHystInjectionOrp = view.findViewById(R.id.seekbar_hysteresis_injection_chlore_orp);
        labelHystInjectionOrp = view.findViewById(R.id.texte_hysteresis_injection_chlore_orp);
        viewHystInjectionAmpero = view.findViewById(R.id.layout_hysteresis_injection_chlore_ampero);
        sliderHystInjectionAmpero = view.findViewById(R.id.seekbar_hysteresis_injection_chlore_ampero);
        labelHystInjectionAmpero = view.findViewById(R.id.texte_hysteresis_injection_chlore_ampero);
        sliderTempsSecuriteInjection.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                labelTempsSecuriteInjection.setText("Temps sécurité injection : " + progress + " min");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirTempsSecuriteInjection(seekBar.getProgress());
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "temps_securite_injection=" + Donnees.instance().obtenirTempsSecuriteInjection(), false);
            }
        });
        sliderHystInjectionPh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                labelHystInjectionPh.setText("Hystérésis injection ph : " + String.format("%.2f", progress / 100.0));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirHystInjectionPh(seekBar.getProgress() / 100.0);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "hyst_injection_ph=" + Donnees.instance().obtenirHystInjectionPh(), false);
            }
        });
        sliderHystInjectionOrp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                labelHystInjectionOrp.setText("Hystérésis injection chlore (ORP) : " + progress + " mV");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirHystInjectionChloreOrp(seekBar.getProgress());
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "hyst_injection_orp=" + Donnees.instance().obtenirHystInjectionChloreOrp(), false);
            }
        });
        sliderHystInjectionAmpero.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                labelHystInjectionAmpero.setText("Hystérésis injection chlore (ampéro) : " + String.format("%.2f", progress / 100.0) + " ppm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirHystInjectionChloreAmpero(seekBar.getProgress() / 100.0);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "hyst_injection_ampero=" + Donnees.instance().obtenirHystInjectionChloreAmpero(), false);
            }
        });

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
            donneesBassinAEteModifie();

            switchEtatRegulations.setChecked(Donnees.instance().obtenirEtatRegulations());
            gestionRegulationsAEteModifie();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.texte_donnees_bassin:
                typeModification = 0;
                afficherQuestion(true, "Veuillez entrer le volume du bassin");
                break;
            case R.id.texte_tempo_demarrage:
                typeModification = 1;
                afficherQuestion(true, "Veuillez entrer la temporisation de démarrage des équipements (en minutes)");
                break;
            case R.id.switch_etat_regulations:
                Donnees.instance().definirEtatRegulations(((Switch) v.findViewById(v.getId())).isChecked());
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "etat_regulations=" + (Donnees.instance().obtenirEtatRegulations() ? 1 : 0), false);
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
                if (valeur > 0) {
                    Donnees.instance().definirVolumeBassin(valeur);
                    donneesBassinAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "volume=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 1:
                if ((2 <= valeur) && (valeur <= 20)) {
                    Donnees.instance().definirTempoDemarrage((int) valeur);
                    donneesBassinAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "temporisation_demarrage=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            default:
                break;
        }
    }

    private void donneesBassinAEteModifie() {
        labelDonneesBassin.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelDonneesBassin.setText("Volume du bassin : " + Donnees.instance().obtenirVolumeBassin() + " m³");

        labelTempoDemarrage.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelTempoDemarrage.setText("Temporisation de démarrage : " + Donnees.instance().obtenirTempoDemarrage() + " minute(s)");
    }

    private void gestionRegulationsAEteModifie() {
        spinnerTypeRefoulement.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        spinnerTypeRegulation.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());

        switchEtatRegulations.setEnabled(Donnees.instance().obtenirActiviteIHM());

        sliderTempsSecuriteInjection.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        sliderHystInjectionPh.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        sliderHystInjectionOrp.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        sliderHystInjectionAmpero.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());

        viewTempsSecuriteInjection.setVisibility(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR ? View.GONE : View.VISIBLE);
        viewHystInjectionPh.setVisibility((Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR) || !Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) ? View.GONE : View.VISIBLE);
        viewHystInjectionOrp.setVisibility((Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR) || !Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) ? View.GONE : View.VISIBLE);
        viewHystInjectionAmpero.setVisibility((Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR) || !Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ampero) ? View.GONE : View.VISIBLE);

        if (!spinnerIsOpen) {
            spinnerTypeRefoulement.setSelection(Donnees.instance().obtenirTypeRefoulement());
            spinnerTypeRegulation.setSelection(Donnees.instance().obtenirTypeRegulation());
        }

        if (!sliderTempsSecuriteInjection.isPressed()) {
            sliderTempsSecuriteInjection.setProgress(Donnees.instance().obtenirTempsSecuriteInjection());
        }

        if (!sliderHystInjectionPh.isPressed()) {
            sliderHystInjectionPh.setProgress((int)(Donnees.instance().obtenirHystInjectionPh() * 100));
        }

        if (!sliderHystInjectionOrp.isPressed()) {
            sliderHystInjectionOrp.setProgress(Donnees.instance().obtenirHystInjectionChloreOrp());
        }

        if (!sliderHystInjectionAmpero.isPressed()) {
            sliderHystInjectionAmpero.setProgress((int)(Donnees.instance().obtenirHystInjectionChloreAmpero() * 100));
        }
    }
}
