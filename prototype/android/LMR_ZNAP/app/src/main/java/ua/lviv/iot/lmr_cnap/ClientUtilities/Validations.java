package ua.lviv.iot.lmr_cnap.ClientUtilities;

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
        if (password.length() < 8){
            return false;
        }
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "[a-zA-Z0-9]{8,24}";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidFirstName(final String first_name) {
        if (first_name.length() < 1){
            return false;
        }
        Pattern pattern;
        Matcher matcher;
        final String FIRST_NAME_PATTERN = "[А-Яа-я]+|[a-zA-Z]+";
        pattern = Pattern.compile(FIRST_NAME_PATTERN);
        matcher = pattern.matcher(first_name);
        return matcher.matches();
    }

    public static boolean isValidMiddleName(final String middle_name) {
        if (middle_name.length() < 3 ){
            return false;
        }
        Pattern pattern;
        Matcher matcher;
        final String MIDDLE_NAME_PATTERN = "[А-Яа-я]+|[a-zA-Z]+";
        pattern = Pattern.compile(MIDDLE_NAME_PATTERN);
        matcher = pattern.matcher(middle_name);
        return matcher.matches();
    }

    public static boolean isValidLastName(final String last_name) {
        if (last_name.length() < 3 ){
            return false;
        }
        Pattern pattern;
        Matcher matcher;
        final String LAST_NAME_PATTERN = "[А-Яа-я]+|[a-zA-Z]+";
        pattern = Pattern.compile(LAST_NAME_PATTERN);
        matcher = pattern.matcher(last_name);
        return matcher.matches();
    }

    public static boolean isValidEmail(final String email) {
        if (email.length() < 6 ){
            return false;
        }
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "[a-zA-Z0-9]+@[a-zA-Z]+\\.[A-Za-z]{2,4}";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidPhoneNumber(final String phone_number) {
        Pattern pattern;
        Matcher matcher;
        final String PHONE_NUMBER_PATTERN = "\\+[0-9]{12}";
        pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        matcher = pattern.matcher(phone_number);
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
