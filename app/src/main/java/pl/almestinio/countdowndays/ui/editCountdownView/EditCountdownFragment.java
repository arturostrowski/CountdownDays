package pl.almestinio.countdowndays.ui.editCountdownView;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.countdowndays.MainActivity;
import pl.almestinio.countdowndays.R;
import pl.almestinio.countdowndays.database.DatabaseCountdownDay;
import pl.almestinio.countdowndays.model.CountdownDay;
import pl.almestinio.countdowndays.ui.menuView.MenuFragment;
import pl.almestinio.countdowndays.util.DateUtil;

/**
 * Created by mesti193 on 4/1/2018.
 */

public class EditCountdownFragment extends Fragment implements EditCountdownContracts.View, DatePickerDialog.OnDateSetListener{

    @BindView(R.id.buttonSetDate)
    Button buttonSetDate;
    @BindView(R.id.textViewNewCountdownDays)
    TextView textViewNewCountdownDays;
    @BindView(R.id.editTextDate)
    EditText editTextDate;
    @BindView(R.id.editTextTitle)
    EditText editTextTitle;
    @BindView(R.id.imageViewColorPicker)
    ImageView imageViewColorPicker;
    @BindView(R.id.imageViewSolidColorPicker)
    ImageView imageViewSolidColorPicker;

    @BindString(R.string.edit_fragment_title)
    String editFragmentTitle;
    @BindString(R.string.bundle_id)
    String bundleId;

    private Bundle bundle;

    private EditCountdownPresenter presenter;

    private FragmentManager fragmentManager;

    private CountdownDay countdownDay;
    private DateTime dateTime;
    private String hexColorStroke;
    private String hexColorSolid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_countdown, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(editFragmentTitle);
        bundle = getArguments();
        fragmentManager = getFragmentManager();
        presenter = new EditCountdownPresenter(this);

        editTextDate.setEnabled(false);

        loadCountdownDay();

        imageViewColorPicker.setOnClickListener(v -> presenter.getColor(hexColorStroke, 0));
        imageViewSolidColorPicker.setOnClickListener(v -> presenter.getColor(hexColorSolid, 1));

        buttonSetDate.setOnClickListener(v -> showDate(dateTime.getYear(), dateTime.getMonthOfYear()-1, dateTime.getDayOfMonth(), R.style.DatePickerSpinner));

        return view;
    }

    public void loadCountdownDay(){
        countdownDay = DatabaseCountdownDay.getDay(bundle.getInt(bundleId));
        hexColorStroke = countdownDay.getColorStroke();
        hexColorSolid = countdownDay.getColorSolid();
        dateTime = countdownDay.getDate();

        textViewNewCountdownDays.setText(String.valueOf(DateUtil.getDifferenceBetweenTwoDates(DateUtil.getTodayDay(), countdownDay.getDate())));
        GradientDrawable drawable = (GradientDrawable) textViewNewCountdownDays.getBackground();
        drawable.setStroke(14, Color.parseColor(countdownDay.getColorStroke()));
        drawable.setColor(Color.parseColor(countdownDay.getColorSolid()));

        GradientDrawable drawableStroke = (GradientDrawable) imageViewColorPicker.getBackground();
        drawableStroke.setColor(Color.parseColor(countdownDay.getColorStroke()));
        GradientDrawable drawableSolid = (GradientDrawable) imageViewSolidColorPicker.getBackground();
        drawableSolid.setColor(Color.parseColor(countdownDay.getColorSolid()));


        editTextDate.setText(countdownDay.getDate().getDayOfMonth()+"."+countdownDay.getDate().getMonthOfYear()+"."+countdownDay.getDate().getYear());
        editTextTitle.setText(countdownDay.getTitle());

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
                    presenter.editCountdownToDatabase(countdownDay, editTextTitle.getText().toString(), dateTime, hexColorStroke, hexColorSolid);
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
        new SpinnerDatePickerDialogBuilder().context(getContext()).callback(this).spinnerTheme(spinnerTheme).defaultDate(year, monthOfYear, dayOfMonth).build().show();
    }

    @Override
    public void showSnackbarSuccess(String message) {
        Snackbar.with(getContext(), null).type(Type.SUCCESS).message(message).duration(Duration.SHORT).show();
    }

    @Override
    public void showSnackbarError(String message) {
        Snackbar.with(getContext(), null).type(Type.ERROR).message(message).duration(Duration.SHORT).show();
    }

    @Override
    public void showColorPicker(String color, int id) {
        color = color.substring(1);
        int rgb = (int)Long.parseLong(color, 16);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = (rgb >> 0) & 0xFF;
        ColorPicker cp = new ColorPicker(getActivity(), r, g, b);

        cp.show();

        cp.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color) {

                switch (id) {
                    //STROKE
                    case 0:
                        hexColorStroke = String.format("#%02x%02x%02x", cp.getRed(), cp.getGreen(), cp.getBlue());

                        GradientDrawable drawableStroke = (GradientDrawable) textViewNewCountdownDays.getBackground();
                        drawableStroke.setStroke(14, Color.parseColor(hexColorStroke));

                        GradientDrawable drawableStroke2 = (GradientDrawable) imageViewColorPicker.getBackground();
                        drawableStroke2.setColor(Color.parseColor(hexColorStroke));

                        cp.dismiss();
                        break;
                    //SOLID
                    case 1:
                        hexColorSolid = String.format( "#%02x%02x%02x", cp.getRed(), cp.getGreen(), cp.getBlue());

                        GradientDrawable drawableSolid = (GradientDrawable) textViewNewCountdownDays.getBackground();
                        drawableSolid.setColor(Color.parseColor(hexColorSolid));

                        GradientDrawable drawableSolid2 = (GradientDrawable) imageViewSolidColorPicker.getBackground();
                        drawableSolid2.setColor(Color.parseColor(hexColorSolid));

                        cp.dismiss();
                        break;

                }
            }
        });
    }

    @Override
    public void startMenuFragment() {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new MenuFragment()).addToBackStack(MenuFragment.class.getName()).commit();
    }
}
