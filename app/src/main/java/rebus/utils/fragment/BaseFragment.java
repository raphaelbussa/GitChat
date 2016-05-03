package rebus.utils.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rebus.utils.activity.BaseActivity;


/**
 * Created by Raphael on 07/10/2015.
 */
public abstract class BaseFragment extends Fragment {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutResource(), container, false);
        return rootView;
    }

    public Toolbar getToolbar() {
        return ((BaseActivity) getActivity()).getToolbar();
    }

    public View getToolbarShadow() {
        return ((BaseActivity) getActivity()).getToolbarShadow();
    }

    public View getRootView() {
        return rootView;
    }

    public void setIcon(int icon) {
        ((BaseActivity) getActivity()).setIcon(icon);
    }

    public void setIcon(Drawable icon) {
        ((BaseActivity) getActivity()).setIcon(icon);
    }

    public void setTitle(int title) {
        ((BaseActivity) getActivity()).setTitle(title);
    }

    public void setSubtitle(int subtitle) {
        ((BaseActivity) getActivity()).setSubtitle(subtitle);
    }

    public void setTitle(String title) {
        ((BaseActivity) getActivity()).setTitle(title);
    }

    public void setSubtitle(String subtitle) {
        ((BaseActivity) getActivity()).setSubtitle(subtitle);
    }

    public void setNavigationIcon(int icon) {
        ((BaseActivity) getActivity()).setNavigationIcon(icon);
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        ((BaseActivity) getActivity()).setNavigationOnClickListener(onClickListener);
    }

    public void setStatusBarColor(int color) {
        ((BaseActivity) getActivity()).setStatusBarColor(color);
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    public abstract boolean onSupportBackPressed(Bundle bundle);

}
