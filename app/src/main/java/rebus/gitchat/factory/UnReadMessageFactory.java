package rebus.gitchat.factory;

import android.content.Context;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rebus.gitchat.http.HttpRequestClient;
import rebus.gitchat.http.response.gitter.message.Message;
import rebus.utils.Utils;

/**
 * Created by raphaelbussa on 22/01/16.
 */
public class UnReadMessageFactory {

    private static UnReadMessageFactory instance;
    private Context context;
    private List<Message> messageList;
    private String[] messages;
    private String room;
    private CallBack callBack;
    private int currentItem = 0;

    public static UnReadMessageFactory with(Context context) {
        instance = new UnReadMessageFactory();
        instance.init(context);
        return instance;
    }

    /**
     * @param context init context
     */
    private void init(Context context) {
        this.context = context;
        this.messageList = new ArrayList<>();
    }

    public UnReadMessageFactory setRoom(String room) {
        this.room = room;
        return this;
    }

    public UnReadMessageFactory setMessages(String[] messages) {
        this.messages = messages;
        return this;
    }

    public void get(CallBack callBack) {
        this.callBack = callBack;
        start();
    }

    private void start() {
        if (messages.length == 0) {
            callBack.onFinish(true, messageList);
            return;
        }
        getSingleMessage();
    }

    private void getSingleMessage() {
        HttpRequestClient.with(context).getGitterSingleMessage(room, messages[currentItem], new FutureCallback<Message>() {
            @Override
            public void onCompleted(Exception e, Message result) {
                if (e != null) {
                    callBack.onFinish(false, null);
                    return;
                }
                messageList.add(result);
                currentItem++;
                if (messages.length == currentItem) {
                    Collections.sort(messageList, new Comparator<Message>() {
                        @Override
                        public int compare(Message lhs, Message rhs) {
                            String a = Utils.dateLong(lhs.getSent());
                            String b = Utils.dateLong(rhs.getSent());
                            return a.compareTo(b);
                        }
                    });
                    callBack.onFinish(true, messageList);
                } else {
                    getSingleMessage();
                }
            }
        });
    }

    public interface CallBack {
        void onFinish(boolean success, List<Message> messageList);
    }


}
