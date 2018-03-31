package pl.almestinio.countdowndays.ui.menuView;

/**
 * Created by mesti193 on 3/31/2018.
 */

public interface MenuContracts {

    interface View{
        void showSnackbarSuccess(String message);
        void showSnackbarError(String message);
        void setAdapterAndGetRecyclerView();
    }

    interface Presenter{
        void loadData();
        void onButtonClicked();
        void onHolderClicked();
        void onHolderClicked(String title);
        void onMoreOptionsButtonClicked();
        void onMoreOptionsButtonClicked(String title);
    }

}
