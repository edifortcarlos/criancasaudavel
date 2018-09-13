package ao.co.najareal.vaciname.ui.fragmentos;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.adapters.VacinaAdapter;
import ao.co.najareal.vaciname.model.Vacina;
import ao.co.najareal.vaciname.viewModel.VacinaViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class Plano_Nacional_Vacina_Fragment extends Fragment {


    private View v;

    private VacinaAdapter adapter;
    private RecyclerView rvVacinas;
    private VacinaViewModel vacinaViewModel;
    private LinearLayoutManager llm;
    private int qtdItens , totalItens, totalItensVisivel;


    public Plano_Nacional_Vacina_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_plano__nacional__vacina_, container, false);
        init();
        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init() {
        rvVacinas = v.findViewById(R.id.rvVacinas);
        vacinaViewModel = ViewModelProviders.of(this).get(VacinaViewModel.class);
        llm = new LinearLayoutManager(getContext());
        adapter = new VacinaAdapter(getContext());
        rvVacinas.setLayoutManager(llm);
        rvVacinas.setAdapter(adapter);

        vacinaViewModel.getAll().observe(this, new Observer<List<Vacina>>() {
            @Override
            public void onChanged(@Nullable List<Vacina> vacinas) {
                adapter.setVacinas(vacinas);
            }
        });


        rvVacinas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                qtdItens = llm.getChildCount();
                totalItens = llm.getItemCount();
                totalItensVisivel= llm.findFirstVisibleItemPosition();


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

}
