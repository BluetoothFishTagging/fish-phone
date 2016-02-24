package bft.fishtagsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.widget.Toast;

public class WifiDetector extends BroadcastReceiver {
    private final Handler handler;
    public WifiDetector(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        final String action = intent.getAction();
        if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if(info.isConnected()){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "WIFI_ESTABLISHED", Toast.LENGTH_LONG).show();
                    }
                });

            }
        }
    }
}
