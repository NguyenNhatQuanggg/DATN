package com.project2.banhangmypham;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.project2.banhangmypham.localstorage.LocalStorageManager;

public class OrchidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        LocalStorageManager.create(this);
    }
}
