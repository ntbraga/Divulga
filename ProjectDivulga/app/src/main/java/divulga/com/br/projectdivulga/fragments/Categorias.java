package divulga.com.br.projectdivulga.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import divulga.com.br.projectdivulga.MainActivity;
import divulga.com.br.projectdivulga.ModelDB.Categories;
import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.R;
import divulga.com.br.projectdivulga.Adapters.CategoryAdapter;
import divulga.com.br.projectdivulga.Utils.ClickHelper;
import divulga.com.br.projectdivulga.Utils.GridSpacingItemDecoration;
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

    void prepareData(Context context){
        /*
        if(RealmController.getInstance().has(Categories.class))
            categories.addAll(RealmController.getInstance().getAll(Categories.class));
        */
        Call<Cities> call = RestApi.getApiInterface().getCity(MainActivity.mainActivity.selectedCity.getId());

        new CallWithProgressBar<Cities>().doCall(call, MainActivity.mainActivity, new CallWithProgressBar.ProgressCallBack<Cities>(){
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                categories.clear();
                categories.addAll(response.body().getCategories());
                if(categories.isEmpty())
                    layout.setVisibility(View.VISIBLE);
                else layout.setVisibility(View.GONE);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {

            }
        });
    }

}
