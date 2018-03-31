package pl.almestinio.countdowndays.model;

import org.joda.time.DateTime;

/**
 * Created by mesti193 on 3/31/2018.
 */

public class CountdownDay {

    private String title;
    private DateTime date;
    private String color;

    public CountdownDay(String title, DateTime date, String color) {
        this.title = title;
        this.date = date;
        this.color = color;
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
