package fr.ozonex.myozonex;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

public class FragmentParams extends Fragment implements Animation.AnimationListener, View.OnClickListener {
    View view = null;

    ScrollView scrollView;

    private LinearLayout layoutBt;
    private TextView texteDonneesBt;
    private ImageButton boutonRechercherBt;
    private Animation animationRechercherBt;

    private WifiManager wifiManager;
    private ArrayAdapter<String> wifiAdapter;
    private ListView listViewWifi;

    private LinearLayout layoutWifi;
    private TextView texteDonneesWiFi;
    private ImageButton boutonRechercherWiFi;
    private Animation animationRechercherWiFi;

    private int varState = 0;

    private View sepInstallation;
    private Button boutonCodeInstallateur;
    private LinearLayout layoutInstallationEquipements;
    private CheckBox rbRegulateurPhMoins;
    private CheckBox rbRegulateurORP;
    private CheckBox rbPompeFiltration;
    private LinearLayout layoutInstallationCapteurs;
    private CheckBox rbCapteurPh;
    private Button boutonEtalonnageCapteurPh;
    private CheckBox rbCapteurRedox;
    private Button boutonEtalonnageCapteurRedox;
    private CheckBox rbCapteurTempBassin;
    private Button boutonEtalonnageCapteurTempBassin;

