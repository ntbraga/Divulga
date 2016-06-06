package divulga.com.br.projectdivulga;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import divulga.com.br.projectdivulga.ModelDB.Categories;
import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.ModelDB.Establishments;
import divulga.com.br.projectdivulga.fragments.Anuncie;
import divulga.com.br.projectdivulga.fragments.Categorias;
import divulga.com.br.projectdivulga.fragments.Cidades;
import divulga.com.br.projectdivulga.fragments.Estabelecimentos;
import divulga.com.br.projectdivulga.fragments.Favoritos;
import divulga.com.br.projectdivulga.fragments.Sobre;
import divulga.com.br.projectdivulga.fragments.TelefonesUteis;
import divulga.com.br.projectdivulga.rest.ApiInterface;
import divulga.com.br.projectdivulga.rest.RestApi;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static MainActivity mainActivity;

    public Cities selectedCity;
    public Establishments selectedEstablishment;
    public Categories selectedCategory;
    public Toolbar toolbar;
    public NavigationView navigationView;
    public String citiesJson;

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
        citiesJson = getIntent().getStringExtra("cities");
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        addFragment(Anuncie.class, getString(R.string.anuncie_frag), R.id.nav_anuncie);
        addFragment(Cidades.class, getString(R.string.cidades_frag), R.id.nav_cidades);
        addFragment(Estabelecimentos.class, getString(R.string.estab_frag), -1);
        addFragment(Sobre.class, getString(R.string.sobre_frag), R.id.nav_sobre);
        addFragment(TelefonesUteis.class, getString(R.string.tel_frag), R.id.nav_tel);
        addFragment(Categorias.class, getString(R.string.cat_frag), -1);
        addFragment(Favoritos.class, getString(R.string.fav_frag), R.id.nav_fav);
        if(inUse == null) {
            commitFragment(getString(R.string.cidades_frag), null);
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
            commitFragment(getString(R.string.cidades_frag), null);
        } else if (id == R.id.nav_sobre) {
            commitFragment(getString(R.string.sobre_frag), getString(R.string.cidades_frag));
        } else if (id == R.id.nav_anuncie) {
            commitFragment(getString(R.string.anuncie_frag), getString(R.string.cidades_frag));
        } else if (id == R.id.nav_tel) {
            commitFragment(getString(R.string.tel_frag), getString(R.string.cidades_frag));
        } else if (id == R.id.nav_fav) {
            commitFragment(getString(R.string.fav_frag), getString(R.string.cidades_frag));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
            }else{
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case 123:{
                    Toast.makeText(this, "HUE ", Toast.LENGTH_SHORT);
                    break;
                }
                default:{
                    break;
                }
            }
        }
    }

    private Intent call = null;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (call != null) {
                        startActivity(call);
                    }
                }
                break;
            }
            default:{

            }
        }
    }

    public void doCall(String number){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.mainActivity,
                    new String[]{Manifest.permission.CALL_PHONE}, 10);
            call = callIntent;
            return;
        }
        startActivity(callIntent);
    }

    public void sendMail(String mail){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:" + mail);
        intent.setData(data);
        startActivity(intent);
    }
}
