package rebus.gitchat.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.koushikdutta.async.future.FutureCallback;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.fingerlinks.mobile.android.navigator.Navigator;

import rebus.gitchat.Constants;
import rebus.gitchat.R;
import rebus.gitchat.factory.user.UserBean;
import rebus.gitchat.factory.user.UserFactory;
import rebus.gitchat.http.HttpRequestClient;
import rebus.gitchat.http.response.CodeResponse;
import rebus.gitchat.http.response.TokenResponse;
import rebus.utils.activity.BaseActivity;

/**
 * Created by Raphael on 19/12/2015.
 */
public class LoginGithubActivity extends BaseActivity {

    private WebView webView;
    private ProgressWheel progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(getResources().getColor(R.color.primary_dark));
        setTitle(R.string.login);
        setNavigationIcon(R.drawable.ic_close);
        setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progress = (ProgressWheel) findViewById(R.id.progress);

        CookieManager.getInstance().removeAllCookie();

        webView = (WebView) findViewById(R.id.webview);
        webView.clearHistory();
        webView.clearCache(true);
        webView.getSettings().setSaveFormData(false);
        webView.setWebViewClient(new OAuthViewClient());
        webView.loadUrl(Constants.getApiGithubOauthCode());

    }

    private class OAuthViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            if (!url.startsWith(Constants.API_GITHUB_REDIRECT_URI)) {
                webView.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (url.startsWith(Constants.API_GITHUB_REDIRECT_URI)) {
                webView.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                HttpRequestClient.with(LoginGithubActivity.this).getCode(url, new FutureCallback<CodeResponse>() {
                    @Override
                    public void onCompleted(Exception e, CodeResponse result) {
                        if (e != null) {
                            showError();
                            return;
                        }
                        if (result.isSuccess()) {
                            HttpRequestClient.with(LoginGithubActivity.this).getGitHubToken(result.getCode(), new FutureCallback<TokenResponse>() {
                                @Override
                                public void onCompleted(Exception e, TokenResponse result) {
                                    if (result.getAccess_token() != null && result.getToken_type() != null) {
                                        String token = result.getToken_type() + " " + result.getAccess_token();
                                        UserFactory.with(LoginGithubActivity.this, UserFactory.TYPE.GITHUB).logIn(token, new UserFactory.UserCallBack() {
                                            @Override
                                            public void onError() {
                                                showError();
                                            }

                                            @Override
                                            public void onSuccess(UserBean user) {
                                                Navigator.with(LoginGithubActivity.this)
                                                        .build()
                                                        .goTo(MainActivity.class)
                                                        .commit();
                                                finish();
                                            }
                                        });
                                    } else {
                                        showError();
                                    }
                                }
                            });
                        } else {
                            showError();
                        }
                    }
                });
                return;
            }
            super.onPageStarted(view, url, favicon);
        }

    }

    private void showError() {
        new MaterialDialog.Builder(LoginGithubActivity.this)
                .title(R.string.login_error_t)
                .content(R.string.login_error_c)
                .cancelable(false)
                .positiveText(R.string.authorize)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            webView.loadUrl(Constants.getApiGitterOauthCode());
                        }
                    }
                })
                .show();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected int getToolbarShadowId() {
        return R.id.toolbar_shadow;
    }
}
