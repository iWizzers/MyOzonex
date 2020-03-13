package fr.ozonex.myozonex;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class FragmentBassin extends Fragment implements View.OnClickListener {
    View view = null;

    // Tout orientations
    TextView texteDonnees;
    LinearLayout layoutAsservissement;
    TextView texteDonneesAsservissement;

    // Orientation portrait
    LinearLayout globalLayoutPortrait;

    // Orientation paysage
    HorizontalScrollView globalLayoutPaysage;
    ImageButton boutonRetour;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bassin_layout, container, false);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            globalLayoutPortrait = view.findViewById(R.id.global_layout);
        } else {
            new ScaleListener((HorizontalScrollView) view.findViewById(R.id.horizontal_scroll),
                    (ScrollView) view.findViewById(R.id.vertical_scroll),
                    (AbsoluteLayout) view.findViewById(R.id.layout));

            globalLayoutPaysage = view.findViewById(R.id.horizontal_scroll);
            boutonRetour = view.findViewById(R.id.bouton_retour);

            boutonRetour.setOnClickListener(this);
        }

        texteDonnees = view.findViewById(R.id.texte_donnees);
        layoutAsservissement = view.findViewById(R.id.layout_regulations);
        texteDonneesAsservissement = view.findViewById(R.id.texte_donnees_regulations);


        update();


        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                globalLayoutPortrait.setBackgroundResource(Donnees.instance().obtenirBackground());
            } else {
                globalLayoutPaysage.setBackgroundResource(Donnees.instance().obtenirBackground());
            }

            texteDonnees.setText("Volume : " + Donnees.instance().obtenirVolumeBassin() + " m3");
            layoutAsservissement.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ph)
                    || Donnees.instance().presence(Donnees.Capteur.Redox)
                    || Donnees.instance().presence(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
            texteDonneesAsservissement.setText("Type de refoulements : " + Donnees.instance().obtenirTypeRefoulement() +
                    "\nType de régulation : " + Donnees.instance().obtenirTypeAsservissement());

            if (Donnees.instance().obtenirTypeAsservissement() == Donnees.ASSERVISSEMENT_LIN) {
                texteDonneesAsservissement.setText(texteDonneesAsservissement.getText() +
                        "\nTemps sécurité injection : " + Donnees.instance().obtenirTempsSecuriteInjection() + " minute(s)" +
                        "\nHystéresis sécurité injection pH : " + Donnees.instance().obtenirHysteresisInjectionPh() +
                        "\nHystéresis sécurité injection ORP : " + Donnees.instance().obtenirHysteresisInjectionORP() + " mV" +
                        "\nHystéresis sécurité injection ampéro : " + Donnees.instance().obtenirHysteresisInjectionAmpero());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_retour:
                if (Donnees.instance().obtenirPageSource() == Donnees.PAGE_SYNOPTIQUE) {
                    MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_synoptique_layout));
                } else {
                    MainActivity.instance().onNavigationItemSelected(MainActivity.instance().menu.findItem(R.id.nav_menu_layout));
                }
                break;
            default:
                break;
        }
    }
}
