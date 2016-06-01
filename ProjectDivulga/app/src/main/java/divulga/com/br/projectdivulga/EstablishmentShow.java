package divulga.com.br.projectdivulga;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import divulga.com.br.projectdivulga.ModelDB.Establishments;

public class EstablishmentShow extends BaseActivity {
    private Establishments establishments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        establishments = MainActivity.mainActivity.selectedEstablishment;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(establishments.getEstab_name());
    }
}
