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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.model.CriancaVacina;
import ao.co.najareal.vaciname.model.Vacina;
import ao.co.najareal.vaciname.model.util.VacinaEnums;
import ao.co.najareal.vaciname.ui.util.DataPickerListnerData;
import ao.co.najareal.vaciname.viewModel.CriancaViewModel;
import ao.co.najareal.vaciname.viewModel.VacinaViewModel;

public class CriancaVacinaAdapter extends RecyclerView.Adapter<CriancaVacinaAdapter.CriancaVacinaViewHolder> {

    private Context context;
    private List<CriancaVacina> criancas;
    private VacinaViewModel vacinaViewModel;
    private CriancaViewModel criancaViewModel;

    public CriancaVacinaAdapter(Context context, VacinaViewModel vacinaViewModel, CriancaViewModel criancaViewModel) {
        this.context = context;
        this.vacinaViewModel = vacinaViewModel;
        this.criancaViewModel = criancaViewModel;
    }

    @NonNull
    @Override
    public CriancaVacinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_crianca_vacina, parent, false);
        return new CriancaVacinaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CriancaVacinaViewHolder holder, int position) {
        holder.bind(criancas.get(position));
    }

    @Override
    public int getItemCount() {
        if (criancas == null)
            return 0;
        return criancas.size();
    }

    public void setCriancaVacinas(List<CriancaVacina> criancas) {
        this.criancas = criancas;
        notifyDataSetChanged();
    }


    private void editarCriancaVacina(final CriancaVacina c) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.editar_crianca_vacina, null);
        final EditText txtData = v.findViewById(R.id.txtData);
        final EditText txtUnidadeHospitalar = v.findViewById(R.id.txtUnidadeHospitalar);
        final RadioButton rbFeita = v.findViewById(R.id.rbFeita);
        RadioButton rbAgendada = v.findViewById(R.id.rbAgendada);

        Button btnSalvar = v.findViewById(R.id.btnSalvar);
        Button btnCancelar = v.findViewById(R.id.btnCancelar);
        final DataPickerListnerData listnerDataDoVeiculo = new DataPickerListnerData(txtData, null);
        txtData.setOnFocusChangeListener(listnerDataDoVeiculo);

        rbFeita.setText(VacinaEnums.FEITA);
        rbAgendada.setText(VacinaEnums.AGENDADA);
        if(c.getEstado().equalsIgnoreCase(VacinaEnums.FEITA)){
            rbFeita.setChecked(true);
            rbAgendada.setEnabled(false);
        }else if(c.getEstado().equalsIgnoreCase(VacinaEnums.AGENDADA)){
            rbAgendada.setChecked(true);
            rbAgendada.setEnabled(true);
        }

        txtUnidadeHospitalar.setText(c.getUnidadeSanitaria());
        txtData.setText(sdf.format(c.getDataDaAdministracao()));

        dialog.setView(v);
        final AlertDialog ab = dialog.create();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CriancaVacina cc = new CriancaVacina();
                try {
                    cc = criancaViewModel.getCriancaVacina(c.getIdCrianca(),c.getIdVacina());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                cc.setDataDaAdministracao(listnerDataDoVeiculo.getDataObtida().getTime());
                cc.setUnidadeSanitaria(txtUnidadeHospitalar.getText().toString());
                if (rbFeita.isChecked()) {
                    cc.setEstado(VacinaEnums.FEITA);
                } else {
                    cc.setEstado(VacinaEnums.AGENDADA);
                }
                criancaViewModel.saveCriancaVacina(cc);
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


    protected class CriancaVacinaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtNome, txtDose, txtEstado, txtUnidadeHospitalar, txtData;
        private ImageView imgDelete;
        private ImageView imgEditar;
        private CriancaVacina c;
        private LinearLayout linearLayout,layoutImgDelete,layoutImgEditar;

        public CriancaVacinaViewHolder(View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtDose = itemView.findViewById(R.id.btnDose);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            txtUnidadeHospitalar = itemView.findViewById(R.id.txtUnidadeHospitalar);
            txtData = itemView.findViewById(R.id.txtData);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEditar = itemView.findViewById(R.id.imgEditar);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            layoutImgDelete = itemView.findViewById(R.id.layoutImgDelete);
            layoutImgEditar = itemView.findViewById(R.id.layoutImgEditar);
            itemView.setOnClickListener(this);
            imgDelete.setOnClickListener(this);
            imgEditar.setOnClickListener(this);
        }

        public void bind(CriancaVacina c) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.c = c;
            try {
                Vacina vacina = vacinaViewModel.getVacinaById(this.c.getIdVacina());
                txtNome.setText(vacina.getNome());
                txtDose.setText(c.getDose());
                txtEstado.setText(c.getEstado());
                txtUnidadeHospitalar.setText(c.getUnidadeSanitaria());
                txtData.setText(sdf.format(c.getDataDaAdministracao()));
                if(this.c.getEstado().equalsIgnoreCase(VacinaEnums.DISPENSADA)){
                    imgDelete.setImageResource(R.drawable.ic_gota_24dp);
                    layoutImgEditar.setVisibility(View.GONE);
                }else if(this.c.getEstado().equalsIgnoreCase(VacinaEnums.AGENDADA)){
                    imgDelete.setImageResource(R.drawable.ic_gota_off_24dp);
                    layoutImgEditar.setVisibility(View.VISIBLE);
                }else{
                    layoutImgDelete.setVisibility(View.GONE);
                    layoutImgEditar.setVisibility(View.GONE);
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
                case R.id.imgEditar:
                    editarCriancaVacina(c);
                    notifyDataSetChanged();
                    break;
                case R.id.imgDelete:
                    try {
                        if(c.getEstado().equalsIgnoreCase(VacinaEnums.AGENDADA)){
                            criancaViewModel.dispensarCriancaVacina(c);
                            notifyDataSetChanged();
                        } else if(c.getEstado().equalsIgnoreCase(VacinaEnums.DISPENSADA)){
                            criancaViewModel.agendarCriancaVacina(c);
                            notifyDataSetChanged();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
