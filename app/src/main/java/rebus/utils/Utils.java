package rebus.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import rebus.gitchat.R;

/**
 * Created by Raphael on 13/12/2015.
 */
public class Utils {

    public static MaterialDialog loadingDialog(Context context) {
        return  new MaterialDialog.Builder(context)
                .content(R.string.loading)
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    public static String getAvatarUrl(String name, boolean isUrl) {
        if (name == null) return "";
        String[] parse = name.split("/");
        try {
            if (isUrl) {
                return "https://avatars.githubusercontent.com/" + parse[1];
            } else {
                return "https://avatars.githubusercontent.com/" + parse[0];
            }
        } catch (Exception e) {
            return "";
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String dateLong(String pubDate) {
        try {
            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dateFormat.setTimeZone(timeZone);
            Date date = dateFormat.parse(pubDate);
            return String.valueOf(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String dateConverted(String pubDate) {
        try {
            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dateFormat.setTimeZone(timeZone);
            Date date = dateFormat.parse(pubDate);
            DateFormat outDateFormat = new SimpleDateFormat("d MMM - HH:mm");
            return outDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

}