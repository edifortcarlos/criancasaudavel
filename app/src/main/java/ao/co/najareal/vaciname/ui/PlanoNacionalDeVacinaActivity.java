package ao.co.najareal.vaciname.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.Date;
import java.util.List;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.adapters.VacinaAdapter;
import ao.co.najareal.vaciname.model.Vacina;
import ao.co.najareal.vaciname.viewModel.VacinaViewModel;

public class PlanoNacionalDeVacinaActivity extends AppCompatActivity {

    private VacinaAdapter adapter;
    private RecyclerView rvVacinas;
    private VacinaViewModel vacinaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plano_nacional_de_vacina);

        rvVacinas = findViewById(R.id.rvVacinas);
        vacinaViewModel = ViewModelProviders.of(this).get(VacinaViewModel.class);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        adapter = new VacinaAdapter(this);
        rvVacinas.setLayoutManager(llm);
        rvVacinas.setAdapter(adapter);

        vacinaViewModel.getAll().observe(this, new Observer<List<Vacina>>() {
            @Override
            public void onChanged(@Nullable List<Vacina> vacinas) {
                adapter.setVacinas(vacinas);
            }
        });

    }
}
