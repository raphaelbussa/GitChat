package rebus.gitchat.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.koushikdutta.async.future.FutureCallback;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.fingerlinks.mobile.android.navigator.Navigator;

import java.util.List;

import rebus.gitchat.R;
import rebus.gitchat.http.HttpRequestClient;
import rebus.gitchat.http.response.gitter.room.Room;
import rebus.gitchat.ui.DetailRoomActivity;
import rebus.gitchat.ui.adapter.RoomsAdapter;
import rebus.utils.fragment.BaseFragment;

/**
 * Created by Raphael on 20/12/2015.
 */
public class RoomsFragment extends BaseFragment implements FutureCallback<List<Room>>, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, View.OnLongClickListener {

    private RoomsAdapter adapter;
    private ProgressWheel progress;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSubtitle(R.string.rooms);
        RecyclerView list = (RecyclerView) getRootView().findViewById(R.id.list);
        refreshLayout = (SwipeRefreshLayout) getRootView().findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary), getResources().getColor(R.color.accent));
        refreshLayout.setOnRefreshListener(this);
        progress = (ProgressWheel) getRootView().findViewById(R.id.progress);
        adapter = new RoomsAdapter(getActivity());
        adapter.setOnClickListener(this);
        adapter.setOnLongClickListener(this);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
        list.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.row_space)));
        list.setLayoutManager(manager);
        list.setAdapter(adapter);

        HttpRequestClient.with(getActivity()).getGitterRooms(this);
    }

    @Override
    public void onRefresh() {
        HttpRequestClient.with(getActivity()).getGitterRooms(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLayout.setRefreshing(true);
        HttpRequestClient.with(getActivity()).getGitterRooms(this);
    }

    @Override
    public void onCompleted(Exception e, List<Room> result) {
        refreshLayout.setRefreshing(false);
        progress.setVisibility(View.GONE);
        if (e != null) {
            //TODO msg errore
            return;
        }
        adapter.update(result);
    }

    @Override
    public void onClick(View v) {
        Room room = (Room) v.getTag();
        Bundle bundle = new Bundle();
        bundle.putString("ID", room.getId());
        Navigator.with(getActivity())
                .build()
                .goTo(DetailRoomActivity.class, bundle)
                .animation()
                .commit();
    }

    @Override
    public boolean onLongClick(View v) {
        Room room = (Room) v.getTag();
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.inflate(R.menu.popup_main);
        popupMenu.setGravity(Gravity.END);
        popupMenu.show();
        return true;
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space + space;
            }
            outRect.left = space + space;
            outRect.right = space + space;
            if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                outRect.bottom = space + space;
            }
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_rooms;
    }

    @Override
    public boolean onSupportBackPressed(Bundle bundle) {
        return false;
    }
}
