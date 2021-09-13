package fr.ozonex.myozonex;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class HttpService extends Service {
    private HttpServiceThread httpServiceThread = null;
    private Timer timer;
    private TimerTask timerTask;

    public HttpService() {
        if (httpServiceThread == null) {
            httpServiceThread = new HttpServiceThread();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            startMyOwnForeground();
        } else {
            startForeground(0, new Notification());
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "fr.ozonex.myozonex.permanence";
        String channelName = "Background Service";
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        channel.setLightColor(Color.CYAN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(channel);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopTimerTask();

        if (httpServiceThread != null) {
            httpServiceThread.isAlive = false;
            httpServiceThread = null;
        }

        if (!Donnees.getPreferences(Donnees.ID_SYSTEME).isEmpty()) {
            Intent broadcastIntent = new Intent(this, HttpRestarterBroadcastReceiver.class);
            sendBroadcast(broadcastIntent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every X seconds
        timer.schedule(timerTask, 0, Global.TEMPO_RAFRAICHISSEMENT);
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                if (!Donnees.getPreferences(Donnees.ID_SYSTEME).isEmpty() && Donnees.instance().getTimerState()) {
                    if (BluetoothLe.instance().connected != BluetoothLe.Connected.True) {
                        if (!Donnees.instance().contientRequeteHttp(StructureHttp.RequestHTTP.Get, StructureHttp.PageHTTP.PageData, "")) {
                            Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Get, StructureHttp.PageHTTP.PageData, "", false);
                        }
                    } else {
                        if (!Donnees.instance().contientRequeteBt("Data?")) {
                            Donnees.instance().ajouterRequeteBt("Data?", false);
                        }
                    }
                }
            }
        };
    }

    /**
     * not needed
     */
    public void stopTimerTask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class HttpServiceThread extends Thread {
        public boolean isAlive = false;
        public boolean envoiDonnees = false;
        public boolean envoiDonneesBt = false;
        private static final String BDD = "http://www.myozonex.fr";              // Serveur
        private static final String REQUEST_METHOD = "GET";
        private ProgressDialog progressDialog;
        private boolean progressDialogVisible = false;

        public HttpServiceThread() {
            isAlive = true;
            envoiDonnees = false;
            envoiDonneesBt = false;
            progressDialogVisible = false;
            start();
        }

        public void run() {
            while (isAlive) {
                if (Donnees.instance().contientRequeteHttp()) {
                    final StructureHttp structureHttp = Donnees.instance().obtenirStructureHttp();

                    if (structureHttp.getPage() != StructureHttp.PageHTTP.PageData) {
                        if (!progressDialogVisible) {
                            progressDialogVisible = true;

                            MainActivity.instance().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog = ProgressDialog.show(MainActivity.instance(), getString(R.string.serveur_titre), getString(structureHttp.getRequest() == StructureHttp.RequestHTTP.Update ? R.string.serveur_application_modifications : R.string.serveur_connexion_bdd), true);
                                }
                            });
                        } else {
                            MainActivity.instance().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.setTitle(getString(R.string.serveur_titre));
                                    progressDialog.setMessage(getString(structureHttp.getRequest() == StructureHttp.RequestHTTP.Update ? R.string.serveur_application_modifications : R.string.serveur_connexion_bdd));
                                }
                            });
                        }
                    }

                    if (!envoiDonnees) {
                        envoiDonnees = (structureHttp.getRequest() == StructureHttp.RequestHTTP.Update) && (structureHttp.getPage() != StructureHttp.PageHTTP.Changes);
                    }

                    if (!envoiDonneesBt) {
                        envoiDonneesBt = (structureHttp.getRequest() == StructureHttp.RequestHTTP.Update) && (structureHttp.getPage() != StructureHttp.PageHTTP.Changes) && !structureHttp.isFromBt();
                    }

                    String result;
                    String inputLine;
                    String data = BDD + "/release/" + structureHttp.getRequestString() + '_' + structureHttp.getPageString() + "?id_systeme=" + Donnees.getPreferences(Donnees.ID_SYSTEME) + (!structureHttp.getData().isEmpty() ? '&' + structureHttp.getData() : "");

                    try {
                        //Log.d("URL", data);
                        // Create a URL object holding our url
                        URL url = new URL(data);

                        // Create a connection
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        // Set methods and timeouts
                        connection.setRequestMethod(REQUEST_METHOD);
                        connection.setReadTimeout(Global.TEMPO_RAFRAICHISSEMENT / 2);
                        connection.setConnectTimeout(Global.TEMPO_RAFRAICHISSEMENT / 2);

                        // Connect to our url
                        connection.connect();

                        // Create a new InputStreamReader
                        InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());

                        // Create a new buffered reader and String Builder
                        BufferedReader reader = new BufferedReader(streamReader);
                        StringBuilder stringBuilder = new StringBuilder();

                        // Check if the line we are reading is not null
                        while ((inputLine = reader.readLine()) != null) {
                            stringBuilder.append(inputLine);
                        }

                        // Close our InputStream and Buffered reader
                        reader.close();
                        streamReader.close();

                        // Set our result equal to our stringBuilder
                        result = stringBuilder.toString();
                    } catch (IOException e) {
                        //e.printStackTrace();
                        result = null;
                    }

                    if ((structureHttp.getRequest() == StructureHttp.RequestHTTP.Update) && (structureHttp.getPage() == StructureHttp.PageHTTP.Changes) && envoiDonneesBt) {
                        envoiDonneesBt = false;
                        Donnees.instance().ajouterRequeteBt("Data", true);
                    } else if (structureHttp.getRequest() == StructureHttp.RequestHTTP.Get) {
                        final String finalResult = result;
                        MainActivity.instance().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.instance().getTreatment(finalResult);
                            }
                        });
                    }

                    Donnees.instance().supprimerRequeteHttp();
                } else if (envoiDonnees) {
                    envoiDonnees = false;
                    Donnees.instance().ajouterRequeteHttp(StructureHttp.RequestHTTP.Update, StructureHttp.PageHTTP.Changes, "changes_from_app=1", false);
                } else if (Donnees.instance().contientRequeteBt()) {
                    final StructureBt structureBt = Donnees.instance().obtenirStructureBt();

                    if (structureBt.getProgressDialogVisible()) {
                        if (!progressDialogVisible) {
                            progressDialogVisible = true;

                            MainActivity.instance().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog = ProgressDialog.show(MainActivity.instance(), getString(R.string.bluetooth_titre), getString(structureBt.getData().charAt(structureBt.getData().length() - 1) == '?' ? R.string.bluetooth_get_data : R.string.serveur_application_modifications), true);
                                }
                            });
                        } else {
                            MainActivity.instance().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.setTitle(getString(R.string.bluetooth_titre));
                                    progressDialog.setMessage(getString(structureBt.getData().charAt(structureBt.getData().length() - 1) == '?' ? R.string.bluetooth_get_data : R.string.serveur_application_modifications));
                                }
                            });
                        }
                    }

                    BluetoothLe.instance().send(structureBt.getData());

                    while (!BluetoothLe.instance().readTrameComplete) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    BluetoothLe.instance().readTrameComplete = false;
                    Donnees.instance().supprimerRequeteBt();
                } else if (progressDialogVisible) {
                    progressDialogVisible = false;

                    MainActivity.instance().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                    });
                }
            }
        }
    }
}
