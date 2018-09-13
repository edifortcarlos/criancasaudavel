package ao.co.najareal.vaciname.database;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

import ao.co.najareal.vaciname.MainActivity;

public class BootReciverImpl extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")) {
            Intent intent2 = new Intent(context, AlarmeVacina.class);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent2, 0);

            Date data = new Date();
            data.setHours(7);
            data.setMinutes(0);
            data.setSeconds(0);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, data.getTime(), 86_400_000 /*24h em milissegundos*/, pi);

        }
    }
}
