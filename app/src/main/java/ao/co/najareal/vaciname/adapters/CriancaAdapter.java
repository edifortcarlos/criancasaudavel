package ao.co.najareal.vaciname.adapters;

import android.app.AlertDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.model.Crianca;
import ao.co.najareal.vaciname.model.CriancaVacina;
import ao.co.najareal.vaciname.model.PesoCrianca;
import ao.co.najareal.vaciname.model.util.Medida;
import ao.co.najareal.vaciname.model.util.Sexo;
import ao.co.najareal.vaciname.ui.CriancaVacinaActivity;
import ao.co.najareal.vaciname.ui.graph.MedidasValoresActivity;
import ao.co.najareal.vaciname.ui.util.Conversor;
import ao.co.najareal.vaciname.viewModel.CriancaViewModel;
import ao.co.najareal.vaciname.viewModel.VacinaViewModel;

public class CriancaAdapter extends RecyclerView.Adapter<CriancaAdapter.CriancaViewHolder> {

    private Context context;
    private List<Crianca> criancas;
    private CriancaViewModel criancaViewModel;
    private VacinaViewModel vacinaViewModel;
    private LifecycleOwner lifecycleOwner;

    public CriancaAdapter(Context context, LifecycleOwner lifecycleOwner, CriancaViewModel rep, VacinaViewModel vacinaViewModel) {
        this.criancaViewModel = rep;
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.vacinaViewModel = vacinaViewModel;
    }

    @NonNull
    @Override
    public CriancaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_crianca, parent, false);
        return new CriancaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CriancaViewHolder holder, int position) {
        try {
            holder.bind(criancas.get(position));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (criancas == null)
            return 0;
        return criancas.size();
    }

    public void setCriancas(List<Crianca> criancas) {
        this.criancas = criancas;
        notifyDataSetChanged();
    }

    protected class CriancaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtNome;
        private Button btnIdade, btnPeso, btnVacinasAtrasadas;
        private Crianca c;
        private RelativeLayout linearLayout1;
        private ImageView mMenu, imgFoto;

        public CriancaViewHolder(View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
            btnIdade = itemView.findViewById(R.id.btnIdade);
            btnVacinasAtrasadas = itemView.findViewById(R.id.btnVacinasAtrasadas);
            btnPeso = itemView.findViewById(R.id.btnPeso);
            mMenu = itemView.findViewById(R.id.mMenu);
            imgFoto = itemView.findViewById(R.id.imgFoto);
            linearLayout1 = itemView.findViewById(R.id.linearLayout1);
            txtNome.setOnClickListener(this);
            mMenu.setOnClickListener(this);
            imgFoto.setOnClickListener(this);
            btnVacinasAtrasadas.setOnClickListener(this);
            btnPeso.setOnClickListener(this);

        }

        public void bind(Crianca c) throws ExecutionException, InterruptedException {
            this.c = c;
            txtNome.setText(c.getNome());
            btnIdade.setText(Conversor.longParaIdade(new Date(), c.getDataDeNascimento()));

            btnPeso.setText(String.valueOf(this.c.getPeso()) + " " + context.getString(R.string.kg));
            if (c.getSexo().equalsIgnoreCase(Sexo.FEMININO)) {
                linearLayout1.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            } else {
                linearLayout1.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            }

            PesoCrianca pesoCrianca = criancaViewModel.getUltimoPeso(c.getId());
            if(pesoCrianca !=null) {
                btnPeso.setText(String.valueOf(pesoCrianca.getPeso()) + " " + context.getString(R.string.kg));
            }

            int vacinasAtrasadas = criancaViewModel.getQtdVacinasAtrasadas(c.getId(),new Date().getTime());

            btnVacinasAtrasadas.setVisibility(View.GONE);
            criancaViewModel.setId(c.getId());
            criancaViewModel.getQtdVacinasAtrasadasLiveData().observe(lifecycleOwner, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer integer) {
                    if (vacinasAtrasadas > 0 /* verificar vacinas em atraso*/) {
                        btnVacinasAtrasadas.setText(vacinasAtrasadas + " " + context.getResources().getString(R.string.xVacinasEmAtraso));
                        btnVacinasAtrasadas.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        btnVacinasAtrasadas.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mMenu:
                    final PopupMenu popupMenu = new PopupMenu(context, mMenu);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_inicial, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(onMenuItemClickListener());
                    popupMenu.show();
                    break;
                case R.id.btnPeso:
                    context.startActivity(new Intent(context, MedidasValoresActivity.class)
                            .putExtra("tipo", Medida.PESO)
                            .putExtra("id", c.getId()));
                    break;
                case R.id.btnVacinasAtrasadas:

                    //List<CriancaVacina> cvs = criancaViewModel.getAllVacinasAtrasadas(c.getId(), new Date().getTime());

                    listarVacinas();


                    break;
                case R.id.imgFoto:
                case R.id.txtNome:
                    criancaViewModel.setId(c.getId());
                    context.startActivity(new Intent(context, CriancaVacinaActivity.class)
                            .putExtra("id", c.getId()));
                    break;
            }
        }

        private PopupMenu.OnMenuItemClickListener onMenuItemClickListener() {
            return new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.mnuVisualizar:
                            context.startActivity(new Intent(context, CriancaVacinaActivity.class)
                                    .putExtra("id", c.getId()));
                            break;
                        case R.id.mnuApagar:
                            apagarCartao();
                            break;
                    }
                    return false;
                }
            };
        }

        private void apagarCartao() {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            View v = LayoutInflater.from(context).inflate(R.layout.modal_alert, null);
            final TextView txtDescricao = v.findViewById(R.id.txtDescricao);
            final TextView txtQuestao = v.findViewById(R.id.txtTitulo);
            TextView txtSim = v.findViewById(R.id.txtSim);
            TextView txtNao = v.findViewById(R.id.txtNao);

            txtDescricao.setText(context.getResources().getString(R.string.SimApagarCartaoDeVacinaDescricao));
            txtQuestao.setText(context.getResources().getString(R.string.ApagarCartaoDeVacina));

            dialog.setView(v);
            final AlertDialog ab = dialog.create();

            txtSim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    criancaViewModel.deleteCrianca(c);
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


        private void listarVacinas() {
            android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(context);
            View v = LayoutInflater.from(context).inflate(R.layout.lista_vaicnas, null);
            Button btnOK = v.findViewById(R.id.btnOK);
            RecyclerView rvVacinas = v.findViewById(R.id.rvVacinas);


            CriancaVacinaAdapter adapter = new CriancaVacinaAdapter(v.getContext(), vacinaViewModel, criancaViewModel);
            rvVacinas.setAdapter(adapter);
            rvVacinas.setLayoutManager(new LinearLayoutManager(v.getContext()));

            criancaViewModel.setId(c.getId());
            criancaViewModel.getAllVacinasAtrasadasLiveData().observe(lifecycleOwner, new Observer<List<CriancaVacina>>() {
                @Override
                public void onChanged(@Nullable List<CriancaVacina> criancas) {
                    adapter.setCriancaVacinas(criancas);
                }
            });

            dialog.setView(v);
            final android.app.AlertDialog ab = dialog.create();

            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ab.dismiss();
                }
            });


            ab.show();
        }
    }

}