    int typeModification = 0;
    int nbPointEtalonnage = 0;
    Donnees.Capteur capteur;
    LinearLayout viewQuestion;
    public ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.params_layout, container, false);

        scrollView = view.findViewById(R.id.scrollview);

        layoutBt = view.findViewById(R.id.layout_bt);
        texteDonneesBt = view.findViewById(R.id.texte_donnees_bt);
        boutonRechercherBt = view.findViewById(R.id.bouton_rechercher_bt);
        animationRechercherBt = AnimationUtils.loadAnimation(MainActivity.instance().getApplicationContext(),
                R.anim.rotate);
        animationRechercherBt.setAnimationListener(this);
        boutonRechercherBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varState = 0;
                texteDonneesBt.setText("Connexion en cours...");
                setAnimationBtButtonState(true);
                Bluetooth.instance().connect();
            }
        });

        layoutWifi = view.findViewById(R.id.layout_wifi);
        texteDonneesWiFi = view.findViewById(R.id.texte_donnees_wifi);
        boutonRechercherWiFi = view.findViewById(R.id.bouton_rechercher_wifi);
        animationRechercherWiFi = AnimationUtils.loadAnimation(MainActivity.instance().getApplicationContext(),
                R.anim.rotate);
        animationRechercherWiFi.setAnimationListener(this);
        boutonRechercherWiFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varState = 1;
                scanWifi();
            }
        });

        listViewWifi = view.findViewById(R.id.listview_wifi);
        wifiManager = (WifiManager) MainActivity.instance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiAdapter = new ArrayAdapter<String>(MainActivity.instance(), android.R.layout.simple_list_item_1) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };
        listViewWifi.setAdapter(wifiAdapter);
        listViewWifi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.instance());
                builder.setCancelable(true);
                builder.setTitle("Connexion Wi-Fi : " + wifiAdapter.getItem(position));
                builder.setMessage("Veuillez entrer le mot de passe");

                final EditText input = new EditText(MainActivity.instance());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.instance().masquerClavier();

                        texteDonneesWiFi.setText("Connexion au réseau WiFi en cours...");
                        setAnimationWiFiButtonState(true);
                        listViewWifi.setVisibility(View.GONE);

                        Donnees.instance().ajouterRequeteBt("WiFi;" + wifiAdapter.getItem(position) + ';' + input.getText().toString(), true);
                    }
                });

                builder.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.setView(input);
                dialog.show();
            }
        });

        sepInstallation = view.findViewById(R.id.sep_installation);
        boutonCodeInstallateur = view.findViewById(R.id.bouton_code_installateur);
        layoutInstallationEquipements = view.findViewById(R.id.layout_installation_equipements);
        rbRegulateurPhMoins = view.findViewById(R.id.rb_regulateur_ph_moins);
        rbRegulateurORP = view.findViewById(R.id.rb_regulateur_orp);
        rbPompeFiltration = view.findViewById(R.id.rb_pompe_filtration);
        layoutInstallationCapteurs = view.findViewById(R.id.layout_installation_capteurs);
        rbCapteurPh = view.findViewById(R.id.rb_capteur_ph);
        boutonEtalonnageCapteurPh  = view.findViewById(R.id.bouton_etalonnage_capteur_ph);
        rbCapteurRedox = view.findViewById(R.id.rb_capteur_redox);
        boutonEtalonnageCapteurRedox  = view.findViewById(R.id.bouton_etalonnage_capteur_redox);
        rbCapteurTempBassin = view.findViewById(R.id.rb_capteur_temp_bassin);
        boutonEtalonnageCapteurTempBassin  = view.findViewById(R.id.bouton_etalonnage_capteur_temp_bassin);

        boutonCodeInstallateur.setOnClickListener(this);
        rbRegulateurPhMoins.setOnClickListener(this);
        rbRegulateurORP.setOnClickListener(this);
        rbPompeFiltration.setOnClickListener(this);
        rbCapteurPh.setOnClickListener(this);
        boutonEtalonnageCapteurPh.setOnClickListener(this);
        rbCapteurRedox.setOnClickListener(this);
        boutonEtalonnageCapteurRedox.setOnClickListener(this);
        rbCapteurTempBassin.setOnClickListener(this);
        boutonEtalonnageCapteurTempBassin.setOnClickListener(this);

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
            layoutBt.setVisibility(Integer.parseInt(Donnees.getPreferences(Donnees.BT_PAIRED_DEVICE)) == 1 ? View.VISIBLE : View.GONE);
            texteDonneesBt.setText(Bluetooth.instance().isOn() ? Bluetooth.instance().isConnected() ? "Connecté" : "Hors de portée" : "Désactivé");
            boutonRechercherBt.setVisibility(Bluetooth.instance().isConnected() ? View.GONE : View.VISIBLE);

            layoutWifi.setVisibility(Bluetooth.instance().isConnected() ? View.VISIBLE : View.GONE);
            texteDonneesWiFi.setText(Donnees.instance().getWiFiState() == 1 ? "Connecté" : "Non connecté");
            boutonRechercherWiFi.setVisibility(Donnees.instance().getWiFiState() == 0 ? View.VISIBLE : View.GONE);

            sepInstallation.setVisibility(Integer.parseInt(Donnees.getPreferences(Donnees.BT_PAIRED_DEVICE)) == 1 ? View.VISIBLE : View.GONE);
            boutonCodeInstallateur.setVisibility(!Donnees.instance().obtenirCodeInstallateur() ? View.VISIBLE : View.GONE);
            layoutInstallationEquipements.setVisibility(Donnees.instance().obtenirCodeInstallateur() && Bluetooth.instance().isConnected() ? View.VISIBLE : View.GONE);
            rbRegulateurPhMoins.setChecked(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins));
            rbRegulateurORP.setChecked(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp));
            rbPompeFiltration.setChecked(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PompeFiltration));
            layoutInstallationCapteurs.setVisibility(Donnees.instance().obtenirCodeInstallateur() && Bluetooth.instance().isConnected() ? View.VISIBLE : View.GONE);
            rbCapteurPh.setChecked(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph));
            boutonEtalonnageCapteurPh.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) ? View.VISIBLE : View.GONE);
            rbCapteurRedox.setChecked(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox));
            boutonEtalonnageCapteurRedox.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) ? View.VISIBLE : View.GONE);
            rbCapteurTempBassin.setChecked(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.TemperatureBassin));
            boutonEtalonnageCapteurTempBassin.setVisibility(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.TemperatureBassin) ? View.VISIBLE : View.GONE);
        }
    }

    public void updateBt() {
        if ((view != null) && isAdded()) {
            switch (varState) {
                case 0:
                    setAnimationBtButtonState(false);
                    update();

                    break;
                case 1:
                    setAnimationWiFiButtonState(false);
                    update();

                    if (Donnees.instance().getWiFiState() == 0) {
                        scanWifi();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void cancelBt() {
        if ((view != null) && isAdded()) {
            texteDonneesBt.setText("Connexion impossible");
            setAnimationBtButtonState(false);
        }
    }

    private void scanWifi() {
        MainActivity.instance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                texteDonneesWiFi.setText("Recherche des réseaux Wi-Fi disponible...");
                boutonRechercherWiFi.setVisibility(View.VISIBLE);
                setAnimationWiFiButtonState(true);
                listViewWifi.setVisibility(View.GONE);
            }
        });

        new Thread() {
            @Override
            public void run() {
                MainActivity.instance().registerReceiver(wifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                wifiManager.startScan();
            }
        }.start();
    }

    BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);

            if (success) {
                wifiAdapter.clear();
                List<ScanResult> results = wifiManager.getScanResults();

                for (ScanResult result : results) {
                    boolean exists = false;

                    for (int i = 0; i < wifiAdapter.getCount(); i++) {
                        if (result.SSID.equals(wifiAdapter.getItem(i))) {
                            exists = true;
                        }
                    }

                    if (!exists && !result.SSID.isEmpty()) {
                        wifiAdapter.add(result.SSID);
                        setWifiLayoutHeight();
                    }
                }
            }

            texteDonneesWiFi.setText("Veuillez sélectionner le réseau Wi-Fi");
            setAnimationWiFiButtonState(false);
            listViewWifi.setVisibility(View.VISIBLE);
            MainActivity.instance().unregisterReceiver(wifiScanReceiver);
        }
    };

    public void setAnimationBtButtonState(Boolean state) {
        if (state) {
            boutonRechercherBt.startAnimation(animationRechercherBt);
            boutonRechercherBt.setClickable(false);
        } else {
            boutonRechercherBt.clearAnimation();
            animationRechercherBt.cancel();
            animationRechercherBt.reset();
            boutonRechercherBt.setClickable(true);
        }
    }

    public void setAnimationWiFiButtonState(Boolean state) {
        if (state) {
            boutonRechercherWiFi.startAnimation(animationRechercherWiFi);
            boutonRechercherWiFi.setClickable(false);
        } else {
            boutonRechercherWiFi.clearAnimation();
            animationRechercherWiFi.cancel();
            animationRechercherWiFi.reset();
            boutonRechercherWiFi.setClickable(true);
        }
    }

    private void setWifiLayoutHeight() {
        int totalHeight = 0;
        for (int i = 0; i < wifiAdapter.getCount(); i++) {
            View listItem = wifiAdapter.getView(i, null, listViewWifi);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listViewWifi.getLayoutParams();
        params.height = totalHeight + (listViewWifi.getDividerHeight() * (wifiAdapter.getCount() - 1));
        listViewWifi.setLayoutParams(params);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_code_installateur:
                afficherQuestion(true, "Veuillez entrer le code installateur");
                break;
            case R.id.rb_regulateur_ph_moins:
            case R.id.rb_regulateur_orp:
            case R.id.rb_pompe_filtration:
            case R.id.rb_capteur_ph:
            case R.id.rb_capteur_redox:
            case R.id.rb_capteur_temp_bassin:
                installer((CheckBox) v.findViewById(v.getId()));
                break;
            case R.id.bouton_etalonnage_capteur_ph:
            case R.id.bouton_etalonnage_capteur_redox:
            case R.id.bouton_etalonnage_capteur_temp_bassin:
                etalonnage((Button) v.findViewById(v.getId()));
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

        if (valeur == Global.CODE_INSTALLATEUR) {
            Donnees.instance().definirCodeInstallateur(true);
            update();
            afficherQuestion(false, "");
        } else {
            reponse.setText("");
        }
    }

    private void installer(CheckBox sender) {
        if (sender == rbRegulateurPhMoins) {
            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.PhMoins, sender.isChecked());
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "installe=" + (sender.isChecked() ? 1 : 0), false);
        } else if (sender == rbRegulateurORP) {
            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Orp, sender.isChecked());
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "installe=" + (sender.isChecked() ? 1 : 0), false);
        } else if (sender == rbPompeFiltration) {
            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.PompeFiltration, sender.isChecked());
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "installe=" + (sender.isChecked() ? 1 : 0), false);
        } else if (sender == rbCapteurPh) {
            Donnees.instance().definirCapteurInstalle(Donnees.Capteur.Ph, sender.isChecked());
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageCapteurs, "type=pH&installe=" + (sender.isChecked() ? 1 : 0), false);
        } else if (sender == rbCapteurRedox) {
            Donnees.instance().definirCapteurInstalle(Donnees.Capteur.Redox, sender.isChecked());
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageCapteurs, "type=ORP&installe=" + (sender.isChecked() ? 1 : 0), false);
        } else if (sender == rbCapteurTempBassin) {
            Donnees.instance().definirCapteurInstalle(Donnees.Capteur.TemperatureBassin, sender.isChecked());
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageCapteurs, "type=Température bassin&installe=" + (sender.isChecked() ? 1 : 0), false);
        }

        MainActivity.instance().updatePages();
    }

    private void etalonnage(Button sender) {
        if (sender == boutonEtalonnageCapteurPh) {
            capteur = Donnees.Capteur.Ph;
        } else if (sender == boutonEtalonnageCapteurRedox) {
            capteur = Donnees.Capteur.Redox;
        } else if (sender == boutonEtalonnageCapteurTempBassin) {
            capteur = Donnees.Capteur.TemperatureBassin;
        }

        etalonnage(typeModification = 0, false);
    }

    public void etalonnage(int position, boolean annulation) {
        if ((view != null) && isAdded()) {
            switch (position) {
                case 0:
                    nbPointEtalonnage = 1;

                    if (capteur == Donnees.Capteur.Ph) {
                        progressDialog = ProgressDialog.show(MainActivity.instance(), "Étalonnage en cours", "Veuillez mettre la sonde à " + (Global.BASE_VOLTAGE_PH_MIN) + " mV", true);
                        Donnees.instance().ajouterRequeteBt("Calibration;pH;1", false);
                    } else if (capteur == Donnees.Capteur.Redox) {
                        progressDialog = ProgressDialog.show(MainActivity.instance(), "Étalonnage en cours", "Veuillez mettre la sonde à " + (Global.BASE_VOLTAGE_REDOX_MIN) + " mV", true);
                        Donnees.instance().ajouterRequeteBt("Calibration;Redox;1", false);
                    } else if (capteur == Donnees.Capteur.TemperatureBassin) {
                        progressDialog = ProgressDialog.show(MainActivity.instance(), "Étalonnage en cours", "Veuillez mettre la sonde à " + ((int) ((Global.BASE_RESISTANCE_PT + Global.STEP_MIN_PT * Global.COEFFICIENT_PT) * 10)) + " Ohms", true);
                        Donnees.instance().ajouterRequeteBt("Calibration;TempBassin;1", false);
                    }
                    break;
                case 1:
                    if ((progressDialog != null) && (progressDialog.isShowing())) {
                        progressDialog.dismiss();
                    }

                    nbPointEtalonnage = 2;

                    if (capteur == Donnees.Capteur.Ph) {
                        progressDialog = ProgressDialog.show(MainActivity.instance(), "Étalonnage en cours", "Veuillez mettre la sonde à " + (Global.BASE_VOLTAGE_PH_MAX) + " mV", true);
                        Donnees.instance().ajouterRequeteBt("Calibration;pH;2", false);
                    } else if (capteur == Donnees.Capteur.Redox) {
                        progressDialog = ProgressDialog.show(MainActivity.instance(), "Étalonnage en cours", "Veuillez mettre la sonde à " + (Global.BASE_VOLTAGE_REDOX_MAX) + " mV", true);
                        Donnees.instance().ajouterRequeteBt("Calibration;Redox;2", false);
                    } else if (capteur == Donnees.Capteur.TemperatureBassin) {
                        progressDialog = ProgressDialog.show(MainActivity.instance(), "Étalonnage en cours", "Veuillez mettre la sonde à " + ((int) ((Global.BASE_RESISTANCE_PT + Global.STEP_MAX_PT * Global.COEFFICIENT_PT) * 10)) + " Ohms", true);
                        Donnees.instance().ajouterRequeteBt("Calibration;TempBassin;2", false);
                    }
                    break;
                default:
                    if ((progressDialog != null) && (progressDialog.isShowing())) {
                        progressDialog.dismiss();
                    }

                    MainActivity.instance().afficherAlertDialog(annulation ? "Étalonnage impossible" : "Étalonnage réussi", annulation ? nbPointEtalonnage == 1 ? "La valeur est instable." : "Les valeurs sont incohérentes." : "Étalonnage effectué avec succès.", "OK");
                    break;
            }

            typeModification++;
        }
    }
}
