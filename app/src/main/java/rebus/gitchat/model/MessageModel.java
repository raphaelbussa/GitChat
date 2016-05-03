package rebus.gitchat.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by raphaelbussa on 02/04/16.
 */
public class MessageModel extends RealmObject {

    @PrimaryKey
    private String id;
    private String room_id;
    private String text;
    private String html;
    private String sent;
    private UserModel fromUser;
    private boolean unread;
    private int readBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
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

    public UserModel getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserModel fromUser) {
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
