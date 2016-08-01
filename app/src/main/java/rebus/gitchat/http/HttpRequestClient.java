package rebus.gitchat.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

import rebus.gitchat.Constants;
import rebus.gitchat.factory.user.UserAdapter;
import rebus.gitchat.factory.user.UserFactory;
import rebus.gitchat.http.response.CodeResponse;
import rebus.gitchat.http.response.SampleResponse;
import rebus.gitchat.http.response.TokenResponse;
import rebus.gitchat.http.response.gitter.message.Message;
import rebus.gitchat.http.response.gitter.message.UnreadMessage;
import rebus.gitchat.http.response.gitter.room.Room;
import rebus.gitchat.http.response.gitter.room.SuggestedRoom;
import rebus.gitchat.http.response.gitter.user.User;

/**
 * Created by Raphael on 17/12/2015.
 */
public class HttpRequestClient {

    private Context context;

    /**
     * @param context current context
     * @return current instance
     */
    public static HttpRequestClient with(Context context) {
        HttpRequestClient instance = new HttpRequestClient();
        instance.init(context);
        return instance;
    }

    /**
     * @param context init context
     */
    private void init(Context context) {
        this.context = context;
    }

    /**
     * @param futureCallback response callback
     */
    public void getGitterRooms(FutureCallback<List<Room>> futureCallback) {
        Ion.with(context)
                .load("GET", Constants.API_GITTER_BASE_URL + "/rooms")
                .setHeader("Authorization", UserAdapter.with(context, UserFactory.TYPE.GITTER).getUserToken())
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .as(new TypeToken<List<Room>>() {
                })
                .setCallback(futureCallback);
    }

    /**
     * @param id             room id
     * @param futureCallback response callback
     */
    public void getGitterRoom(String id, FutureCallback<Room> futureCallback) {
        Ion.with(context)
                .load("GET", Constants.API_GITTER_BASE_URL + "/rooms/" + id)
                .setHeader("Authorization", UserAdapter.with(context, UserFactory.TYPE.GITTER).getUserToken())
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .as(Room.class)
                .setCallback(futureCallback);
    }

    /**
     * @param id             room id
     * @param beforeId       get message before this id
     * @param afterId        get message after this id never pass afterId and beforeId between not null
     * @param futureCallback response callback
     */
    public void getGitterRoomMessages(String id, String beforeId, String afterId, FutureCallback<List<Message>> futureCallback) {
        String url = Constants.API_GITTER_BASE_URL + "/rooms/" + id + "/chatMessages";
        if (beforeId != null) url = url + "?beforeId=" + beforeId;
        if (afterId != null) url = url + "?afterId=" + afterId;
        Ion.with(context)
                .load("GET", url)
                .setHeader("Authorization", UserAdapter.with(context, UserFactory.TYPE.GITTER).getUserToken())
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .as(new TypeToken<List<Message>>() {
                })
                .setCallback(futureCallback);
    }

    /**
     * @param id             roomId
     * @param query          query
     * @param futureCallback response callback
     */
    public void getGitterUserMention(String id, String query, FutureCallback<List<User>> futureCallback) {
        String url = Constants.API_GITTER_BASE_URL + "/rooms/" + id + "/users?q=" + query;
        Ion.with(context)
                .load("GET", url)
                .setHeader("Authorization", UserAdapter.with(context, UserFactory.TYPE.GITTER).getUserToken())
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .as(new TypeToken<List<User>>() {
                })
                .setCallback(futureCallback);
    }

    /**
     * @param id             roomId where message will be send
     * @param text           just text message
     * @param futureCallback response callback
     */
    public void sendGitterMessage(String id, String text, FutureCallback<Message> futureCallback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", text);
        Ion.with(context)
                .load("POST", Constants.API_GITTER_BASE_URL + "/rooms/" + id + "/chatMessages")
                .setHeader("Authorization", UserAdapter.with(context, UserFactory.TYPE.GITTER).getUserToken())
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .setJsonObjectBody(jsonObject)
                .as(Message.class)
                .setCallback(futureCallback);
    }

    /**
     * @param futureCallback response callback
     */
    public void getGitterSuggestedRooms(FutureCallback<List<SuggestedRoom>> futureCallback) {
        Ion.with(context)
                .load("GET", Constants.API_GITTER_BASE_URL + "/user/me/suggestedRooms")
                .setHeader("Authorization", UserAdapter.with(context, UserFactory.TYPE.GITTER).getUserToken())
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .as(new TypeToken<List<SuggestedRoom>>() {
                })
                .setCallback(futureCallback);
    }

