package pl.almestinio.countdowndays.ui.editCountdownView;

import org.joda.time.DateTime;

import pl.almestinio.countdowndays.database.DatabaseCountdownDay;
import pl.almestinio.countdowndays.model.CountdownDay;

/**
 * Created by mesti193 on 4/1/2018.
 */

public class EditCountdownPresenter implements EditCountdownContracts.Presenter {

    private EditCountdownContracts.View view;

    public EditCountdownPresenter(EditCountdownContracts.View view){
        this.view = view;
    }

    @Override
    public void getColor(String color) {
        view.showColorPicker(color);
    }

    @Override
    public void editCountdownToDatabase(CountdownDay countdownDay, String title, DateTime dateTime, String color) {
        try {

            DatabaseCountdownDay.updateDays(countdownDay.getId(), title, dateTime, color);
            view.showSnackbarSuccess("Edit successful");
            view.startMenuFragment();

        }catch (Exception e){
            view.showSnackbarError("Error with updating countdown to database");
            e.printStackTrace();
        }
    }
}
