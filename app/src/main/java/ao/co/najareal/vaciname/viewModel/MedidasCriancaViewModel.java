package ao.co.najareal.vaciname.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.model.TemperaturaCrianca;
import ao.co.najareal.vaciname.repository.MedidasCriancaRepository;

public class MedidasCriancaViewModel extends AndroidViewModel {

    private MedidasCriancaRepository rep;
    private LiveData<List<TemperaturaCrianca>> campanhas;

    public MedidasCriancaViewModel(@NonNull Application application) {
        super(application);
        rep = new MedidasCriancaRepository(application);
        campanhas = rep.getAll();
    }

    public void save(TemperaturaCrianca temperaturaCrianca){
        rep.save(temperaturaCrianca);
    }

    public void delete(TemperaturaCrianca temperaturaCrianca){
        rep.delete(temperaturaCrianca);
    }

    public LiveData<List<TemperaturaCrianca>> getAll(){
        return campanhas;
    }

    public LiveData<List<TemperaturaCrianca>> getVacinaByIdCrianca(int idCrianca){
        return rep.getVacinaByIdCrianca(idCrianca);
    }

    public List<TemperaturaCrianca> getAllVacinas() throws ExecutionException, InterruptedException {
        return rep.getAllList();
    }

    public TemperaturaCrianca getVacinaById(int id) throws ExecutionException, InterruptedException {
        return rep.getVacinaById(id);
    }
}
