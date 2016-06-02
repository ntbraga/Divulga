package divulga.com.br.projectdivulga;

import android.app.Application;

import divulga.com.br.projectdivulga.rest.RealmController;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Taty Braga on 02/06/2016.
 */
public class MainAplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        RealmController.with(this);
    }
}
