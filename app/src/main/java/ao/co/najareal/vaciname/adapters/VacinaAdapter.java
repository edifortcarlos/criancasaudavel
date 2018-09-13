package ao.co.najareal.vaciname.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.model.Vacina;
import ao.co.najareal.vaciname.ui.VacinaDetalheActivity;

public class VacinaAdapter extends RecyclerView.Adapter<VacinaAdapter.VacinaViewHolder> {

    private Context context;
    private List<Vacina> vacinas;

    public VacinaAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public VacinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_vacina,parent,false);
        return new VacinaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VacinaViewHolder holder, int position) {
            holder.bind(vacinas.get(position));
    }

    @Override
    public int getItemCount() {
        if(vacinas == null)
            return 0;
        return vacinas.size();
    }

    public void setVacinas(List<Vacina> vacinas) {
        this.vacinas = vacinas;
        notifyDataSetChanged();
    }

    public void addVacinas(List<Vacina> vacinas) {
        this.vacinas.addAll(vacinas);
        notifyDataSetChanged();
    }

    protected class VacinaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtNome;
        Button btnDose, btnTempoRecomendado;
        private Vacina c ;

        public VacinaViewHolder(View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
            btnDose = itemView.findViewById(R.id.btnDose);
            btnTempoRecomendado = itemView.findViewById(R.id.btnTempoRecomendado);
            itemView.setOnClickListener(this);

        }

        public void bind(Vacina c){
            this.c = c;
            txtNome.setText(this.c.getNome());
            btnDose.setText(this.c.getDose());
            btnTempoRecomendado.setText(this.c.getIdadeRecomendada());
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, VacinaDetalheActivity.class).putExtra("id",c.getId()));
        }
    }
}
