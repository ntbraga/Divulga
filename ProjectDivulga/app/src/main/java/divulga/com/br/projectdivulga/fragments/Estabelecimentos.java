package divulga.com.br.projectdivulga.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import divulga.com.br.projectdivulga.EstablishmentShow;
import divulga.com.br.projectdivulga.MainActivity;
import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.ModelDB.Establishments;
import divulga.com.br.projectdivulga.R;
import divulga.com.br.projectdivulga.Utils.ClickHelper;
import divulga.com.br.projectdivulga.Adapters.EstabAdapter;
import divulga.com.br.projectdivulga.rest.RealmController;

public class Estabelecimentos extends Fragment {

    private List<Establishments> estabList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EstabAdapter estabAdapter;
    private Cities city;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estabelecimentos, container, false);
        city = MainActivity.mainActivity.selectedCity;

        recyclerView = (RecyclerView) view.findViewById(R.id.estab_rec);
        MainActivity.mainActivity.toolbar.setTitle(MainActivity.mainActivity.selectedCity.getCity_name()+" - "+MainActivity.mainActivity.selectedCategory.getCat_name());
        estabAdapter = new EstabAdapter(estabList, view.getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(estabAdapter);


        prepareMovieData();

        return view;
    }

    private void prepareMovieData() {
        if(RealmController.getInstance().has(Establishments.class))
            estabList.addAll(RealmController.getInstance().getAll(Establishments.class));
        estabAdapter.notifyDataSetChanged();
    }

}
