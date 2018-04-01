package pl.almestinio.countdowndays.ui.newCountdownView;

import pl.almestinio.countdowndays.database.DatabaseCountdownDay;
import pl.almestinio.countdowndays.model.CountdownDay;

/**
 * Created by mesti193 on 3/31/2018.
 */

public class NewCountdownPresenter implements NewCountdownContracts.Presenter {

    private NewCountdownContracts.View view;

    public NewCountdownPresenter(NewCountdownContracts.View view){
        this.view = view;
    }


    @Override
    public void getColor(String color) {
        view.showColorPicker(color);
    }

    @Override
    public void addCountdownToDatabase(CountdownDay countdownDay) {

        try{

            DatabaseCountdownDay.addOrUpdateDays(new CountdownDay(countdownDay.getTitle(), countdownDay.getDate(), countdownDay.getColor()));
            view.showSnackbarSuccess("Added countdown day!");
            view.startMenuFragment();

        }catch (Exception e){
            view.showSnackbarerror("Error with adding countdown to database");
            e.printStackTrace();
        }
    }

}
