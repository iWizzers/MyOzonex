package fr.ozonex.myozonex;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

public class CustomNotification extends ContextWrapper {
    private static final String CHANNEL_ID = "my_ozonex_channel";
    private static CustomNotification inst;
    public static CustomNotification instance() {
        return inst;
    }

    private NotificationManager notificationManager;

    private int notificationId = 2;
    private List<StructureNotificationID> listeNotification = new ArrayList<>();

    public CustomNotification(Context context) {
        super(context);
        inst = this;
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Update";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.enableLights(true);
            channel.setLightColor(Color.CYAN);
            channel.enableVibration(true);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    public void ajouter(String contenu) {
        if (!MainActivity.instance().isActivityResumed()) {
            // Create an explicit intent for an Activity in your app
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_stat_logo)
                    .setContentTitle("MyOzonex")
                    .setContentText(contenu)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(contenu))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            // notificationId is a unique int for each notification that you must define
            if (notificationId < 2) {
                notificationId = 2;
            }
            getManager().notify(notificationId, mBuilder.build());
            listeNotification.add(new StructureNotificationID(notificationId++, contenu));
        }
    }

    public void supprimer() {
        getManager().cancelAll();
        listeNotification.clear();
    }

    public void supprimer(String contenu) {
        for (int i = 0; i < listeNotification.size(); i++) {
            if (listeNotification.get(i).getContenu().equals(contenu)) {
                getManager().cancel(listeNotification.get(i).getID());
                listeNotification.remove(i);
                break;
            }
        }
    }
}
