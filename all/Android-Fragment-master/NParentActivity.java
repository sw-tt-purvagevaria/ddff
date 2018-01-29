package com.pg.demowebservice;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by test on 10/10/17.
 */

public class ParentActivity extends AppCompatActivity {
    private String TAG = "ParentActivity";


    //initialize objects
    public void initObjects() {


    }

    //initialization of ui controls
    public void initUIControls() {


    }


    //register components for click listener
    public void registerForListener() {

    }


    //set list adapter
    public void setAdapter() {
    }


    //it will take input as a string and check wether it is not null and  not empty and return true or false
    public boolean isStrNotNull(String string) {
        return string != null && !string.isEmpty();
    }//end of isStrNotNull()


    //this function will display input message in a toast
    public void showToast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        //  Toast.makeText(ParentActivity.this, msg, Toast.LENGTH_SHORT).show();
    }//end of showToast()


    //method will hide the soft input keyboard
    public void hideSoftKeyBoard(Context context) {
        // Check if no view has focus:
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }//end of hideSoftKeyBoard()


    public void showKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }


    /**
     * will append 3 dots in textview android
     *
     * @param limit
     * @param text
     * @return
     */
    public String cutText(int limit, String text) {
        if (text.length() > limit) {
            return text.substring(0, limit - 3) + "...";
        } else {
            return text;
        }

    }   // end of cutText

    /**
     * will remove character from fix position
     *
     * @param strValue
     * @param index
     * @return
     */
    public String deleteCharAt(String strValue, int index) {
        return strValue.substring(0, index) + strValue.substring(index + 1);

    }

    /**
     * will set app screen as a full screen mode
     */
    protected void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }   //end of setFullScreen


    /**
     * use for navigation
     *
     * @param activity ==> Name of activity
     * @param isFinish ==> Set value if want to finish activity or not
     */
    protected void navigateToActivity(Class activity, boolean isFinish) {
        Intent i = new Intent(this, activity);
        startActivity(i);
        if (isFinish) {
            finish();
        }

    }   //end of navigateToActivity


    /**
     * will check if current device is pre lollipop device or not
     * Its used for ripple drawable
     *
     * @return
     */
    protected boolean isPreLollipop() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            return false;
        } else {
            // do something for phones running an SDK before lollipop
            return true;
        }
    }   //end of isPreLollipop


    /**
     * will get device id
     *
     * @return
     */
    protected String getDeviceID() {
        return Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID) + "";
    }   //end of getDeviceID

    /**
     * will get device info
     *
     * @return
     */
    protected String getDeviceInfo() {
        String deviceInfo = "OS_VERSION :" + System.getProperty("os.version")
                + "SDK :" + android.os.Build.VERSION.SDK
                + "DEVICE :" + android.os.Build.DEVICE
                + "MODEL :" + android.os.Build.MODEL
                + "PRODUCT :" + android.os.Build.PRODUCT;
        return deviceInfo.replace(" ", "");
    }   //end of getDeviceInfo


    protected String getUserName(String firstName, String lastName) {
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1, firstName.length()).toLowerCase();
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1, lastName.length()).toLowerCase();

        return firstName + " " + lastName;
    }


}
