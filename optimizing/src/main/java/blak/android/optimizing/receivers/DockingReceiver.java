package blak.android.optimizing.receivers;

import blak.android.optimizing.ConnectApplication;
import blak.android.optimizing.internal.ConnectivityUtils;
import blak.android.optimizing.internal.DockingState;
import blak.android.optimizing.NetworkDispatcher;

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
