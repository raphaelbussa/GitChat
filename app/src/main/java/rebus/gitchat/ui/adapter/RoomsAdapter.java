package rebus.gitchat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikepenz.iconics.view.IconicsTextView;

import java.util.ArrayList;
import java.util.List;

import rebus.gitchat.R;
import rebus.gitchat.http.response.gitter.room.GithubType;
import rebus.gitchat.http.response.gitter.room.Room;
import rebus.utils.Utils;
import rebus.utils.widget.ImageSquare;
import rebus.utils.widget.VerticalTextView;

/**
 * Created by Raphael on 20/12/2015.
 */
public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.MainViewHolder> {

    private Context context;
    private List<Room> roomList;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    public RoomsAdapter(Context context) {
        this.context = context;
        this.roomList = new ArrayList<>();
    }

    public void update(List<Room> roomList) {
        this.roomList.clear();
        this.roomList.addAll(roomList);
        this.notifyDataSetChanged();
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rooms, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Room room = roomList.get(position);
        switch (GithubType.valueOf(room.getGithubType())) {
            case USER_CHANNEL:
            case ONETOONE:
                holder.name.setText("{oct-person} " + room.getName());
                break;
            case ORG_CHANNEL:
            case ORG:
                holder.name.setText("{oct-organization} " + room.getName());
                break;
            case REPO_CHANNEL:
            case REPO:
                holder.name.setText("{oct-repo} " + room.getName());
                break;
            default:
                holder.name.setText(room.getName());
                break;
        }
        if (room.getTopic().length() == 0) {
            holder.topic.setText(R.string.no_description);
        } else {
            holder.topic.setText(room.getTopic());
        }
        if (room.getMentions() != 0) {
            holder.unread.setVisibility(View.VISIBLE);
            holder.unread.setBackgroundResource(R.color.new_notifications);
            if (room.getMentions() == 100) {
                holder.unread.setText("{cmd-bell-ring} " + String.valueOf(room.getMentions()) + "+");
            } else {
                holder.unread.setText("{cmd-bell-ring} " + String.valueOf(room.getMentions()));
            }
        } else {
            if (room.getUnreadItems() != 0) {
                holder.unread.setVisibility(View.VISIBLE);
                holder.unread.setBackgroundResource(R.color.accent);
                if (room.getUnreadItems() == 100) {
                    holder.unread.setText("{cmd-forum} " + String.valueOf(room.getUnreadItems()) + "+");
                } else {
                    holder.unread.setText("{cmd-forum} " + String.valueOf(room.getUnreadItems()));
                }
            } else {
                holder.unread.setVisibility(View.GONE);
            }
        }
        Glide.with(context)
                .load(Utils.getAvatarUrl(room.getUrl(), true))
                .asBitmap()
                .into(holder.avatar);
        holder.item.setTag(room);
        if (onClickListener != null) holder.item.setOnClickListener(onClickListener);
        if (onLongClickListener != null) holder.item.setOnLongClickListener(onLongClickListener);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        private CardView item;
        private ImageSquare avatar;
        private IconicsTextView name;
        private TextView topic;
        private VerticalTextView unread;

        public MainViewHolder(View itemView) {
            super(itemView);
            item = (CardView) itemView.findViewById(R.id.item);
            avatar = (ImageSquare) itemView.findViewById(R.id.avatar);
            name = (IconicsTextView) itemView.findViewById(R.id.name);
            topic = (TextView) itemView.findViewById(R.id.topic);
            unread = (VerticalTextView) itemView.findViewById(R.id.unread);
        }
    }
}
