package ao.co.najareal.vaciname.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.model.Campanha;
import ao.co.najareal.vaciname.viewModel.CampanhaViewModel;
import ao.co.najareal.vaciname.adapters.CampanhaAdapter;

public class CampanhaActivity extends AppCompatActivity {

    private RecyclerView rvCampanhas;
    private CampanhaAdapter adapter;
    private CampanhaViewModel campanhaViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campanha);

        setTitle(getString(R.string.campanhas));
        campanhaViewModel = ViewModelProviders.of(this).get(CampanhaViewModel.class);

        adapter = new CampanhaAdapter(this);
        rvCampanhas = findViewById(R.id.rvCampanhas);
        rvCampanhas.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvCampanhas.setAdapter(adapter);

        campanhaViewModel.getAll().observe(this, new Observer<List<Campanha>>() {
            @Override
            public void onChanged(@Nullable List<Campanha> campanhas) {
                adapter.setCampanhas(campanhas);
            }
        });


    }
}
