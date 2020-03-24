package com.joojang.bookfriend;

import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.dataResponse.RecommGubunResponse;
import com.joojang.bookfriend.model.RecommGubun;

import java.util.ArrayList;
import java.util.Date;

public class BaseApplication extends MultiDexApplication {

    private final String TAG = BaseApplication.class.getSimpleName();

    private static BaseApplication mInstance;
    public static Context context;

    private RetroClient retroClient;

    public String LOGINTOKEN;
    public String REFRESHTOKEN;
    public int EXPIRES_IN;
    public Date LOGINDATE;

    // 그룹구분
    public ArrayList<RecommGubun> RECOMMGUBUN;

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = this.getApplicationContext();
        retroClient = RetroClient.getInstance(context).createBaseApi();
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

    public String[] gubun_code(){
        String[] result = new String[RECOMMGUBUN.size()];
        for(int i=0 ; i<RECOMMGUBUN.size();i++){
            result[i] = RECOMMGUBUN.get(i).getCode_id();
        }
        return result;
    }

    public String[] gubun_name(){
        String[] result = new String[RECOMMGUBUN.size()];
        for(int i=0 ; i<RECOMMGUBUN.size();i++){
            result[i] = RECOMMGUBUN.get(i).getCode_name();
        }
        return result;
    }

}
