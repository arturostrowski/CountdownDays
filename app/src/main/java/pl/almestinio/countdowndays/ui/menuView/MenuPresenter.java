package pl.almestinio.countdowndays.ui.menuView;

import android.widget.Button;

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
        view.getDaysFromDatabase();
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
    public void onHolderClicked(String title) {
        view.showSnackbarSuccess(title+" holder clicked :3");
    }

    @Override
    public void onMoreOptionsButtonClicked(Button button, int position) {
        view.showPopupMenu(button, position);
    }

    @Override
    public void onMoreOptionsButtonClicked(String title) {
        view.showSnackbarSuccess(title+" more options button clicked :P");
    }

    @Override
    public void onFabClicked() {
        view.showSnackbarSuccess("Fab clicked :3");
        view.startNewCountdownFragment();
    }
}
