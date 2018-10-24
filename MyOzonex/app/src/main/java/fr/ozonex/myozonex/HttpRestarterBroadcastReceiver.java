package fr.ozonex.myozonex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HttpRestarterBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, HttpService.class));;
    }
}
