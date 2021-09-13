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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FragmentRegulateurPhMoins extends Fragment implements View.OnClickListener {
    View view = null;

    Boolean sliderIsTracking = false;

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

    TextView labelDebit;
    TextView labelPointConsigne;
    TextView labelHysteresis;

    SeekBar sliderMinAlarme;
    TextView labelMinAlarme;
    SeekBar sliderMaxAlarme;
    TextView labelMaxAlarme;

    int typeModification = 0;
    LinearLayout viewQuestion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.regulateur_ph_moins_layout, container, false);

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

        labelDebit = view.findViewById(R.id.texte_debit);
        labelPointConsigne = view.findViewById(R.id.texte_point_consigne);
        labelHysteresis = view.findViewById(R.id.texte_hysteresis);

        sliderMinAlarme = view.findViewById(R.id.seekbar_min_alarme);
        labelMinAlarme = view.findViewById(R.id.texte_min_alarme);
        sliderMaxAlarme = view.findViewById(R.id.seekbar_max_alarme);
        labelMaxAlarme = view.findViewById(R.id.texte_max_alarme);

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

        labelDebit.setOnClickListener(this);
        labelPointConsigne.setOnClickListener(this);
        labelHysteresis.setOnClickListener(this);

        sliderMinAlarme.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Boolean change = false;
                int minVal = progress;
                int maxVal = Integer.parseInt(String.valueOf(sliderMaxAlarme.getProgress()));

                if (sliderIsTracking) {
                    if (minVal > maxVal - 1) {
                        change = true;
                        minVal = maxVal - 1;
                    }
                }

                if (change) {
                    sliderMinAlarme.setProgress(minVal);
                } else {
                    labelMinAlarme.setText(String.valueOf(minVal / 10.0));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                sliderIsTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirAlarmeSeuilBas(Donnees.Equipement.PhGlobal, seekBar.getProgress() / 10.0, null);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhGlobal, "alarme_seuil_bas=" + Donnees.instance().obtenirAlarmeSeuilBas(Donnees.Equipement.PhGlobal, null), false);
                sliderIsTracking = false;
            }
        });
        sliderMaxAlarme.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Boolean change = false;
                int minVal = Integer.parseInt(String.valueOf(sliderMinAlarme.getProgress()));
                int maxVal = progress;

                if (sliderIsTracking) {
                    if (maxVal < minVal + 1) {
                        change = true;
                        maxVal = minVal + 1;
                    }
                }

                if (change) {
                    sliderMaxAlarme.setProgress(maxVal);
                } else {
                    labelMaxAlarme.setText(String.valueOf(maxVal / 10.0));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                sliderIsTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirAlarmeSeuilHaut(Donnees.Equipement.PhGlobal, seekBar.getProgress() / 10.0, null);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhGlobal, "alarme_seuil_haut=" + Donnees.instance().obtenirAlarmeSeuilHaut(Donnees.Equipement.PhGlobal, null), false);
                sliderIsTracking = false;
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
                afficherQuestion(true, "Veuillez entrer la durée d'injection minimum (de 1 à " + (Donnees.instance().obtenirDureeCycle(Donnees.Equipement.PhMoins) - 2) + " secondes)");
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
            case R.id.texte_point_consigne:
                typeModification = 8;
                afficherQuestion(true, "Veuillez entrer le point de consigne");
                break;
            case R.id.texte_hysteresis:
                typeModification = 9;
                afficherQuestion(true, "Veuillez entrer l'hystérésis");
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
                    Donnees.instance().definirDebitEquipement(Donnees.Equipement.PhMoins, valeur);
                    installationAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "debit=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 1:
                if ((0 < valeur) && (valeur <= 655.35)) {
                    Donnees.instance().definirDateConso(Donnees.Equipement.PhMoins, new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));
                    Donnees.instance().definirVolume(Donnees.Equipement.PhMoins, valeur);
                    Donnees.instance().definirVolumeRestant(Donnees.Equipement.PhMoins, valeur);
                    consoAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "date_consommation=" + Donnees.instance().obtenirDateConso(Donnees.Equipement.PhMoins) + "&volume=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 2:
                if ((3 <= valeur) && (valeur <= 360)) {
                    Donnees.instance().definirDureeCycle(Donnees.Equipement.PhMoins, (int) valeur);
                    asservissementAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "duree_cycle=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 3:
                if ((1 <= valeur) && (valeur <= (Donnees.instance().obtenirDureeCycle(Donnees.Equipement.PhMoins) - 2))) {
                    Donnees.instance().definirDureeInjectionMinimum(Donnees.Equipement.PhMoins, (int) valeur);
                    asservissementAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "duree_injection_minimum=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 4:
                if ((1 <= valeur) && (valeur <= 50)) {
                    Donnees.instance().definirMultiplicateurDifference(Donnees.Equipement.PhMoins, (int) valeur);
                    asservissementAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "multiplicateur_diff=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 5:
                if ((0.1 <= valeur) && (valeur <= 30)) {
                    Donnees.instance().definirDureeInjection(Donnees.Equipement.PhMoins, valeur);
                    asservissementAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "duree_injection=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 6:
                if ((1 <= valeur) && (valeur <= 60)) {
                    Donnees.instance().definirTempsReponse(Donnees.Equipement.PhMoins, (int) valeur);
                    asservissementAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "temps_reponse=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 7:
                if ((0 <= valeur) && (valeur <= 1380)) {
                    int tpsInjectionEffectue = Donnees.instance().obtenirTempsInjectionJournalierMax(Donnees.Equipement.PhMoins) - Donnees.instance().obtenirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhMoins);
                    Donnees.instance().definirTempsInjectionJournalierMax(Donnees.Equipement.PhMoins, (int) valeur);
                    Donnees.instance().definirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhMoins, ((int) valeur - tpsInjectionEffectue) > 0 ? (int) valeur - tpsInjectionEffectue : 0);
                    asservissementAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "temps_injection_jour_max=" + valeur, false);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "temps_injection_jour_max_restant=" + Donnees.instance().obtenirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhMoins), false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 8:
                if ((0 <= valeur) && (valeur <= 14)) {
                    Donnees.instance().definirPointConsignePh(valeur);
                    installationAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhGlobal, "point_consigne=" + valeur, false);
                    afficherQuestion(false, "");
                } else {
                    reponse.setText("");
                }
                break;
            case 9:
                if (valeur >= 0) {
                    Donnees.instance().definirHysteresisPhMoins(valeur);
                    installationAEteModifie();
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhGlobal, "hysteresis_moins=" + valeur, false);
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
            int etat = Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PhMoins);

            if (sender == viewBoutonAuto) {
                etat = (etat == Donnees.AUTO_MARCHE) ? Donnees.AUTO_MARCHE : Donnees.AUTO_ARRET;
            } else if (sender == viewBoutonMarche) {
                if (Donnees.instance().obtenirEtatRegulations()) {
                    etat = Donnees.MARCHE;
                } else {
                    MainActivity.instance().afficherAlertDialog(getString(R.string.mode_fonctionnement_titre), getString(R.string.mode_fonctionnement_texte_regulations), "OK");
                    return;
                }
            } else {
                etat = Donnees.ARRET;
            }

            Donnees.instance().definirEtatEquipement(Donnees.Equipement.PhMoins, etat);
            modeAEteModifie();
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "etat=" + etat, false);
        }
    }

    private void modeAEteModifie() {
        int mode = Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PhMoins);

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
        labelDebit.setText(Html.fromHtml("Débit : <b>" + String.format("%.2f", Donnees.instance().obtenirDebitEquipement(Donnees.Equipement.PhMoins)) + " L/h</b>"));

        labelPointConsigne.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelPointConsigne.setText(Html.fromHtml("Point de consigne : <b>" + String.format("%.2f", Donnees.instance().obtenirPointConsignePh()) + "</b>"));
        labelHysteresis.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelHysteresis.setText(Html.fromHtml("Hystérésis : <b>" + String.format("%.2f", Donnees.instance().obtenirHysteresisPhMoins()) + " (" + (String.format("%.2f", Donnees.instance().obtenirPointConsignePh() + Donnees.instance().obtenirHysteresisPhMoins())) + ")</b>"));
    }

    private void consoAEteModifie() {
        String color = "";
        String date = Donnees.instance().obtenirDateConso(Donnees.Equipement.PhMoins);
        double volume = Donnees.instance().obtenirVolume(Donnees.Equipement.PhMoins);
        double volumeRestant = Donnees.instance().obtenirVolumeRestant(Donnees.Equipement.PhMoins);

        if (volumeRestant < (volume * Global.HYSTERESIS_BIDON_VIDE / 100.0)) {
            color = "red";
        } else if (volumeRestant < (volume * Global.HYSTERESIS_BIDON_PRESQUE_VIDE / 100.0)) {
            color = "orange";
        } else {
            color = "#00FF00";
        }

        labelConsommations.setEnabled(Donnees.instance().obtenirActiviteIHM());
        labelConsommations.setText(Html.fromHtml("Depuis : " + date + "<br />Volume : <b>" + volume + " L</b><br /><font color=\"" + color + "\"><i>Volume restant : <b>" + volumeRestant + " L</b></i></font>"));
        labelConsoProduitInjecte.setText(Html.fromHtml("Produit injecté sur 1 / 7 / 28 jours : <b>" + Donnees.instance().obtenirConsoJour(Donnees.Equipement.PhMoins) + " L</b> / <b>" + Donnees.instance().obtenirConsoSemaine(Donnees.Equipement.PhMoins) + " L</b> / <b>" + Donnees.instance().obtenirConsoMois(Donnees.Equipement.PhMoins) + " L</b>"));
        labelConsoProduitInjecte.setVisibility((Donnees.instance().obtenirTypeAppareil() == Donnees.MYOZONEX) || (Donnees.instance().obtenirTypeAppareil() == Donnees.MYOZONEXESSENTIEL) ? View.VISIBLE : View.GONE);
    }

    private void asservissementAEteModifie() {
        labelDureeCycle.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelDureeCycle.setText(Html.fromHtml("Durée cycle : <b>" + Donnees.instance().obtenirDureeCycle(Donnees.Equipement.PhMoins) + " seconde(s)</b>"));
        labelDureeCycle.setVisibility(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_LIN ? View.VISIBLE : View.GONE);
        labelDureeInjectionMin.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelDureeInjectionMin.setText(Html.fromHtml("Durée injection minimum : <b>" + Donnees.instance().obtenirDureeInjectionMinimum(Donnees.Equipement.PhMoins) + " seconde(s)</b>"));
        labelDureeInjectionMin.setVisibility(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_LIN ? View.VISIBLE : View.GONE);
        labelMultDiff.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelMultDiff.setText(Html.fromHtml("Multiplicateur de différence : <b>" + Donnees.instance().obtenirMultiplicateurDifference(Donnees.Equipement.PhMoins) + "</b>"));
        labelMultDiff.setVisibility(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_LIN ? View.VISIBLE : View.GONE);

        labelDureeInjection.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelDureeInjection.setText(Html.fromHtml("Durée injection : <b>" + Donnees.instance().obtenirDureeInjection(Donnees.Equipement.PhMoins) + " minute(s)</b>"));
        labelDureeInjection.setVisibility(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR ? View.VISIBLE : View.GONE);
        labelTempsReponse.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelTempsReponse.setText(Html.fromHtml("Temps de réponse après injection : <b>" + Donnees.instance().obtenirTempsReponse(Donnees.Equipement.PhMoins) + " minute(s)</b>"));
        labelTempsReponse.setVisibility(Donnees.instance().obtenirTypeRegulation() == Donnees.ASSERVISSEMENT_TOR ? View.VISIBLE : View.GONE);

        labelTempsJourMax.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
        labelTempsJourMax.setText(Html.fromHtml("Temps d'injection journalier maximum : <b>" + Donnees.instance().obtenirTempsInjectionJournalierMax(Donnees.Equipement.PhMoins) + " minute(s)</b><br /><font color=\"" + (Donnees.instance().obtenirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhMoins) > 0 ? "#00FF00" : "orange") + "\"><i>Temps d'injection journalier maximum restant : <b>" + Donnees.instance().obtenirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhMoins) + " minute(s)</b></i></font>"));
    }

    private void alarmesAEteModifie() {
        sliderMinAlarme.setEnabled(Donnees.instance().obtenirActiviteIHM());
        sliderMaxAlarme.setEnabled(Donnees.instance().obtenirActiviteIHM());

        if (!sliderIsTracking) {
            sliderMinAlarme.setProgress((int) (Donnees.instance().obtenirAlarmeSeuilBas(Donnees.Equipement.PhGlobal, null) * 10));
            sliderMaxAlarme.setProgress((int) (Donnees.instance().obtenirAlarmeSeuilHaut(Donnees.Equipement.PhGlobal, null) * 10));
        }
    }
}
