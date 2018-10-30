package ao.co.najareal.vaciname.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.adapters.CriancaVacinaAdapter;
import ao.co.najareal.vaciname.database.VacinameRoomDatabase;
import ao.co.najareal.vaciname.model.Crianca;
import ao.co.najareal.vaciname.model.CriancaVacina;
import ao.co.najareal.vaciname.model.PesoCrianca;
import ao.co.najareal.vaciname.model.Vacina;
import ao.co.najareal.vaciname.model.util.Medida;
import ao.co.najareal.vaciname.model.util.Sexo;
import ao.co.najareal.vaciname.ui.graph.MedidasActivity;
import ao.co.najareal.vaciname.ui.graph.MedidasValoresActivity;
import ao.co.najareal.vaciname.ui.util.DataPickerListnerData;
import ao.co.najareal.vaciname.viewModel.CriancaViewModel;
import ao.co.najareal.vaciname.viewModel.VacinaViewModel;

public class CriancaVacinaActivity extends AppCompatActivity implements View.OnClickListener {

    private String formatDate = "dd/MM/yyyy";
    private TextView txtNome, txtPeso, txtDataDeNascimento;

    private RecyclerView rvCriancaVacina;
    private CriancaVacinaAdapter adapter;
    private Button btnVerDados;
    private Button btnEditarDados;

    private CriancaViewModel criancaViewModel;
    private VacinaViewModel vacinaViewModel;
    private int id;
    private Crianca crianca1;

