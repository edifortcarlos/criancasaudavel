package ao.co.najareal.vaciname.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.database.CriancaDao;
import ao.co.najareal.vaciname.database.VacinameRoomDatabase;
import ao.co.najareal.vaciname.model.AlturaCrianca;
import ao.co.najareal.vaciname.model.Crianca;
import ao.co.najareal.vaciname.model.CriancaVacina;
import ao.co.najareal.vaciname.model.PesoCrianca;
import ao.co.najareal.vaciname.model.TemperaturaCrianca;
import ao.co.najareal.vaciname.model.util.VacinaEnums;

public class CriancaRepository {

    private VacinameRoomDatabase db;
    private CriancaDao criancaDao;
    private LiveData<List<Crianca>> criancas;
    private LiveData<List<PesoCrianca>> pesos;
    private LiveData<List<AlturaCrianca>> Alturas;
    private LiveData<List<TemperaturaCrianca>> temperaturas;
    private List<CriancaVacina> criancaVacinas;

    public CriancaRepository(Application application) {
        db = VacinameRoomDatabase.getDatabase(application);
        criancaDao = db.criancaDao();
        criancas = criancaDao.getAll();
    }

    public long save(Crianca crianca) throws ExecutionException, InterruptedException {
        return new SaveCrianca().execute(crianca).get();
    }

    public void delete(Crianca crianca) {
        new DeleteCrianca().execute(crianca);
    }

    public Crianca getCriancaById(int id) throws ExecutionException, InterruptedException {
        return new GetCriancaById().execute(id).get();
    }


    public LiveData<List<Crianca>> getAll() {
        return criancas;
    }

   //------------------------------- CriancaVacina ----------------------------------


    public void save(CriancaVacina crianca) {
        new SaveCriancaVacina().execute(crianca);
    }

    public void delete(CriancaVacina crianca) {
        new DeleteCriancaVacina().execute(crianca);
    }

    public LiveData<List<CriancaVacina>> getAllVacinasLiveData(int idCrianca){
        return criancaDao.getAllVacinasLiveData(idCrianca);
    }

    public List<CriancaVacina> getAllVacinas(int idCrianca) throws ExecutionException, InterruptedException {
        return new GetAllVacinas().execute(idCrianca).get();
    }
    public List<CriancaVacina> getAllVacinasAtrasadas(int idCrianca,long dataRecomendada) throws ExecutionException, InterruptedException {
        return new GetAllVacinasAtrasadas().execute(idCrianca,dataRecomendada).get();
    }
    public LiveData<List<CriancaVacina>> getAllVacinasAtrasadasLiveData(int idCrianca,long dataRecomendada)  {
        return criancaDao.getAllVacinasAtrasadasLiveData(idCrianca,dataRecomendada,VacinaEnums.AGENDADA);
    }

    public LiveData<List<CriancaVacina>> getAllVacinasDispensadaLiveData(int idCrianca,long dataRecomendada)  {
        return criancaDao.getAllVacinasAtrasadasLiveData(idCrianca,dataRecomendada,VacinaEnums.DISPENSADA);
    }

    public LiveData<List<CriancaVacina>> getAllVacinasFeitasLiveData(int idCrianca,long dataRecomendada)  {
        return criancaDao.getAllVacinasAtrasadasLiveData(idCrianca,dataRecomendada,VacinaEnums.FEITA);
    }

    public LiveData<Integer> getQtdVacinasAtrasadasLiveData(int idCrianca,long dataRecomendada)  {
        return criancaDao.getQtdVacinasAtrasadasLiveData(idCrianca,dataRecomendada,VacinaEnums.AGENDADA);
    }


    public Integer getQtdVacinasAtrasadas(int idCrianca,long dataRecomendada) throws ExecutionException, InterruptedException {
        return new GetQtdVacinasAtrasadasLiveData().execute(idCrianca,dataRecomendada,VacinaEnums.AGENDADA).get();
    }

    public LiveData<List<CriancaVacina>> getAllVacinasByEstado(int idCrianca,String estado) {
        return criancaDao.getAllVacinasByEstado(idCrianca,estado);
    }

    public List<Integer> getIdsVacinasDaLista(int idCrianca) {
        return criancaDao.getIdsVacinasDaLista(idCrianca);
    }

    public LiveData<List<CriancaVacina>> getVacinasForaDaLista(int idCrianca,List<Integer> idsVacinas) {
        return criancaDao.getVacinasForaDaLista(idCrianca,idsVacinas);
    }

    public CriancaVacina getCriancaVacina(int idCrianca,int idVacina) throws ExecutionException, InterruptedException {
        return new GetCriancaVacina().execute(idCrianca,idVacina).get();
    }

    //------------------------------- Peso / Altura / Temperatura ----------------------------------


    public void save(PesoCrianca crianca) {
        new SavePeso().execute(crianca);
    }

    public void delete(PesoCrianca crianca) {
        new DeletePeso().execute(crianca);
    }

