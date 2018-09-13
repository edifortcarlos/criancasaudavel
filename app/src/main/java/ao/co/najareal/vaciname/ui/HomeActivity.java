package ao.co.najareal.vaciname.ui;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.model.Crianca;
import ao.co.najareal.vaciname.model.util.Sexo;
import ao.co.najareal.vaciname.ui.fragmentos.Listagem_Cartao_Vacina_Fragment;
import ao.co.najareal.vaciname.ui.fragmentos.Plano_Nacional_Vacina_Fragment;
import ao.co.najareal.vaciname.ui.fragmentos.SectionsPagerAdapter;
import ao.co.najareal.vaciname.ui.info.BemVindo;
import ao.co.najareal.vaciname.ui.info.PolicyPrivacyActivity;
import ao.co.najareal.vaciname.ui.info.SobreActivity;
import ao.co.najareal.vaciname.ui.util.DataPickerListnerDataDoVeiculo;
import ao.co.najareal.vaciname.viewModel.CriancaViewModel;

public class HomeActivity extends AppCompatActivity {

    public ViewPager mViewPager;
    public TabLayout mTabLayout;
    private AnimationDrawable animationDrawable;
    private ImageView imageView;

    private CriancaViewModel criancaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        criancaViewModel = ViewModelProviders.of(this).get(CriancaViewModel.class);
        imageView = findViewById(R.id.voa);
        imageView.setBackgroundResource(R.drawable.animacao);
        animationDrawable = (AnimationDrawable) imageView.getBackground();

