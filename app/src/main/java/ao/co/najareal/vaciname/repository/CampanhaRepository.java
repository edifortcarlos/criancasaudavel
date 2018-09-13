package ao.co.najareal.vaciname.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.database.CampanhaDao;
import ao.co.najareal.vaciname.database.VacinameRoomDatabase;
import ao.co.najareal.vaciname.model.Campanha;

public class CampanhaRepository {

    private VacinameRoomDatabase db;
    private CampanhaDao campanhaDao;
    private LiveData<List<Campanha>> vacinas;
    private List<Campanha> campanhaList;

    public CampanhaRepository(Application application) {
        db = VacinameRoomDatabase.getDatabase(application);
        campanhaDao = db.campanhaDao();
        vacinas = campanhaDao.getAll();
        try {
            campanhaList = new GetAllList().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void save(Campanha campanha) {
        new Save().execute(campanha);
    }

    public void delete(Campanha campanha) {
        new Delete().execute(campanha);
    }

    public LiveData<List<Campanha>> getAll() {
        return vacinas;
    }

    public List<Campanha> getCampanhaByEstado(String estado) throws ExecutionException, InterruptedException {
        return new GetCampanhaByEstado().execute(estado).get();
    }

    public List<Campanha> getAllList()  {
        return campanhaList;
    }

    public Campanha getCampanhaById(int id) throws ExecutionException, InterruptedException {
        return new GetCampanhaById().execute(id).get();
    }

    protected class Save extends AsyncTask<Campanha, Void, Void> {

        @Override
        protected Void doInBackground(Campanha... vacinas) {
            Campanha campanha = vacinas[0];
            if (campanha == null) {
                return null;
            }
            if (campanha.getId() < 1) {
                campanhaDao.insert(campanha);
            } else {
                campanhaDao.update(campanha);
            }
            return null;
        }
    }

    protected class Delete extends AsyncTask<Campanha, Void, Void> {

        @Override
        protected Void doInBackground(Campanha... vacinas) {
            Campanha campanha = vacinas[0];
            if (campanha == null) {
                return null;
            }
            campanhaDao.delete(campanha);
            return null;
        }
    }

    protected class GetAllList extends AsyncTask<Void, Void, List<Campanha>> {

        @Override
        protected List<Campanha> doInBackground(Void... voids) {
            return campanhaDao.getAllList();
        }
    }
    protected class GetCampanhaById extends AsyncTask<Integer, Void, Campanha> {

        @Override
        protected Campanha doInBackground(Integer... id) {
            return campanhaDao.getCampanhaById(id[0]);
        }
    }
    protected class GetCampanhaByEstado extends AsyncTask<String, Void, List<Campanha>> {

        @Override
        protected List<Campanha> doInBackground(String... id) {
            return campanhaDao.getCampanhaByEstado(id[0]);
        }
    }

}
