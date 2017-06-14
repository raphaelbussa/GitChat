package rebus.gitchat.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import org.fingerlinks.mobile.android.navigator.Navigator;

import rebus.gitchat.R;
import rebus.gitchat.factory.user.UserFactory;
import rebus.utils.activity.BaseActivity;

public class SplashActivity extends BaseActivity {

    private Button login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(getResources().getColor(R.color.primary_dark));
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.with(SplashActivity.this)
                        .build()
                        .goTo(LoginGitterActivity.class)
                        .commit();
                finish();
            }
        });
        if (UserFactory.with(SplashActivity.this, UserFactory.TYPE.GITTER).isLogged()) {
            goToHome();
        } else {
            animateLogin();
        }
    }

    private void goToHome() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigator.with(SplashActivity.this)
                        .build()
                        .goTo(MainActivity.class)
                        .commit();
                finish();
            }
        }, 400);
    }

    private void animateLogin() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.SlideInUp)
                        .duration(1200)
                        .withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                login.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        })
                        .playOn(login);
            }
        }, 400);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected int getToolbarId() {
        return 0;
    }

    @Override
    protected int getToolbarShadowId() {
        return 0;
    }
}
