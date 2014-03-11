package blak.android.connectlib.receivers;

import blak.android.utils.MyLog;
import blak.android.utils.SimpleMessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class PowerConnectReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        boolean isConnected = isConnected(intent);

        if (isConnected) {
            Intent batteryStatusIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            String chargingType = getChargingType(batteryStatusIntent);

            SimpleMessage.show(context, "power connected", chargingType);
            MyLog.v("power connected", chargingType);
        } else {
            SimpleMessage.show(context, "power disconnected");
            MyLog.v("power disconnected");
        }
    }

    public static boolean isConnected(Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
            return true;
        }
        if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            return false;
        }
        throw new IllegalArgumentException();
    }

    public static String getChargingType(Intent batteryStatusIntent) {
        int chargePlug = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        switch (chargePlug) {
            case BatteryManager.BATTERY_PLUGGED_USB:
                return "usb";

            case BatteryManager.BATTERY_PLUGGED_AC:
                return "ac";
        }
        throw new IllegalArgumentException();
    }
}
