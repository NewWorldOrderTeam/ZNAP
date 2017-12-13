package com.znap.lmr.lmr_znap;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zava on 12.12.2017.
 */

public class Validations {
    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "[a-zA-Z0-9]{8,24}";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidFirstName(final String first_name) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "[А-Яа-я]";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(first_name);
        return matcher.matches();
    }

    public static boolean isValidMiddleName(final String last_name) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "[А-Яа-я]";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(last_name);
        return matcher.matches();
    }

    public static boolean isValidLastName(final String middle_name) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "[А-Яа-я]";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(middle_name);
        return matcher.matches();
    }

    public static boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }


}
