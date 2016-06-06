package divulga.com.br.projectdivulga.rest;



import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import divulga.com.br.projectdivulga.ModelDB.Categories;
import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.ModelDB.Establishments;
import io.realm.Realm;
import io.realm.RealmList;
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

    //find all objects in the Book.class
    public <T extends RealmObject> RealmResults<T> getAll(Class<T> clazz) {
        return realm.where(clazz).findAll();
    }

    public RealmResults<Categories> getCategories(int id_city){
        return realm.where(Categories.class).equalTo("id_city", id_city).findAll();
    }

    public RealmResults<Establishments> getEstablishments(int id_cat){
        return realm.where(Establishments.class).equalTo("id_cat", id_cat).findAll();
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

    public void setEstablishmentFavorite(Establishments establishment){
        realm.beginTransaction();
        establishment.setFavorite(!establishment.isFavorite());
        realm.copyToRealmOrUpdate(establishment);
        realm.commitTransaction();
    }

    public Establishments setEstablishment(Establishments establishment){
        realm.beginTransaction();
        Establishments realmEstab = realm.where(Establishments.class).equalTo("id", establishment.getId()).findFirst();
        if(realmEstab != null)
            establishment.setFavorite(realmEstab.isFavorite());
        realm.copyToRealmOrUpdate(establishment);
        realm.commitTransaction();
        return establishment;
    }



    public void clearAndAddAllEstablishments(List<Establishments> objects, int id_cat){
        realm.beginTransaction();
        if(objects.size() > 0){
            List<Establishments> temp = new ArrayList<>();
            for(Establishments estab: objects){
                Establishments tmp = realm.where(Establishments.class).equalTo("id", estab.getId()).findFirst();
                if(tmp != null) estab.setFavorite(tmp.isFavorite());
                temp.add(estab);
            }
            realm.where(Establishments.class).equalTo("id_cat", id_cat).findAll().deleteAllFromRealm();
            realm.copyToRealmOrUpdate(temp);
        }else{
            realm.where(Establishments.class).equalTo("id_cat", id_cat).findAll().deleteAllFromRealm();
        }
        realm.commitTransaction();
    }


    public void clearAndAddAllCities(List<Cities> objects){
        realm.beginTransaction();
        realm.where(Cities.class).findAll().deleteAllFromRealm();
        realm.copyToRealmOrUpdate(objects);
        realm.commitTransaction();
    }

    public void clearAndAddAllCategories(List<Categories> objecs, int id_city){
        realm.beginTransaction();
        realm.where(Categories.class).equalTo("id_city", id_city).findAll().deleteAllFromRealm();
        realm.copyToRealmOrUpdate(objecs);
        realm.commitTransaction();
    }

}
