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
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v4.view.MotionEventCompat.getPointerCount;

public class FragmentDonnees extends Fragment implements View.OnClickListener {
    View view = null;

    // Tout orientations
    LinearLayout layoutPh;
    LinearLayout layoutORP;

    // Orientation portrait
    LinearLayout globalLayoutPortrait;
    TextView texteAucunCapteurs;
    LinearLayout layoutTemperatureBassin;
    TextView texteTemperatureBassin;
    TextView valeurTemperatureBassin;
    LinearLayout layoutTemperatureInterne;
    TextView texteTemperatureInterne;
    TextView valeurTemperatureInterne;
    LinearLayout layoutHumiditeInterne;
    TextView texteHumiditeInterne;
    TextView valeurHumiditeInterne;
    LinearLayout layoutPressionAtmInterne;
    TextView textePressionAtmInterne;
    TextView valeurPressionAtmInterne;
    LinearLayout layoutTemperatureExterne;
    TextView texteTemperatureExterne;
    TextView valeurTemperatureExterne;
    LinearLayout layoutHumiditeExterne;
    TextView texteHumiditeExterne;
    TextView valeurHumiditeExterne;
    LinearLayout layoutPressionAtmExterne;
    TextView textePressionAtmExterne;
    TextView valeurPressionAtmExterne;
    TextView textePh;
    TextView valeurPh;
    TextView texteORP;
    TextView valeurORP;
    LinearLayout layoutPression;
    TextView textePression;
    TextView valeurPression;
    LinearLayout layoutAmpero;
    TextView texteAmpero;
    TextView valeurAmpero;
    LinearLayout layout_4_20_Libre;
    TextView texte_4_20_Libre;
    TextView valeur_4_20_Libre;

