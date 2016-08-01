package rebus.gitchat.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.koushikdutta.async.future.FutureCallback;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.ActionItemBadgeAdder;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.fingerlinks.mobile.android.navigator.Navigator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.Sort;
import rebus.gitchat.Constants;
import rebus.gitchat.R;
import rebus.gitchat.factory.UnReadMessageFactory;
import rebus.gitchat.http.HttpRequestClient;
import rebus.gitchat.http.response.SampleResponse;
import rebus.gitchat.http.response.gitter.message.Message;
import rebus.gitchat.http.response.gitter.message.UnreadMessage;
import rebus.gitchat.http.response.gitter.room.Room;
import rebus.gitchat.http.response.gitter.user.User;
import rebus.gitchat.model.MessageModel;
import rebus.gitchat.model.UserModel;
import rebus.gitchat.ui.adapter.MentionMessagesAdapter;
import rebus.gitchat.ui.adapter.MessagesAdapter;
import rebus.utils.Utils;
import rebus.utils.activity.BaseActivity;
import rebus.utils.adapter.HeaderViewRecyclerAdapter;
import tr.xip.errorview.ErrorView;

/**
 * Created by Raphael on 21/12/2015.
 */
public class DetailRoomActivity extends BaseActivity {

    int numMentions = 0;
    private String id;
    private MessagesAdapter adapter;
    private MentionMessagesAdapter mentionsAdapter;
    private RecyclerView list;
    private RecyclerView mentionList;
    private MultiAutoCompleteTextView message;
    private Button send;
    private ProgressWheel progressWheel;
    private ErrorView errorView;
    private Button loadMore;
    private LinearLayout messageBox;
    private View messageBoxShadow;
    private ProgressWheel progressMore;
    private DrawerLayout drawerLayout;
    private ProgressWheel progressWheelMention;
    private ErrorView errorViewMention;
    private FloatingActionButton joinRoom;
    private MaterialDialog progress;

