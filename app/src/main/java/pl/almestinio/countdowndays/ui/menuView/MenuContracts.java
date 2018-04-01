package pl.almestinio.countdowndays.ui.menuView;

import android.widget.Button;

/**
 * Created by mesti193 on 3/31/2018.
 */

public interface MenuContracts {

    interface View{
        void showSnackbarSuccess(String message);
        void showSnackbarError(String message);
        void showPopupMenu(Button button, int position);
        void getDaysFromDatabase();
        void sortList();
        void setAdapterAndGetRecyclerView();
        void startNewCountdownFragment();
    }

    interface Presenter{
        void loadData();
        void onSortOptionMenuClicked(int sort);
        void onHolderClicked(String title);
        void onMoreOptionsButtonClicked(Button button, int position);
        void onMoreOptionsButtonClicked(String title);
        void onFabClicked();
    }

}
