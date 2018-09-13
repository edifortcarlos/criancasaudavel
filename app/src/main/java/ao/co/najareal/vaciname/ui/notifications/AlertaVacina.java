package ao.co.najareal.vaciname.ui.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

import ao.co.najareal.vaciname.MainActivity;
import ao.co.najareal.vaciname.R;

public class AlertaVacina extends BroadcastReceiver {

/*
    Intent i = new Intent(getApplicationContext(), AlertaVacina.class);
    PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);

    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    alarmManager.set(AlarmManager.RTC_WAKEUP, new Date().getTime(), pi);
/*
        long[] pattern = {1};
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, 2);
*/

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("vaciname","AlertaVacina");
        Toast.makeText(context,"AlertaVacina", Toast.LENGTH_LONG).show();

        String title = "Vacinas em Atraso";
        String msg = "A Crianca x tem y vacinas em atraso";

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,1,i,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setSmallIcon(R.drawable.logonovo)
                .setWhen(new Date().getTime())
                .setContentTitle(title)
                .setContentText(msg)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification);

    }
}
