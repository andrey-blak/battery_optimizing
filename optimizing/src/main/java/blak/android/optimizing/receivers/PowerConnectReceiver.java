package blak.android.optimizing.receivers;

import blak.android.optimizing.internal.ChargingType;
import blak.android.optimizing.ConnectApplication;
import blak.android.optimizing.internal.ConnectivityUtils;
import blak.android.optimizing.NetworkDispatcher;

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
