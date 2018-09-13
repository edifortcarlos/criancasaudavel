package ao.co.najareal.vaciname.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.database.VacinaDao;
import ao.co.najareal.vaciname.database.VacinameRoomDatabase;
import ao.co.najareal.vaciname.model.Vacina;

public class VacinaRepository {

    private VacinameRoomDatabase db;
    private VacinaDao vacinaDao;
    private LiveData<List<Vacina>> vacinas;
    private List<Vacina> vacinasList;

    public VacinaRepository(Application application) {
        db = VacinameRoomDatabase.getDatabase(application);
        vacinaDao = db.vacinaDao();
        vacinas = vacinaDao.getAll();
        try {
            vacinasList = new GetAllList().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void save(Vacina vacina) {
        new Save().execute(vacina);
    }

    public void delete(Vacina vacina) {
        new Delete().execute(vacina);
    }

    public LiveData<List<Vacina>> getAll() {
        return vacinas;
    }

    public LiveData<List<Vacina>> getVacinaByNome(String nome) {
        return vacinaDao.getVacinaByNome(nome);
    }

    public List<Vacina> getAllList()  {
        return vacinasList;
    }

    public Vacina getVacinaById(int id) throws ExecutionException, InterruptedException {
        return new GetVacinaById().execute(id).get();
    }

    protected class Save extends AsyncTask<Vacina, Void, Void> {

        @Override
        protected Void doInBackground(Vacina... vacinas) {
            Vacina vacina = vacinas[0];
            if (vacina == null) {
                return null;
            }
            if (vacina.getId() < 1) {
                vacinaDao.insert(vacina);
            } else {
                vacinaDao.update(vacina);
            }
            return null;
        }
    }

    protected class Delete extends AsyncTask<Vacina, Void, Void> {

        @Override
        protected Void doInBackground(Vacina... vacinas) {
            Vacina vacina = vacinas[0];
            if (vacina == null) {
                return null;
            }
            vacinaDao.delete(vacina);
            return null;
        }
    }

    protected class GetAllList extends AsyncTask<Void, Void, List<Vacina>> {

        @Override
        protected List<Vacina> doInBackground(Void... voids) {
            return vacinaDao.getAllList();
        }
    }
    protected class GetVacinaById extends AsyncTask<Integer, Void, Vacina> {

        @Override
        protected Vacina doInBackground(Integer... id) {
            return vacinaDao.getVacinaById(id[0]);
        }
    }

}
