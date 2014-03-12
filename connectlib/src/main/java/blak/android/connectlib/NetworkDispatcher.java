package blak.android.connectlib;

import blak.android.connectlib.internal.BatteryLevel;
import blak.android.connectlib.internal.ChargingType;
import blak.android.connectlib.internal.ConnectivityUtils;
import blak.android.connectlib.internal.DockingState;
import blak.android.connectlib.internal.NetworkType;
import blak.android.connectlib.receivers.BatterySignificantReceiver;
import blak.android.connectlib.receivers.DockingReceiver;
import blak.android.connectlib.receivers.PowerConnectReceiver;
import blak.android.utils.MyLog;
import blak.android.utils.SimpleMessage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collection;

public class NetworkDispatcher {
    private NetworkType mNetworkType;
    private ChargingType mChargingType;
    private BatteryLevel mBatteryLevel;
    private DockingState mDockingState;

    public NetworkDispatcher(Context context) {
        mNetworkType = getInitNetworkType(context);
        mChargingType = getInitChargingType(context);
        mBatteryLevel = getInitBatteryLevel();
        mDockingState = getInitDockingState(context);

        stateChanged("init");
    }

    private static NetworkType getInitNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return ConnectivityUtils.getNetworkType(activeNetwork);
    }

    private static ChargingType getInitChargingType(Context context) {
        return ConnectivityUtils.getChargingStatus(context);
    }

    private static BatteryLevel getInitBatteryLevel() {
        return BatteryLevel.OK;
    }

    private static DockingState getInitDockingState(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_DOCK_EVENT));
        return ConnectivityUtils.getDockingState(intent);
    }

    public void onNetworkTypeChange(Context context, NetworkType networkType) {
        mNetworkType = networkType;
        if (networkType == NetworkType.NONE) {
            disableReceivers(context);
            stateChanged("Network disconnected");
        } else {
            enableReceivers(context);
            stateChanged("Network connected");
        }
    }

    public void onBatterySignificantChange(BatteryLevel level) {
        mBatteryLevel = level;

        stateChanged("Battery significant");
    }

    public void onChargingConnected(ChargingType chargingType) {
        mChargingType = chargingType;

        stateChanged("Charging changed");
    }

    public void onDockingChange(DockingState dockingState) {
        mDockingState = dockingState;

        stateChanged("Docking");
    }

    // todo temp
    private void stateChanged(String changed) {
        String message = concat(changed, ":", mNetworkType, mChargingType, mBatteryLevel, mDockingState);

        SimpleMessage.show(ConnectApplication.getInstance(), message);
        MyLog.v(message);
    }

    // todo temp
    private static String concat(Object... tokens) {
        return TextUtils.join(" ", tokens);
    }

    private static void enableReceivers(Context context) {
        setComponentsState(context, PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
    }

    private static void disableReceivers(Context context) {
        setComponentsState(context, PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
    }

    private static void setComponentsState(Context context, int state) {
        PackageManager pm = context.getPackageManager();
        Collection<ComponentName> components = getComponents(context);
        for (ComponentName component : components) {
            pm.setComponentEnabledSetting(component, state, PackageManager.DONT_KILL_APP);
        }
    }

    private static Collection<ComponentName> getComponents(Context context) {
        Collection<ComponentName> components = new ArrayList<>();
        components.add(new ComponentName(context, PowerConnectReceiver.class));
        components.add(new ComponentName(context, BatterySignificantReceiver.class));
        components.add(new ComponentName(context, DockingReceiver.class));
        return components;
    }
}
