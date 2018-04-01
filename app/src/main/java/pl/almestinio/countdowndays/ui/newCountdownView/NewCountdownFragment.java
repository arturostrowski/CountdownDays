package pl.almestinio.countdowndays.ui.newCountdownView;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.countdowndays.MainActivity;
import pl.almestinio.countdowndays.R;
import pl.almestinio.countdowndays.model.CountdownDay;
import pl.almestinio.countdowndays.ui.menuView.MenuFragment;
import pl.almestinio.countdowndays.util.DateUtil;


/**
 * Created by mesti193 on 3/31/2018.
 */

public class NewCountdownFragment extends Fragment implements NewCountdownContracts.View, DatePickerDialog.OnDateSetListener{

    @BindView(R.id.buttonSetDate)
    Button buttonSetDate;
    @BindView(R.id.editTextDate)
    EditText editTextDate;
    @BindView(R.id.textViewNewCountdownDays)
    TextView textViewNewCountdownDays;
    @BindView(R.id.editTextTitle)
    EditText editTextTitle;

    @BindView(R.id.imageViewColorPicker)
    ImageView imageViewColorPicker;

    private NewCountdownContracts.Presenter presenter;

    private FragmentManager fragmentManager;

    private DateTime dateTime;
    private String hexColor = "#DD2C00";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_countdown, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Add new countdown");
        fragmentManager = getFragmentManager();

        presenter = new NewCountdownPresenter(this);

        editTextDate.setEnabled(false);

        DateTime dateTimeToday = DateUtil.getTodayDay();
        buttonSetDate.setOnClickListener(v -> showDate(dateTimeToday.getYear(), dateTimeToday.getMonthOfYear()-1, dateTimeToday.getDayOfMonth(), R.style.DatePickerSpinner));

        imageViewColorPicker.setOnClickListener(v -> presenter.getColor());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        GradientDrawable drawable2 = (GradientDrawable) textViewNewCountdownDays.getBackground();
        drawable2.setStroke(10, Color.parseColor("#DD2C00"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_new_countdown, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_create_new_countdown:
                if(editTextDate.getText().length()==0){
                    showSnackbarerror("Date is required!");
                }else{
                    presenter.addCountdownToDatabase(new CountdownDay(editTextTitle.getText().toString(), dateTime, hexColor));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        DateTime dateTimeEnd = new DateTime(year, monthOfYear+1, dayOfMonth, 23, 59);
        int days = DateUtil.getDifferenceBetweenTwoDates(DateUtil.getTodayDay(), dateTimeEnd);

        textViewNewCountdownDays.setText(String.valueOf(days));
        editTextDate.setText(dateTimeEnd.getDayOfMonth()+"."+dateTimeEnd.getMonthOfYear()+"."+dateTimeEnd.getYear());
        dateTime = dateTimeEnd;
    }


    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(getContext())
                .callback(this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
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
    public void showSnackbarerror(String message) {
        Snackbar.with(getContext(), null)
                .type(Type.ERROR)
                .message(message)
                .duration(Duration.SHORT)
                .show();
    }

    @Override
    public void showColorPicker() {
        ColorPicker cp = new ColorPicker(getActivity(), 255, 0, 0);

        cp.show();

        cp.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color) {
                hexColor = String.format( "#%02x%02x%02x", cp.getRed(), cp.getGreen(), cp.getBlue());
                imageViewColorPicker.setBackgroundColor(Color.parseColor(hexColor));
                GradientDrawable drawable2 = (GradientDrawable) textViewNewCountdownDays.getBackground();
                drawable2.setStroke(10, Color.parseColor(hexColor));
                cp.dismiss();
            }
        });

    }

    @Override
    public void startMenuFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new MenuFragment());
        fragmentTransaction.addToBackStack(MenuFragment.class.getName());
        fragmentTransaction.commit();
    }
}
