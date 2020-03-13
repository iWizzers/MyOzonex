package fr.ozonex.myozonex;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetRequest extends AsyncTask<String, Void, String> {
    //private static final String BDD = "http://192.168.1.8/myozonexserver";    // Local
    private static final String BDD = "http://www.g-pac.online";              // Serveur
    private static final String REQUEST_METHOD = "GET";

    private ProgressDialog progressDialog;

    public enum RequestHTTP {
        Get,
        Update
    };

    private static String requestString[] = {
        "get",
        "update"
    };

    public enum PageHTTP {
        PageLogin,
        PageData,
        PageEvents,
        PageAutomatisation,
        PageHorlogerie,
        PageBassin,
        PageCapteurs,
        PagePompeFiltration,
        PageFiltre,
        PageSurpresseur,
        PageChauffage,
        PageOzonateur,
        PageLampesUV,
        PageElectrolyseur,
        PageRegulateurPhPlus,
        PageRegulateurPhMoins,
        PageRegulateurORP,
        PageAlgicide,
        PageEclairage
    };

    private static String pageString[] = {
        "login",
        "data",
        "events",
        "automatisation",
        "horlogerie",
        "bassin",
        "capteurs",
        "pompe_filtration",
        "filtre",
        "surpresseur",
        "chauffage",
        "ozonateur",
        "lampes_uv",
        "electrolyseur",
        "reg_ph_plus",
        "reg_ph_moins",
        "reg_orp",
        "algicide",
        "eclairage"
    };

    private MainActivity mainActivity;

    public HttpGetRequest(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public static String getRequestString(RequestHTTP request) {
        return requestString[request.ordinal()];
    }

    public static String getPageString(PageHTTP page) {
        return pageString[page.ordinal()];
    }

    public void setProgressDialogView(boolean visible, String titre, String texte) {
        if (visible) {
            progressDialog = ProgressDialog.show(mainActivity, titre,
                    texte, true);
        } else {
            progressDialog = null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String urls[] = {"", ""};

        String stringChanges = BDD + '/' + "update_changes?id_systeme=" + Donnees.getPreferences(Donnees.ID_SYSTEME) + "&changes_from_app=1";
        String stringDonnees = BDD + '/' + params[0] + '_' + params[1] + "?id_systeme=" + Donnees.getPreferences(Donnees.ID_SYSTEME);
        if (params.length > 2) {
            stringDonnees += '&' + params[2];
        }

        urls[0] = stringDonnees;
        if (!params[0].equals(HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Get))) {
            urls[1] = stringChanges;
        }

        String result = "";
        String inputLine;

        for (int i = 0; i < urls.length; i++) {
            try {
                Log.d("TEST", urls[i]);
                if (urls[i].isEmpty()) {
                    break;
                }

                // Create a URL object holding our url
                URL url = new URL(urls[i]);

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
                if (i == 0) {
                    result = stringBuilder.toString();
                }
            } catch (IOException e) {
                //e.printStackTrace();
                if (i == 0) {
                    result = null;
                }
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if ((progressDialog != null) && (progressDialog.isShowing())) {
            progressDialog.dismiss();
        }

        mainActivity.getTreatment(result);
    }
}
