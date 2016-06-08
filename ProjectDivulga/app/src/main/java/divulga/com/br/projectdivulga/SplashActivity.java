package divulga.com.br.projectdivulga;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.ModelDB.Validation;
import divulga.com.br.projectdivulga.Utils.CustomAlertDialog;
import divulga.com.br.projectdivulga.rest.RealmController;
import divulga.com.br.projectdivulga.rest.RestApi;
import io.realm.RealmObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        intent = new Intent(SplashActivity.this, MainActivity.class);
        final AsyncTask<Void, Void, Intent> task = new AsyncTask<Void, Void, Intent>(){
            @Override
            protected void onPreExecute() {
            }

            @Override
            protected Intent doInBackground(Void... params) {
                Call<List<Cities>> call = RestApi.getApiInterface().getAllCities();
                List<Cities> cities;
                try {
                    Response<List<Cities>> resp = call.execute();
                    cities = resp.body();
                } catch (IOException e) {
                    cities = new ArrayList<>();
                }

                if(cities.isEmpty())
                    intent.putExtra("cities", "erro");
                else intent.putExtra("cities", new Gson().getAdapter(new TypeToken<List<Cities>>(){}).toJson(cities));
                return intent;
            }

            @Override
            protected void onPostExecute(Intent aVoid) {
                startActivity(aVoid);
                finish();
            }
        };

        Call<Validation> validation = RestApi.getApiInterface().getValidation();
        validation.enqueue(new Callback<Validation>() {
            @Override
            public void onResponse(Call<Validation> call, Response<Validation> response) {
                RealmController.getInstance().getRealm().beginTransaction();
                RealmController.getInstance().getRealm().copyToRealmOrUpdate(response.body());
                RealmController.getInstance().getRealm().commitTransaction();
                doWork(response.body(), task);
            }

            @Override
            public void onFailure(Call<Validation> call, Throwable t) {
                doWork(RealmController.getInstance().getRealm().where(Validation.class).findFirst(), task);
            }
        });
    }

    private void doWork(Validation validation, AsyncTask<Void, Void, Intent> task){
        if(validation != null)
            if(validation.isAuth()) {
                intent.putExtra("auth", validation.isAuth());
                intent.putExtra("deadline", validation.getDeadline()*1000);
                task.execute();
                return;
            }

        new AlertDialog.Builder(SplashActivity.this).setMessage("Esta versão de teste expirou!")
                .setCancelable(false)
                .setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
