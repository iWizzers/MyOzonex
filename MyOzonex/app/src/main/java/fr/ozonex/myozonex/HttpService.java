package fr.ozonex.myozonex;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class HttpService extends Service {
    private Timer timer;
    private TimerTask timerTask;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopTimerTask();

        if (!MainActivity.instance().isActivityResumed()) {
            Intent broadcastIntent = new Intent(this, HttpRestarterBroadcastReceiver.class);
            sendBroadcast(broadcastIntent);
        }
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
                MainActivity.instance().sendData(false,
                        "",
                        "",
                        HttpGetRequest.getRequestString(HttpGetRequest.RequestHTTP.Get),
                        HttpGetRequest.getPageString(HttpGetRequest.PageHTTP.PageData));
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