        mViewPager = findViewById(R.id.main_viewpager);
        mTabLayout = findViewById(R.id.main_tablayout);
        mTabLayout.setupWithViewPager(mViewPager);
        metodo_list_fragment();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertNovaCrianca();
            }
        });

    }

    private void init() {


    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCampanha:
                startActivity(new Intent(this, CampanhaActivity.class));
                return true;
            case R.id.menuTour:
                startActivity(new Intent(this, BemVindo.class).putExtra("pedido", true));
                return true;
            case R.id.menuSobre:
                startActivity(new Intent(this, SobreActivity.class));
                return true;
            case R.id.menuPoliticaPrivacidade:
                startActivity(new Intent(this, PolicyPrivacyActivity.class));
                return true;
            case R.id.menuSugestoes:
                if (verifyPermitions()) {
                    suggestion();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void metodo_list_fragment() {
        SectionsPagerAdapter sectionsAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsAdapter.adcionaFragment(new Listagem_Cartao_Vacina_Fragment());
        sectionsAdapter.adcionaFragment(new Plano_Nacional_Vacina_Fragment());
        mViewPager.setAdapter(sectionsAdapter);
    }

    private void validarCrianca(String msg) {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.modal_alert, null);
        final TextView txtDescricao = v.findViewById(R.id.txtDescricao);
        final TextView txtQuestao = v.findViewById(R.id.txtTitulo);
        TextView txtSim = v.findViewById(R.id.txtSim);
        TextView txtNao = v.findViewById(R.id.txtNao);

        txtSim.setText("OK");
        txtQuestao.setText(msg);

        dialog.setView(v);
        final android.app.AlertDialog ab = dialog.create();

        txtSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ab.dismiss();
            }
        });

        txtNao.setVisibility(View.GONE);

        ab.show();
    }

    private boolean validarCrianca(Crianca c) {

        boolean valida = true;
        String msg = "";

        if (c.getNome().isEmpty()) {
            msg += "\n" + getString(R.string.preencherNomeDoCartao);
            valida &= false;
        }

        if (c.getNome().indexOf(" ") < 0) {
            msg += "\n" + getString(R.string.informeDoisNomes);
            valida &= false;
        }


        if (c.getPeso() < 1) {
            msg += "\n" + getString(R.string.InformePesoValido);
            valida &= false;
        }

        if (c.getPeso() > 6) {
            msg += "\n" + getString(R.string.verifiqueOPesoRecemNascido2_5Kg);
            valida &= false;
        }

        /*
        if (c.getDataDeNascimento() < ((2018 - 1900) * 31_536_000_000L)) {
            msg += "\n" + getString(R.string.InformeUmaDataValida);
            valida &= false;
        }*/

        if (c.getDataDeNascimento() > new Date().getTime()) {
            msg += "\n" + getString(R.string.DataDeNascimentoDeveSerAnteriorADataActual);
            valida &= false;
        }

        if (c.getPai().isEmpty() && c.getMae().isEmpty()) {
            msg += "\n" + getString(R.string.informePaiOuMae);
            valida &= false;
        }


        if (c.getMorada().isEmpty()) {
            msg += "\n" + getString(R.string.informeAMorada);
            valida &= false;
        }


        if (c.getLocalDoParto().equalsIgnoreCase(getResources().getString(R.string.noHospital))
                && c.getUnidadeHOspitalar().isEmpty()) {
            msg += "\n" + getString(R.string.informeAUnidadeHospitalar);
            valida &= false;
        }
        if (!msg.isEmpty()) {
            validarCrianca(msg);
        }
        return valida;
    }

    private void alertNovaCrianca() {


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.nova_crianca, null);
        final EditText txtNome = v.findViewById(R.id.txtNome);
        final EditText txtDataDeNascimento = v.findViewById(R.id.txtDataDeNascimento);
        final EditText txtPeso = v.findViewById(R.id.txtPeso);
        final EditText txtNaturalidade = v.findViewById(R.id.txtNaturalidade);
        final EditText txtNacionalidade = v.findViewById(R.id.txtNacionalidade);
        final EditText txtPai = v.findViewById(R.id.txtPai);
        final EditText txtMae = v.findViewById(R.id.txtMae);
        final EditText txtMorada = v.findViewById(R.id.txtMorada);
        final EditText txtUnidadeHospitalar = v.findViewById(R.id.txtUnidadeHospitalar);
        final EditText txtResponsavelPeloParto = v.findViewById(R.id.txtResponsavelPeloParto);
        final RadioButton rbMAsculino = v.findViewById(R.id.rbMasculino);
        final Spinner spnApgar = v.findViewById(R.id.spnApgar);
        final Spinner spnLocalDoParto = v.findViewById(R.id.spnLocalDoParto);
        final Spinner spnTipoDeParto = v.findViewById(R.id.spnTipoDeParto);
        Button btnSalvar = v.findViewById(R.id.btnSalvar);
        Button btnCancelar = v.findViewById(R.id.btnCancelar);

        ArrayAdapter adapterApgar = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, new Integer[]{1, 2, 3, 4, 5});
        ArrayAdapter adapterTipoDeParto = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.tipoDeParto));
        ArrayAdapter adapterLocalDoParto = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.LocalDoParto));
        spnTipoDeParto.setAdapter(adapterTipoDeParto);
        spnApgar.setAdapter(adapterApgar);
        spnLocalDoParto.setAdapter(adapterLocalDoParto);


        dialog.setView(v);
        final AlertDialog ab = dialog.create();


        final DataPickerListnerDataDoVeiculo listnerDataDoVeiculo = new DataPickerListnerDataDoVeiculo(txtDataDeNascimento, null);
        txtDataDeNascimento.setOnFocusChangeListener(listnerDataDoVeiculo);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crianca c = new Crianca();
                c.setNome(txtNome.getText().toString());
                c.setDataDeNascimento(listnerDataDoVeiculo.getDataObtida().getTime());
                c.setMae(txtMae.getText().toString());
                c.setPai(txtPai.getText().toString());
                c.setMorada(txtMorada.getText().toString());
                c.setNacionalidade(txtNacionalidade.getText().toString());
                c.setNaturalidade(txtNaturalidade.getText().toString());
                c.setResponsavelPeloParto(txtResponsavelPeloParto.getText().toString());
                c.setUnidadeHOspitalar(txtUnidadeHospitalar.getText().toString());
                if (!txtPeso.getText().toString().isEmpty()) {
                    c.setPeso(Float.parseFloat(txtPeso.getText().toString()));
                }
                c.setLocalDoParto(spnLocalDoParto.getSelectedItem().toString());
                c.setTipoDeParto(spnTipoDeParto.getSelectedItem().toString());
                c.setApgar(Integer.parseInt(spnApgar.getSelectedItem().toString()));

                if (rbMAsculino.isChecked()) {
                    c.setSexo(Sexo.MASCULINO);
                } else {
                    c.setSexo(Sexo.FEMININO);
                }

                try {
                    if (!validarCrianca(c)) {
                        return;
                    }
                    criancaViewModel.saveCrianca(c);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ab.dismiss();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ab.dismiss();
            }
        });

        ab.show();
    }


    private boolean hasPermitions = true;
    private final int REQUEST_PERMISSION = 0;

    private void askPermitions() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.SEND_SMS
                }, REQUEST_PERMISSION);
    }


    // retorna true se todas permissoes forem concedidas
    private boolean verifyPermitions() {
        hasPermitions = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;

        if (!hasPermitions) {
            askPermitions();
        }
        return hasPermitions;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    suggestion();
                } else {
                    Toast.makeText(this, "Para enviar sugestões de as permissões necessarias ao aplicativo", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    private void suggestion() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.model_suggestion, null);
        ab.setView(v);

        final RadioButton rbSuggestion = v.findViewById(R.id.rbSuggestion);
        final RadioButton rbComplaint = v.findViewById(R.id.rbComplaint);
        final RadioButton rbSMS = v.findViewById(R.id.rbBySMS);
        final RadioButton rbByMessage = v.findViewById(R.id.rbByMessage);
        Button btnCancel = v.findViewById(R.id.btnCancel);
        Button btnSend = v.findViewById(R.id.btnOk);
        final EditText txtText = v.findViewById(R.id.txtContent3);

        final AlertDialog a = ab.create();


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.dismiss();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((rbByMessage.isChecked() || rbSMS.isChecked()) && (rbSuggestion.isChecked() || rbComplaint.isChecked())) {
                    if (TextUtils.isEmpty(txtText.getText().toString())) {
                        Toast.makeText(HomeActivity.this, "Digite a susa Sugestão/Reclamação", Toast.LENGTH_SHORT).show();
                    } else {
                        if (rbSMS.isChecked()) {
                            String type = rbSuggestion.isChecked() ? "Sugestão" : "Reclamação";
                            String number = "00244937304095";
                            String smsText = "Corpo Saudavel - " + type + "\n" + txtText.getText().toString();

                            sendSMS(number, smsText);

                            a.dismiss();
                        } else if (rbByMessage.isChecked()) {
                            Toast.makeText(HomeActivity.this, "Opção indisponivel", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "É uma sugestão ?", Toast.LENGTH_SHORT).show();
                }
            }
        });
        a.show();
    }

    // Envio de Sugestão
    public void sendSMS(String numero, String texto) {

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numero, null, texto, null, null);
        Toast.makeText(this, R.string.enviandoSugestao, Toast.LENGTH_LONG).show();
    }

}
