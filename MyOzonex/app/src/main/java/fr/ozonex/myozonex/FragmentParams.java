package fr.ozonex.myozonex;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class FragmentParams extends Fragment implements View.OnClickListener {
    View view = null;

    ScrollView scrollView;

    private LinearLayout layoutBt;
    private TextView texteDonneesBt;
    private ImageButton boutonRechercherBt;

    private LinearLayout layoutWifi;
    private LinearLayout layoutWPS;
    private Switch switchWPS;
    private TextView texteDonneesWiFi;
    private ImageButton boutonRechercherWiFi;
    private WifiManager wifiManager;
    private ArrayAdapter<String> wifiAdapter;
    private ListView listViewWifi;
    private int connectToWiFi = 0;

    private View sepInstallation;
    private Button boutonCodeInstallateur;

    LinearLayout viewQuestion;
    public ProgressDialog progressDialog;

    private Handler handler = new Handler();

    private static final long SCAN_PERIOD = 60000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.params_layout, container, false);

        scrollView = view.findViewById(R.id.scrollview);

        layoutBt = view.findViewById(R.id.layout_bt);
        texteDonneesBt = view.findViewById(R.id.texte_donnees_bt);
        boutonRechercherBt = view.findViewById(R.id.bouton_rechercher_bt);
        boutonRechercherBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BluetoothLe.instance().isInit()) {
                    BluetoothLe.instance().init();
                } else {
                    if (!BluetoothLe.instance().isOn()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        MainActivity.instance().startActivityForResult(enableBtIntent, BluetoothLe.REQUEST_ENABLE_BT);
                    } else {
                        if (BluetoothLe.instance().connected != BluetoothLe.Connected.True) {
                            BluetoothLe.instance().scanLeDevice(true);
                        } else {
                            BluetoothLe.instance().disconnect();
                        }
                    }
                }
            }
        });

        layoutWifi = view.findViewById(R.id.layout_wifi);
        layoutWPS = view.findViewById(R.id.layout_wps);
        switchWPS = view.findViewById(R.id.switch_wps);
        switchWPS.setOnClickListener(this);
        texteDonneesWiFi = view.findViewById(R.id.texte_donnees_wifi);
        boutonRechercherWiFi = view.findViewById(R.id.bouton_rechercher_wifi);
        boutonRechercherWiFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                builder.setTitle(getString(R.string.wifi_connexion, wifiAdapter.getItem(position)));
                builder.setMessage(getString(R.string.wifi_mot_de_passe));

                final EditText input = new EditText(MainActivity.instance());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Donnees.instance().stopTimer();
                        MainActivity.instance().masquerClavier();

                        progressDialog = ProgressDialog.show(MainActivity.instance(), getString(R.string.wifi_titre), getString(R.string.wifi_connexion_en_cours, wifiAdapter.getItem(position)), true);
                        listViewWifi.setVisibility(View.GONE);

                        // Stops scanning after a pre-defined scan period.
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Donnees.instance().ajouterRequeteBt("WiFi?", false);
                            }
                        }, SCAN_PERIOD);

                        connectToWiFi = position + 1;
                        Donnees.instance().ajouterRequeteBt("WiFi;" + wifiAdapter.getItem(position) + ';' + input.getText().toString(), false);
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

        boutonCodeInstallateur.setOnClickListener(this);

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
            texteDonneesBt.setText("Etat : " + (BluetoothLe.instance().isSupported() ? BluetoothLe.instance().isOn() ? BluetoothLe.instance().connected == BluetoothLe.Connected.True ? "Connecté" : "Non connecté" : "Désactivé" : "Non supporté"));
            boutonRechercherBt.setImageResource(BluetoothLe.instance().connected == BluetoothLe.Connected.True ? android.R.drawable.ic_menu_close_clear_cancel : android.R.drawable.ic_menu_search);

            layoutWifi.setVisibility(BluetoothLe.instance().connected == BluetoothLe.Connected.True ? View.VISIBLE : View.GONE);
            layoutWPS.setVisibility(Donnees.instance().getWiFiState() == 0 ? View.VISIBLE : View.GONE);
            texteDonneesWiFi.setText("Etat : " + (Donnees.instance().getWiFiState() == 1 ? "Connecté" : "Non connecté"));
            boutonRechercherWiFi.setVisibility(Donnees.instance().getWiFiState() == 0 ? View.VISIBLE : View.GONE);

            sepInstallation.setVisibility(Integer.parseInt(Donnees.getPreferences(Donnees.BT_PAIRED_DEVICE)) == 1 ? View.VISIBLE : View.GONE);
            boutonCodeInstallateur.setVisibility(!Donnees.instance().obtenirCodeInstallateur() ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_wps:
                switchWPSValueChanged((Switch) v.findViewById(v.getId()));
                break;
            case R.id.bouton_code_installateur:
                afficherQuestion(true, "Veuillez entrer le code installateur");
                break;
            default:
                break;
        }
    }

    public void stopWPS() {
        if ((view != null) && isAdded()) {
            switchWPS.setChecked(false);
            switchWPSValueChanged(switchWPS);
        }
    }

    private void switchWPSValueChanged(Switch sender) {
        update();

        if (sender.isChecked()) {
            Donnees.instance().stopTimer();
            progressDialog = ProgressDialog.show(MainActivity.instance(), getString(R.string.wifi_titre), getString(R.string.wifi_wps_en_cours), true);

            // Stops scanning after a pre-defined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Donnees.instance().startTimer();

                    progressDialog.dismiss();
                    progressDialog = null;

                    switchWPS.setChecked(false);
                    MainActivity.instance().afficherAlertDialog(getString(R.string.wifi_titre), getString(R.string.wifi_erreur_wps), "OK");
                    Donnees.instance().ajouterRequeteBt("WPS;0", false);
                }
            }, SCAN_PERIOD);

            Donnees.instance().ajouterRequeteBt("WPS;1", false);
        } else {
            Donnees.instance().startTimer();
            handler.removeCallbacksAndMessages(null);
            MainActivity.instance().afficherAlertDialog(getString(R.string.wifi_titre), getString(R.string.wifi_connexion_ok), "OK");
        }
    }

    private void scanWifi() {
        MainActivity.instance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = ProgressDialog.show(MainActivity.instance(), getString(R.string.wifi_titre), getString(R.string.wifi_recherche_en_cours), true);
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

            progressDialog.dismiss();
            progressDialog = null;

            texteDonneesWiFi.setText("Etat : Veuillez sélectionner le réseau Wi-Fi");
            listViewWifi.setVisibility(View.VISIBLE);
            MainActivity.instance().unregisterReceiver(wifiScanReceiver);
        }
    };

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

    public void stopWiFi() {
        if ((view != null) && isAdded() && (connectToWiFi > 0)) {
            Donnees.instance().startTimer();
            handler.removeCallbacksAndMessages(null);

            progressDialog.dismiss();
            progressDialog = null;

            if (Donnees.instance().getWiFiState() == 1) {
                MainActivity.instance().afficherAlertDialog(getString(R.string.wifi_titre), getString(R.string.wifi_connexion_ok), "OK");
            } else {
                MainActivity.instance().afficherAlertDialog(getString(R.string.wifi_titre), getString(R.string.wifi_erreur_connexion, wifiAdapter.getItem(connectToWiFi - 1)), "OK");
            }

            connectToWiFi = 0;
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
}
