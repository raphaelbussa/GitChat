package rebus.gitchat.factory.user;

import android.content.Context;
import android.content.SharedPreferences;

import rebus.gitchat.R;

/**
 * Created by Raphael on 17/12/2015.
 */
public class UserAdapter {

    private final static String GITTER = R.class.getPackage().getName() + ".prefs.user.gitter";
    private final static String GITHUB = R.class.getPackage().getName() + ".prefs.user.github";
    private final static String KEY_USER_TOKEN = R.class.getPackage().getName() + ".user_token";
    private final static String KEY_USER_NAME = R.class.getPackage().getName() + ".user_name";
    private final static String KEY_USER_AVATAR = R.class.getPackage().getName() + ".user_avatar";
    private final static String KEY_USER_FULL_NAME = R.class.getPackage().getName() + ".user_full_name";
    private final static String KEY_USER_ID = R.class.getPackage().getName() + ".user_id";
    private static UserAdapter instance;
    private SharedPreferences sharedPreferences;

    public static UserAdapter with(Context context, UserFactory.TYPE type) {
        if (instance == null) {
            instance = new UserAdapter();
        }
        instance.initSharedPreferenceAdapter(context, type);
        return instance;
    }

    private void initSharedPreferenceAdapter(Context context, UserFactory.TYPE type) {
        switch (type) {
            case GITTER:
                this.sharedPreferences = context.getSharedPreferences(GITTER, 0);
                break;
            case GITHUB:
                this.sharedPreferences = context.getSharedPreferences(GITHUB, 0);
                break;
        }
    }

    public String getUserFullName() {
        return sharedPreferences.getString(KEY_USER_FULL_NAME, "");
    }

    public void setUserFullName(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_FULL_NAME, value);
        editor.apply();
    }

    public String getUserAvatar() {
        return sharedPreferences.getString(KEY_USER_AVATAR, "");
    }

    public void setUserAvatar(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_AVATAR, value);
        editor.apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, "");
    }

    public void setUserName(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_NAME, value);
        editor.apply();
    }

    public String getUserToken() {
        return sharedPreferences.getString(KEY_USER_TOKEN, "");
    }

    public void setUserToken(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_TOKEN, value);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, "");
    }

    public void setUserId(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, value);
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