    private SimpleDateFormat sdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crianca_vacina);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtNome = findViewById(R.id.txtNome);
        txtPeso = findViewById(R.id.txtPeso);
        txtDataDeNascimento = findViewById(R.id.txtDataDeNascimento);
        sdf = new SimpleDateFormat(formatDate);

        id = getIntent().getIntExtra(getString(R.string.id), 0);
        if (id < 1) {
            // Instrução para voltar para a tela anterior
            onBackPressed();
        }
        btnVerDados = findViewById(R.id.btnVerDados);
        btnEditarDados = findViewById(R.id.btnEditarDados);
        btnVerDados.setOnClickListener(this);
        btnEditarDados.setOnClickListener(this);

        criancaViewModel = ViewModelProviders.of(this).get(CriancaViewModel.class);
        vacinaViewModel = ViewModelProviders.of(this).get(VacinaViewModel.class);

        rvCriancaVacina = findViewById(R.id.rvCriancaVacina);
        adapter = new CriancaVacinaAdapter(this, vacinaViewModel, criancaViewModel);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvCriancaVacina.setLayoutManager(llm);
        rvCriancaVacina.setAdapter(adapter);
        txtPeso.setOnClickListener(this);

        criancaViewModel.setId(id);
        criancaViewModel.getAllVacinas().observe(this, new Observer<List<CriancaVacina>>() {
            @Override
            public void onChanged(@Nullable List<CriancaVacina> criancas) {
                adapter.setCriancaVacinas(criancas);
            }
        });


        setDadosCrianca();

    }

    private void setDadosCrianca() {
        try {
            crianca1 = criancaViewModel.getCriancaById(id);
            int index = crianca1.getNome().indexOf(" ");
            if (index < 1) {
                index = crianca1.getNome().length();
            }
            setTitle(crianca1.getNome().substring(0, index));
            txtNome.setText(crianca1.getNome());

            txtPeso.setText(String.valueOf(crianca1.getPeso()) + " " + getString(R.string.kg));
            txtDataDeNascimento.setText(sdf.format(crianca1.getDataDeNascimento()));
            // adapter.setCriancaVacinas(criancaViewModel.getAllVacinas(id));
            PesoCrianca pesoCrianca = criancaViewModel.getUltimoPeso(crianca1.getId());
            if (pesoCrianca != null && pesoCrianca.getPeso() > 0) {
                txtPeso.setText(String.valueOf(pesoCrianca.getPeso()) + " " + getString(R.string.kg));
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVerDados:
                verDados(crianca1);
                break;
            case R.id.btnEditarDados:
                editarDados(crianca1);
                break;
            case R.id.txtPeso:
                startActivity(new Intent(this, MedidasValoresActivity.class)
                        .putExtra(getString(R.string.tipo), Medida.PESO)
                        .putExtra(getString(R.string.id), id));
                break;
        }
    }


    private void verDados(@NonNull Crianca c) {
        vendoDados = true;
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.ver_dados_crianca, null);
        final TextView txtNome = v.findViewById(R.id.txtNome);
        final TextView txtPesoNascenca = v.findViewById(R.id.txtPesoNascenca);
        final TextView txtDataDeNascimento = v.findViewById(R.id.txtDataDeNascimento);
        final TextView txtPeso = v.findViewById(R.id.txtPeso);
        final TextView txtNaturalidade = v.findViewById(R.id.txtNaturalidade);
        final TextView txtNacionalidade = v.findViewById(R.id.txtNacionalidade);
        final TextView txtPai = v.findViewById(R.id.txtPai);
        final TextView txtMae = v.findViewById(R.id.txtMae);
        final TextView txtMorada = v.findViewById(R.id.txtMorada);
        final TextView txtLocalDoParto = v.findViewById(R.id.txtLocalDoParto);
        final TextView txtResponsavelPeloParto = v.findViewById(R.id.txtResponsavelPeloParto);
        final TextView txtUnidadeHospitalar = v.findViewById(R.id.txtUnidadeHospitalar);
        final TextView txtSexo = v.findViewById(R.id.txtSexo);
        final TextView txtApgar = v.findViewById(R.id.txtApgar);
        final TextView txtTipoDeParto = v.findViewById(R.id.txtTipoDeParto);
        Button btnSalvar = v.findViewById(R.id.btnFechar);

        SimpleDateFormat sdf = new SimpleDateFormat(formatDate);

        txtNome.setText(c.getNome());
        txtDataDeNascimento.setText(sdf.format(c.getDataDeNascimento()));
        txtPesoNascenca.setText(String.valueOf(c.getPeso()) + " " + getString(R.string.kg));

        txtNaturalidade.setText(c.getNaturalidade());
        txtNacionalidade.setText(c.getNacionalidade());
        txtPai.setText(c.getPai());
        txtMae.setText(c.getMae());
        txtMorada.setText(c.getMorada());
        txtLocalDoParto.setText(c.getLocalDoParto());
        txtUnidadeHospitalar.setText(c.getUnidadeHOspitalar());
        txtApgar.setText(String.valueOf(c.getApgar()));
        txtSexo.setText(c.getSexo());
        txtTipoDeParto.setText(c.getTipoDeParto());
        txtResponsavelPeloParto.setText(c.getResponsavelPeloParto());

        criancaViewModel.getUltimoPesoLiveData().observe(this, new Observer<PesoCrianca>() {
            @Override
            public void onChanged(@Nullable PesoCrianca pesoCrianca) {
                if (pesoCrianca != null && pesoCrianca.getPeso() > 0) {
                    txtPeso.setText(String.valueOf(pesoCrianca.getPeso()) + " " + getString(R.string.kg));
                }else{
                    txtPeso.setText(txtPesoNascenca.getText().toString());
                }
            }
        });

        dialog.setView(v);
        final android.support.v7.app.AlertDialog ab = dialog.create();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ab.dismiss();
                vendoDados = false;
            }
        });

        ab.setCancelable(false);
        ab.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("editando",editando);
        outState.putBoolean("vendoDados",vendoDados);
        if(editando){
            outState.putString("txtNaturalidade",txtNaturalidade.getText().toString());
            outState.putString("txtNacionalidade",txtNacionalidade.getText().toString());
            outState.putString("txtPai",txtPai.getText().toString());
            outState.putString("txtMae",txtMae.getText().toString());
            outState.putString("txtMorada",txtMorada.getText().toString());
            outState.putString("txtUnidadeHospitalar",txtUnidadeHospitalar.getText().toString());
            outState.putString("txtResponsavelPeloParto",txtResponsavelPeloParto.getText().toString());
            outState.putString("txtNome1",txtNome1.getText().toString());
            outState.putString("txtTitulo",txtTitulo.getText().toString());
            outState.putString("txtDataDeNascimento1",txtDataDeNascimento1.getText().toString());
            outState.putString("txtPeso1",txtPeso1.getText().toString());

            outState.putInt("spnApgar",spnApgar.getSelectedItemPosition());
            outState.putInt("spnTipoDeParto",spnTipoDeParto.getSelectedItemPosition());
            outState.putInt("spnLocalDoParto",spnLocalDoParto.getSelectedItemPosition());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("vaciname","onRestoreInstanceState "+editando);
        editando = savedInstanceState.getBoolean("editando");
        if(editando){
            editarDados(crianca1);
            txtNaturalidade.setText(savedInstanceState.getString("txtNaturalidade"));
            txtNacionalidade.setText(savedInstanceState.getString("txtNacionalidade"));
            txtPai.setText(savedInstanceState.getString("txtPai"));
            txtMae.setText(savedInstanceState.getString("txtMae"));
            txtMorada.setText(savedInstanceState.getString("txtMorada"));
            txtUnidadeHospitalar.setText(savedInstanceState.getString("txtUnidadeHospitalar"));
            txtResponsavelPeloParto.setText(savedInstanceState.getString("txtResponsavelPeloParto"));
            txtNome1.setText(savedInstanceState.getString("txtNome1"));
            txtTitulo.setText(savedInstanceState.getString("txtTitulo"));
            txtDataDeNascimento1.setText(savedInstanceState.getString("txtDataDeNascimento1"));
            txtPeso1.setText(savedInstanceState.getString("txtPeso1"));

            spnApgar.setSelection(savedInstanceState.getInt("spnApgar"));
            spnLocalDoParto.setSelection(savedInstanceState.getInt("spnLocalDoParto"));
            spnTipoDeParto.setSelection(savedInstanceState.getInt("spnTipoDeParto"));
        }
        vendoDados = savedInstanceState.getBoolean("vendoDados");
        if(vendoDados){
            verDados(crianca1);
        }
    }

    private EditText txtNaturalidade,txtNacionalidade,txtPai,txtMae,txtMorada,txtUnidadeHospitalar,txtResponsavelPeloParto,
            txtNome1,txtDataDeNascimento1,txtPeso1;
    private TextView txtTitulo;
    private Spinner spnApgar,spnTipoDeParto,spnLocalDoParto;
    private RadioButton rbMAsculino,rbFeminino;
    private boolean editando = false;
    private boolean vendoDados = false;

    private void editarDados(@NonNull final Crianca crianca) {
        editando = true;
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.nova_crianca, null);
        txtTitulo = v.findViewById(R.id.txtTitulo);

        txtNome1 = v.findViewById(R.id.txtNome);
        txtDataDeNascimento1 = v.findViewById(R.id.txtDataDeNascimento);
        txtPeso1 = v.findViewById(R.id.txtPeso);
        txtNaturalidade = v.findViewById(R.id.txtNaturalidade);
        txtNacionalidade = v.findViewById(R.id.txtNacionalidade);
        txtPai = v.findViewById(R.id.txtPai);
        txtMae = v.findViewById(R.id.txtMae);
        txtMorada = v.findViewById(R.id.txtMorada);
        txtUnidadeHospitalar = v.findViewById(R.id.txtUnidadeHospitalar);
        txtResponsavelPeloParto = v.findViewById(R.id.txtResponsavelPeloParto);
        rbMAsculino = v.findViewById(R.id.rbMasculino);
        rbFeminino = v.findViewById(R.id.rbFeminino);
        spnApgar = v.findViewById(R.id.spnApgar);
        spnTipoDeParto = v.findViewById(R.id.spnTipoDeParto);
        spnLocalDoParto = v.findViewById(R.id.spnLocalDoParto);
        Button btnSalvar = v.findViewById(R.id.btnSalvar);
        Button btnCancelar = v.findViewById(R.id.btnCancelar);
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate);

        ArrayAdapter adapterApgar = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, new Integer[]{1, 2, 3, 4, 5});
        ArrayAdapter adapterTipoDeParto = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.tipoDeParto));
        ArrayAdapter adapterLocalDoParto = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.LocalDoParto));
        spnTipoDeParto.setAdapter(adapterTipoDeParto);
        spnApgar.setAdapter(adapterApgar);
        spnLocalDoParto.setAdapter(adapterLocalDoParto);

        txtTitulo.setText(R.string.editarDadosDaCrianca);
        txtNome1.setText(crianca.getNome());
        txtDataDeNascimento1.setText(sdf.format(crianca.getDataDeNascimento()));
        txtPeso1.setText(String.valueOf(crianca.getPeso()));
        txtNaturalidade.setText(crianca.getNaturalidade());
        txtNacionalidade.setText(crianca.getNacionalidade());
        txtPai.setText(crianca.getPai());
        txtMae.setText(crianca.getMae());
        txtMorada.setText(crianca.getMorada());
        txtUnidadeHospitalar.setText(crianca.getUnidadeHOspitalar());
        txtResponsavelPeloParto.setText(crianca.getResponsavelPeloParto());

        if (crianca.getSexo().equalsIgnoreCase(Sexo.MASCULINO)) {
            rbMAsculino.setChecked(true);
        } else {
            rbFeminino.setChecked(true);
        }

        spnLocalDoParto.setSelection(adapterLocalDoParto.getPosition(crianca.getLocalDoParto()));
        spnApgar.setSelection(adapterApgar.getPosition(crianca.getApgar()));
        spnTipoDeParto.setSelection(adapterTipoDeParto.getPosition(crianca.getTipoDeParto()));

        dialog.setView(v);
        final android.support.v7.app.AlertDialog ab = dialog.create();


        final DataPickerListnerData listnerDataDoVeiculo = new DataPickerListnerData(txtDataDeNascimento1, crianca.getDataDeNascimento());
        txtDataDeNascimento1.setOnFocusChangeListener(listnerDataDoVeiculo);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crianca c = crianca;
                c.setNome(txtNome1.getText().toString());
                //c.setDataDeNascimento(listnerDataDoVeiculo.getDataObtida().getTime());
                c.setMae(txtMae.getText().toString());
                c.setPai(txtPai.getText().toString());
                c.setMorada(txtMorada.getText().toString());
                c.setNacionalidade(txtNacionalidade.getText().toString());
                c.setNaturalidade(txtNaturalidade.getText().toString());
                c.setResponsavelPeloParto(txtResponsavelPeloParto.getText().toString());
                c.setUnidadeHOspitalar(txtUnidadeHospitalar.getText().toString());
                c.setPeso(Float.parseFloat(txtPeso1.getText().toString()));
                c.setLocalDoParto(spnLocalDoParto.getSelectedItem().toString());
                c.setTipoDeParto(spnTipoDeParto.getSelectedItem().toString());
                c.setApgar(Integer.parseInt(spnApgar.getSelectedItem().toString()));

                if (rbMAsculino.isChecked()) {
                    c.setSexo(Sexo.MASCULINO);
                } else {
                    c.setSexo(Sexo.FEMININO);
                }

                try {
                    criancaViewModel.saveCrianca(c);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ab.dismiss();
                editando = false;
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ab.dismiss();
                editando = false;
            }
        });

        ab.setCancelable(false);
        ab.show();
    }

    protected class GetAllVicina extends AsyncTask<Void, Void, List<Vacina>> {
        VacinameRoomDatabase db = VacinameRoomDatabase.getDatabase(getApplication());

        @Override
        protected List<Vacina> doInBackground(Void... voids) {
            return db.vacinaDao().getAllList();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crianca, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdicionaPeso:
                startActivity(new Intent(this, MedidasValoresActivity.class)
                        .putExtra("tipo", Medida.PESO)
                        .putExtra("id", id));
                break;
            case R.id.menuAdicionaAltura:
                startActivity(new Intent(this, MedidasValoresActivity.class)
                        .putExtra("tipo", Medida.ALTURA)
                        .putExtra("id", id));
                break;
            case R.id.menuAdicionaTemperatura:
                startActivity(new Intent(this, MedidasValoresActivity.class)
                        .putExtra("tipo", Medida.TEMPERATURA)
                        .putExtra("id", id));
                break;
            case R.id.menuVerGraficos:
                startActivity(new Intent(CriancaVacinaActivity.this, MedidasActivity.class).putExtra("id", id));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
