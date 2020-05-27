package com.fayarretype.mymobilekitchen.tools.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class ServiceControl {

    public static boolean networkConnection(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() &&
                conMgr.getActiveNetworkInfo().isConnected();
    }
}
