package blak.android.connectlib.receivers;

import blak.android.utils.MyLog;
import blak.android.utils.SimpleMessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork == null) {
            SimpleMessage.show(context.getApplicationContext(), "connection", false);
            MyLog.v("connection", false);
            return;
        }

        boolean isConnected = activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
            SimpleMessage.show(context.getApplicationContext(), "connection", false);
            MyLog.v("connection", false);
            return;
        }

        boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        boolean isMobile = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
        boolean isRoaming = activeNetwork.isRoaming();

        SimpleMessage.show(context.getApplicationContext(), "connection", isConnected, "wifi", isWiFi, "mobile", isMobile, "roaming", isRoaming);
        MyLog.v("connection", isConnected, "wifi", isWiFi, "mobile", isMobile, "roaming", isRoaming);
    }
}
