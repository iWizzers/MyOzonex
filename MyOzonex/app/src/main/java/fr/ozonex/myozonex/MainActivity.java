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
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

    private boolean activityResume = false;

    private HttpGetRequest request;

    Toolbar toolbar;
    Menu menu;

    private int page = 0;

    private ActionBarDrawerToggle toggle;
    private FragmentConnexion fragmentConnexion = new FragmentConnexion();
    private FragmentDonnees fragmentDonnees = new FragmentDonnees();
    private FragmentSynoptique fragmentSynoptique = new FragmentSynoptique();
    private FragmentMenu fragmentMenu = new FragmentMenu();
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

    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inst = this;

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        menu = navigationView.getMenu();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(this);
            menu.findItem(R.id.nav_synoptique_layout).setVisible(false);
            menu.findItem(R.id.nav_menu_layout).setVisible(false);

            View headerView = navigationView.getHeaderView(0);
            TextView idSysteme = (TextView) headerView.findViewById(R.id.id_systeme);
            idSysteme.setText("Système : " + Donnees.getPreferences(Donnees.ID_SYSTEME));
        } else {
            // Fullscreen
            getSupportActionBar().hide();
        }

        httpService = new HttpService();
        serviceIntent = new Intent(this, httpService.getClass());

        new Notification(this);

        this.savedInstanceState = savedInstanceState;

        Handler update = new Handler();
        update.postDelayed(refresh,0);
    }

    private final Runnable refresh = new Runnable() {
        public void run() {
            restoreMe(savedInstanceState);
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
            Donnees.setPreferences(Donnees.ID_SYSTEME, "");
            Donnees.setPreferences(Donnees.MOTDEPASSE, "");
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
    }

    @Override
    public void onResume() {
        super.onResume();
        activityResume = true;

        if (!isMyServiceRunning(httpService.getClass())) {
            startService(serviceIntent);
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
                connexion();
            } else {
                onNavigationItemSelected(menu.findItem(R.id.nav_deconnexion_layout));
            }
        } else {
            page = state.getInt("page");

            if ((page == R.id.nav_synoptique_layout)
                    || (page == R.id.nav_menu_layout)) {
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
                HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.Login),
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
        } else {
            if (!result.isEmpty()) {
                if (result.contains("LOG")) {
                    if (result.contains("OK")) {
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

                        object = new JSONObject(jsonObject.getString("Bassin"));
                        Donnees.instance().definirVolumeBassin(object.getInt("volume"));
                        Donnees.instance().definirTypeAsservissement(object.getString("regulation_type"));

                        object = new JSONObject(jsonObject.getString("Capteurs"));
                        Donnees.instance().definirPresence(Donnees.Capteur.TemperatureBassin, object.getJSONObject("Température bassin").getInt("is_installed"));
                        Donnees.instance().definirEtat(Donnees.Capteur.TemperatureBassin, object.getJSONObject("Température bassin").getString("state"));
                        Donnees.instance().definirValeur(Donnees.Capteur.TemperatureBassin, object.getJSONObject("Température bassin").getDouble("value"));

                        Donnees.instance().definirPresence(Donnees.Capteur.TemperatureLocal, object.getJSONObject("Température externe").getInt("is_installed"));
                        Donnees.instance().definirEtat(Donnees.Capteur.TemperatureLocal, object.getJSONObject("Température externe").getString("state"));
                        Donnees.instance().definirValeur(Donnees.Capteur.TemperatureLocal, object.getJSONObject("Température externe").getDouble("value"));

                        Donnees.instance().definirPresence(Donnees.Capteur.Ph, object.getJSONObject("pH").getInt("is_installed"));
                        Donnees.instance().definirEtat(Donnees.Capteur.Ph, object.getJSONObject("pH").getString("state"));
                        Donnees.instance().definirValeur(Donnees.Capteur.Ph, object.getJSONObject("pH").getDouble("value"));

                        Donnees.instance().definirPresence(Donnees.Capteur.Redox, object.getJSONObject("ORP").getInt("is_installed"));
                        Donnees.instance().definirEtat(Donnees.Capteur.Redox, object.getJSONObject("ORP").getString("state"));
                        Donnees.instance().definirValeur(Donnees.Capteur.Redox, object.getJSONObject("ORP").getDouble("value"));

                        Donnees.instance().definirPresence(Donnees.Capteur.Pression, object.getJSONObject("Pression").getInt("is_installed"));
                        Donnees.instance().definirEtat(Donnees.Capteur.Pression, object.getJSONObject("Pression").getString("state"));
                        Donnees.instance().definirValeur(Donnees.Capteur.Pression, object.getJSONObject("Pression").getDouble("value"));

                        Donnees.instance().definirPresence(Donnees.Capteur.Ampero, object.getJSONObject("Ampéro").getInt("is_installed"));
                        Donnees.instance().definirEtat(Donnees.Capteur.Ampero, object.getJSONObject("Ampéro").getString("state"));
                        Donnees.instance().definirValeur(Donnees.Capteur.Ampero, object.getJSONObject("Ampéro").getDouble("value"));

                        object = new JSONObject(jsonObject.getString("Pompe filtration"));
                        Donnees.instance().definirEquipementInstalle(Donnees.Equipement.PompeFiltration, object.getInt("is_installed") > 0);
                        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.PompeFiltration, object.getInt("state"));
                        Donnees.instance().definirDateDebutConso(Donnees.Equipement.PompeFiltration, object.getString("date_consumption"));
                        Donnees.instance().definirConsoHP(Donnees.Equipement.PompeFiltration, object.getDouble("peak_consumption"));
                        Donnees.instance().definirConsoHC(Donnees.Equipement.PompeFiltration, object.getDouble("off_peak_consumption"));

                        object = new JSONObject(jsonObject.getString("Filtre"));
                        Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Filtre, object.getInt("is_installed") > 0);
                        Donnees.instance().definirDateDernierLavage(object.getString("date_last_wash"));
                        Donnees.instance().definirPressionApresLavage(object.getDouble("pressure_after_wash"));
                        Donnees.instance().definirPressionProchainLavage(object.getDouble("next_pressure"));
                        Donnees.instance().definirSeuilBasPression(object.getDouble("low_pressure_thresh"));
                        Donnees.instance().definirSeuilHautPression(object.getDouble("high_pressure_thresh"));
                        Donnees.instance().definirSeuilSecuriteSurpression(object.getDouble("security_pressure_thresh"));

                        object = new JSONObject(jsonObject.getString("Surpresseur"));
                        Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Surpresseur, object.getInt("is_installed") > 0);
                        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Surpresseur, object.getInt("state"));
                        Donnees.instance().definirDateDebutConso(Donnees.Equipement.Surpresseur, object.getString("date_consumption"));
                        Donnees.instance().definirConsoHP(Donnees.Equipement.Surpresseur, object.getDouble("peak_consumption"));
                        Donnees.instance().definirConsoHC(Donnees.Equipement.Surpresseur, object.getDouble("off_peak_consumption"));

                        object = new JSONObject(jsonObject.getString("Chauffage"));
                        Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Chauffage, object.getInt("is_installed") > 0);
                        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Chauffage, object.getInt("state"));
                        Donnees.instance().definirDateDebutConso(Donnees.Equipement.Chauffage, object.getString("date_consumption"));
                        Donnees.instance().definirConsoHP(Donnees.Equipement.Chauffage, object.getDouble("peak_consumption"));
                        Donnees.instance().definirConsoHC(Donnees.Equipement.Chauffage, object.getDouble("off_peak_consumption"));
                        Donnees.instance().definirControlePompeFiltration(object.getInt("temperature_control"));
                        Donnees.instance().definirTemperatureArret(object.getInt("stop_temperature"));
                        Donnees.instance().definirTemperatureEnclenchement(object.getInt("start_temperature"));
                        Donnees.instance().definirTemperatureConsigne(object.getInt("setpoint_temperature"));

                        object = new JSONObject(jsonObject.getString("Lampes UV"));
                        Donnees.instance().definirEquipementInstalle(Donnees.Equipement.LampesUV, object.getInt("is_installed") > 0);
                        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.LampesUV, object.getInt("state"));
                        Donnees.instance().definirDateDebutConso(Donnees.Equipement.LampesUV, object.getString("date_consumption"));
                        Donnees.instance().definirConsoHP(Donnees.Equipement.LampesUV, object.getDouble("peak_consumption"));
                        Donnees.instance().definirConsoHC(Donnees.Equipement.LampesUV, object.getDouble("off_peak_consumption"));

                        object = new JSONObject(jsonObject.getString("Ozone"));
                        Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Ozone, object.getInt("is_installed") > 0);
                        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Ozone, object.getInt("state"));
                        Donnees.instance().definirDateDebutConso(Donnees.Equipement.Ozone, object.getString("date_consumption"));
                        Donnees.instance().definirConsoHP(Donnees.Equipement.Ozone, object.getDouble("peak_consumption"));
                        Donnees.instance().definirConsoHC(Donnees.Equipement.Ozone, object.getDouble("off_peak_consumption"));

                        //ELECTROLYSEUR

                        object = new JSONObject(jsonObject.getString("Régulateur pH-"));
                        Donnees.instance().definirEquipementInstalle(Donnees.Equipement.PhMoins, object.getInt("is_installed") > 0);
                        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.PhMoins, object.getInt("state"));
                        Donnees.instance().definirDateDebutConso(Donnees.Equipement.PhMoins, object.getString("date_consumption"));
                        Donnees.instance().definirConsoVolume(Donnees.Equipement.PhMoins, object.getDouble("volume"));
                        Donnees.instance().definirConsoVolumeRestant(Donnees.Equipement.PhMoins, object.getDouble("remaining_volume"));
                        Donnees.instance().definirTraitementEnCours(Donnees.Equipement.PhMoins, object.getInt("ongoing_treatment"));
                        Donnees.instance().definirReglageAuto(Donnees.Equipement.PhMoins, object.getInt("auto_adjust"));
                        Donnees.instance().definirDureeCycle(Donnees.Equipement.PhMoins, object.getInt("cycle_time"));
                        Donnees.instance().definirMultiplicateurDifference(Donnees.Equipement.PhMoins, object.getInt("multiplier"));
                        Donnees.instance().definirDureeInjection(Donnees.Equipement.PhMoins, object.getString("injection_time"));
                        Donnees.instance().definirTempsReponse(Donnees.Equipement.PhMoins, object.getString("response_time"));
                        Donnees.instance().definirTempsInjectionJournalierMax(Donnees.Equipement.PhMoins, object.getInt("max_daily_injection_time"));
                        Donnees.instance().definirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhMoins, object.getInt("max_daily_injection_time_remaining"));

                        object = new JSONObject(jsonObject.getString("Régulateur pH+"));
                        Donnees.instance().definirEquipementInstalle(Donnees.Equipement.PhPlus, object.getInt("is_installed") > 0);
                        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.PhPlus, object.getInt("state"));
                        Donnees.instance().definirDateDebutConso(Donnees.Equipement.PhPlus, object.getString("date_consumption"));
                        Donnees.instance().definirConsoVolume(Donnees.Equipement.PhPlus, object.getDouble("volume"));
                        Donnees.instance().definirConsoVolumeRestant(Donnees.Equipement.PhPlus, object.getDouble("remaining_volume"));
                        Donnees.instance().definirTraitementEnCours(Donnees.Equipement.PhPlus, object.getInt("ongoing_treatment"));
                        Donnees.instance().definirReglageAuto(Donnees.Equipement.PhPlus, object.getInt("auto_adjust"));
                        Donnees.instance().definirDureeCycle(Donnees.Equipement.PhPlus, object.getInt("cycle_time"));
                        Donnees.instance().definirMultiplicateurDifference(Donnees.Equipement.PhPlus, object.getInt("multiplier"));
                        Donnees.instance().definirDureeInjection(Donnees.Equipement.PhPlus, object.getString("injection_time"));
                        Donnees.instance().definirTempsReponse(Donnees.Equipement.PhPlus, object.getString("response_time"));
                        Donnees.instance().definirTempsInjectionJournalierMax(Donnees.Equipement.PhPlus, object.getInt("max_daily_injection_time"));
                        Donnees.instance().definirTempsInjectionJournalierMaxRestant(Donnees.Equipement.PhPlus, object.getInt("max_daily_injection_time_remaining"));

                        object = new JSONObject(jsonObject.getString("Régulateur ORP"));
                        Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Orp, object.getInt("is_installed") > 0);
                        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Orp, object.getInt("state"));
                        Donnees.instance().definirDateDebutConso(Donnees.Equipement.Orp, object.getString("date_consumption"));
                        Donnees.instance().definirConsoVolume(Donnees.Equipement.Orp, object.getDouble("volume"));
                        Donnees.instance().definirConsoVolumeRestant(Donnees.Equipement.Orp, object.getDouble("remaining_volume"));
                        Donnees.instance().definirTraitementEnCours(Donnees.Equipement.Orp, object.getInt("ongoing_treatment"));
                        Donnees.instance().definirReglageAuto(Donnees.Equipement.Orp, object.getInt("auto_adjust"));
                        Donnees.instance().definirDureeCycle(Donnees.Equipement.Orp, object.getInt("cycle_time"));
                        Donnees.instance().definirMultiplicateurDifference(Donnees.Equipement.Orp, object.getInt("multiplier"));
                        Donnees.instance().definirDureeInjection(Donnees.Equipement.Orp, object.getString("injection_time"));
                        Donnees.instance().definirTempsReponse(Donnees.Equipement.Orp, object.getString("response_time"));
                        Donnees.instance().definirTempsInjectionJournalierMax(Donnees.Equipement.Orp, object.getInt("max_daily_injection_time"));
                        Donnees.instance().definirTempsInjectionJournalierMaxRestant(Donnees.Equipement.Orp, object.getInt("max_daily_injection_time_remaining"));
                        Donnees.instance().definirEtat(Donnees.Equipement.Orp, object.getInt("superchlorination"));
                        Donnees.instance().definirDonneesSurchloration("Fréquence : <b>" + object.getString("frequency") + "</b><br /><b>" + object.getInt("mv_added") + " mV </b>ajouté<br /><font color=\"#00FF00\"><i>Prochaine dans <b>" + object.getInt("next_superchlorination") + " jour(s)</b></i></font>");

                        object = new JSONObject(jsonObject.getString("Algicide"));
                        Donnees.instance().definirEquipementInstalle(Donnees.Equipement.Algicide, object.getInt("is_installed") > 0);
                        Donnees.instance().definirModeFonctionnement(Donnees.Equipement.Algicide, object.getInt("state"));
                        Donnees.instance().definirDateDebutConso(Donnees.Equipement.Algicide, object.getString("date_consumption"));
                        Donnees.instance().definirConsoVolume(Donnees.Equipement.Algicide, object.getDouble("volume"));
                        Donnees.instance().definirConsoVolumeRestant(Donnees.Equipement.Algicide, object.getDouble("remaining_volume"));
                        Donnees.instance().definirTraitementEnCours(Donnees.Equipement.Algicide, object.getInt("ongoing_treatment"));
                        Donnees.instance().definirEtat(Donnees.Equipement.Algicide, object.getInt("enable"));
                        Donnees.instance().definirDonneesAlgicide("Fréquence : " + object.getString("frequency") + "\nPendant " + object.getInt("during") + " minute(s)\nProchaine dans " + object.getInt("next") + " jour(s)" + ((object.getInt("next") == 0) ? "\nTemps restant : " + object.getInt("remaining_time") + " minute(s)" : ""));

                        menu.findItem(R.id.nav_pompe_filtration_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PompeFiltration));
                        menu.findItem(R.id.nav_filtre_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Filtre));
                        menu.findItem(R.id.nav_surpresseur_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Surpresseur));
                        menu.findItem(R.id.nav_chauffage_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage));
                        menu.findItem(R.id.nav_lampes_uv_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.LampesUV));
                        menu.findItem(R.id.nav_ozone_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Ozone));
                        menu.findItem(R.id.nav_electrolyseur_layout).setVisible(false);
                        menu.findItem(R.id.nav_regulateur_ph_moins_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins));
                        menu.findItem(R.id.nav_regulateur_ph_plus_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhPlus));
                        menu.findItem(R.id.nav_regulateur_orp_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp));
                        menu.findItem(R.id.nav_algicide_layout).setVisible(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Algicide));

                        updatePages();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void updatePages() {
        fragmentDonnees.update();
        fragmentSynoptique.update();
        fragmentMenu.update();
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
    }
}
