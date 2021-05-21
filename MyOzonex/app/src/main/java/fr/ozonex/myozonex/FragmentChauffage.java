package fr.ozonex.myozonex;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class FragmentChauffage extends Fragment implements View.OnClickListener {
    View view = null;

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

    Switch switchGestionTempBassin;
    SeekBar sliderGestionTempBassin;
    TextView labelGestionTempBassin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chauffage_layout, container, false);

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

        switchGestionTempBassin = view.findViewById(R.id.switch_gestion_temp_bassin);
        sliderGestionTempBassin = view.findViewById(R.id.seekbar_gestion_temp_bassin);
        labelGestionTempBassin = view.findViewById(R.id.texte_gestion_temp_bassin);

        viewBoutonMarche.setOnClickListener(this);
        viewBoutonArret.setOnClickListener(this);
        viewBoutonAuto.setOnClickListener(this);

        switchGestionTempBassin.setOnClickListener(this);
        sliderGestionTempBassin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                labelGestionTempBassin.setText(progress + " °C");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Donnees.instance().definirTemperatureConsigne(seekBar.getProgress());
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageChauffage, "temperature_consigne=" + Donnees.instance().obtenirTemperatureConsigne(), false);
            }
        });

        update();

        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            modeAEteModifie();

            viewConsommations.setVisibility(Donnees.instance().obtenirTypeAppareil() == Donnees.MYOZONEX ? View.VISIBLE : View.GONE);
            labelConsommations.setText("Date : " + Donnees.instance().obtenirDateConso(Donnees.Equipement.Chauffage) + "\nHeures pleines : " + Donnees.instance().obtenirConsoHP(Donnees.Equipement.Chauffage) + " kWh \nHeures creuses : " + Donnees.instance().obtenirConsoHC(Donnees.Equipement.Chauffage) + " kWh");

            switchGestionTempBassin.setChecked(Donnees.instance().obtenirControlePompeFiltration() == Donnees.CONTROLE_POMPE_FILTRATION);
            gestionTempBassinAEteModifie();
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
            case R.id.switch_gestion_temp_bassin:
                switchGestionTempBassinValueChanged((Switch) v.findViewById(v.getId()));
                break;
            default:
                break;
        }
    }

    private void modifierMode(LinearLayout sender) {
        if (Donnees.instance().obtenirActiviteIHM()) {
            int etat = Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Chauffage);

            if (sender == viewBoutonAuto) {
                etat = (etat == Donnees.AUTO_MARCHE) ? Donnees.AUTO_MARCHE : Donnees.AUTO_ARRET;
            } else if (sender == viewBoutonMarche) {
                etat = Donnees.MARCHE;
            } else {
                etat = Donnees.ARRET;
            }

            Donnees.instance().definirEtatEquipement(Donnees.Equipement.Chauffage, etat);
            modeAEteModifie();
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageChauffage, "etat=" + etat, false);
        }
    }

    private void modeAEteModifie() {
        int mode = Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Chauffage);

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

    private void switchGestionTempBassinValueChanged(Switch sender) {
        Donnees.instance().definirControlePompeFiltration(sender.isChecked() ? Donnees.CONTROLE_POMPE_FILTRATION : Donnees.CONTROLE_PAR_POMPE_FILTRATION);
        gestionTempBassinAEteModifie();
        Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageChauffage, "gestion_temperature=" + Donnees.instance().obtenirControlePompeFiltration(), false);
    }

    private void gestionTempBassinAEteModifie() {
        switchGestionTempBassin.setEnabled(Donnees.instance().obtenirActiviteIHM());
        sliderGestionTempBassin.setEnabled(Donnees.instance().obtenirActiviteIHM());
        
        if (!sliderGestionTempBassin.isPressed()) {
            sliderGestionTempBassin.setProgress(Donnees.instance().obtenirTemperatureConsigne());
        }
    }
}
