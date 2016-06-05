package divulga.com.br.projectdivulga;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.rest.RestApi;
import retrofit2.Call;
import retrofit2.Response;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new AsyncTask<Void, Void, Intent>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Intent doInBackground(Void... params) {
                Call<List<Cities>> call = RestApi.getApiInterface().getAllCities();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                try {
                    Response<List<Cities>> resp = call.execute();
                    intent.putExtra("cities", new Gson().getAdapter(new TypeToken<List<Cities>>(){}).toJson(resp.body()));
                } catch (IOException e) {
                    Log.v("ERROR", e.getMessage());
                    intent.putExtra("cities", "erro");
                }

                return intent;
            }

            @Override
            protected void onPostExecute(Intent aVoid) {
                startActivity(aVoid);
                finish();
            }
        }.execute();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
