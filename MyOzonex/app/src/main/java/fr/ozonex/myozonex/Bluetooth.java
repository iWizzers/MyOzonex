package fr.ozonex.myozonex;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

public class Bluetooth {
    private static Bluetooth inst = new Bluetooth();
    public static Bluetooth instance() {
        return inst;
    }

    public static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter myBluetoothAdapter = null;
    private BluetoothClient bluetoothClient = null;
    private BluetoothDevice bluetoothDevice = null;

    private Handler update = new Handler();

    private ProgressDialog progressDialog;

    public void init() {
        if (MainActivity.instance().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            MainActivity.instance().requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (myBluetoothAdapter != null) {
            if (!isOn()) {
                Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                MainActivity.instance().startActivityForResult(turnOnIntent, Bluetooth.REQUEST_ENABLE_BT);
            } else {
                connect();
            }
        }
    }

    public boolean isOn() {
        boolean ret = false;

        if (myBluetoothAdapter != null) {
            ret = myBluetoothAdapter.isEnabled();
        }

        return ret;
    }

    public boolean isConnected() {
        boolean ret = false;

        if (bluetoothClient != null) {
            ret = bluetoothClient.isAlive;
        }

        return ret;
    }

    public boolean isPaired() {
        boolean btFound = false;

        // get paired devices
        Set<BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();

        // put it's one to the adapter
        for (BluetoothDevice device : pairedDevices) {
            if (device.getName().equals("MyOzonex Mini")) {
                btFound = true;
                bluetoothDevice = device;
                break;
            }
        }

        return btFound;
    }

    public boolean isUsed() {
        boolean ret = true;

        if (bluetoothClient != null) {
            ret = bluetoothClient.isUsed;
        }

        return ret;
    }

    public void connect() {
        new Thread() {
            boolean btFound = false;

            @Override
            public void run() {
                if (myBluetoothAdapter != null) {
                    MainActivity.instance().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog = ProgressDialog.show(MainActivity.instance(), "Veuillez patienter", "Connexion Bluetooth en cours...", true);
                        }
                    });

                    if (isPaired()) {
                        btFound = true;
                        connect(bluetoothDevice);
                    }

                    if (!btFound) {
                        find();
                    }
                } else {
                    init();
                }
            }
        }.start();
    }

    public void connect(BluetoothDevice device) {
        bluetoothClient = new BluetoothClient(device);
    }

    public void disconnect() {
        bluetoothClient.close();
    }

    final BroadcastReceiver bReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // add the name and the MAC address of the object to the arrayAdapter
                if (device.getName().equals("MyOzonex Mini")) {
                    update.removeCallbacks(cancel);
                    bluetoothDevice = device;

                    myBluetoothAdapter.cancelDiscovery();
                    unregisterReceiver();

                    pairDevice(device);
                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
                    unregisterReceiver();
                    connect(bluetoothDevice);
                } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDING){
                    update.postDelayed(cancel,0);
                }
            }
        }
    };

    private void find() {
        if (myBluetoothAdapter.isDiscovering()) {
            update.postDelayed(cancel,0);
        } else {
            myBluetoothAdapter.startDiscovery();
            update.postDelayed(cancel,10000);
            MainActivity.instance().registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }
    }

    private void pairDevice(BluetoothDevice device) {
        try {
            MainActivity.instance().registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));

            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Runnable cancel = new Runnable() {
        public void run() {
            MainActivity.instance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if ((progressDialog != null) && (progressDialog.isShowing())) {
                        progressDialog.dismiss();
                    }
                }
            });

            myBluetoothAdapter.cancelDiscovery();
            unregisterReceiver();
            MainActivity.instance().cancelBt();

            bluetoothDevice = null;
        }
    };

    public void unregisterReceiver() {
        MainActivity.instance().unregisterReceiver(bReceiver);
    }

    public void send(String data) {
        Log.d("TEST", data);
        if (bluetoothClient != null) {
            if (data.equals("Data")) {
                data += new SimpleDateFormat(";uddMMyyyyHHmmss;").format(Calendar.getInstance().getTime());
                data += "0;0;0;";
                data += String.valueOf(Donnees.instance().obtenirVolumeBassin()) + ';';
                data += String.valueOf(Donnees.instance().obtenirTempoDemarrage()) + ';';
                data += String.valueOf(Donnees.instance().obtenirTypeRefoulement()) + ';';
                data += String.valueOf(Donnees.instance().obtenirTypeRegulation()) + ';';
                data += String.valueOf(Donnees.instance().obtenirTempsSecuriteInjection()) + ';';
                data += String.valueOf(Donnees.instance().obtenirHystInjectionPh()) + ';';
                data += String.valueOf(Donnees.instance().obtenirHystInjectionChloreOrp()) + ';';
                data += String.valueOf(Donnees.instance().obtenirEtatRegulations() ? 1 : 0) + ';';

                data += String.valueOf(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PompeFiltration) ? 1 : 0) + ';';
                data += String.valueOf(Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PompeFiltration)) + ';';
                data += String.valueOf(Donnees.instance().obtenirEtatLectureCapteurs() ? 1 : 0) + ';';
                for (int i = 0; i < Global.MAX_PLAGES_EQUIPEMENTS; i++) {
                    data += Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, i).getPlage() + ';';
                }
                data += String.valueOf(Donnees.instance().obtenirEtatHorsGel() ? 1 : 0) + ';';
                data += String.valueOf(Donnees.instance().obtenirEnclHorsGel()) + ';';
                data += String.valueOf(Donnees.instance().obtenirArretHorsGel()) + ';';
                data += String.valueOf(Donnees.instance().obtenirFreqHorsGel()) + ';';

                data += String.valueOf(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Ozone) ? 1 : 0) + ';';
                data += String.valueOf(Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Ozone)) + ';';
                data += String.valueOf(Donnees.instance().obtenirTypeOzone()) + ';';
                data += String.valueOf(Donnees.instance().obtenirNombreVentilateursOzone()) + ';';
                data += String.valueOf(Donnees.instance().obtenirTempoOzone()) + ';';
                data += String.valueOf(Donnees.instance().obtenirErreurOzone()) + ';';
                data += String.valueOf(Donnees.instance().obtenirVitesseFan1Ozone()) + ';';
                data += String.valueOf(Donnees.instance().obtenirVitesseFan2Ozone()) + ';';
                data += String.valueOf(Donnees.instance().obtenirCourantAlimOzone()) + ';';
                data += String.valueOf(Donnees.instance().obtenirTensionAlimOzone()) + ';';
                data += String.valueOf(Donnees.instance().obtenirHauteTensionAlimOzone()) + ';';

                data += String.valueOf(Donnees.instance().obtenirPointConsignePh()) + ';';
                data += String.valueOf(Donnees.instance().obtenirHysteresisPhMoins()) + ';';
                data += String.valueOf(Donnees.instance().obtenirAlarmeSeuilBas(Donnees.Equipement.PhGlobal, null)) + ';';
                data += String.valueOf(Donnees.instance().obtenirAlarmeSeuilHaut(Donnees.Equipement.PhGlobal, null)) + ';';

                data += String.valueOf(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? 1 : 0) + ';';
                data += String.valueOf(Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PhMoins)) + ';';
                data += String.valueOf(Donnees.instance().obtenirDebitEquipement(Donnees.Equipement.PhMoins)) + ';';
                data += String.valueOf(Donnees.instance().obtenirVolume(Donnees.Equipement.PhMoins)) + ';';
                data += "0;";
                data += "0;";
                data += String.valueOf(Donnees.instance().obtenirDureeCycle(Donnees.Equipement.PhMoins)) + ';';
                data += String.valueOf(Donnees.instance().obtenirMultiplicateurDifference(Donnees.Equipement.PhMoins)) + ';';
                data += String.valueOf(Donnees.instance().obtenirDureeInjectionMinimum(Donnees.Equipement.PhMoins)) + ';';
                data += String.valueOf(Donnees.instance().obtenirDureeInjection(Donnees.Equipement.PhMoins)) + ';';
                data += String.valueOf(Donnees.instance().obtenirTempsReponse(Donnees.Equipement.PhMoins)) + ';';
                data += String.valueOf(Donnees.instance().obtenirTempsInjectionJournalierMax(Donnees.Equipement.PhMoins)) + ';';
                data += "0;";

                data += String.valueOf(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? 1 : 0) + ';';
                data += String.valueOf(Donnees.instance().obtenirPointConsigneOrp()) + ';';
                data += String.valueOf(Donnees.instance().obtenirHysteresisOrp()) + ';';
                data += String.valueOf(Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Orp)) + ';';
                data += String.valueOf(Donnees.instance().obtenirDebitEquipement(Donnees.Equipement.Orp)) + ';';
                data += String.valueOf(Donnees.instance().obtenirVolume(Donnees.Equipement.Orp)) + ';';
                data += "0;";
                data += "0;";
                data += String.valueOf(Donnees.instance().obtenirDureeCycle(Donnees.Equipement.Orp)) + ';';
                data += String.valueOf(Donnees.instance().obtenirMultiplicateurDifference(Donnees.Equipement.Orp)) + ';';
                data += String.valueOf(Donnees.instance().obtenirDureeInjectionMinimum(Donnees.Equipement.Orp)) + ';';
                data += String.valueOf(Donnees.instance().obtenirDureeInjection(Donnees.Equipement.Orp)) + ';';
                data += String.valueOf(Donnees.instance().obtenirTempsReponse(Donnees.Equipement.Orp)) + ';';
                data += String.valueOf(Donnees.instance().obtenirTempsInjectionJournalierMax(Donnees.Equipement.Orp)) + ';';
                data += "0;";
                data += String.valueOf(Donnees.instance().obtenirAlarmeSeuilBas(Donnees.Equipement.Orp, Donnees.Capteur.Redox)) + ';';
                data += String.valueOf(Donnees.instance().obtenirAlarmeSeuilHaut(Donnees.Equipement.Orp, Donnees.Capteur.Redox)) + ';';
                data += String.valueOf(Donnees.instance().obtenirEtat(Donnees.Equipement.Orp) ? 1 : 0) + ';';
                data += String.valueOf(Donnees.instance().obtenirJourSurchloration()) + ';';
                data += String.valueOf(Donnees.instance().obtenirFrequenceSurchloration()) + ';';
                data += String.valueOf(Donnees.instance().obtenirValeurSurchloration()) + ';';
                data += String.valueOf(Donnees.instance().obtenirProchaineSurchloration()) + ';';

                data += String.valueOf(Donnees.instance().obtenirDonneesEquipementsAuto() ? 1 : 0) + ';';
                data += String.valueOf(Donnees.instance().obtenirModifPlagesAuto() ? 1 : 0) + ';';
                data += String.valueOf(Donnees.instance().obtenirPlagesAuto() ? 1 : 0) + ';';
                data += String.valueOf(Donnees.instance().obtenirDebutPlageAuto()) + ';';
                data += String.valueOf(Donnees.instance().obtenirTempsFiltrationJour()) + ';';
                data += String.valueOf(Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.PhMoins) ? 1 : 0) + ';';
                data += String.valueOf(Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.Orp) ? 1 : 0) + ';';
                data += String.valueOf(Donnees.instance().obtenirPointConsigneOrpAuto() ? 1 : 0) + ';';
                data += "0;";

                data += String.valueOf(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.TemperatureBassin) ? 1 : 0) + ';';
                data += "0;";
                data += String.valueOf(Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.TemperatureBassin)) + ';';
                data += "0;";
                data += String.valueOf(Donnees.instance().obtenirEtatEtalonnageCapteur(Donnees.Capteur.TemperatureBassin) ? 1 : 0) + ';';
                data += String.valueOf(Donnees.instance().obtenirValeurEtalonnageCapteur(Donnees.Capteur.TemperatureBassin)) + ';';

                data += String.valueOf(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) ? 1 : 0) + ';';
                data += "0;";
                data += String.valueOf(Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Ph)) + ';';
                data += "0;";
                data += String.valueOf(Donnees.instance().obtenirEtatEtalonnageCapteur(Donnees.Capteur.Ph) ? 1 : 0) + ';';
                data += String.valueOf(Donnees.instance().obtenirValeurEtalonnageCapteur(Donnees.Capteur.Ph)) + ';';

                data += String.valueOf(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) ? 1 : 0) + ';';
                data += "0;";
                data += String.valueOf(Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Redox)) + ';';
                data += "0;";
                data += String.valueOf(Donnees.instance().obtenirEtatEtalonnageCapteur(Donnees.Capteur.Redox) ? 1 : 0) + ';';
                data += String.valueOf(Donnees.instance().obtenirValeurEtalonnageCapteur(Donnees.Capteur.Redox)) + ';';

                Donnees.instance().definirEtalonnageCapteur(Donnees.Capteur.TemperatureBassin, false, 0);
                Donnees.instance().definirEtalonnageCapteur(Donnees.Capteur.Ph, false, 0);
                Donnees.instance().definirEtalonnageCapteur(Donnees.Capteur.Redox, false, 0);
            }

            bluetoothClient.send(data);
        }
    }

    private class BluetoothClient extends Thread {
        private BluetoothSocket mmSocket;
        private InputStream inputStream;
        private OutputStream outputStream;
        private boolean isAlive = false;
        private boolean isUsed = false;
        String trame = "";

        Handler clearTrameHandler = new Handler(Looper.getMainLooper());
        Runnable clearTrame = new Runnable() {
            public void run() {
                trame = "";
            }
        };

        public BluetoothClient(BluetoothDevice device) {
            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                mmSocket = device.createRfcommSocketToServiceRecord(device.getUuids()[0].getUuid());
                mmSocket.connect();

                inputStream = mmSocket.getInputStream();
                outputStream = mmSocket.getOutputStream();

                isAlive = true;
                isUsed = false;
                start();

                Donnees.instance().ajouterRequeteBt("Time;" + new SimpleDateFormat("uddMMyyyyHHmmss").format(Calendar.getInstance().getTime()), true);
                Donnees.instance().ajouterRequeteBt("WiFi?", true);
                Donnees.instance().ajouterRequeteBt("Data?", true);

                MainActivity.instance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.instance(), "Connecté en Bluetooth", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                //e.printStackTrace();
                MainActivity.instance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.instance().afficherAlertDialog("Connexion Bluetooth", "Appareil hors de portée.", "OK");
                    }
                });

                MainActivity.instance().cancelBt();
            }

            MainActivity.instance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if ((progressDialog != null) && (progressDialog.isShowing())) {
                        progressDialog.dismiss();
                    }
                }
            });
        }

        public void run() {
            try {
                while (isAlive) {
                    if (inputStream.available() > 0) {
                        isUsed = true;
                        clearTrameHandler.removeCallbacks(clearTrame);
                        clearTrameHandler.postDelayed(clearTrame,1000);
                        trame += String.format("%c", inputStream.read());

                        if ((trame.charAt(0) == '<') && (trame.charAt(trame.length() - 1) == '>')) {
                            clearTrameHandler.removeCallbacks(clearTrame);
                            MainActivity.instance().getBtTreatment(trame.replaceAll("<", "").replaceAll(">", ""));

                            trame = "";
                            isUsed = false;
                        }
                    } else {
                        Thread.sleep(100);
                    }
                }
            } catch (Exception exception) {
                //Log.e("DEBUG", "Cannot read data", exception);
                close();
            }
        }

        public void send(String data) {
            isUsed = true;

            try {
                outputStream.write(data.getBytes());
                outputStream.flush();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                //Log.e("DEBUG", "Cannot write message", e);
            }

            isUsed = false;
        }

        // Closes the client socket and causes the thread to finish.
        public void close() {
            try {
                mmSocket.close();
                isAlive = false;
                isUsed = true;
            } catch (IOException e) {
                //Log.e("DEBUG", "Cannot close socket", e);
            }
        }
    }
}