    /**
     * @param url            oauth 2.0 redirect url with auth code
     * @param futureCallback response callback
     */
    public void getCode(String url, FutureCallback<CodeResponse> futureCallback) {
        Ion.with(context)
                .load("GET", url)
                .as(CodeResponse.class)
                .setCallback(futureCallback);
    }

    /**
     * @param futureCallback response callback
     */
    public void getGitterUser(final FutureCallback<User> futureCallback) {
        Ion.with(context)
                .load("GET", Constants.API_GITTER_BASE_URL + "/user")
                .setHeader("Authorization", UserAdapter.with(context, UserFactory.TYPE.GITTER).getUserToken())
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .as(new TypeToken<List<User>>() {
                })
                .setCallback(new FutureCallback<List<User>>() {
                    @Override
                    public void onCompleted(Exception e, List<User> result) {
                        if (e != null) {
                            futureCallback.onCompleted(e, null);
                            return;
                        }
                        futureCallback.onCompleted(null, result.get(0));
                    }
                });
    }

    public void getGitterSingleMessage(String roomId, String messageId, FutureCallback<Message> futureCallback) {
        String url = Constants.API_GITTER_BASE_URL + "/rooms/" + roomId + "/chatMessages/" + messageId;
        Ion.with(context)
                .load("GET", url)
                .setHeader("Authorization", UserAdapter.with(context, UserFactory.TYPE.GITTER).getUserToken())
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .as(Message.class)
                .setCallback(futureCallback);
    }

    public void getGitterUnreadItems(String roomId, FutureCallback<UnreadMessage> futureCallback) {
        String userId = UserFactory.with(context, UserFactory.TYPE.GITTER).getUser().getId();
        String url = Constants.API_GITTER_BASE_URL + "/user/" + userId + "/rooms/" + roomId + "/unreadItems";
        Ion.with(context)
                .load("GET", url)
                .setHeader("Authorization", UserAdapter.with(context, UserFactory.TYPE.GITTER).getUserToken())
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .as(UnreadMessage.class)
                .setCallback(futureCallback);
    }

    public void deleteGitterUserFromRoom(String roomId, String userId, FutureCallback<SampleResponse> futureCallback) {
        String url = Constants.API_GITTER_BASE_URL + "/rooms/" + roomId + "/users/" + userId;
        Ion.with(context)
                .load("DELETE", url)
                .setHeader("Authorization", UserAdapter.with(context, UserFactory.TYPE.GITTER).getUserToken())
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .as(SampleResponse.class)
                .setCallback(futureCallback);
    }

    public void deleteGitterUserFromRoom(String roomId, FutureCallback<SampleResponse> futureCallback) {
        String userId = UserFactory.with(context, UserFactory.TYPE.GITTER).getUser().getId();
        deleteGitterUserFromRoom(roomId, userId, futureCallback);
    }

    public void setGitterUnreadItems(UnreadMessage unreadItems, String roomId, FutureCallback<SampleResponse> futureCallback) {
        String userId = UserFactory.with(context, UserFactory.TYPE.GITTER).getUser().getId();
        String url = Constants.API_GITTER_BASE_URL + "/user/" + userId + "/rooms/" + roomId + "/unreadItems";
        Ion.with(context)
                .load("POST", url)
                .setHeader("Authorization", UserAdapter.with(context, UserFactory.TYPE.GITTER).getUserToken())
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .setJsonPojoBody(unreadItems)
                .as(SampleResponse.class)
                .setCallback(futureCallback);
    }

    public void setGitterJoinRoom(String uri, FutureCallback<Room> futureCallback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uri", uri);
        String url = Constants.API_GITTER_BASE_URL + "/rooms/";
        Ion.with(context)
                .load("POST", url)
                .setHeader("Authorization", UserAdapter.with(context, UserFactory.TYPE.GITTER).getUserToken())
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .setJsonObjectBody(jsonObject)
                .as(Room.class)
                .setCallback(futureCallback);
    }

    public void getGitterToken(String code, FutureCallback<TokenResponse> futureCallback) {
        Ion.with(context)
                .load("POST", Constants.API_GITTER_OAUTH_TOKEN)
                .setTimeout(60000)
                .setBodyParameter("code", code)
                .setBodyParameter("client_id", Constants.API_GITTER_CLIENT_ID)
                .setBodyParameter("client_secret", Constants.API_GITTER_CLIENT_SECRET)
                .setBodyParameter("redirect_uri", Constants.API_GITTER_REDIRECT_URI)
                .setBodyParameter("grant_type", "authorization_code")
                .as(TokenResponse.class)
                .setCallback(futureCallback);
    }

}
