package blak.android.connectlib.receivers;

import blak.android.connectlib.ChargingType;
import blak.android.connectlib.ConnectApplication;
import blak.android.connectlib.ConnectivityUtils;
import blak.android.connectlib.NetworkDispatcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PowerConnectReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ChargingType chargingType = ConnectivityUtils.getChargingType(context, intent);
        NetworkDispatcher networkDispatcher = ConnectApplication.getInstance().getNetworkDispatcher();
        networkDispatcher.onChargingConnected(chargingType);
    }
}
