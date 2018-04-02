package pl.almestinio.countdowndays.ui.menuView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.countdowndays.MainActivity;
import pl.almestinio.countdowndays.R;
import pl.almestinio.countdowndays.adapter.CountdownDaysAdapter;
import pl.almestinio.countdowndays.database.DatabaseUserSettings;
import pl.almestinio.countdowndays.model.CountdownDay;
import pl.almestinio.countdowndays.ui.editCountdownView.EditCountdownFragment;
import pl.almestinio.countdowndays.ui.newCountdownView.NewCountdownFragment;
import pl.almestinio.countdowndays.ui.settingsView.SettingsActivity;

/**
 * Created by mesti193 on 3/31/2018.
 */

public class MenuFragment extends Fragment implements MenuContracts.View {

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    private CountdownDaysAdapter countdownDaysAdapter;
    private RecyclerView recyclerViewCountdownDays;
    private List<CountdownDay> countdownDayList = new ArrayList<CountdownDay>();

    private MenuContracts.Presenter presenter;

    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_fragment_title);
        fragmentManager = getFragmentManager();
        presenter = new MenuPresenter(this);

        recyclerViewCountdownDays = (RecyclerView) view.findViewById(R.id.recyclerViewCountdownDays);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerViewCountdownDays.setLayoutManager(layoutManager);



        floatingActionButton.setOnClickListener(v -> presenter.onFabClicked());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                    presenter.onSettingsOptionMenuClicked();
                return true;
            case R.id.action_sort_ascending:
                    presenter.onSortOptionMenuClicked(0);
                return true;
            case R.id.action_sort_descending:
                    presenter.onSortOptionMenuClicked(1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSnackbarSuccess(String message) {
        Snackbar.with(getContext(), null)
                .type(Type.SUCCESS)
                .message(message)
                .duration(Duration.SHORT)
                .show();
    }

    @Override
    public void showSnackbarError(String message) {
        Snackbar.with(getContext(), null)
                .type(Type.ERROR)
                .message(message)
                .duration(Duration.SHORT)
                .show();
    }

    @Override
    public void showPopupMenu(Button button, int position) {
        PopupMenu popup = new PopupMenu(getContext(), button);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.edit:
                            presenter.onEditClicked(countdownDayList.get(position).getId());
                        return true;
                    case R.id.delete:
                            presenter.onDeleteClicked(countdownDayList.get(position).getId());
                        return true;
                }
                return true;
            }
        });
        popup.show();
    }

    @Override
    public void showDaysFromDatabase(List<CountdownDay> countdownDayList) {
        this.countdownDayList = countdownDayList;
    }

    @Override
    public void sortList() {
        Collections.sort(countdownDayList, new Comparator<CountdownDay>() {
            @Override
            public int compare(CountdownDay e1, CountdownDay e2) {
                DateTime dt1 = new DateTime(e1.getDate());
                DateTime dt2 = new DateTime(e2.getDate());
                try{
                    if(DatabaseUserSettings.getUserSettings().get(0).getSort().equals("ascending")){
                        //Sort ascending
                        return dt1.compareTo(dt2);
                    }else{
                        //Sort descending
                        return dt2.compareTo(dt1);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return dt1.compareTo(dt2);
                }
            }
        });
    }

    @Override
    public void setAdapterAndGetRecyclerView() {
        countdownDaysAdapter = new CountdownDaysAdapter(countdownDayList, getContext(), presenter);
        recyclerViewCountdownDays.setAdapter(countdownDaysAdapter);
        recyclerViewCountdownDays.setNestedScrollingEnabled(false);
        recyclerViewCountdownDays.invalidate();
        countdownDaysAdapter.notifyDataSetChanged();
    }

    @Override
    public void startEditCountdownFragment(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        EditCountdownFragment editCountdownFragment = new EditCountdownFragment();
        editCountdownFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, editCountdownFragment);
        fragmentTransaction.addToBackStack(EditCountdownFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    public void startNewCountdownFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new NewCountdownFragment());
        fragmentTransaction.addToBackStack(NewCountdownFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    public void startSettingsActivity() {
        startActivity(new Intent(getActivity(), SettingsActivity.class));
    }

}
