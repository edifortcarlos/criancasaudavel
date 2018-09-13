package ao.co.najareal.vaciname.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.model.Campanha;
import ao.co.najareal.vaciname.ui.util.Conversor;

public class CampanhaAdapter extends RecyclerView.Adapter<CampanhaAdapter.AlturaViewHolder> {

    private Context context;
    private List<Campanha> campanha;

    public CampanhaAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AlturaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_campanha, parent, false);
        return new AlturaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlturaViewHolder holder, int position) {
        holder.bind(campanha.get(position));
    }

    @Override
    public int getItemCount() {
        if (campanha == null)
            return 0;
        return campanha.size();
    }

    public void setCampanhas(List<Campanha> campanha) {
        this.campanha = campanha;
        notifyDataSetChanged();
    }

    protected class AlturaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtNome, txtAnoDaCampanha, txtDiaInicio, txtMesInicio, txtAnoDataInicio,
                txtDiaFim, txtMesFim, txtAnoDataFim;

        private Campanha c;

        public AlturaViewHolder(View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtAnoDaCampanha = itemView.findViewById(R.id.txtAnoDaCampanha);
            txtDiaInicio = itemView.findViewById(R.id.txtDiaInicio);
            txtMesInicio = itemView.findViewById(R.id.txtMesInicio);
            txtAnoDataInicio = itemView.findViewById(R.id.txtAnoDataInicio);
            txtDiaFim = itemView.findViewById(R.id.txtDiaFim);
            txtMesFim = itemView.findViewById(R.id.txtMesFim);
            txtAnoDataFim = itemView.findViewById(R.id.txtAnoDataFim);
            itemView.setOnClickListener(this);
        }

        public void bind(Campanha c) {
            this.c = c;
            txtNome.setText(String.valueOf(c.getNome()));

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(c.getDataInicio());

            int anoDaCampanha = 1900 + calendar.getTime().getYear();

            int diaInicio = calendar.getTime().getDate();
            int mesInicio = calendar.getTime().getMonth();
            int anoInicio = 1900 + calendar.getTime().getYear();

            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(c.getDataFim());

            int diaFim = calendar.getTime().getDate();
            int mesFim = calendar.getTime().getMonth();
            int anoFim = 1900 + calendar.getTime().getYear();


            txtAnoDaCampanha.setText(String.valueOf(anoDaCampanha));

            txtDiaInicio.setText(String.valueOf(diaInicio));
            txtMesInicio.setText(Conversor.mesNumeroMesTexto(mesInicio, context.getResources()));
            txtAnoDataInicio.setText(String.valueOf(anoInicio));

            txtDiaFim.setText(String.valueOf(diaFim));
            txtMesFim.setText(Conversor.mesNumeroMesTexto(mesFim, context.getResources()));
            txtAnoDataFim.setText(String.valueOf(anoFim));

        }

        @Override
        public void onClick(View v) {
            verDados(c);
        }

        private void verDados(@NonNull final Campanha c) {
            android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(context);
            View v = LayoutInflater.from(context).inflate(R.layout.ver_dados_campanha, null);
            final TextView txtNome = v.findViewById(R.id.txtNome);
            final TextView txtDataInicio = v.findViewById(R.id.txtAnoDaCampanha);
            final TextView txtDataFim = v.findViewById(R.id.txtDataFim);
            final TextView txtProvincias = v.findViewById(R.id.txtProvincias);
            final TextView txtVacinas = v.findViewById(R.id.txtVacinas);
            final TextView txtEstado = v.findViewById(R.id.txtEstado);
            Button btnSalvar = v.findViewById(R.id.btnFechar);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            txtNome.setText(c.getNome());
            txtDataInicio.setText(sdf.format(c.getDataInicio()));
            txtDataFim.setText(sdf.format(c.getDataFim()));
            txtProvincias.setText(c.getProvincias());
            txtVacinas.setText(c.getVacinas());
            txtEstado.setText(c.getEstado());


            dialog.setView(v);
            final android.support.v7.app.AlertDialog ab = dialog.create();

            btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ab.dismiss();
                }
            });

            ab.show();
        }


    }


}
