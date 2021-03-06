package divulga.com.br.projectdivulga.rest;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.Log;

import divulga.com.br.projectdivulga.CustomProgressDialog;
import divulga.com.br.projectdivulga.ModelDB.Establishments;
import divulga.com.br.projectdivulga.Utils.CustomAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ntbra on 04/06/2016.
 */
public class CallWithProgressBar<T>{

    public void doCall(Call<T> call, final Activity activity, final Class<T> clazz, final ProgressCallBack<T> callback){
        CustomAlertDialog.showAlert(activity);
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                CustomAlertDialog.waitAndFinish();
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                CustomAlertDialog.waitAndFinish();
                callback.onFailure(call, t);
            }
        });
    }

    public interface ProgressCallBack<T>{
        public void onResponse(Call<T> call, Response<T> response);
        public void onFailure(Call<T> call, Throwable t);
    }
}
