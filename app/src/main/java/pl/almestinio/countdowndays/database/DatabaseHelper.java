package pl.almestinio.countdowndays.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.almestinio.countdowndays.model.CountdownDay;

/**
 * Created by mesti193 on 3/31/2018.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "DatabaseCountdownDays.db";
    private static final int DATABASE_VERSION = 1;
    private Dao<CountdownDay, Integer> countdownDays = null;

    static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) instance = new DatabaseHelper(context);
        return instance;
    }


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, CountdownDay.class);
//            TableUtils.dropTable(connectionSource, CountdownDay.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, CountdownDay.class);
//            TableUtils.dropTable(connectionSource, CountdownDay.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        List<String> allSql = new ArrayList<>();
        for (String sql : allSql) {
            database.execSQL(sql);
        }
    }

    public Dao<CountdownDay, Integer> getDays() {
        if (countdownDays == null) {
            try {
                countdownDays = getDao(CountdownDay.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return countdownDays;
    }

}
