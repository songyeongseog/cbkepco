package com.example.mainapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class FragmentActivity_Haebinggi extends AppCompatActivity {




    Toolbar toolbar;

    Fragment_Haebinggi_Boan fragment_haebinggi_boan;        // 해빙기 보안감시설비 탭 변수 선언 (가져오기)
    Fragment_Haebinggi_Museon fragment_haebinggi_museon;        // 해빙기 무선통신설비 탭 변수 선언 (가져오기)
    Fragment_Haebinggi_Gwang fragment_haebinggi_gwang;        // 해빙기 광전송장치 탭 변수 선언 (가져오기)
    Fragment_Haebinggi_ICT fragment_haebinggi_ict;        // 해빙기  ICT실 탭 변수 선언 (가져오기)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_haebinggi);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        fragment_haebinggi_boan = new Fragment_Haebinggi_Boan();    // 해빙기 보안감시설비 탭
        fragment_haebinggi_museon = new Fragment_Haebinggi_Museon();    // 해빙기 무선통신설비 탭
        fragment_haebinggi_gwang = new Fragment_Haebinggi_Gwang();    // 해빙기 광전송장치 탭
        fragment_haebinggi_ict = new Fragment_Haebinggi_ICT();    // 해빙기  ICT실 탭

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_haebinggi_boan).commit();

        TabLayout tabs = findViewById(R.id.tabs);

        tabs.addTab(tabs.newTab().setText("보안감시설비"));
        tabs.addTab(tabs.newTab().setText("무선통신설비"));
        tabs.addTab(tabs.newTab().setText("광전송장치"));
        tabs.addTab(tabs.newTab().setText("ICT실"));



        int selectedTab = getIntent().getIntExtra("SELECTED_TAB", 0);

        if (selectedTab == 0) {
            tabs.getTabAt(0).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_haebinggi_boan).commit();
        } else if (selectedTab == 1) {
            tabs.getTabAt(1).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_haebinggi_museon).commit();
        } else if (selectedTab == 2) {
            tabs.getTabAt(2).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_haebinggi_gwang).commit();
        } else if (selectedTab == 3) {
            tabs.getTabAt(3).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_haebinggi_ict).commit();
        }



        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭 : " + position);


                Fragment selected = null;
                if (position == 0) {
                    selected = fragment_haebinggi_boan;
                } else if (position == 1) {
                    selected = fragment_haebinggi_museon;
                } else if (position == 2) {
                    selected = fragment_haebinggi_gwang;
                } else if (position == 3) {
                    selected = fragment_haebinggi_ict;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });



    }

}
