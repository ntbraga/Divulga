package divulga.com.br.projectdivulga.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.text.InputType;

import divulga.com.br.projectdivulga.CustomProgressDialog;
import divulga.com.br.projectdivulga.MainActivity;
import divulga.com.br.projectdivulga.R;

/**
 * Created by ntbra on 04/03/2016.
 */
public class CustomAlertDialog{
    public static CustomProgressDialog instance;
    public static Object wait = new Object();
    public static boolean showDialog = false;
    public static CustomProgressDialog showAlert(Activity activity){
        Intent intent = new Intent(activity, CustomProgressDialog.class);
        activity.startActivity(intent);
        return instance;
    }

    public static void doProgressShowAction(Activity activity, ProgressAction action){
        showAlert(activity);
        action.doAction();
        waitAndFinish();
    }

    public static void doBackgroundProgressAction(final Activity activity, final ProgressAction action){
        new Thread(new Runnable() {
            @Override
            public void run() {
                showAlert(activity);
                action.doAction();
                waitAndFinish();
            }
        }).start();
    }

    public static void waitAndFinish(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(instance == null)
                synchronized (wait){
                    try {
                        wait.wait();
                        if(instance!=null)
                            instance.finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else instance.finish();
            }
        });
        t.start();
    }

    public static AlertDialog getAlertDialog(Activity activity, String message){
        return new AlertDialog.Builder(activity).setMessage(message).setCancelable(false).create();
    }

    public static AlertDialog getAlertDialog(Activity activity, String message, @DrawableRes int id){
        return new AlertDialog.Builder(activity).setMessage(message).setIcon(activity.getDrawable(id)).setPositiveButton("Continuar", null).create();
    }

    public static void showNoInternetDialog(final Activity activity, final AlertAction action) {
        if(!showDialog) {
            final WifiManager mng = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setMessage("Não há conexão com a internet, você pode estar acessando dados desatualizados.\n" +
                            "Ative a internet para atulizar!")
                    .setPositiveButton("Ativar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!mng.isWifiEnabled() && mng.setWifiEnabled(true) && action != null)
                                action.okAction(mng);
                        }
                    })
                    .setNeutralButton("Continuar", null)
                    .create();

            AlertDialog dialog2 = new AlertDialog.Builder(activity)
                    .setMessage("Não há conexão com a internet, você pode estar acessando dados desatualizados.\n")
                    .setNeutralButton("Continuar", null)
                    .create();

            if (!mng.isWifiEnabled())
                dialog.show();
            else dialog2.show();
            showDialog = true;
        }
    }

    public interface ProgressAction{
        void doAction();
    }

    public interface AlertAction{
        public void okAction(WifiManager manager);
    }
}