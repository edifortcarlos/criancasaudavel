package ao.co.najareal.vaciname;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Date;

import ao.co.najareal.vaciname.database.AlarmeVacina;
import ao.co.najareal.vaciname.database.VacinameRoomDatabase;
import ao.co.najareal.vaciname.ui.info.BemVindo;
import ao.co.najareal.vaciname.viewModel.VacinaViewModel;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        Intent i = new Intent(getApplicationContext(), AlarmeVacina.class);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, new Date().getTime(), pi);
*/

        Context context = this;
        Intent intent2 = new Intent(context, AlarmeVacina.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent2, 0);

        Date data = new Date();
        data.setHours(0);
        data.setMinutes(0);
        data.setSeconds(0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, data.getTime(), 86_400_000 /*24h em milissegundos*/, pi);
/*
        long[] pattern = {1};
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, 2);
*/
        // Chamada ao view model para esforçar a geração da base de dados
        ViewModelProviders.of(this).get(VacinaViewModel.class);

        new CallMain().execute();

    }

    private class CallMain extends AsyncTask<Void, Void, Void> {

        Date d = new Date();

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                if (VacinameRoomDatabase.CARREGADO) {
                    Thread.sleep(1000);
                }
                while (!VacinameRoomDatabase.CARREGADO) {
                    Thread.sleep(4000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(new Intent(MainActivity.this, BemVindo.class));
            finish();
        }
    }
}
