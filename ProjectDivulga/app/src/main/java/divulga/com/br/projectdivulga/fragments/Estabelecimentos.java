package divulga.com.br.projectdivulga.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import divulga.com.br.projectdivulga.EstablishmentShow;
import divulga.com.br.projectdivulga.MainActivity;
import divulga.com.br.projectdivulga.ModelDB.Categories;
import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.ModelDB.Establishments;
import divulga.com.br.projectdivulga.R;
import divulga.com.br.projectdivulga.Utils.ClickHelper;
import divulga.com.br.projectdivulga.Adapters.EstabAdapter;
import divulga.com.br.projectdivulga.Utils.CustomAlertDialog;
import divulga.com.br.projectdivulga.Utils.WifiReceiver;
import divulga.com.br.projectdivulga.rest.CallWithProgressBar;
import divulga.com.br.projectdivulga.rest.RealmController;
import divulga.com.br.projectdivulga.rest.RestApi;
import retrofit2.Call;
import retrofit2.Response;

public class Estabelecimentos extends Fragment {

    private List<Establishments> estabList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EstabAdapter estabAdapter;
    private RelativeLayout layout;
    private Cities city;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estabelecimentos, container, false);
        city = MainActivity.mainActivity.selectedCity;

        recyclerView = (RecyclerView) view.findViewById(R.id.estab_rec);
        layout = (RelativeLayout) view.findViewById(R.id.empty_rec);
        MainActivity.mainActivity.toolbar.setTitle(MainActivity.mainActivity.selectedCity.getCity_name()+" - "+MainActivity.mainActivity.selectedCategory.getCat_name());
        estabAdapter = new EstabAdapter(estabList, view.getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(estabAdapter);


        prepareData(view.getContext());

        return view;
    }

    private void prepareData(final Context context) {
        Call<Categories> call = RestApi.getApiInterface().getCategories(MainActivity.mainActivity.selectedCategory.getId());

        new CallWithProgressBar<Categories>().doCall(call, MainActivity.mainActivity, Categories.class, new CallWithProgressBar.ProgressCallBack<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                estabList.clear();
                estabList.addAll(response.body().getEstablishments());
                if(estabList.isEmpty())
                    layout.setVisibility(View.VISIBLE);
                else layout.setVisibility(View.GONE);
                estabAdapter.notifyDataSetChanged();
                RealmController.getInstance().clearAndAddAllEstablishments(estabList, MainActivity.mainActivity.selectedCategory.getId());
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                estabList.clear();
                estabList.addAll(RealmController.getInstance().getEstablishments(MainActivity.mainActivity.selectedCategory.getId()));
                if(estabList.isEmpty())
                    layout.setVisibility(View.VISIBLE);
                else layout.setVisibility(View.GONE);
                estabAdapter.notifyDataSetChanged();
                CustomAlertDialog.showNoInternetDialog(MainActivity.mainActivity, new CustomAlertDialog.AlertAction() {
                    @Override
                    public void okAction(final WifiManager manager) {
                        CustomAlertDialog.doBackgroundProgressAction(MainActivity.mainActivity, new CustomAlertDialog.ProgressAction() {
                            @Override
                            public void doAction() {
                                synchronized (WifiReceiver.wait){
                                    try {
                                        WifiReceiver.wait.wait(3000);
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                new Handler(context.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        prepareData(context);
                                    }
                                });

                            }
                        });
                    }
                });
            }
        });

    }

}
