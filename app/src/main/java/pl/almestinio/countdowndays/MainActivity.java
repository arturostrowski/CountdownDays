package pl.almestinio.countdowndays;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.facebook.stetho.Stetho;

import butterknife.ButterKnife;
import pl.almestinio.countdowndays.database.DatabaseHelper;
import pl.almestinio.countdowndays.database.DatabaseUserSettings;
import pl.almestinio.countdowndays.model.UserSettings;
import pl.almestinio.countdowndays.ui.menuView.MenuFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);
        DatabaseHelper.getInstance(this);
        fragmentManager = getSupportFragmentManager();

        DatabaseUserSettings.addOrUpdateDays(new UserSettings("ascending"));

        changeFragment(new MenuFragment(), MenuFragment.class.getName());


//        startService(new Intent(this, ForegroundService.class));
    }


    public void changeFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

}
