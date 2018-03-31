package pl.almestinio.countdowndays.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.joda.time.DateTime;

/**
 * Created by mesti193 on 3/31/2018.
 */
@DatabaseTable(tableName = "CountdownDays")
public class CountdownDay {

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_DATE = "date";
    public static final String COL_COLOR = "color";

    @DatabaseField(generatedId = true, columnName = COL_ID)
    private int id;
    @DatabaseField(columnName = COL_TITLE)
    private String title;
    @DatabaseField(columnName = COL_DATE)
    private DateTime date;
    @DatabaseField(columnName = COL_COLOR)
    private String color;

    public CountdownDay(){

    }

    public CountdownDay(String title, DateTime date, String color) {
        this.title = title;
        this.date = date;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
