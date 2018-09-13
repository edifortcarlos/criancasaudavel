package ao.co.najareal.vaciname.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.model.TemperaturaCrianca;
import ao.co.najareal.vaciname.ui.util.DataPickerListnerDataDoVeiculo;
import ao.co.najareal.vaciname.viewModel.CriancaViewModel;

public class TemperaturaAdapter extends RecyclerView.Adapter<TemperaturaAdapter.TemperaturaViewHolder> {

    private Context context;
    private List<TemperaturaCrianca> temperaturas;
    private CriancaViewModel criancaViewModel;

    public TemperaturaAdapter(Context context, CriancaViewModel criancaViewModel) {
        this.criancaViewModel = criancaViewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public TemperaturaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_medida, parent, false);
        return new TemperaturaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TemperaturaViewHolder holder, int position) {
        holder.bind(temperaturas.get(position));
    }

    @Override
    public int getItemCount() {
        if (temperaturas == null)
            return 0;
        return temperaturas.size();
    }

    public void setTemperaturas(List<TemperaturaCrianca> temperaturas) {
        this.temperaturas = temperaturas;
        notifyDataSetChanged();
    }

    protected class TemperaturaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtValor, txtData;
        private ImageView imgEditar;
        private TemperaturaCrianca c;

        public TemperaturaViewHolder(View itemView) {
            super(itemView);
            txtValor = itemView.findViewById(R.id.txtValor);
            txtData = itemView.findViewById(R.id.txtData);
            itemView.setOnClickListener(this);
            imgEditar = itemView.findViewById(R.id.imgEditar);
            imgEditar.setOnClickListener(this);

        }

        public void bind(TemperaturaCrianca c) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.c = c;
            txtValor.setText(String.valueOf(this.c.getTemperatura()));
            txtData.setText(sdf.format(this.c.getData()));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgDelete:
                    break;
                case R.id.imgEditar:
                    editarDados(c);
                    break;
            }
        }
    }

    private void editarDados(@NonNull final TemperaturaCrianca crianca) {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.add_temperatura, null);
        final EditText txtTemperatura = v.findViewById(R.id.txtTemperatura);
        final EditText txtData = v.findViewById(R.id.txtData);
        Button btnSalvar = v.findViewById(R.id.btnSalvar);
        Button btnCancelar = v.findViewById(R.id.btnCancelar);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        txtTemperatura.setText(String.valueOf(crianca.getTemperatura()));
        txtData.setText(sdf.format(crianca.getData()));

        dialog.setView(v);
        final android.support.v7.app.AlertDialog ab = dialog.create();

        final DataPickerListnerDataDoVeiculo listnerDataDoVeiculo = new DataPickerListnerDataDoVeiculo(txtData, crianca.getData());
        txtData.setOnFocusChangeListener(listnerDataDoVeiculo);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TemperaturaCrianca c = crianca;
                c.setTemperatura(Float.parseFloat(txtTemperatura.getText().toString()));
                c.setData(listnerDataDoVeiculo.getDataObtida().getTime());
                criancaViewModel.save(c);
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
}
