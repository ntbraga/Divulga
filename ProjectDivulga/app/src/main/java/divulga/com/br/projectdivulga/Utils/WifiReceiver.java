package divulga.com.br.projectdivulga.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class WifiReceiver extends BroadcastReceiver {
    public static Object wait = new Object();
    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null && info.isConnected()) {
            // Do your work.
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            if(wifiManager.getConnectionInfo().getSupplicantState() == SupplicantState.COMPLETED && wifiManager.isWifiEnabled()){
                synchronized (wait){
                    wait.notifyAll();
                }
            }
        }
    }
}