    private boolean isMember = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getExtras().getString("ID");
        setNavigationIcon(R.drawable.ic_back);
        setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.with(DetailRoomActivity.this).utils().finishWithAnimation();
            }
        });
        send = (Button) findViewById(R.id.send);
        joinRoom = (FloatingActionButton) findViewById(R.id.join_room);
        messageBoxShadow = findViewById(R.id.message_box_shadow);
        messageBox = (LinearLayout) findViewById(R.id.new_message_box);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        list = (RecyclerView) findViewById(R.id.list);
        mentionList = (RecyclerView) findViewById(R.id.mentions_list);
        errorView = (ErrorView) findViewById(R.id.error);
        progressWheel = (ProgressWheel) findViewById(R.id.progress);
        errorViewMention = (ErrorView) findViewById(R.id.error_mention);
        progressWheelMention = (ProgressWheel) findViewById(R.id.progress_mention);
        message = (MultiAutoCompleteTextView) findViewById(R.id.message);
        send.setEnabled(false);
        message.setTokenizer(new MentionTokenizer());
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (message.getText().length() != 0 && message.getText().toString().trim().length() != 0) {
                    send.setEnabled(true);
                } else {
                    send.setEnabled(false);
                }
                updateMentionList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Utils.hideKeyboard(DetailRoomActivity.this);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        adapter = new MessagesAdapter(DetailRoomActivity.this);
        HeaderViewRecyclerAdapter headerViewRecyclerAdapter = new HeaderViewRecyclerAdapter(adapter);
        View footer = getLayoutInflater().inflate(R.layout.footer_messages_list, null);
        loadMore = (Button) footer.findViewById(R.id.load_more);
        progressMore = (ProgressWheel) footer.findViewById(R.id.progress_more);
        headerViewRecyclerAdapter.addFooterView(footer);
        GridLayoutManager manager = new GridLayoutManager(DetailRoomActivity.this, 1, LinearLayoutManager.VERTICAL, true);
        list.setLayoutManager(manager);
        list.setAdapter(headerViewRecyclerAdapter);
        loadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMore.setVisibility(View.GONE);
                progressMore.setVisibility(View.VISIBLE);
                String beforeId = adapter.getFirstId();
                loadMessages(beforeId, null);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.getText().length() != 0) {
                    send.setEnabled(false);
                    HttpRequestClient.with(DetailRoomActivity.this).sendGitterMessage(id, message.getText().toString(), new FutureCallback<Message>() {
                        @Override
                        public void onCompleted(Exception e, Message result) {
                            if (e != null) {
                                send.setEnabled(true);
                                return;
                            }
                            if (result != null) {
                                send.setEnabled(true);
                                message.setText("");
                            } else {
                                send.setEnabled(true);
                            }
                        }
                    });
                }
            }
        });
        initNotification();
        initRefresh();
        loadRoom();
        loadUnread();
    }

    private void updateMentionList(String s) {
        String expression = "@([A-Za-z0-9_-]+)$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            String query = matcher.group().replace("@", "");
            HttpRequestClient.with(DetailRoomActivity.this).getGitterUserMention(id, query, new FutureCallback<List<User>>() {
                @Override
                public void onCompleted(Exception e, List<User> result) {
                    List<String> userMentions = new ArrayList<>();
                    if (e != null) {
                        return;
                    }
                    for (User user : result) {
                        userMentions.add(user.getUsername());
                    }
                    String[] users = new String[userMentions.size()];
                    users = userMentions.toArray(users);
                    ArrayAdapter<String> mentionAdapter = new ArrayAdapter<>(DetailRoomActivity.this, R.layout.row_mention, users);
                    message.setAdapter(mentionAdapter);
                }
            });
        }
    }

    private void initNotification() {
        mentionsAdapter = new MentionMessagesAdapter(DetailRoomActivity.this);
        GridLayoutManager manager = new GridLayoutManager(DetailRoomActivity.this, 1, LinearLayoutManager.VERTICAL, false);
        mentionList.addItemDecoration(new SpacesItemDecoration());
        mentionList.setLayoutManager(manager);
        mentionList.setAdapter(mentionsAdapter);
    }

    private void initRefresh() {
        setUpMessages();
        loadMessages(adapter.getFirstId(), adapter.getLastId());
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String afterId = adapter.getLastId();
                loadMessages(null, afterId);
            }
        };
        timer.schedule(timerTask, 5000, 5000);
    }

    private void loadRoom() {
        HttpRequestClient.with(DetailRoomActivity.this).getGitterRoom(id, new FutureCallback<Room>() {
            @Override
            public void onCompleted(Exception e, Room result) {
                if (e != null) {
                    return;
                }
                setUpRoom(result);
            }
        });
    }

    private void setMessageRead(UnreadMessage unreadMessage) {
        HttpRequestClient.with(DetailRoomActivity.this).setGitterUnreadItems(unreadMessage, id, new FutureCallback<SampleResponse>() {
            @Override
            public void onCompleted(Exception e, SampleResponse result) {
                if (e != null) {
                    return;
                }
            }
        });
    }

    private void loadUnread() {
        HttpRequestClient.with(DetailRoomActivity.this).getGitterUnreadItems(id, new FutureCallback<UnreadMessage>() {
            @Override
            public void onCompleted(Exception e, UnreadMessage result) {
                if (e != null) {
                    return;
                }
                setUpUnread(result);
            }
        });
    }

    private boolean loading = false;

    private void loadMessages(String beforeId, String afterId) {
        if (loading) return;
        loading = true;
        HttpRequestClient.with(DetailRoomActivity.this).getGitterRoomMessages(id, beforeId, afterId, new FutureCallback<List<Message>>() {
            @Override
            public void onCompleted(Exception e, List<Message> result) {
                loading = false;
                if (e != null) {
                    return;
                }
                popolateRealm(result);
            }
        });
    }

    private void popolateRealm(List<Message> messages) {
        List<MessageModel> messageModelList = new ArrayList<>();
        for (Message m : messages) {
            UserModel user = new UserModel();
            user.setId(m.getFromUser().getId());
            user.setUsername(m.getFromUser().getUsername());
            user.setDisplayName(m.getFromUser().getDisplayName());
            user.setUrl(m.getFromUser().getUrl());
            user.setAvatarUrlMedium(m.getFromUser().getAvatarUrlMedium());
            MessageModel model = new MessageModel();
            model.setRoom_id(id);
            model.setId(m.getId());
            model.setText(m.getText());
            if (m.getHtml().length() == 0) {
                model.setHtml("<i>This message was deleted</i>");
            } else {
                model.setHtml(m.getHtml());
            }
            model.setSent(m.getSent());
            model.setFromUser(user);
            model.setUnread(m.isUnread());
            model.setReadBy(m.getReadBy());
            messageModelList.add(model);
        }
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(messageModelList);
        realm.commitTransaction();
        setUpMessages();
    }

    private void setUpMessages() {
        Realm realm = Realm.getDefaultInstance();
        List<MessageModel> messageModels = realm.where(MessageModel.class).equalTo("room_id", id).findAllSorted("sent", Sort.DESCENDING);
        loadMore.setVisibility(View.VISIBLE);
        progressMore.setVisibility(View.GONE);
        progressWheel.setVisibility(View.GONE);
        if (messageModels.size() == 0) {
            errorView.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        } else {
            errorView.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        }
        if (messageModels.size() == 0) {
            adapter.setFirstId(null);
            adapter.setLastId(null);
        } else {
            adapter.setFirstId(messageModels.get(messageModels.size() - 1).getId());
            adapter.setLastId(messageModels.get(0).getId());
        }
        adapter.update(messageModels);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail_room, menu);
        new ActionItemBadgeAdder().act(this).menu(menu).title(R.string.notification).itemDetails(0, R.id.user_mentions, 1).showAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS).add(ActionItemBadge.BadgeStyles.RED_LARGE, numMentions);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (isMember) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        menu.findItem(R.id.leave_room).setVisible(isMember);
        menu.findItem(R.id.user_mentions).setVisible(isMember);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_mentions:
                if (drawerLayout != null && !drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
                break;
            case R.id.leave_room:
                new MaterialDialog.Builder(DetailRoomActivity.this)
                        .title(R.string.leave_room)
                        .content(R.string.leave_room_msg)
                        .positiveText(R.string.ok)
                        .negativeText(R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                progress = Utils.loadingDialog(DetailRoomActivity.this);
                                HttpRequestClient.with(DetailRoomActivity.this).deleteGitterUserFromRoom(id, new FutureCallback<SampleResponse>() {
                                    @Override
                                    public void onCompleted(Exception e, SampleResponse result) {
                                        progress.dismiss();
                                        if (e == null && result != null && result.isSuccess()) {
                                            Navigator.with(DetailRoomActivity.this).utils().finishWithAnimation();
                                        }
                                    }
                                });
                            }
                        })
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpUnread(UnreadMessage unread) {
        errorViewMention.setVisibility(View.VISIBLE);
        progressWheelMention.setVisibility(View.GONE);
        if (unread == null || unread.getMention() == null) return;
        numMentions = unread.getMention().length;
        invalidateOptionsMenu();
        UnReadMessageFactory.with(DetailRoomActivity.this)
                .setMessages(unread.getMention())
                .setRoom(id)
                .get(new UnReadMessageFactory.CallBack() {
                    @Override
                    public void onFinish(boolean success, List<Message> messageList) {
                        if (success) {
                            mentionsAdapter.update(messageList);
                            if (messageList.size() != 0) {
                                mentionList.setVisibility(View.VISIBLE);
                                errorViewMention.setVisibility(View.GONE);
                            }
                        }
                    }
                });
        setMessageRead(unread);
    }

    private void setUpRoom(Room room) {
        isMember = room.isRoomMember();
        invalidateOptionsMenu();
        if (room.isRoomMember()) {
            messageBoxShadow.setVisibility(View.VISIBLE);
            messageBox.setVisibility(View.VISIBLE);
            joinRoom.setVisibility(View.GONE);
        } else {
            messageBoxShadow.setVisibility(View.GONE);
            messageBox.setVisibility(View.GONE);
            joinRoom.setTag(room.getUri());
            joinRoom.setVisibility(View.VISIBLE);
            joinRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uri = (String) v.getTag();
                    HttpRequestClient.with(DetailRoomActivity.this).setGitterJoinRoom(uri, new FutureCallback<Room>() {
                        @Override
                        public void onCompleted(Exception e, Room result) {
                            if (e != null) return;
                            if (result != null) setUpRoom(result);
                        }
                    });
                }
            });
        }
        setTitle(room.getName());
        if (room.getTopic().length() == 0) {
            setSubtitle(R.string.no_description);
        } else {
            setSubtitle(room.getTopic());
        }
    }

    private class MentionTokenizer implements MultiAutoCompleteTextView.Tokenizer {

        public int findTokenStart(CharSequence text, int cursor) {
            int i = cursor;

            while (i > 0 && text.charAt(i - 1) != '@') {
                i--;
            }
            while (i < cursor && text.charAt(i) == ' ') {
                i++;
            }
            if (i == 0) return 999999999;
            return i;
        }

        public int findTokenEnd(CharSequence text, int cursor) {
            int i = cursor;
            int len = text.length();

            while (i < len) {
                if (text.charAt(i) == '@') {
                    return i;
                } else {
                    i++;
                }
            }

            return len;
        }

        public CharSequence terminateToken(CharSequence text) {
            return text + " ";
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
            return;
        }
        Navigator.with(DetailRoomActivity.this).utils().finishWithAnimation();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail_room;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected int getToolbarShadowId() {
        return R.id.toolbar_shadow;
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = Utils.getStatusBarHeight(DetailRoomActivity.this);
            }
        }
    }

}
