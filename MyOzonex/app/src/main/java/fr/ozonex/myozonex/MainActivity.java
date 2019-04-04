package fr.ozonex.myozonex;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Intent serviceIntent;
    private HttpService httpService;

    private static MainActivity inst;
    public static MainActivity instance() {
        return inst;
    }

    private boolean premierDemarrage = false;
    private boolean activityResume = false;

    private HttpGetRequest request;

    Toolbar toolbar;
    Menu menu;

    private int page = 0;

    boolean msgEtatSysteme = false;
    TextView ledEtatSysteme;
    TextView texteEtatSysteme;

    private ActionBarDrawerToggle toggle;
    private FragmentConnexion fragmentConnexion = new FragmentConnexion();
    private FragmentDonnees fragmentDonnees = new FragmentDonnees();
    private FragmentSynoptique fragmentSynoptique = new FragmentSynoptique();
    private FragmentMenu fragmentMenu = new FragmentMenu();
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

    Bundle savedInstanceState;

    private Handler clignotementLED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inst = this;

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        menu = navigationView.getMenu();

        View decorView = getWindow().getDecorView();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            drawer.setFitsSystemWindows(true);
            toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(this);
            menu.findItem(R.id.nav_menu_layout).setVisible(false);

            View headerView = navigationView.getHeaderView(0);
            TextView idSysteme = (TextView) headerView.findViewById(R.id.id_systeme);
            idSysteme.setText("Système : " + Donnees.getPreferences(Donnees.ID_SYSTEME));
            ledEtatSysteme = headerView.findViewById(R.id.led_etat_systeme);
            texteEtatSysteme = headerView.findViewById(R.id.texte_etat_systeme);

            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            drawer.setFitsSystemWindows(false);
            getSupportActionBar().hide();

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            // Set the content to appear under the system bars so that the
                            // content doesn't resize when the system bars hide and show.
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            // Hide the nav bar and status bar
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        httpService = new HttpService();
        serviceIntent = new Intent(this, httpService.getClass());

        new Notification(this);

        this.savedInstanceState = savedInstanceState;

        Handler update = new Handler();
        update.postDelayed(refresh,0);

        clignotementLED = new Handler();
    }



    private final Runnable refresh = new Runnable() {
        public void run() {
            restoreMe(savedInstanceState);
        }
    };

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_actualiser) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_deconnexion_layout) {
            showNavigationViewButton(false);
            stopService(serviceIntent);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentConnexion)
                    .commit();
            toolbar.setTitle(getString(R.string.connexion));
        } else if (id == R.id.nav_donnees_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentDonnees)
                    .commit();
            toolbar.setTitle(getString(R.string.donnees));
        } else if (id == R.id.nav_synoptique_layout) {
            Donnees.instance().definirPageSource(Donnees.PAGE_SYNOPTIQUE);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentSynoptique)
                    .commit();
            toolbar.setTitle(getString(R.string.synoptique));
        } else if (id == R.id.nav_menu_layout) {
            Donnees.instance().definirPageSource(Donnees.PAGE_MENU);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentMenu)
                    .commit();
            toolbar.setTitle(getString(R.string.menu));
        } else if (id == R.id.nav_bassin_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentBassin)
                    .commit();
            toolbar.setTitle(getString(R.string.bassin));
        } else if (id == R.id.nav_eclairage_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentEclairage)
                    .commit();
            toolbar.setTitle(getString(R.string.eclairage));
        } else if (id == R.id.nav_pompe_filtration_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentPompeFiltration)
                    .commit();
            toolbar.setTitle(getString(R.string.pompe_filtration));
        } else if (id == R.id.nav_filtre_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentFiltre)
                    .commit();
            toolbar.setTitle(getString(R.string.filtre));
        } else if (id == R.id.nav_surpresseur_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentSurpresseur)
                    .commit();
            toolbar.setTitle(getString(R.string.surpresseur));
        } else if (id == R.id.nav_chauffage_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentChauffage)
                    .commit();
            toolbar.setTitle(getString(R.string.chauffage));
        } else if (id == R.id.nav_lampes_uv_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentLampesUV)
                    .commit();
            toolbar.setTitle(getString(R.string.lampes_uv));
        } else if (id == R.id.nav_ozone_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentOzone)
                    .commit();
            toolbar.setTitle(getString(R.string.ozonateur));
        } else if (id == R.id.nav_regulateur_ph_plus_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentRegulateurPhPlus)
                    .commit();
            toolbar.setTitle(getString(R.string.regulateur_ph_plus));
        } else if (id == R.id.nav_regulateur_ph_moins_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentRegulateurPhMoins)
                    .commit();
            toolbar.setTitle(getString(R.string.regulateur_ph_moins));
        } else if (id == R.id.nav_regulateur_orp_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentRegulateurORP)
                    .commit();
            toolbar.setTitle(getString(R.string.regulateur_orp));
        } else if (id == R.id.nav_algicide_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentAlgicide)
                    .commit();
            toolbar.setTitle(getString(R.string.algicide));
        } else if (id == R.id.nav_events_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentEvents)
                    .commit();
            toolbar.setTitle(getString(R.string.events));
        } else if (id == R.id.nav_automatisation_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , fragmentAutomatisation)
                    .commit();
            toolbar.setTitle(getString(R.string.automatisation));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        page = id;

        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        activityResume = false;

        if (clignotementLED != null) {
            clignotementLED.removeCallbacks(clignotement);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        activityResume = true;

        if ((fragmentConnexion.getView() == null) && !fragmentConnexion.isAdded() && !isMyServiceRunning(httpService.getClass())) {
            startService(serviceIntent);
        }

        if (clignotementLED != null) {
            clignotementLED.postDelayed(clignotement,0);
        }

        Notification.instance().supprimer();
    }

    @Override
    protected void onDestroy() {
        activityResume = false;

        stopService(serviceIntent);

        super.onDestroy();
    }

    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);

        saveInstanceState.putInt("page", page);
    }

    private void restoreMe(Bundle state) {
        if (state == null) {
            if (!Donnees.getPreferences(Donnees.ID_SYSTEME).isEmpty() && !Donnees.getPreferences(Donnees.MOTDEPASSE).isEmpty()) {
                premierDemarrage = true;
                connexion();
            } else {
                onNavigationItemSelected(menu.findItem(R.id.nav_deconnexion_layout));
            }
        } else {
            page = state.getInt("page");

            if (page == R.id.nav_menu_layout) {
                page = R.id.nav_donnees_layout;
            }

            onNavigationItemSelected(menu.findItem(page));
        }
    }

    public void connexion() {
        String data = "password=" + Donnees.getPreferences(Donnees.MOTDEPASSE);

        sendData(true,
                "Base de données",
                "Connexion à la base de données...",
                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Get),
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageLogin),
                data);

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

    public boolean isActivityResumed() {
        return activityResume;
    }

    public void setHtmlText(TextView textView, String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(text));
        }
    }

    public void sendData(boolean progressVisible, String progressTitle, String progressText, String... params) {
        request = new HttpGetRequest(this);
        request.setProgressDialogView(progressVisible, progressTitle, progressText);
        request.execute(params);
    }

    public void getTreatment(String result) {
        request = null;

        if (result == null) {
            Toast.makeText(this, "Un problème est survenu lors de la communication avec le serveur", Toast.LENGTH_SHORT).show();

            if (premierDemarrage) {
                onNavigationItemSelected(menu.findItem(R.id.nav_deconnexion_layout));
                premierDemarrage = false;
            }
        } else {
            if (!result.isEmpty()) {
                if (result.contains("LOG")) {
                    if (result.contains("OK")) {
                        premierDemarrage = false;
                        showNavigationViewButton(true);
                        onNavigationItemSelected(menu.findItem(R.id.nav_donnees_layout));

                        if (!isMyServiceRunning(httpService.getClass())) {
                            startService(serviceIntent);
                        }
                    } else {
                        Toast.makeText(this, "ID ou mot de passe erroné", Toast.LENGTH_SHORT).show();
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
                            Donnees.instance().definirPlagesAuto(object.getInt("plages_auto") > 0);
                            Donnees.instance().definirDebutPlageAuto(object.getString("debut_plage_auto"));
                            Donnees.instance().definirTempsFiltrationJour(object.getString("temps_filtration_jour"));
                            Donnees.instance().definirAsservissementAuto(Donnees.Equipement.PhPlus, object.getInt("asservissement_ph_plus") > 0);
                            Donnees.instance().definirAsservissementAuto(Donnees.Equipement.PhMoins, object.getInt("asservissement_ph_moins") > 0);
                            Donnees.instance().definirAsservissementAuto(Donnees.Equipement.Orp, object.getInt("asservissement_orp") > 0);
                            Donnees.instance().definirConsigneOrpAuto(object.getInt("consigne_orp_auto") > 0);
                        } catch (JSONException e) {
                            Log.d("ERROR", "Automatisation");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Bassin"));
                            Donnees.instance().definirVolumeBassin(object.getInt("volume"));
                            Donnees.instance().definirTypeRefoulement(object.getString("type_refoulement"));
                            Donnees.instance().definirTypeAsservissement(object.getString("type_regulation"));
                            Donnees.instance().definirTempsSecuriteInjection(object.getInt("temps_securite_injection"));
                            Donnees.instance().definirHysteresisInjectionPh(object.getDouble("hyst_injection_ph"));
                            Donnees.instance().definirHysteresisInjectionORP(object.getInt("hyst_injection_orp"));
                            Donnees.instance().definirHysteresisInjectionAmpero(object.getDouble("hyst_injection_ampero"));
                            Donnees.instance().definirEtatRegulations(object.getInt("etat_regulations") > 0);
                        } catch (JSONException e) {
                            Log.d("ERROR", "Bassin");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Capteurs"));
                            Donnees.instance().definirPresence(Donnees.Capteur.TemperatureBassin, object.getJSONObject("Température bassin").getInt("installe"));
                            Donnees.instance().definirEtat(Donnees.Capteur.TemperatureBassin, object.getJSONObject("Température bassin").getString("etat"));
                            Donnees.instance().definirValeur(Donnees.Capteur.TemperatureBassin, object.getJSONObject("Température bassin").getDouble("valeur"));

                            Donnees.instance().definirPresence(Donnees.Capteur.CapteurInterne, object.getJSONObject("Température interne").getInt("installe"));
                            Donnees.instance().definirEtat(Donnees.Capteur.TemperatureInterne, object.getJSONObject("Température interne").getString("etat"));
                            Donnees.instance().definirValeur(Donnees.Capteur.TemperatureInterne, object.getJSONObject("Température interne").getDouble("valeur"));
                            Donnees.instance().definirEtat(Donnees.Capteur.HumiditeInterne, object.getJSONObject("Humidité interne").getString("etat"));
                            Donnees.instance().definirValeur(Donnees.Capteur.HumiditeInterne, object.getJSONObject("Humidité interne").getDouble("valeur"));
                            Donnees.instance().definirEtat(Donnees.Capteur.PressionAtmospheriqueInterne, object.getJSONObject("Pression atmosphérique interne").getString("etat"));
                            Donnees.instance().definirValeur(Donnees.Capteur.PressionAtmospheriqueInterne, object.getJSONObject("Pression atmosphérique interne").getDouble("valeur"));

                            Donnees.instance().definirPresence(Donnees.Capteur.CapteurExterne, object.getJSONObject("Température externe").getInt("installe"));
                            Donnees.instance().definirEtat(Donnees.Capteur.TemperatureExterne, object.getJSONObject("Température externe").getString("etat"));
                            Donnees.instance().definirValeur(Donnees.Capteur.TemperatureExterne, object.getJSONObject("Température externe").getDouble("valeur"));
                            Donnees.instance().definirEtat(Donnees.Capteur.HumiditeExterne, object.getJSONObject("Humidité externe").getString("etat"));
                            Donnees.instance().definirValeur(Donnees.Capteur.HumiditeExterne, object.getJSONObject("Humidité externe").getDouble("valeur"));
                            Donnees.instance().definirEtat(Donnees.Capteur.PressionAtmospheriqueExterne, object.getJSONObject("Pression atmosphérique externe").getString("etat"));
                            Donnees.instance().definirValeur(Donnees.Capteur.PressionAtmospheriqueExterne, object.getJSONObject("Pression atmosphérique externe").getDouble("valeur"));

                            Donnees.instance().definirPresence(Donnees.Capteur.Ph, object.getJSONObject("pH").getInt("installe"));
                            Donnees.instance().definirEtat(Donnees.Capteur.Ph, object.getJSONObject("pH").getString("etat"));
                            Donnees.instance().definirValeur(Donnees.Capteur.Ph, object.getJSONObject("pH").getDouble("valeur"));

                            Donnees.instance().definirPresence(Donnees.Capteur.Redox, object.getJSONObject("ORP").getInt("installe"));
                            Donnees.instance().definirEtat(Donnees.Capteur.Redox, object.getJSONObject("ORP").getString("etat"));
                            Donnees.instance().definirValeur(Donnees.Capteur.Redox, object.getJSONObject("ORP").getDouble("valeur"));

                            Donnees.instance().definirPresence(Donnees.Capteur.Pression, object.getJSONObject("Pression").getInt("installe"));
                            Donnees.instance().definirEtat(Donnees.Capteur.Pression, object.getJSONObject("Pression").getString("etat"));
                            Donnees.instance().definirValeur(Donnees.Capteur.Pression, object.getJSONObject("Pression").getDouble("valeur"));

                            Donnees.instance().definirPresence(Donnees.Capteur.Ampero, object.getJSONObject("Ampéro").getInt("installe"));
                            Donnees.instance().definirEtat(Donnees.Capteur.Ampero, object.getJSONObject("Ampéro").getString("etat"));
                            Donnees.instance().definirValeur(Donnees.Capteur.Ampero, object.getJSONObject("Ampéro").getDouble("valeur"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Capteurs");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Eclairage"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Eclairage, object.getInt("installe") > 0);
                            Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Eclairage, object.getInt("etat"));
                            Donnees.instance().definirPlage(Donnees.Equipement.Eclairage, 0, object.getString("plage_1"));
                            Donnees.instance().definirPlage(Donnees.Equipement.Eclairage, 1, object.getString("plage_2"));
                            Donnees.instance().definirPlage(Donnees.Equipement.Eclairage, 2, object.getString("plage_3"));
                            Donnees.instance().definirPlage(Donnees.Equipement.Eclairage, 3, object.getString("plage_4"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Eclairage");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Pompe filtration"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.PompeFiltration, object.getInt("installe") > 0);
                            Donnees.instance().definirModeFonctionnement(Donnees.Equipement.PompeFiltration, object.getInt("etat"));
                            Donnees.instance().definirEtatLectureCapteurs(object.getInt("lecture_capteurs") > 0);
                            Donnees.instance().definirDateDebutConso(Donnees.Equipement.PompeFiltration, object.getString("date_consommation"));
                            Donnees.instance().definirConsoHP(Donnees.Equipement.PompeFiltration, object.getDouble("consommation_hp"));
                            Donnees.instance().definirConsoHC(Donnees.Equipement.PompeFiltration, object.getDouble("consommation_hc"));
                            Donnees.instance().definirPlage(Donnees.Equipement.PompeFiltration, 0, object.getString("plage_1"));
                            Donnees.instance().definirPlage(Donnees.Equipement.PompeFiltration, 1, object.getString("plage_2"));
                            Donnees.instance().definirPlage(Donnees.Equipement.PompeFiltration, 2, object.getString("plage_3"));
                            Donnees.instance().definirPlage(Donnees.Equipement.PompeFiltration, 3, object.getString("plage_4"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Pompe filtration");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Filtre"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Filtre, object.getInt("installe") > 0);
                            Donnees.instance().definirDateDernierLavage(object.getString("date_dernier_lavage"));
                            Donnees.instance().definirPressionApresLavage(object.getDouble("pression_apres_lavage"));
                            Donnees.instance().definirPressionProchainLavage(object.getDouble("pression_prochain_lavage"));
                            Donnees.instance().definirSeuilSecuriteSurpression(object.getDouble("seuil_securite_surpression"));
                            Donnees.instance().definirSeuilHautPression(object.getDouble("seuil_haut_pression"));
                            Donnees.instance().definirSeuilBasPression(object.getDouble("seuil_bas_pression"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Filtre");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Surpresseur"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Surpresseur, object.getInt("installe") > 0);
                            Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Surpresseur, object.getInt("etat"));
                            Donnees.instance().definirDateDebutConso(Donnees.Equipement.Surpresseur, object.getString("date_consommation"));
                            Donnees.instance().definirConsoHP(Donnees.Equipement.Surpresseur, object.getDouble("consommation_hp"));
                            Donnees.instance().definirConsoHC(Donnees.Equipement.Surpresseur, object.getDouble("consommation_hc"));
                            Donnees.instance().definirPlage(Donnees.Equipement.Surpresseur, 0, object.getString("plage_1"));
                            Donnees.instance().definirPlage(Donnees.Equipement.Surpresseur, 1, object.getString("plage_2"));
                            Donnees.instance().definirPlage(Donnees.Equipement.Surpresseur, 2, object.getString("plage_3"));
                            Donnees.instance().definirPlage(Donnees.Equipement.Surpresseur, 3, object.getString("plage_4"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Surpresseur");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Chauffage"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Chauffage, object.getInt("installe") > 0);
                            Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Chauffage, object.getInt("etat"));
                            Donnees.instance().definirDateDebutConso(Donnees.Equipement.Chauffage, object.getString("date_consommation"));
                            Donnees.instance().definirConsoHP(Donnees.Equipement.Chauffage, object.getDouble("consommation_hp"));
                            Donnees.instance().definirConsoHC(Donnees.Equipement.Chauffage, object.getDouble("consommation_hc"));
                            Donnees.instance().definirControlePompeFiltration(object.getInt("gestion_temperature"));
                            Donnees.instance().definirTemperatureArret(object.getInt("temperature_arret"));
                            Donnees.instance().definirTemperatureEnclenchement(object.getInt("temperature_encl"));
                            Donnees.instance().definirTemperatureConsigne(object.getInt("temperature_consigne"));
                            Donnees.instance().definirPlage(Donnees.Equipement.Chauffage, 0, object.getString("plage_1"));
                            Donnees.instance().definirPlage(Donnees.Equipement.Chauffage, 1, object.getString("plage_2"));
                            Donnees.instance().definirPlage(Donnees.Equipement.Chauffage, 2, object.getString("plage_3"));
                            Donnees.instance().definirPlage(Donnees.Equipement.Chauffage, 3, object.getString("plage_4"));
                            Donnees.instance().definirTypeChauffage(object.getInt("type_chauffage"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Chauffage");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Lampes UV"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.LampesUV, object.getInt("installe") > 0);
                            Donnees.instance().definirModeFonctionnement(Donnees.Equipement.LampesUV, object.getInt("etat"));
                            Donnees.instance().definirDateDebutConso(Donnees.Equipement.LampesUV, object.getString("date_consommation"));
                            Donnees.instance().definirConsoHP(Donnees.Equipement.LampesUV, object.getDouble("consommation_hp"));
                            Donnees.instance().definirConsoHC(Donnees.Equipement.LampesUV, object.getDouble("consommation_hc"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Lampes UV");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Ozonateur"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Ozone, object.getInt("installe") > 0);
                            Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Ozone, object.getInt("etat"));
                            Donnees.instance().definirDateDebutConso(Donnees.Equipement.Ozone, object.getString("date_consommation"));
                            Donnees.instance().definirConsoHP(Donnees.Equipement.Ozone, object.getDouble("consommation_hp"));
                            Donnees.instance().definirConsoHC(Donnees.Equipement.Ozone, object.getDouble("consommation_hc"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Ozonateur");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Electrolyseur"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Electrolyseur, object.getInt("installe") > 0);
                            Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Electrolyseur, object.getInt("etat"));
                            Donnees.instance().definirDateDebutConso(Donnees.Equipement.Electrolyseur, object.getString("date_consommation"));
                            Donnees.instance().definirConsoHP(Donnees.Equipement.Electrolyseur, object.getDouble("consommation_hp"));
                            Donnees.instance().definirConsoHC(Donnees.Equipement.Electrolyseur, object.getDouble("consommation_hc"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Electrolyseur");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Régulateur pH"));
                            Donnees.instance().definirConsignePh(object.getDouble("point_consigne"));
                            Donnees.instance().definirHysteresisPhPlus(object.getDouble("hysteresis_plus"));
                            Donnees.instance().definirHysteresisPhMoins(object.getDouble("hysteresis_moins"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Régulateur pH");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Régulateur pH-"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.PhMoins, object.getInt("installe") > 0);
                            Donnees.instance().definirModeFonctionnement(Donnees.Equipement.PhMoins, object.getInt("etat"));
                            Donnees.instance().definirDateDebutConso(Donnees.Equipement.PhMoins, object.getString("date_consommation"));
                            Donnees.instance().definirConsoVolume(Donnees.Equipement.PhMoins, object.getDouble("volume"));
                            Donnees.instance().definirConsoVolumeRestant(Donnees.Equipement.PhMoins, object.getDouble("volume_restant"));
                            Donnees.instance().definirConsoJour(Donnees.Equipement.PhMoins, object.getDouble("consommation_jour"));
                            Donnees.instance().definirConsoSemaine(Donnees.Equipement.PhMoins, object.getDouble("consommation_semaine"));
                            Donnees.instance().definirConsoMois(Donnees.Equipement.PhMoins, object.getDouble("consommation_mois"));
                            Donnees.instance().definirTraitementEnCours(Donnees.Equipement.PhMoins, object.getInt("injection"));
                            Donnees.instance().definirDureeCycle(Donnees.Equipement.PhMoins, object.getInt("duree_cycle"));
                            Donnees.instance().definirMultiplicateurDifference(Donnees.Equipement.PhMoins, object.getInt("multiplicateur_diff"));
                            Donnees.instance().definirDureeInjection(Donnees.Equipement.PhMoins, object.getString("duree_injection"));
                            Donnees.instance().definirTempsReponse(Donnees.Equipement.PhMoins, object.getString("temps_reponse"));
                            Donnees.instance().definirTempsInjectionJournalierMax(Donnees.Equipement.PhMoins, object.getInt("temps_injection_jour_max"));
                            Donnees.instance().definirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhMoins, object.getInt("temps_injection_jour_max_restant"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Régulateur pH-");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Régulateur pH+"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.PhPlus, object.getInt("installe") > 0);
                            Donnees.instance().definirModeFonctionnement(Donnees.Equipement.PhPlus, object.getInt("etat"));
                            Donnees.instance().definirDateDebutConso(Donnees.Equipement.PhPlus, object.getString("date_consommation"));
                            Donnees.instance().definirConsoVolume(Donnees.Equipement.PhPlus, object.getDouble("volume"));
                            Donnees.instance().definirConsoVolumeRestant(Donnees.Equipement.PhPlus, object.getDouble("volume_restant"));
                            Donnees.instance().definirConsoJour(Donnees.Equipement.PhPlus, object.getDouble("consommation_jour"));
                            Donnees.instance().definirConsoSemaine(Donnees.Equipement.PhPlus, object.getDouble("consommation_semaine"));
                            Donnees.instance().definirConsoMois(Donnees.Equipement.PhPlus, object.getDouble("consommation_mois"));
                            Donnees.instance().definirTraitementEnCours(Donnees.Equipement.PhPlus, object.getInt("injection"));
                            Donnees.instance().definirDureeCycle(Donnees.Equipement.PhPlus, object.getInt("duree_cycle"));
                            Donnees.instance().definirMultiplicateurDifference(Donnees.Equipement.PhPlus, object.getInt("multiplicateur_diff"));
                            Donnees.instance().definirDureeInjection(Donnees.Equipement.PhPlus, object.getString("duree_injection"));
                            Donnees.instance().definirTempsReponse(Donnees.Equipement.PhPlus, object.getString("temps_reponse"));
                            Donnees.instance().definirTempsInjectionJournalierMax(Donnees.Equipement.PhPlus, object.getInt("temps_injection_jour_max"));
                            Donnees.instance().definirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhPlus, object.getInt("temps_injection_jour_max_restant"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Régulateur pH+");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Régulateur ORP"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Orp, object.getInt("installe") > 0);
                            Donnees.instance().definirConsigneOrp(object.getInt("point_consigne_orp"));
                            Donnees.instance().definirHysteresisOrp(object.getInt("hysteresis_orp"));
                            Donnees.instance().definirConsigneAmpero(object.getDouble("point_consigne_ampero"));
                            Donnees.instance().definirHysteresisAmpero(object.getDouble("hysteresis_ampero"));
                            Donnees.instance().definirChloreLibreActif(object.getDouble("chlore_libre_actif"));
                            Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Orp, object.getInt("etat"));
                            Donnees.instance().definirDateDebutConso(Donnees.Equipement.Orp, object.getString("date_consommation"));
                            Donnees.instance().definirConsoVolume(Donnees.Equipement.Orp, object.getDouble("volume"));
                            Donnees.instance().definirConsoVolumeRestant(Donnees.Equipement.Orp, object.getDouble("volume_restant"));
                            Donnees.instance().definirConsoJour(Donnees.Equipement.Orp, object.getDouble("consommation_jour"));
                            Donnees.instance().definirConsoSemaine(Donnees.Equipement.Orp, object.getDouble("consommation_semaine"));
                            Donnees.instance().definirConsoMois(Donnees.Equipement.Orp, object.getDouble("consommation_mois"));
                            Donnees.instance().definirTraitementEnCours(Donnees.Equipement.Orp, object.getInt("injection"));
                            Donnees.instance().definirDureeCycle(Donnees.Equipement.Orp, object.getInt("duree_cycle"));
                            Donnees.instance().definirMultiplicateurDifference(Donnees.Equipement.Orp, object.getInt("multiplicateur_diff"));
                            Donnees.instance().definirDureeInjection(Donnees.Equipement.Orp, object.getString("duree_injection"));
                            Donnees.instance().definirTempsReponse(Donnees.Equipement.Orp, object.getString("temps_reponse"));
                            Donnees.instance().definirTempsInjectionJournalierMax(Donnees.Equipement.Orp, object.getInt("temps_injection_jour_max"));
                            Donnees.instance().definirTempsInjectionJournalierMaxRestant(Donnees.Equipement.Orp, object.getInt("temps_injection_jour_max_restant"));
                            Donnees.instance().definirEtat(Donnees.Equipement.Orp, object.getInt("surchloration"));
                            Donnees.instance().definirDonneesSurchloration("Fréquence : <b>" + object.getString("frequence") + "</b><br /><b>" + object.getInt("mv_ajoute") + " mV </b>ajouté<br /><font color=\"#00FF00\"><i>Prochaine dans <b>" + object.getInt("prochaine_surchloration") + " jour(s)</b></i></font>");
                        } catch (JSONException e) {
                            Log.d("ERROR", "Régulateur ORP");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Algicide"));
                            Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Algicide, object.getInt("installe") > 0);
                            Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Algicide, object.getInt("etat"));
                            Donnees.instance().definirDateDebutConso(Donnees.Equipement.Algicide, object.getString("date_consommation"));
                            Donnees.instance().definirConsoVolume(Donnees.Equipement.Algicide, object.getDouble("volume"));
                            Donnees.instance().definirConsoVolumeRestant(Donnees.Equipement.Algicide, object.getDouble("volume_restant"));
                            Donnees.instance().definirTraitementEnCours(Donnees.Equipement.Algicide, object.getInt("injection"));
                            Donnees.instance().definirEtat(Donnees.Equipement.Algicide, object.getInt("active"));
                            Donnees.instance().definirDonneesAlgicide("Fréquence : <b>" + object.getString("frequence") + "</b><br />Pendant <b>" + object.getInt("pendant") + " minute(s)</b><br /><font color=\"#00FF00\"><i>Prochaine dans " + object.getInt("prochain") + " jour(s)</b></i></font>" + ((object.getInt("prochain") == 0) ? "<br /><font color=\"" + (object.getInt("temps_restant") > 0 ? "#00FF00" : "orange") + "\"><i>Temps restant : <b>" + object.getInt("temps_restant") + " minute(s)</b></i></font>" : ""));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Algicide");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Horlogerie"));
                            Donnees.instance().definirPlage(Donnees.Equipement.HeuresCreuses, 0, object.getString("plage_1"));
                            Donnees.instance().definirPlage(Donnees.Equipement.HeuresCreuses, 1, object.getString("plage_2"));
                            Donnees.instance().definirPlage(Donnees.Equipement.HeuresCreuses, 2, object.getString("plage_3"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Horlogerie");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString("Système"));
                            Donnees.instance().definirActiviteIHM(object.getString("alive"));
                            Donnees.instance().definirBackground(object.getInt("background"));
                        } catch (JSONException e) {
                            Log.d("ERROR", "Système");
                        }

                        try {
                            object = new JSONObject(jsonObject.getString( "Events"));
                            Donnees.instance().supprimerEvents();
                            for (int i = 1; i < (object.length() + 1); i++) {
                                if (!object.getString("event" + i).isEmpty()) {
                                    Donnees.instance().ajouterEvent(object.getJSONObject("event" + i).getString("texte"), object.getJSONObject("event" + i).getInt("couleur"), object.getJSONObject("event" + i).getString("dateheure"));

                                    if (object.getJSONObject("event" + i).getInt("lu") == 0) {
                                        Notification.instance().ajouter("Nouvel evenement", object.getJSONObject("event" + i).getString("texte") + " à " + object.getJSONObject("event" + i).getString("dateheure"));
                                        MainActivity.instance().sendData(false,
                                                "",
                                                "",
                                                HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Update),
                                                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageEvents),
                                                "texte=" + object.getJSONObject("event" + i).getString("texte") + "&dateheure=" + object.getJSONObject("event" + i).getString("dateheure") + "&lu=1");
                                    }
                                } else {
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            Log.d("ERROR", "Events");
                        }

                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                            ledEtatSysteme.setActivated(Donnees.instance().obtenirActiviteIHM());
                            texteEtatSysteme.setText(Donnees.instance().obtenirActiviteIHM() ? "Etat : connecté" : "Etat : déconnecté");
                        }

                        if (!Donnees.instance().obtenirActiviteIHM()) {
                            if (!msgEtatSysteme) {
                                msgEtatSysteme = true;
                                Toast.makeText(this, "Le système s'est déconnecté du serveur", Toast.LENGTH_SHORT).show();
                                Notification.instance().ajouter("Déconnexion", "Le système s'est déconnecté du serveur");
                            }
                        } else {
                            if (msgEtatSysteme) {
                                msgEtatSysteme = false;
                                Toast.makeText(this, "Le système s'est connecté au serveur", Toast.LENGTH_SHORT).show();
                                Notification.instance().ajouter("Connexion", "Le système s'est connecté au serveur");
                            }
                        }

                        menu.findItem(R.id.nav_eclairage_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Eclairage));
                        menu.findItem(R.id.nav_pompe_filtration_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PompeFiltration));
                        menu.findItem(R.id.nav_filtre_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Filtre));
                        menu.findItem(R.id.nav_surpresseur_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur));
                        menu.findItem(R.id.nav_chauffage_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage));
                        menu.findItem(R.id.nav_lampes_uv_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.LampesUV));
                        menu.findItem(R.id.nav_ozone_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Ozone));
                        menu.findItem(R.id.nav_electrolyseur_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Electrolyseur));
                        menu.findItem(R.id.nav_regulateur_ph_moins_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins));
                        menu.findItem(R.id.nav_regulateur_ph_plus_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus));
                        menu.findItem(R.id.nav_regulateur_orp_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp));
                        menu.findItem(R.id.nav_algicide_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Algicide));

                        updatePages();
                    } catch (JSONException e) {
                        //e.printStackTrace();
                        Toast.makeText(this, "Une erreur s'est produite lors de la communication avec le serveur", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void updatePages() {
        fragmentDonnees.update();
        fragmentSynoptique.update();
        fragmentMenu.update();
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
        fragmentEvents.update();
        fragmentAutomatisation.update();
    }
}
