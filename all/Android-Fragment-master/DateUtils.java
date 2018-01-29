package com.css.bcg.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by test on 13/5/17.
 */

public class DateUtils {

    private static String TAG = "DateUtils";

    /***
     * will get all days of week
     * @return
     */
    public static String[] getAllDaysOfWeek() {
        String[] namesOfDays = {
                "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        return namesOfDays;
    }


    /***
     * will get all days of week
     * @return
     */
    public static String[] getAllFullDaysOfWeek() {
        String[] namesOfDays = {"monday", "tuesday", "wednesday",
                "thursday", "friday", "saturday", "sunday"};
        return namesOfDays;
    }


    public static String getDayOfWeek() {
        String weekDay = "";

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (Calendar.MONDAY == dayOfWeek) {
            weekDay = "monday";
        } else if (Calendar.TUESDAY == dayOfWeek) {
            weekDay = "tuesday";
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            weekDay = "wednesday";
        } else if (Calendar.THURSDAY == dayOfWeek) {
            weekDay = "thursday";
        } else if (Calendar.FRIDAY == dayOfWeek) {
            weekDay = "friday";
        } else if (Calendar.SATURDAY == dayOfWeek) {
            weekDay = "saturday";
        } else if (Calendar.SUNDAY == dayOfWeek) {
            weekDay = "sunday";
        }

        return weekDay;
    }

    public static String getDayOfWeekInSort() {
        String weekDay = "";
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (Calendar.MONDAY == dayOfWeek) {
            weekDay = "mon";
        } else if (Calendar.TUESDAY == dayOfWeek) {
            weekDay = "tue";
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            weekDay = "wed";
        } else if (Calendar.THURSDAY == dayOfWeek) {
            weekDay = "thu";
        } else if (Calendar.FRIDAY == dayOfWeek) {
            weekDay = "fri";
        } else if (Calendar.SATURDAY == dayOfWeek) {
            weekDay = "sat";
        } else if (Calendar.SUNDAY == dayOfWeek) {
            weekDay = "sun";
        }

        return weekDay.toUpperCase();


    }

    /***
     * will get all dates between range
     * @param startdate
     * @param enddate
     * @return
     */
    public static List<Integer> getDaysBetweenDates(Date startdate, Date enddate) {
        Loger.d(TAG, "startdate :: " + startdate + " enddate " + enddate);
        List<Integer> dates = new ArrayList<Integer>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate)) {
            Date result = calendar.getTime();
            Loger.d(TAG, "result :: " + result);
            dates.add(result.getDate());
            calendar.add(Calendar.DATE, 1);
        }

        Loger.d(TAG, "dates :: " + dates);
        return dates;
    }   //end of getDaysBetweenDates


    /***{@link java.util.IllegalFormatCodePointException }
     * will convert string to date
     * @param inputDate
     * @return
     */
    public static Date getDateFromString(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputDate);
        Date date = null;
        try {
            return date = inputFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }   //end of getDateFromString


    /**
     * this will convert the strDate format and return string
     *
     * @param inputDate
     * @param inputDateFormat
     * @param outputDateFormat
     * @return
     */
    public static String changeDateFormat(String inputDate, String inputDateFormat, String outputDateFormat) {
        // String inputPattern = "dd-MMM-yyyy";
        // String outputPattern = "yyyy-MM-dd";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormat);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormat);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(inputDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }//end of parseDate

    /**
     * will convert 24hrs time into AM PM
     *
     * @param time
     * @return
     */
    public static String convertTimeInAmPn(String time) {
        DateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("hh:mm a");
        try {

            return outputFormat.format(inputFormat.parse(time)) + "";
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
    }   //end of convertTimeInAmPn


    /**
     * will convert 24hrs time into AM PM
     *
     * @param format
     * @return
     */
    public static String getCurrentDate(String format) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(c.getTime());

    }   //end of convertTimeInAmPn


    public static String[] getCurrentWeek() {
        String[] days = new String[7];
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 1);
        // SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd.MM.yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("dd");

        for (int i = 0; i < 7; i++) {
            Loger.i("TAG ", "WEEK :: " + sdf.format(cal.getTime()));
            days[i] = sdf.format(cal.getTime());
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }
        return days;
    }


    public static String[] getCurrentWeekFullDate(String format) {
        String[] days = new String[7];
        Calendar cal = Calendar.getInstance();
        //cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);


        SimpleDateFormat sdf = new SimpleDateFormat(format);

        for (int i = 0; i < 7; i++) {
            Loger.i("TAG ", "getCurrentWeekFullDate :: " + sdf.format(cal.getTime()));
            days[i] = sdf.format(cal.getTime());
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }
        return days;
    }


    public static int getCurrentDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        Loger.d(TAG, "Day of week :: " + dayOfWeek);
        if (dayOfWeek != 0) {
            return dayOfWeek - 1;
        } else {
            return 0;
        }

    }


    public static int getCurrentTime() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.SECOND);
    }

    
     /**
     * will return weekend date of month
     */
    public static void getFullMonth() {
        ArrayList<String> allSaturday = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        Loger.d(TAG, "Maximum days of month :: " + maxDay);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd , EEEE");
        SimpleDateFormat dayOfWeek = new SimpleDateFormat("EEEE");
        Loger.d(TAG, "Before loop :: " + df.format(cal.getTime()));
        for (int i = 0; i < maxDay; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i + 1);
            //Loger.d(TAG, "All days :: " + df.format(cal.getTime()));
            Loger.d(TAG, "WeekName  :: " + dayOfWeek.format(cal.getTime()));
            if (dayOfWeek.format(cal.getTime()).equalsIgnoreCase("Saturday")) {
                Loger.d(TAG, "Saturday :: " + df.format(cal.getTime()));
                allSaturday.add(df.format(cal.getTime()));

            }
        }
        getOddSaturday(allSaturday);
        getEvenSaturday(allSaturday);
        getAllSaturday(allSaturday);
    }   //end of getWeekEndDate


    /**
     * will return weekend date of month
     */
    public static void getAllSaturday(ArrayList<String> allSaturday) {
        Loger.d(TAG, "Total saturday of month:: " + allSaturday.size());
        for (int i = 0; i < allSaturday.size(); i++) {
            if (i % 2 == 0) {
                Loger.d(TAG, "Odd saturday :: " + allSaturday.get(i) + " index :: " + i + " result :: " + i % 2);
            } else {
                Loger.d(TAG, "Even saturday :: " + allSaturday.get(i) + " index :: " + i + " result :: " + i % 2);
            }
        }
    }   //end of getOddSaturday

    /**
     * will return weekend date of month
     */
    public static void getEvenSaturday(ArrayList<String> allSaturday) {
        Loger.d(TAG, "Total saturday of month:: " + allSaturday.size());

        if (allSaturday.size() == 4) {
            Loger.d(TAG, "First :: " + allSaturday.get(0) + " Third :: " + allSaturday.get(2));
        } else {    //there are 5 saturdays in month
            Loger.d(TAG, "First :: " + allSaturday.get(0) + " Third :: " + allSaturday.get(2) + " Fifth :: " + allSaturday.get(4));
        }

    }   //end of getOddSaturday


    /**
     * will return weekend date of month
     */
    public static void getOddSaturday(ArrayList<String> allSaturday) {
        Loger.d(TAG, "Second  :: " + allSaturday.get(1) + " Forth :: " + allSaturday.get(3));
    }   //end of getEvenSaturday
}
