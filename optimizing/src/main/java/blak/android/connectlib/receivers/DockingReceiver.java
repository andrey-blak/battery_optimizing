package blak.android.connectlib.receivers;

import blak.android.connectlib.ConnectApplication;
import blak.android.connectlib.internal.ConnectivityUtils;
import blak.android.connectlib.internal.DockingState;
import blak.android.connectlib.NetworkDispatcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DockingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DockingState dockingState = ConnectivityUtils.getDockingState(intent);
        NetworkDispatcher networkDispatcher = ConnectApplication.getInstance().getNetworkDispatcher();
        networkDispatcher.onDockingChange(dockingState);
    }
}