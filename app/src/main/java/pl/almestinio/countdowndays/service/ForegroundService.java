package pl.almestinio.countdowndays.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mesti193 on 4/2/2018.
 */

public class ForegroundService extends Service {

    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();

        timer = new Timer();
        timer.schedule(timerTask, 2000, 20*1000);

    }


    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Log.i("TIMER", "TASK IN SERVICE");
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SERVICE", "START");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}