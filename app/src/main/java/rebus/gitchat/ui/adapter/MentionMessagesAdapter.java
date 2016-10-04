package rebus.gitchat.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rebus.gitchat.R;
import rebus.gitchat.factory.user.UserFactory;
import rebus.gitchat.http.response.gitter.message.Message;
import rebus.header.view.BezelImageView;
import rebus.utils.Utils;

/**
 * Created by Raphael on 20/12/2015.
 */
public class MentionMessagesAdapter extends RecyclerView.Adapter<MentionMessagesAdapter.MainViewHolder> {

    private static final String TAG = MentionMessagesAdapter.class.getName();

    private Context context;
    private List<Message> messageList;
    private String myUser;
    private String lastId;
    private String firstId;

    public MentionMessagesAdapter(Context context) {
        this.context = context;
        this.messageList = new ArrayList<>();
        this.myUser = UserFactory.with(context, UserFactory.TYPE.GITTER).getUser().getName();
    }

    public void update(List<Message> messageList) {
        this.messageList.clear();
        this.messageList.addAll(messageList);
        this.notifyDataSetChanged();
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (message.getFromUser().getUsername().equals(myUser)) {
            holder.bg.setBackgroundResource(R.drawable.balloon_my);
            holder.avatar.setVisibility(View.GONE);
        } else {
            holder.bg.setBackgroundResource(R.drawable.balloon);
            holder.avatar.setVisibility(View.VISIBLE);
            holder.avatar.setTag(R.id.USER_NAME, message.getFromUser().getUsername());
            holder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userName = (String) v.getTag(R.id.USER_NAME);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("gitchat://user/" + userName));
                    context.startActivity(i);
                }
            });
            Glide.with(context)
                    .load(message.getFromUser().getAvatarUrlMedium())
                    .into(holder.avatar);
        }
        holder.username.setText(message.getFromUser().getUsername());
        holder.date.setText(Utils.dateConverted(message.getSent()));
        holder.message.setHtml(message.getHtml(), new HtmlHttpImageGetter(holder.message, "", true));
        Pattern mentionPattern = Pattern.compile("@([A-Za-z0-9_-]+)");
        Pattern hashtagPattern = Pattern.compile("#([A-Za-z0-9_-]+)");
        Pattern linkPattern = Pattern.compile("((http|https):/{2})+(.+)");
        String mentionScheme = "gitchat://user/";
        String hashtagScheme = "gitchat://issue/";
        Linkify.TransformFilter mentionFilter = new Linkify.TransformFilter() {
            public String transformUrl(final Matcher match, String url) {
                return match.group(1);
            }
        };
        Linkify.TransformFilter hashtagFilter = new Linkify.TransformFilter() {
            public String transformUrl(final Matcher match, String url) {
                return match.group(1);
            }
        };
        Linkify.TransformFilter linkFilter = new Linkify.TransformFilter() {
            public String transformUrl(final Matcher match, String url) {
                return match.group();
            }
        };
        Linkify.addLinks(holder.message, linkPattern, "", null, linkFilter);
        Linkify.addLinks(holder.message, mentionPattern, mentionScheme, null, mentionFilter);
        Linkify.addLinks(holder.message, hashtagPattern, hashtagScheme, null, hashtagFilter);
        holder.message.setLinkTextColor(ContextCompat.getColor(context, R.color.primary));
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public void onViewRecycled(MainViewHolder holder) {
        Log.d(TAG, "onViewRecycled [" + holder.isRecyclable() + "]");
        super.onViewRecycled(holder);
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        private BezelImageView avatar;
        private TextView username;
        private TextView date;
        private HtmlTextView message;
        private LinearLayout bg;

        public MainViewHolder(View itemView) {
            super(itemView);
            avatar = (BezelImageView) itemView.findViewById(R.id.avatar);
            username = (TextView) itemView.findViewById(R.id.username);
            date = (TextView) itemView.findViewById(R.id.date);
            message = (HtmlTextView) itemView.findViewById(R.id.message);
            bg = (LinearLayout) itemView.findViewById(R.id.bg);
        }
    }

}
