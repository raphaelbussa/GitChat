package rebus.gitchat.factory.user;

import android.content.Context;
import android.text.TextUtils;

import com.koushikdutta.async.future.FutureCallback;

import rebus.gitchat.http.HttpRequestClient;
import rebus.gitchat.http.response.gitter.user.User;

/**
 * Created by Raphael on 17/12/2015.
 */
public class UserFactory {

    private static UserFactory instance;
    private Context context;
    private TYPE type;

    public static UserFactory with(Context context, TYPE type) {
        if (instance == null) {
            instance = new UserFactory();
        }
        instance.initUserFactory(context, type);
        return instance;
    }

    private void initUserFactory(Context context, TYPE type) {
        this.context = context;
        this.type = type;
    }

    public boolean isLogged() {
        return (!TextUtils.isEmpty(UserAdapter.with(context, type).getUserToken()));
    }

    public UserBean getUser() {
        UserBean userBean = new UserBean();
        userBean.setName(UserAdapter.with(context, type).getUserName());
        userBean.setAvatar(UserAdapter.with(context, type).getUserAvatar());
        userBean.setFullName(UserAdapter.with(context, type).getUserFullName());
        userBean.setToken(UserAdapter.with(context, type).getUserToken());
        userBean.setId(UserAdapter.with(context, type).getUserId());
        return userBean;
    }

    public void getUserUpdated(final UserCallBack callBack) {
        switch (type) {
            case GITTER:
                HttpRequestClient.with(context).getGitterUser(new FutureCallback<User>() {
                    @Override
                    public void onCompleted(Exception e, User result) {
                        if (e != null) {
                            callBack.onError();
                            return;
                        }
                        UserAdapter.with(context, type).setUserAvatar(result.getAvatarUrlMedium());
                        UserAdapter.with(context, type).setUserFullName(result.getDisplayName());
                        UserAdapter.with(context, type).setUserName(result.getUsername());
                        UserAdapter.with(context, type).setUserId(result.getId());
                        callBack.onSuccess(getUser());
                    }
                });
                return;
            default:
                callBack.onError();
        }
    }

    public void logOut(LogOutCallBack callBack) {
        UserAdapter.with(context, type).clear();
        callBack.onSuccess();
    }

    public void logIn(String token, final UserCallBack callBack) {
        UserAdapter.with(context, type).setUserToken(token);
        switch (type) {
            case GITTER:
                HttpRequestClient.with(context).getGitterUser(new FutureCallback<User>() {
                    @Override
                    public void onCompleted(Exception e, User result) {
                        if (e != null) {
                            callBack.onError();
                            return;
                        }
                        UserAdapter.with(context, type).setUserAvatar(result.getAvatarUrlMedium());
                        UserAdapter.with(context, type).setUserFullName(result.getDisplayName());
                        UserAdapter.with(context, type).setUserName(result.getUsername());
                        UserAdapter.with(context, type).setUserId(result.getId());
                        callBack.onSuccess(getUser());
                    }
                });
                return;
            default:
                callBack.onError();
        }
    }

    public enum TYPE {
        GITTER, GITHUB
    }

    public interface UserCallBack {
        void onError();

        void onSuccess(UserBean user);
    }

    public interface LogOutCallBack {
        void onSuccess();
    }

}
