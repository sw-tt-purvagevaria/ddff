package com.css.bcg.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.css.bcg.R;
import com.css.bcg.datacontainer.PreferenceManager;
import com.css.bcg.receiver.MyAlarmReceiver;
import com.css.bcg.util.Loger;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/***************************************************************************************
 * class name :: ParentActivity
 * This is parent class of this project every activity of this project extends this class
 * It contain common  methods
 ***************************************************************************************/
public class ParentActivity extends AppCompatActivity implements View.OnClickListener {


    private String TAG = "ParentActivity";

    private Context ctx;
    private boolean on_back_press = false;

    public DrawerLayout drawerLayout;
    public View fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = this;
        on_back_press = false;


    }//end of onCreate()

    @Override
    protected void onStart() {
        super.onStart();
        if (!on_back_press) {
            overridePendingTransition(R.anim.left, R.anim.right);
            on_back_press = false;
        }
    }//end of onStart()

    //it will finish activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onPreviousView();
    }//end of onBackPressed()

    //initialize objects
    public void initObjects() {


    }

    //initialization of ui controls
    public void initUIControls() {


    }

    // will set font awsome back icon

    public void setBackIcon() {

    }


    //register components for click listener
    public void registerForListener() {

    }

    //set top bar components
    public void setTopBarComponents() {

    }

    /***
     * used when whit bordered button is to be se in top bor
     *
     * @param buttonText --> text which is to be set
     */
    public void setTopBarWithRoundButton(String buttonText) {

    } // end of setTopBarWithRoundButton()


    //set data to ui
    public void setUIData() {
    }

    //get data from intent
    public void getIntentData() {

    }

    //set list adapter
    public void setAdapter() {
    }


    public void noTransition() {
        overridePendingTransition(0, 0);
    }

    //it will take input as a string and check wether it is not null and  not empty and return true or false
    public boolean isStrNotNull(String string) {
        return string != null && !string.isEmpty();
    }//end of isStrNotNull()

    //this will call on backPress button and finish current activity
    public void onPreviousView() {
        on_back_press = true;
        finish();
        overridePendingTransition(R.anim.back_anim_left, R.anim.back_anim_right);
    }//end of onPreviousView()

    //this function will display input message in a toast
    public void showToast(String msg) {

        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout_id));

        // set a message
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(msg);

        // Toast...
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
        //  Toast.makeText(ParentActivity.this, msg, Toast.LENGTH_SHORT).show();
    }//end of showToast()

    public void showToastWithVibration(String msg) {
        Toast.makeText(ParentActivity.this, msg, Toast.LENGTH_SHORT).show();
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(3000);


    }

    //this method will release all memory of current activity
    public void releaseMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }//end of releaseMemory()

    //method will hide the soft input keyboard
    public void hideSoftKeyBoard(Context context) {
        // Check if no view has focus:
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }//end of hideSoftKeyBoard()

    //method hide the soft input keyboard
    public void hideSoftKeyBoard(EditText editText, Context context) {
        // Check if no view has focus:
        if (editText != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }//end of hideSoftKeyBoard()

    public void showKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }


    /**
     * setMarginsOfTopBar will set margin of text view
     *
     * @param view   -> view for which you want to set margin
     * @param left   -> left right top bottom margin
     * @param top
     * @param right
     * @param bottom
     */
    public void setMarginsOfTopBar(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMemory();
    }//end of onDestroy

    //set the white_cursor position of editText to last(end of last line)
    public void setEditTextCursorPosition(EditText editText) {
        editText.setSelection(editText.getText().length());
    }//end of setEditTextCursorPosition()


    //hide title bar (status bar)
    public void hideTitle() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //return current app package name
    public String getPackageName(Context context) {
        return context.getPackageName();
    }//end of getPackageName()


    //release progress dialog
    protected void releaseDialog(ProgressDialog pd) {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }//end of releaseDialog()


    @Override
    public void onClick(View view) {

    }


    /*************************************************************************
     * scrollToTop will scroll up the view
     *
     * @param scrollView
     ************************************************************************/
    public void scrollToTop(final ScrollView scrollView) {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }   // eof scrollToTop

    /*************************************************************************
     * scrollToTop will scroll up the view
     *
     * @param scrollView
     *************************************************************************/
    public void scrollToBottom(final ScrollView scrollView) {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }   // eof scrollToTop


    /***************************************************************************************
     * getCurrentTime() is used to get if device is phone or tablet
     * @return current startJourneyTime
     ****************************************************************************************/
    public static String getCurrentDateTime() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        //return simpleDateFormat.format(date);
        return "2016-12-20 12:36:33";


    }//getCurrentTime


    /***************************************************************************************
     * getCurrentTime() is used to get if device is phone or tablet
     * @return current startJourneyTime
     ****************************************************************************************/
    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return simpleDateFormat.format(date);

    }//getCurrentTime

    /***
     * encodeString
     * @param s
     * @return
     */

    public String encodeString(String s) {
        byte[] data = new byte[0];

        try {
            data = s.getBytes("UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            String base64Encoded = Base64.encodeToString(data, Base64.DEFAULT);

            return base64Encoded;

        }
    }

    /***
     *
     * @param encoded
     * @return
     */
    public String decodeString(String encoded) {
        byte[] dataDec = Base64.decode(encoded, Base64.DEFAULT);
        String decodedString = "";
        try {

            decodedString = new String(dataDec, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } finally {

            return decodedString;
        }
    }


    /**
     * setErrorToast will set dialog validation if quantity will decrease from min quantity
     *
     * @param quantity
     * @param minimumQuantity
     */
    public void setErrorToast(Integer quantity, Integer minimumQuantity) {
        if (quantity <= minimumQuantity) {
            showToast("Requires minimum quantity of " + minimumQuantity + " or more for this item!");
        }

    }   // end of setErrorToast


    /**
     * will allow user to eneter max 4 digits
     *
     * @param quantity
     */
    private void setForDigitValidation(int quantity) {
        if (quantity > 9999) {
            showToast("Max number of product reached ! ");
        }
    }   // end of setForDigitValidation


    /***
     * will decrease quantity on plus button click
     */
    public String getCurrentDataSql() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        //return simpleDateFormat.format(date);
        return simpleDateFormat.format(date);


    }   // end of decreaseQuantity


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
     * will show work out view or exercise view in bottom pannel
     *
     * @param hasExerciseView
     */
    protected void hasExerciseInBottom(boolean hasExerciseView) {
        TextView txtWorkOut = (TextView) findViewById(R.id.txtBottomWorkOut);
        View viewWorkOut = (View) findViewById(R.id.viewWorkOut);
        TextView txtExercise = (TextView) findViewById(R.id.txtBottomExercise);
        View viewExercise = (View) findViewById(R.id.viewExercise);
        if (hasExerciseView) {
            txtWorkOut.setVisibility(View.GONE);
            viewWorkOut.setVisibility(View.GONE);
            txtExercise.setVisibility(View.VISIBLE);
            viewExercise.setVisibility(View.VISIBLE);
        } else {
            txtWorkOut.setVisibility(View.VISIBLE);
            viewWorkOut.setVisibility(View.VISIBLE);
            txtExercise.setVisibility(View.GONE);
            viewExercise.setVisibility(View.GONE);
        }
    }   //end of hasExerciseInBottom


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
     * closeDrawer
     *
     * @param drawerLayout
     * @param view
     **/
    public void closeDrawer(DrawerLayout drawerLayout, View view) {
        if (drawerLayout.isDrawerOpen(view)) {
            drawerLayout.closeDrawer(view);
        }
    }//end of closeDrawer()


    /**
     * closeDrawer
     *
     * @param drawerLayout
     * @param view
     **/
    public void openDrawer(DrawerLayout drawerLayout, View view) {
        drawerLayout.openDrawer(view);

    }//end of closeDrawer()


    public void setStatusBarColor(String color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.parseColor(color));
        }
    }


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
     * will get imei number
     *
     * @return
     */
    protected String getIMEINumber() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId() + "";
    }   //end of getIMEINumber

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


    /**
     * will set reminder at 12 am
     */
    protected void setReminder(int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Calendar.YEAR); //2016
        calendar.set(Calendar.MONTH, Calendar.MONTH - 1); //6 =  7(july) because its 0 to 11
        calendar.set(Calendar.DAY_OF_MONTH, date);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 55);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        PendingIntent pendingIntent;
        Intent myIntent = new Intent(this, MyAlarmReceiver.class);
        myIntent.putExtra("strReminderText", "call alaram");

        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);//PendingIntent.FLAG_ONE_SHOT
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }   //end of setReminder

    /**
     * will set user image from picture path
     *
     * @param context
     */
    protected void setUserImageFromPreference(Context context, ImageView imageView) {
        Loger.d(TAG, "Path :: " + PreferenceManager.getUserProfilePicture(this));
        if (!PreferenceManager.getUserProfilePicture(context).isEmpty()) {
            Loger.d(TAG, "Path :: " + PreferenceManager.getUserProfilePicture(this));
            Picasso.with(this)
                    .load(PreferenceManager.getUserProfilePicture(this))
                    .placeholder(R.drawable.default_user)
                    .error(R.drawable.default_user)
                    .fit()
                    .into(imageView);
        } else {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.default_user));
        }
    }   //end of setUserImage




}//end of ParentActivity class
