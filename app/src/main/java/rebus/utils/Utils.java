package rebus.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Weeks;
import org.joda.time.Years;

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

    @SuppressLint("SimpleDateFormat")
    public static String dateRelative(Context context, String pubDate) {
        try {
            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dateFormat.setTimeZone(timeZone);
            Date date = dateFormat.parse(pubDate);
            DateTime start = new DateTime(date);
            DateTime end = new DateTime(System.currentTimeMillis());
            int seconds = Seconds.secondsBetween(start, end).getSeconds();
            int minutes = Minutes.minutesBetween(start, end).getMinutes();
            int hours = Hours.hoursBetween(start, end).getHours();
            int days = Days.daysBetween(start, end).getDays();
            int weeks = Weeks.weeksBetween(start, end).getWeeks();
            int months = Months.monthsBetween(start, end).getMonths();
            int years = Years.yearsBetween(start, end).getYears();
            if (years > 0) {
                if (years == 1) {
                    return context.getString(R.string.year, years);
                } else {
                    return context.getString(R.string.years, years);
                }
            }
            if (months > 0) {
                if (months == 1) {
                    return context.getString(R.string.month, months);
                } else {
                    return context.getString(R.string.months, months);
                }
            }
            if (weeks > 0) {
                if (weeks == 1) {
                    return context.getString(R.string.week, weeks);
                } else {
                    return context.getString(R.string.weeks, weeks);
                }
            }
            if (days > 0) {
                if (days == 1) {
                    return context.getString(R.string.day, days);
                } else {
                    return context.getString(R.string.days, days);
                }
            }
            if (hours > 0) {
                if (hours == 1) {
                    return context.getString(R.string.hour, hours);
                } else {
                    return context.getString(R.string.hours, hours);
                }
            }
            if (minutes > 0) {
                if (minutes == 1) {
                    return context.getString(R.string.minute, minutes);
                } else {
                    return context.getString(R.string.minutes, minutes);
                }
            }
            if (seconds > 0) {
                if (seconds == 1) {
                    return context.getString(R.string.second, seconds);
                } else {
                    return context.getString(R.string.seconds, seconds);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return context.getString(R.string.just_now);
        }
        return context.getString(R.string.just_now);
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