package blak.android.connectlib.receivers;

import blak.android.utils.MyLog;
import blak.android.utils.SimpleMessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class DockingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }

        int dockState = intent.getIntExtra(Intent.EXTRA_DOCK_STATE, -1);
        boolean isDocked = dockState != Intent.EXTRA_DOCK_STATE_UNDOCKED;

        if (!isDocked) {
            SimpleMessage.show(context.getApplicationContext(), "undocked");
            MyLog.v("undocked");
            return;
        }

        String type = getDockingType(dockState);

        SimpleMessage.show(context.getApplicationContext(), "docked", type);
        MyLog.v("docked", type);
    }

    private static String getDockingType(int dockState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return getDockingTypeHoneycomb(dockState);
        } else {
            return getDockingTypePrehoneycomb(dockState);
        }
    }

    private static String getDockingTypeHoneycomb(int dockState) {
        switch (dockState) {
            case Intent.EXTRA_DOCK_STATE_CAR:
                return "car";

            case Intent.EXTRA_DOCK_STATE_DESK:
            case Intent.EXTRA_DOCK_STATE_LE_DESK:
            case Intent.EXTRA_DOCK_STATE_HE_DESK:
                return "desk";
        }

        throw new IllegalArgumentException();
    }

    private static String getDockingTypePrehoneycomb(int dockState) {
        switch (dockState) {
            case Intent.EXTRA_DOCK_STATE_CAR:
                return "car";

            case Intent.EXTRA_DOCK_STATE_DESK:
                return "desk";
        }

        throw new IllegalArgumentException();
    }
}
