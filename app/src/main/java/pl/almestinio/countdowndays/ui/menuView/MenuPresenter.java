package pl.almestinio.countdowndays.ui.menuView;

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
    public void onButtonClicked() {
        view.showSnackbarSuccess("Button clicked ;>");
    }

    @Override
    public void onHolderClicked() {
        view.showSnackbarSuccess("Holder clicked :3");
    }

    @Override
    public void onHolderClicked(String title) {
        view.showSnackbarSuccess(title+" holder clicked :3");
    }

    @Override
    public void onMoreOptionsButtonClicked() {
        view.showSnackbarSuccess("More options button clicked :P");
    }

    @Override
    public void onMoreOptionsButtonClicked(String title) {
        view.showSnackbarSuccess(title+" more options button clicked :P");
    }
}
