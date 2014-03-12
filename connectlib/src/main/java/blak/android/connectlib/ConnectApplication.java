package blak.android.connectlib;

import android.app.Application;

public class ConnectApplication extends Application {
    private static ConnectApplication sInstance;

    private NetworkDispatcher mNetworkDispatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        //mNetworkDispatcher = new NetworkDispatcher(getApplicationContext());
        mNetworkDispatcher = new NetworkDispatcher();
    }

    public static ConnectApplication getInstance() {
        return sInstance;
    }

    public NetworkDispatcher getNetworkDispatcher() {
        return mNetworkDispatcher;
    }
}
