package com.hp.mss.print.helper;

/**
 * Created by hop on 25/02/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.hp.mss.print.activity.LoginActivity;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String status = NetworkUtil.getConnectivityStatusString(context);
        if(LoginActivity.btnSubmit != null)
        {
            switch (NetworkUtil.getConnectivityStatus(context))
            {
                case NetworkUtil.TYPE_NOT_CONNECTED:
                    Toast.makeText(context, status, Toast.LENGTH_LONG).show();
                    LoginActivity.btnSubmit.setVisibility(View.INVISIBLE);
                    break;
                case NetworkUtil.TYPE_MOBILE:
                case NetworkUtil.TYPE_WIFI:
                    LoginActivity.btnSubmit.setVisibility(View.VISIBLE);
                    break;
                default:
                    LoginActivity.btnSubmit.setVisibility(View.VISIBLE);
            }

        }
    }
}
