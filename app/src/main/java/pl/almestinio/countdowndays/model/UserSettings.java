package pl.almestinio.countdowndays.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by mesti193 on 3/31/2018.
 */

@DatabaseTable(tableName = "UserSettings")
public class UserSettings {

    public static final String COL_ID = "id";
    public static final String COL_SORT = "sort";

    @DatabaseField(generatedId = true, columnName = COL_ID)
    private int id;
    @DatabaseField(columnName = COL_SORT)
    private String sort;

    public UserSettings(){

    }

    public UserSettings(String sort){
        this.sort = sort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
