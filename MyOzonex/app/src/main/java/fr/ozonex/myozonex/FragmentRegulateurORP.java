package fr.ozonex.myozonex;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FragmentRegulateurORP extends Fragment implements View.OnClickListener {
    View view = null;

    Boolean sliderORPIsTracking = false;
    Boolean sliderAmperoIsTracking = false;

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

    TextView labelConsommations;
    TextView labelConsoProduitInjecte;

    TextView labelDureeCycle;
    TextView labelDureeInjectionMin;
    TextView labelMultDiff;
    TextView labelDureeInjection;
    TextView labelTempsReponse;
    TextView labelTempsJourMax;

    Switch switchSurchloration;
    LinearLayout viewJourSurchloration;
    SeekBar sliderJourSurchloration;
    LinearLayout viewFreqSurchloration;
    TextView labelFreqSurchloration;
    SeekBar sliderFreqSurchloration;
    LinearLayout viewMvAjouteSurchloration;
    TextView labelMvAjouteSurchloration;
    SeekBar sliderMvAjouteSurchloration;
    LinearLayout viewProchaineSurchloration;
    TextView labelProchaineSurchloration;
    Button boutonRAZSurchloration;

    TextView labelDebit;
    TextView labelPointConsigneRedox;
    TextView labelHysteresisRedox;
    TextView labelPointConsigneAmpero;
    TextView labelHysteresisAmpero;

    LinearLayout viewAlarmesORP;
    SeekBar sliderMinAlarmeORP;
    TextView labelMinAlarmeORP;
    SeekBar sliderMaxAlarmeORP;
    TextView labelMaxAlarmeORP;
    LinearLayout viewAlarmesAmpero;
    SeekBar sliderMinAlarmeAmpero;
    TextView labelMinAlarmeAmpero;
    SeekBar sliderMaxAlarmeAmpero;
    TextView labelMaxAlarmeAmpero;

    int typeModification = 0;
    LinearLayout viewQuestion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.regulateur_orp_layout, container, false);

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

        labelConsommations = view.findViewById(R.id.texte_consommations);
        labelConsoProduitInjecte = view.findViewById(R.id.texte_conso_produit_injecte);

        labelDureeCycle = view.findViewById(R.id.texte_duree_cycle);
        labelDureeInjectionMin = view.findViewById(R.id.texte_duree_injection_min);
        labelMultDiff = view.findViewById(R.id.texte_mult_diff);
        labelDureeInjection = view.findViewById(R.id.texte_duree_injection);
        labelTempsReponse = view.findViewById(R.id.texte_temps_reponse);
        labelTempsJourMax = view.findViewById(R.id.texte_temps_jour_max);

        switchSurchloration = view.findViewById(R.id.switch_surchloration);
        viewJourSurchloration = view.findViewById(R.id.layout_jour_surchloration);
        sliderJourSurchloration = view.findViewById(R.id.seekbar_jour_surchloration);
        viewFreqSurchloration = view.findViewById(R.id.layout_freq_surchloration);
        labelFreqSurchloration = view.findViewById(R.id.texte_freq_surchloration);
        sliderFreqSurchloration = view.findViewById(R.id.seekbar_freq_surchloration);
        viewMvAjouteSurchloration = view.findViewById(R.id.layout_mv_ajoute_surchloration);
        labelMvAjouteSurchloration = view.findViewById(R.id.texte_mv_ajoute_surchloration);
        sliderMvAjouteSurchloration = view.findViewById(R.id.seekbar_mv_ajoute_surchloration);
        viewProchaineSurchloration = view.findViewById(R.id.layout_prochaine_surchloration);
        labelProchaineSurchloration = view.findViewById(R.id.texte_prochaine_surchloration);
        boutonRAZSurchloration = view.findViewById(R.id.bouton_raz_surchloration);

        labelDebit = view.findViewById(R.id.texte_debit);
        labelPointConsigneRedox = view.findViewById(R.id.texte_point_consigne_redox);
        labelHysteresisRedox = view.findViewById(R.id.texte_hysteresis_redox);
        labelPointConsigneAmpero = view.findViewById(R.id.texte_point_consigne_ampero);
        labelHysteresisAmpero = view.findViewById(R.id.texte_hysteresis_ampero);

        viewAlarmesORP = view.findViewById(R.id.layout_alarmes_orp);
        sliderMinAlarmeORP = view.findViewById(R.id.seekbar_min_alarme_orp);
        labelMinAlarmeORP = view.findViewById(R.id.texte_min_alarme_orp);
        sliderMaxAlarmeORP = view.findViewById(R.id.seekbar_max_alarme_orp);
        labelMaxAlarmeORP = view.findViewById(R.id.texte_max_alarme_orp);
        viewAlarmesAmpero = view.findViewById(R.id.layout_alarmes_ampero);
        sliderMinAlarmeAmpero = view.findViewById(R.id.seekbar_min_alarme_ampero);
        labelMinAlarmeAmpero = view.findViewById(R.id.texte_min_alarme_ampero);
        sliderMaxAlarmeAmpero = view.findViewById(R.id.seekbar_max_alarme_ampero);
        labelMaxAlarmeAmpero = view.findViewById(R.id.texte_max_alarme_ampero);

        viewBoutonMarche.setOnClickListener(this);
        viewBoutonArret.setOnClickListener(this);
        viewBoutonAuto.setOnClickListener(this);

        labelConsommations.setOnClickListener(this);

        labelDureeCycle.setOnClickListener(this);
        labelDureeInjectionMin.setOnClickListener(this);
        labelMultDiff.setOnClickListener(this);
        labelDureeInjection.setOnClickListener(this);
        labelTempsReponse.setOnClickListener(this);
        labelTempsJourMax.setOnClickListener(this);

        switchSurchloration.setOnClickListener(this);
        sliderJourSurchloration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                day = day == Calendar.MONDAY ? 1 : day == Calendar.TUESDAY ? 2 : day == Calendar.WEDNESDAY ? 3 : day == Calendar.THURSDAY ? 4 : day == Calendar.FRIDAY ? 5 : day == Calendar.SATURDAY ? 6 : 7;
                int valeur = Integer.parseInt(Donnees.instance().obtenirFrequenceSurchloration().split(" ")[0]);
                String freq = Donnees.instance().obtenirFrequenceSurchloration().split(" ")[1];
                labelProchaineSurchloration.setText("Prochaine dans " + (7 * (freq.equals("mois") ? 4 : 1) * valeur - (day - progress)) + " jours");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                day = day == Calendar.MONDAY ? 1 : day == Calendar.TUESDAY ? 2 : day == Calendar.WEDNESDAY ? 3 : day == Calendar.THURSDAY ? 4 : day == Calendar.FRIDAY ? 5 : day == Calendar.SATURDAY ? 6 : 7;
                int valeur = Integer.parseInt(Donnees.instance().obtenirFrequenceSurchloration().split(" ")[0]);
                String freq = Donnees.instance().obtenirFrequenceSurchloration().split(" ")[1];
                Donnees.instance().definirJourSurchloration(seekBar.getProgress());
                Donnees.instance().definirProchaineSurchloration(7 * (freq.equals("mois") ? 4 : 1) * valeur - (day - seekBar.getProgress()));
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "jour=" + Donnees.instance().obtenirJourSurchloration(), false);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "prochaine_surchloration=" + Donnees.instance().obtenirProchaineSurchloration(), false);
            }
        });
        sliderFreqSurchloration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                day = day == Calendar.MONDAY ? 1 : day == Calendar.TUESDAY ? 2 : day == Calendar.WEDNESDAY ? 3 : day == Calendar.THURSDAY ? 4 : day == Calendar.FRIDAY ? 5 : day == Calendar.SATURDAY ? 6 : 7;
                labelFreqSurchloration.setText("Fréquence : " + (progress - 3 > 0 ? progress - 3 : progress) + (progress - 3 > 0 ? " mois" : " semaine(s)"));
                labelProchaineSurchloration.setText("Prochaine dans " + (7 * (progress - 3 > 0 ? 4 : 1) * (progress - 3 > 0 ? progress - 3 : progress) - (day - Donnees.instance().obtenirJourSurchloration())) + " jours");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                day = day == Calendar.MONDAY ? 1 : day == Calendar.TUESDAY ? 2 : day == Calendar.WEDNESDAY ? 3 : day == Calendar.THURSDAY ? 4 : day == Calendar.FRIDAY ? 5 : day == Calendar.SATURDAY ? 6 : 7;
                Donnees.instance().definirFrequenceSurchloration((seekBar.getProgress() - 3 > 0 ? seekBar.getProgress() - 3 : seekBar.getProgress()) + (seekBar.getProgress() - 3 > 0 ? " mois" : " semaine(s)"));
                int valeur = Integer.parseInt(Donnees.instance().obtenirFrequenceSurchloration().split(" ")[0]);
                String freq = Donnees.instance().obtenirFrequenceSurchloration().split(" ")[1];
                Donnees.instance().definirProchaineSurchloration(7 * (freq.equals("mois") ? 4 : 1) * valeur - (day - Donnees.instance().obtenirJourSurchloration()));
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "frequence=" + Donnees.instance().obtenirFrequenceSurchloration(), false);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "prochaine_surchloration=" + Donnees.instance().obtenirProchaineSurchloration(), false);
            }
        });
        sliderMvAjouteSurchloration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                labelMvAjouteSurchloration.setText((progress * 10) + " mV");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirValeurSurchloration(seekBar.getProgress() * 10);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "mv_ajoute=" + Donnees.instance().obtenirValeurSurchloration(), false);
            }
        });
        boutonRAZSurchloration.setOnClickListener(this);

        labelDebit.setOnClickListener(this);
        labelPointConsigneRedox.setOnClickListener(this);
        labelHysteresisRedox.setOnClickListener(this);
        labelPointConsigneAmpero.setOnClickListener(this);
        labelHysteresisAmpero.setOnClickListener(this);

        sliderMinAlarmeORP.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Boolean change = false;
                int minVal = progress;
                int maxVal = Integer.parseInt(String.valueOf(sliderMaxAlarmeORP.getProgress()));

                if (sliderORPIsTracking) {
                    if (minVal > maxVal - 1) {
                        change = true;
                        minVal = maxVal - 1;
                    }
                }

                if (change) {
                    sliderMinAlarmeORP.setProgress(minVal);
                } else {
                    labelMinAlarmeORP.setText(minVal + " mV");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                sliderORPIsTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirAlarmeSeuilBas(Donnees.Equipement.Orp, seekBar.getProgress(), Donnees.Capteur.Redox);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "alarme_seuil_bas_orp=" + Donnees.instance().obtenirAlarmeSeuilBas(Donnees.Equipement.Orp, Donnees.Capteur.Redox), false);
                sliderORPIsTracking = false;
            }
        });
        sliderMaxAlarmeORP.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Boolean change = false;
                int minVal = Integer.parseInt(String.valueOf(sliderMinAlarmeORP.getProgress()));
                int maxVal = progress;

                if (sliderORPIsTracking) {
                    if (maxVal < minVal + 1) {
                        change = true;
                        maxVal = minVal + 1;
                    }
                }

                if (change) {
                    sliderMaxAlarmeORP.setProgress(maxVal);
                } else {
                    labelMaxAlarmeORP.setText(maxVal + " mV");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                sliderORPIsTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirAlarmeSeuilHaut(Donnees.Equipement.Orp, seekBar.getProgress(), Donnees.Capteur.Redox);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "alarme_seuil_haut_orp=" + Donnees.instance().obtenirAlarmeSeuilHaut(Donnees.Equipement.Orp, Donnees.Capteur.Redox), false);
                sliderORPIsTracking = false;
            }
        });
        sliderMinAlarmeAmpero.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Boolean change = false;
                int minVal = progress;
                int maxVal = Integer.parseInt(String.valueOf(sliderMaxAlarmeAmpero.getProgress()));

                if (sliderAmperoIsTracking) {
                    if (minVal > maxVal - 1) {
                        change = true;
                        minVal = maxVal - 1;
                    }
                }

                if (change) {
                    sliderMinAlarmeAmpero.setProgress(minVal);
                } else {
                    labelMinAlarmeAmpero.setText((minVal / 10.0) + " ppm");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                sliderAmperoIsTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirAlarmeSeuilBas(Donnees.Equipement.Orp, seekBar.getProgress() / 10.0, Donnees.Capteur.Ampero);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "alarme_seuil_bas_ampero=" + Donnees.instance().obtenirAlarmeSeuilBas(Donnees.Equipement.Orp, Donnees.Capteur.Ampero), false);
                sliderAmperoIsTracking = false;
            }
        });
        sliderMaxAlarmeAmpero.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Boolean change = false;
                int minVal = Integer.parseInt(String.valueOf(sliderMinAlarmeAmpero.getProgress()));
                int maxVal = progress;

                if (sliderAmperoIsTracking) {
                    if (maxVal < minVal + 1) {
                        change = true;
                        maxVal = minVal + 1;
                    }
                }

                if (change) {
                    sliderMaxAlarmeAmpero.setProgress(maxVal);
                } else {
                    labelMaxAlarmeAmpero.setText((maxVal / 10.0) + " ppm");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                sliderAmperoIsTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirAlarmeSeuilHaut(Donnees.Equipement.Orp, seekBar.getProgress() / 10.0, Donnees.Capteur.Ampero);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "alarme_seuil_haut_ampero=" + Donnees.instance().obtenirAlarmeSeuilHaut(Donnees.Equipement.Orp, Donnees.Capteur.Ampero), false);
                sliderAmperoIsTracking = false;
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
            modeAEteModifie();

            consoAEteModifie();

            asservissementAEteModifie();

            switchSurchloration.setChecked(Donnees.instance().obtenirEtat(Donnees.Equipement.Orp));
            surchlorationAEteModifie();
                    
            installationAEteModifie();

            alarmesAEteModifie();
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
            case R.id.texte_debit:
                typeModification = 0;
                afficherQuestion(true, "Veuillez entrer le débit (en L/h)");
                break;
            case R.id.texte_consommations:
                typeModification = 1;
                afficherQuestion(true, "Veuillez entrer le volume du bidon (en Litres)");
                break;
            case R.id.texte_duree_cycle:
                typeModification = 2;
                afficherQuestion(true, "Veuillez entrer la durée du cycle (de 3 à 360 secondes)");
                break;
            case R.id.texte_duree_injection_min:
                typeModification = 3;
                afficherQuestion(true, "Veuillez entrer la durée d'injection minimum (de 1 à " + (Donnees.instance().obtenirDureeCycle(Donnees.Equipement.Orp) - 2) + " secondes)");
                break;
            case R.id.texte_mult_diff:
                typeModification = 4;
                afficherQuestion(true, "Veuillez entrer le multiplicateur de différence (de 1 à 50)");
                break;
            case R.id.texte_duree_injection:
                typeModification = 5;
                afficherQuestion(true, "Veuillez entrer la durée d'injection (de 0.1 à 30 minutes)");
                break;
            case R.id.texte_temps_reponse:
                typeModification = 6;
                afficherQuestion(true, "Veuillez entrer le temps de réponse après injection (de 1 à 60 minutes)");
                break;
            case R.id.texte_temps_jour_max:
                typeModification = 7;
                afficherQuestion(true, "Veuillez entrer le temps d'injection journalier maximum (de 0 à 1380 minutes)");
                break;
            case R.id.texte_point_consigne_redox:
                typeModification = 8;
                afficherQuestion(true, "Veuillez entrer le point de consigne Redox (en mV)");
                break;
            case R.id.texte_hysteresis_redox:
                typeModification = 9;
                afficherQuestion(true, "Veuillez entrer l'hystérésis Redox (en mV)");
                break;
            case R.id.texte_point_consigne_ampero:
                typeModification = 10;
                afficherQuestion(true, "Veuillez entrer le point de consigne Ampéro (en ppm)");
                break;
            case R.id.texte_hysteresis_ampero:
                typeModification = 11;
                afficherQuestion(true, "Veuillez entrer l'hystérésis Ampéro (en ppm)");
                break;
            case R.id.switch_surchloration:
                switchSurchlorationValueChanged((Switch) v.findViewById(v.getId()));
                break;
            case R.id.bouton_raz_surchloration:
                modifierMode(viewBoutonArret);

                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                day = day == Calendar.MONDAY ? 1 : day == Calendar.TUESDAY ? 2 : day == Calendar.WEDNESDAY ? 3 : day == Calendar.THURSDAY ? 4 : day == Calendar.FRIDAY ? 5 : day == Calendar.SATURDAY ? 6 : 7;
                int valeur = Integer.parseInt(Donnees.instance().obtenirFrequenceSurchloration().split(" ")[0]);
                String freq = Donnees.instance().obtenirFrequenceSurchloration().split(" ")[1];
                Donnees.instance().definirJourSurchloration(day);
                Donnees.instance().definirProchaineSurchloration(7 * (freq.equals("mois") ? valeur * 4 : valeur));
                surchlorationAEteModifie();
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "jour=" + Donnees.instance().obtenirJourSurchloration(), false);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "prochaine_surchloration=" + Donnees.instance().obtenirProchaineSurchloration(), false);
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
                    Donnees.instance().definirDebitEquipement(Donnees.Equipement.Orp, valeur);
                    installationAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "debit=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 1:
                if ((0 < valeur) && (valeur <= 655.35)) {
                    Donnees.instance().definirDateConso(Donnees.Equipement.Orp, new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));
                    Donnees.instance().definirVolume(Donnees.Equipement.Orp, valeur);
                    Donnees.instance().definirVolumeRestant(Donnees.Equipement.Orp, valeur);
                    consoAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "date_consommation=" + Donnees.instance().obtenirDateConso(Donnees.Equipement.Orp) + "&volume=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 2:
                if ((3 <= valeur) && (valeur <= 360)) {
                    Donnees.instance().definirDureeCycle(Donnees.Equipement.Orp, (int) valeur);
                    asservissementAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "duree_cycle=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 3:
                if ((1 <= valeur) && (valeur <= (Donnees.instance().obtenirDureeCycle(Donnees.Equipement.Orp) - 2))) {
                    Donnees.instance().definirDureeInjectionMinimum(Donnees.Equipement.Orp, (int) valeur);
                    asservissementAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "duree_injection_minimum=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 4:
                if ((1 <= valeur) && (valeur <= 50)) {
                    Donnees.instance().definirMultiplicateurDifference(Donnees.Equipement.Orp, (int) valeur);
                    asservissementAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "multiplicateur_diff=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 5:
                if ((0.1 <= valeur) && (valeur <= 30)) {
                    Donnees.instance().definirDureeInjection(Donnees.Equipement.Orp, valeur);
                    asservissementAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "duree_injection=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 6:
                if ((1 <= valeur) && (valeur <= 60)) {
                    Donnees.instance().definirTempsReponse(Donnees.Equipement.Orp, (int) valeur);
                    asservissementAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "temps_reponse=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 7:
                if ((0 <= valeur) && (valeur <= 1380)) {
                    int tpsInjectionEffectue = Donnees.instance().obtenirTempsInjectionJournalierMax(Donnees.Equipement.Orp) - Donnees.instance().obtenirTempsInjectionJournalierMaxRestant(Donnees.Equipement.Orp);
                    Donnees.instance().definirTempsInjectionJournalierMax(Donnees.Equipement.Orp, (int) valeur);
                    Donnees.instance().definirTempsInjectionJournalierMaxRestant(Donnees.Equipement.Orp, ((int) valeur - tpsInjectionEffectue) > 0 ? (int) valeur - tpsInjectionEffectue : 0);
                    asservissementAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "temps_injection_jour_max=" + valeur, false);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "temps_injection_jour_max_restant=" + Donnees.instance().obtenirTempsInjectionJournalierMaxRestant(Donnees.Equipement.Orp), false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 8:
                if ((0 <= valeur) && (valeur <= 1000)) {
                    Donnees.instance().definirPointConsigneOrp((int) valeur);
                    installationAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "point_consigne_orp=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 9:
                if (valeur >= 0) {
                    Donnees.instance().definirHysteresisOrp((int) valeur);
                    installationAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "hysteresis_orp=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 10:
                if ((0 <= valeur) && (valeur <= 5)) {
                    Donnees.instance().definirPointConsigneAmpero(valeur);
                    installationAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "point_consigne_ampero=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 11:
                if (valeur >= 0) {
                    Donnees.instance().definirHysteresisAmpero(valeur);
                    installationAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "hysteresis_ampero=" + valeur, false);
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
            int etat = Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Orp);

            if (sender == viewBoutonAuto) {
                etat = (etat == Donnees.AUTO_MARCHE) ? Donnees.AUTO_MARCHE : Donnees.AUTO_ARRET;
            } else if (sender == viewBoutonMarche) {
                if (Donnees.instance().obtenirEtatRegulations()) {
                    etat = Donnees.MARCHE;
                } else {
                    MainActivity.instance().afficherAlertDialog("Modification du mode de fonctionnement", "Le mode de fonctionnement choisi n'est pas possible car les régulations sont désactivées.\nPour modifier cela, veuillez activer les régulations (onglet Bassin).", "OK");
                    return;
                }
            } else {
                etat = Donnees.ARRET;
            }

            Donnees.instance().definirEtatEquipement(Donnees.Equipement.Orp, etat);
            modeAEteModifie();
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "etat=" + etat, false);
        }
    }

    private void modeAEteModifie() {
        int mode = Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Orp);

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
        labelDebit.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelDebit.setText(Html.fromHtml("Débit : <b>" + String.format("%.2f", Donnees.instance().obtenirDebitEquipement(Donnees.Equipement.Orp)) + " L/h</b>"));

        labelPointConsigneRedox.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelPointConsigneRedox.setText(Html.fromHtml("Point de consigne (Redox) : <b>" + Donnees.instance().obtenirPointConsigneOrp() + " mV</b>"));
        labelHysteresisRedox.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelHysteresisRedox.setText(Html.fromHtml("Hystérésis (Redox) : <b>" + Donnees.instance().obtenirHysteresisOrp() + " mV (" + (Donnees.instance().obtenirPointConsigneOrp() - Donnees.instance().obtenirHysteresisOrp()) + " mV)</b>"));

        labelPointConsigneAmpero.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelPointConsigneAmpero.setText(Html.fromHtml("Point de consigne (Ampéro) : <b>" + Donnees.instance().obtenirPointConsigneAmpero() + " ppm</b>"));
        labelPointConsigneAmpero.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
        labelHysteresisAmpero.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelHysteresisAmpero.setText(Html.fromHtml("Hystérésis (Ampéro) : <b>" + Donnees.instance().obtenirHysteresisAmpero() + " ppm (" + (Donnees.instance().obtenirPointConsigneAmpero() - Donnees.instance().obtenirHysteresisAmpero()) + " ppm)</b>"));
        labelHysteresisAmpero.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
    }

    private void consoAEteModifie() {
        String color = "";
        String date = Donnees.instance().obtenirDateConso(Donnees.Equipement.Orp);
        double volume = Donnees.instance().obtenirVolume(Donnees.Equipement.Orp);
        double volumeRestant = Donnees.instance().obtenirVolumeRestant(Donnees.Equipement.Orp);

        if (volumeRestant < (volume * Global.HYSTERESIS_BIDON_VIDE / 100.0)) {
            color = "red";
        } else if (volumeRestant < (volume * Global.HYSTERESIS_BIDON_PRESQUE_VIDE / 100.0)) {
            color = "orange";
        } else {
            color = "#00FF00";
        }

        labelConsommations.setEnabled(Donnees.instance().obtenirActiviteIHM());
        labelConsommations.setText(Html.fromHtml("Depuis : " + date + "<br />Volume : <b>" + volume + " L</b><br /><font color=\"" + color + "\"><i>Volume restant : <b>" + volumeRestant + " L</b></i></font>"));
        labelConsoProduitInjecte.setText(Html.fromHtml("Produit injecté sur 1 / 7 / 28 jours : <b>" + Donnees.instance().obtenirConsoJour(Donnees.Equipement.Orp) + " L</b> / <b>" + Donnees.instance().obtenirConsoSemaine(Donnees.Equipement.Orp) + " L</b> / <b>" + Donnees.instance().obtenirConsoMois(Donnees.Equipement.Orp) + " L</b>"));
        labelConsoProduitInjecte.setVisibility((Donnees.instance().obtenirTypeAppareil() == Donnees.MYOZONEX) || (Donnees.instance().obtenirTypeAppareil() == Donnees.MYOZONEXESSENTIEL) ? View.VISIBLE : View.GONE);
    }

    private void asservissementAEteModifie() {
        labelDureeCycle.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelDureeCycle.setText(Html.fromHtml("Durée cycle : <b>" + Donnees.instance().obtenirDureeCycle(Donnees.Equipement.Orp) + " seconde(s)</b>"));
        labelDureeCycle.setVisibility(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_LIN ? View.VISIBLE : View.GONE);
        labelDureeInjectionMin.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelDureeInjectionMin.setText(Html.fromHtml("Durée injection minimum : <b>" + Donnees.instance().obtenirDureeInjectionMinimum(Donnees.Equipement.Orp) + " seconde(s)</b>"));
        labelDureeInjectionMin.setVisibility(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_LIN ? View.VISIBLE : View.GONE);
        labelMultDiff.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelMultDiff.setText(Html.fromHtml("Multiplicateur de différence : <b>" + Donnees.instance().obtenirMultiplicateurDifference(Donnees.Equipement.Orp) + "</b>"));
        labelMultDiff.setVisibility(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_LIN ? View.VISIBLE : View.GONE);

        labelDureeInjection.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelDureeInjection.setText(Html.fromHtml("Durée injection : <b>" + Donnees.instance().obtenirDureeInjection(Donnees.Equipement.Orp) + " minute(s)</b>"));
        labelDureeInjection.setVisibility(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR ? View.VISIBLE : View.GONE);
        labelTempsReponse.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelTempsReponse.setText(Html.fromHtml("Temps de réponse après injection : <b>" + Donnees.instance().obtenirTempsReponse(Donnees.Equipement.Orp) + " minute(s)</b>"));
        labelTempsReponse.setVisibility(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR ? View.VISIBLE : View.GONE);

        labelTempsJourMax.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelTempsJourMax.setText(Html.fromHtml("Temps d'injection journalier maximum : <b>" + Donnees.instance().obtenirTempsInjectionJournalierMax(Donnees.Equipement.Orp) + " minute(s)</b><br /><font color=\"" + (Donnees.instance().obtenirTempsInjectionJournalierMaxRestant(Donnees.Equipement.Orp) > 0 ? "#00FF00" : "orange") + "\"><i>Temps d'injection journalier maximum restant : <b>" + Donnees.instance().obtenirTempsInjectionJournalierMaxRestant(Donnees.Equipement.Orp) + " minute(s)</b></i></font>"));
    }

    private void switchSurchlorationValueChanged(Switch sender) {
        Donnees.instance().definirEtat(Donnees.Equipement.Orp, sender.isChecked() ? 1 : 0);
        surchlorationAEteModifie();
        Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "surchloration=" + (Donnees.instance().obtenirEtat(Donnees.Equipement.Orp) ? 1 : 0), false);
    }

    private void surchlorationAEteModifie() {
        switchSurchloration.setEnabled(Donnees.instance().obtenirActiviteIHM());
        sliderJourSurchloration.setEnabled(Donnees.instance().obtenirActiviteIHM());
        sliderFreqSurchloration.setEnabled(Donnees.instance().obtenirActiviteIHM());
        sliderMvAjouteSurchloration.setEnabled(Donnees.instance().obtenirActiviteIHM());
        boutonRAZSurchloration.setEnabled(Donnees.instance().obtenirActiviteIHM());

        viewJourSurchloration.setVisibility(Donnees.instance().obtenirEtat(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);
        viewFreqSurchloration.setVisibility(Donnees.instance().obtenirEtat(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);
        viewMvAjouteSurchloration.setVisibility(Donnees.instance().obtenirEtat(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);
        viewProchaineSurchloration.setVisibility(Donnees.instance().obtenirEtat(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);

        if (!sliderJourSurchloration.isPressed()) {
            sliderJourSurchloration.setProgress(Donnees.instance().obtenirJourSurchloration());
        }

        if (!sliderFreqSurchloration.isPressed()) {
            int valeur = Integer.parseInt(Donnees.instance().obtenirFrequenceSurchloration().split(" ")[0]);
            String freq = Donnees.instance().obtenirFrequenceSurchloration().split(" ")[1];
            sliderFreqSurchloration.setProgress((valeur - 3 > 0 ? valeur - 3 : valeur) + (freq.equals("mois") ? 3 : 0));
        }

        if (!sliderMvAjouteSurchloration.isPressed()) {
            sliderMvAjouteSurchloration.setProgress(Donnees.instance().obtenirValeurSurchloration() / 10);
        }

        labelProchaineSurchloration.setText("Prochaine dans " + (Donnees.instance().obtenirProchaineSurchloration()) + " jours");
    }

    private void alarmesAEteModifie() {
        sliderMinAlarmeORP.setEnabled(Donnees.instance().obtenirActiviteIHM());
        sliderMaxAlarmeORP.setEnabled(Donnees.instance().obtenirActiviteIHM());

        if (!sliderORPIsTracking) {
            sliderMinAlarmeORP.setProgress((int) Donnees.instance().obtenirAlarmeSeuilBas(Donnees.Equipement.Orp, Donnees.Capteur.Redox));
            sliderMaxAlarmeORP.setProgress((int) Donnees.instance().obtenirAlarmeSeuilHaut(Donnees.Equipement.Orp, Donnees.Capteur.Redox));
        }

        viewAlarmesAmpero.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
        sliderMinAlarmeAmpero.setEnabled(Donnees.instance().obtenirActiviteIHM());
        sliderMaxAlarmeAmpero.setEnabled(Donnees.instance().obtenirActiviteIHM());

        if (!sliderAmperoIsTracking) {
            sliderMinAlarmeAmpero.setProgress((int) (Donnees.instance().obtenirAlarmeSeuilBas(Donnees.Equipement.Orp, Donnees.Capteur.Ampero) * 10.0));
            sliderMaxAlarmeAmpero.setProgress((int) (Donnees.instance().obtenirAlarmeSeuilHaut(Donnees.Equipement.Orp, Donnees.Capteur.Ampero) * 10.0));
        }
    }
}
