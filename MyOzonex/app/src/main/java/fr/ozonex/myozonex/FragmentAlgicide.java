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

import java.util.Calendar;

public class FragmentAlgicide extends Fragment implements View.OnClickListener {
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

    TextView labelDebit;

    LinearLayout viewConsommations;
    TextView labelConsommations;

    Switch switchEtat;
    LinearLayout viewJour;
    SeekBar sliderJour;
    LinearLayout viewFreq;
    TextView labelFreq;
    SeekBar sliderFreq;
    LinearLayout viewDuree;
    TextView labelDuree;
    SeekBar sliderDuree;
    LinearLayout viewProchaine;
    TextView labelProchaine;
    Button boutonRAZ;

    LinearLayout viewQuestion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.algicide_layout, container, false);

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

        labelDebit = view.findViewById(R.id.texte_debit);
        labelDebit.setOnClickListener(this);

        viewConsommations = view.findViewById(R.id.layout_consommations);
        labelConsommations = view.findViewById(R.id.texte_consommations);

        switchEtat = view.findViewById(R.id.switch_etat);
        viewJour = view.findViewById(R.id.layout_jour);
        sliderJour = view.findViewById(R.id.seekbar_jour);
        viewFreq = view.findViewById(R.id.layout_freq);
        labelFreq = view.findViewById(R.id.texte_freq);
        sliderFreq = view.findViewById(R.id.seekbar_freq);
        viewDuree = view.findViewById(R.id.layout_duree);
        labelDuree = view.findViewById(R.id.texte_duree);
        sliderDuree = view.findViewById(R.id.seekbar_duree);
        viewProchaine = view.findViewById(R.id.layout_prochaine);
        labelProchaine = view.findViewById(R.id.texte_prochaine);
        boutonRAZ = view.findViewById(R.id.bouton_raz);

        viewBoutonMarche.setOnClickListener(this);
        viewBoutonArret.setOnClickListener(this);
        viewBoutonAuto.setOnClickListener(this);

        switchEtat.setOnClickListener(this);
        sliderJour.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                day = day == Calendar.MONDAY ? 1 : day == Calendar.TUESDAY ? 2 : day == Calendar.WEDNESDAY ? 3 : day == Calendar.THURSDAY ? 4 : day == Calendar.FRIDAY ? 5 : day == Calendar.SATURDAY ? 6 : 7;
                int valeur = Integer.parseInt(Donnees.instance().obtenirFrequenceAlgicide().split(" ")[0]);
                String freq = Donnees.instance().obtenirFrequenceAlgicide().split(" ")[1];
                labelProchaine.setText("Prochaine dans " + (7 * (freq.equals("mois") ? 4 : 1) * valeur - (day - progress)) + " jours");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                day = day == Calendar.MONDAY ? 1 : day == Calendar.TUESDAY ? 2 : day == Calendar.WEDNESDAY ? 3 : day == Calendar.THURSDAY ? 4 : day == Calendar.FRIDAY ? 5 : day == Calendar.SATURDAY ? 6 : 7;
                int valeur = Integer.parseInt(Donnees.instance().obtenirFrequenceAlgicide().split(" ")[0]);
                String freq = Donnees.instance().obtenirFrequenceAlgicide().split(" ")[1];
                Donnees.instance().definirJourAlgicide(seekBar.getProgress());
                Donnees.instance().definirProchaineAlgicide(7 * (freq.equals("mois") ? 4 : 1) * valeur - (day - seekBar.getProgress()));
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAlgicide, "jour=" + Donnees.instance().obtenirJourAlgicide(), false);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAlgicide, "prochain=" + Donnees.instance().obtenirProchaineAlgicide(), false);
            }
        });
        sliderFreq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                day = day == Calendar.MONDAY ? 1 : day == Calendar.TUESDAY ? 2 : day == Calendar.WEDNESDAY ? 3 : day == Calendar.THURSDAY ? 4 : day == Calendar.FRIDAY ? 5 : day == Calendar.SATURDAY ? 6 : 7;
                labelFreq.setText("Fréquence : " + (progress - 3 > 0 ? progress - 3 : progress) + (progress - 3 > 0 ? " mois" : " semaine(s)"));
                labelProchaine.setText("Prochaine dans " + (7 * (progress - 3 > 0 ? 4 : 1) * (progress - 3 > 0 ? progress - 3 : progress) - (day - Donnees.instance().obtenirJourAlgicide())) + " jours");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                day = day == Calendar.MONDAY ? 1 : day == Calendar.TUESDAY ? 2 : day == Calendar.WEDNESDAY ? 3 : day == Calendar.THURSDAY ? 4 : day == Calendar.FRIDAY ? 5 : day == Calendar.SATURDAY ? 6 : 7;
                Donnees.instance().definirFrequenceAlgicide((seekBar.getProgress() - 3 > 0 ? seekBar.getProgress() - 3 : seekBar.getProgress()) + (seekBar.getProgress() - 3 > 0 ? " mois" : " semaine(s)"));
                int valeur = Integer.parseInt(Donnees.instance().obtenirFrequenceAlgicide().split(" ")[0]);
                String freq = Donnees.instance().obtenirFrequenceAlgicide().split(" ")[1];
                Donnees.instance().definirProchaineAlgicide(7 * (freq.equals("mois") ? 4 : 1) * valeur - (day - Donnees.instance().obtenirJourAlgicide()));
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAlgicide, "frequence=" + Donnees.instance().obtenirFrequenceAlgicide(), false);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAlgicide, "prochain=" + Donnees.instance().obtenirProchaineAlgicide(), false);
            }
        });
        sliderDuree.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                labelDuree.setText(progress + " min");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirDureeAlgicide(seekBar.getProgress());
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAlgicide, "pendant=" + Donnees.instance().obtenirDureeAlgicide(), false);
            }
        });
        boutonRAZ.setOnClickListener(this);

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

            switchEtat.setChecked(Donnees.instance().obtenirEtat(Donnees.Equipement.Algicide));
            etatAEteModifie();

            installationAEteModifie();
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
                afficherQuestion(true, "Veuillez entrer le débit (en L/h)");
                break;
            case R.id.switch_etat:
                switchEtatValueChanged((Switch) v.findViewById(v.getId()));
                break;
            case R.id.bouton_raz:
                modifierMode(viewBoutonArret);

                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                day = day == Calendar.MONDAY ? 1 : day == Calendar.TUESDAY ? 2 : day == Calendar.WEDNESDAY ? 3 : day == Calendar.THURSDAY ? 4 : day == Calendar.FRIDAY ? 5 : day == Calendar.SATURDAY ? 6 : 7;
                int valeur = Integer.parseInt(Donnees.instance().obtenirFrequenceAlgicide().split(" ")[0]);
                String freq = Donnees.instance().obtenirFrequenceAlgicide().split(" ")[1];
                Donnees.instance().definirJourAlgicide(day);
                Donnees.instance().definirProchaineAlgicide(7 * (freq.equals("mois") ? valeur * 4 : valeur));
                etatAEteModifie();
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAlgicide, "jour=" + Donnees.instance().obtenirJourAlgicide(), false);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAlgicide, "prochain=" + Donnees.instance().obtenirProchaineAlgicide(), false);
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

        if (valeur > 0) {
            Donnees.instance().definirDebitEquipement(Donnees.Equipement.Algicide, valeur);
            installationAEteModifie();
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAlgicide, "debit=" + valeur, false);
            afficherQuestion(false, "");
        } else {
            reponse.setText("");
        }
    }

    private void modifierMode(LinearLayout sender) {
        if (Donnees.instance().obtenirActiviteIHM()) {
            int etat = Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Algicide);

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

            Donnees.instance().definirEtatEquipement(Donnees.Equipement.Algicide, etat);
            modeAEteModifie();
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAlgicide, "etat=" + etat, false);
        }
    }

    private void modeAEteModifie() {
        int mode = Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Algicide);

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
        labelDebit.setText(Html.fromHtml("Débit : <b>" + String.format("%.2f", Donnees.instance().obtenirDebitEquipement(Donnees.Equipement.Algicide)) + " L/h</b>"));
    }

    private void consoAEteModifie() {
        String color = "";
        String date = Donnees.instance().obtenirDateConso(Donnees.Equipement.Algicide);
        double volume = Donnees.instance().obtenirVolume(Donnees.Equipement.Algicide);
        double volumeRestant = Donnees.instance().obtenirVolumeRestant(Donnees.Equipement.Algicide);

        if (volumeRestant < (volume * Global.HYSTERESIS_BIDON_VIDE / 100.0)) {
            color = "red";
        } else if (volumeRestant < (volume * Global.HYSTERESIS_BIDON_PRESQUE_VIDE / 100.0)) {
            color = "orange";
        } else {
            color = "#00FF00";
        }

        viewConsommations.setVisibility(Donnees.instance().obtenirTypeAppareil() == Donnees.MYOZONEX ? View.VISIBLE : View.GONE);
        labelConsommations.setText(Html.fromHtml("Depuis : " + date + "<br />Volume : <b>" + volume + " L</b><br /><font color=\"" + color + "\"><i>Volume restant : <b>" + volumeRestant + " L</b></i></font><br /><br />Produit injecté sur 1 / 7 / 28 jours : <b>" + Donnees.instance().obtenirConsoJour(Donnees.Equipement.Algicide) + "</b> / <b>" + Donnees.instance().obtenirConsoSemaine(Donnees.Equipement.Algicide) + "</b> / <b>" + Donnees.instance().obtenirConsoMois(Donnees.Equipement.Algicide) + "</b>"));
    }

    private void switchEtatValueChanged(Switch sender) {
        Donnees.instance().definirEtat(Donnees.Equipement.Algicide, sender.isChecked() ? 1 : 0);
        etatAEteModifie();
        Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAlgicide, "active=" + (Donnees.instance().obtenirEtat(Donnees.Equipement.Algicide) ? 1 : 0), false);
    }

    private void etatAEteModifie() {
        switchEtat.setEnabled(Donnees.instance().obtenirActiviteIHM());
        sliderJour.setEnabled(Donnees.instance().obtenirActiviteIHM());
        sliderFreq.setEnabled(Donnees.instance().obtenirActiviteIHM());
        sliderDuree.setEnabled(Donnees.instance().obtenirActiviteIHM());
        boutonRAZ.setEnabled(Donnees.instance().obtenirActiviteIHM());

        viewJour.setVisibility(Donnees.instance().obtenirEtat(Donnees.Equipement.Algicide) ? View.VISIBLE : View.GONE);
        viewFreq.setVisibility(Donnees.instance().obtenirEtat(Donnees.Equipement.Algicide) ? View.VISIBLE : View.GONE);
        viewDuree.setVisibility(Donnees.instance().obtenirEtat(Donnees.Equipement.Algicide) ? View.VISIBLE : View.GONE);
        viewProchaine.setVisibility(Donnees.instance().obtenirEtat(Donnees.Equipement.Algicide) ? View.VISIBLE : View.GONE);

        if (!sliderJour.isPressed()) {
            sliderJour.setProgress(Donnees.instance().obtenirJourAlgicide());
        }

        if (!sliderFreq.isPressed()) {
            int valeur = Integer.parseInt(Donnees.instance().obtenirFrequenceAlgicide().split(" ")[0]);
            String freq = Donnees.instance().obtenirFrequenceAlgicide().split(" ")[1];
            sliderFreq.setProgress((valeur - 3 > 0 ? valeur - 3 : valeur) + (freq.equals("mois") ? 3 : 0));
        }

        if (!sliderDuree.isPressed()) {
            sliderDuree.setProgress(Donnees.instance().obtenirDureeAlgicide());
        }

        labelProchaine.setText("Prochaine dans " + (Donnees.instance().obtenirProchaineAlgicide()) + " jours");
    }
}
