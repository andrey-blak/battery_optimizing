package blak.android.connectlib.battery;

import blak.android.utils.SimpleMessage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryActivity extends Activity {
    @Override
    protected void onResume() {
        super.onResume();

        Intent batteryStatus = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        showChargingStatus(getApplicationContext(), batteryStatus);

        IntentFilter chargingFilter = new IntentFilter();
        chargingFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        chargingFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(mPowerConnectReceiver, chargingFilter);

        IntentFilter significantFilter = new IntentFilter();
        significantFilter.addAction(Intent.ACTION_BATTERY_LOW);
        significantFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        registerReceiver(mSignificantReceiver, significantFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //unregisterReceiver(mBatteryChangedIntent);
        unregisterReceiver(mPowerConnectReceiver);
        unregisterReceiver(mSignificantReceiver);
    }

    private static final BroadcastReceiver mPowerConnectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            String message = null;
            if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
                message = "connected";
            } else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                message = "disconnected";
            }
            SimpleMessage.show(context, message);
        }
    };

    private static final BroadcastReceiver mSignificantReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            String message = null;
            if (action.equals(Intent.ACTION_BATTERY_LOW)) {
                message = "low";
            } else if (action.equals(Intent.ACTION_BATTERY_OKAY)) {
                message = "ok";
            }
            SimpleMessage.show(context, message);
        }
    };

    private static void showChargingStatus(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        String usb = (usbCharge) ? "usb" : "";
        String ac = (acCharge) ? "ac" : "";

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float) scale;

        SimpleMessage.showLong(context, intent.getAction() + "charging" + " " + isCharging + " " + usb + " " + ac + " " + batteryPct);
    }
}
