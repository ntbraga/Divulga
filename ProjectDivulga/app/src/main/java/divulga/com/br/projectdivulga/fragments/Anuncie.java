package divulga.com.br.projectdivulga.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import divulga.com.br.projectdivulga.MainActivity;
import divulga.com.br.projectdivulga.R;
public class Anuncie extends Fragment {
    private FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity.mainActivity.toolbar.setTitle(getString(R.string.anuncie));
        View view = inflater.inflate(R.layout.fragment_anuncie, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.anuncie_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

}
