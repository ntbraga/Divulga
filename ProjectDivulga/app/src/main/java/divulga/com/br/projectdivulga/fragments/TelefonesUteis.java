package divulga.com.br.projectdivulga.fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import divulga.com.br.projectdivulga.MainActivity;
import divulga.com.br.projectdivulga.ModelDB.TelefoneUtil;
import divulga.com.br.projectdivulga.R;
import divulga.com.br.projectdivulga.Adapters.TelefoneAdapter;


public class TelefonesUteis extends Fragment {

    private List<TelefoneUtil> telList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TelefoneAdapter telAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_telefones_uteis, container, false);

        MainActivity.mainActivity.toolbar.setTitle(getString(R.string.telefones_uteis));
        recyclerView = (RecyclerView) view.findViewById(R.id.tel_util_rec);

        telAdapter = new TelefoneAdapter(telList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(telAdapter);

        prepareData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void prepareData(){
        telList.add(new TelefoneUtil("ANEEL", "167", getContext().getDrawable(R.drawable.ic_taxi)));
        telList.add(new TelefoneUtil("Corpo de Bombeiros", "193", getContext().getDrawable(R.drawable.ic_taxi)));
        telList.add(new TelefoneUtil("Defesa Civil", "199", getContext().getDrawable(R.drawable.ic_taxi)));
        telList.add(new TelefoneUtil("Polícia Civil", "197", getContext().getDrawable(R.drawable.ic_taxi)));
        telList.add(new TelefoneUtil("Polícia Militar", "190", getContext().getDrawable(R.drawable.ic_taxi)));
        telList.add(new TelefoneUtil("Procon", "151", getContext().getDrawable(R.drawable.ic_taxi)));
        telList.add(new TelefoneUtil("SAMU", "192", getContext().getDrawable(R.drawable.ic_taxi)));
        telAdapter.notifyDataSetChanged();
    }
}
