package divulga.com.br.projectdivulga;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CustomProgressDialog extends Activity {
    public static CustomProgressDialog instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress_dialog);
        setFinishOnTouchOutside(false);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        instance = CustomProgressDialog.this;
    }


}
