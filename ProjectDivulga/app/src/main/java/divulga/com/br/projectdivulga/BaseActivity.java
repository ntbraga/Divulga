package divulga.com.br.projectdivulga;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Taty Braga on 30/05/2016.
 */
public class BaseActivity extends AppCompatActivity{


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    Map<String, Fragment> frags = new HashMap<>();

    public Map<String, Fragment> getFrags() {
        return frags;
    }

    public void addFragment(Class<? extends Fragment> clazz, String tag){
        try {
            frags.put(tag, clazz.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String commitFragment(final String frag, final String onBack){
        Handler handlerTimer = new Handler();
        final Fragment fragment = getFrags().get(frag);
        handlerTimer.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, frag);
                if(onBack != null) transaction.addToBackStack(onBack);
                transaction.commit();
            }
        }, 300);
        return frag;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
