package fr.ozonex.myozonex;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.internal.NavigationMenuView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static MainActivity inst;
    public static MainActivity instance() {
        return inst;
    }

    private Intent serviceIntent;

    public boolean premierDemarrage = false;
    private boolean activityResume = false;

    private Handler clignotementLED;

    public Toolbar toolbar;
    private TextView toolbarTitle;

    private ActionBarDrawerToggle toggle;
    public Menu menu;
    private TextView idSysteme;
    private boolean msgEtatSysteme = false;
    private ImageView imageEtatSysteme;
    private TextView texteEtatSysteme;
    private TextView versionApplication;

    private FragmentConnexion fragmentConnexion = new FragmentConnexion();
    private FragmentDonnees fragmentDonnees = new FragmentDonnees();
    private FragmentSynoptique fragmentSynoptique = new FragmentSynoptique();
    private FragmentBassin fragmentBassin = new FragmentBassin();
    private FragmentEclairage fragmentEclairage = new FragmentEclairage();
    private FragmentPompeFiltration fragmentPompeFiltration = new FragmentPompeFiltration();
    private FragmentFiltre fragmentFiltre = new FragmentFiltre();
    private FragmentSurpresseur fragmentSurpresseur = new FragmentSurpresseur();
    private FragmentChauffage fragmentChauffage = new FragmentChauffage();
    private FragmentLampesUV fragmentLampesUV = new FragmentLampesUV();
    private FragmentOzone fragmentOzone = new FragmentOzone();
    private FragmentRegulateurPhPlus fragmentRegulateurPhPlus = new FragmentRegulateurPhPlus();
    private FragmentRegulateurPhMoins fragmentRegulateurPhMoins = new FragmentRegulateurPhMoins();
    private FragmentRegulateurORP fragmentRegulateurORP = new FragmentRegulateurORP();
    private FragmentAlgicide fragmentAlgicide = new FragmentAlgicide();
    private FragmentEvents fragmentEvents = new FragmentEvents();
    private FragmentAutomatisation fragmentAutomatisation = new FragmentAutomatisation();
    private FragmentParams fragmentParams = new FragmentParams();

    private Donnees.Capteur typeCapteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inst = this;

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        menu = navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(this);

        drawer.setFitsSystemWindows(true);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                ((NavigationMenuView)((NavigationView)findViewById(R.id.nav_view)).getChildAt(0)).scrollToPosition(0);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        idSysteme = headerView.findViewById(R.id.id_systeme);
        imageEtatSysteme = headerView.findViewById(R.id.image_etat_systeme);
        texteEtatSysteme = headerView.findViewById(R.id.texte_etat_systeme);
        versionApplication = headerView.findViewById(R.id.version_application);

        serviceIntent = new Intent(this, HttpService.class);

        new CustomNotification(this);

        clignotementLED = new Handler();

        int index = 1;
        while (true) {
            if (!Donnees.getPreferences(Donnees.ID_SYSTEME_X.replace("x", String.valueOf(index))).isEmpty()) {
                Donnees.instance().ajouterAppareil(Donnees.getPreferences(Donnees.ID_SYSTEME_X.replace("x", String.valueOf(index))));
            } else {
                break;
            }

            index++;
        }

        if (!Donnees.getPreferences(Donnees.ID_SYSTEME).isEmpty()) {
            premierDemarrage = true;
            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Get, StructureHttp.PageHTTP.PageLogin, "admin", false);
        } else {
            onNavigationItemSelected(menu.findItem(R.id.nav_deconnexion_layout));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        activityResume = true;

        if (!isMyServiceRunning(HttpService.class)) {
            startService(serviceIntent);
        }

        if (clignotementLED != null) {
            clignotementLED.postDelayed(clignotement,0);
        }

        //MainActivity.instance().registerReceiver(BluetoothLe.instance().gattUpdateReceiver, BluetoothLe.instance().makeGattUpdateIntentFilter());

        CustomNotification.instance().supprimer();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onPause() {
        super.onPause();
        activityResume = false;

        Donnees.instance().definirCodeInstallateur(false);

        if (clignotementLED != null) {
            clignotementLED.removeCallbacks(clignotement);
        }

        //unregisterReceiver(BluetoothLe.instance().gattUpdateReceiver);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onDestroy() {
        activityResume = false;

        Donnees.instance().definirCodeInstallateur(false);

        if (Bluetooth.instance().isConnected()) {
            Bluetooth.instance().disconnect();
        }
        //fragmentPremiereConnexion.unregisterReceiver();
        stopService(serviceIntent);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_deconnexion_layout) {
            showNavigationViewButton(false);

            Donnees.setPreferences(Donnees.ID_SYSTEME, "");
            if (Bluetooth.instance().isConnected()) {
                Bluetooth.instance().disconnect();
            }
            Donnees.instance().setWiFiState(0);

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentConnexion)
                    .commit();
            toolbarTitle.setText(getString(R.string.connexion));
        } else if (id == R.id.nav_accueil_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentDonnees)
                    .commit();
            toolbarTitle.setText(getString(R.string.accueil));
        } else if (id == R.id.nav_synoptique_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentSynoptique)
                    .commit();
            toolbarTitle.setText(getString(R.string.synoptique));
        } else if (id == R.id.nav_bassin_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentBassin)
                    .commit();
            toolbarTitle.setText(getString(R.string.bassin));
        } else if (id == R.id.nav_eclairage_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentEclairage)
                    .commit();
            toolbarTitle.setText(getString(R.string.eclairage));
        } else if (id == R.id.nav_pompe_filtration_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentPompeFiltration)
                    .commit();
            toolbarTitle.setText(getString(R.string.pompe_filtration));
        } else if (id == R.id.nav_filtre_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentFiltre)
                    .commit();
            toolbarTitle.setText(getString(R.string.filtre));
        } else if (id == R.id.nav_surpresseur_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentSurpresseur)
                    .commit();
            toolbarTitle.setText(getString(R.string.surpresseur));
        } else if (id == R.id.nav_chauffage_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentChauffage)
                    .commit();
            toolbarTitle.setText(getString(R.string.chauffage));
        } else if (id == R.id.nav_lampes_uv_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentLampesUV)
                    .commit();
            toolbarTitle.setText(getString(R.string.lampes_uv));
        } else if (id == R.id.nav_ozone_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentOzone)
                    .commit();
            toolbarTitle.setText(getString(R.string.ozonateur));
        } else if (id == R.id.nav_regulateur_ph_plus_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentRegulateurPhPlus)
                    .commit();
            toolbarTitle.setText(getString(R.string.regulateur_ph_plus));
        } else if (id == R.id.nav_regulateur_ph_moins_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentRegulateurPhMoins)
                    .commit();
            toolbarTitle.setText(getString(R.string.regulateur_ph_moins));
        } else if (id == R.id.nav_regulateur_orp_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentRegulateurORP)
                    .commit();
            toolbarTitle.setText(getString(R.string.regulateur_orp));
        } else if (id == R.id.nav_algicide_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentAlgicide)
                    .commit();
            toolbarTitle.setText(getString(R.string.algicide));
        } else if (id == R.id.nav_events_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentEvents)
                    .commit();
            toolbarTitle.setText(getString(R.string.events));
        } else if (id == R.id.nav_automatisation_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentAutomatisation)
                    .commit();
            toolbarTitle.setText(getString(R.string.automatisation));
        } else if (id == R.id.nav_parametres_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentParams)
                    .commit();
            toolbarTitle.setText(getString(R.string.parametres));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == Bluetooth.REQUEST_ENABLE_BT) {
            if (Bluetooth.instance().isOn()) {
                Bluetooth.instance().connect();
            }
        }
    }

    public boolean isActivityResumed() {
        return activityResume;
    }

    private Runnable clignotement = new Runnable() {
        @Override
        public void run() {
            fragmentDonnees.clignotement();
            fragmentSynoptique.clignotement();

            clignotementLED.postDelayed(this, Global.TEMPO_CLIGNOTEMENT_LED);
        }
    };

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }

        return false;
    }

    public void masquerClavier() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showNavigationViewButton(boolean visible) {
        if (toggle != null) {
            toggle.setDrawerIndicatorEnabled(visible);
        }
    }

    public void updatePages() {
        updateNavigationView();
        fragmentEvents.update();
        fragmentAutomatisation.update();
        fragmentSynoptique.update();
        fragmentBassin.update();
        fragmentEclairage.update();
        fragmentPompeFiltration.update();
        fragmentFiltre.update();
        fragmentSurpresseur.update();
        fragmentChauffage.update();
        fragmentLampesUV.update();
        fragmentOzone.update();
        fragmentRegulateurPhPlus.update();
        fragmentRegulateurPhMoins.update();
        fragmentRegulateurORP.update();
        fragmentAlgicide.update();
        fragmentDonnees.update();
    }

    private void updateNavigationView() {
        idSysteme.setText("Système : " + Donnees.getPreferences(Donnees.ID_SYSTEME));
        imageEtatSysteme.setImageResource(Donnees.instance().obtenirActiviteIHM() ? android.R.drawable.presence_online : android.R.drawable.presence_offline);
        texteEtatSysteme.setText("Etat : " + (Donnees.instance().obtenirActiviteIHM() ? "connecté" : "déconnecté"));

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionApplication.setText("Version : " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            //e.printStackTrace();
            versionApplication.setText("Version : visible dans les paramètres de l'application");
        }

        menu.findItem(R.id.nav_aucun_equipement_option_layout).setVisible(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PompeFiltration) && !Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Filtre) && !Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur) && !Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) && !Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.LampesUV) && !Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Electrolyseur) && !Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Ozone));
        menu.findItem(R.id.nav_eclairage_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Eclairage));
        menu.findItem(R.id.nav_pompe_filtration_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PompeFiltration));
        menu.findItem(R.id.nav_filtre_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Filtre));
        menu.findItem(R.id.nav_surpresseur_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur));
        menu.findItem(R.id.nav_chauffage_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage));
        menu.findItem(R.id.nav_lampes_uv_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.LampesUV));
        menu.findItem(R.id.nav_electrolyseur_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Electrolyseur));
        menu.findItem(R.id.nav_ozone_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Ozone));
        menu.findItem(R.id.nav_aucune_regulations_layout).setVisible(!Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus) && !Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) && !Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) && !Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Algicide));
        menu.findItem(R.id.nav_regulateur_ph_moins_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins));
        menu.findItem(R.id.nav_regulateur_ph_plus_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus));
        menu.findItem(R.id.nav_regulateur_orp_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp));
        menu.findItem(R.id.nav_algicide_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Algicide));
    }

    public void afficherAlertDialog(String title, String message, String buttonTitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.instance());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(buttonTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void cancelBt() {
        fragmentParams.cancelBt();
    }

    public void getTreatment(String result) {
        if (result == null) {
            if (Integer.parseInt(Donnees.getPreferences(Donnees.BT_PAIRED_DEVICE)) == 1) {
                Bluetooth.instance().init();
            } else {
                if (isActivityResumed()) {
                    Toast.makeText(this, "Un problème est survenu lors de la communication avec le serveur", Toast.LENGTH_SHORT).show();
                }
            }

            if (premierDemarrage) {
                premierDemarrage = false;
                onNavigationItemSelected(menu.findItem(R.id.nav_deconnexion_layout));
            }
        } else {
            if (!result.isEmpty()) {
                if (result.contains("LOG")) {
                    if (result.contains("OK")) {
                        int index = 1;
                        while (!Donnees.instance().obtenirListeAppareils().contains(Donnees.getPreferences(Donnees.ID_SYSTEME))) {
                            if (Donnees.getPreferences(Donnees.ID_SYSTEME_X.replace("x", String.valueOf(index))).isEmpty()) {
                                Donnees.setPreferences(Donnees.ID_SYSTEME_X.replace("x", String.valueOf(index)), Donnees.getPreferences(Donnees.ID_SYSTEME));
                                Donnees.instance().ajouterAppareil(Donnees.getPreferences(Donnees.ID_SYSTEME));
                                fragmentConnexion.update();
                            }

                            index++;
                        }

                        premierDemarrage = false;
                        updateNavigationView();
                        showNavigationViewButton(true);
                        onNavigationItemSelected(menu.findItem(R.id.nav_accueil_layout));

                        Donnees.instance().definirTypeAppareil(Integer.parseInt(result.split(";")[1]));
                        Donnees.setPreferences(Donnees.BT_PAIRED_DEVICE, Donnees.instance().obtenirTypeAppareil() >= Donnees.MYOZONEXMINI ? "1" : "0");
                        if (Integer.parseInt(Donnees.getPreferences(Donnees.BT_PAIRED_DEVICE)) == 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.instance());
                            builder.setCancelable(true);
                            builder.setTitle("Connexion Bluetooth");
                            builder.setMessage("Souhaitez-vous vous connecter via Bluetooth ?");

                            builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Bluetooth.instance().init();
                                    //BluetoothLe.instance().init();
                                }
                            });

                            builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                        Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Get, StructureHttp.PageHTTP.PageData, "", false);
                    } else {
                        Donnees.setPreferences(Donnees.ID_SYSTEME, "");

                        if (isActivityResumed()) {
                            MainActivity.instance().afficherAlertDialog("Connexion", "ID et/ou mot de passe erroné.", "OK");
                        }
                    }
                } else {
                    JSONObject jsonObject = null;

                    try {
                        jsonObject = new JSONObject(result);
                        JSONObject object;

                        try {
                            object = new JSONObject(jsonObject.getString("Automatisation"));
                            Donnees.instance().definirHeuresCreusesAuto(object.getInt("heures_creuses") > 0);
                            Donnees.instance().definirDonneesEquipementsAuto(object.getInt("donnees_equipement") > 0);
                            Donnees.instance().definirModifPlagesAuto(object.getInt("modif_plage_auto") > 0);
                            Donnees.instance().definirPlagesAuto(object.getInt("plages_auto") > 0);
                            Donnees.instance().definirDebutPlageAuto(object.getString("debut_plage_auto"));
                            Donnees.instance().definirTempsFiltrationJour(object.getString("temps_filtration_jour"));
                            Donnees.instance().definirPlageAuto(object.getString("plage_auto"));
                            Donnees.instance().definirAsservissementAuto(Donnees.Equipement.PhPlus, object.getInt("asservissement_ph_plus") > 0);
                            Donnees.instance().definirAsservissementAuto(Donnees.Equipement.PhMoins, object.getInt("asservissement_ph_moins") > 0);
                            Donnees.instance().definirAsservissementAuto(Donnees.Equipement.Orp, object.getInt("asservissement_orp") > 0);
                            Donnees.instance().definirPointConsigneOrpAuto(object.getInt("consigne_orp_auto") > 0);
                            //Donnees.instance().definirPointConsigneOrpAuto(object.getInt("capteur_niveau_eau") > 0);
                        } catch (JSONException e) {
                            Log.d("ERROR", "Automatisation");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Bassin"));
                            Donnees.instance().definirVolumeBassin(object.getDouble("volume"));
                            Donnees.instance().definirTempoDemarrage(object.getInt("temporisation_demarrage"));
                            Donnees.instance().definirTypeRefoulement(object.getInt("type_refoulement"));
                            Donnees.instance().definirTypeRegulation(object.getInt("type_regulation"));
                            Donnees.instance().definirTempsSecuriteInjection(object.getInt("temps_securite_injection"));
                            Donnees.instance().definirHystInjectionPh(object.getDouble("hyst_injection_ph"));
                            Donnees.instance().definirHystInjectionChloreOrp(object.getInt("hyst_injection_orp"));
                            Donnees.instance().definirHystInjectionChloreAmpero(object.getDouble("hyst_injection_ampero"));
                            Donnees.instance().definirEtatRegulations(object.getInt("etat_regulations") > 0);
                        } catch (JSONException e) {
                            Log.d("ERROR", "Bassin");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Capteurs"));
                            Donnees.instance().definirCapteurInstalle(Donnees.Capteur.TemperatureBassin, object.getJSONObject("Température bassin").getInt("installe") > 0);
                            Donnees.instance().definirEtatCapteur(Donnees.Capteur.TemperatureBassin, object.getJSONObject("Température bassin").getString("etat"));
                            Donnees.instance().definirValeurCapteur(Donnees.Capteur.TemperatureBassin, object.getJSONObject("Température bassin").getDouble("valeur"));

                            Donnees.instance().definirCapteurInstalle(Donnees.Capteur.CapteurInterne, object.getJSONObject("Température interne").getInt("installe") > 0);
                            Donnees.instance().definirEtatCapteur(Donnees.Capteur.TemperatureInterne, object.getJSONObject("Température interne").getString("etat"));
                            Donnees.instance().definirValeurCapteur(Donnees.Capteur.TemperatureInterne, object.getJSONObject("Température interne").getDouble("valeur"));
                            Donnees.instance().definirEtatCapteur(Donnees.Capteur.HumiditeInterne, object.getJSONObject("Humidité interne").getString("etat"));
                            Donnees.instance().definirValeurCapteur(Donnees.Capteur.HumiditeInterne, object.getJSONObject("Humidité interne").getDouble("valeur"));
                            Donnees.instance().definirEtatCapteur(Donnees.Capteur.PressionAtmospheriqueInterne, object.getJSONObject("Pression atmosphérique interne").getString("etat"));
                            Donnees.instance().definirValeurCapteur(Donnees.Capteur.PressionAtmospheriqueInterne, object.getJSONObject("Pression atmosphérique interne").getDouble("valeur"));

                            Donnees.instance().definirCapteurInstalle(Donnees.Capteur.CapteurExterne, object.getJSONObject("Température externe").getInt("installe") > 0);
                            Donnees.instance().definirEtatCapteur(Donnees.Capteur.TemperatureExterne, object.getJSONObject("Température externe").getString("etat"));
                            Donnees.instance().definirValeurCapteur(Donnees.Capteur.TemperatureExterne, object.getJSONObject("Température externe").getDouble("valeur"));
                            Donnees.instance().definirEtatCapteur(Donnees.Capteur.HumiditeExterne, object.getJSONObject("Humidité externe").getString("etat"));
                            Donnees.instance().definirValeurCapteur(Donnees.Capteur.HumiditeExterne, object.getJSONObject("Humidité externe").getDouble("valeur"));
                            Donnees.instance().definirEtatCapteur(Donnees.Capteur.PressionAtmospheriqueExterne, object.getJSONObject("Pression atmosphérique externe").getString("etat"));
                            Donnees.instance().definirValeurCapteur(Donnees.Capteur.PressionAtmospheriqueExterne, object.getJSONObject("Pression atmosphérique externe").getDouble("valeur"));

                            Donnees.instance().definirCapteurInstalle(Donnees.Capteur.Ph, object.getJSONObject("pH").getInt("installe") > 0);
                            Donnees.instance().definirEtatCapteur(Donnees.Capteur.Ph, object.getJSONObject("pH").getString("etat"));
                            Donnees.instance().definirValeurCapteur(Donnees.Capteur.Ph, object.getJSONObject("pH").getDouble("valeur"));

                            Donnees.instance().definirCapteurInstalle(Donnees.Capteur.Redox, object.getJSONObject("ORP").getInt("installe") > 0);
                            Donnees.instance().definirEtatCapteur(Donnees.Capteur.Redox, object.getJSONObject("ORP").getString("etat"));
                            Donnees.instance().definirValeurCapteur(Donnees.Capteur.Redox, object.getJSONObject("ORP").getDouble("valeur"));

                            Donnees.instance().definirCapteurInstalle(Donnees.Capteur.Pression, object.getJSONObject("Pression").getInt("installe") > 0);
                            Donnees.instance().definirEtatCapteur(Donnees.Capteur.Pression, object.getJSONObject("Pression").getString("etat"));
                            Donnees.instance().definirValeurCapteur(Donnees.Capteur.Pression, object.getJSONObject("Pression").getDouble("valeur"));

                            Donnees.instance().definirCapteurInstalle(Donnees.Capteur.Ampero, object.getJSONObject("Ampéro").getInt("installe") > 0);
                            Donnees.instance().definirEtatCapteur(Donnees.Capteur.Ampero, object.getJSONObject("Ampéro").getString("etat"));
                            Donnees.instance().definirValeurCapteur(Donnees.Capteur.Ampero, object.getJSONObject("Ampéro").getDouble("valeur"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Capteurs");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Eclairage"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Eclairage, object.getInt("installe") > 0);
                            Donnees.instance().definirEtatEquipement(Donnees.Equipement.Eclairage, object.getInt("etat"));
                            for (int i = 0; i < Global.MAX_PLAGES_EQUIPEMENTS; i++) {
                                Donnees.instance().definirPlageFonctionnement(Donnees.Equipement.Eclairage, i, object.getString(String.format("plage_%d", i+1)));
                            }
                        } catch (JSONException e) {
                            Log.d("ERROR", "Eclairage");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Pompe filtration"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.PompeFiltration, object.getInt("installe") > 0);
                            Donnees.instance().definirEtatEquipement(Donnees.Equipement.PompeFiltration, object.getInt("etat"));
                            Donnees.instance().definirEtatLectureCapteurs(object.getInt("lecture_capteurs") > 0);
                            Donnees.instance().definirDateConso(Donnees.Equipement.PompeFiltration, object.getString("date_consommation"));
                            Donnees.instance().definirConsoHP(Donnees.Equipement.PompeFiltration, object.getDouble("consommation_hp"));
                            Donnees.instance().definirConsoHC(Donnees.Equipement.PompeFiltration, object.getDouble("consommation_hc"));
                            for (int i = 0; i < Global.MAX_PLAGES_EQUIPEMENTS; i++) {
                                Donnees.instance().definirPlageFonctionnement(Donnees.Equipement.PompeFiltration, i, object.getString(String.format("plage_%d", i+1)));
                            }
                            Donnees.instance().definirEtatHorsGel(object.getInt("etat_hors_gel") > 0);
                            Donnees.instance().definirEnclHorsGel(object.getInt("enclenchement_hors_gel"));
                            Donnees.instance().definirArretHorsGel(object.getInt("arret_hors_gel"));
                            Donnees.instance().definirFreqHorsGel(object.getInt("frequence_hors_gel"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Pompe filtration");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Filtre"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Filtre, object.getInt("installe") > 0);
                            Donnees.instance().definirDateDernierLavage(object.getString("date_dernier_lavage"));
                            Donnees.instance().definirPressionApresLavage(object.getDouble("pression_apres_lavage"));
                            Donnees.instance().definirPressionProchainLavage(object.getDouble("pression_prochain_lavage"));
                            Donnees.instance().definirSeuilRincage(object.getInt("seuil_rincage"));
                            Donnees.instance().definirSeuilSecuriteSurpression(object.getDouble("seuil_securite_surpression"));
                            Donnees.instance().definirSeuilHautPression(object.getDouble("seuil_haut_pression"));
                            Donnees.instance().definirSeuilBasPression(object.getDouble("seuil_bas_pression"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Filtre");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Surpresseur"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Surpresseur, object.getInt("installe") > 0);
                            Donnees.instance().definirEtatEquipement(Donnees.Equipement.Surpresseur, object.getInt("etat"));
                            Donnees.instance().definirDateConso(Donnees.Equipement.Surpresseur, object.getString("date_consommation"));
                            Donnees.instance().definirConsoHP(Donnees.Equipement.Surpresseur, object.getDouble("consommation_hp"));
                            Donnees.instance().definirConsoHC(Donnees.Equipement.Surpresseur, object.getDouble("consommation_hc"));
                            for (int i = 0; i < Global.MAX_PLAGES_EQUIPEMENTS; i++) {
                                Donnees.instance().definirPlageFonctionnement(Donnees.Equipement.Surpresseur, i, object.getString(String.format("plage_%d", i+1)));
                            }
                        } catch (JSONException e) {
                            Log.d("ERROR", "Surpresseur");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Chauffage"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Chauffage, object.getInt("installe") > 0);
                            Donnees.instance().definirEtatEquipement(Donnees.Equipement.Chauffage, object.getInt("etat"));
                            Donnees.instance().definirDateConso(Donnees.Equipement.Chauffage, object.getString("date_consommation"));
                            Donnees.instance().definirConsoHP(Donnees.Equipement.Chauffage, object.getDouble("consommation_hp"));
                            Donnees.instance().definirConsoHC(Donnees.Equipement.Chauffage, object.getDouble("consommation_hc"));
                            Donnees.instance().definirControlePompeFiltration(object.getInt("gestion_temperature"));
                            Donnees.instance().definirTemperatureConsigne(object.getInt("temperature_consigne"));
                            //Donnees.instance().definirControlePompeFiltration(object.getInt("gestion_reversible"));
                            //Donnees.instance().definirTemperatureConsigne(object.getInt("temperature_reversible"));
                            Donnees.instance().definirTypeChauffage(object.getInt("type_chauffage"));
                            Donnees.instance().definirAlarmeSeuilBas(Donnees.Equipement.Chauffage, object.getInt("alarme_seuil_bas"), null);
                            Donnees.instance().definirAlarmeSeuilHaut(Donnees.Equipement.Chauffage, object.getInt("alarme_seuil_haut"), null);
                        } catch (JSONException e) {
                            Log.d("ERROR", "Chauffage");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Lampes UV"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.LampesUV, object.getInt("installe") > 0);
                            Donnees.instance().definirEtatEquipement(Donnees.Equipement.LampesUV, object.getInt("etat"));
                            Donnees.instance().definirDateConso(Donnees.Equipement.LampesUV, object.getString("date_consommation"));
                            Donnees.instance().definirConsoHP(Donnees.Equipement.LampesUV, object.getDouble("consommation_hp"));
                            Donnees.instance().definirConsoHC(Donnees.Equipement.LampesUV, object.getDouble("consommation_hc"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Lampes UV");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Ozonateur"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Ozone, object.getInt("installe") > 0);
                            Donnees.instance().definirEtatEquipement(Donnees.Equipement.Ozone, object.getInt("etat"));
                            Donnees.instance().definirDateConso(Donnees.Equipement.Ozone, object.getString("date_consommation"));
                            Donnees.instance().definirConsoHP(Donnees.Equipement.Ozone, object.getDouble("consommation_hp"));
                            Donnees.instance().definirConsoHC(Donnees.Equipement.Ozone, object.getDouble("consommation_hc"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Ozonateur");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Electrolyseur"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Electrolyseur, object.getInt("installe") > 0);
                            Donnees.instance().definirEtatEquipement(Donnees.Equipement.Electrolyseur, object.getInt("etat"));
                            Donnees.instance().definirDateConso(Donnees.Equipement.Electrolyseur, object.getString("date_consommation"));
                            Donnees.instance().definirConsoHP(Donnees.Equipement.Electrolyseur, object.getDouble("consommation_hp"));
                            Donnees.instance().definirConsoHC(Donnees.Equipement.Electrolyseur, object.getDouble("consommation_hc"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Electrolyseur");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Régulateur pH"));
                            Donnees.instance().definirPointConsignePh(object.getDouble("point_consigne"));
                            Donnees.instance().definirHysteresisPhPlus(object.getDouble("hysteresis_plus"));
                            Donnees.instance().definirHysteresisPhMoins(object.getDouble("hysteresis_moins"));
                            Donnees.instance().definirAlarmeSeuilBas(Donnees.Equipement.PhGlobal, object.getDouble("alarme_seuil_bas"), null);
                            Donnees.instance().definirAlarmeSeuilHaut(Donnees.Equipement.PhGlobal, object.getDouble("alarme_seuil_haut"), null);
                        } catch (JSONException e) {
                            Log.d("ERROR", "Régulateur pH");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Régulateur pH-"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.PhMoins, object.getInt("installe") > 0);
                            Donnees.instance().definirEtatEquipement(Donnees.Equipement.PhMoins, object.getInt("etat"));
                            Donnees.instance().definirDebitEquipement(Donnees.Equipement.PhMoins, object.getDouble("debit"));
                            Donnees.instance().definirDateConso(Donnees.Equipement.PhMoins, object.getString("date_consommation"));
                            Donnees.instance().definirVolume(Donnees.Equipement.PhMoins, object.getDouble("volume"));
                            Donnees.instance().definirVolumeRestant(Donnees.Equipement.PhMoins, object.getDouble("volume_restant"));
                            Donnees.instance().definirConsoJour(Donnees.Equipement.PhMoins, object.getDouble("consommation_jour"));
                            Donnees.instance().definirConsoSemaine(Donnees.Equipement.PhMoins, object.getDouble("consommation_semaine"));
                            Donnees.instance().definirConsoMois(Donnees.Equipement.PhMoins, object.getDouble("consommation_mois"));
                            Donnees.instance().definirTraitementEnCours(Donnees.Equipement.PhMoins, object.getInt("injection"));
                            Donnees.instance().definirDureeCycle(Donnees.Equipement.PhMoins, object.getInt("duree_cycle"));
                            Donnees.instance().definirMultiplicateurDifference(Donnees.Equipement.PhMoins, object.getInt("multiplicateur_diff"));
                            Donnees.instance().definirDureeInjectionMinimum(Donnees.Equipement.PhMoins, object.getInt("duree_injection_minimum"));
                            Donnees.instance().definirDureeInjection(Donnees.Equipement.PhMoins, object.getDouble("duree_injection"));
                            Donnees.instance().definirTempsReponse(Donnees.Equipement.PhMoins, object.getInt("temps_reponse"));
                            Donnees.instance().definirTempsInjectionJournalierMax(Donnees.Equipement.PhMoins, object.getInt("temps_injection_jour_max"));
                            Donnees.instance().definirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhMoins, object.getInt("temps_injection_jour_max_restant"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Régulateur pH-");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Régulateur pH+"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.PhPlus, object.getInt("installe") > 0);
                            Donnees.instance().definirEtatEquipement(Donnees.Equipement.PhPlus, object.getInt("etat"));
                            Donnees.instance().definirDebitEquipement(Donnees.Equipement.PhPlus, object.getDouble("debit"));
                            Donnees.instance().definirDateConso(Donnees.Equipement.PhPlus, object.getString("date_consommation"));
                            Donnees.instance().definirVolume(Donnees.Equipement.PhPlus, object.getDouble("volume"));
                            Donnees.instance().definirVolumeRestant(Donnees.Equipement.PhPlus, object.getDouble("volume_restant"));
                            Donnees.instance().definirConsoJour(Donnees.Equipement.PhPlus, object.getDouble("consommation_jour"));
                            Donnees.instance().definirConsoSemaine(Donnees.Equipement.PhPlus, object.getDouble("consommation_semaine"));
                            Donnees.instance().definirConsoMois(Donnees.Equipement.PhPlus, object.getDouble("consommation_mois"));
                            Donnees.instance().definirTraitementEnCours(Donnees.Equipement.PhPlus, object.getInt("injection"));
                            Donnees.instance().definirDureeCycle(Donnees.Equipement.PhPlus, object.getInt("duree_cycle"));
                            Donnees.instance().definirMultiplicateurDifference(Donnees.Equipement.PhPlus, object.getInt("multiplicateur_diff"));
                            Donnees.instance().definirDureeInjectionMinimum(Donnees.Equipement.PhPlus, object.getInt("duree_injection_minimum"));
                            Donnees.instance().definirDureeInjection(Donnees.Equipement.PhPlus, object.getDouble("duree_injection"));
                            Donnees.instance().definirTempsReponse(Donnees.Equipement.PhPlus, object.getInt("temps_reponse"));
                            Donnees.instance().definirTempsInjectionJournalierMax(Donnees.Equipement.PhPlus, object.getInt("temps_injection_jour_max"));
                            Donnees.instance().definirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhPlus, object.getInt("temps_injection_jour_max_restant"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Régulateur pH+");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Régulateur ORP"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Orp, object.getInt("installe") > 0);
                            Donnees.instance().definirPointConsigneOrp(object.getInt("point_consigne_orp"));
                            Donnees.instance().definirHysteresisOrp(object.getInt("hysteresis_orp"));
                            Donnees.instance().definirPointConsigneAmpero(object.getDouble("point_consigne_ampero"));
                            Donnees.instance().definirHysteresisAmpero(object.getDouble("hysteresis_ampero"));
                            Donnees.instance().definirChloreLibreActif(object.getDouble("chlore_libre_actif"));
                            Donnees.instance().definirEtatEquipement(Donnees.Equipement.Orp, object.getInt("etat"));
                            Donnees.instance().definirDebitEquipement(Donnees.Equipement.Orp, object.getDouble("debit"));
                            Donnees.instance().definirDateConso(Donnees.Equipement.Orp, object.getString("date_consommation"));
                            Donnees.instance().definirVolume(Donnees.Equipement.Orp, object.getDouble("volume"));
                            Donnees.instance().definirVolumeRestant(Donnees.Equipement.Orp, object.getDouble("volume_restant"));
                            Donnees.instance().definirConsoJour(Donnees.Equipement.Orp, object.getDouble("consommation_jour"));
                            Donnees.instance().definirConsoSemaine(Donnees.Equipement.Orp, object.getDouble("consommation_semaine"));
                            Donnees.instance().definirConsoMois(Donnees.Equipement.Orp, object.getDouble("consommation_mois"));
                            Donnees.instance().definirTraitementEnCours(Donnees.Equipement.Orp, object.getInt("injection"));
                            Donnees.instance().definirDureeCycle(Donnees.Equipement.Orp, object.getInt("duree_cycle"));
                            Donnees.instance().definirMultiplicateurDifference(Donnees.Equipement.Orp, object.getInt("multiplicateur_diff"));
                            Donnees.instance().definirDureeInjectionMinimum(Donnees.Equipement.Orp, object.getInt("duree_injection_minimum"));
                            Donnees.instance().definirDureeInjection(Donnees.Equipement.Orp, object.getDouble("duree_injection"));
                            Donnees.instance().definirTempsReponse(Donnees.Equipement.Orp, object.getInt("temps_reponse"));
                            Donnees.instance().definirTempsInjectionJournalierMax(Donnees.Equipement.Orp, object.getInt("temps_injection_jour_max"));
                            Donnees.instance().definirTempsInjectionJournalierMaxRestant(Donnees.Equipement.Orp, object.getInt("temps_injection_jour_max_restant"));
                            Donnees.instance().definirEtat(Donnees.Equipement.Orp, object.getInt("surchloration"));
                            Donnees.instance().definirJourSurchloration(object.getInt("jour"));
                            Donnees.instance().definirFrequenceSurchloration(object.getString("frequence"));
                            Donnees.instance().definirValeurSurchloration(object.getInt("mv_ajoute"));
                            Donnees.instance().definirProchaineSurchloration(object.getInt("prochaine_surchloration"));
                            Donnees.instance().definirAlarmeSeuilBas(Donnees.Equipement.Orp, object.getDouble("alarme_seuil_bas_ampero"), Donnees.Capteur.Ampero);
                            Donnees.instance().definirAlarmeSeuilHaut(Donnees.Equipement.Orp, object.getDouble("alarme_seuil_haut_ampero"), Donnees.Capteur.Ampero);
                            Donnees.instance().definirAlarmeSeuilBas(Donnees.Equipement.Orp, object.getInt("alarme_seuil_bas_orp"), Donnees.Capteur.Redox);
                            Donnees.instance().definirAlarmeSeuilHaut(Donnees.Equipement.Orp, object.getInt("alarme_seuil_haut_orp"), Donnees.Capteur.Redox);
                        } catch (JSONException e) {
                            Log.d("ERROR", "Régulateur ORP");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Algicide"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Algicide, object.getInt("installe") > 0);
                            Donnees.instance().definirEtatEquipement(Donnees.Equipement.Algicide, object.getInt("etat"));
                            Donnees.instance().definirDebitEquipement(Donnees.Equipement.Algicide, object.getDouble("debit"));
                            Donnees.instance().definirDateConso(Donnees.Equipement.Algicide, object.getString("date_consommation"));
                            Donnees.instance().definirVolume(Donnees.Equipement.Algicide, object.getDouble("volume"));
                            Donnees.instance().definirVolumeRestant(Donnees.Equipement.Algicide, object.getDouble("volume_restant"));
                            Donnees.instance().definirTraitementEnCours(Donnees.Equipement.Algicide, object.getInt("injection"));
                            Donnees.instance().definirEtat(Donnees.Equipement.Algicide, object.getInt("active"));
                            Donnees.instance().definirJourAlgicide(object.getInt("jour"));
                            Donnees.instance().definirFrequenceAlgicide(object.getString("frequence"));
                            Donnees.instance().definirDureeAlgicide(object.getInt("pendant"));
                            Donnees.instance().definirProchaineAlgicide(object.getInt("prochain"));
                            Donnees.instance().definirDureeRestantAlgicide(object.getInt("temps_restant"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Algicide");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Horlogerie"));
                            for (int i = 0; i < Global.MAX_PLAGES_HEURES_CREUSES; i++) {
                                Donnees.instance().definirPlageFonctionnement(Donnees.Equipement.HeuresCreuses, i, String.format("plage_%d", i+1));
                            }

                            Donnees.instance().definirGMT(object.getString("index_gmt"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Horlogerie");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Système"));
                            Donnees.instance().definirActiviteIHM(object.getString("alive"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Système");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString( "Events"));

                            Donnees.instance().configurationEvents();

                            for (int i = 1; i < (object.length() + 1); i++) {
                                if (!object.getString("event" + i).isEmpty()) {
                                    Donnees.instance().ajouterEvent(object.getJSONObject("event" + i).getString("texte"), object.getJSONObject("event" + i).getInt("couleur"), object.getJSONObject("event" + i).getString("dateheure"));
                                } else {
                                    break;
                                }
                            }

                            Donnees.instance().supprimerEvents();
                        } catch (JSONException e) {
                            Log.d("ERROR", "Events");
                        }

                        if (!Donnees.instance().obtenirActiviteIHM()) {
                            if (!msgEtatSysteme) {
                                msgEtatSysteme = true;

                                if (isActivityResumed()) {
                                    Toast.makeText(this, "L'appareil est déconnecté du serveur", Toast.LENGTH_SHORT).show();
                                }
                                CustomNotification.instance().supprimer("L'appareil est connecté au serveur");
                                CustomNotification.instance().ajouter("L'appareil est déconnecté du serveur");
                            }
                        } else {
                            if (msgEtatSysteme) {
                                msgEtatSysteme = false;

                                if (isActivityResumed()) {
                                    Toast.makeText(this, "L'appareil est connecté au serveur", Toast.LENGTH_SHORT).show();
                                }
                                CustomNotification.instance().supprimer("L'appareil est déconnecté du serveur");
                                CustomNotification.instance().ajouter("L'appareil est connecté au serveur");
                            }
                        }

                        updatePages();
                    } catch (JSONException e) {
                        //e.printStackTrace();
                        if (isActivityResumed()) {
                            Toast.makeText(this, "Une erreur s'est produite lors de la communication avec le serveur", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    public void getBtTreatment(String result) {
        if (result.contains("WiFi")) {
            Donnees.instance().setWiFiState(Integer.parseInt(result.split(";")[1]));
            fragmentParams.updateBt();
        } else if (result.contains("Data")) {
            result = result.replaceAll("Data;", "");
            String buff = "";
            int pos = 0;

            for (int i = 0; i < result.length(); i++) {
                if (result.charAt(i) == ';') {
                    traitementRequete(buff, pos++);
                    buff = "";
                } else if (result.charAt(i) == '\0') {
                    break;
                } else {
                    buff += result.charAt(i);
                }
            }

            traitementRequete(buff, pos);

            MainActivity.instance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updatePages();
                }
            });
        } else if (result.contains("Calibration")) {
            final String finalResult = result;
            MainActivity.instance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fragmentParams.etalonnage(finalResult.contains("next") ? fragmentParams.typeModification : 100, finalResult.contains("canceled"));
                }
            });
        }
    }

    private void traitementRequete(String buff, int pos)
    {
        double valeur = 0;

        if (((46 <= pos) && (pos <= 48)) || ((61 <= pos) && (pos <= 63)))	// Durée inj min | Durée inj | Tps réponse
        {
            valeur = Double.parseDouble(buff.split(" ")[0]);
        }
        else if (pos == 70)	// Fréq surchloration
        {
            valeur = Double.parseDouble(buff.split(" ")[0]);
        }
        else if ((pos != 0) && (pos != 15) && (pos != 16) && (pos != 17) && (pos != 18) && (pos != 75) && (pos != 76) && (pos != 77))
        {
            valeur = Double.parseDouble(buff);
        }

        switch (pos)
        {
            case 0:
                if (!buff.equals(Donnees.instance().obtenirDateDerniereConnexion() + Donnees.instance().obtenirHeureDerniereConnexion()))
                {
                    Donnees.instance().definirActiviteIHM(String.valueOf(buff.charAt(0)) + String.valueOf(buff.charAt(1)) + "/" + String.valueOf(buff.charAt(2)) + String.valueOf(buff.charAt(3)) + "/" + String.valueOf(buff.charAt(4)) + String.valueOf(buff.charAt(5)) + String.valueOf(buff.charAt(6)) + String.valueOf(buff.charAt(7)) + "-" + String.valueOf(buff.charAt(8)) + String.valueOf(buff.charAt(9)) + ":" + String.valueOf(buff.charAt(10)) + String.valueOf(buff.charAt(11)));
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageLogin, "alive=" + Donnees.instance().obtenirDateDerniereConnexion() + "-" + Donnees.instance().obtenirHeureDerniereConnexion(), true);
                }
                break;
            case 4:
                if (valeur != Donnees.instance().obtenirVolumeBassin())
                {
                    Donnees.instance().definirVolumeBassin((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "volume=" + valeur, true);
                }
                break;
            case 5:
                if (valeur != Donnees.instance().obtenirTempoDemarrage())
                {
                    Donnees.instance().definirTempoDemarrage((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "temporisation_demarrage=" + valeur, true);
                }
                break;
            case 6:
                if (valeur != Donnees.instance().obtenirTypeRefoulement())
                {
                    Donnees.instance().definirTypeRefoulement((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "type_refoulement=" + (valeur == 1 ? "MULTIPLE" : "UNIQUE"), true);
                }
                break;
            case 7:
                if (valeur != Donnees.instance().obtenirTypeRegulation())
                {
                    Donnees.instance().definirTypeRegulation((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "type_regulation=" + (valeur == 1 ? "LINEAIRE" : "TOR"), true);
                }
                break;
            case 8:
                if (valeur != Donnees.instance().obtenirTempsSecuriteInjection())
                {
                    Donnees.instance().definirTempsSecuriteInjection((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "temps_securite_injection=" + valeur, true);
                }
                break;
            case 9:
                if (valeur != Donnees.instance().obtenirHystInjectionPh())
                {
                    Donnees.instance().definirHystInjectionPh(valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "hyst_injection_ph=" + valeur, true);
                }
                break;
            case 10:
                if (valeur != Donnees.instance().obtenirHystInjectionChloreOrp())
                {
                    Donnees.instance().definirHystInjectionChloreOrp((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "hyst_injection_orp=" + valeur, true);
                }
                break;
            case 11:
                if (valeur != (Donnees.instance().obtenirEtatRegulations() ? 1 : 0))
                {
                    Donnees.instance().definirEtatRegulations(valeur == 1);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageBassin, "etat_regulations=" + valeur, true);
                }
                break;
            case 12:
                if (valeur != (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PompeFiltration) ? 1 : 0))
                {
                    Donnees.instance().definirEquipementInstalle(Donnees.Equipement.PompeFiltration, valeur == 1);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "installe=" + valeur, true);
                }
                break;
            case 13:
                if ((valeur != Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PompeFiltration)))// && !Donnees.instance().obtenirEtatBypass())
                {
                    Donnees.instance().definirEtatEquipement(Donnees.Equipement.PompeFiltration, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "etat=" + valeur, true);
                }
                break;
            case 14:
                if (valeur != (Donnees.instance().obtenirEtatLectureCapteurs() ? 1 : 0))
                {
                    Donnees.instance().definirEtatLectureCapteurs(valeur == 1);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "lecture_capteurs=" + valeur, true);
                }
                break;
            case 15: case 16: case 17: case 18:
                if (!Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, pos - 15).getPlage().equals(buff))
                {
                    Donnees.instance().definirPlageFonctionnement(Donnees.Equipement.PompeFiltration, pos - 15, buff);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "plage_" + String.valueOf(pos - 15 + 1) + '=' + buff, true);
                }
                break;
            case 19:
                if (valeur != (Donnees.instance().obtenirEtatHorsGel() ? 1 : 0))
                {
                    Donnees.instance().definirEtatHorsGel(valeur == 1);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "etat_hors_gel=" + valeur, true);
                }
                break;
            case 20:
                if ((valeur != Donnees.instance().obtenirEnclHorsGel()) && Donnees.instance().obtenirEtatHorsGel())
                {
                    Donnees.instance().definirEnclHorsGel((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "enclenchement_hors_gel=" + valeur, true);
                }
                break;
            case 21:
                if (((valeur + Donnees.instance().obtenirEnclHorsGel() + 1) != Donnees.instance().obtenirArretHorsGel()) && Donnees.instance().obtenirEtatHorsGel())
                {
                    Donnees.instance().definirArretHorsGel((int) (valeur + Donnees.instance().obtenirEnclHorsGel() + 1));
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "arret_hors_gel=" + valeur, true);
                }
                break;
            case 22:
                if ((((valeur + 1) * 2) != Donnees.instance().obtenirFreqHorsGel()) && Donnees.instance().obtenirEtatHorsGel())
                {
                    Donnees.instance().definirFreqHorsGel((int) ((valeur + 1) * 2));
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PagePompeFiltration, "frequence_hors_gel=" + valeur, true);
                }
                break;
            case 23:
                if (valeur != (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Ozone) ? 1 : 0))
                {
                    Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Ozone, valeur == 1);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "installe=" + valeur, true);
                }
                break;
            case 24:
                if (valeur != Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Ozone))
                {
                    Donnees.instance().definirEtatEquipement(Donnees.Equipement.Ozone, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "etat=" + valeur, true);
                }
                break;
            case 25:
                if (valeur != Donnees.instance().obtenirTypeOzone())
                {
                    Donnees.instance().definirTypeOzone((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "type_ozone=" + valeur, true);
                }
                break;
            case 26:
                if (valeur != Donnees.instance().obtenirNombreVentilateursOzone())
                {
                    Donnees.instance().definirNombreVentilateursOzone((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "nombre_ventilateurs=" + valeur, true);
                }
                break;
            case 27:
                if (valeur != Donnees.instance().obtenirTempoOzone())
                {
                    Donnees.instance().definirTempoOzone((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "tempo_ozone=" + valeur, true);
                }
                break;
            case 28:
                if (valeur != Donnees.instance().obtenirErreurOzone())
                {
                    Donnees.instance().definirErreurOzone((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "erreurs_ozone=" + valeur, true);
                }
                break;
            case 29:
                if (valeur != Donnees.instance().obtenirVitesseFan1Ozone())
                {
                    Donnees.instance().definirVitesseFan1Ozone((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "vitesse_fan_1_ozone=" + valeur, true);
                }
                break;
            case 30:
                if (valeur != Donnees.instance().obtenirVitesseFan2Ozone())
                {
                    Donnees.instance().definirVitesseFan2Ozone((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "vitesse_fan_2_ozone=" + valeur, true);
                }
                break;
            case 31:
                if (valeur != Donnees.instance().obtenirCourantAlimOzone())
                {
                    Donnees.instance().definirCourantAlimOzone((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "courant_alim_ozone=" + valeur, true);
                }
                break;
            case 32:
                if (valeur != Donnees.instance().obtenirTensionAlimOzone())
                {
                    Donnees.instance().definirTensionAlimOzone(valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "tension_alim_ozone=" + valeur, true);
                }
                break;
            case 33:
                if (valeur != Donnees.instance().obtenirHauteTensionAlimOzone())
                {
                    Donnees.instance().definirHauteTensionAlimOzone((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageOzonateur, "haute_tension_alim_ozone=" + valeur, true);
                }
                break;
            case 34:
                if (valeur != Donnees.instance().obtenirPointConsignePh())
                {
                    Donnees.instance().definirPointConsignePh(valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhGlobal, "point_consigne=" + valeur, true);
                }
                break;
            case 35:
                if (valeur != Donnees.instance().obtenirHysteresisPhMoins())
                {
                    Donnees.instance().definirHysteresisPhMoins(valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhGlobal, "hysteresis_moins=" + valeur, true);
                }
                break;
            case 36:
                if (valeur != Donnees.instance().obtenirAlarmeSeuilBas(Donnees.Equipement.PhGlobal, null))
                {
                    Donnees.instance().definirAlarmeSeuilBas(Donnees.Equipement.PhGlobal, valeur, null);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhGlobal, "alarme_seuil_bas=" + valeur, true);
                }
                break;
            case 37:
                if (valeur != Donnees.instance().obtenirAlarmeSeuilHaut(Donnees.Equipement.PhGlobal, null))
                {
                    Donnees.instance().definirAlarmeSeuilHaut(Donnees.Equipement.PhGlobal, valeur, null);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhGlobal, "alarme_seuil_haut=" + valeur, true);
                }
                break;
            case 38:
                if (valeur != (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? 1 : 0))
                {
                    Donnees.instance().definirEquipementInstalle(Donnees.Equipement.PhMoins, valeur == 1);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "installe=" + valeur, true);
                }
                break;
            case 39:
                if (valeur != Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PhMoins))
                {
                    Donnees.instance().definirEtatEquipement(Donnees.Equipement.PhMoins, (valeur == Donnees.MARCHE) && !Donnees.instance().obtenirEtatRegulations() ? Donnees.ARRET : (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "etat=" + valeur, true);
                }
                break;
            case 40:
                if (valeur != Donnees.instance().obtenirDebitEquipement(Donnees.Equipement.PhMoins))
                {
                    Donnees.instance().definirDebitEquipement(Donnees.Equipement.PhMoins, valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "debit=" + valeur, true);
                }
                break;
            case 41:
                if (valeur != Donnees.instance().obtenirVolume(Donnees.Equipement.PhMoins))
                {
                    Donnees.instance().definirVolume(Donnees.Equipement.PhMoins, valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "date_consommation=" + Donnees.instance().obtenirDateConso(Donnees.Equipement.PhMoins) + "&volume=" + valeur, true);
                }
                break;
            case 42:
                if (valeur != Donnees.instance().obtenirVolume(Donnees.Equipement.PhMoins))
                {
                    Donnees.instance().definirVolumeRestant(Donnees.Equipement.PhMoins, valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "volume_restant=" + valeur, true);
                }
                break;
            case 43:
                if (valeur != (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.PhMoins) ? 1 : 0))
                {
                    Donnees.instance().definirTraitementEnCours(Donnees.Equipement.PhMoins, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "injection=" + valeur, true);
                }
                break;
            case 44:
                if (valeur != Donnees.instance().obtenirDureeCycle(Donnees.Equipement.PhMoins))
                {
                    Donnees.instance().definirDureeCycle(Donnees.Equipement.PhMoins, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "duree_cycle=" + valeur, true);
                }
                break;
            case 45:
                if (valeur != Donnees.instance().obtenirMultiplicateurDifference(Donnees.Equipement.PhMoins))
                {
                    Donnees.instance().definirMultiplicateurDifference(Donnees.Equipement.PhMoins, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "multiplicateur_diff=" + valeur, true);
                }
                break;
            case 46:
                if (valeur != Donnees.instance().obtenirDureeInjectionMinimum(Donnees.Equipement.PhMoins))
                {
                    Donnees.instance().definirDureeInjectionMinimum(Donnees.Equipement.PhMoins, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "duree_injection_minimum=" + valeur, true);
                }
                break;
            case 47:
                if (valeur != Donnees.instance().obtenirDureeInjection(Donnees.Equipement.PhMoins))
                {
                    Donnees.instance().definirDureeInjection(Donnees.Equipement.PhMoins, valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "duree_injection=" + valeur, true);
                }
                break;
            case 48:
                if (valeur != Donnees.instance().obtenirTempsReponse(Donnees.Equipement.PhMoins))
                {
                    Donnees.instance().definirTempsReponse(Donnees.Equipement.PhMoins, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "temps_reponse=" + valeur, true);
                }
                break;
            case 49:
                if (valeur != Donnees.instance().obtenirTempsInjectionJournalierMax(Donnees.Equipement.PhMoins))
                {
                    Donnees.instance().definirTempsInjectionJournalierMax(Donnees.Equipement.PhMoins, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "temps_injection_jour_max=" + valeur, true);
                }
                break;
            case 50:
                if (valeur != Donnees.instance().obtenirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhMoins))
                {
                    Donnees.instance().definirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhMoins, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurPhMoins, "temps_injection_jour_max_restant=" + valeur, true);
                }
                break;
            case 51:
                if (valeur != (Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? 1 : 0))
                {
                    Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Orp, valeur == 1);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "installe=" + valeur, true);
                }
                break;
            case 52:
                if (valeur != Donnees.instance().obtenirPointConsigneOrp())
                {
                    Donnees.instance().definirPointConsigneOrp((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "point_consigne_orp=" + valeur, true);
                }
                break;
            case 53:
                if (valeur != Donnees.instance().obtenirHysteresisOrp())
                {
                    Donnees.instance().definirHysteresisOrp((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "hysteresis_orp=" + valeur, true);
                }
                break;
            case 54:
                if (valeur != Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Orp))
                {
                    Donnees.instance().definirEtatEquipement(Donnees.Equipement.Orp, (valeur == Donnees.MARCHE) && !Donnees.instance().obtenirEtatRegulations() ? Donnees.ARRET : (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "etat=" + valeur, true);
                }
                break;
            case 55:
                if (valeur != Donnees.instance().obtenirDebitEquipement(Donnees.Equipement.Orp))
                {
                    Donnees.instance().definirDebitEquipement(Donnees.Equipement.Orp, valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "debit=" + valeur, true);
                }
                break;
            case 56:
                if (valeur != Donnees.instance().obtenirVolume(Donnees.Equipement.Orp))
                {
                    Donnees.instance().definirVolume(Donnees.Equipement.Orp, valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "date_consommation=" + Donnees.instance().obtenirDateConso(Donnees.Equipement.Orp) + "&volume=" + valeur, true);
                }
                break;
            case 57:
                if (valeur != Donnees.instance().obtenirVolumeRestant(Donnees.Equipement.Orp))
                {
                    Donnees.instance().definirVolumeRestant(Donnees.Equipement.Orp, valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "volume_restant=" + valeur, true);
                }
                break;
            case 58:
                if (valeur != (Donnees.instance().obtenirTraitementEnCours(Donnees.Equipement.Orp) ? 1 : 0))
                {
                    Donnees.instance().definirTraitementEnCours(Donnees.Equipement.Orp, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "injection=" + valeur, true);
                }
                break;
            case 59:
                if (valeur != Donnees.instance().obtenirDureeCycle(Donnees.Equipement.Orp))
                {
                    Donnees.instance().definirDureeCycle(Donnees.Equipement.Orp, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "duree_cycle=" + valeur, true);
                }
                break;
            case 60:
                if (valeur != Donnees.instance().obtenirMultiplicateurDifference(Donnees.Equipement.Orp))
                {
                    Donnees.instance().definirMultiplicateurDifference(Donnees.Equipement.Orp, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "multiplicateur_diff=" + valeur, true);
                }
                break;
            case 61:
                if (valeur != Donnees.instance().obtenirDureeInjectionMinimum(Donnees.Equipement.Orp))
                {
                    Donnees.instance().definirDureeInjectionMinimum(Donnees.Equipement.Orp, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "duree_injection_minimum=" + valeur, true);
                }
                break;
            case 62:
                if (valeur != Donnees.instance().obtenirDureeInjection(Donnees.Equipement.Orp))
                {
                    Donnees.instance().definirDureeInjection(Donnees.Equipement.Orp, valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "duree_injection=" + valeur, true);
                }
                break;
            case 63:
                if (valeur != Donnees.instance().obtenirTempsReponse(Donnees.Equipement.Orp))
                {
                    Donnees.instance().definirTempsReponse(Donnees.Equipement.Orp, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "temps_reponse=" + valeur, true);
                }
                break;
            case 64:
                if (valeur != Donnees.instance().obtenirTempsInjectionJournalierMax(Donnees.Equipement.Orp))
                {
                    Donnees.instance().definirTempsInjectionJournalierMax(Donnees.Equipement.Orp, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "temps_injection_jour_max=" + valeur, true);
                }
                break;
            case 65:
                if (valeur != Donnees.instance().obtenirTempsInjectionJournalierMaxRestant(Donnees.Equipement.Orp))
                {
                    Donnees.instance().definirTempsInjectionJournalierMaxRestant(Donnees.Equipement.Orp, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "temps_injection_jour_max_restant=" + valeur, true);
                }
                break;
            case 66:
                if (valeur != Donnees.instance().obtenirAlarmeSeuilBas(Donnees.Equipement.Orp, Donnees.Capteur.Redox))
                {
                    Donnees.instance().definirAlarmeSeuilBas(Donnees.Equipement.Orp, valeur, Donnees.Capteur.Redox);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "alarme_seuil_bas_orp=" + valeur, true);
                }
                break;
            case 67:
                if (valeur != Donnees.instance().obtenirAlarmeSeuilHaut(Donnees.Equipement.Orp, Donnees.Capteur.Redox))
                {
                    Donnees.instance().definirAlarmeSeuilHaut(Donnees.Equipement.Orp, valeur, Donnees.Capteur.Redox);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "alarme_seuil_haut_orp=" + valeur, true);
                }
                break;
            case 68:
                if (valeur != (Donnees.instance().obtenirEtat(Donnees.Equipement.Orp) ? 1 : 0))
                {
                    Donnees.instance().definirEtat(Donnees.Equipement.Orp, (int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "surchloration=" + valeur, true);
                }
                break;
            case 69:
                if (valeur != Donnees.instance().obtenirJourSurchloration())
                {
                    Donnees.instance().definirJourSurchloration((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "jour=" + valeur, true);
                }
                break;
            case 70:
                if (!buff.equals(Donnees.instance().obtenirFrequenceSurchloration()))
                {
                    Donnees.instance().definirFrequenceSurchloration(String.valueOf((int) valeur) + " " + buff.split(" ")[1]);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "frequence=" + Donnees.instance().obtenirFrequenceSurchloration(), true);
                }
                break;
            case 71:
                if (valeur != Donnees.instance().obtenirValeurSurchloration())
                {
                    Donnees.instance().definirValeurSurchloration((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "mv_ajoute=" + valeur, true);
                }
                break;
            case 72:
                if (valeur != Donnees.instance().obtenirProchaineSurchloration())
                {
                    Donnees.instance().definirProchaineSurchloration((int) valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageRegulateurORP, "prochaine_surchloration=" + valeur, true);
                }
                break;
            case 73:
                if (valeur != (Donnees.instance().obtenirDonneesEquipementsAuto() ? 1 : 0))
                {
                    Donnees.instance().definirDonneesEquipementsAuto(valeur == 1);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAutomatisation, "donnees_equipement=" + valeur, true);
                }
                break;
            case 74:
                if (valeur != (Donnees.instance().obtenirPlagesAuto() ? 1 : 0))
                {
                    Donnees.instance().definirPlagesAuto(valeur == 1);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAutomatisation, "plages_auto=" + valeur, true);
                }
                break;
            case 75:
                if (!Donnees.instance().obtenirPlageAuto().equals(buff))
                {
                    Donnees.instance().definirPlageAuto(buff);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAutomatisation, "plage_auto=" + buff, true);
                }
                break;
            case 77:
                if (!Donnees.instance().obtenirTempsFiltrationJour().equals(buff))
                {
                    Donnees.instance().definirTempsFiltrationJour(buff);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAutomatisation, "temps_filtration_jour=" + buff, true);
                }
                break;
            case 78:
                if (valeur != (Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.PhMoins) ? 1 : 0))
                {
                    Donnees.instance().definirAsservissementAuto(Donnees.Equipement.PhMoins, valeur == 1);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAutomatisation, "asservissement_ph_moins=" + valeur, true);
                }
                break;
            case 79:
                if (valeur != (Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.Orp) ? 1 : 0))
                {
                    Donnees.instance().definirAsservissementAuto(Donnees.Equipement.Orp, valeur == 1);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAutomatisation, "asservissement_orp=" + valeur, true);
                }
                break;
            case 80:
                if (valeur != (Donnees.instance().obtenirPointConsigneOrpAuto() ? 1 : 0))
                {
                    Donnees.instance().definirPointConsigneOrpAuto(valeur == 1);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageAutomatisation, "consigne_orp_auto=" + valeur, true);
                }
                break;
            case 81:
                //if (valeur != obtenirEtatCapteurNiveau())
                //{
                //    definirEtatCapteurNiveau(valeur);
                //}
                break;
            case 82: case 88: case 94:
                typeCapteur = pos == 82 ? Donnees.Capteur.TemperatureBassin : pos == 88 ? Donnees.Capteur.Ph : Donnees.Capteur.Redox;
                if (valeur != (Donnees.instance().obtenirCapteurInstalle(typeCapteur) ? 1 : 0))
                {
                    Donnees.instance().definirCapteurInstalle(typeCapteur, valeur == 1);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageCapteurs, "type=" + (typeCapteur == Donnees.Capteur.TemperatureBassin ? "Température bassin" : typeCapteur == Donnees.Capteur.Ph ? "pH" : "ORP") + "&installe=" + valeur, true);
                }
                break;
            case 83: case 89: case 95:
                if (valeur != (Donnees.instance().obtenirEtatCapteur(typeCapteur) ? 1 : 0))
                {
                    Donnees.instance().definirEtatCapteur(typeCapteur, valeur == 1 ? "OK" : "ERR");
                }
                break;
            case 84: case 90: case 96:
                if (valeur != Donnees.instance().obtenirValeurCapteur(typeCapteur))
                {
                    Donnees.instance().definirValeurCapteur(typeCapteur, valeur);
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.PageCapteurs, "type=" + (typeCapteur == Donnees.Capteur.TemperatureBassin ? "Température bassin" : typeCapteur == Donnees.Capteur.Ph ? "pH" : "ORP") + "&etat=" + (Donnees.instance().obtenirEtatCapteur(typeCapteur) ? "OK" : "ERR") + "&valeur=" + valeur, true);
                }
                break;
            default:
                break;
        }
    }
}
