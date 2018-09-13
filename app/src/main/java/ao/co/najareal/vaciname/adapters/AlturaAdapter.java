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
import ao.co.najareal.vaciname.model.AlturaCrianca;
import ao.co.najareal.vaciname.ui.util.DataPickerListnerDataDoVeiculo;
import ao.co.najareal.vaciname.viewModel.CriancaViewModel;

public class AlturaAdapter extends RecyclerView.Adapter<AlturaAdapter.AlturaViewHolder> {

    private Context context;
    private List<AlturaCrianca> alturas;
    private CriancaViewModel rep;

    public AlturaAdapter(Context context, CriancaViewModel rep) {
        this.rep = rep;
        this.context = context;
    }

    @NonNull
    @Override
    public AlturaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_medida, parent, false);
        return new AlturaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlturaViewHolder holder, int position) {
        holder.bind(alturas.get(position));
    }

    @Override
    public int getItemCount() {
        if (alturas == null)
            return 0;
        return alturas.size();
    }

    public void setAlturas(List<AlturaCrianca> alturas) {
        this.alturas = alturas;
        notifyDataSetChanged();
    }

    protected class AlturaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtValor, txtData;
        private ImageView imgEditar;
        private AlturaCrianca c;

        public AlturaViewHolder(View itemView) {
            super(itemView);
            txtValor = itemView.findViewById(R.id.txtValor);
            txtData = itemView.findViewById(R.id.txtData);
            itemView.setOnClickListener(this);

            imgEditar = itemView.findViewById(R.id.imgEditar);
            imgEditar.setOnClickListener(this);
        }

        public void bind(AlturaCrianca c) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.c = c;
            txtValor.setText(String.valueOf(this.c.getAltura()));
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

    private void editarDados(@NonNull final AlturaCrianca crianca) {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.add_altura, null);
        final EditText txtAltura = v.findViewById(R.id.txtAltura);
        final EditText txtData = v.findViewById(R.id.txtData);
        Button btnSalvar = v.findViewById(R.id.btnSalvar);
        Button btnCancelar = v.findViewById(R.id.btnCancelar);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        txtAltura.setText(String.valueOf(crianca.getAltura()));
        txtData.setText(sdf.format(crianca.getData()));

        dialog.setView(v);
        final android.support.v7.app.AlertDialog ab = dialog.create();

        final DataPickerListnerDataDoVeiculo listnerDataDoVeiculo = new DataPickerListnerDataDoVeiculo(txtData, crianca.getData());
        txtData.setOnFocusChangeListener(listnerDataDoVeiculo);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlturaCrianca c = crianca;
                c.setAltura(Float.parseFloat(txtAltura.getText().toString()));
                c.setData(listnerDataDoVeiculo.getDataObtida().getTime());
                rep.save(c);
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