    public LiveData<List<PesoCrianca>> getPesoById(int idCrianca) {
        if(idCrianca < 1){
            return null;
        }
        return criancaDao.getPesosById(idCrianca);
    }



    public PesoCrianca getUltimoPeso(int idCrianca) throws ExecutionException, InterruptedException {
        return new GetUltimoPeso().execute(idCrianca).get();
    }


    public LiveData<PesoCrianca> getUltimoPesoLiveData(int idCrianca) {
        if(idCrianca < 1){
            return null;
        }
        LiveData<PesoCrianca> ultimoPesoLiveData = criancaDao.getUltimoPesoLiveData(idCrianca);

        return ultimoPesoLiveData;
    }

    public List<PesoCrianca> getPesoByIdGrafico(int idCrianca) throws ExecutionException, InterruptedException {
        return new GetPesoByIdGrafico().execute(idCrianca).get();
    }

    public void save(AlturaCrianca crianca) {
        new SaveAltura().execute(crianca);
    }


    public AlturaCrianca getAlturaAnterior(int idCrianca, long data) throws ExecutionException, InterruptedException {
        return new GetAlturaAnterior().execute(idCrianca,data).get();
    }

    public AlturaCrianca getAlturaMaxima(int idCrianca) throws ExecutionException, InterruptedException {
        return new GetAlturaMaxima().execute(idCrianca).get();
    }

    public void delete(AlturaCrianca crianca) {
        new DeleteAltura().execute(crianca);
    }

    public List<AlturaCrianca> getAlturasById(int idCrianca) throws ExecutionException, InterruptedException {
        return new GetAlturasById().execute(idCrianca).get();
    }

    public void save(TemperaturaCrianca crianca) {
        new SaveTemperatura().execute(crianca);
    }

    public void delete(TemperaturaCrianca crianca) {
        new DeleteTemperatura().execute(crianca);
    }

    public List<TemperaturaCrianca> getTemperaturasById(int idCrianca) throws ExecutionException, InterruptedException {
        return new GetTemperaturasById().execute(idCrianca).get();
    }


    // --------------------------- classes ----------------------------------

    protected class SaveCrianca extends AsyncTask<Crianca, Void, Long> {

        @Override
        protected Long doInBackground(Crianca... criancas) {
            Crianca crianca = criancas[0];
            long id = -2;
            if (crianca == null) {
                return null;
            }
            if (crianca.getId() < 1) {
                id = criancaDao.insert(crianca);
            } else {
                criancaDao.update(crianca);
            }
            return id;
        }
    }


    protected class DeleteCrianca extends AsyncTask<Crianca, Void, Void> {

        @Override
        protected Void doInBackground(Crianca... criancas) {
            Crianca crianca = criancas[0];
            if (crianca == null) {
                return null;
            }
            criancaDao.delete(crianca);
            return null;
        }
    }

    protected class GetCriancaById extends AsyncTask<Integer, Void, Crianca> {

        @Override
        protected Crianca doInBackground(Integer... id) {
            if (id == null) {
                return null;
            }
            return criancaDao.getCriancaById(id[0]);
        }
    }


    protected class SavePeso extends AsyncTask<PesoCrianca, Void, Long> {

        @Override
        protected Long doInBackground(PesoCrianca... criancas) {
            PesoCrianca crianca = criancas[0];
            long id = -2;
            if (crianca == null) {
                return null;
            }
            if (crianca.getId() < 1) {
                id = criancaDao.insert(crianca);
            } else {
                criancaDao.update(crianca);
            }
            return id;
        }
    }


    protected class DeletePeso extends AsyncTask<PesoCrianca, Void, Void> {

        @Override
        protected Void doInBackground(PesoCrianca... criancas) {
            PesoCrianca crianca = criancas[0];
            if (crianca == null) {
                return null;
            }
            criancaDao.delete(crianca);
            return null;
        }
    }


    protected class SaveAltura extends AsyncTask<AlturaCrianca, Void, Long> {

        @Override
        protected Long doInBackground(AlturaCrianca... criancas) {
            AlturaCrianca crianca = criancas[0];
            long id = -2;
            if (crianca == null) {
                return null;
            }
            if (crianca.getId() < 1) {
                id = criancaDao.insert(crianca);
            } else {
                criancaDao.update(crianca);
            }
            return id;
        }
    }


    protected class DeleteAltura extends AsyncTask<AlturaCrianca, Void, Void> {

        @Override
        protected Void doInBackground(AlturaCrianca... criancas) {
            AlturaCrianca crianca = criancas[0];
            if (crianca == null) {
                return null;
            }
            criancaDao.delete(crianca);
            return null;
        }
    }

    protected class SaveTemperatura extends AsyncTask<TemperaturaCrianca, Void, Long> {

