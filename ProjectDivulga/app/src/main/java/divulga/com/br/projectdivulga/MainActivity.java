package divulga.com.br.projectdivulga;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import divulga.com.br.projectdivulga.ModelDB.Categories;
import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.ModelDB.Establishments;
import divulga.com.br.projectdivulga.fragments.Anuncie;
import divulga.com.br.projectdivulga.fragments.Categorias;
import divulga.com.br.projectdivulga.fragments.Cidades;
import divulga.com.br.projectdivulga.fragments.Estabelecimentos;
import divulga.com.br.projectdivulga.fragments.Sobre;
import divulga.com.br.projectdivulga.fragments.TelefonesUteis;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static MainActivity mainActivity;

    public Cities selectedCity;
    public Establishments selectedEstablishment;
    public Categories selectedCategory;
    public Toolbar toolbar;
    public NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        addFragment(Anuncie.class, "ANUNCIE", R.id.nav_anuncie);
        addFragment(Cidades.class, "CIDADES", R.id.nav_cidades);
        addFragment(Estabelecimentos.class, "ESTABELECIMENTOS", -1);
        addFragment(Sobre.class, "SOBRE", R.id.nav_sobre);
        addFragment(TelefonesUteis.class, "TELEFONES", R.id.nav_tel);
        addFragment(Categorias.class, "CATEGORIAS", -1);
        if(inUse == null) {
            commitFragment("CIDADES", null);
            navigationView.setCheckedItem(R.id.nav_cidades);
        }else{
            commitFragment(inUse, lastUsed);
        }
        mainActivity = MainActivity.this;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cidades) {
            commitFragment("CIDADES", null);
        } else if (id == R.id.nav_sobre) {
            commitFragment("SOBRE", "CIDADES");
        } else if (id == R.id.nav_anuncie) {
            commitFragment("ANUNCIE", "CIDADES");
        } else if (id == R.id.nav_tel) {
            commitFragment("TELEFONES", "CIDADES");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            inUse = lastUsed;
            lastUsed = null;
            if(inUse != null){
                int id = ids.get(inUse);
                if(id != -1){
                    MainActivity.mainActivity.navigationView.setCheckedItem(id);
                }
            }
        }else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
