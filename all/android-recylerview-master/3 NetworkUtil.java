package com.pg.demowebservice.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/***************************************************************************************************
 * Class Name :: NetworkUtil
 * Functionality  ::
 * 1) check internet is connected or not
 * 2) check connection type(wifi or mobile data)
 ***************************************************************************************************/
public class NetworkUtil {

    private static int TYPE_WIFI = 1;
    private static int TYPE_MOBILE = 2;
    private static int TYPE_NOT_CONNECTED = 0;

    /***********************************************************************************************
     * this method will check internet connection is available or not
     *
     * @param context
     * @return
     ***********************************************************************************************/
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }//end of isNetworkAvailable

}//end of NetworkUtil class
