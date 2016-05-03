package rebus.gitchat;

import org.fingerlinks.mobile.android.navigator.utils.Constant;

/**
 * Created by Raphael on 19/12/2015.
 */
public class Constants {

    public static final String TAG_PROFILE = "TAG_PROFILE";
    public static final String TAG_ROOMS = "TAG_ROOMS";
    public static final String TAG_SUGGESTED_ROOMS = "TAG_SUGGESTED_ROOMS";

    public static String API_GITTER_BASE_URL = "https://api.gitter.im/v1";
    public static String API_GITTER_BASE_URL_STREAM = "https://stream.gitter.im/v1";
    public static String API_GITTER_OAUTH_CODE = "https://gitter.im/login/oauth/authorize";
    public static String API_GITTER_OAUTH_TOKEN = "https://gitter.im/login/oauth/token";
    public static String API_GITTER_CLIENT_ID = "API_GITTER_CLIENT_ID";
    public static String API_GITTER_CLIENT_SECRET = "API_GITTER_CLIENT_SECRET";
    public static String API_GITTER_REDIRECT_URI = "API_GITTER_REDIRECT_URI";

    public static String API_GITHUB_BASE_URL = "https://api.github.com";
    public static String API_GITHUB_OAUTH_CODE = "https://github.com/login/oauth/authorize";
    public static String API_GITHUB_OAUTH_TOKEN = "https://github.com/login/oauth/access_token";
    public static String API_GITHUB_CLIENT_ID = "API_GITHUB_CLIENT_ID";
    public static String API_GITHUB_CLIENT_SECRET = "API_GITHUB_CLIENT_SECRET";
    public static String API_GITHUB_REDIRECT_URI = "API_GITHUB_REDIRECT_URI";

    /**
     * @return Navigator BUNDLE
     */
    public static String BUNDLE() {
        return Constant.BUNDLE;
    }

    public static String getApiGitterOauthCode() {
        return API_GITTER_OAUTH_CODE +
                "?client_id=" + API_GITTER_CLIENT_ID +
                "&redirect_uri=" + API_GITTER_REDIRECT_URI +
                "&response_type=code";
    }

    public static String getApiGithubOauthCode() {
        return API_GITHUB_OAUTH_CODE +
                "?client_id=" + API_GITHUB_CLIENT_ID +
                "&redirect_uri=" + API_GITHUB_REDIRECT_URI +
                "&response_type=code";
    }

}