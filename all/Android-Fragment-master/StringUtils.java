package com.pg.demowebservice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by test on 11/10/17.
 */

public class StringUtils {


    /**
     * will capitalize first later of the string
     *
     * @param string
     * @return
     */
    public static String getCapitalizeString(String string) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(string);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }
        return capMatcher.appendTail(capBuffer).toString();
    }   //end of getCapitalizeString

    /**
     * will capitalize whole string
     *
     * @param string
     * @return
     */
    public static String getFullyCapitalizeString(String string) {
        return string.toUpperCase();
    }   //end of getFullyCapitalizeString


    /**
     * will check is string is not null
     *
     * @param string
     * @return
     */
    public static boolean isStrNotNull(String string) {
        return string != null && !string.isEmpty();
    }   //end of isStrNotNull()


    /**
     * will append 3 dots in textview android
     *
     * @param limit
     * @param string
     * @return
     */
    public static String cutTextAndAppendDots(String string,int limit) {
        if (string.length() > limit) {
            return string.substring(0, limit - 3) + "...";
        } else {
            return string;
        }

    }   // end of cutText


    /**
     * will remove character from fix position
     *
     * @param string
     * @param index
     * @return
     */
    public static String deleteCharAt(String string, int index) {
        return string.substring(0, index) + string.substring(index + 1);
    }   //end of deleteCharAt


    /**
     * will remove character from fix position
     *
     * @param string
     * @param index
     * @return
     */
    public static String removeLaterFromStart(String string, int index) {
        if (isStrNotNull(string))
            return string.substring(index);
        else
            return "";
    }   //end of deleteCharAt


    /**
     * will remove character from end  position
     *
     * @param string
     * @param index
     * @return
     */
    public static String removeLaterFromEnd(String string, int index) {
        if (isStrNotNull(string))
            return string.substring(0, string.length() - index);
        else
            return "";
    }   //end of deleteCharAt

    /**
     * will get middle characters from string
     *
     * @param string
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static String getMiddleCharacters(String string, int startIndex, int endIndex) {
        if (isStrNotNull(string))
            return string.substring(startIndex, endIndex);
        else
            return "";
    }   //end of deleteCharAt


}
