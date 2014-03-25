package blak.android.connectlib.receivers;

import blak.android.connectlib.internal.ChargingType;
import blak.android.connectlib.ConnectApplication;
import blak.android.connectlib.internal.ConnectivityUtils;
import blak.android.connectlib.NetworkDispatcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PowerConnectReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ChargingType chargingType = ConnectivityUtils.getChargingStatus(context);
        NetworkDispatcher networkDispatcher = ConnectApplication.getInstance().getNetworkDispatcher();
        networkDispatcher.onChargingConnected(chargingType);
    }
}
