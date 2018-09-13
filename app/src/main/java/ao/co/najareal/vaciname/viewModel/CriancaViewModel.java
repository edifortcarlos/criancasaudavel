package ao.co.najareal.vaciname.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.model.AlturaCrianca;
import ao.co.najareal.vaciname.model.Crianca;
import ao.co.najareal.vaciname.model.CriancaVacina;
import ao.co.najareal.vaciname.model.PesoCrianca;
import ao.co.najareal.vaciname.model.TemperaturaCrianca;
import ao.co.najareal.vaciname.model.Vacina;
import ao.co.najareal.vaciname.model.util.VacinaEnums;
import ao.co.najareal.vaciname.repository.CriancaRepository;
import ao.co.najareal.vaciname.repository.VacinaRepository;

public class CriancaViewModel extends AndroidViewModel {

    private CriancaRepository rep;
    private VacinaRepository repVacina;
    private LiveData<List<Crianca>> criancas;
    private LiveData<List<PesoCrianca>> pesosMutable = new MutableLiveData<>();
    private LiveData<PesoCrianca> pesoMutable = new MutableLiveData<>();
    private LiveData<Integer> qtdVacinasAtrasadaMutable = new MutableLiveData<>();
    private LiveData<List<CriancaVacina>> vacinasMutable = new MutableLiveData<>();
    private LiveData<List<CriancaVacina>> vacinasAtrasadasMutable = new MutableLiveData<>();
    private int id = 1;
    private LiveData<List<PesoCrianca>> pesoLiveData;
    private LiveData<PesoCrianca> pesoLiveData1;
    private LiveData<List<CriancaVacina>> vacinaLiveData;
    private LiveData<List<CriancaVacina>> vacinasAtrasadasLiveData;
    private LiveData<Integer> qtdVacinasAtrasadasLiveData;


    public CriancaViewModel(@NonNull Application application) {
        super(application);
        //this.setId(idCrianca);
        rep = new CriancaRepository(application);
        repVacina = new VacinaRepository(application);
        criancas = rep.getAll();

    }

    public void setId(int idCrianca) {
        this.id = idCrianca;
        pesosMutable = getPesoById(id);
        pesoMutable = getUltimoPesoLiveData(id);
        vacinasMutable = getAllVacinasLiveData(id);
        vacinasAtrasadasLiveData = getAllVacinasAtrasadasLiveData(id, new Date().getTime());
        qtdVacinasAtrasadasLiveData = getQtdVacinasAtrasadasLiveData(id, new Date().getTime());


        pesoLiveData = Transformations.switchMap(pesosMutable, pesoObj -> getPesoById(id));
        pesoLiveData1 = Transformations.switchMap(pesoMutable, obj -> getUltimoPesoLiveData(id));
        vacinaLiveData = Transformations.switchMap(vacinasMutable, obj -> getAllVacinasLiveData(id));
        vacinasAtrasadasLiveData = Transformations.switchMap(vacinasAtrasadasMutable, obj -> getAllVacinasAtrasadasLiveData(id, new Date().getTime()));
        qtdVacinasAtrasadasLiveData = Transformations.switchMap(qtdVacinasAtrasadaMutable, obj -> getQtdVacinasAtrasadasLiveData(id, new Date().getTime()));

    }


    public int saveCrianca(Crianca crianca) throws ExecutionException, InterruptedException {
        int id = (int) rep.save(crianca);
        if (id > 0) {
            List<Vacina> vacinas = repVacina.getAllList();
            for (Vacina vacina : vacinas) {
                CriancaVacina criancaVacina = new CriancaVacina();
                criancaVacina.setDose(vacina.getDose());
                criancaVacina.setIdCrianca(id);
                criancaVacina.setIdVacina(vacina.getId());
                criancaVacina.setDataDaAdministracao(crianca.getDataDeNascimento() + (vacina.getIdadeRecomendadaint() * 2_628_000_000L /*um mes em milissegundos*/));
                criancaVacina.setEstado(VacinaEnums.AGENDADA);
                criancaVacina.setDose(vacina.getDose());
                criancaVacina.setUnidadeSanitaria("Hospital Geral de Luanda");
                rep.save(criancaVacina);
            }
        }

        return id;
    }

    public void deleteCrianca(Crianca crianca) {
        rep.delete(crianca);
    }

    public Crianca getCriancaById(int id) throws ExecutionException, InterruptedException {
        return rep.getCriancaById(id);
    }

    public LiveData<List<Crianca>> getAll() {
        return criancas;
    }

    //----------------------- CriancaVacina ------------------
    public void saveCriancaVacina(CriancaVacina crianca) {
        rep.save(crianca);
    }

    public void dispensarCriancaVacina(CriancaVacina crianca) throws ExecutionException, InterruptedException {
        crianca = getCriancaVacina(crianca.getIdCrianca(), crianca.getIdVacina());
        crianca.setEstado(VacinaEnums.DISPENSADA);
        rep.save(crianca);
    }

    public void agendarCriancaVacina(CriancaVacina crianca) throws ExecutionException, InterruptedException {
        crianca = getCriancaVacina(crianca.getIdCrianca(), crianca.getIdVacina());
        crianca.setEstado(VacinaEnums.AGENDADA);
        rep.save(crianca);
    }

