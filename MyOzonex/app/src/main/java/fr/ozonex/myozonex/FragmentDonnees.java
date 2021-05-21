package fr.ozonex.myozonex;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class FragmentDonnees extends Fragment implements View.OnClickListener {
    View view = null;

    ScrollView scrollView;

    ImageView imageDerniereConnexion;
    TextView texteDerniereConnexion;

    LinearLayout layoutPh;
    TextView valeurPh;

    LinearLayout layoutRedox;
    TextView valeurRedox;

    LinearLayout layoutAmpero;
    TextView valeurAmpero;

    LinearLayout layoutTempBassin;
    TextView valeurTempBassin;

    LinearLayout layoutPression;
    TextView valeurPression;

    LinearLayout layoutTempExterne;
    TextView valeurTemperatureExterne;

    LinearLayout layoutHumiditeExterne;
    TextView valeurHumiditeExterne;

    LinearLayout layoutPressionAtmExterne;
    TextView valeurPressionAtmExterne;

    LinearLayout layoutTempInterne;
    TextView valeurTemperatureInterne;

    LinearLayout layoutHumiditeInterne;
    TextView valeurHumiditeInterne;

    LinearLayout layoutPressionAtmInterne;
    TextView valeurPressionAtmInterne;

    int typeModification = 0;
    LinearLayout viewQuestion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.donnees_layout, container, false);

        scrollView = view.findViewById(R.id.scrollview);

        imageDerniereConnexion = view.findViewById(R.id.image_derniere_connexion);
        texteDerniereConnexion = view.findViewById(R.id.texte_derniere_connexion);

        layoutPh = view.findViewById(R.id.layout_ph);
        valeurPh = view.findViewById(R.id.texte_valeur_ph);
        valeurPh.setOnClickListener(this);

        layoutRedox = view.findViewById(R.id.layout_redox);
        valeurRedox = view.findViewById(R.id.texte_valeur_redox);
        valeurRedox.setOnClickListener(this);

        layoutAmpero = view.findViewById(R.id.layout_ampero);
        valeurAmpero = view.findViewById(R.id.texte_valeur_ampero);
        valeurAmpero.setOnClickListener(this);

        layoutTempBassin = view.findViewById(R.id.layout_temperature_bassin);
        valeurTempBassin = view.findViewById(R.id.texte_valeur_temperature_bassin);
        valeurTempBassin.setOnClickListener(this);

        layoutPression = view.findViewById(R.id.layout_pression);
        valeurPression = view.findViewById(R.id.texte_valeur_pression);
        valeurPression.setOnClickListener(this);

        layoutTempExterne = view.findViewById(R.id.layout_temperature_externe);
        valeurTemperatureExterne = view.findViewById(R.id.texte_valeur_temperature_externe);

        layoutHumiditeExterne = view.findViewById(R.id.layout_humidite_externe);
        valeurHumiditeExterne = view.findViewById(R.id.texte_valeur_humidite_externe);

        layoutPressionAtmExterne = view.findViewById(R.id.layout_pression_atm_externe);
        valeurPressionAtmExterne = view.findViewById(R.id.texte_valeur_pression_atm_externe);

        layoutTempInterne = view.findViewById(R.id.layout_temperature_interne);
        valeurTemperatureInterne = view.findViewById(R.id.texte_valeur_temperature_interne);

        layoutHumiditeInterne = view.findViewById(R.id.layout_humidite_interne);
        valeurHumiditeInterne = view.findViewById(R.id.texte_valeur_humidite_interne);

        layoutPressionAtmInterne = view.findViewById(R.id.layout_pression_atm_interne);
        valeurPressionAtmInterne = view.findViewById(R.id.texte_valeur_pression_atm_interne);

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
            imageDerniereConnexion.setImageResource(Donnees.instance().obtenirActiviteIHM() ? android.R.drawable.presence_online : android.R.drawable.presence_offline);
            texteDerniereConnexion.setText("Dernière connexion le " + Donnees.instance().obtenirDateDerniereConnexion() + " à " + Donnees.instance().obtenirHeureDerniereConnexion());

            layoutPh.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) ? View.VISIBLE : View.GONE);
            valeurPh.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Ph) ? String.format("%.2f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Ph)) : "Err");
            valeurPh.setTextColor(Donnees.instance().obtenirEtatLectureCapteurs() ? Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.PhPlus) || Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.PhMoins) ? Color.rgb(255, 127, 0) : !Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Ph) || (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) && (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Ph) < (Donnees.instance().obtenirPointConsignePh() - Donnees.instance().obtenirHysteresisPhPlus()))) || (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) && (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Ph) > (Donnees.instance().obtenirPointConsignePh() + Donnees.instance().obtenirHysteresisPhMoins()))) ? Color.RED : Color.GREEN : Color.rgb(245, 245, 220));

            layoutRedox.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) ? View.VISIBLE : View.GONE);
            valeurRedox.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Redox) ? String.format("%.0f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Redox)) + " mV" : "Err");
            valeurRedox.setTextColor(Donnees.instance().obtenirEtatLectureCapteurs() ? Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.Orp) ? Color.rgb(255, 127, 0) : !Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Redox) || (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) && (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Redox) < (Donnees.instance().obtenirPointConsigneOrp() - Donnees.instance().obtenirHysteresisOrp()))) ? Color.RED : Color.GREEN : Color.rgb(245, 245, 220));

            layoutAmpero.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
            valeurAmpero.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Ampero) ? String.format("%.2f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Ampero)) + " ppm" : "Err");
            valeurAmpero.setTextColor(Donnees.instance().obtenirEtatLectureCapteurs() ? Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.Orp) ? Color.rgb(255, 127, 0) : !Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Ampero) || (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) && (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Ampero) < (Donnees.instance().obtenirPointConsigneAmpero() - Donnees.instance().obtenirHysteresisAmpero()))) ? Color.RED : Color.GREEN : Color.rgb(245, 245, 220));

            layoutTempBassin.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
            valeurTempBassin.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.TemperatureBassin) ? String.format("%.1f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.TemperatureBassin)) + " °C" : "Err");
            valeurTempBassin.setTextColor(Donnees.instance().obtenirEtatLectureCapteurs() ? (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Chauffage) == Donnees.MARCHE) || (Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Chauffage) == Donnees.AUTO_MARCHE) ? Color.rgb(255, 127, 0) : !Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.TemperatureBassin) || (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) && (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.TemperatureBassin) < (double)Donnees.instance().obtenirTemperatureConsigne())) ? Color.RED : Color.GREEN : Color.rgb(245, 245, 220));

            layoutPression.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Pression) ? View.VISIBLE : View.GONE);
            valeurPression.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Pression) ? String.format("%.2f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Pression)) + " bar" : "Err");
            valeurPression.setTextColor(Donnees.instance().obtenirEtatLectureCapteurs() ? !Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.Pression) || (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Pression) < Donnees.instance().obtenirSeuilBasPression()) || (Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Pression) > Donnees.instance().obtenirSeuilHautPression()) ? Color.RED : Color.GREEN : Color.rgb(245, 245, 220));

            layoutTempExterne.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.CapteurExterne) ? View.VISIBLE : View.GONE);
            valeurTemperatureExterne.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.TemperatureExterne) ? String.format("%.1f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.TemperatureExterne)) + " °C" : "Err");
            valeurTemperatureExterne.setTextColor(!Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.TemperatureExterne) ? Color.RED : Color.GREEN);

            layoutHumiditeExterne.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.CapteurExterne) ? View.VISIBLE : View.GONE);
            valeurHumiditeExterne.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.HumiditeExterne) ? String.format("%.1f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.HumiditeExterne)) + " %" : "Err");
            valeurHumiditeExterne.setTextColor(!Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.HumiditeExterne) ? Color.RED : Color.GREEN);

            layoutPressionAtmExterne.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.CapteurExterne) ? View.VISIBLE : View.GONE);
            valeurPressionAtmExterne.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.PressionAtmospheriqueExterne) ? String.format("%.0f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.PressionAtmospheriqueExterne)) + " hPa" : "Err");
            valeurPressionAtmExterne.setTextColor(!Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.PressionAtmospheriqueExterne) ? Color.RED : Color.GREEN);

            layoutTempInterne.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.CapteurInterne) ? View.VISIBLE : View.GONE);
            valeurTemperatureInterne.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.TemperatureInterne) ? String.format("%.1f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.TemperatureInterne)) + " °C" : "Err");
            valeurTemperatureInterne.setTextColor(!Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.TemperatureInterne) ? Color.RED : Color.GREEN);

            layoutHumiditeInterne.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.CapteurInterne) ? View.VISIBLE : View.GONE);
            valeurHumiditeInterne.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.HumiditeInterne) ? String.format("%.1f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.HumiditeInterne)) + " %" : "Err");
            valeurHumiditeInterne.setTextColor(!Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.HumiditeInterne) ? Color.RED : Color.GREEN);

            layoutPressionAtmInterne.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.CapteurInterne) ? View.VISIBLE : View.GONE);
            valeurPressionAtmInterne.setText(Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.PressionAtmospheriqueInterne) ? String.format("%.0f", Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.PressionAtmospheriqueInterne)) + " hPa" : "Err");
            valeurPressionAtmInterne.setTextColor(!Donnees.instance().obtenirEtatCapteur(Donnees.Capteur.PressionAtmospheriqueInterne) ? Color.RED : Color.GREEN);
        }
    }

    public void clignotement() {
        if ((view != null) && isAdded()) {
            if (!Donnees.instance().obtenirEtatLectureCapteurs()) {
                if (valeurPh.getCurrentTextColor() == Color.rgb(245, 245, 220)) {
                    valeurPh.setTextColor(Color.GRAY);
                    valeurRedox.setTextColor(Color.GRAY);
                    valeurAmpero.setTextColor(Color.GRAY);
                    valeurTempBassin.setTextColor(Color.GRAY);
                } else {
                    valeurPh.setTextColor(Color.rgb(245, 245, 220));
                    valeurRedox.setTextColor(Color.rgb(245, 245, 220));
                    valeurAmpero.setTextColor(Color.rgb(245, 245, 220));
                    valeurTempBassin.setTextColor(Color.rgb(245, 245, 220));
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.texte_valeur_ph:
                typeModification = 0;
                afficherQuestion(true, "Veuillez saisir la valeur mesurée dans la piscine");
                break;
            case R.id.texte_valeur_redox:
                typeModification = 1;
                afficherQuestion(true, "Veuillez saisir la valeur mesurée dans la piscine (en mV)");
                break;
            case R.id.texte_valeur_ampero:
                typeModification = 2;
                afficherQuestion(true, "Veuillez saisir la valeur mesurée dans la piscine (en ppm)");
                break;
            case R.id.texte_valeur_temperature_bassin:
                typeModification = 3;
                afficherQuestion(true, "Veuillez indiquer la température réelle (en °C)");
                break;
            case R.id.texte_valeur_pression:
                typeModification = 4;
                afficherQuestion(true, "Veuillez saisir la valeur mesurée dans la piscine (en bar)");
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
                Donnees.instance().definirEtalonnageCapteur(Donnees.Capteur.Ph, true, valeur);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageCapteurs, "type=pH&etalonnage=1&valeur_etalonnage=" + valeur, false);
                afficherQuestion(false, "");
                break;
            case 1:
                Donnees.instance().definirEtalonnageCapteur(Donnees.Capteur.Redox, true, valeur);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageCapteurs, "type=ORP&etalonnage=1&valeur_etalonnage=" + valeur, false);
                afficherQuestion(false, "");
                break;
            case 2:
                Donnees.instance().definirEtalonnageCapteur(Donnees.Capteur.Ampero, true, valeur);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageCapteurs, "type=Ampéro&etalonnage=1&valeur_etalonnage=" + valeur, false);
                afficherQuestion(false, "");
                break;
            case 3:
                Donnees.instance().definirEtalonnageCapteur(Donnees.Capteur.TemperatureBassin, true, valeur);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageCapteurs, "type=Température bassin&etalonnage=1&valeur_etalonnage=" + valeur, false);
                afficherQuestion(false, "");
                break;
            case 4:
                Donnees.instance().definirEtalonnageCapteur(Donnees.Capteur.Pression, true, valeur);
                Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageCapteurs, "type=Pression&etalonnage=1&valeur_etalonnage=" + valeur, false);
                afficherQuestion(false, "");
                break;
            default:
                break;
        }
    }
}
