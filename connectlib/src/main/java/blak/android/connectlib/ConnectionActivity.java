package blak.android.connectlib;

import blak.android.utils.MyLog;
import blak.android.utils.SimpleMessage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

public class ConnectionActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mConnectivityReceiver);
    }

    private final BroadcastReceiver mConnectivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (activeNetwork == null) {
                return;
            }

            boolean isConnected = activeNetwork.isConnectedOrConnecting();
            boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            boolean isMobile = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
            boolean isRoaming = activeNetwork.isRoaming();

            SimpleMessage.show(getApplicationContext(), "connection", isConnected, "wifi", isWiFi, "mobile", isMobile, "roaming", isRoaming);
            MyLog.v("connection", isConnected, "wifi", isWiFi, "mobile", isMobile, "roaming", isRoaming);
        }
    };
}
