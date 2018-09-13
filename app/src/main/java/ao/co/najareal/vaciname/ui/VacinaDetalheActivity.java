package ao.co.najareal.vaciname.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.adapters.VacinaAdapter;
import ao.co.najareal.vaciname.model.Vacina;
import ao.co.najareal.vaciname.viewModel.VacinaViewModel;

public class VacinaDetalheActivity extends AppCompatActivity {

    private TextView txtNome,txtDose, txtTempoRecomendado;

    private VacinaViewModel vacinaViewModel;
    private VacinaAdapter adapter;
    private RecyclerView rvVacinas;
    private Vacina vacina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacina_detalhe);
        setTitle(getString(R.string.detalhesDaVacina));

        txtNome = findViewById(R.id.txtNome);
        txtDose = findViewById(R.id.btnDose);
        txtTempoRecomendado = findViewById(R.id.btnTempoRecomendado);

        int id = getIntent().getIntExtra("id",0);
        rvVacinas = findViewById(R.id.rvVacinas);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        adapter = new VacinaAdapter(this);
        rvVacinas.setAdapter(adapter);
        rvVacinas.setLayoutManager(llm);
        vacinaViewModel = ViewModelProviders.of(this).get(VacinaViewModel.class);
        try {
            vacina = vacinaViewModel.getVacinaById(id);
            setVacina(vacina);
            vacinaViewModel.getVacinaByNome(vacina.getNome()).observe(this, new Observer<List<Vacina>>() {
                @Override
                public void onChanged(@Nullable List<Vacina> vacinas) {
                    adapter.setVacinas(vacinas);
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setVacina(Vacina c){
        txtNome.setText(c.getNome());
        txtDose.setText(c.getDose());
        txtTempoRecomendado.setText(c.getIdadeRecomendada());
    }
}
