package pl.almestinio.countdowndays.database;

import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

import pl.almestinio.countdowndays.model.UserSettings;

/**
 * Created by mesti193 on 3/31/2018.
 */

public class DatabaseUserSettings {

    public static List<UserSettings> getUserSettings() {
        List<UserSettings> userSettings = null;
        try {
            userSettings = DatabaseHelper.instance.getUserSettings().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userSettings;
    }

    public static void addOrUpdateDays(UserSettings userSettings) {
        try {
            DatabaseHelper.instance.getUserSettings().createOrUpdate(userSettings);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUserSettings() {
        try {
            DeleteBuilder<UserSettings, Integer> deleteBuilder = DatabaseHelper.instance.getUserSettings().deleteBuilder();
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
