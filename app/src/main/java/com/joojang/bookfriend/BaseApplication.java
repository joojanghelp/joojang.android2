package com.joojang.bookfriend;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import java.util.Date;

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication mInstance;
    public static Context context;

    public String LOGINTOKEN;
    public String REFRESHTOKEN;
    public int EXPIRES_IN;
    public Date LOGINDATE;

    public static synchronized BaseApplication getInstance() {
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
        return "Bearer " + LOGINTOKEN;
    }

    public void setLOGINTOKEN(String LOGINTOKEN) {
        this.LOGINTOKEN = LOGINTOKEN;
    }


    public String getREFRESHTOKEN() {
        return REFRESHTOKEN;
    }

    public void setREFRESHTOKEN(String REFRESHTOKEN) {
        this.REFRESHTOKEN = REFRESHTOKEN;
    }

    public int getEXPIRES_IN() {
        return EXPIRES_IN;
    }

    public void setEXPIRES_IN(int EXPIRES_IN) {
        this.EXPIRES_IN = EXPIRES_IN;
    }

    public Date getLOGINDATE() {
        return LOGINDATE;
    }

    public void setLOGINDATE(Date LOGINDATE) {
        this.LOGINDATE = LOGINDATE;
    }
}
