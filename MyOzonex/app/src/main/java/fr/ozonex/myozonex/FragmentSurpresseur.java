package fr.ozonex.myozonex;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.sql.Time;
import java.text.DecimalFormat;

public class FragmentSurpresseur extends Fragment implements View.OnClickListener {
    View view = null;

    ImageButton boutonModifierPlage;
    int indexPlage;
    String heureMinimumPlage;
    String heureMaximumPlage;
    String heureDebutPlage;
    String heureFinPlage;

    // Tout orientations
    TextView texteConso;
    GridLayout widgetPlage1;
    ImageButton boutonSupprimerPlage1;
    ImageButton boutonModifierPlage1;
    TextView textePlage1;
    GridLayout widgetPlage2;
    ImageButton boutonSupprimerPlage2;
    ImageButton boutonModifierPlage2;
    TextView textePlage2;
    GridLayout widgetPlage3;
    ImageButton boutonSupprimerPlage3;
    ImageButton boutonModifierPlage3;
    TextView textePlage3;
    GridLayout widgetPlage4;
    ImageButton boutonSupprimerPlage4;
    ImageButton boutonModifierPlage4;
    TextView textePlage4;
    Button boutonAjouterPlage;

    // Orientation portrait
    LinearLayout globalLayoutPortrait;
    RadioGroup rgBoutonsMode;
    RadioButton rbAuto;
    RadioButton rbArret;
    RadioButton rbMarche;

