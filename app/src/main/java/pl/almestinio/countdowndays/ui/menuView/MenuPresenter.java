package pl.almestinio.countdowndays.ui.menuView;

import android.widget.Button;

import pl.almestinio.countdowndays.database.DatabaseCountdownDay;
import pl.almestinio.countdowndays.database.DatabaseUserSettings;
import pl.almestinio.countdowndays.model.UserSettings;

/**
 * Created by mesti193 on 3/31/2018.
 */

public class MenuPresenter implements MenuContracts.Presenter {

    private MenuContracts.View view;

    public MenuPresenter(MenuContracts.View view){
        this.view = view;
    }


    @Override
    public void loadData() {
        view.showDaysFromDatabase(DatabaseCountdownDay.getDays());
        view.sortList();
        view.setAdapterAndGetRecyclerView();
    }

    @Override
    public void onSortOptionMenuClicked(int sort) {
        try{
            if(!DatabaseUserSettings.getUserSettings().isEmpty()) {
                DatabaseUserSettings.deleteUserSettings();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        switch (sort){
            case 0:
                DatabaseUserSettings.addOrUpdateDays(new UserSettings("ascending"));
                break;
            case 1:
                DatabaseUserSettings.addOrUpdateDays(new UserSettings("descending"));
                break;
        }

        loadData();
    }

    @Override
    public void onSettingsOptionMenuClicked() {
        view.startSettingsActivity();
    }

    @Override
    public void onHolderClicked(String title, int days) {
        if(days == 0){
            view.showSnackbarSuccess(title+" is today!");
        }else if(days == 1){
            view.showSnackbarSuccess(title+" will start tomorrow!");
        }else{
            view.showSnackbarSuccess(title+" will start in "+days+" days!");
        }
    }

    @Override
    public void onMoreOptionsButtonClicked(Button button, int position) {
        view.showPopupMenu(button, position);
    }

    @Override
    public void onEditClicked(int id) {
        view.startEditCountdownFragment(id);
    }

    @Override
    public void onDeleteClicked(int id) {
        try{
            DatabaseCountdownDay.deleteDay(id);
            loadData();
            view.showSnackbarSuccess("Removed countdown");
        }catch (Exception e){
            view.setAdapterAndGetRecyclerView();
            view.showSnackbarError("Error with removing countdown day... Please try again");
            e.printStackTrace();
        }
    }

    @Override
    public void onFabClicked() {
        view.showSnackbarSuccess("Fab clicked :3");
        view.startNewCountdownFragment();
    }

}
