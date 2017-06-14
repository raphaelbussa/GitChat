package rebus.gitchat;

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
    public static String API_GITTER_CLIENT_ID = "2e2e4831c088bf7439817f2b8033e571fa8224d0";
    public static String API_GITTER_CLIENT_SECRET = "271c19b4d977f9e29563432a9dc8ea4de46c894d";
    public static String API_GITTER_REDIRECT_URI = "http://rebus007.altervista.org/api/gitter/gitchat.php";

    public static String API_GITHUB_BASE_URL = "https://api.github.com";

    public static String getApiGitterOauthCode() {
        return API_GITTER_OAUTH_CODE +
                "?client_id=" + API_GITTER_CLIENT_ID +
                "&redirect_uri=" + API_GITTER_REDIRECT_URI +
                "&response_type=code";
    }

}
