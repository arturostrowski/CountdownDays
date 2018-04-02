package pl.almestinio.countdowndays.ui.menuView;

import android.widget.Button;

import java.util.List;

import pl.almestinio.countdowndays.model.CountdownDay;

/**
 * Created by mesti193 on 3/31/2018.
 */

public interface MenuContracts {

    interface View{
        void showSnackbarSuccess(String message);
        void showSnackbarError(String message);
        void showPopupMenu(Button button, int position);
        void showDaysFromDatabase(List<CountdownDay> countdownDayList);
        void sortList();
        void setAdapterAndGetRecyclerView();
        void startEditCountdownFragment(int id);
        void startNewCountdownFragment();
        void startSettingsActivity();
    }

    interface Presenter{
        void loadData();
        void onSortOptionMenuClicked(int sort);
        void onSettingsOptionMenuClicked();
        void onHolderClicked(String title, int days);
        void onMoreOptionsButtonClicked(Button button, int position);
        void onEditClicked(int position);
        void onDeleteClicked(int id);
        void onFabClicked();
    }

}
