package ao.co.najareal.vaciname.adapters;

import android.app.AlertDialog;
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
import ao.co.najareal.vaciname.model.PesoCrianca;
import ao.co.najareal.vaciname.ui.util.DataPickerListnerData;
import ao.co.najareal.vaciname.viewModel.CriancaViewModel;

public class PesoAdapter extends RecyclerView.Adapter<PesoAdapter.PesoViewHolder> {

    private Context context;
    private List<PesoCrianca> pesos;
    private CriancaViewModel rep;

    public PesoAdapter(Context context, CriancaViewModel rep) {
        this.rep = rep;
        this.context = context;
    }

    @NonNull
    @Override
    public PesoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_medida, parent, false);
        return new PesoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PesoViewHolder holder, int position) {
        holder.bind(pesos.get(position));
    }

    @Override
    public int getItemCount() {
        if (pesos == null)
            return 0;
        return pesos.size();
    }

    public void setPesos(List<PesoCrianca> pesos) {
        this.pesos = pesos;
        notifyDataSetChanged();
    }

    protected class PesoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtValor, txtData;
        private ImageView imgEditar,imgDelete;
        private PesoCrianca c;

        public PesoViewHolder(View itemView) {
            super(itemView);
            txtValor = itemView.findViewById(R.id.txtValor);
            txtData = itemView.findViewById(R.id.txtData);
            imgEditar = itemView.findViewById(R.id.imgEditar);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEditar.setOnClickListener(this);
            imgDelete.setOnClickListener(this);

        }

        public void bind(PesoCrianca c) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.c = c;
            txtValor.setText(String.valueOf(this.c.getPeso()));
            txtData.setText(sdf.format(this.c.getData()));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgDelete:
                    apagarPeso(c);
                    break;
                case R.id.imgEditar:
                    editarDados(c);
                    break;
            }
        }
    }


    private void apagarPeso(@NonNull final PesoCrianca crianca){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.modal_alert, null);
        final TextView txtDescricao = v.findViewById(R.id.txtDescricao);
        final TextView txtQuestao = v.findViewById(R.id.txtTitulo);
        TextView txtSim = v.findViewById(R.id.txtSim);
        TextView txtNao = v.findViewById(R.id.txtNao);

        txtDescricao.setText(R.string.apagarPesoDefinitivo);
        txtQuestao.setText(R.string.desejaApagarOPeso);

        dialog.setView(v);
        final AlertDialog ab = dialog.create();

        txtSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rep.delete(crianca);
                notifyDataSetChanged();
                ab.dismiss();
            }
        });

        txtNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ab.dismiss();
            }
        });

        ab.show();
    }


    private void editarDados(@NonNull final PesoCrianca crianca) {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.add_peso, null);
        final EditText txtPeso = v.findViewById(R.id.txtPeso);
        final EditText txtData = v.findViewById(R.id.txtData);
        Button btnSalvar = v.findViewById(R.id.btnSalvar);
        Button btnCancelar = v.findViewById(R.id.btnCancelar);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        txtPeso.setText(String.valueOf(crianca.getPeso()));
        txtData.setText(sdf.format(crianca.getData()));

        dialog.setView(v);
        final android.support.v7.app.AlertDialog ab = dialog.create();

        final DataPickerListnerData listnerDataDoVeiculo = new DataPickerListnerData(txtData, crianca.getData());
        txtData.setOnFocusChangeListener(listnerDataDoVeiculo);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PesoCrianca c = crianca;
                c.setPeso(Float.parseFloat(txtPeso.getText().toString()));
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
