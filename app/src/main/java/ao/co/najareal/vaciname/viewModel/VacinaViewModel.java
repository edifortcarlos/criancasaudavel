package ao.co.najareal.vaciname.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.model.Vacina;
import ao.co.najareal.vaciname.repository.VacinaRepository;

public class VacinaViewModel extends AndroidViewModel {

    private VacinaRepository rep;
    private LiveData<List<Vacina>> vacinas;

    public VacinaViewModel(@NonNull Application application) {
        super(application);
        rep = new VacinaRepository(application);
        vacinas = rep.getAll();
    }

    public void save(Vacina vacina){
        rep.save(vacina);
    }

    public void delete(Vacina vacina){
        rep.delete(vacina);
    }

    public LiveData<List<Vacina>> getAll(){
        return vacinas;
    }

    public LiveData<List<Vacina>> getVacinaByNome(String nome){
        return rep.getVacinaByNome(nome);
    }

    public List<Vacina> getAllVacinas() throws ExecutionException, InterruptedException {
        return rep.getAllList();
    }

    public Vacina getVacinaById(int id) throws ExecutionException, InterruptedException {
        return rep.getVacinaById(id);
    }
}
