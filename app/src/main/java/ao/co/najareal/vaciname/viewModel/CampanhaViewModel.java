package ao.co.najareal.vaciname.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.model.Campanha;
import ao.co.najareal.vaciname.repository.CampanhaRepository;

public class CampanhaViewModel extends AndroidViewModel {

    private CampanhaRepository rep;
    private LiveData<List<Campanha>> campanhas;

    public CampanhaViewModel(@NonNull Application application) {
        super(application);
        rep = new CampanhaRepository(application);
        campanhas = rep.getAll();
    }

    public void save(Campanha campanha){
        rep.save(campanha);
    }

    public void delete(Campanha campanha){
        rep.delete(campanha);
    }

    public LiveData<List<Campanha>> getAll(){
        return campanhas;
    }

    public List<Campanha> getCampanhaByEstado(String nome) throws ExecutionException, InterruptedException {
        return rep.getCampanhaByEstado(nome);
    }
    public Campanha getCampanhaById(int id) throws ExecutionException, InterruptedException {
        return rep.getCampanhaById(id);
    }

    public List<Campanha> getAllCampanhas() throws ExecutionException, InterruptedException {
        return rep.getAllList();
    }

}