    // Orientation paysage
    HorizontalScrollView globalLayoutPaysage;
    ImageButton boutonRetour;
    ImageView bouton3Etats;
    Button boutonAuto;
    Button boutonArret;
    Button boutonMarche;
    RenduPlage renduPlage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.surpresseur_layout, container, false);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            globalLayoutPortrait = view.findViewById(R.id.global_layout);
            rgBoutonsMode = (RadioGroup) view.findViewById(R.id.groupe_boutons_mode);
            rbAuto = (RadioButton) view.findViewById(R.id.radio_bouton_auto);
            rbArret = (RadioButton) view.findViewById(R.id.radio_bouton_arret);
            rbMarche = (RadioButton) view.findViewById(R.id.radio_bouton_marche);

            rbAuto.setOnClickListener(this);
            rbArret.setOnClickListener(this);
            rbMarche.setOnClickListener(this);
        } else {
            new ScaleListener((HorizontalScrollView) view.findViewById(R.id.horizontal_scroll),
                    (ScrollView) view.findViewById(R.id.vertical_scroll),
                    (AbsoluteLayout) view.findViewById(R.id.layout));

            globalLayoutPaysage = view.findViewById(R.id.horizontal_scroll);
            boutonRetour = (ImageButton) view.findViewById(R.id.bouton_retour);
            bouton3Etats = (ImageView) view.findViewById(R.id.bouton_3_etats);
            boutonAuto = (Button) view.findViewById(R.id.bouton_auto);
            boutonArret = (Button) view.findViewById(R.id.bouton_arret);
            boutonMarche = (Button) view.findViewById(R.id.bouton_marche);
            LinearLayout viewPlage = (LinearLayout) view.findViewById(R.id.view_plage);
            renduPlage = new RenduPlage(MainActivity.instance(), Donnees.Equipement.Surpresseur);
            viewPlage.addView(renduPlage);

            boutonRetour.setOnClickListener(this);
            boutonAuto.setOnClickListener(this);
            boutonArret.setOnClickListener(this);
            boutonMarche.setOnClickListener(this);
        }

        texteConso = (TextView) view.findViewById(R.id.texte_donnees_conso);
        widgetPlage1 = (GridLayout) view.findViewById(R.id.widget_plage_1);
        boutonSupprimerPlage1 = (ImageButton) view.findViewById(R.id.bouton_supprimer_plage_1);
        boutonModifierPlage1 = (ImageButton) view.findViewById(R.id.bouton_modifier_plage_1);
        textePlage1 = (TextView) view.findViewById(R.id.texte_plage_1);
        widgetPlage2 = (GridLayout) view.findViewById(R.id.widget_plage_2);
        boutonSupprimerPlage2 = (ImageButton) view.findViewById(R.id.bouton_supprimer_plage_2);
        boutonModifierPlage2 = (ImageButton) view.findViewById(R.id.bouton_modifier_plage_2);
        textePlage2 = (TextView) view.findViewById(R.id.texte_plage_2);
        widgetPlage3 = (GridLayout) view.findViewById(R.id.widget_plage_3);
        boutonSupprimerPlage3 = (ImageButton) view.findViewById(R.id.bouton_supprimer_plage_3);
        boutonModifierPlage3 = (ImageButton) view.findViewById(R.id.bouton_modifier_plage_3);
        textePlage3 = (TextView) view.findViewById(R.id.texte_plage_3);
        widgetPlage4 = (GridLayout) view.findViewById(R.id.widget_plage_4);
        boutonSupprimerPlage4 = (ImageButton) view.findViewById(R.id.bouton_supprimer_plage_4);
        boutonModifierPlage4 = (ImageButton) view.findViewById(R.id.bouton_modifier_plage_4);
        textePlage4 = (TextView) view.findViewById(R.id.texte_plage_4);
        boutonAjouterPlage = view.findViewById(R.id.bouton_ajouter_plage);

        boutonSupprimerPlage1.setOnClickListener(this);
        boutonModifierPlage1.setOnClickListener(this);
        boutonSupprimerPlage2.setOnClickListener(this);
        boutonModifierPlage2.setOnClickListener(this);
        boutonSupprimerPlage3.setOnClickListener(this);
        boutonModifierPlage3.setOnClickListener(this);
        boutonSupprimerPlage4.setOnClickListener(this);
        boutonModifierPlage4.setOnClickListener(this);
        boutonAjouterPlage.setOnClickListener(this);


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

            modeAEteModifie(Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Surpresseur));

            consoAEteModifie(Donnees.instance().obtenirDateDebutConso(Donnees.Equipement.Surpresseur),
                    Donnees.instance().obtenirConsoHP(Donnees.Equipement.Surpresseur),
                    Donnees.instance().obtenirConsoHC(Donnees.Equipement.Surpresseur));

            plageModifie();
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
            case R.id.radio_bouton_auto:
            case R.id.radio_bouton_arret:
            case R.id.radio_bouton_marche:
                modifierMode((RadioButton) view.findViewById(rgBoutonsMode.getCheckedRadioButtonId()));
                break;
            case R.id.bouton_auto:
            case R.id.bouton_arret:
            case R.id.bouton_marche:
                modifierMode((Button) v.findViewById(v.getId()));
                break;
            case R.id.bouton_supprimer_plage_1:
            case R.id.bouton_supprimer_plage_2:
            case R.id.bouton_supprimer_plage_3:
            case R.id.bouton_supprimer_plage_4:
                supprimerPlage((ImageButton) v.findViewById(v.getId()));
                break;
            case R.id.bouton_modifier_plage_1:
            case R.id.bouton_modifier_plage_2:
            case R.id.bouton_modifier_plage_3:
            case R.id.bouton_modifier_plage_4:
                modifierIndex((ImageButton) v.findViewById(v.getId()), true);
                break;
            case R.id.bouton_ajouter_plage:
                ajouterIndex(true);
                break;
            default:
                break;
        }
    }

    private void modifierMode(RadioButton rb) {
        int etat = Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Surpresseur);
        String data = "etat=";

        if (rb == rbAuto) {
            etat = (etat == Donnees.AUTO_MARCHE) ? Donnees.AUTO_MARCHE : Donnees.AUTO_ARRET;
        } else if (rb == rbMarche) {
            etat = Donnees.MARCHE;
        } else {
            etat = Donnees.ARRET;
        }

        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Surpresseur, etat);
        modeAEteModifie(etat);

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageSurpresseur),
                data + String.valueOf(etat));
    }

    private void modifierMode(Button bouton) {
        int etat = Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Surpresseur);
        String data = "etat=";

        if (bouton == boutonAuto) {
            etat = (etat == Donnees.AUTO_MARCHE) ? Donnees.AUTO_MARCHE : Donnees.AUTO_ARRET;
        } else if (bouton == boutonMarche) {
            etat = Donnees.MARCHE;
        } else {
            etat = Donnees.ARRET;
        }

        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Surpresseur, etat);
        modeAEteModifie(etat);

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageSurpresseur),
                data + String.valueOf(etat));
    }

    private void modeAEteModifie(int mode) {
        String autoText;
        LinearLayout.LayoutParams paramEtatOk = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1.0f
        );
        LinearLayout.LayoutParams paramEtatNok = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                2.0f
        );

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            autoText = "Auto (" + ((mode == Donnees.AUTO_MARCHE) ? "marche" : "arrêt") + ")";

            if (mode > Donnees.AUTO) {
                rbAuto.setChecked(true);
            } else if (mode == Donnees.MARCHE) {
                rbMarche.setChecked(true);
            } else {
                rbArret.setChecked(true);
            }

            rbAuto.setText(autoText);
        } else {
            if (mode > Donnees.AUTO) {
                bouton3Etats.setImageResource(R.drawable.bouton_haut_vertical);
                boutonAuto.setTextColor(getResources().getColor(R.color.bouton3EtatSelectionne));
                boutonArret.setTextColor(getResources().getColor(R.color.bouton3EtatNonSelectionne));
                boutonMarche.setTextColor(getResources().getColor(R.color.bouton3EtatNonSelectionne));
                boutonAuto.setLayoutParams(paramEtatOk);
                boutonArret.setLayoutParams(paramEtatNok);
                boutonMarche.setLayoutParams(paramEtatNok);
            } else if (mode == Donnees.MARCHE) {
                bouton3Etats.setImageResource(R.drawable.bouton_bas_vertical);
                boutonAuto.setTextColor(getResources().getColor(R.color.bouton3EtatNonSelectionne));
                boutonArret.setTextColor(getResources().getColor(R.color.bouton3EtatNonSelectionne));
                boutonMarche.setTextColor(getResources().getColor(R.color.bouton3EtatSelectionne));
                boutonAuto.setLayoutParams(paramEtatNok);
                boutonArret.setLayoutParams(paramEtatNok);
                boutonMarche.setLayoutParams(paramEtatOk);
            } else {
                bouton3Etats.setImageResource(R.drawable.bouton_off_vertical);
                boutonAuto.setTextColor(getResources().getColor(R.color.bouton3EtatNonSelectionne));
                boutonArret.setTextColor(getResources().getColor(R.color.bouton3EtatSelectionne));
                boutonMarche.setTextColor(getResources().getColor(R.color.bouton3EtatNonSelectionne));
                boutonAuto.setLayoutParams(paramEtatNok);
                boutonArret.setLayoutParams(paramEtatOk);
                boutonMarche.setLayoutParams(paramEtatNok);
            }
        }
    }

    private void consoAEteModifie(String date, double consoHP, double consoHC) {
        texteConso.setText("Depuis : " + date +
                "\nHeures pleines : " + consoHP + " kWh" +
                "\nHeures creuses : " + consoHC + " kWh");
    }

    private void ajouterIndex(boolean raz) {
        Intent intent = new Intent(MainActivity.instance(), ClavierActivity.class);

        if (raz) {
            for (int i = 0; i < Global.MAX_PLAGES_EQUIPEMENTS; i++) {
                if (!Donnees.instance().obtenirEtatPlage(Donnees.Equipement.Surpresseur, i)) {
                    indexPlage = i;
                    break;
                }
            }

            heureDebutPlage = null;
            heureFinPlage = null;
        }

        if (heureDebutPlage == null) {
            intent.putExtra(ClavierActivity.EXTRA_QUESTION, "Donnez l'heure de début de la nouvelle plage séparée par une virgule");
            heureMinimumPlage = indexPlage > 0 ? Donnees.instance().obtenirPlage(Donnees.Equipement.Surpresseur, indexPlage - 1).split(" - ")[1] : "00h00";
        } else {
            intent.putExtra(ClavierActivity.EXTRA_QUESTION, "Donnez l'heure de fin de la nouvelle plage séparée par une virgule");
            heureMinimumPlage = heureDebutPlage;
        }
        heureMaximumPlage = "23h59";

        intent.putExtra(ClavierActivity.EXTRA_INDICE, "Exemple : 9h15 → 09.15\nMinimum : " + heureMinimumPlage + "\nMaximum : " + heureMaximumPlage);
        intent.putExtra(ClavierActivity.EXTRA_AUTRE, "ajouter");
        startActivityForResult(intent, 1);
    }

    private void supprimerPlage(ImageButton imageButton) {
        int index;
        String data = "plage_";

        if (imageButton == boutonSupprimerPlage1) {
            index = 0;
        } else if (imageButton == boutonSupprimerPlage2) {
            index = 1;
        } else if (imageButton == boutonSupprimerPlage3) {
            index = 2;
        } else {
            index = 3;
        }

        if (index < (Global.MAX_PLAGES_EQUIPEMENTS - 1)) {
            for (int i = index; i < (Global.MAX_PLAGES_EQUIPEMENTS - 1); i++) {
                Donnees.instance().definirPlage(Donnees.Equipement.Surpresseur, i, Donnees.instance().obtenirPlage(Donnees.Equipement.Surpresseur, i + 1));
            }
            Donnees.instance().definirPlage(Donnees.Equipement.Surpresseur, Global.MAX_PLAGES_EQUIPEMENTS - 1, "00h00 - 00h00");
        } else {
            Donnees.instance().definirPlage(Donnees.Equipement.Surpresseur, index, "00h00 - 00h00");
        }

        plageModifie();

        for (int i = index; i < Global.MAX_PLAGES_EQUIPEMENTS; i++) {
            MainActivity.instance().sendData(false,
                    "",
                    "",
                    HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                    HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageSurpresseur),
                    data + String.valueOf(i + 1) + '=' + Donnees.instance().obtenirPlage(Donnees.Equipement.Surpresseur, i));
        }
    }

    private void modifierIndex(ImageButton imageButton, boolean raz) {
        Intent intent = new Intent(MainActivity.instance(), ClavierActivity.class);

        if (raz) {
            boutonModifierPlage = imageButton;
            heureDebutPlage = null;
            heureFinPlage = null;

            if (imageButton == boutonModifierPlage1) {
                indexPlage = 0;
            } else if (imageButton == boutonModifierPlage2) {
                indexPlage = 1;
            } else if (imageButton == boutonModifierPlage3) {
                indexPlage = 2;
            } else {
                indexPlage = 3;
            }
        }

        if (heureDebutPlage == null) {
            intent.putExtra(ClavierActivity.EXTRA_QUESTION, "Donnez l'heure de début de la plage séparée par une virgule");
            heureMinimumPlage = indexPlage > 0 ? Donnees.instance().obtenirPlage(Donnees.Equipement.Surpresseur, indexPlage - 1).split(" - ")[0] : "00h00";
        } else {
            intent.putExtra(ClavierActivity.EXTRA_QUESTION, "Donnez l'heure de fin de la plage séparée par une virgule");
            heureMinimumPlage = heureDebutPlage;
        }
        heureMaximumPlage = indexPlage == (Global.MAX_PLAGES_EQUIPEMENTS - 1) ? (Donnees.instance().obtenirEtatPlage(Donnees.Equipement.Surpresseur, indexPlage + 1) ? Donnees.instance().obtenirPlage(Donnees.Equipement.Surpresseur, indexPlage + 1).split(" - ")[1] : "23h59") : "23h59";

        intent.putExtra(ClavierActivity.EXTRA_INDICE, "Exemple : 9h15 → 09.15\nMinimum : " + heureMinimumPlage + "\nMaximum : " + heureMaximumPlage);
        intent.putExtra(ClavierActivity.EXTRA_AUTRE, "modifier");
        startActivityForResult(intent, 1);
    }

    private boolean verifierHeure(String strHeure) {
        boolean ret = false;

        if (strHeure.contains("h")) {
            Time heure = new Time(Integer.parseInt(strHeure.split("h")[0]), Integer.parseInt(strHeure.split("h")[1]), 0);
            Time heureMinimum = new Time(Integer.parseInt(heureMinimumPlage.split("h")[0]), Integer.parseInt(heureMinimumPlage.split("h")[1]), 0);
            Time heureMaximum = new Time(Integer.parseInt(heureMaximumPlage.split("h")[0]), Integer.parseInt(heureMaximumPlage.split("h")[1]), 0);

            if (heure.after(heureMinimum) && heure.before(heureMaximum)) {
                ret = true;
            }
        }

        return ret;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String autre = data.getStringExtra(ClavierActivity.EXTRA_AUTRE);
                String result = data.getStringExtra(ClavierActivity.EXTRA_RESULTAT);
                result = result.replace(',', 'h');
                if (result.split("h").length > 1) {
                    DecimalFormat df = new DecimalFormat("00");
                    result = df.format(Double.parseDouble(result.split("h")[0])) + 'h' + df.format(Double.parseDouble(result.split("h")[1]));
                }

                if (heureDebutPlage == null) {
                    heureDebutPlage = result;
                    if (verifierHeure(heureDebutPlage)) {
                        if (autre.equals("ajouter")) {
                            ajouterIndex(false);
                        } else {
                            modifierIndex(boutonModifierPlage, false);
                        }
                    } else {
                        if (autre.equals("ajouter")) {
                            ajouterIndex(true);
                        } else {
                            modifierIndex(boutonModifierPlage, true);
                        }
                    }
                } else {
                    heureFinPlage = result;
                    if (verifierHeure(heureFinPlage)) {
                        if (autre.equals("ajouter")) {
                            ajouterPlage();
                        } else {
                            modifierPlage();
                        }
                    } else {
                        if (autre.equals("ajouter")) {
                            ajouterIndex(false);
                        } else {
                            modifierIndex(boutonModifierPlage, false);
                        }
                    }
                }
            }
        }
    }

    private void ajouterPlage() {
        String data = "plage_";

        Donnees.instance().definirPlage(Donnees.Equipement.Surpresseur, indexPlage, heureDebutPlage + " - " + heureFinPlage);
        plageModifie();

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageSurpresseur),
                data + String.valueOf(indexPlage + 1) + '=' + Donnees.instance().obtenirPlage(Donnees.Equipement.Surpresseur, indexPlage));
    }

    private void modifierPlage() {
        String data = "plage_";

        Donnees.instance().definirPlage(Donnees.Equipement.Surpresseur, indexPlage, heureDebutPlage + " - " + heureFinPlage);
        plageModifie();

        MainActivity.instance().sendData(false,
                "",
                "",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageSurpresseur),
                data + String.valueOf(indexPlage + 1) + '=' + Donnees.instance().obtenirPlage(Donnees.Equipement.Surpresseur, indexPlage));
    }

    private void plageModifie() {
        widgetPlage1.setVisibility(Donnees.instance().obtenirEtatPlage(Donnees.Equipement.Surpresseur, 0) ? View.VISIBLE : View.GONE);
        textePlage1.setText(Donnees.instance().obtenirPlage(Donnees.Equipement.Surpresseur, 0));
        widgetPlage2.setVisibility(Donnees.instance().obtenirEtatPlage(Donnees.Equipement.Surpresseur, 1) ? View.VISIBLE : View.GONE);
        textePlage2.setText(Donnees.instance().obtenirPlage(Donnees.Equipement.Surpresseur, 1));
        widgetPlage3.setVisibility(Donnees.instance().obtenirEtatPlage(Donnees.Equipement.Surpresseur, 2) ? View.VISIBLE : View.GONE);
        textePlage3.setText(Donnees.instance().obtenirPlage(Donnees.Equipement.Surpresseur, 2));
        widgetPlage4.setVisibility(Donnees.instance().obtenirEtatPlage(Donnees.Equipement.Surpresseur, 3) ? View.VISIBLE : View.GONE);
        textePlage4.setText(Donnees.instance().obtenirPlage(Donnees.Equipement.Surpresseur, 3));
        boutonAjouterPlage.setVisibility(Donnees.instance().obtenirEtatPlage(Donnees.Equipement.Surpresseur, 3) ? View.GONE : View.VISIBLE);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            renduPlage.invalidate();
        }
    }
}
