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

public class FragmentFiltre extends Fragment implements View.OnClickListener {
    View view = null;

    // Tout orientations
    TextView texteConso;

    // Orientation portrait
    LinearLayout globalLayoutPortrait;
    LinearLayout layoutConfiguration;
    TextView texteDonneesConfiguration;

    // Orientation paysage
    HorizontalScrollView globalLayoutPaysage;
    ImageButton boutonRetour;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filtre_layout, container, false);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            globalLayoutPortrait = view.findViewById(R.id.global_layout);
            layoutConfiguration = view.findViewById(R.id.layout_configuration);
            texteDonneesConfiguration = (TextView) view.findViewById(R.id.texte_donnees_configuration);
        } else {
            new ScaleListener((HorizontalScrollView) view.findViewById(R.id.horizontal_scroll),
                    (ScrollView) view.findViewById(R.id.vertical_scroll),
                    (AbsoluteLayout) view.findViewById(R.id.layout));

            globalLayoutPaysage = view.findViewById(R.id.horizontal_scroll);
            boutonRetour = (ImageButton) view.findViewById(R.id.bouton_retour);

            boutonRetour.setOnClickListener(this);
        }

        texteConso = (TextView) view.findViewById(R.id.texte_donnees_conso);


        update();


        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                globalLayoutPortrait.setBackgroundResource(Donnees.instance().obtenirBackground());
                layoutConfiguration.setVisibility(Donnees.instance().presence(Donnees.Capteur.Pression) ? View.VISIBLE : View.GONE);
                modifierConfiguration();
            } else {
                globalLayoutPaysage.setBackgroundResource(Donnees.instance().obtenirBackground());
            }

            modifierDernierLavage();
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

    private void modifierDernierLavage() {
        String donnees;
        donnees = "Date : ";
        donnees += Donnees.instance().obtenirDateDernierLavage();
        donnees += "\nPression après rinçage / lavage : ";
        donnees += Donnees.instance().obtenirPressionApresLavage() + " bar";
        donnees += "\nProchain rinçage / lavage si pression > ";
        donnees += Donnees.instance().obtenirPressionProchainLavage() + " bar";

        texteConso.setText(donnees);
    }

    private void modifierConfiguration() {
        String donnees;
        donnees = "Seuil sécurité surpression : ";
        donnees += Donnees.instance().obtenirSeuilSecuriteSurpression() + " bar";
        donnees += "\nSeuil haut pression : ";
        donnees += Donnees.instance().obtenirSeuilHautPression() + " bar";
        donnees += "\nSeuil bas pression : ";
        donnees += Donnees.instance().obtenirSeuilBasPression() + " bar";

        texteDonneesConfiguration.setText(donnees);
    }
}
