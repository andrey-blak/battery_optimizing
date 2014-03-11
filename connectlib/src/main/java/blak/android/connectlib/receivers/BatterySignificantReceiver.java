package blak.android.connectlib.receivers;

import blak.android.utils.SimpleMessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BatterySignificantReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String batteryLevel = getBatteryLevel(intent);
        SimpleMessage.show(context, batteryLevel);
    }

    private static String getBatteryLevel(Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case Intent.ACTION_BATTERY_LOW:
                return "low";

            case Intent.ACTION_BATTERY_OKAY:
                return "ok";
        }
        throw new IllegalArgumentException();
    }
}
