package com.syh4834.chabak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    HomeFragment homeFragment = new HomeFragment();
    ListFragment listFragment = new ListFragment();
    LikeFragment likeFragment = new LikeFragment();
    MyPageFragment myPageFragment = new MyPageFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, homeFragment).commit();
                return true;
            case R.id.menu_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, listFragment).commit();
                return true;
            case R.id.menu_like:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, likeFragment).commit();
                return true;
            case R.id.menu_myPage:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, myPageFragment).commit();
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_bottom_navi);
        bottomNavigationView.setItemIconTintList(null);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, homeFragment).commit(); //특정 Fragment를 첫화면으로 설정하고 싶을 경우 homeFragment->해당Fragment로 변경경

       bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }
}