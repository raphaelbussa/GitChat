package rebus.gitchat.http.response.gitter.message;

import java.io.Serializable;

import rebus.gitchat.http.response.gitter.user.User;

/**
 * Created by Raphael on 21/12/2015.
 */
public class Message implements Serializable {

    private String id;
    private String text;
    private String html;
    private String sent;
    private User fromUser;
    private boolean unread;
    private int readBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public int getReadBy() {
        return readBy;
    }

    public void setReadBy(int readBy) {
        this.readBy = readBy;
    }
}
