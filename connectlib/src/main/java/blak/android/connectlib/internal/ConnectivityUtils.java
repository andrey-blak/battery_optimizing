package blak.android.connectlib.internal;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;

public class ConnectivityUtils {
    private static final int NONE = -1;
    private static final int BATTERY_PLUGGED_NONE = 0;

    public static NetworkType getNetworkType(NetworkInfo activeNetwork) {
        if ((activeNetwork == null) || !activeNetwork.isConnectedOrConnecting()) {
            return NetworkType.NONE;
        }

        if (activeNetwork.isRoaming()) {
            return NetworkType.ROAMING;
        }

        switch (activeNetwork.getType()) {
            case ConnectivityManager.TYPE_WIFI:
                return NetworkType.WIFI;

            case ConnectivityManager.TYPE_MOBILE:
                return NetworkType.MOBILE;

            default:
                return NetworkType.UNKNOWN;
        }
    }

    public static BatteryLevel getBatteryLevel(Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case Intent.ACTION_BATTERY_LOW:
                return BatteryLevel.LOW;

            case Intent.ACTION_BATTERY_OKAY:
                return BatteryLevel.OK;
        }
        throw new IllegalArgumentException();
    }

    public static ChargingType getChargingStatus(Context context) {
        Intent batteryStatusIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int chargePlug = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, BATTERY_PLUGGED_NONE);
        switch (chargePlug) {
            case BATTERY_PLUGGED_NONE:
                return ChargingType.NONE;

            case BatteryManager.BATTERY_PLUGGED_USB:
                return ChargingType.USB;

            case BatteryManager.BATTERY_PLUGGED_AC:
                return ChargingType.AC;

            default:
                return ChargingType.UNKNOWN;
        }
    }

    public static DockingState getDockingState(Intent intent) {
        if (intent == null) {
            return DockingState.UNDOCKED;
        }
        int dockState = intent.getIntExtra(Intent.EXTRA_DOCK_STATE, NONE);
        switch (dockState) {
            case Intent.EXTRA_DOCK_STATE_UNDOCKED:
                return DockingState.UNDOCKED;

            case Intent.EXTRA_DOCK_STATE_CAR:
                return DockingState.CAR;

            case Intent.EXTRA_DOCK_STATE_DESK:
            case Intent.EXTRA_DOCK_STATE_LE_DESK:
            case Intent.EXTRA_DOCK_STATE_HE_DESK:
                return DockingState.DESK;
        }

        throw new IllegalArgumentException();
    }
}
