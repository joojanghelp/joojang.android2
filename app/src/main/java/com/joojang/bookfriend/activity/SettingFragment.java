package com.joojang.bookfriend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.joojang.bookfriend.BaseApplication;
import com.joojang.bookfriend.R;
import com.joojang.bookfriend.api.RetroCallback;
import com.joojang.bookfriend.api.RetroClient;
import com.joojang.bookfriend.dataResponse.BookDetailResponse;
import com.joojang.bookfriend.dataResponse.DefaultResponse;
import com.joojang.bookfriend.dataResponse.SettingInfoResponse;
import com.joojang.bookfriend.login.LoginActivity;
import com.joojang.bookfriend.model.ActivityState;
import com.joojang.bookfriend.utils.Util;

import java.util.Date;

public class SettingFragment extends Fragment {

    private final String TAG = SettingFragment.class.getSimpleName();
    private RetroClient retroClient;

    private ViewGroup rootView;
    private TextInputEditText et_setting_replybooks, et_setting_readbooks, et_setting_name;
    private SwitchCompat switch_state;
    private TextView tv_logout;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);

        retroClient = RetroClient.getInstance(getActivity()).createBaseApi();

        initComponent();
        proc_getSettingInfo();

        return rootView;
    }

    private void initComponent(){
        et_setting_name = rootView.findViewById(R.id.et_setting_name);
        et_setting_readbooks = rootView.findViewById(R.id.et_setting_readbooks);
        et_setting_replybooks = rootView.findViewById(R.id.et_setting_replybooks);
        switch_state = rootView.findViewById(R.id.switch_state);
        tv_logout = rootView.findViewById(R.id.tv_logout);

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        switch_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proc_changeActivityState( ((SwitchCompat)v).isChecked() );
            }
        });

    }
    private void proc_getSettingInfo(){

        retroClient.getSettingInfo(new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "proc_getSettingInfo onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "proc_getSettingInfo onSuccess :"+code);
                SettingInfoResponse settingInfoResponse = (SettingInfoResponse) receiveData;
                if (settingInfoResponse != null) {
                    setData(settingInfoResponse);
                }
            }

            @Override
            public void onFail(int code, String message) {
                Log.d( TAG,"onFail : "+code);
                Log.d( TAG,"onFail : "+message);
            }
        });
    }

    private void setData(SettingInfoResponse settingInfoResponse){

        et_setting_name.setText( settingInfoResponse.getName() );
        et_setting_readbooks.setText( settingInfoResponse.getRead_book_count() );
        et_setting_replybooks.setText( settingInfoResponse.getActivity_count() );

        String activity_state = settingInfoResponse.getActivity_state();

        if ( activity_state.equals("N") ){
            switch_state.setChecked( false );
        }else{
            switch_state.setChecked( true );
        }




    }

    private void logout(){
        BaseApplication.getInstance().setLOGINTOKEN("");
        BaseApplication.getInstance().setREFRESHTOKEN("");
        Util.saveAccessTokenPreferences(getActivity(),"");
        Util.saveRefreshTokenPreferences(getActivity(),"");

        Intent intent = new Intent( getActivity() , LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void proc_changeActivityState(boolean isChecked){
        ActivityState activityState = new ActivityState();
        if (isChecked) {
            activityState.setActivity_state("Y");
        }else{
            activityState.setActivity_state("N");
        }

        retroClient.changeActivityState(activityState,new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "proc_changeActivityState onError ");
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.d(TAG, "proc_changeActivityState onSuccess :"+code);
                DefaultResponse defaultResponse = (DefaultResponse) receiveData;
                if (defaultResponse != null) {
                    Toast.makeText(getActivity(),"변경되었습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(int code, String message) {
                Log.d( TAG,"onFail : "+code);
                Log.d( TAG,"onFail : "+message);
            }
        });
    }
}
