package ao.co.najareal.vaciname.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.adapters.CriancaAdapter;
import ao.co.najareal.vaciname.model.Crianca;
import ao.co.najareal.vaciname.viewModel.CriancaViewModel;
import ao.co.najareal.vaciname.viewModel.VacinaViewModel;

public class CriancaActivity extends AppCompatActivity {

    private CriancaViewModel criancaViewModel;
    private VacinaViewModel vacinaViewModel;
    private RecyclerView rvCrianca;
    private CriancaAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crianca);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        criancaViewModel = ViewModelProviders.of(this).get(CriancaViewModel.class);
        vacinaViewModel = ViewModelProviders.of(this).get(VacinaViewModel.class);
        rvCrianca = findViewById(R.id.rvCriancas);
        adapter = new CriancaAdapter(this,this, criancaViewModel,vacinaViewModel);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvCrianca.setLayoutManager(llm);
        rvCrianca.setAdapter(adapter);

        criancaViewModel.getAll().observe(this, new Observer<List<Crianca>>() {
            @Override
            public void onChanged(@Nullable List<Crianca> criancas) {
                adapter.setCriancas(criancas);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_crianca, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

}
