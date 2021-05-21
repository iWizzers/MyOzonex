package fr.ozonex.myozonex;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DecimalFormat;

public class FragmentPompeFiltration extends Fragment implements View.OnClickListener {
    View view = null;

    Boolean sliderIsTracking = false;
    int indexPlage;

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

    LinearLayout viewConsommations;
    TextView labelConsommations;

    Switch switchHorsGel;
    LinearLayout viewTempHorsGel;
    SeekBar sliderMinTempHorsGel;
    TextView labelMinTempHorsGel;
    SeekBar sliderMaxTempHorsGel;
    TextView labelMaxTempHorsGel;
    LinearLayout viewFreqHorsGel;
    SeekBar sliderFreqHorsGel;

    LinearLayout viewPlagesHoraires;
    LinearLayout viewPlage1;
    ImageButton boutonSupprPlage1;
    TextView labelHeureDebutPlage1;
    TextView labelHeureFinPlage1;
    LinearLayout viewPlage2;
    ImageButton boutonSupprPlage2;
    TextView labelHeureDebutPlage2;
    TextView labelHeureFinPlage2;
    LinearLayout viewPlage3;
    ImageButton boutonSupprPlage3;
    TextView labelHeureDebutPlage3;
    TextView labelHeureFinPlage3;
    LinearLayout viewPlage4;
    ImageButton boutonSupprPlage4;
    TextView labelHeureDebutPlage4;
    TextView labelHeureFinPlage4;
    Button boutonAjouterPlageHoraire;

