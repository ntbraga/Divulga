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

import divulga.com.br.projectdivulga.rest.ApiInterface;
import divulga.com.br.projectdivulga.rest.RestApi;

/**
 * Created by Taty Braga on 30/05/2016.
 */
public class BaseActivity extends AppCompatActivity{
    public String lastUsed = null;
    public String inUse = null;

    Map<String, Class<? extends Fragment>> frags = new HashMap<>();
    public Map<String, Integer> ids = new HashMap<>();

    public Map<String, Class<? extends Fragment>> getFrags() {
        return frags;
    }

    public Map<String, Integer> getIds() {
        return ids;
    }

    public void addFragment(Class<? extends Fragment> clazz, String tag, int id){
        frags.put(tag, clazz);
        ids.put(tag, id);
    }

    public void commitFragment(final String frag, final String onBack){
        Handler handlerTimer = new Handler();
        Fragment fragment = null;
        try {
            fragment = getFrags().get(frag).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        final Fragment finalFrag = fragment;
        handlerTimer.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, finalFrag, frag);
                if(onBack != null && !frag.equals(onBack)) transaction.addToBackStack(null);
                transaction.commit();
            }
        }, 300);
        inUse = frag;
        lastUsed = onBack;
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
