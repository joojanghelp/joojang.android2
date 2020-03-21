package com.joojang.bookfriend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.joojang.bookfriend.activity.BookFragment;
import com.joojang.bookfriend.activity.BookRegistFragment;
import com.joojang.bookfriend.activity.RecommBookFragment;
import com.joojang.bookfriend.activity.SettingFragment;
import com.joojang.bookfriend.utils.Tools;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;

    FragmentManager fragmentManager;
    RecommBookFragment recommBookFragment;
    BookFragment bookFragment;
    BookRegistFragment bookRegistFragment;
    SettingFragment settingFragment;


    TextView tv_ActionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        fragmentManager = getSupportFragmentManager();

        bookFragment = new BookFragment();
        recommBookFragment = new RecommBookFragment();
        bookRegistFragment = new BookRegistFragment();
        settingFragment = new SettingFragment();

        fragmentManager.beginTransaction().replace(R.id.container, recommBookFragment).commitAllowingStateLoss();

        initComponent();

    }

    private void initComponent() {
        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_recomm:
                        fragmentManager.beginTransaction().replace(R.id.container, recommBookFragment).commitAllowingStateLoss();
                        tv_ActionBarTitle.setText("권장도서");
                        return true;
                    case R.id.navigation_recent:
                        fragmentManager.beginTransaction().replace(R.id.container, bookFragment).commitAllowingStateLoss();
                        tv_ActionBarTitle.setText("독서목록");
                        return true;
                    case R.id.navigation_favorites:
                        fragmentManager.beginTransaction().replace(R.id.container, bookRegistFragment).commitAllowingStateLoss();
                        tv_ActionBarTitle.setText("도서등록");
                        return true;
                    case R.id.navigation_nearby:
                        fragmentManager.beginTransaction().replace(R.id.container, settingFragment).commitAllowingStateLoss();
                        tv_ActionBarTitle.setText("설정");
                        return true;
                }
                return false;
            }
        });

        tv_ActionBarTitle = findViewById(R.id.tv_ActionBarTitle);
        tv_ActionBarTitle.setText("권장도서");
    }






}
