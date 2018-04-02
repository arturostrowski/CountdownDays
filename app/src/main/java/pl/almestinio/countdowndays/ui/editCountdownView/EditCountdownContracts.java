package pl.almestinio.countdowndays.ui.editCountdownView;

import org.joda.time.DateTime;

import pl.almestinio.countdowndays.model.CountdownDay;

/**
 * Created by mesti193 on 4/1/2018.
 */

public interface EditCountdownContracts {

    interface View{
        void showSnackbarSuccess(String message);
        void showSnackbarError(String message);
        void showColorPicker(String color, int id);
        void startMenuFragment();
    }

    interface Presenter{
        void getColor(String color, int id);
        void editCountdownToDatabase(CountdownDay countdownDay, String title, DateTime dateTime, String colorStroke, String colorSolid);
    }

}
