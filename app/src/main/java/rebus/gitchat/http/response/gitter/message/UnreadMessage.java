package rebus.gitchat.http.response.gitter.message;

import java.io.Serializable;

/**
 * Created by raphaelbussa on 22/01/16.
 */
public class UnreadMessage implements Serializable {

    private String[] chat;
    private String[] mention;

    public String[] getChat() {
        return chat;
    }

    public void setChat(String[] chat) {
        this.chat = chat;
    }

    public String[] getMention() {
        return mention;
    }

    public void setMention(String[] mention) {
        this.mention = mention;
    }
}
