package rebus.gitchat.http.response.gitter.user;

import java.io.Serializable;

/**
 * Created by Raphael on 21/12/2015.
 */
public class User implements Serializable {

    private String id;
    private String username;
    private String displayName;
    private String url;
    private String avatarUrlMedium;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatarUrlMedium() {
        return avatarUrlMedium;
    }

    public void setAvatarUrlMedium(String avatarUrlMedium) {
        this.avatarUrlMedium = avatarUrlMedium;
    }

}