    // Orientation paysage
    HorizontalScrollView globalLayoutPaysage;
    Button boutonSynoptique;
    TextView texteDonneesPh;
    TextView texteActifORP;
    TextView texteLibreActifAmpero;
    TextView texteDonneesAmpero;
    View ligneSepORP;
    TextView texteDonneesORP;
    LinearLayout layoutTemperatures;
    View ligneSepTemperatures;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.donnees_layout, container, false);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            globalLayoutPortrait = view.findViewById(R.id.global_layout);
            texteAucunCapteurs = (TextView) view.findViewById(R.id.texte_aucun_capteurs);
            layoutTemperatureBassin = view.findViewById(R.id.layout_temperature_bassin);
            texteTemperatureBassin = (TextView) view.findViewById(R.id.texte_temperature_bassin);
            valeurTemperatureBassin = (TextView) view.findViewById(R.id.valeur_temperature_bassin);
            layoutTemperatureInterne = view.findViewById(R.id.layout_temperature_interne);
            texteTemperatureInterne = (TextView) view.findViewById(R.id.texte_temperature_interne);
            valeurTemperatureInterne = (TextView) view.findViewById(R.id.valeur_temperature_interne);
            layoutHumiditeInterne = view.findViewById(R.id.layout_humidite_interne);
            texteHumiditeInterne = (TextView) view.findViewById(R.id.texte_humidite_interne);
            valeurHumiditeInterne = (TextView) view.findViewById(R.id.valeur_humidite_interne);
            layoutPressionAtmInterne = view.findViewById(R.id.layout_pression_atm_interne);
            textePressionAtmInterne = (TextView) view.findViewById(R.id.texte_pression_atm_interne);
            valeurPressionAtmInterne = (TextView) view.findViewById(R.id.valeur_pression_atm_interne);
            layoutTemperatureExterne = view.findViewById(R.id.layout_temperature_externe);
            texteTemperatureExterne = (TextView) view.findViewById(R.id.texte_temperature_externe);
            valeurTemperatureExterne = (TextView) view.findViewById(R.id.valeur_temperature_externe);
            layoutHumiditeExterne = view.findViewById(R.id.layout_humidite_externe);
            texteHumiditeExterne = (TextView) view.findViewById(R.id.texte_humidite_externe);
            valeurHumiditeExterne = (TextView) view.findViewById(R.id.valeur_humidite_externe);
            layoutPressionAtmExterne = view.findViewById(R.id.layout_pression_atm_externe);
            textePressionAtmExterne = (TextView) view.findViewById(R.id.texte_pression_atm_externe);
            valeurPressionAtmExterne = (TextView) view.findViewById(R.id.valeur_pression_atm_externe);
            layoutPh = view.findViewById(R.id.layout_ph);
            textePh = (TextView) view.findViewById(R.id.texte_ph);
            valeurPh = (TextView) view.findViewById(R.id.valeur_ph);
            layoutORP = view.findViewById(R.id.layout_orp);
            texteORP = (TextView) view.findViewById(R.id.texte_orp);
            valeurORP = (TextView) view.findViewById(R.id.valeur_orp);
            layoutPression = view.findViewById(R.id.layout_pression);
            textePression = (TextView) view.findViewById(R.id.texte_pression);
            valeurPression = (TextView) view.findViewById(R.id.valeur_pression);
            layoutAmpero = view.findViewById(R.id.layout_ampero);
            texteAmpero = (TextView) view.findViewById(R.id.texte_ampero);
            valeurAmpero = (TextView) view.findViewById(R.id.valeur_ampero);
            layout_4_20_Libre = view.findViewById(R.id.layout_4_20_libre);
            texte_4_20_Libre = (TextView) view.findViewById(R.id.texte_4_20_libre);
            valeur_4_20_Libre = (TextView) view.findViewById(R.id.valeur_4_20_libre);
        } else {
            new ScaleListener((HorizontalScrollView) view.findViewById(R.id.horizontal_scroll),
                    (ScrollView) view.findViewById(R.id.vertical_scroll),
                    (AbsoluteLayout) view.findViewById(R.id.layout));

            globalLayoutPaysage = view.findViewById(R.id.horizontal_scroll);
            boutonSynoptique = (Button) view.findViewById(R.id.bouton_synoptique);
            layoutPh = (LinearLayout) view.findViewById(R.id.layout_ph);
            valeurPh = (TextView) view.findViewById(R.id.texte_valeur_ph);
            texteDonneesPh = (TextView) view.findViewById(R.id.texte_donnees_ph);
            layoutORP = (LinearLayout) view.findViewById(R.id.layout_orp);
            texteActifORP = (TextView) view.findViewById(R.id.texte_actif_orp);
            valeurAmpero = (TextView) view.findViewById(R.id.texte_valeur_ampero);
            texteLibreActifAmpero = (TextView) view.findViewById(R.id.texte_valeur_libre_actif);
            texteDonneesAmpero = (TextView) view.findViewById(R.id.texte_donnees_ampero);
            ligneSepORP = view.findViewById(R.id.ligne_sep_orp);
            valeurORP = (TextView) view.findViewById(R.id.texte_valeur_orp);
            texteDonneesORP = (TextView) view.findViewById(R.id.texte_donnees_orp);
            layoutTemperatures = (LinearLayout) view.findViewById(R.id.layout_temperatures);
            texteTemperatureBassin = (TextView) view.findViewById(R.id.texte_temp_bassin);
            valeurTemperatureBassin = (TextView) view.findViewById(R.id.texte_valeur_temp_bassin);
            ligneSepTemperatures = view.findViewById(R.id.ligne_sep_temperatures);
            texteTemperatureExterne = (TextView) view.findViewById(R.id.texte_temp_ext);
            valeurTemperatureExterne = (TextView) view.findViewById(R.id.texte_valeur_temp_ext);


            boutonSynoptique.setOnClickListener(this);
        }


        update();


        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                globalLayoutPortrait.setBackgroundResource(Donnees.instance().obtenirBackground());

                texteAucunCapteurs.setVisibility(!Donnees.instance().presence(Donnees.Capteur.TemperatureBassin)
                        && !Donnees.instance().presence(Donnees.Capteur.CapteurInterne)
                        && !Donnees.instance().presence(Donnees.Capteur.CapteurExterne)
                        && !Donnees.instance().presence(Donnees.Capteur.Ph)
                        && !Donnees.instance().presence(Donnees.Capteur.Redox)
                        && !Donnees.instance().presence(Donnees.Capteur.Pression)
                        && !Donnees.instance().presence(Donnees.Capteur.Ampero)
                        && !Donnees.instance().presence(Donnees.Capteur._4_20_Libre) ? View.VISIBLE : View.GONE);

                layoutTemperatureBassin.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
                valeurTemperatureBassin.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin) + " °C");

                layoutTemperatureInterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.CapteurInterne) ? View.VISIBLE : View.GONE);
                valeurTemperatureInterne.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureInterne) + " °C");

                layoutHumiditeInterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.CapteurInterne) ? View.VISIBLE : View.GONE);
                valeurHumiditeInterne.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.HumiditeInterne) + " %");

                layoutPressionAtmInterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.CapteurInterne) ? View.VISIBLE : View.GONE);
                valeurPressionAtmInterne.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.PressionAtmospheriqueInterne) + " hPa");

                layoutTemperatureExterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.CapteurExterne) ? View.VISIBLE : View.GONE);
                valeurTemperatureExterne.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureExterne) + " °C");

                layoutHumiditeExterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.CapteurExterne) ? View.VISIBLE : View.GONE);
                valeurHumiditeExterne.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.HumiditeExterne) + " %");

                layoutPressionAtmExterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.CapteurExterne) ? View.VISIBLE : View.GONE);
                valeurPressionAtmExterne.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.PressionAtmospheriqueExterne) + " hPa");

                layoutPh.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ph) ? View.VISIBLE : View.GONE);
                valeurPh.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.Ph));

                layoutORP.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) ? View.VISIBLE : View.GONE);
                valeurORP.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.Redox) + " mV");

                layoutPression.setVisibility(Donnees.instance().presence(Donnees.Capteur.Pression) ? View.VISIBLE : View.GONE);
                valeurPression.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.Pression) + " bar");

                layoutAmpero.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
                valeurAmpero.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.Ampero) + " ppm");

                layout_4_20_Libre.setVisibility(Donnees.instance().presence(Donnees.Capteur._4_20_Libre) ? View.VISIBLE : View.GONE);
                valeur_4_20_Libre.setText(Donnees.instance().obtenirValeur(Donnees.Capteur._4_20_Libre));
            } else {
                globalLayoutPaysage.setBackgroundResource(Donnees.instance().obtenirBackground());

                layoutPh.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ph) ? View.VISIBLE : View.GONE);
                valeurPh.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.Ph));
                texteDonneesPh.setVisibility(View.GONE);

                layoutORP.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) || Donnees.instance().presence(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
                texteActifORP.setVisibility(View.GONE);
                valeurAmpero.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
                valeurAmpero.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.Ampero) + " ppm");
                texteLibreActifAmpero.setVisibility(View.GONE);
                texteDonneesAmpero.setVisibility(View.GONE);
                ligneSepORP.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) && Donnees.instance().presence(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
                valeurORP.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) ? View.VISIBLE : View.GONE);
                valeurORP.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.Redox) + " mV");
                texteDonneesORP.setVisibility(View.GONE);

                layoutTemperatures.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) || Donnees.instance().presence(Donnees.Capteur.CapteurExterne) ? View.VISIBLE : View.GONE);
                texteTemperatureBassin.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
                valeurTemperatureBassin.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
                valeurTemperatureBassin.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin) + " °C");
                ligneSepTemperatures.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) && Donnees.instance().presence(Donnees.Capteur.CapteurExterne) ? View.VISIBLE : View.GONE);
                texteTemperatureExterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureExterne) ? View.VISIBLE : View.GONE);
                valeurTemperatureExterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureExterne) ? View.VISIBLE : View.GONE);
                valeurTemperatureExterne.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureExterne) + " °C");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_synoptique:
                MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_synoptique_layout));
                break;
            default:
                break;
        }
    }
}
