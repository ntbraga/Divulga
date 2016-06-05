package divulga.com.br.projectdivulga.Utils;

import android.app.Activity;
import android.content.Intent;
import android.text.InputType;

import divulga.com.br.projectdivulga.CustomProgressDialog;

/**
 * Created by ntbra on 04/03/2016.
 */
public class CustomAlertDialog{

    public static CustomProgressDialog showAlert(Activity activity){
        Intent intent = new Intent(activity, CustomProgressDialog.class);
        activity.startActivity(intent);
        return CustomProgressDialog.instance;
    }
}