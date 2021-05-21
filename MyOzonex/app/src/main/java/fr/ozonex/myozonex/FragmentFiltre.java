package fr.ozonex.myozonex;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentFiltre extends Fragment {
    View view = null;

    LinearLayout viewDonnees;
    TextView labelDonnees;

    TextView labelDernierRincage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filtre_layout, container, false);

        viewDonnees = view.findViewById(R.id.layout_donnees);
        labelDonnees = view.findViewById(R.id.texte_donnees);
        labelDernierRincage = view.findViewById(R.id.texte_dernier_rincage);

        update();

        return view;
    }

    public void update() {
        if ((view != null) && isAdded()) {
            viewDonnees.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Pression) ? View.VISIBLE : View.GONE);
            labelDonnees.setText("Seuil rinçage : " + Donnees.instance().obtenirSeuilRincage() + " %\n\nSeuil sécurité surpression : " + String.format("%.1f", Donnees.instance().obtenirSeuilSecuriteSurpression()) + " bar\nSeuil haut pression : " + String.format("%.1f", Donnees.instance().obtenirSeuilHautPression()) + " bar\nSeuil bas pression : " + String.format("%.1f", Donnees.instance().obtenirSeuilBasPression()) + " bar");

            labelDernierRincage.setText("Date : " + Donnees.instance().obtenirDateDernierLavage());
            if (Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Pression)) {
                labelDernierRincage.append("\nPression après rinçage / lavage : " + String.format("%.1f", Donnees.instance().obtenirPressionApresLavage()) + " bar\nProchain rinçage / lavage si pression > " + String.format("%.1f", Donnees.instance().obtenirPressionProchainLavage()) + " bar");
            }
        }
    }
}
