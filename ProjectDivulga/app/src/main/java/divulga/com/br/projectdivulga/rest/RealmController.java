package divulga.com.br.projectdivulga.rest;



import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.ModelDB.Establishments;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //clear all objects from Book.class
    public void clearAll(Class<? extends RealmObject> clazz) {
        realm.beginTransaction();
        realm.where(clazz).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public <T extends RealmObject> RealmResults<T> getAll(Class<T> clazz) {
        return realm.where(clazz).findAll();
    }

    //query a single item with the given id
    public <T extends RealmObject> T get(Class<T> clazz, int id) {
        return realm.where(clazz).equalTo("id", id).findFirst();
    }

    //check if Book.class is empty
    public boolean has(Class<? extends RealmObject> clazz) {
        return realm.where(clazz).count() > 0;
    }



    public RealmResults<Establishments> getAllEstablishmentsFavorites(){
        return realm.where(Establishments.class).equalTo("favorite", true).findAll();
    }

    public void updateAllEstablishments(List<Establishments> updated){
        Map<Integer, Boolean> favorites = new HashMap<>();
        if(has(Establishments.class)) {
            RealmResults<Establishments> all = getAll(Establishments.class);
            for (int i = 0; i < all.size(); i++) {
                favorites.put(all.get(i).getId(), all.get(i).isFavorite());
            }
            clearAll(Establishments.class);
        }
        realm.beginTransaction();
        for(int i = 0; i<updated.size(); i++){
            Establishments est = updated.get(i);
            if(favorites.containsKey(est.getId())){
                est.setFavorite(favorites.get(est.getId()));
            }
            realm.copyToRealm(est);
        }
        realm.commitTransaction();
    }

    public void setEstablishmentFavorite(Establishments establishment, boolean favorite){
        realm.beginTransaction();
        get(Establishments.class, establishment.getId()).setFavorite(favorite);
        realm.commitTransaction();
    }

    public void addAll(List<? extends RealmObject> objects){
        realm.beginTransaction();
        realm.copyToRealm(objects);
        realm.commitTransaction();
    }

    public <T extends RealmObject> void clearAndAddAll(List<T> objects, Class<T> clazz){
        clearAll(clazz);
        addAll(objects);
    }


}
