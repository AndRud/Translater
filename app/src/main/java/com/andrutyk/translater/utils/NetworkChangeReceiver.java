package com.andrutyk.translater.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.andrutyk.translater.ui.MainActivity;

/**
 * Created by admin on 22.07.2016.
 */
public class NetworkChangeReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = NetworkUtil.getConnectivityStatusString(context);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            try {
                MainActivity.getInstace().setTvInternetConn(status != NetworkUtil.NETWORK_STATUS_NOT_CONNECTED);
            } catch (Exception e) {

            }
        }
    }
}