        @Override
        protected Long doInBackground(TemperaturaCrianca... criancas) {
            TemperaturaCrianca crianca = criancas[0];
            long id = -2;
            if (crianca == null) {
                return null;
            }
            if (crianca.getId() < 1) {
                id = criancaDao.insert(crianca);
            } else {
                criancaDao.update(crianca);
            }
            return id;
        }
    }



    protected class DeleteTemperatura extends AsyncTask<TemperaturaCrianca, Void, Void> {

        @Override
        protected Void doInBackground(TemperaturaCrianca... criancas) {
            TemperaturaCrianca crianca = criancas[0];
            if (crianca == null) {
                return null;
            }
            criancaDao.delete(crianca);
            return null;
        }
    }


    protected class SaveCriancaVacina extends AsyncTask<CriancaVacina, Void, Void> {

        @Override
        protected Void doInBackground(CriancaVacina... criancas) {
            CriancaVacina crianca = criancas[0];
            if (crianca == null) {
                return null;
            }
            boolean existeVacina = criancaDao.criancaVacinaDose(crianca.getIdCrianca(), crianca.getIdVacina(), crianca.getDose()) == null;
            if (existeVacina) {
                criancaDao.insert(crianca);
            } else {
                criancaDao.update(crianca);
            }
            return null;
        }
    }


    protected class DeleteCriancaVacina extends AsyncTask<CriancaVacina, Void, Void> {

        @Override
        protected Void doInBackground(CriancaVacina... criancas) {
            CriancaVacina crianca = criancas[0];
            if (crianca == null) {
                return null;
            }
            boolean existeVacina = criancaDao.criancaVacinaDose(crianca.getIdCrianca(), crianca.getIdVacina(), crianca.getDose()) == null;
            if (existeVacina) {
                criancaDao.delete(crianca);
            }

            return null;
        }
    }

    protected class GetCriancaVacina extends AsyncTask<Integer, Void, CriancaVacina> {
        @Override
        protected CriancaVacina doInBackground(Integer... integers) {
            int idCrianca = integers[0];
            int idVacina = integers[1];
            return criancaDao.getCriancaVacina(idCrianca,idVacina);
        }
    }


    protected class GetAlturasById extends AsyncTask<Integer, Void, List<AlturaCrianca>> {
        @Override
        protected List<AlturaCrianca> doInBackground(Integer... integers) {
            int idCrianca = integers[0];
            return criancaDao.getAlturasById(idCrianca);
        }
    }

    protected class GetAllVacinas extends AsyncTask<Integer, Void, List<CriancaVacina>> {
        @Override
        protected List<CriancaVacina> doInBackground(Integer... integers) {
            int idCrianca = integers[0];
            return criancaDao.getAllVacinas(idCrianca);
        }
    }

    protected class GetAllVacinasAtrasadas extends AsyncTask<Object, Void, List<CriancaVacina>> {
        @Override
        protected List<CriancaVacina> doInBackground(Object... integers) {
            int idCrianca = (Integer) integers[0];
            long dataRecomendada = (Long) integers[1];
            return criancaDao.getAllVacinasAtrasadas(idCrianca,dataRecomendada, VacinaEnums.AGENDADA);
        }
    }

    protected class GetPesoByIdGrafico extends AsyncTask<Integer, Void, List<PesoCrianca>> {
        @Override
        protected List<PesoCrianca> doInBackground(Integer... integers) {
            int idCrianca = integers[0];
            return criancaDao.getPesosByIdGrafico(idCrianca);
        }
    }

    protected class GetTemperaturasById extends AsyncTask<Integer, Void, List<TemperaturaCrianca>> {
        @Override
        protected List<TemperaturaCrianca> doInBackground(Integer... integers) {
            int idCrianca = integers[0];
            return criancaDao.getTemperaturasById(idCrianca);
        }
    }


    private class GetUltimoPeso extends AsyncTask<Integer,Void,PesoCrianca>{
        @Override
        protected PesoCrianca doInBackground(Integer... integers) {
            return criancaDao.getUltimoPeso(integers[0]);
        }
    }

    private class GetAlturaAnterior extends AsyncTask<Object,Void,AlturaCrianca>{
        @Override
        protected AlturaCrianca doInBackground(Object... obj) {
            int idCrianca = (int) obj[0];
            long data = (long) obj[1];
            return criancaDao.getAlturaAnterior(idCrianca,data);
        }
    }

    private class GetAlturaMaxima extends AsyncTask<Object,Void,AlturaCrianca>{
        @Override
        protected AlturaCrianca doInBackground(Object... obj) {
            int idCrianca = (int) obj[0];
            return criancaDao.getAlturaMaxima(idCrianca);
        }
    }

    private class GetQtdVacinasAtrasadasLiveData extends AsyncTask<Object, Void,Integer>{
        @Override
        protected Integer doInBackground(Object... objects) {
            int idCrianca = (int) objects[0];
            long data = (long)objects[1];
            String estado = (String) objects[2];
            return criancaDao.getQtdVacinasAtrasadas(idCrianca,data,estado);
        }
    }
}
