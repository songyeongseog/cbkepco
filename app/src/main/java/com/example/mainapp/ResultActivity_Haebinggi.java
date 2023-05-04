package com.example.mainapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class ResultActivity_Haebinggi extends AppCompatActivity {


    Toolbar toolbar;

    Result_fragment_Haebinggi_Boan result_fragment_haebinggi_boan;
    Result_fragment_Haebinggi_Museon result_fragment_haebinggi_museon;
    Result_fragment_Haebinggi_Gwang result_fragment_haebinggi_gwang;
    Result_fragment_Haebinggi_ICT result_fragment_haebinggi_ict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_haebinggi);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        result_fragment_haebinggi_boan = new Result_fragment_Haebinggi_Boan();    // 해빙기 보안감시설비 탭
        result_fragment_haebinggi_museon = new Result_fragment_Haebinggi_Museon();    // 해빙기 무선통신설비 탭
        result_fragment_haebinggi_gwang = new Result_fragment_Haebinggi_Gwang();    // 해빙기 광전송장치 탭
        result_fragment_haebinggi_ict = new Result_fragment_Haebinggi_ICT();    // 해빙기  ICT실 탭

        getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_haebinggi_boan).commit();

        TabLayout tabs = findViewById(R.id.tabs);

        tabs.addTab(tabs.newTab().setText("보안감시설비 점검결과"));
        tabs.addTab(tabs.newTab().setText("무선통신설비 점검결과"));
        tabs.addTab(tabs.newTab().setText("광전송장치 점검결과"));
        tabs.addTab(tabs.newTab().setText("ICT실 점검결과"));



        int selectedTab = getIntent().getIntExtra("SELECTED_TAB", 0);

        if (selectedTab == 0) {
            tabs.getTabAt(0).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_haebinggi_boan).commit();
        } else if (selectedTab == 1) {
            tabs.getTabAt(1).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_haebinggi_museon).commit();
        } else if (selectedTab == 2) {
            tabs.getTabAt(2).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_haebinggi_gwang).commit();
        } else if (selectedTab == 3) {
            tabs.getTabAt(3).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_haebinggi_ict).commit();
        }



        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭 : " + position);


                Fragment selected = null;
                if (position == 0) {
                    selected = result_fragment_haebinggi_boan;
                } else if (position == 1) {
                    selected = result_fragment_haebinggi_museon;
                } else if (position == 2) {
                    selected = result_fragment_haebinggi_gwang;
                } else if (position == 3) {
                    selected = result_fragment_haebinggi_ict;
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
