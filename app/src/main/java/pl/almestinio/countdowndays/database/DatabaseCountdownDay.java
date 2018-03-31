package pl.almestinio.countdowndays.database;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

import pl.almestinio.countdowndays.model.CountdownDay;

/**
 * Created by mesti193 on 3/31/2018.
 */

public class DatabaseCountdownDay {

    public static List<CountdownDay> getDays() {
        List<CountdownDay> categoryList = null;
        try {
            categoryList = DatabaseHelper.instance.getDays().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public static void addOrUpdateDays(CountdownDay day) {
        try {
            DatabaseHelper.instance.getDays().createOrUpdate(day);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateDays(String day) {
        try {
//            DatabaseHelper.instance.getUser().update(user);
            UpdateBuilder<CountdownDay, Integer> updateBuilder = DatabaseHelper.instance.getDays().updateBuilder();
//            updateBuilder.where().eq("id", 1);
//            updateBuilder.updateColumnValue("userid", day);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDay(int id){
        try{

            DeleteBuilder<CountdownDay, Integer> deleteBuilder = DatabaseHelper.instance.getDays().deleteBuilder();
            deleteBuilder.where().eq("id", id);
            deleteBuilder.delete();

        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public static void deleteAllDays() {
        try {
            DeleteBuilder<CountdownDay, Integer> deleteBuilder = DatabaseHelper.instance.getDays().deleteBuilder();
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
