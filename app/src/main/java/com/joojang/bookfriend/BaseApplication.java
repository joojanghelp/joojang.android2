package com.joojang.bookfriend;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication mInstance;
    public static Context context;

    public String LOGINTOKEN;

    public static synchronized BaseApplication getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = this.getApplicationContext();
    }

    public String getLOGINTOKEN() {
        return LOGINTOKEN;
    }

    public String getBearerLOGINTOKEN() {
        return "Bearer "+LOGINTOKEN;
    }

    public void setLOGINTOKEN(String LOGINTOKEN) {
        this.LOGINTOKEN = LOGINTOKEN;
    }
}
