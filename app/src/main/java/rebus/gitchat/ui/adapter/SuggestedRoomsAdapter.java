package rebus.gitchat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import rebus.gitchat.R;
import rebus.gitchat.http.response.gitter.room.SuggestedRoom;
import rebus.utils.Utils;

/**
 * Created by Raphael on 20/12/2015.
 */
public class SuggestedRoomsAdapter extends RecyclerView.Adapter<SuggestedRoomsAdapter.MainViewHolder> {

    private Context context;
    private List<SuggestedRoom> roomList;

    public SuggestedRoomsAdapter(Context context) {
        this.context = context;
        this.roomList = new ArrayList<>();
    }

    public void update(List<SuggestedRoom> roomList) {
        this.roomList.clear();
        this.roomList.addAll(roomList);
        this.notifyDataSetChanged();
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_suggested_rooms, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        SuggestedRoom room = roomList.get(position);
        holder.name.setText(room.getUri());
        holder.topic.setText(context.getString(R.string.user_num, room.getUserCount()));
        Glide.with(context)
                .load(Utils.getAvatarUrl(room.getUri(), false))
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

    public class MainViewHolder extends RecyclerView.ViewHolder {

        private CardView item;
        private ImageView avatar;
        private TextView name;
        private TextView topic;

        public MainViewHolder(View itemView) {
            super(itemView);
            item = (CardView) itemView.findViewById(R.id.item);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            topic = (TextView) itemView.findViewById(R.id.topic);
        }
    }

    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

}