    LinearLayout viewQuestion;
    TimePicker timePickerDebutPlage;
    TimePicker timePickerFinPlage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pompe_filtration_layout, container, false);

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

        viewConsommations = view.findViewById(R.id.layout_consommations);
        labelConsommations = view.findViewById(R.id.texte_consommations);

        switchHorsGel = view.findViewById(R.id.switch_hors_gel);
        viewTempHorsGel = view.findViewById(R.id.layout_temp_hors_gel);
        sliderMinTempHorsGel = view.findViewById(R.id.seekbar_min_temp_hors_gel);
        labelMinTempHorsGel = view.findViewById(R.id.texte_min_temp_hors_gel);
        sliderMaxTempHorsGel = view.findViewById(R.id.seekbar_max_temp_hors_gel);
        labelMaxTempHorsGel = view.findViewById(R.id.texte_max_temp_hors_gel);
        viewFreqHorsGel = view.findViewById(R.id.layout_freq_hors_gel);
        sliderFreqHorsGel = view.findViewById(R.id.seekbar_freq_hors_gel);

        viewPlagesHoraires = view.findViewById(R.id.layout_plages_horaires);
        viewPlage1 = view.findViewById(R.id.layout_plage_1);
        boutonSupprPlage1 = view.findViewById(R.id.bouton_supprimer_plage_1);
        labelHeureDebutPlage1 = view.findViewById(R.id.texte_heure_debut_plage_1);
        labelHeureFinPlage1 = view.findViewById(R.id.texte_heure_fin_plage_1);
        viewPlage2 = view.findViewById(R.id.layout_plage_2);
        boutonSupprPlage2 = view.findViewById(R.id.bouton_supprimer_plage_2);
        labelHeureDebutPlage2 = view.findViewById(R.id.texte_heure_debut_plage_2);
        labelHeureFinPlage2 = view.findViewById(R.id.texte_heure_fin_plage_2);
        viewPlage3 = view.findViewById(R.id.layout_plage_3);
        boutonSupprPlage3 = view.findViewById(R.id.bouton_supprimer_plage_3);
        labelHeureDebutPlage3 = view.findViewById(R.id.texte_heure_debut_plage_3);
        labelHeureFinPlage3 = view.findViewById(R.id.texte_heure_fin_plage_3);
        viewPlage4 = view.findViewById(R.id.layout_plage_4);
        boutonSupprPlage4 = view.findViewById(R.id.bouton_supprimer_plage_4);
        labelHeureDebutPlage4 = view.findViewById(R.id.texte_heure_debut_plage_4);
        labelHeureFinPlage4 = view.findViewById(R.id.texte_heure_fin_plage_4);
        boutonAjouterPlageHoraire = view.findViewById(R.id.bouton_ajouter_plage_horaire);

        viewQuestion = view.findViewById(R.id.layout_question);
        timePickerDebutPlage = view.findViewById(R.id.time_picker_debut_plage);
        timePickerFinPlage = view.findViewById(R.id.time_picker_fin_plage);
        ImageButton boutonAnnulerQuestion = view.findViewById(R.id.bouton_annuler_question);
        ImageButton boutonValiderQuestion = view.findViewById(R.id.bouton_valider_question);

        viewBoutonMarche.setOnClickListener(this);
        viewBoutonArret.setOnClickListener(this);
        viewBoutonAuto.setOnClickListener(this);

        switchHorsGel.setOnClickListener(this);
        sliderMinTempHorsGel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Boolean change = false;
                int minVal = progress;
                int maxVal = Integer.parseInt(String.valueOf(sliderMaxTempHorsGel.getProgress()));

                if (sliderIsTracking) {
                    if (minVal < maxVal - 3) {
                        change = true;
                        minVal = maxVal - 3;
                    } else if (minVal > maxVal - 1) {
                        change = true;
                        minVal = maxVal - 1;
                    }
                }

                if (change) {
                    sliderMinTempHorsGel.setProgress(minVal);
                } else {
                    labelMinTempHorsGel.setText(minVal + " °C");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                sliderIsTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirEnclHorsGel(seekBar.getProgress());
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "enclenchement_hors_gel=" + Donnees.instance().obtenirEnclHorsGel(), false);
                sliderIsTracking = false;
            }
        });
        sliderMaxTempHorsGel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Boolean change = false;
                int minVal = Integer.parseInt(String.valueOf(sliderMinTempHorsGel.getProgress()));
                int maxVal = progress;

                if (sliderIsTracking) {
                    if (maxVal > minVal + 3) {
                        change = true;
                        maxVal = minVal + 3;
                    } else if (maxVal < minVal + 1) {
                        change = true;
                        maxVal = minVal + 1;
                    }
                }

                if (change) {
                    sliderMaxTempHorsGel.setProgress(maxVal);
                } else {
                    labelMaxTempHorsGel.setText(maxVal + " °C");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                sliderIsTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirArretHorsGel(seekBar.getProgress());
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "arret_hors_gel=" + (Donnees.instance().obtenirArretHorsGel() - Donnees.instance().obtenirEnclHorsGel() - 1), false);
                sliderIsTracking = false;
            }
        });
        sliderFreqHorsGel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirFreqHorsGel(seekBar.getProgress());
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "frequence_hors_gel=" + Donnees.instance().obtenirFreqHorsGel(), false);
            }
        });

        boutonSupprPlage1.setOnClickListener(this);
        viewPlage1.setOnClickListener(this);
        boutonSupprPlage2.setOnClickListener(this);
        viewPlage2.setOnClickListener(this);
        boutonSupprPlage3.setOnClickListener(this);
        viewPlage3.setOnClickListener(this);
        boutonSupprPlage4.setOnClickListener(this);
        viewPlage4.setOnClickListener(this);
        boutonAjouterPlageHoraire.setOnClickListener(this);

        timePickerDebutPlage.setIs24HourView(true); // 24H Mode.
        timePickerFinPlage.setIs24HourView(true); // 24H Mode.
        boutonAnnulerQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficherTimePicker(false, "", "", "");
            }
        });
        boutonValiderQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validerAjoutPlage();
            }
        });

        update();

        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            modeAEteModifie();

            viewConsommations.setVisibility(Donnees.instance().obtenirTypeAppareil() == Donnees.MYOZONEX ? View.VISIBLE : View.GONE);
            labelConsommations.setText("Date : " + Donnees.instance().obtenirDateConso(Donnees.Equipement.PompeFiltration) + "\nHeures pleines : " + Donnees.instance().obtenirConsoHP(Donnees.Equipement.PompeFiltration) + " kWh \nHeures creuses : " + Donnees.instance().obtenirConsoHC(Donnees.Equipement.PompeFiltration) + " kWh");

            switchHorsGel.setChecked(Donnees.instance().obtenirEtatHorsGel());
            horsGelAEteModifie();

            plagesAEteModifie();
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
            case R.id.switch_hors_gel:
                switchHorsGelValueChanged((Switch) v.findViewById(v.getId()));
                break;
            case R.id.bouton_supprimer_plage_1:
            case R.id.bouton_supprimer_plage_2:
            case R.id.bouton_supprimer_plage_3:
            case R.id.bouton_supprimer_plage_4:
                supprimerPlage((ImageButton) v.findViewById(v.getId()));
                break;
            case R.id.layout_plage_1:
            case R.id.layout_plage_2:
            case R.id.layout_plage_3:
            case R.id.layout_plage_4:
                modifierPlage((LinearLayout) v.findViewById(v.getId()));
                break;
            case R.id.bouton_ajouter_plage_horaire:
                ajouterPlage();
                break;
            default:
                break;
        }
    }

    private void modifierMode(LinearLayout sender) {
        if (Donnees.instance().obtenirActiviteIHM()) {
            int etat = Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PompeFiltration);

            if (sender == viewBoutonAuto) {
                etat = (etat == Donnees.AUTO_MARCHE) ? Donnees.AUTO_MARCHE : Donnees.AUTO_ARRET;
            } else if (sender == viewBoutonMarche) {
                etat = Donnees.MARCHE;
            } else {
                etat = Donnees.ARRET;
            }

            Donnees.instance().definirEtatEquipement(Donnees.Equipement.PompeFiltration, etat);
            Donnees.instance().definirEtatLectureCapteurs(false);
            modeAEteModifie();
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "etat=" + etat, false);
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "lecture_capteurs=0", false);
        }
    }

    private void modeAEteModifie() {
        int mode = Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PompeFiltration);

        viewBoutonMarche.setBackgroundTintList(ColorStateList.valueOf(mode == Donnees.MARCHE ? Color.rgb(255, 83, 13) : Color.rgb(245, 245, 220)));
        labelBoutonMarche.setTextColor(mode == Donnees.MARCHE ? Color.rgb(40, 40, 40) : Color.rgb(128, 128, 128));
        imageBoutonMarche.setColorFilter(mode == Donnees.MARCHE ? Color.rgb(40, 40, 40) : Color.rgb(128, 128, 128));

        viewBoutonArret.setBackgroundColor(mode == Donnees.ARRET ? Color.rgb(255, 83, 13) : Color.rgb(245, 245, 220));
        labelBoutonArret.setTextColor(mode == Donnees.ARRET ? Color.rgb(40, 40, 40) : Color.rgb(128, 128, 128));
        imageBoutonArret.setColorFilter(mode == Donnees.ARRET ? Color.rgb(40, 40, 40) : Color.rgb(128, 128, 128));

        viewBoutonAuto.setBackgroundTintList(ColorStateList.valueOf(mode > Donnees.AUTO ? Color.rgb(0, 174, 239) : Color.rgb(245, 245, 220)));
        labelBoutonAuto.setTextColor(mode > Donnees.AUTO ? Color.rgb(40, 40, 40) : Color.rgb(128, 128, 128));
        imageBoutonAuto.setColorFilter(mode > Donnees.AUTO ? Color.rgb(40, 40, 40) : Color.rgb(128, 128, 128));

        viewPlagesHoraires.setVisibility(mode < Donnees.AUTO ? View.GONE : View.VISIBLE);
    }

    private void switchHorsGelValueChanged(Switch sender) {
        Donnees.instance().definirEtatHorsGel(sender.isChecked());
        horsGelAEteModifie();
        Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "etat_hors_gel=" + (Donnees.instance().obtenirEtatHorsGel() ? 1 : 0), false);
    }

    private void horsGelAEteModifie() {
        switchHorsGel.setEnabled(Donnees.instance().obtenirActiviteIHM());
        sliderMinTempHorsGel.setEnabled(Donnees.instance().obtenirActiviteIHM());
        sliderMaxTempHorsGel.setEnabled(Donnees.instance().obtenirActiviteIHM());
        sliderFreqHorsGel.setEnabled(Donnees.instance().obtenirActiviteIHM());

        viewTempHorsGel.setVisibility(Donnees.instance().obtenirEtatHorsGel() ? View.VISIBLE : View.GONE);
        viewFreqHorsGel.setVisibility(Donnees.instance().obtenirEtatHorsGel() ? View.VISIBLE : View.GONE);

        if (!sliderIsTracking) {
            sliderMinTempHorsGel.setProgress(Donnees.instance().obtenirEnclHorsGel());
            sliderMaxTempHorsGel.setProgress(Donnees.instance().obtenirEnclHorsGel() + Donnees.instance().obtenirArretHorsGel() + 1);
        }

        if (!sliderFreqHorsGel.isPressed()) {
            sliderFreqHorsGel.setProgress(Donnees.instance().obtenirFreqHorsGel());
        }
    }

    private void plagesAEteModifie() {
        viewPlage1.setVisibility(Donnees.instance().obtenirPlagesAuto() || Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, 0).getEtat() ? View.VISIBLE : View.GONE);
        labelHeureDebutPlage1.setText(Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, 0).getPlage().split(" - ")[0]);
        labelHeureFinPlage1.setText(Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, 0).getPlage().split(" - ")[1]);

        viewPlage2.setVisibility(Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, 1).getEtat() ? View.VISIBLE : View.GONE);
        labelHeureDebutPlage2.setText(Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, 1).getPlage().split(" - ")[0]);
        labelHeureFinPlage2.setText(Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, 1).getPlage().split(" - ")[1]);

        viewPlage3.setVisibility(Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, 2).getEtat() ? View.VISIBLE : View.GONE);
        labelHeureDebutPlage3.setText(Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, 2).getPlage().split(" - ")[0]);
        labelHeureFinPlage3.setText(Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, 2).getPlage().split(" - ")[1]);

        viewPlage4.setVisibility(Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, 3).getEtat() ? View.VISIBLE : View.GONE);
        labelHeureDebutPlage4.setText(Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, 3).getPlage().split(" - ")[0]);
        labelHeureFinPlage4.setText(Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, 3).getPlage().split(" - ")[1]);

        boutonAjouterPlageHoraire.setVisibility(!Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, 3).getEtat() && !Donnees.instance().obtenirPlagesAuto() ? View.VISIBLE : View.GONE);
    }

    private void supprimerPlage(ImageButton sender) {
        indexPlage = sender == boutonSupprPlage1 ? 0 : sender == boutonSupprPlage2 ? 1 : sender == boutonSupprPlage3 ? 2 : 3;

        if (indexPlage < (Global.MAX_PLAGES_EQUIPEMENTS - 1)) {
            for (int i = indexPlage; i < Global.MAX_PLAGES_EQUIPEMENTS-2; i++) {
                Donnees.instance().definirPlageFonctionnement(Donnees.Equipement.PompeFiltration, i, Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, i+1).getPlage());
            }

            Donnees.instance().definirPlageFonctionnement(Donnees.Equipement.PompeFiltration, Global.MAX_PLAGES_EQUIPEMENTS-1, "00h00 - 00h00");
        } else {
            Donnees.instance().definirPlageFonctionnement(Donnees.Equipement.PompeFiltration, indexPlage, "00h00 - 00h00");
        }

        plagesAEteModifie();

        for (int i = indexPlage; i < Global.MAX_PLAGES_EQUIPEMENTS-1; i++) {
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "plage_" + (i + 1) + "=" + Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, i).getPlage(), false);
        }
    }

    private void ajouterPlage() {
        for (int i = 0; i < Global.MAX_PLAGES_EQUIPEMENTS-1; i++) {
            if (!Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, i).getEtat()) {
                indexPlage = i;
                break;
            }
        }

        afficherTimePicker(true, "Veuillez entrer la plage de fonctionnement de l'équipement", "", "");
    }

    private void modifierPlage(LinearLayout sender) {
        indexPlage = sender == viewPlage1 ? 0 : sender == viewPlage2 ? 1 : sender == viewPlage3 ? 2 : 3;

        afficherTimePicker(true, "Veuillez entrer la plage de fonctionnement de l'équipement", Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, indexPlage).getPlage().split(" - ")[0].replaceAll("h",":"), Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, indexPlage).getPlage().split(" - ")[1].replaceAll("h", ":"));
    }

    private void afficherTimePicker(Boolean visible, String question, String heureDebutPlage, String heureFinPlage) {
        scrollView.setVisibility(!visible ? View.VISIBLE : View.GONE);
        viewQuestion.setVisibility(visible ? View.VISIBLE : View.GONE);

        TextView textView = view.findViewById(R.id.texte_question);
        textView.setText(question);

        if (!heureDebutPlage.isEmpty() && !heureFinPlage.isEmpty()) {
            timePickerDebutPlage.setHour(Integer.parseInt(heureDebutPlage.split(":")[0]));
            timePickerDebutPlage.setMinute(Integer.parseInt(heureDebutPlage.split(":")[1]));
            timePickerFinPlage.setHour(Integer.parseInt(heureFinPlage.split(":")[0]));
            timePickerFinPlage.setMinute(Integer.parseInt(heureFinPlage.split(":")[1]));
        }
    }

    private void validerAjoutPlage() {
        DecimalFormat df = new DecimalFormat("00");
        String heureDebutPlage = df.format(timePickerDebutPlage.getHour()) + 'h' + df.format(timePickerDebutPlage.getMinute());
        String heureFinPlage = df.format(timePickerFinPlage.getHour()) + 'h' + df.format(timePickerFinPlage.getMinute());

        Donnees.instance().definirPlageFonctionnement(Donnees.Equipement.PompeFiltration, indexPlage, heureDebutPlage + " - " + heureFinPlage);
        plagesAEteModifie();
        Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "plage_" + (indexPlage + 1) + "=" + Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, indexPlage).getPlage(), false);

        afficherTimePicker(false, "", "", "");
    }
}
