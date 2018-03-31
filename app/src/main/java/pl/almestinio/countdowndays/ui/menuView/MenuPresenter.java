package pl.almestinio.countdowndays.ui.menuView;

import android.widget.Button;

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
        view.setAdapterAndGetRecyclerView();
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
}
