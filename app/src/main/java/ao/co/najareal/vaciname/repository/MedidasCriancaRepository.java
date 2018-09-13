package ao.co.najareal.vaciname.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.database.MedidasCriancaDao;
import ao.co.najareal.vaciname.database.VacinameRoomDatabase;
import ao.co.najareal.vaciname.model.TemperaturaCrianca;

public class MedidasCriancaRepository {

    private VacinameRoomDatabase db;
    private MedidasCriancaDao medidasCriancaDao;
    private LiveData<List<TemperaturaCrianca>> vacinas;
    private List<TemperaturaCrianca> campanhaList;

    public MedidasCriancaRepository(Application application) {
        db = VacinameRoomDatabase.getDatabase(application);
        medidasCriancaDao = db.medidasCriancaDao();
        vacinas = medidasCriancaDao.getAll();
        try {
            campanhaList = new GetAllList().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void save(TemperaturaCrianca temperaturaCrianca) {
        new Save().execute(temperaturaCrianca);
    }

    public void delete(TemperaturaCrianca temperaturaCrianca) {
        new Delete().execute(temperaturaCrianca);
    }

    public LiveData<List<TemperaturaCrianca>> getAll() {
        return vacinas;
    }

    public LiveData<List<TemperaturaCrianca>> getVacinaByIdCrianca(int idCrianca) {
        return medidasCriancaDao.getMedidasCriancaByIdCrianca(idCrianca);
    }

    public List<TemperaturaCrianca> getAllList()  {
        return campanhaList;
    }

    public TemperaturaCrianca getVacinaById(int id) throws ExecutionException, InterruptedException {
        return new GetMedidasCriancaById().execute(id).get();
    }

    protected class Save extends AsyncTask<TemperaturaCrianca, Void, Void> {

        @Override
        protected Void doInBackground(TemperaturaCrianca... vacinas) {
            TemperaturaCrianca temperaturaCrianca = vacinas[0];
            if (temperaturaCrianca == null) {
                return null;
            }
            if (temperaturaCrianca.getId() < 1) {
                medidasCriancaDao.insert(temperaturaCrianca);
            } else {
                medidasCriancaDao.update(temperaturaCrianca);
            }
            return null;
        }
    }

    protected class Delete extends AsyncTask<TemperaturaCrianca, Void, Void> {

        @Override
        protected Void doInBackground(TemperaturaCrianca... vacinas) {
            TemperaturaCrianca temperaturaCrianca = vacinas[0];
            if (temperaturaCrianca == null) {
                return null;
            }
            medidasCriancaDao.delete(temperaturaCrianca);
            return null;
        }
    }

    protected class GetAllList extends AsyncTask<Void, Void, List<TemperaturaCrianca>> {

        @Override
        protected List<TemperaturaCrianca> doInBackground(Void... voids) {
            return medidasCriancaDao.getAllList();
        }
    }
    protected class GetMedidasCriancaById extends AsyncTask<Integer, Void, TemperaturaCrianca> {

        @Override
        protected TemperaturaCrianca doInBackground(Integer... id) {
            return medidasCriancaDao.getMedidasCriancaById(id[0]);
        }
    }

}
