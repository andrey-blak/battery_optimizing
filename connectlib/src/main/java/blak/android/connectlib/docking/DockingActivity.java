package blak.android.connectlib.docking;

import blak.android.utils.MyLog;
import blak.android.utils.SimpleMessage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class DockingActivity extends Activity {
    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(Intent.ACTION_DOCK_EVENT);
        Intent dockStatus = registerReceiver(mDockingReceiver, filter);
        showDocking(getApplicationContext(), dockStatus);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mDockingReceiver);
    }

    private static void showDocking(Context context, Intent intent) {
        if (intent == null) {
            return;
        }

        int dockState = intent.getIntExtra(Intent.EXTRA_DOCK_STATE, -1);
        boolean isDocked = dockState != Intent.EXTRA_DOCK_STATE_UNDOCKED;

        boolean isCar = dockState == Intent.EXTRA_DOCK_STATE_CAR;
        boolean isDesk = dockState == Intent.EXTRA_DOCK_STATE_DESK ||
                dockState == Intent.EXTRA_DOCK_STATE_LE_DESK ||
                dockState == Intent.EXTRA_DOCK_STATE_HE_DESK;
        SimpleMessage.show(context.getApplicationContext(), "docked", isDocked, "car", isCar, "desk", isDesk);
        MyLog.v("docked", isDocked, "car", isCar, "desk", isDesk);
    }

    private final BroadcastReceiver mDockingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showDocking(context, intent);
        }
    };
}
