package blak.android.optimizing.receivers;

import blak.android.optimizing.internal.BatteryLevel;
import blak.android.optimizing.ConnectApplication;
import blak.android.optimizing.internal.ConnectivityUtils;
import blak.android.optimizing.NetworkDispatcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BatterySignificantReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        BatteryLevel batteryLevel = ConnectivityUtils.getBatteryLevel(intent);
        NetworkDispatcher networkDispatcher = ConnectApplication.getInstance().getNetworkDispatcher();
        networkDispatcher.onBatterySignificantChange(batteryLevel);
    }
}
