package com.sayor.org.cutmypie;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        // Register parse models
        ParseObject.registerSubclass(FoodData.class);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "pvoUWtH7upbjefp5UComlHmjoYVCEK7Fb3OkMZqc", "j4UnaWOHu7g2HPJ4GAXtjXMeXSoNE5uOkaqP18fs");

    }
}
