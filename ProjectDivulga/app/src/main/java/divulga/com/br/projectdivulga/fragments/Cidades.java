package divulga.com.br.projectdivulga.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.R;
import divulga.com.br.projectdivulga.Utils.CityAdapter;
import divulga.com.br.projectdivulga.Utils.DividerItemDecoration;

public class Cidades extends Fragment {

    private List<Cities> cityList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CityAdapter cityAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cidades, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.city_rec);

        cityAdapter = new CityAdapter(cityList, view.getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(cityAdapter);
        prepareMovieData();

        return view;
    }


    private void prepareMovieData() {
        for(int i = 0; i<20; i++){
            cityList.add(new Cities(i, "Cidade "+i, "Minas Gerais"));
        }
        cityAdapter.notifyDataSetChanged();
    }

}
