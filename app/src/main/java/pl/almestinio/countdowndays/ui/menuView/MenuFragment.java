package pl.almestinio.countdowndays.ui.menuView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import pl.almestinio.countdowndays.R;
import pl.almestinio.countdowndays.adapter.CountdownDaysAdapter;
import pl.almestinio.countdowndays.model.CountdownDay;

/**
 * Created by mesti193 on 3/31/2018.
 */

public class MenuFragment extends Fragment implements MenuContracts.View {

    private CountdownDaysAdapter countdownDaysAdapter;
    private RecyclerView recyclerViewCountdownDays;
    private List<CountdownDay> countdownDayList = new ArrayList<CountdownDay>();

    private MenuContracts.Presenter presenter;

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



        DateTime dateTime2 = new DateTime(2018, 4, 2, 23, 59);
        DateTime dateTime3 = new DateTime(2018, 4, 5, 23, 59);
        DateTime dateTime4 = new DateTime(2018, 4, 8, 23, 59);


        countdownDayList.add(new CountdownDay("Android App", dateTime2, "red"));
        countdownDayList.add(new CountdownDay("School", dateTime3, "red"));
        countdownDayList.add(new CountdownDay("Gym", dateTime4, "red"));

        presenter.loadData();

        return view;
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
    public void setAdapterAndGetRecyclerView() {
        countdownDaysAdapter = new CountdownDaysAdapter(countdownDayList, getContext(), presenter);
        recyclerViewCountdownDays.setAdapter(countdownDaysAdapter);
        recyclerViewCountdownDays.setNestedScrollingEnabled(false);
        recyclerViewCountdownDays.invalidate();
    }

}
