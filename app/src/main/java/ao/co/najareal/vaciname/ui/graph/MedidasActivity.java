package ao.co.najareal.vaciname.ui.graph;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.model.AlturaCrianca;
import ao.co.najareal.vaciname.model.PesoCrianca;
import ao.co.najareal.vaciname.model.TemperaturaCrianca;
import ao.co.najareal.vaciname.model.util.Medida;
import ao.co.najareal.vaciname.ui.util.DataPickerListnerData;
import ao.co.najareal.vaciname.ui.util.Dialogos;
import ao.co.najareal.vaciname.viewModel.CriancaViewModel;

public class MedidasActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtPeso, txtAltura, txtTemperatura;

    private CriancaViewModel criancaViewModel;
    private List<PesoCrianca> pesoCriancas;
    private List<AlturaCrianca> alturaCriancas;
    private List<TemperaturaCrianca> temperaturaCriancas;
    private int idCrianca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medidas);
        setTitle(getString(R.string.Graficos));

        txtPeso = findViewById(R.id.txtPeso);
        txtAltura = findViewById(R.id.txtAltura);
        txtTemperatura = findViewById(R.id.txtTemperatura);

        txtPeso.setOnClickListener(this);
        txtAltura.setOnClickListener(this);
        txtTemperatura.setOnClickListener(this);

        criancaViewModel = ViewModelProviders.of(this).get(CriancaViewModel.class);
        // criancaViewModel.setId(idCrianca);

        mostrarDados();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarDados();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void mostrarDados() {
        try {
            idCrianca = getIntent().getIntExtra("id", 0);
            if (idCrianca == 0) {
                onBackPressed();
            }
            pesoCriancas = criancaViewModel.getPesoByIdGrafico(idCrianca);
            alturaCriancas = criancaViewModel.getAlturasById(idCrianca);
            temperaturaCriancas = criancaViewModel.getTemperaturasById(idCrianca);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        graficoPeso(pesoCriancas);
        graficoAltura(alturaCriancas);
        graficoTemperatura(temperaturaCriancas);

        //graficoTodos(pesoCriancas, alturaCriancas, temperaturaCriancas);
        gerarGraficoPesoIdeal();
    }

    public void gerarGraficoPesoIdeal() {

        GraphView graph = (GraphView) findViewById(R.id.grafico3);
        // ----------------------- Peso ideal ----------------
        DataPoint[] paramPesoIdeal = new DataPoint[]{
                new DataPoint(0, 3.5),
                new DataPoint(1, 4),
                new DataPoint(2, 5),
                new DataPoint(3, 5.9),
                new DataPoint(4, 6.6),
                new DataPoint(5, 7.1),
                new DataPoint(6, 7.8),
                new DataPoint(7, 8.1),
                new DataPoint(8, 8.5),
                new DataPoint(9, 9)
        };
        LineGraphSeries<DataPoint> seriesPesoIdeal = new LineGraphSeries<DataPoint>(paramPesoIdeal);
        // ----------------------- Peso ideal ----------------


        // ----------------------- Peso Alerta ----------------
        DataPoint[] paramPesoAlerta = new DataPoint[]{
                new DataPoint(0, 2.5),
                new DataPoint(1, 2),
                new DataPoint(2, 3),
                new DataPoint(3, 3.9),
                new DataPoint(4, 4.6),
                new DataPoint(5, 5.1),
                new DataPoint(6, 5.8),
                new DataPoint(7, 6.1),
                new DataPoint(8, 6.5),
                new DataPoint(9, 7)
        };
        LineGraphSeries<DataPoint> seriesPesoAlerta = new LineGraphSeries<DataPoint>(paramPesoAlerta);
        // ----------------------- Peso Alerta ----------------


        // ----------------------- Peso Perigo ----------------
        DataPoint[] paramPesoPerigo = new DataPoint[]{
                new DataPoint(0, 0.5),
                new DataPoint(1, 1),
                new DataPoint(2, 2),
                new DataPoint(3, 2.9),
                new DataPoint(4, 3.6),
                new DataPoint(5, 3.1),
                new DataPoint(6, 4.8),
                new DataPoint(7, 5.1),
                new DataPoint(8, 5.5),
                new DataPoint(9, 6)
        };
        LineGraphSeries<DataPoint> seriesPesoPerigo = new LineGraphSeries<DataPoint>(paramPesoPerigo);
        // ----------------------- Peso Perigo ----------------


        gerarGrafico3Linhas(1, 9, graph, seriesPesoIdeal, seriesPesoAlerta, seriesPesoPerigo);
    }

    private void graficoTodos(List<PesoCrianca> pesos, List<AlturaCrianca> alturas, List<TemperaturaCrianca> temperaturas) {

        // datas de inicio e fim do grafico
        Date date = new Date(pesos.get(0).getData());
        date.setDate(date.getDate() - 1);
        Date date4 = new Date(pesos.get(pesos.size() - 1).getData());
        date4.setDate(date4.getDate() + 1);

        final java.text.DateFormat dateTimeFormatter = DateFormat.getTimeFormat(this);

        // ----------------------- Peso ----------------
        DataPoint[] paramPeso = new DataPoint[pesos.size()];
        int i = 0;
        for (PesoCrianca p : pesos) {
            paramPeso[i++] = new DataPoint(p.getData(), p.getPeso());
        }
        LineGraphSeries<DataPoint> seriesPeso = new LineGraphSeries<>();
        if (paramPeso.length > 0) {
            seriesPeso = new LineGraphSeries<DataPoint>(paramPeso);
        }
        // ----------------------- Peso ----------------

        // ----------------------- Altura ----------------
        DataPoint[] paramAltura = new DataPoint[alturas.size()];
        i = 0;
        for (AlturaCrianca p : alturas) {
            paramAltura[i++] = new DataPoint(p.getData(), p.getAltura());
        }
        LineGraphSeries<DataPoint> seriesAlturas = new LineGraphSeries<>();
        if (paramAltura.length > 0) {
            seriesAlturas = new LineGraphSeries<DataPoint>(paramAltura);
        }
        // ----------------------- Altura ----------------

        // ----------------------- Temperatura ----------------
        DataPoint[] paramTemperatura = new DataPoint[pesos.size()];
        i = 0;
        for (PesoCrianca p : pesos) {
            paramTemperatura[i++] = new DataPoint(p.getData(), p.getPeso());
        }
        LineGraphSeries<DataPoint> seriesTemperratura = new LineGraphSeries<>();
        if (paramTemperatura.length > 0) {
            seriesTemperratura = new LineGraphSeries<DataPoint>(paramTemperatura);
        }
        // ----------------------- Temperatura ----------------


        GraphView graph = (GraphView) findViewById(R.id.grafico3);

        gerarGrafico3Linhas(date, date4, graph, seriesPeso, seriesAlturas, seriesTemperratura);
    }

    private void graficoPeso(List<PesoCrianca> pesos) {

        DataPoint[] param;
        param = new DataPoint[pesos.size()];

        int i = 0;
        for (PesoCrianca p : pesos) {
            param[i++] = new DataPoint(p.getData(), p.getPeso());
        }

        if (param.length < 1) {
            return;
        }
        // datas de inicio e fim do grafico
        Date date = new Date(pesos.get(0).getData());
        date.setDate(date.getDate() - 1);
        Date date4 = new Date(pesos.get(pesos.size() - 1).getData());
        date4.setDate(date4.getDate() + 1);

        final java.text.DateFormat dateTimeFormatter = DateFormat.getTimeFormat(this);


        GraphView graph = (GraphView) findViewById(R.id.graficoPeso);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(param);
        gerarGrafico(date, date4, graph, series);
    }

    private void graficoAltura(List<AlturaCrianca> alturas) {

        DataPoint[] param = new DataPoint[alturas.size()];

        int i = 0;
        for (AlturaCrianca p : alturas) {
            param[i++] = new DataPoint(p.getData(), p.getAltura());
        }

        if (param.length < 1) {
            return;
        }
        // datas de inicio e fim do grafico
        Date date = new Date(alturas.get(0).getData());
        date.setDate(date.getDate() - 1);
        Date date4 = new Date(alturas.get(alturas.size() - 1).getData());
        date4.setDate(date4.getDate() + 1);

        final java.text.DateFormat dateTimeFormatter = DateFormat.getTimeFormat(this);


        GraphView graph = (GraphView) findViewById(R.id.graficoAltura);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(param);
        gerarGrafico(date, date4, graph, series);
    }

    private void graficoTemperatura(List<TemperaturaCrianca> temperaturas) {

        DataPoint[] param = new DataPoint[temperaturas.size()];

        int i = 0;
        for (TemperaturaCrianca p : temperaturas) {
            param[i++] = new DataPoint(p.getData(), p.getTemperatura());
        }

        if (param.length < 1) {
            return;
        }
        // datas de inicio e fim do grafico
        Date date = new Date(temperaturas.get(0).getData());
        date.setDate(date.getDate() - 1);
        Date date4 = new Date(temperaturas.get(temperaturas.size() - 1).getData());
        date4.setDate(date4.getDate() + 1);

        final java.text.DateFormat dateTimeFormatter = DateFormat.getTimeFormat(this);


        GraphView graph = (GraphView) findViewById(R.id.graficoTemperatura);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(param);
        gerarGrafico(date, date4, graph, series);
    }

    private void gerarGrafico(Date date, Date date4, GraphView graph, LineGraphSeries<DataPoint> series) {

        graph.removeAllSeries();
        graph.addSeries(series);

        int mNumLabels = 6;
        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new MedidasDateAsXAxisLabelFormatter(graph.getContext()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(mNumLabels);

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(date.getTime());
        graph.getViewport().setMaxX(date4.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not nessecary
        graph.getGridLabelRenderer().setHumanRounding(false);
    }

    private void gerarGrafico3Linhas(Date date, Date date4, GraphView graph, LineGraphSeries<DataPoint> series,
                                     LineGraphSeries<DataPoint> series1, LineGraphSeries<DataPoint> series2) {


        series.setColor(getResources().getColor(R.color.colorRed));
        series1.setColor(getResources().getColor(R.color.colorGreen));
        series2.setColor(getResources().getColor(R.color.colorYellow));

        graph.removeAllSeries();
        graph.addSeries(series);
        graph.addSeries(series1);
        graph.addSeries(series2);

        graph.setTitle("Peso Ideal");


        int mNumLabels = 6;
        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new MedidasDateAsXAxisLabelFormatter(graph.getContext()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(mNumLabels);

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(date.getTime());
        graph.getViewport().setMaxX(date4.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not nessecary
        graph.getGridLabelRenderer().setHumanRounding(false);
    }


    private void gerarGrafico3Linhas(long inicial, long finall, GraphView graph, LineGraphSeries<DataPoint> series,
                                     LineGraphSeries<DataPoint> series1, LineGraphSeries<DataPoint> series2) {


        series.setColor(getResources().getColor(R.color.colorGreen));
        series1.setColor(getResources().getColor(R.color.colorYellow));
        series2.setColor(getResources().getColor(R.color.colorRed));

        graph.removeAllSeries();
        graph.addSeries(series);
        graph.addSeries(series1);
        graph.addSeries(series2);

        // graph.setTitle("Peso Ideal");


        int mNumLabels = 6;
        graph.getGridLabelRenderer().setNumHorizontalLabels(mNumLabels);

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(inicial - 1);
        graph.getViewport().setMaxX(finall + 1);
        graph.getViewport().setXAxisBoundsManual(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_medidas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdicionaPeso:
                if (idCrianca > 0) {
                    addPeso(idCrianca);
                } else {
                    Toast.makeText(this, R.string.selecionaUmaCrianca, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menuAdicionaAltura:
                if (idCrianca > 0) {
                    addAltura(idCrianca);
                } else {
                    Toast.makeText(this, R.string.selecionaUmaCrianca, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menuAdicionaTemperatura:
                if (idCrianca > 0) {
                    addTemperatura(idCrianca);
                } else {
                    Toast.makeText(this, R.string.selecionaUmaCrianca, Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtPeso:
                startActivity(new Intent(this, MedidasValoresActivity.class)
                        .putExtra("tipo", Medida.PESO)
                        .putExtra("id", idCrianca));
                break;
            case R.id.txtAltura:

                startActivity(new Intent(this, MedidasValoresActivity.class)
                        .putExtra("tipo", Medida.ALTURA)
                        .putExtra("id", idCrianca));
                break;
            case R.id.txtTemperatura:

                startActivity(new Intent(this, MedidasValoresActivity.class)
                        .putExtra("tipo", Medida.TEMPERATURA)
                        .putExtra("id", idCrianca));
                break;
        }
    }


    private void addPeso(final int id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.add_peso, null);
        final EditText txtData = v.findViewById(R.id.txtData);
        final EditText txtPeso = v.findViewById(R.id.txtPeso);
        Button btnSalvar = v.findViewById(R.id.btnSalvar);
        Button btnCancelar = v.findViewById(R.id.btnCancelar);
        final DataPickerListnerData listnerDataDoVeiculo = new DataPickerListnerData(txtData, null);
        txtData.setOnFocusChangeListener(listnerDataDoVeiculo);

        dialog.setView(v);
        final AlertDialog ab = dialog.create();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PesoCrianca c = new PesoCrianca();
                c.setIdCrianca(id);
                c.setData(listnerDataDoVeiculo.getDataObtida().getTime());
                if (txtPeso.getText().toString().isEmpty()) {
                    Dialogos.validar(v.getContext(), "Validar Peso", "Informe o Peso", null, null, "OK", null);
                    return;
                }
                c.setPeso(Float.parseFloat(txtPeso.getText().toString()));

                if (c.getPeso() < 1) {
                    Dialogos.validar(v.getContext(), "O peso deve ser maior que Zero", "O peso deve ser maior que Zero", null, null, "OK", null);
                    return;
                }
                if (c.getData() > new Date().getTime()) {
                    Dialogos.validar(v.getContext(), "A data não pode ser a futuro", "O peso deve ser maior que Zero", null, null, "OK", null);
                    return;
                }
                criancaViewModel.save(c);
                ab.dismiss();
                mostrarDados();
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


    private void addAltura(final int id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.add_altura, null);
        final EditText txtData = v.findViewById(R.id.txtData);
        final EditText txtAltura = v.findViewById(R.id.txtAltura);
        Button btnSalvar = v.findViewById(R.id.btnSalvar);
        Button btnCancelar = v.findViewById(R.id.btnCancelar);
        final DataPickerListnerData listnerDataDoVeiculo = new DataPickerListnerData(txtData, null);
        txtData.setOnFocusChangeListener(listnerDataDoVeiculo);

        dialog.setView(v);
        final AlertDialog ab = dialog.create();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlturaCrianca c = new AlturaCrianca();
                c.setIdCrianca(id);
                c.setData(listnerDataDoVeiculo.getDataObtida().getTime());
                if (txtAltura.getText().toString().isEmpty()) {
                    Dialogos.validar(v.getContext(), "Validar Altura", "Informe a altura", null, null, "OK", null);
                    return;
                }
                c.setAltura(Float.parseFloat(txtAltura.getText().toString()));

                if (c.getAltura() < 1) {
                    Dialogos.validar(v.getContext(), "A altura deve ser maior que Zero", "", null, null, "OK", null);
                    return;
                }
                if (c.getData() > new Date().getTime()) {
                    Dialogos.validar(v.getContext(), "A data não pode ser a futuro", "", null, null, "OK", null);
                    return;
                }
                float alturaAnterior = 0; // BUscar da base de dados
                float alturaMaxima = 0; // BUscar da base de dados
/*
                try {
                    AlturaCrianca alturaMaxima12 = criancaViewModel.getAlturaMaxima(c.getIdCrianca());

                    if (alturaMaxima12 != null) {
                        alturaMaxima = alturaMaxima12.getAltura();

                        if (alturaMaxima < c.getAltura() && c.getData() < alturaMaxima) {
                            Dialogos.validar(v.getContext(), "A altura não pode ser maior que a maior altura ja registada (" + alturaMaxima + ")", "", null, null, "OK", null);
                            return;
                        }
                    }
                    AlturaCrianca alturaCrianca = criancaViewModel.getAlturaAnterior(c.getIdCrianca(), new Date().getTime());
                    if (alturaCrianca != null) {
                        alturaAnterior = alturaCrianca.getAltura();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (alturaAnterior > c.getAltura()) {
                    Dialogos.validar(v.getContext(), "A altura deve ser crescente diminuiu " + (alturaAnterior - c.getAltura()) + " em relação a anterior", "", null, null, "OK", null);
                    return;
                }*/
                criancaViewModel.save(c);
                ab.dismiss();
                mostrarDados();
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


    private void addTemperatura(final int id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.add_temperatura, null);
        final EditText txtData = v.findViewById(R.id.txtData);
        final EditText txtTemperatura = v.findViewById(R.id.txtTemperatura);
        Button btnSalvar = v.findViewById(R.id.btnSalvar);
        Button btnCancelar = v.findViewById(R.id.btnCancelar);
        final DataPickerListnerData listnerDataDoVeiculo = new DataPickerListnerData(txtData, null);
        txtData.setOnFocusChangeListener(listnerDataDoVeiculo);

        dialog.setView(v);
        final AlertDialog ab = dialog.create();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TemperaturaCrianca c = new TemperaturaCrianca();
                c.setIdCrianca(id);
                c.setData(listnerDataDoVeiculo.getDataObtida().getTime());
                if (txtTemperatura.getText().toString().isEmpty()) {
                    Dialogos.validar(v.getContext(), "Validar Temperatura", "Informe a temperatura", null, null, "OK", null);
                    return;
                }
                c.setTemperatura(Float.parseFloat(txtTemperatura.getText().toString()));

                if (c.getTemperatura() < 1) {
                    Dialogos.validar(v.getContext(), "A altura deve ser maior que Zero", "", null, null, "OK", null);
                    return;
                }
                if (c.getData() > new Date().getTime()) {
                    Dialogos.validar(v.getContext(), "A data não pode ser a futuro", "", null, null, "OK", null);
                    return;
                }
                criancaViewModel.save(c);
                ab.dismiss();
                mostrarDados();
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

    protected class MedidasDateAsXAxisLabelFormatter extends DateAsXAxisLabelFormatter {

        public MedidasDateAsXAxisLabelFormatter(Context context) {
            super(context);
        }

        public MedidasDateAsXAxisLabelFormatter(Context context, java.text.DateFormat dateFormat) {
            super(context, dateFormat);
        }

        @Override
        public String formatLabel(double value, boolean isValueX) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
            if (isValueX) {
                // format as date
                //mCalendar.setTimeInMillis((long) value);
                return sdf.format(value);
            } else {
                return super.formatLabel(value, isValueX);
            }
        }
    }
}
