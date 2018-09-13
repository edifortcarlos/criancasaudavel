package ao.co.najareal.vaciname.ui.graph;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.adapters.AlturaAdapter;
import ao.co.najareal.vaciname.adapters.PesoAdapter;
import ao.co.najareal.vaciname.adapters.TemperaturaAdapter;
import ao.co.najareal.vaciname.model.PesoCrianca;
import ao.co.najareal.vaciname.model.util.Medida;
import ao.co.najareal.vaciname.viewModel.CriancaViewModel;

public class MedidasValoresActivity extends AppCompatActivity {

    private RecyclerView rvMedidas;
    private String tipoMedida;
    private int idCrianca;

    private PesoAdapter adapterPeso;
    private AlturaAdapter adapterAltura;
    private TemperaturaAdapter adapterTemperatura;
    private CriancaViewModel criancaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medidas_valores);

        Intent intent = getIntent();
        tipoMedida = intent.getStringExtra("tipo");
        idCrianca = intent.getIntExtra("id", 0);

        if(idCrianca < 1){
            onBackPressed();
            finish();
        }

        criancaViewModel = ViewModelProviders.of(this).get(CriancaViewModel.class);
        criancaViewModel.setId(idCrianca);

        rvMedidas = findViewById(R.id.rvMedidas);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvMedidas.setLayoutManager(llm);

        try {
            switch (tipoMedida) {
                case Medida.PESO:
                    setTitle(getString(R.string.medicaoDoPeso));
                    adapterPeso = new PesoAdapter(this, criancaViewModel);
                    rvMedidas.setAdapter(adapterPeso);
                    criancaViewModel.getPesoById().observe(this, new Observer<List<PesoCrianca>>() {
                        @Override
                        public void onChanged(@Nullable List<PesoCrianca> pesoCriancas) {
                            if(pesoCriancas == null || pesoCriancas.size()<1) {
                                onBackPressed();
                                finish();
                            }
                            adapterPeso.setPesos(pesoCriancas);
                        }
                    });
                    break;
                case Medida.ALTURA:
                    setTitle(getString(R.string.medicaoDaAltura));
                    adapterAltura = new AlturaAdapter(this, criancaViewModel);
                    rvMedidas.setAdapter(adapterAltura);
                    adapterAltura.setAlturas(criancaViewModel.getAlturasById(idCrianca));
                    break;
                case Medida.TEMPERATURA:
                    setTitle(getString(R.string.medicaoDaTemperatura));
                    adapterTemperatura = new TemperaturaAdapter(this, criancaViewModel);
                    rvMedidas.setAdapter(adapterTemperatura);
                    adapterTemperatura.setTemperaturas(criancaViewModel.getTemperaturasById(idCrianca));
                    break;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
