package rebus.gitchat.ui.fragment;

import android.os.Bundle;
import android.view.View;

import rebus.gitchat.R;
import rebus.utils.fragment.BaseFragment;

/**
 * Created by Raphael on 20/12/2015.
 */
public class ProfileFragment extends BaseFragment {

    private static final String TAG = ProfileFragment.class.getName();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    public boolean onSupportBackPressed(Bundle bundle) {
        return false;
    }
}
