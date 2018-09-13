package ao.co.najareal.vaciname.ui.fragmentos;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import java.util.List;

import ao.co.najareal.vaciname.R;
import ao.co.najareal.vaciname.adapters.CriancaAdapter;
import ao.co.najareal.vaciname.adapters.CriancaVacinaAdapter;
import ao.co.najareal.vaciname.model.Crianca;
import ao.co.najareal.vaciname.model.CriancaVacina;
import ao.co.najareal.vaciname.model.Vacina;
import ao.co.najareal.vaciname.ui.HomeActivity;
import ao.co.najareal.vaciname.viewModel.CriancaViewModel;
import ao.co.najareal.vaciname.viewModel.VacinaViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class Listagem_Cartao_Vacina_Fragment extends Fragment{

    private CriancaViewModel criancaViewModel;
    private VacinaViewModel vacinaViewModel;
    private RecyclerView rvCrianca;
    private CriancaAdapter adapter;
    private View view;

    public Listagem_Cartao_Vacina_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_listagem__cartao__vacina_, container, false);
        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        criancaViewModel = ViewModelProviders.of(this).get(CriancaViewModel.class);
       // criancaViewModel.setId(id);
        vacinaViewModel = ViewModelProviders.of(this).get(VacinaViewModel.class);
        rvCrianca = view.findViewById(R.id.rvCriancas);
        adapter = new CriancaAdapter(getActivity(),getActivity(), criancaViewModel,vacinaViewModel);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvCrianca.setLayoutManager(llm);
        rvCrianca.setAdapter(adapter);

        criancaViewModel.getAll().observe(this, new Observer<List<Crianca>>() {
            @Override
            public void onChanged(@Nullable List<Crianca> criancas) {
                adapter.setCriancas(criancas);
            }
        });

    }

}
