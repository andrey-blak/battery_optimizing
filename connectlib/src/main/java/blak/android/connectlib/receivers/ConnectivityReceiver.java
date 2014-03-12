package blak.android.connectlib.receivers;

import blak.android.connectlib.ConnectApplication;
import blak.android.connectlib.ConnectivityUtils;
import blak.android.connectlib.NetworkDispatcher;
import blak.android.connectlib.NetworkType;

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
        NetworkType networkType = ConnectivityUtils.getNetworkType(activeNetwork);
        NetworkDispatcher networkDispatcher = ConnectApplication.getInstance().getNetworkDispatcher();
        networkDispatcher.onNetworkTypeChange(context, networkType);
    }
}
