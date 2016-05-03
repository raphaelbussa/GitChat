package rebus.gitchat.factory.user;

import java.io.Serializable;

/**
 * Created by Raphael on 24/12/2015.
 */
public class UserBean implements Serializable {

    private String token;
    private String name;
    private String avatar;
    private String fullName;
    private String id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
