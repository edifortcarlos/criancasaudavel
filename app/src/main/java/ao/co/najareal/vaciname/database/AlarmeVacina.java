package ao.co.najareal.vaciname.database;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Vibrator;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.model.Crianca;
import ao.co.najareal.vaciname.model.CriancaVacina;
import ao.co.najareal.vaciname.model.util.VacinaEnums;
import ao.co.najareal.vaciname.ui.HomeActivity;

public class AlarmeVacina extends BroadcastReceiver {

    private VacinameRoomDatabase db;
    private CriancaDao criancaDao;


    @Override
    public void onReceive(Context context, Intent intent) {

        db = VacinameRoomDatabase.getDatabase(context);
        criancaDao = db.criancaDao();
        try {
            String msgVacinasAtrasadas = "";
            Date data = new Date();
            List<Integer> idCriancasComVacinasAtrasadas;
            List<CriancaVacina> allVacinasAtrasadas;
            String nome = "";
            String textoVacina = "Vacinas em atraso";
            String tituloNofificacao = "Vacinas em Atraso";
            int idNotificacao = 1;


            idCriancasComVacinasAtrasadas = new GetAllCriancaComVacinasAtrasadas().execute(new Date().getTime()).get();

            int tam = 0;
            String pNome; // Primeiro nome
            if (idCriancasComVacinasAtrasadas == null)
                return;
            if (idCriancasComVacinasAtrasadas.size() == 1) {
                int id = idCriancasComVacinasAtrasadas.get(0);
                allVacinasAtrasadas = new GetAllVacinasAtrasadas().execute(id, new Date().getTime(), VacinaEnums.AGENDADA).get();
                nome = new GetCriancaById().execute(id).get().getNome();
                nome = nome.substring(0, nome.indexOf(" ")); // Pega apenas o primeiro nome
                msgVacinasAtrasadas += nome + " tem " + allVacinasAtrasadas.size() + " " + textoVacina + " " + "\n";
            } else {

                for (int id : idCriancasComVacinasAtrasadas) {
                    if (tam + 1 == idCriancasComVacinasAtrasadas.size()) {
                        pNome = new GetCriancaById().execute(id).get().getNome();
                        pNome = pNome.substring(0, pNome.indexOf(" ")); // Pega apenas o primeiro nome
                        nome = nome.substring(0, nome.length() - 1); // Tirar a virgula
                        nome += " e " + pNome;
                        tam++;
                    } else {
                        pNome = new GetCriancaById().execute(id).get().getNome();
                        pNome = pNome.substring(0, pNome.indexOf(" ")); // Pega apenas o primeiro nome
                        nome += pNome + ",";
                        tam++;
                    }
                }
            }

            msgVacinasAtrasadas += nome + " têm " + textoVacina + " " + "\n";
            Intent intent1 = new Intent(context, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pi = PendingIntent.getActivity(context, 1, intent1, 0);
            Notification notification = new Notification.Builder(context)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_gota_24dp)
                    .setWhen(new Date().getTime())
                    .setContentTitle(tituloNofificacao)
                    .setContentIntent(pi)
                    .setContentText(msgVacinasAtrasadas)
                    .build();

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(idNotificacao, notification);

            Vibrator  vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(300);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class GetAllCriancaComVacinasAtrasadas extends AsyncTask<Long, Void, List<Integer>> {

        @Override
        protected List<Integer> doInBackground(Long... longs) {
            String estado = VacinaEnums.AGENDADA;
            return criancaDao.getAllCriancaComVacinasAtrasadas(longs[0],estado);
        }
    }

    private class GetCriancaById extends AsyncTask<Integer, Void, Crianca> {

        @Override
        protected Crianca doInBackground(Integer... id) {
            return criancaDao.getCriancaById(id[0]);
        }
    }

    private class GetAllVacinasAtrasadas extends AsyncTask<Object, Void, List<CriancaVacina>> {

        @Override
        protected List<CriancaVacina> doInBackground(Object... obj) {
            int id = (Integer) obj[0];
            long data = (Long) obj[1];
            String estado = (String) obj[2];
            return criancaDao.getAllVacinasAtrasadas(id, data, estado);
        }
    }
}