    public LiveData<List<CriancaVacina>> getAllVacinasAtrasadasLiveData(int idCrianca, long dataRecomendada) {
        vacinasAtrasadasMutable = rep.getAllVacinasAtrasadasLiveData(idCrianca, dataRecomendada);
        return vacinasAtrasadasMutable;
    }

    public LiveData<List<CriancaVacina>> getAllVacinasAtrasadasLiveData() {
        vacinasAtrasadasLiveData = Transformations.switchMap(vacinasAtrasadasMutable, obj -> getAllVacinasAtrasadasLiveData(id, new Date().getTime()));
        return vacinasAtrasadasLiveData;
    }

    public LiveData<List<CriancaVacina>> getAllVacinas() {
        return vacinaLiveData;
    }

    public LiveData<List<CriancaVacina>> getAllVacinasLiveData(int idCrianca) {
        vacinasMutable = rep.getAllVacinasLiveData(idCrianca);
        return vacinasMutable;
    }

    public List<CriancaVacina> getAllVacinas(int idCrianca) throws ExecutionException, InterruptedException {
        return rep.getAllVacinas(idCrianca);
    }

    public List<CriancaVacina> getAllVacinasAtrasadas(int idCrianca, long dataRecomendada)
            throws ExecutionException, InterruptedException {
        return rep.getAllVacinasAtrasadas(idCrianca, dataRecomendada);
    }

    public LiveData<List<CriancaVacina>> getAllVacinasByEstado(int idCrianca, String estado) {
        vacinasMutable = rep.getAllVacinasByEstado(idCrianca, estado);
        return vacinasMutable;
    }

    public LiveData<List<CriancaVacina>> getAllVacinasByEstado() {
        vacinaLiveData = Transformations.switchMap(vacinasMutable, obj -> getAllVacinasByEstado(id, ""));
        return vacinaLiveData;
    }

    public LiveData<List<CriancaVacina>> getVacinasForaDaLista() {
        vacinaLiveData = rep.getVacinasForaDaLista(id, rep.getIdsVacinasDaLista(id));
        return vacinaLiveData;
    }

    public LiveData<List<CriancaVacina>> getVacinasForaDaLista(int idCrianca, List<Integer> idsVacinas) {
        vacinasMutable = getVacinasForaDaLista(idCrianca, idsVacinas);
        return vacinasMutable;
    }

    public CriancaVacina getCriancaVacina(int idCrianca, int idVacina) throws ExecutionException, InterruptedException {
        return rep.getCriancaVacina(idCrianca, idVacina);
    }

    //----------------------- Peso ------------------
    public void save(PesoCrianca crianca) {
        rep.save(crianca);
    }

    public void delete(PesoCrianca crianca) {
        rep.delete(crianca);
    }

    public LiveData<List<PesoCrianca>> getPesoById() {
        return pesoLiveData;
    }

    private LiveData<List<PesoCrianca>> getPesoById(int id) {
        pesosMutable = rep.getPesoById(id);
        return pesosMutable;
    }


    public LiveData<Integer> getQtdVacinasAtrasadasLiveData(int idCrianca, long dataRecomendada) {
        Log.e("vaciname","Id "+idCrianca+" getQtdVacinasAtrasadasLiveData");
        qtdVacinasAtrasadaMutable = rep.getQtdVacinasAtrasadasLiveData(idCrianca, dataRecomendada);
        return qtdVacinasAtrasadaMutable;
    }

    public LiveData<Integer> getQtdVacinasAtrasadasLiveData() {
        return qtdVacinasAtrasadasLiveData;
    }

    public LiveData<PesoCrianca> getUltimoPesoLiveData() {
        return pesoLiveData1;
    }

    public int getQtdVacinasAtrasadas(int idCrianca, long dataRecomendada) throws ExecutionException, InterruptedException {
        return rep.getQtdVacinasAtrasadas(idCrianca,dataRecomendada);
    }


    public PesoCrianca getUltimoPeso(int id) throws ExecutionException, InterruptedException {
        return rep.getUltimoPeso(id);
    }

    private LiveData<PesoCrianca> getUltimoPesoLiveData(int idCrianca) {
        pesoMutable = rep.getUltimoPesoLiveData(idCrianca);
        return pesoMutable;
    }

    public List<PesoCrianca> getPesoByIdGrafico(int id) throws ExecutionException, InterruptedException {
        return rep.getPesoByIdGrafico(id);
    }

    //----------------------- Altura ------------------
    public void save(AlturaCrianca crianca) {
        rep.save(crianca);
    }

    public AlturaCrianca getAlturaAnterior(int idCrianca, long data) throws ExecutionException, InterruptedException {
        return rep.getAlturaAnterior(idCrianca,data);
    }

    public AlturaCrianca getAlturaMaxima(int idCrianca) throws ExecutionException, InterruptedException {
        return rep.getAlturaMaxima(idCrianca);
    }

    public void delete(AlturaCrianca crianca) {
        rep.delete(crianca);
    }

    public List<AlturaCrianca> getAlturasById(int idCrianca) throws ExecutionException, InterruptedException {
        return rep.getAlturasById(idCrianca);
    }

    //----------------------- Temperatura ------------------
    public void save(TemperaturaCrianca crianca) {
        rep.save(crianca);
    }

    public void delete(TemperaturaCrianca crianca) {
        rep.delete(crianca);
    }

    public List<TemperaturaCrianca> getTemperaturasById(int idCrianca) throws ExecutionException, InterruptedException {
        return rep.getTemperaturasById(idCrianca);
    }


}
