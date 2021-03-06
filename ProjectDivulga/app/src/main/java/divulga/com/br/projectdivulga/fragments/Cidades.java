package divulga.com.br.projectdivulga.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import divulga.com.br.projectdivulga.MainActivity;
import divulga.com.br.projectdivulga.ModelDB.Categories;
import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.R;
import divulga.com.br.projectdivulga.Adapters.CityAdapter;
import divulga.com.br.projectdivulga.Utils.ClickHelper;
import divulga.com.br.projectdivulga.Utils.CustomAlertDialog;
import divulga.com.br.projectdivulga.Utils.DividerItemDecoration;
import divulga.com.br.projectdivulga.rest.RealmController;

public class Cidades extends Fragment {

    private List<Cities> cityList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CityAdapter cityAdapter;
    private RelativeLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cidades, container, false);
        MainActivity.mainActivity.toolbar.setTitle("Cidades");
        recyclerView = (RecyclerView) view.findViewById(R.id.city_rec);
        layout = (RelativeLayout) view.findViewById(R.id.empty_rec);
        cityAdapter = new CityAdapter(cityList, view.getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(cityAdapter);

        recyclerView.addOnItemTouchListener(new ClickHelper.RecyclerTouchListener(view.getContext(), recyclerView, new ClickHelper.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MainActivity.mainActivity.selectedCity = cityList.get(position);
                MainActivity.mainActivity.commitFragment("CATEGORIAS", "CIDADES");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareData();

        return view;
    }



    private void prepareData() {
        String cities = MainActivity.mainActivity.citiesJson;
        if(!cities.equals("erro")){
            Gson gson = new Gson();
            TypeToken<List<Cities>> token = new TypeToken<List<Cities>>(){};
            cityList.clear();
            try {
                cityList.addAll(gson.getAdapter(token).fromJson(cities));
                RealmController.getInstance().clearAndAddAllCities(cityList);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            cityList.clear();
            cityList.addAll(RealmController.getInstance().getAll(Cities.class));
        }
        cityAdapter.notifyDataSetChanged();
        if(cityList.isEmpty()){
            CustomAlertDialog.getAlertDialog(MainActivity.mainActivity, "É necessário conectar-se a internet para utilizar o aplicativo pela primeira vez.", R.drawable.ic_signal_off).show();
            layout.setVisibility(View.VISIBLE);
        }
        else layout.setVisibility(View.GONE);

        /*

        if(RealmController.getInstance().has(Cities.class))
            cityList.addAll(RealmController.getInstance().getAll(Cities.class));
        if(!cityList.isEmpty())
            layout.setVisibility(View.GONE);

        */
    }

}
