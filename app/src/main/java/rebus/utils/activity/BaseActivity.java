package rebus.utils.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Raphael on 07/10/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private View toolbarShadow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        if (getToolbarId() != 0) {
            toolbar = (Toolbar) findViewById(getToolbarId());
            if (toolbar != null) {
                setSupportActionBar(toolbar);
            }
        }
        if (getToolbarShadowId() != 0) {
            toolbarShadow = findViewById(getToolbarShadowId());
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public View getToolbarShadow() {
        return toolbarShadow;
    }

    public void setIcon(int icon) {
        if (getSupportActionBar() != null) getSupportActionBar().setIcon(icon);
    }

    public void setIcon(Drawable icon) {
        if (getSupportActionBar() != null) getSupportActionBar().setIcon(icon);
    }

    public void setTitle(String title) {
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
    }

    public void setTitle(int title) {
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
    }

    public void setSubtitle(String subtitle) {
        if (getSupportActionBar() != null) getSupportActionBar().setSubtitle(subtitle);
    }

    public void setSubtitle(int subtitle) {
        if (getSupportActionBar() != null) getSupportActionBar().setSubtitle(subtitle);
    }

    public void setNavigationIcon(int icon) {
        if (toolbar != null) toolbar.setNavigationIcon(icon);
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        if (toolbar != null) toolbar.setNavigationOnClickListener(onClickListener);
    }

    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    public Activity getActivity() {
        return this;
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected abstract int getToolbarId();

    protected abstract int getToolbarShadowId();

}
