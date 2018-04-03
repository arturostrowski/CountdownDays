package pl.almestinio.countdowndays;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.facebook.stetho.Stetho;

import butterknife.BindString;
import butterknife.ButterKnife;
import pl.almestinio.countdowndays.database.DatabaseHelper;
import pl.almestinio.countdowndays.database.DatabaseUserSettings;
import pl.almestinio.countdowndays.model.UserSettings;
import pl.almestinio.countdowndays.ui.menuView.MenuFragment;

public class MainActivity extends AppCompatActivity {

    @BindString(R.string.database_user_settings_sort_ascending)
    String userSettingsSortAscending;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);
        DatabaseHelper.getInstance(this);
        fragmentManager = getSupportFragmentManager();

        if(DatabaseUserSettings.getUserSettings().isEmpty()){
            DatabaseUserSettings.addOrUpdateDays(new UserSettings(userSettingsSortAscending));
        }

        changeFragment(new MenuFragment(), MenuFragment.class.getName());

    }


    public void changeFragment(Fragment fragment, String tag){
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(tag).commit();
    }

}
