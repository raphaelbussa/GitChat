package rebus.gitchat.http.response.gitter.room;

import java.io.Serializable;

/**
 * Created by Raphael on 21/12/2015.
 */
public class SuggestedRoom implements Serializable {

    private String id;
    private String uri;
    private String avatarUrl;
    private int userCount;
    private boolean exists;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

}
