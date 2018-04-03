package pl.almestinio.countdowndays.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.joda.time.DateTime;

/**
 * Created by mesti193 on 3/31/2018.
 */
@DatabaseTable(tableName = "CountdownDays2")
public class CountdownDay {

    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_DATE = "date";
    private static final String COL_COLOR_STROKE = "color_stroke";
    private static final String COL_COLOR_SOLID = "color_solid";

    @DatabaseField(generatedId = true, columnName = COL_ID)
    private int id;
    @DatabaseField(columnName = COL_TITLE)
    private String title;
    @DatabaseField(columnName = COL_DATE)
    private DateTime date;
    @DatabaseField(columnName = COL_COLOR_STROKE)
    private String colorStroke;
    @DatabaseField(columnName = COL_COLOR_SOLID)
    private String colorSolid;


    public CountdownDay(){

    }

    public CountdownDay(String title, DateTime date, String colorStroke, String colorSolid) {
        this.title = title;
        this.date = date;
        this.colorStroke = colorStroke;
        this.colorSolid = colorSolid;
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

    public String getColorStroke() {
        return colorStroke;
    }

    public void setColorStroke(String colorStroke) {
        this.colorStroke = colorStroke;
    }

    public String getColorSolid() {
        return colorSolid;
    }

    public void setColorSolid(String colorSolid) {
        this.colorSolid = colorSolid;
    }
}
