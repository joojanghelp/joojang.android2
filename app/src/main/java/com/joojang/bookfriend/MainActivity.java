package com.joojang.bookfriend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.joojang.bookfriend.utils.Tools;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;

    FragmentManager fragmentManager;
    BookFragment bookFragment;
    BookRegistFragment bookRegistFragment;
    SettingFragment settingFragment;


    TextView tv_ActionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_light);

        fragmentManager = getSupportFragmentManager();

        bookFragment = new BookFragment();
        bookRegistFragment = new BookRegistFragment();
        settingFragment = new SettingFragment();

        fragmentManager.beginTransaction().replace(R.id.container, bookFragment).commit();

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
                    case R.id.navigation_recent:
                        fragmentManager.beginTransaction().replace(R.id.container, bookFragment).commit();
                        tv_ActionBarTitle.setText("도서목록");
                        return true;
                    case R.id.navigation_favorites:
                        fragmentManager.beginTransaction().replace(R.id.container, bookRegistFragment).commit();
                        tv_ActionBarTitle.setText("도서등록");
                        return true;
                    case R.id.navigation_nearby:
                        fragmentManager.beginTransaction().replace(R.id.container, settingFragment).commit();
                        tv_ActionBarTitle.setText("설정");
                        return true;
                }
                return false;
            }
        });

        NestedScrollView nested_content = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        nested_content.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < oldScrollY) { // up
                    animateNavigation(false);
                }
                if (scrollY > oldScrollY) { // down
                    animateNavigation(true);
                }
            }
        });

        tv_ActionBarTitle = findViewById(R.id.tv_ActionBarTitle);
        tv_ActionBarTitle.setText("도서목록");
    }

    boolean isNavigationHide = false;

    private void animateNavigation(final boolean hide) {
        if (isNavigationHide && hide || !isNavigationHide && !hide) return;
        isNavigationHide = hide;
        int moveY = hide ? (2 * navigation.getHeight()) : 0;
        navigation.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }



}
