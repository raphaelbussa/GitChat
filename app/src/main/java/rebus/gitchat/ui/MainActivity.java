package rebus.gitchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsSupportFragment;

import org.cryse.widget.persistentsearch.DefaultVoiceRecognizerDelegate;
import org.cryse.widget.persistentsearch.PersistentSearchView;
import org.cryse.widget.persistentsearch.VoiceRecognitionDelegate;
import org.fingerlinks.mobile.android.navigator.Navigator;

import java.util.ArrayList;
import java.util.List;

import rebus.gitchat.Constants;
import rebus.gitchat.R;
import rebus.gitchat.factory.search.SearchFactory;
import rebus.gitchat.factory.user.UserAdapter;
import rebus.gitchat.factory.user.UserBean;
import rebus.gitchat.factory.user.UserFactory;
import rebus.gitchat.http.response.gitter.message.Message;
import rebus.gitchat.ui.fragment.RoomsFragment;
import rebus.gitchat.ui.fragment.SuggestedRoomsFragment;
import rebus.header.view.HeaderInterface;
import rebus.header.view.HeaderView;
import rebus.utils.Utils;
import rebus.utils.activity.BaseActivity;

/**
 * Created by Raphael on 19/12/2015.
 */
public class MainActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getName();

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1023;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private PersistentSearchView searchView;
    private View viewSearchTint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewSearchTint = findViewById(R.id.view_search_tint);
        searchView = (PersistentSearchView) findViewById(R.id.search_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Utils.hideKeyboard(MainActivity.this);
                searchView.closeSearch();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        searchView.setSuggestionBuilder(new SearchFactory());
        searchView.setSearchListener(new PersistentSearchView.SearchListener() {
            @Override
            public void onSearchCleared() {

            }

            @Override
            public void onSearchTermChanged(String term) {

            }

            @Override
            public void onSearch(String query) {
                if (!query.startsWith("#") || !query.startsWith("@")) {
                    query = "#" + query;
                }
                Log.d(TAG, query);
                //TODO SEARCH!!!
                searchView.closeSearch();
            }

            @Override
            public void onSearchEditOpened() {
                viewSearchTint.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchEditClosed() {
                viewSearchTint.setVisibility(View.GONE);
            }

            @Override
            public boolean onSearchEditBackPressed() {
                return false;
            }

            @Override
            public void onSearchExit() {

            }
        });

        VoiceRecognitionDelegate delegate = new DefaultVoiceRecognizerDelegate(this, VOICE_RECOGNITION_REQUEST_CODE);
        if (delegate.isVoiceRecognitionAvailable())
            searchView.setVoiceRecognitionDelegate(delegate);

        viewSearchTint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.closeSearch();
            }
        });

        addHeader();
        setTitle(R.string.app_name);
        setNavigationIcon(R.drawable.ic_menu);
        setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout != null && !drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        if (savedInstanceState == null) {
            Navigator.with(MainActivity.this)
                    .build()
                    .goTo(Fragment.instantiate(MainActivity.this, RoomsFragment.class.getName()), R.id.container)
                    .tag(Constants.TAG_ROOMS)
                    .addToBackStack()
                    .add()
                    .commit();
        }
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    private void addHeader() {
        UserBean user = UserFactory.with(MainActivity.this, UserFactory.TYPE.GITTER).getUser();
        HeaderView headerView = new HeaderView(MainActivity.this, false);
        headerView.username(user.getFullName());
        headerView.email("@" + user.getName());
        headerView.background().setBackgroundColor(getResources().getColor(R.color.primary));
        headerView.setOnHeaderClickListener(new HeaderInterface.OnHeaderClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
        headerView.setOnAvatarClickListener(new HeaderInterface.OnAvatarClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        headerView.setArrow(new HeaderInterface.OnArrowClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.inflate(R.menu.popup_main);
                popupMenu.setGravity(Gravity.END);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.logout) {
                            UserFactory.with(MainActivity.this, UserFactory.TYPE.GITTER)
                                    .logOut(new UserFactory.LogOutCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            Navigator.with(MainActivity.this)
                                                    .build()
                                                    .goTo(SplashActivity.class)
                                                    .commit();
                                            finish();
                                        }
                                    });
                        }
                        return false;
                    }
                });
            }
        });
        Glide.with(MainActivity.this)
                .load(user.getAvatar())
                .asBitmap()
                .into(headerView.avatar());
        navigationView.addHeaderView(headerView);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if (searchView.isSearching()) {
            searchView.closeSearch();
            return;
        }
        if (Navigator.with(MainActivity.this).utils().getActualTag().equals(Constants.TAG_ROOMS)) {
            finish();
        } else {
            Navigator.with(MainActivity.this).utils().goBackToSpecificPoint(Constants.TAG_ROOMS);
        }
    }

    @Override
    public void onBackStackChanged() {
        switch (Navigator.with(MainActivity.this).utils().getActualTag()) {
            case Constants.TAG_ROOMS:
                navigationView.setCheckedItem(R.id.rooms);
                break;
            case Constants.TAG_SUGGESTED_ROOMS:
                navigationView.setCheckedItem(R.id.suggested_rooms);
                break;
        }
    }

    private void closeDrawer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
            }
        }, 400);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        closeDrawer();
        if (item.isChecked()) return false;
        switch (item.getItemId()) {
            case R.id.rooms:
                if (!Navigator.with(MainActivity.this).utils().getActualTag().equals(Constants.TAG_ROOMS)) {
                    Navigator.with(MainActivity.this).utils().goBackToSpecificPoint(Constants.TAG_ROOMS);
                }
                break;
            case R.id.suggested_rooms:
                Navigator.with(MainActivity.this)
                        .build()
                        .goTo(Fragment.instantiate(MainActivity.this, SuggestedRoomsFragment.class.getName()), R.id.container)
                        .tag(Constants.TAG_SUGGESTED_ROOMS)
                        .addToBackStack()
                        .animation()
                        .replace()
                        .commit();
                break;
            case R.id.profile:
                /*LibsSupportFragment fragment = new LibsBuilder()
                        .withLicenseDialog(true)
                        .withLicenseShown(true)
                        .supportFragment();
                Navigator.with(MainActivity.this)
                        .build()
                        .goTo(fragment, R.id.container)
                        .tag(Constants.TAG_PROFILE)
                        .addToBackStack()
                        .animation()
                        .replace()
                        .commit();*/
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                if (searchView != null) {
                    searchView.setStartPositionFromMenuItem(findViewById(R.id.search));
                    searchView.openSearch();
                    return true;
                } else {
                    return false;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            searchView.populateEditText(matches);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
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
