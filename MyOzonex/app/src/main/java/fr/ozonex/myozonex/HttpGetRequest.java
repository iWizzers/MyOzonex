package fr.ozonex.myozonex;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetRequest extends AsyncTask<String, Void, String> {
    private static final String BDD = "http://www.g-pac.online";
    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;

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
        Login,
        Data,
        Sensors,
        FiltrationPump,
        Booster,
        Filter,
        Ozone,
        Heater,
        UV_lamps,
        Ph_Minus,
        Ph_Plus,
        Orp,
        Algicide
    };

    private static String pageString[] = {
        "login",
        "data",
        "sensors",
        "filtration_pump",
        "booster",
        "filter",
        "ozone",
        "heater",
        "uv_lamps",
        "ph_minus",
        "ph_plus",
        "orp",
        "algicide"
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
        String stringUrl = BDD + '/' + params[0] + '_' + params[1] + "?id_system=" + Donnees.getPreferences(Donnees.ID_SYSTEME);
        if (params.length > 2) {
            stringUrl += '&' + params[2];
        }
        String result = "";
        String inputLine;

        try {
            // Create a URL object holding our url
            URL url = new URL(stringUrl);

            // Create a connection
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            // Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

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
        } catch(IOException e){
            e.printStackTrace();
            result = null;
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
