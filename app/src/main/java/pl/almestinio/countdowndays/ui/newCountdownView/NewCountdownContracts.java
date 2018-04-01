package pl.almestinio.countdowndays.ui.newCountdownView;

import pl.almestinio.countdowndays.model.CountdownDay;

/**
 * Created by mesti193 on 3/31/2018.
 */

public interface NewCountdownContracts {

    interface View{
        void showSnackbarSuccess(String message);
        void showSnackbarerror(String message);
        void showColorPicker(String color);
        void startMenuFragment();
    }

    interface Presenter{

        void getColor(String color);
        void addCountdownToDatabase(CountdownDay countdownDay);

    }

}
