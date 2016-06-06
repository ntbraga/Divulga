package divulga.com.br.projectdivulga.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import divulga.com.br.projectdivulga.MainActivity;
import divulga.com.br.projectdivulga.ModelDB.Categories;
import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.R;
import divulga.com.br.projectdivulga.Adapters.CategoryAdapter;
import divulga.com.br.projectdivulga.Utils.ClickHelper;
import divulga.com.br.projectdivulga.Utils.CustomAlertDialog;
import divulga.com.br.projectdivulga.Utils.GridSpacingItemDecoration;
import divulga.com.br.projectdivulga.Utils.WifiReceiver;
import divulga.com.br.projectdivulga.rest.CallWithProgressBar;
import divulga.com.br.projectdivulga.rest.RealmController;
import divulga.com.br.projectdivulga.rest.RestApi;
import io.realm.RealmObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Categorias extends Fragment {
    private List<Categories> categories = new ArrayList<>();
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private RelativeLayout layout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categorias, container, false);
        MainActivity.mainActivity.toolbar.setTitle(MainActivity.mainActivity.selectedCity.getCity_name());
        recyclerView = (RecyclerView) view.findViewById(R.id.cat_rec);
        layout = (RelativeLayout) view.findViewById(R.id.empty_rec);
        categoryAdapter = new CategoryAdapter(categories, view.getContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categoryAdapter);

        recyclerView.addOnItemTouchListener(new ClickHelper.RecyclerTouchListener(view.getContext(), recyclerView, new ClickHelper.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MainActivity.mainActivity.selectedCategory = categories.get(position);
                MainActivity.mainActivity.commitFragment("ESTABELECIMENTOS", "CATEGORIAS");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareData(view.getContext());
        return view;
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    void prepareData(final Context context){
        /*
        if(RealmController.getInstance().has(Categories.class))
            categories.addAll(RealmController.getInstance().getAll(Categories.class));
        */
        Call<Cities> call = RestApi.getApiInterface().getCity(MainActivity.mainActivity.selectedCity.getId());

        new CallWithProgressBar<Cities>().doCall(call, MainActivity.mainActivity, Cities.class, new CallWithProgressBar.ProgressCallBack<Cities>(){
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                categories.clear();
                categories.addAll(response.body().getCategories());
                if(categories.isEmpty())
                    layout.setVisibility(View.VISIBLE);
                else layout.setVisibility(View.GONE);
                categoryAdapter.notifyDataSetChanged();
                RealmController.getInstance().clearAndAddAll(categories, Categories.class);
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                categories.clear();
                categories.addAll(RealmController.getInstance().getCategories(MainActivity.mainActivity.selectedCity.getId()));
                if(categories.isEmpty())
                    layout.setVisibility(View.VISIBLE);
                else layout.setVisibility(View.GONE);
                categoryAdapter.notifyDataSetChanged();
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
