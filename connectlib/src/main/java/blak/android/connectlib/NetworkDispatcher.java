package blak.android.connectlib;

import blak.android.connectlib.receivers.BatterySignificantReceiver;
import blak.android.connectlib.receivers.DockingReceiver;
import blak.android.connectlib.receivers.PowerConnectReceiver;
import blak.android.utils.MyLog;
import blak.android.utils.SimpleMessage;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collection;

public class NetworkDispatcher {
    private NetworkType mNetworkType;
    private ChargingType mChargingType;
    private BatteryLevel mBatteryLevel;
    private DockingState mDockingState;

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
