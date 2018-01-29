package com.pg.testdemo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by test on 11/10/17.
 */

public class WebServiceHelper {

    private static String TAG = "WebServiceHelper";
    public static String STATUS;
    public static String MESSAGE;
    public static String URL = "";
    private static JSONObject HEADER;
    public static JSONObject DATA_OBJECT;
    public static JSONArray DATA_ARRAY;

    private static Context context;
    private RequestQueue mRequestQueue;


    /**********************************************************************************************
     * WebServiceHelper
     *
     * @param context
     **********************************************************************************************/
    public WebServiceHelper(Context context) {
        this.context = context;
        mRequestQueue = Volley.newRequestQueue(context);

    }//end of WebServiceHelper()


    

    /**********************************************************************************************
     * call ws and get the json data from ws
     *
     * @param postUrl
     * @param handler
     ***********************************************************************************************/
    public void callWS(String postUrl, final HashMap<String, String> strParams, final JSONRequestHandlerPost handler) {
        urlForLog(postUrl, strParams);
        if (!isNetworkAvailable(context)) {
            handler.networkNotAvailable(false);
            return;
        } else {
            handler.networkNotAvailable(true);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            handler.onSuccess(response);//send event to handler class


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handler.onFailure(error);
                            if (error instanceof TimeoutError || error instanceof NetworkError) {
                                // Toast.makeText(context, "Time out error", Toast.LENGTH_SHORT).show();
                                handler.timeOutError();
                            }
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
               /* Map<String, String> param = new HashMap<String, String>();
                String data = encodeKeyValues(strParams);
                param.put("data", data);
                Loger.d(TAG,"Data :: "+data);*/
                    return strParams;
                }
            };
            mRequestQueue = Volley.newRequestQueue(context);
            int socketTimeout = 15000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            mRequestQueue.add(stringRequest);
        }


    }//end of class parseObject()


    /**
     * this is temporary method to see the logs like get method
     *
     * @return
     */
    private void urlForLog(String POSTUL, HashMap<String, String> params) {
        String data = encodeKeyValues(params);
        Log.e(TAG, "URL AS A GET :: " + POSTUL + "?" + data);
        URL = data;


    }

    /**
     * thus method will give the encoded data url from nameVale pair
     *
     * @param urlNameValue
     * @return
     */
    private String encodeKeyValues(HashMap<String, String> urlNameValue) {
        StringBuilder apiUrl = new StringBuilder();
        //append key values to url
        int i = 0;
        for (Map.Entry<String, String> entry : urlNameValue.entrySet()) {
            if (i == 0) {
                apiUrl.append("" + entry.getKey() + "=" + entry.getValue());
            } else {
                apiUrl.append("&" + entry.getKey() + "=" + entry.getValue());
            }
            i++;
        }
        //return encodeString(apiUrl.toString());
        return apiUrl.toString();
    }//end of getApiUrl()


    /***
     * encode url string
     *
     * @param url
     * @return
     */
    private String encodeString(String url) {
        byte[] data = new byte[0];

        try {
            data = url.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return Base64.encodeToString(data, Base64.NO_WRAP);
        }
    }//end of encodeString()

    /**********************************************************************************************
     * @param response
     * @return
     * @throws JSONException
     ***********************************************************************************************/

    public JSONObject getHeaderObject(JSONObject response) throws JSONException {
        return response.optJSONObject("header");
    }//end of getHeaderObject

    /**********************************************************************************************
     * @param jsonHeader
     * @return
     * @throws JSONException
     ***********************************************************************************************/
    public String getMessage(JSONObject jsonHeader) throws JSONException {
        return jsonHeader.optString("message");
    }//end of getMessage

    /**********************************************************************************************
     * @param jsonHeader
     * @return
     * @throws JSONException
     ***********************************************************************************************/
    public String getStatus(JSONObject jsonHeader) throws JSONException {
        return jsonHeader.optString("status");
    }//end of getStatus

    /**********************************************************************************************
     * @param response
     * @return
     * @throws JSONException
     ***********************************************************************************************/
    public JSONObject getDataObject(JSONObject response) throws JSONException {
        return response.optJSONObject("data");
    }//end of getDataObject

    /**********************************************************************************************
     * @param response
     * @return
     * @throws JSONException
     ***********************************************************************************************/
    public JSONArray getDataArray(JSONObject response) throws JSONException {
        return response.optJSONArray("data");
    }//end of getDataArray

    /***********************************************************************************************
     * parse the jsonObject and get all data inside json header
     *
     * @param response
     ***********************************************************************************************/
    public void parseJSONOResponse(String response) {
        if (response != null) {
            try {
                JSONObject objResponse = new JSONObject(response);
                HEADER = objResponse.optJSONObject("header");
                DATA_OBJECT = objResponse.optJSONObject("data");
                DATA_ARRAY = objResponse.optJSONArray("data");
                if (HEADER != null) {
                    STATUS = HEADER.optString("status");
                    MESSAGE = HEADER.optString("message");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//end of parseJSONOResponse


    /***********************************************************************************************
     * thus method will give the url from nameVale pair
     *
     * @param urlNameValue
     * @param postUrl
     * @return
     **********************************************************************************************/
    public static String getApiUrl(HashMap<String, String> urlNameValue, String postUrl) {
        StringBuilder apiUrl = new StringBuilder(postUrl);

        //add ? end of url
        if (!postUrl.contains("?")) {
            apiUrl.append("?");
        }

        //append key values to url
        for (Map.Entry<String, String> entry : urlNameValue.entrySet()) {
            apiUrl.append("&" + entry.getKey() + "=" + entry.getValue());
        }

        return apiUrl.toString();
    }//end of getApiUrl()


    /***********************************************************************************************
     * class name :: JSONRequestHandlerPost
     * this will call ws and get response
     ***********************************************************************************************/
    public static abstract class JSONRequestHandlerPost {
        public abstract void onSuccess(String response);

        public void networkNotAvailable(boolean isNetworkAvailable) {

        }

        public void onFailure(VolleyError error) {
            //showAlertDialog(context.getResources().getString(R.string.admin_error));

            if (error instanceof TimeoutError) {
                Log.e(TAG, "TimeoutError");
                timeOutError();
                //Toast.makeText(context, "Time out error", Toast.LENGTH_SHORT).show();
            } else if (error instanceof NoConnectionError) {
                Log.e(TAG, "tNoConnectionError");
            } else if (error instanceof AuthFailureError) {
                Log.e(TAG, "AuthFailureError");
            } else if (error instanceof NetworkError) {
                Log.e(TAG, "NetworkError");

            } else if (error instanceof ParseError) {
                Log.e(TAG, "ParseError");
            }
        }

        public void timeOutError() {
            Toast.makeText(context, "Timeout error", Toast.LENGTH_SHORT).show();
        }


    }//end of class JSONRequestHandlerPost


    //this function will display input message in a toast
    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        //  Toast.makeText(ParentActivity.this, msg, Toast.LENGTH_SHORT).show();
    }//end of showToast()


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


}//end 0f class WebServiceHelper
