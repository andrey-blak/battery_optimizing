package blak.android.connectlib.receivers;

import blak.android.connectlib.BatteryLevel;
import blak.android.connectlib.ConnectApplication;
import blak.android.connectlib.ConnectivityUtils;
import blak.android.connectlib.NetworkDispatcher;

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
