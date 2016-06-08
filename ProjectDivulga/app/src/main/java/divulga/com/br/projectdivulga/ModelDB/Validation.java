package divulga.com.br.projectdivulga.ModelDB;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ntbra on 07/06/2016.
 */
public class Validation extends RealmObject {
    @PrimaryKey
    private int id = 0;
    private long deadline;
    private boolean auth;

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
