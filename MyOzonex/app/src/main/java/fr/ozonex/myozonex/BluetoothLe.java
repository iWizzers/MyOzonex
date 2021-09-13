package fr.ozonex.myozonex;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BluetoothLe implements ServiceConnection, SerialListener {
    private static BluetoothLe inst = new BluetoothLe();
    public static BluetoothLe instance() {
        return inst;
    }

    public enum Connected { False, Pending, True }

    private boolean init = false;

    // Scan
    private BluetoothAdapter bluetoothAdapter;
    private boolean scanning = false;

    public static final int REQUEST_ENABLE_BT = 1;
    private static final long SCAN_PERIOD = 10000;

    // Control
    private String deviceAddress;
    public SerialService service;

    public Connected previousConnected = Connected.False;
    public Connected connected = Connected.False;
    public boolean initialStart = true;
    private String newline = TextUtil.newline_crlf;

    private Handler handler = new Handler();

    private ProgressDialog progressDialog;
    private String trame = "";
    public boolean readTrameComplete = false;

    public void init() {
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!MainActivity.instance().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            MainActivity.instance().afficherAlertDialog(MainActivity.instance().getString(R.string.bluetooth_titre), MainActivity.instance().getString(R.string.bluetooth_le_non_supporte), "OK");
        } else {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            // Ensures Bluetooth is available on the device and it is enabled. If not,
            // displays a dialog requesting user permission to enable Bluetooth.
            if (bluetoothAdapter == null) {
                MainActivity.instance().afficherAlertDialog(MainActivity.instance().getString(R.string.bluetooth_titre), MainActivity.instance().getString(R.string.bluetooth_non_supporte), "OK");
            } else {
                init = true;

                if (!bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    MainActivity.instance().startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                } else {
                    scanLeDevice(true);
                }
            }
        }
    }

    public boolean isInit() {
        return init;
    }

    public boolean isSupported() {
        return MainActivity.instance().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    public boolean isOn() {
        return bluetoothAdapter != null ? bluetoothAdapter.isEnabled() : false;
    }

    public void scanLeDevice(final boolean enable) {
        if (enable) {
            MainActivity.instance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog = ProgressDialog.show(MainActivity.instance(), MainActivity.instance().getString(R.string.bluetooth_titre), MainActivity.instance().getString(R.string.bluetooth_scan_en_cours), true);
                }
            });

            // Stops scanning after a pre-defined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MainActivity.instance().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                    });

                    MainActivity.instance().updateBt();
                    MainActivity.instance().afficherAlertDialog(MainActivity.instance().getString(R.string.bluetooth_titre), MainActivity.instance().getString(R.string.bluetooth_erreur_scan), "OK");

                    scanning = false;
                    bluetoothAdapter.stopLeScan(leScanCallback);
                }
            }, SCAN_PERIOD);

            scanning = true;
            bluetoothAdapter.startLeScan(leScanCallback);
        } else {
            handler.removeCallbacksAndMessages(null);
            scanning = false;
            bluetoothAdapter.stopLeScan(leScanCallback);
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            MainActivity.instance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if ((device.getName() != null) && device.getName().equals("MyOzonex")) {
                        scanLeDevice(false);
                        deviceAddress = device.getAddress();

                        if (service != null) {
                            service.attach(BluetoothLe.instance());
                        }

                        MainActivity.instance().bindService(new Intent(MainActivity.instance(), SerialService.class), BluetoothLe.instance(), Context.BIND_AUTO_CREATE);
                    }
                }
            });
        }
    };

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        service = ((SerialService.SerialBinder) binder).getService();
        service.attach(this);
        if (initialStart) {
            initialStart = false;
            MainActivity.instance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    connect();
                }
            });
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        //service = null;
    }

    private void connect() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(deviceAddress);
            MainActivity.instance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.setMessage(MainActivity.instance().getString(R.string.bluetooth_connexion_en_cours));
                }
            });
            connected = Connected.Pending;
            SerialSocket socket = new SerialSocket(MainActivity.instance().getApplicationContext(), device);
            service.connect(socket);
        } catch (Exception e) {
            onSerialConnectError(e);
        }
    }

    public void disconnect() {
        connected = Connected.False;
        readTrameComplete = true;
        MainActivity.instance().updateBt();
        service.disconnect();
        service = null;
        MainActivity.instance().unbindService(BluetoothLe.instance());
        initialStart = true;
    }

    public void send(String str) {
        if (connected != Connected.True) {
            return;
        }

        if (str.equals("Data")) {
            str += new SimpleDateFormat(";<uddMMyyyyHHmmss;").format(Calendar.getInstance().getTime());
            str += "0;0;0;";
            str += String.valueOf(Donnees.instance().obtenirVolumeBassin()) + ';';
            str += String.valueOf(Donnees.instance().obtenirTempoDemarrage()) + ';';
            str += String.valueOf(Donnees.instance().obtenirTypeRefoulement()) + ';';
            str += String.valueOf(Donnees.instance().obtenirTypeRegulation()) + ';';
            str += String.valueOf(Donnees.instance().obtenirTempsSecuriteInjection()) + ';';
            str += String.valueOf(Donnees.instance().obtenirHystInjectionPh()) + ';';
            str += String.valueOf(Donnees.instance().obtenirHystInjectionChloreOrp()) + ';';
            str += String.valueOf(Donnees.instance().obtenirEtatRegulations() ? 1 : 0) + ';';

            str += String.valueOf(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PompeFiltration) ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PompeFiltration)) + ';';
            str += String.valueOf(Donnees.instance().obtenirEtatLectureCapteurs() ? 1 : 0) + ';';
            for (int i = 0; i < Global.MAX_PLAGES_EQUIPEMENTS; i++) {
                str += Donnees.instance().obtenirPlageFonctionnement(Donnees.Equipement.PompeFiltration, i).getPlage() + ';';
            }
            str += String.valueOf(Donnees.instance().obtenirEtatHorsGel() ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirEnclHorsGel()) + ';';
            str += String.valueOf(Donnees.instance().obtenirArretHorsGel()) + ';';
            str += String.valueOf(Donnees.instance().obtenirFreqHorsGel()) + ';';

            str += String.valueOf(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Chauffage) ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Chauffage)) + ';';
            str += String.valueOf(Donnees.instance().obtenirControlePompeFiltration()) + ';';
            str += String.valueOf(Donnees.instance().obtenirTemperatureConsigne()) + ';';
            str += String.valueOf(Donnees.instance().obtenirGestionReversible()) + ';';
            str += String.valueOf(Donnees.instance().obtenirTemperatureReversible()) + ';';
            str += String.valueOf(Donnees.instance().obtenirAlarmeSeuilBas(Donnees.Equipement.Chauffage, null)) + ';';
            str += String.valueOf(Donnees.instance().obtenirAlarmeSeuilHaut(Donnees.Equipement.Chauffage, null)) + ';';

            str += String.valueOf(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Ozone) ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Ozone)) + ';';
            str += String.valueOf(Donnees.instance().obtenirTypeOzone()) + ';';
            str += String.valueOf(Donnees.instance().obtenirNombreVentilateursOzone()) + ';';
            str += String.valueOf(Donnees.instance().obtenirTempoOzone()) + ';';
            str += String.valueOf(Donnees.instance().obtenirErreurOzone()) + ';';
            str += String.valueOf(Donnees.instance().obtenirVitesseFan1Ozone()) + ';';
            str += String.valueOf(Donnees.instance().obtenirVitesseFan2Ozone()) + ';';
            str += String.valueOf(Donnees.instance().obtenirCourantAlimOzone()) + ';';
            str += String.valueOf(Donnees.instance().obtenirTensionAlimOzone()) + ';';
            str += String.valueOf(Donnees.instance().obtenirHauteTensionAlimOzone()) + ';';

            str += String.valueOf(Donnees.instance().obtenirPointConsignePh()) + ';';
            str += String.valueOf(Donnees.instance().obtenirHysteresisPhMoins()) + ';';
            str += String.valueOf(Donnees.instance().obtenirAlarmeSeuilBas(Donnees.Equipement.PhGlobal, null)) + ';';
            str += String.valueOf(Donnees.instance().obtenirAlarmeSeuilHaut(Donnees.Equipement.PhGlobal, null)) + ';';

            str += String.valueOf(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.PhMoins) ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.PhMoins)) + ';';
            str += String.valueOf(Donnees.instance().obtenirDebitEquipement(Donnees.Equipement.PhMoins)) + ';';
            str += String.valueOf(Donnees.instance().obtenirVolume(Donnees.Equipement.PhMoins)) + ';';
            str += "0;";
            str += "0;";
            str += String.valueOf(Donnees.instance().obtenirDureeCycle(Donnees.Equipement.PhMoins)) + ';';
            str += String.valueOf(Donnees.instance().obtenirMultiplicateurDifference(Donnees.Equipement.PhMoins)) + ';';
            str += String.valueOf(Donnees.instance().obtenirDureeInjectionMinimum(Donnees.Equipement.PhMoins)) + ';';
            str += String.valueOf(Donnees.instance().obtenirDureeInjection(Donnees.Equipement.PhMoins)) + ';';
            str += String.valueOf(Donnees.instance().obtenirTempsReponse(Donnees.Equipement.PhMoins)) + ';';
            str += String.valueOf(Donnees.instance().obtenirTempsInjectionJournalierMax(Donnees.Equipement.PhMoins)) + ';';
            str += "0;";

            str += String.valueOf(Donnees.instance().obtenirEquipementInstalle(Donnees.Equipement.Orp) ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirPointConsigneOrp()) + ';';
            str += String.valueOf(Donnees.instance().obtenirHysteresisOrp()) + ';';
            str += String.valueOf(Donnees.instance().obtenirEtatEquipement(Donnees.Equipement.Orp)) + ';';
            str += String.valueOf(Donnees.instance().obtenirDebitEquipement(Donnees.Equipement.Orp)) + ';';
            str += String.valueOf(Donnees.instance().obtenirVolume(Donnees.Equipement.Orp)) + ';';
            str += "0;";
            str += "0;";
            str += String.valueOf(Donnees.instance().obtenirDureeCycle(Donnees.Equipement.Orp)) + ';';
            str += String.valueOf(Donnees.instance().obtenirMultiplicateurDifference(Donnees.Equipement.Orp)) + ';';
            str += String.valueOf(Donnees.instance().obtenirDureeInjectionMinimum(Donnees.Equipement.Orp)) + ';';
            str += String.valueOf(Donnees.instance().obtenirDureeInjection(Donnees.Equipement.Orp)) + ';';
            str += String.valueOf(Donnees.instance().obtenirTempsReponse(Donnees.Equipement.Orp)) + ';';
            str += String.valueOf(Donnees.instance().obtenirTempsInjectionJournalierMax(Donnees.Equipement.Orp)) + ';';
            str += "0;";
            str += String.valueOf(Donnees.instance().obtenirAlarmeSeuilBas(Donnees.Equipement.Orp, Donnees.Capteur.Redox)) + ';';
            str += String.valueOf(Donnees.instance().obtenirAlarmeSeuilHaut(Donnees.Equipement.Orp, Donnees.Capteur.Redox)) + ';';
            str += String.valueOf(Donnees.instance().obtenirEtat(Donnees.Equipement.Orp) ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirJourSurchloration()) + ';';
            str += String.valueOf(Donnees.instance().obtenirFrequenceSurchloration()) + ';';
            str += String.valueOf(Donnees.instance().obtenirValeurSurchloration()) + ';';
            str += String.valueOf(Donnees.instance().obtenirProchaineSurchloration()) + ';';

            str += String.valueOf(Donnees.instance().obtenirDonneesEquipementsAuto() ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirModifPlagesAuto() ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirPlagesAuto() ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirDebutPlageAuto()) + ';';
            str += String.valueOf(Donnees.instance().obtenirTempsFiltrationJour()) + ';';
            str += String.valueOf(Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.PhMoins) ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirAsservissementAuto(Donnees.Equipement.Orp) ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirPointConsigneOrpAuto() ? 1 : 0) + ';';
            str += "0;";

            str += String.valueOf(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.TemperatureBassin) ? 1 : 0) + ';';
            str += "0;";
            str += String.valueOf(Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.TemperatureBassin)) + ';';
            str += "0;";
            str += String.valueOf(Donnees.instance().obtenirEtatEtalonnageCapteur(Donnees.Capteur.TemperatureBassin) ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirValeurEtalonnageCapteur(Donnees.Capteur.TemperatureBassin)) + ';';

            str += String.valueOf(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Ph) ? 1 : 0) + ';';
            str += "0;";
            str += String.valueOf(Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Ph)) + ';';
            str += "0;";
            str += String.valueOf(Donnees.instance().obtenirEtatEtalonnageCapteur(Donnees.Capteur.Ph) ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirValeurEtalonnageCapteur(Donnees.Capteur.Ph)) + ';';

            str += String.valueOf(Donnees.instance().obtenirCapteurInstalle(Donnees.Capteur.Redox) ? 1 : 0) + ';';
            str += "0;";
            str += String.valueOf(Donnees.instance().obtenirValeurCapteur(Donnees.Capteur.Redox)) + ';';
            str += "0;";
            str += String.valueOf(Donnees.instance().obtenirEtatEtalonnageCapteur(Donnees.Capteur.Redox) ? 1 : 0) + ';';
            str += String.valueOf(Donnees.instance().obtenirValeurEtalonnageCapteur(Donnees.Capteur.Redox)) + '>';

            Donnees.instance().definirEtalonnageCapteur(Donnees.Capteur.TemperatureBassin, false, 0);
            Donnees.instance().definirEtalonnageCapteur(Donnees.Capteur.Ph, false, 0);
            Donnees.instance().definirEtalonnageCapteur(Donnees.Capteur.Redox, false, 0);
        }

        try {
            service.write((str + newline).getBytes());
        } catch (Exception e) {
            onSerialIoError(e);
        }
    }

    private void receive(byte[] data) {
        String msg = new String(data);
        if(newline.equals(TextUtil.newline_crlf) && msg.length() > 0) {
            // don't show CR as ^M if directly before LF
            msg = msg.replace(TextUtil.newline_crlf, TextUtil.newline_lf);
        }
        trame += msg;

        if (trame.contains("<") && trame.contains(">")) {
            MainActivity.instance().getBtTreatment(trame.replace(trame.substring(0, trame.indexOf("<")), "").replaceAll("<", "").replaceAll(">", ""));
            trame = "";
        }
    }

    /*
     * SerialListener
     */
    @Override
    public void onSerialConnect() {
        MainActivity.instance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                progressDialog = null;
            }
        });

        Toast.makeText(MainActivity.instance(), MainActivity.instance().getString(R.string.bluetooth_connexion_ok), Toast.LENGTH_SHORT).show();

        connected = Connected.True;

        MainActivity.instance().updateBt();
        Donnees.instance().ajouterRequeteBt("ID?", true);
    }

    @Override
    public void onSerialConnectError(Exception e) {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

        MainActivity.instance().afficherAlertDialog(MainActivity.instance().getString(R.string.bluetooth_titre), MainActivity.instance().getString(R.string.bluetooth_erreur_connexion, e.getMessage()), "OK");
        disconnect();
    }

    @Override
    public void onSerialRead(byte[] data) {
        receive(data);
    }

    @Override
    public void onSerialIoError(Exception e) {
        MainActivity.instance().afficherAlertDialog(MainActivity.instance().getString(R.string.bluetooth_titre), MainActivity.instance().getString(R.string.bluetooth_erreur, e.getMessage()), "OK");
        disconnect();
    }
}
