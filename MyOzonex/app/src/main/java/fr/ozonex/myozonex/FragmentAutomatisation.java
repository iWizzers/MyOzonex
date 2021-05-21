package fr.ozonex.myozonex;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class FragmentAutomatisation extends Fragment implements View.OnClickListener {
    View view = null;

    Switch switchHeuresCreusesAuto;

    Switch switchDonneesEquipementsAuto;

    Switch switchPompeFiltrationAuto;
    TextView labelPompeFiltrationAuto;

    TextView labelAsservissementsAuto;
    LinearLayout viewAsservissementPhPlusAuto;
    Switch switchAsservissementPhPlusAuto;
    LinearLayout viewAsservissementPhMoinsAuto;
    Switch switchAsservissementPhMoinsAuto;
    LinearLayout viewAsservissementOrpAuto;
    Switch switchAsservissementOrpAuto;

    LinearLayout viewConsigneOrpAuto;
    Switch switchConsigneOrpAuto;
    TextView labelConsigneOrpAuto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.automatisation_layout, container, false);

        switchHeuresCreusesAuto = view.findViewById(R.id.switch_heures_creuses_auto);

        switchDonneesEquipementsAuto = view.findViewById(R.id.switch_donnees_quipements_auto);

        switchPompeFiltrationAuto = view.findViewById(R.id.switch_pompe_filtration_auto);
        labelPompeFiltrationAuto = view.findViewById(R.id.texte_pompe_filtration_auto);

        labelAsservissementsAuto = view.findViewById(R.id.texte_asservissements_auto);
        viewAsservissementPhPlusAuto = view.findViewById(R.id.layout_asservissement_ph_plus_auto);
        switchAsservissementPhPlusAuto = view.findViewById(R.id.switch_asservissement_ph_plus_auto);
        viewAsservissementPhMoinsAuto = view.findViewById(R.id.layout_asservissement_ph_moins_auto);
        switchAsservissementPhMoinsAuto = view.findViewById(R.id.switch_asservissement_ph_moins_auto);
        viewAsservissementOrpAuto = view.findViewById(R.id.layout_asservissement_orp_auto);
        switchAsservissementOrpAuto = view.findViewById(R.id.switch_asservissement_orp_auto);

        viewConsigneOrpAuto = view.findViewById(R.id.layout_consigne_orp_auto);
        switchConsigneOrpAuto = view.findViewById(R.id.switch_consigne_orp_auto);
        labelConsigneOrpAuto = view.findViewById(R.id.texte_consigne_orp_auto);

        switchHeuresCreusesAuto.setOnClickListener(this);
        switchDonneesEquipementsAuto.setOnClickListener(this);
        switchPompeFiltrationAuto.setOnClickListener(this);
        switchAsservissementPhPlusAuto.setOnClickListener(this);
        switchAsservissementPhMoinsAuto.setOnClickListener(this);
        switchAsservissementOrpAuto.setOnClickListener(this);
        switchConsigneOrpAuto.setOnClickListener(this);

        update();

        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            switchHeuresCreusesAuto.setChecked(Donnees.instance().obtenirHeuresCreusesAuto());
            heuresCreusesAutoAEteModifie();

            switchDonneesEquipementsAuto.setChecked(Donnees.instance().obtenirDonneesEquipementsAuto());
            donneesEquipementsAutoAEteModifie();

            switchPompeFiltrationAuto.setChecked(Donnees.instance().obtenirPlagesAuto());
            pompeFiltrationAutoAEteModifie();

            labelAsservissementsAuto.setText(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) || Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) || Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? "Estimation des valeurs en fonction du volume du bassin" : "Nécessite l'installation d'au moins un régulateur");

            switchAsservissementPhPlusAuto.setChecked(Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.PhPlus));
            asservissementPhPlusAutoAEteModifie();

            switchAsservissementPhMoinsAuto.setChecked(Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.PhMoins));
            asservissementPhMoinsAutoAEteModifie();

            switchAsservissementOrpAuto.setChecked(Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.Orp));
            asservissementOrpAutoAEteModifie();

            switchConsigneOrpAuto.setChecked(Donnees.instance().obtenirPointConsigneOrpAuto());
            consigneOrpAutoAEteModifie();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_heures_creuses_auto:
                switchHeuresCreusesAutoValueAutoChanged((Switch) v.findViewById(v.getId()));
                break;
            case R.id.switch_donnees_quipements_auto:
                switchDonneesEquipementsAutoValueChanged((Switch) v.findViewById(v.getId()));
                break;
            case R.id.switch_pompe_filtration_auto:
                switchPompeFiltrationAutoValueChanged((Switch) v.findViewById(v.getId()));
                break;
            case R.id.switch_asservissement_ph_plus_auto:
                switchAsservissmentPhPlusAutoValueChanged((Switch) v.findViewById(v.getId()));
                break;
            case R.id.switch_asservissement_ph_moins_auto:
                switchAsservissmentPhMoinsAutoValueChanged((Switch) v.findViewById(v.getId()));
                break;
            case R.id.switch_asservissement_orp_auto:
                switchAsservissmentOrpAutoValueChanged((Switch) v.findViewById(v.getId()));
                break;
            case R.id.switch_consigne_orp_auto:
                switchConsigneOrpAutoValueChanged((Switch) v.findViewById(v.getId()));
                break;
            default:
                break;
        }
    }

    private void switchHeuresCreusesAutoValueAutoChanged(Switch sender) {
        Donnees.instance().definirHeuresCreusesAuto(sender.isChecked());
        heuresCreusesAutoAEteModifie();
        Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAutomatisation, "heures_creuses=" + Donnees.instance().obtenirHeuresCreusesAuto(), false);
    }

    private void heuresCreusesAutoAEteModifie() {
        switchHeuresCreusesAuto.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
    }

    private void switchDonneesEquipementsAutoValueChanged(Switch sender) {
        Donnees.instance().definirDonneesEquipementsAuto(sender.isChecked());
        donneesEquipementsAutoAEteModifie();
        Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAutomatisation, "donnees_equipement=" + Donnees.instance().obtenirDonneesEquipementsAuto(), false);
    }

    private void donneesEquipementsAutoAEteModifie() {
        switchDonneesEquipementsAuto.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
    }

    private void switchPompeFiltrationAutoValueChanged(Switch sender) {
        Donnees.instance().definirPlagesAuto(sender.isChecked());
        pompeFiltrationAutoAEteModifie();
        Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAutomatisation, "plages_auto=" + Donnees.instance().obtenirDonneesEquipementsAuto() + "&modif_plage_auto=1", false);
    }

    private void pompeFiltrationAutoAEteModifie() {
        switchPompeFiltrationAuto.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.TemperatureBassin) && Donnees.instance().obtenirCodeInstallateur());

        labelPompeFiltrationAuto.setText(switchPompeFiltrationAuto.isEnabled() ? switchPompeFiltrationAuto.isChecked() ? Donnees.instance().obtenirModifPlagesAuto() ? "Calcul en cours..." : "Temps filtration / jour : " + Donnees.instance().obtenirTempsFiltrationJour() : "Estimation en fonction de la température du bassin" : "Nécessite la température du bassin");
    }

    private void switchAsservissmentPhPlusAutoValueChanged(Switch sender) {
        Donnees.instance().definirAsservissementAuto(Donnees.Equipement.PhPlus, sender.isChecked());
        asservissementPhPlusAutoAEteModifie();
        Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAutomatisation, "asservissement_ph_plus=" + Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.PhPlus), false);
    }

    private void asservissementPhPlusAutoAEteModifie() {
        viewAsservissementPhPlusAuto.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) ? View.VISIBLE : View.GONE);
        switchAsservissementPhPlusAuto.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
    }

    private void switchAsservissmentPhMoinsAutoValueChanged(Switch sender) {
        Donnees.instance().definirAsservissementAuto(Donnees.Equipement.PhMoins, sender.isChecked());
        asservissementPhMoinsAutoAEteModifie();
        Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAutomatisation, "asservissement_ph_moins=" + Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.PhMoins), false);
    }

    private void asservissementPhMoinsAutoAEteModifie() {
        viewAsservissementPhMoinsAuto.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? View.VISIBLE : View.GONE);
        switchAsservissementPhMoinsAuto.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
    }

    private void switchAsservissmentOrpAutoValueChanged(Switch sender) {
        Donnees.instance().definirAsservissementAuto(Donnees.Equipement.Orp, sender.isChecked());
        asservissementOrpAutoAEteModifie();
        Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAutomatisation, "asservissement_orp=" + Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.Orp), false);
    }

    private void asservissementOrpAutoAEteModifie() {
        viewAsservissementOrpAuto.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);
        switchAsservissementOrpAuto.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());
    }

    private void switchConsigneOrpAutoValueChanged(Switch sender) {
        Donnees.instance().definirPointConsigneOrpAuto(sender.isChecked());
        consigneOrpAutoAEteModifie();
        Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAutomatisation, "consigne_orp_auto=" + Donnees.instance().obtenirPointConsigneOrpAuto(), false);
    }

    private void consigneOrpAutoAEteModifie() {
        viewConsigneOrpAuto.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);
        switchConsigneOrpAuto.setEnabled(Donnees.instance().obtenirActiviteIHM() && Donnees.instance().obtenirCodeInstallateur());

        calculConsigneOrpAuto();
    }

    private void calculConsigneOrpAuto() {
        if (Donnees.instance().obtenirPointConsigneOrpAuto()) {
            if (Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.TemperatureBassin)) {
                if (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.TemperatureBassin) <= Global.TEMPERATURE_MINIMUM_CONSIGNE_ORP) {
                    Donnees.instance().definirPointConsigneOrp(Donnees.instance().obtenirPointConsigneOrp());
                    Donnees.instance().definirPointConsigneAmpero(Donnees.instance().obtenirPointConsigneAmpero());
                } else if (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.TemperatureBassin) <= Global.TEMPERATURE_MAXIMUM_CONSIGNE_ORP) {
                    Donnees.instance().definirPointConsigneOrp(Donnees.instance().obtenirPointConsigneOrp() + ((int)Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.TemperatureBassin) - Global.TEMPERATURE_MINIMUM_CONSIGNE_ORP) * Global.AJOUT_CONSIGNE_ORP);
                    Donnees.instance().definirPointConsigneAmpero(Donnees.instance().obtenirPointConsigneAmpero() + (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.TemperatureBassin) - Global.TEMPERATURE_MINIMUM_CONSIGNE_ORP) * Global.AJOUT_CONSIGNE_AMPERO);
                } else {
                    Donnees.instance().definirPointConsigneOrp(Donnees.instance().obtenirPointConsigneOrp() + (Global.TEMPERATURE_MAXIMUM_CONSIGNE_ORP - Global.TEMPERATURE_MINIMUM_CONSIGNE_ORP) * Global.AJOUT_CONSIGNE_ORP);
                    Donnees.instance().definirPointConsigneAmpero(Donnees.instance().obtenirPointConsigneAmpero() + (Global.TEMPERATURE_MAXIMUM_CONSIGNE_ORP - Global.TEMPERATURE_MINIMUM_CONSIGNE_ORP) * Global.AJOUT_CONSIGNE_AMPERO);
                }
            } else {
                Donnees.instance().definirPointConsigneOrp(Donnees.instance().obtenirPointConsigneOrp());
                Donnees.instance().definirPointConsigneAmpero(Donnees.instance().obtenirPointConsigneAmpero());
            }
        } else {
            Donnees.instance().definirPointConsigneOrp(Donnees.instance().obtenirPointConsigneOrp());
            Donnees.instance().definirPointConsigneAmpero(Donnees.instance().obtenirPointConsigneAmpero());
        }

        labelConsigneOrpAuto.setText(switchConsigneOrpAuto.isEnabled() ? switchConsigneOrpAuto.isChecked() ? Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) && Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ampero) ? "Nouveaux points de consigne : " + Donnees.instance().obtenirPointConsigneOrp() + " mV / " + Donnees.instance().obtenirPointConsigneAmpero() + " ppm" : Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ampero) ? "Nouveau point de consigne : " + Donnees.instance().obtenirPointConsigneAmpero() + " ppm" : "Nouveau point de consigne : " + Donnees.instance().obtenirPointConsigneOrp() + " mV" : "Estimation en fonction de la température du bassin" : Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.TemperatureBassin) ? "Nécessite le régulateur ORP" : Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? "Nécessite la température du bassin" : "Nécessite la température du bassin et le régulateur ORP");
    }
}
