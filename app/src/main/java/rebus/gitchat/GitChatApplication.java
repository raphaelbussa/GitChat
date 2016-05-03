package rebus.gitchat;

import android.app.Application;
import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.annotations.RealmModule;
import rebus.gitchat.model.MessageModel;
import rebus.gitchat.model.UserModel;

/**
 * Created by Raphael on 19/12/2015.
 */
public class GitChatApplication extends Application {

    private static final String TAG = GitChatApplication.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext())
                .name(getResources().getString(R.string.database_conf_name))
                .schemaVersion(getResources().getInteger(R.integer.database_conf_version))
                .setModules(new DbModule())
                .deleteRealmIfMigrationNeeded()
                .migration(new RealmMigration() {
                    @Override
                    public void migrate(DynamicRealm dynamicRealm, long l, long l1) {
                        Log.d(TAG, "migrate l [" + l + "] l1 [" + l1 + "]");
                    }
                })
                .build();
        Realm.setDefaultConfiguration(config);
    }

    @RealmModule(classes = {
            MessageModel.class, UserModel.class
    })
    class DbModule {
    }

}
