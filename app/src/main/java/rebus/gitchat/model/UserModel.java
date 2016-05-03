package rebus.gitchat.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by raphaelbussa on 02/04/16.
 */
public class UserModel extends RealmObject {

    @PrimaryKey
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
