package divulga.com.br.projectdivulga;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import divulga.com.br.projectdivulga.Utils.CustomAlertDialog;

public class CustomProgressDialog extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress_dialog);
        setFinishOnTouchOutside(false);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        synchronized (CustomAlertDialog.wait){
            CustomAlertDialog.instance = CustomProgressDialog.this;
            CustomAlertDialog.wait.notifyAll();
        }
    }

    @Override
    public void finish() {
        super.finish();
        CustomAlertDialog.instance = null;
    }
}
