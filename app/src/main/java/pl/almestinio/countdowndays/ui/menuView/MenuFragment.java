package pl.almestinio.countdowndays.ui.menuView;

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
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.countdowndays.R;
import pl.almestinio.countdowndays.adapter.CountdownDaysAdapter;
import pl.almestinio.countdowndays.database.DatabaseCountdownDay;
import pl.almestinio.countdowndays.database.DatabaseUserSettings;
import pl.almestinio.countdowndays.model.CountdownDay;
import pl.almestinio.countdowndays.ui.newCountdownView.NewCountdownFragment;

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

        recyclerViewCountdownDays = (RecyclerView) view.findViewById(R.id.recyclerViewCountdownDays);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerViewCountdownDays.setLayoutManager(layoutManager);

        presenter = new MenuPresenter(this);


//        DateTime dateTime2 = new DateTime(2018, 4, 1, 23, 59);
//        DateTime dateTime3 = new DateTime(2018, 4, 5, 23, 59);
//        DateTime dateTime4 = new DateTime(2018, 4, 8, 23, 59);
//
//
//        countdownDayList.add(new CountdownDay("Android App", dateTime2, "red"));
//        countdownDayList.add(new CountdownDay("School", dateTime3, "green"));
//        countdownDayList.add(new CountdownDay("Gym", dateTime4, "red"));


        floatingActionButton.setOnClickListener(v -> presenter.onFabClicked());


        presenter.loadData();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_ascending) {

                presenter.onSortOptionMenuClicked(0);

            return true;
        }else if(id == R.id.action_sort_descending){

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
                    case R.id.share:
                        return true;
                    case R.id.edit:
                        return true;
                    case R.id.delete:
                        try{
                            countdownDaysAdapter.notifyItemRemoved(position);
                            countdownDaysAdapter.notifyItemChanged(position, countdownDaysAdapter.getItemCount() - position);
                            DatabaseCountdownDay.deleteDay(countdownDayList.get(position).getId());
                            countdownDayList.remove(position);
                            showSnackbarError("Removed countdown");
                        }catch (Exception e){
                            countdownDaysAdapter.notifyDataSetChanged();
                            showSnackbarError("Error with removing countdown day... Please try again");
                            e.printStackTrace();
                        }
//                        presenter.loadData();
                        return true;
                }
                return true;
            }
        });
        popup.show();
    }

    @Override
    public void addItem() {

        Random rand = new Random();
        int xd = rand.nextInt((20 - 2) + 1) + 2;
        DateTime dateTime4 = new DateTime(2018, 4, xd, 23, 59);
        int value = rand.nextInt(2);

        if(value==0){
            countdownDayList.add(new CountdownDay("FAB", dateTime4, "red"));
            DatabaseCountdownDay.addOrUpdateDays(new CountdownDay("FAB", dateTime4, "red"));
        }else{
            countdownDayList.add(new CountdownDay("Hehe", dateTime4, "green"));
            DatabaseCountdownDay.addOrUpdateDays(new CountdownDay("Hehe", dateTime4, "green"));
        }
        countdownDaysAdapter.notifyDataSetChanged();
    }

    @Override
    public void getDaysFromDatabase() {
        if(countdownDayList != null) countdownDayList.clear();
        try{
            List<CountdownDay> countdownDays = DatabaseCountdownDay.getDays();
            for(CountdownDay countdownDay : countdownDays){
                countdownDayList.add(countdownDay);
            }
            if(countdownDayList.isEmpty()){
                showSnackbarError("Empty");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
    }

    @Override
    public void startNewCountdownFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new NewCountdownFragment());
        fragmentTransaction.addToBackStack(NewCountdownFragment.class.getName());
        fragmentTransaction.commit();
    }

}
