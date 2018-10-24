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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v4.view.MotionEventCompat.getPointerCount;

public class FragmentDonnees extends Fragment implements View.OnClickListener {
    View view = null;

    // Orientation portrait
    TextView texteTemperatureBassin;
    TextView valeurTemperatureBassin;
    TextView texteTemperatureExterne;
    TextView valeurTemperatureExterne;
    TextView textePh;
    TextView valeurPh;
    TextView texteORP;
    TextView valeurORP;
    TextView textePression;
    TextView valeurPression;
    TextView texteAmpero;
    TextView valeurAmpero;
    TextView texte_4_20_Libre;
    TextView valeur_4_20_Libre;

    // Orientation paysage
    private ScaleGestureDetector mScaleGestureDetector;
    AbsoluteLayout layout;
    Button boutonSynoptique;
    LinearLayout layoutPh;
    TextView texteDonneesPh;
    LinearLayout layoutORP;
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
            texteTemperatureBassin = (TextView) view.findViewById(R.id.texte_temperature_bassin);
            valeurTemperatureBassin = (TextView) view.findViewById(R.id.valeur_temperature_bassin);
            texteTemperatureExterne = (TextView) view.findViewById(R.id.texte_temperature_externe);
            valeurTemperatureExterne = (TextView) view.findViewById(R.id.valeur_temperature_externe);
            textePh = (TextView) view.findViewById(R.id.texte_ph);
            valeurPh = (TextView) view.findViewById(R.id.valeur_ph);
            texteORP = (TextView) view.findViewById(R.id.texte_orp);
            valeurORP = (TextView) view.findViewById(R.id.valeur_orp);
            textePression = (TextView) view.findViewById(R.id.texte_pression);
            valeurPression = (TextView) view.findViewById(R.id.valeur_pression);
            texteAmpero = (TextView) view.findViewById(R.id.texte_ampero);
            valeurAmpero = (TextView) view.findViewById(R.id.valeur_ampero);
            texte_4_20_Libre = (TextView) view.findViewById(R.id.texte_4_20_libre);
            valeur_4_20_Libre = (TextView) view.findViewById(R.id.valeur_4_20_libre);
        } else {
            layout = (AbsoluteLayout) view.findViewById(R.id.layout);
            mScaleGestureDetector = new ScaleGestureDetector(MainActivity.instance(), new ScaleListener(layout));
            view.findViewById(R.id.horizontal_scroll).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (getPointerCount(event) == 2) {
                        mScaleGestureDetector.onTouchEvent(event);
                    } else {
                        return false;
                    }

                    return true;
                }
            });
            view.findViewById(R.id.vertical_scroll).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (getPointerCount(event) == 2) {
                        mScaleGestureDetector.onTouchEvent(event);
                    } else {
                        return false;
                    }

                    return true;
                }
            });

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
                texteTemperatureBassin.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
                valeurTemperatureBassin.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
                valeurTemperatureBassin.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin) + " °C");

                texteTemperatureExterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureLocal) ? View.VISIBLE : View.GONE);
                valeurTemperatureExterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureLocal) ? View.VISIBLE : View.GONE);
                valeurTemperatureExterne.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureLocal) + " °C");

                textePh.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ph) ? View.VISIBLE : View.GONE);
                valeurPh.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ph) ? View.VISIBLE : View.GONE);
                valeurPh.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.Ph));

                texteORP.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) ? View.VISIBLE : View.GONE);
                valeurORP.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) ? View.VISIBLE : View.GONE);
                valeurORP.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.Redox) + " mV");

                textePression.setVisibility(Donnees.instance().presence(Donnees.Capteur.Pression) ? View.VISIBLE : View.GONE);
                valeurPression.setVisibility(Donnees.instance().presence(Donnees.Capteur.Pression) ? View.VISIBLE : View.GONE);
                valeurPression.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.Pression) + " bar");

                texteAmpero.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
                valeurAmpero.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
                valeurAmpero.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.Ampero) + " ppm");

                texte_4_20_Libre.setVisibility(Donnees.instance().presence(Donnees.Capteur._4_20_Libre) ? View.VISIBLE : View.GONE);
                valeur_4_20_Libre.setVisibility(Donnees.instance().presence(Donnees.Capteur._4_20_Libre) ? View.VISIBLE : View.GONE);
                valeur_4_20_Libre.setText(Donnees.instance().obtenirValeur(Donnees.Capteur._4_20_Libre));
            } else {
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

                layoutTemperatures.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) || Donnees.instance().presence(Donnees.Capteur.TemperatureLocal) ? View.VISIBLE : View.GONE);
                texteTemperatureBassin.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
                valeurTemperatureBassin.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
                valeurTemperatureBassin.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin) + " °C");
                ligneSepTemperatures.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) && Donnees.instance().presence(Donnees.Capteur.TemperatureLocal) ? View.VISIBLE : View.GONE);
                texteTemperatureExterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureLocal) ? View.VISIBLE : View.GONE);
                valeurTemperatureExterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureLocal) ? View.VISIBLE : View.GONE);
                valeurTemperatureExterne.setText(Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureLocal) + " °C");
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
