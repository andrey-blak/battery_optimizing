package blak.android.optimizing;

import android.app.Application;

public class ConnectApplication extends Application {
    private static ConnectApplication sInstance;

    private NetworkDispatcher mNetworkDispatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        mNetworkDispatcher = new NetworkDispatcher(getApplicationContext());
    }

    public static ConnectApplication getInstance() {
        return sInstance;
    }

    public NetworkDispatcher getNetworkDispatcher() {
        return mNetworkDispatcher;
    }
}
