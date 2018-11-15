package fr.ozonex.myozonex;

import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class FragmentDonnees extends Fragment implements View.OnClickListener {
    View view = null;

    // Tout orientations
    LinearLayout layoutPh;
    TextView valeurPh;
    LinearLayout layoutAmpero;
    TextView valeurAmpero;
    LinearLayout layoutORP;
    TextView valeurORP;
    LinearLayout layoutTemperatureBassin;
    LinearLayout layoutTemperatureExterne;
    TextView valeurTemperatureBassin;
    TextView valeurTemperatureExterne;

    // Orientation portrait
    LinearLayout globalLayoutPortrait;
    TextView texteAucunCapteurs;
    LinearLayout layoutTemperatureInterne;
    TextView valeurTemperatureInterne;
    LinearLayout layoutHumiditeInterne;
    TextView valeurHumiditeInterne;
    LinearLayout layoutPressionAtmInterne;
    TextView valeurPressionAtmInterne;
    LinearLayout layoutHumiditeExterne;
    TextView valeurHumiditeExterne;
    LinearLayout layoutPressionAtmExterne;
    TextView valeurPressionAtmExterne;
    LinearLayout layoutPression;
    TextView valeurPression;
    LinearLayout layout_4_20_Libre;
    TextView valeur_4_20_Libre;

    // Orientation paysage
    HorizontalScrollView globalLayoutPaysage;
    Button boutonSynoptique;
    TextView ledEtatPhPlus;
    TextView texteEtatPhPlus;
    TextView ledEtatPhMoins;
    TextView texteEtatPhMoins;
    TextView texteDonneesPh;
    LinearLayout layoutChlore;
    TextView ledEtatChlore;
    TextView texteEtatChlore;
    TextView texteLibreActifAmpero;
    TextView texteDonneesAmpero;
    View ligneSepChlore;
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
            layoutTemperatureInterne = view.findViewById(R.id.layout_temperature_interne);
            valeurTemperatureInterne = (TextView) view.findViewById(R.id.texte_valeur_temperature_interne);
            layoutHumiditeInterne = view.findViewById(R.id.layout_humidite_interne);
            valeurHumiditeInterne = (TextView) view.findViewById(R.id.texte_valeur_humidite_interne);
            layoutPressionAtmInterne = view.findViewById(R.id.layout_pression_atm_interne);
            valeurPressionAtmInterne = (TextView) view.findViewById(R.id.texte_valeur_pression_atm_interne);
            layoutHumiditeExterne = view.findViewById(R.id.layout_humidite_externe);
            valeurHumiditeExterne = (TextView) view.findViewById(R.id.texte_valeur_humidite_externe);
            layoutPressionAtmExterne = view.findViewById(R.id.layout_pression_atm_externe);
            valeurPressionAtmExterne = (TextView) view.findViewById(R.id.texte_valeur_pression_atm_externe);
            layoutPression = view.findViewById(R.id.layout_pression);
            valeurPression = (TextView) view.findViewById(R.id.texte_valeur_pression);
            layout_4_20_Libre = view.findViewById(R.id.layout_4_20_libre);
            valeur_4_20_Libre = (TextView) view.findViewById(R.id.texte_valeur_4_20_libre);
        } else {
            new ScaleListener((HorizontalScrollView) view.findViewById(R.id.horizontal_scroll),
                    (ScrollView) view.findViewById(R.id.vertical_scroll),
                    (AbsoluteLayout) view.findViewById(R.id.layout));

            globalLayoutPaysage = view.findViewById(R.id.horizontal_scroll);
            boutonSynoptique = (Button) view.findViewById(R.id.bouton_synoptique);
            ledEtatPhPlus = view.findViewById(R.id.led_etat_ph_plus);
            texteEtatPhPlus = view.findViewById(R.id.texte_etat_ph_plus);
            ledEtatPhMoins = view.findViewById(R.id.led_etat_ph_moins);
            texteEtatPhMoins = view.findViewById(R.id.texte_etat_ph_moins);
            texteDonneesPh = (TextView) view.findViewById(R.id.texte_donnees_ph);
            layoutChlore = (LinearLayout) view.findViewById(R.id.layout_chlore);
            ledEtatChlore = view.findViewById(R.id.led_etat_chlore);
            texteEtatChlore = view.findViewById(R.id.texte_etat_chlore);
            texteLibreActifAmpero = (TextView) view.findViewById(R.id.texte_valeur_libre_actif);
            texteDonneesAmpero = (TextView) view.findViewById(R.id.texte_donnees_ampero);
            ligneSepChlore = view.findViewById(R.id.ligne_sep_chlore);
            texteDonneesORP = (TextView) view.findViewById(R.id.texte_donnees_orp);
            layoutTemperatures = (LinearLayout) view.findViewById(R.id.layout_temperatures);
            ligneSepTemperatures = view.findViewById(R.id.ligne_sep_temperatures);

            boutonSynoptique.setOnClickListener(this);
        }

        layoutPh = view.findViewById(R.id.layout_ph);
        valeurPh = (TextView) view.findViewById(R.id.texte_valeur_ph);
        layoutAmpero = view.findViewById(R.id.layout_ampero);
        valeurAmpero = (TextView) view.findViewById(R.id.texte_valeur_ampero);
        layoutORP = view.findViewById(R.id.layout_orp);
        valeurORP = (TextView) view.findViewById(R.id.texte_valeur_orp);
        layoutTemperatureBassin = view.findViewById(R.id.layout_temperature_bassin);
        valeurTemperatureBassin = (TextView) view.findViewById(R.id.texte_valeur_temperature_bassin);
        layoutTemperatureExterne = view.findViewById(R.id.layout_temperature_externe);
        valeurTemperatureExterne = (TextView) view.findViewById(R.id.texte_valeur_temperature_externe);


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

                layoutTemperatureInterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.CapteurInterne) ? View.VISIBLE : View.GONE);
                valeurTemperatureInterne.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.TemperatureInterne) ? Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureInterne) + " °C" : "Err");
                valeurTemperatureInterne.setTextColor(Donnees.instance().obtenirEtat(Donnees.Capteur.TemperatureInterne) ? Color.WHITE : Color.RED);

                layoutHumiditeInterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.CapteurInterne) ? View.VISIBLE : View.GONE);
                valeurHumiditeInterne.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.HumiditeInterne) ? Donnees.instance().obtenirValeur(Donnees.Capteur.HumiditeInterne) + " %" : "Err");
                valeurHumiditeInterne.setTextColor(Donnees.instance().obtenirEtat(Donnees.Capteur.HumiditeInterne) ? Color.WHITE : Color.RED);

                layoutPressionAtmInterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.CapteurInterne) ? View.VISIBLE : View.GONE);
                valeurPressionAtmInterne.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.PressionAtmospheriqueInterne) ? Donnees.instance().obtenirValeur(Donnees.Capteur.PressionAtmospheriqueInterne) + " hPa" : "Err");
                valeurPressionAtmInterne.setTextColor(Donnees.instance().obtenirEtat(Donnees.Capteur.PressionAtmospheriqueInterne) ? Color.WHITE : Color.RED);

                layoutHumiditeExterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.CapteurExterne) ? View.VISIBLE : View.GONE);
                valeurHumiditeExterne.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.HumiditeExterne) ? Donnees.instance().obtenirValeur(Donnees.Capteur.HumiditeExterne) + " %" : "Err");
                valeurHumiditeExterne.setTextColor(Donnees.instance().obtenirEtat(Donnees.Capteur.HumiditeExterne) ? Color.WHITE : Color.RED);

                layoutPressionAtmExterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.CapteurExterne) ? View.VISIBLE : View.GONE);
                valeurPressionAtmExterne.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.PressionAtmospheriqueExterne) ? Donnees.instance().obtenirValeur(Donnees.Capteur.PressionAtmospheriqueExterne) + " hPa" : "Err");
                valeurPressionAtmExterne.setTextColor(Donnees.instance().obtenirEtat(Donnees.Capteur.PressionAtmospheriqueExterne) ? Color.WHITE : Color.RED);

                layoutPression.setVisibility(Donnees.instance().presence(Donnees.Capteur.Pression) ? View.VISIBLE : View.GONE);
                valeurPression.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.Pression) ? Donnees.instance().obtenirValeur(Donnees.Capteur.Pression) + " bar" : "Err");
                valeurPression.setTextColor(Donnees.instance().obtenirEtat(Donnees.Capteur.Pression) ? Color.WHITE : Color.RED);

                layout_4_20_Libre.setVisibility(Donnees.instance().presence(Donnees.Capteur._4_20_Libre) ? View.VISIBLE : View.GONE);
                valeur_4_20_Libre.setText(Donnees.instance().obtenirEtat(Donnees.Capteur._4_20_Libre) ? Double.toString(Donnees.instance().obtenirValeur(Donnees.Capteur._4_20_Libre)) : "Err");
                valeur_4_20_Libre.setTextColor(Donnees.instance().obtenirEtat(Donnees.Capteur._4_20_Libre) ? Color.WHITE : Color.RED);
            } else {
                globalLayoutPaysage.setBackgroundResource(Donnees.instance().obtenirBackground());

                ledEtatPhPlus.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) ? View.VISIBLE : View.GONE);
                texteEtatPhPlus.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) ? View.VISIBLE : View.GONE);
                updateEtat(Donnees.Equipement.PhPlus, ledEtatPhPlus, texteEtatPhPlus, "pH+");
                ledEtatPhMoins.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? View.VISIBLE : View.GONE);
                texteEtatPhMoins.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? View.VISIBLE : View.GONE);
                updateEtat(Donnees.Equipement.PhMoins, ledEtatPhMoins, texteEtatPhMoins, "pH-");
                texteDonneesPh.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) || Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? View.VISIBLE : View.GONE);
                if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins)) {
                    texteDonneesPh.setText("Point de consigne : " + Donnees.instance().obtenirConsignePh() + "\nHystérésis : ↑" + Donnees.instance().obtenirHysteresisPhPlus() + " - ↓" + Donnees.instance().obtenirHysteresisPhMoins());
                } else if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus)) {
                    texteDonneesPh.setText("Point de consigne : " + Donnees.instance().obtenirConsignePh() + "\nHystérésis : ↑" + Donnees.instance().obtenirHysteresisPhPlus());
                } else if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins)) {
                    texteDonneesPh.setText("Point de consigne : " + Donnees.instance().obtenirConsignePh() + "\nHystérésis : ↓" + Donnees.instance().obtenirHysteresisPhMoins());
                }

                layoutChlore.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) || Donnees.instance().presence(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
                ledEtatChlore.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);
                texteEtatChlore.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);
                updateEtat(Donnees.Equipement.Orp, ledEtatChlore, texteEtatChlore, "Chlore");
                texteLibreActifAmpero.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);
                texteLibreActifAmpero.setText("Chlore libre actif : " + Donnees.instance().obtenirChloreLibreActif() + " ppm");
                texteDonneesAmpero.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);
                texteDonneesAmpero.setText("Point de consigne : " + Donnees.instance().obtenirConsigneAmpero() + " ppm\nHystérésis : ↑" + Donnees.instance().obtenirHysteresisAmpero());
                ligneSepChlore.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) && Donnees.instance().presence(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
                texteDonneesORP.setVisibility(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? View.VISIBLE : View.GONE);
                texteDonneesORP.setText("Point de consigne : " + Donnees.instance().obtenirConsigneOrp() + " ppm\nHystérésis : ↑" + Donnees.instance().obtenirHysteresisOrp());

                layoutTemperatures.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) || Donnees.instance().presence(Donnees.Capteur.CapteurExterne) ? View.VISIBLE : View.GONE);
                ligneSepTemperatures.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) && Donnees.instance().presence(Donnees.Capteur.CapteurExterne) ? View.VISIBLE : View.GONE);
            }

            layoutPh.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ph) ? View.VISIBLE : View.GONE);
            valeurPh.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.Ph) ? Double.toString(Donnees.instance().obtenirValeur(Donnees.Capteur.Ph)) : "Err");

            layoutAmpero.setVisibility(Donnees.instance().presence(Donnees.Capteur.Ampero) ? View.VISIBLE : View.GONE);
            valeurAmpero.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.Ampero) ? Donnees.instance().obtenirValeur(Donnees.Capteur.Ampero) + " ppm" : "Err");

            layoutORP.setVisibility(Donnees.instance().presence(Donnees.Capteur.Redox) ? View.VISIBLE : View.GONE);
            valeurORP.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.Redox) ? Donnees.instance().obtenirValeur(Donnees.Capteur.Redox) + " mV" : "Err");

            layoutTemperatureBassin.setVisibility(Donnees.instance().presence(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
            valeurTemperatureBassin.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.TemperatureBassin) ? Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin) + " °C" : "Err");

            layoutTemperatureExterne.setVisibility(Donnees.instance().presence(Donnees.Capteur.CapteurExterne) ? View.VISIBLE : View.GONE);
            valeurTemperatureExterne.setText(Donnees.instance().obtenirEtat(Donnees.Capteur.TemperatureExterne) ? Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureExterne) + " °C" : "Err");

            if (Donnees.instance().obtenirEtatLectureCapteurs()) {
                if (Donnees.instance().obtenirEtat(Donnees.Capteur.Ph)) {
                    if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) && Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins)) {
                        if (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.PhPlus)
                                || Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.PhMoins)) {
                            valeurPh.setTextColor(Color.parseColor("#FFAA00"));
                        } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.Ph) < (Donnees.instance().obtenirConsignePh() - Donnees.instance().obtenirHysteresisPhPlus())) {
                            valeurPh.setTextColor(Color.RED);
                        } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.Ph) > (Donnees.instance().obtenirConsignePh() + Donnees.instance().obtenirHysteresisPhMoins())) {
                            valeurPh.setTextColor(Color.RED);
                        } else {
                            valeurPh.setTextColor(Color.GREEN);
                        }
                    } else if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus)) {
                        if (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.PhPlus)) {
                            valeurPh.setTextColor(Color.parseColor("#FFAA00"));
                        } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.Ph) < (Donnees.instance().obtenirConsignePh() - Donnees.instance().obtenirHysteresisPhPlus())) {
                            valeurPh.setTextColor(Color.RED);
                        } else {
                            valeurPh.setTextColor(Color.GREEN);
                        }
                    } else if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins)) {
                        if (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.PhMoins)) {
                            valeurPh.setTextColor(Color.parseColor("#FFAA00"));
                        } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.Ph) > (Donnees.instance().obtenirConsignePh() + Donnees.instance().obtenirHysteresisPhMoins())) {
                            valeurPh.setTextColor(Color.RED);
                        } else {
                            valeurPh.setTextColor(Color.GREEN);
                        }
                    } else {
                        valeurPh.setTextColor(Color.WHITE);
                    }
                } else {
                    valeurPh.setTextColor(Color.RED);
                }

                if (Donnees.instance().presence(Donnees.Capteur.Ampero)) {
                    if (Donnees.instance().obtenirEtat(Donnees.Capteur.Ampero)) {
                        if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp)) {
                            if (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.Orp)) {
                                valeurAmpero.setTextColor(Color.parseColor("#FFAA00"));
                            } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.Ampero) < (Donnees.instance().obtenirConsigneAmpero() - Donnees.instance().obtenirHysteresisAmpero())) {
                                valeurAmpero.setTextColor(Color.RED);
                            } else {
                                valeurAmpero.setTextColor(Color.GREEN);
                            }
                        } else {
                            valeurAmpero.setTextColor(Color.WHITE);
                        }
                    } else {
                        valeurAmpero.setTextColor(Color.RED);
                    }

                    if (Donnees.instance().obtenirEtat(Donnees.Capteur.Redox)) {
                        if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp)) {
                            if (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.Orp)
                                    && !Donnees.instance().obtenirEtat(Donnees.Capteur.Ampero)) {
                                valeurORP.setTextColor(Color.parseColor("#FFAA00"));
                            } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.Redox) < (Donnees.instance().obtenirConsigneOrp() - Donnees.instance().obtenirHysteresisOrp())) {
                                valeurORP.setTextColor(Color.RED);
                            } else {
                                valeurORP.setTextColor(Color.GREEN);
                            }
                        } else {
                            valeurORP.setTextColor(Color.WHITE);
                        }
                    } else {
                        valeurORP.setTextColor(Color.RED);
                    }
                } else {
                    if (Donnees.instance().obtenirEtat(Donnees.Capteur.Redox)) {
                        if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp)) {
                            if (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.Orp)) {
                                valeurORP.setTextColor(Color.parseColor("#FFAA00"));
                            } else if (Donnees.instance().obtenirValeur(Donnees.Capteur.Redox) < (Donnees.instance().obtenirConsigneOrp() - Donnees.instance().obtenirHysteresisOrp())) {
                                valeurORP.setTextColor(Color.RED);
                            } else {
                                valeurORP.setTextColor(Color.GREEN);
                            }
                        } else {
                            valeurORP.setTextColor(Color.WHITE);
                        }
                    } else {
                        valeurORP.setTextColor(Color.RED);
                    }
                }

                if (Donnees.instance().obtenirEtat(Donnees.Capteur.TemperatureBassin)) {
                    if (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage)) {
                        if ((Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Chauffage) == Donnees.AUTO_MARCHE)
                                || (Donnees.instance().obtenirModeFonctionnement(Donnees.Equipement.Chauffage) == Donnees.MARCHE)) {
                            valeurTemperatureBassin.setTextColor(Color.parseColor("#FFAA00"));
                        } else if (Donnees.instance().obtenirControlePompeFiltration() == Donnees.CONTROLE_PAR_POMPE_FILTRATION) {
                            if ((Donnees.instance().obtenirTemperatureArret() <= Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin))
                                    && (Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin) <= Donnees.instance().obtenirTemperatureEnclenchement())) {
                                valeurTemperatureBassin.setTextColor(Color.GREEN);
                            } else {
                                valeurTemperatureBassin.setTextColor(Color.RED);
                            }
                        } else {
                            if (Donnees.instance().obtenirTemperatureConsigne() <= Donnees.instance().obtenirValeur(Donnees.Capteur.TemperatureBassin)) {
                                valeurTemperatureBassin.setTextColor(Color.GREEN);
                            } else {
                                valeurTemperatureBassin.setTextColor(Color.RED);
                            }
                        }
                    } else {
                        valeurTemperatureBassin.setTextColor(Color.WHITE);
                    }
                } else {
                    valeurTemperatureBassin.setTextColor(Color.RED);
                }

                valeurTemperatureExterne.setTextColor(Donnees.instance().obtenirEtat(Donnees.Capteur.TemperatureExterne) ? Color.WHITE : Color.RED);
            }
        }
    }

    private void updateEtat(Donnees.Equipement equipement, TextView led, TextView textView, String strEquipement) {
        int etat = Donnees.instance().obtenirModeFonctionnement(equipement);
        boolean traitementEnCours = Donnees.instance().obtenirTraitementEnCours(equipement);

        if (traitementEnCours) {
            textView.setText(strEquipement + " actif");
            led.setActivated(true);

            if ((etat == Donnees.AUTO_MARCHE)
                    || (etat == Donnees.MARCHE)) {
                led.setEnabled(false);
                led.setClickable(false);
                led.setPressed(false);
            } else {
                led.setEnabled(true);
                led.setClickable(true);
                led.setPressed(false);
            }
        } else {
            textView.setText(strEquipement + " inactif");
            led.setActivated(false);
            led.setEnabled(false);
            led.setClickable(false);
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

    public void clignotement() {
        if ((view != null) && isAdded()) {
            if (!Donnees.instance().obtenirEtatLectureCapteurs()) {
                if (valeurPh.getCurrentTextColor() == Color.WHITE) {
                    valeurPh.setTextColor(Color.GRAY);
                    valeurAmpero.setTextColor(Color.GRAY);
                    valeurORP.setTextColor(Color.GRAY);
                    valeurTemperatureBassin.setTextColor(Color.GRAY);
                } else {
                    valeurPh.setTextColor(Color.WHITE);
                    valeurAmpero.setTextColor(Color.WHITE);
                    valeurORP.setTextColor(Color.WHITE);
                    valeurTemperatureBassin.setTextColor(Color.WHITE);
                }
            }

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (ledEtatPhPlus.isClickable()) {
                    if (ledEtatPhPlus.isPressed()) {
                        ledEtatPhPlus.setPressed(false);
                    } else {
                        ledEtatPhPlus.setPressed(true);
                    }
                }

                if (ledEtatPhMoins.isClickable()) {
                    if (ledEtatPhMoins.isPressed()) {
                        ledEtatPhMoins.setPressed(false);
                    } else {
                        ledEtatPhMoins.setPressed(true);
                    }
                }

                if (ledEtatChlore.isClickable()) {
                    if (ledEtatChlore.isPressed()) {
                        ledEtatChlore.setPressed(false);
                    } else {
                        ledEtatChlore.setPressed(true);
                    }
                }
            }
        }
    }
}
