package com.example.mainapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class ResultActivity_Yeoreumcheol extends AppCompatActivity {


    Toolbar toolbar;

    Result_fragment_Yeoreumcheol_Gyetong result_fragment_yeoreumcheol_gyetong;
    Result_fragment_Yeoreumcheol_Jeonryeok result_fragment_yeoreumcheol_jeonryeok;
    Result_fragment_Yeoreumcheol_Museon result_fragment_yeoreumcheol_museon;
    Result_fragment_Yeoreumcheol_Gwang result_fragment_yeoreumcheol_gwang;
    Result_fragment_Yeoreumcheol_Bisang result_fragment_yeoreumcheol_bisang;
    Result_fragment_Yeoreumcheol_Jeonryeokgu result_fragment_yeoreumcheol_jeonryeokgu;
    Result_fragment_Yeoreumcheol_Jeonwon result_fragment_yeoreumcheol_jeonwon;
    Result_fragment_Yeoreumcheol_Ict result_fragment_yeoreumcheol_ict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_yeoreumcheol);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        result_fragment_yeoreumcheol_gyetong = new Result_fragment_Yeoreumcheol_Gyetong();
        result_fragment_yeoreumcheol_jeonryeok = new Result_fragment_Yeoreumcheol_Jeonryeok();
        result_fragment_yeoreumcheol_museon = new Result_fragment_Yeoreumcheol_Museon();
        result_fragment_yeoreumcheol_gwang = new Result_fragment_Yeoreumcheol_Gwang();
        result_fragment_yeoreumcheol_bisang = new Result_fragment_Yeoreumcheol_Bisang();
        result_fragment_yeoreumcheol_jeonryeokgu = new Result_fragment_Yeoreumcheol_Jeonryeokgu();
        result_fragment_yeoreumcheol_jeonwon = new Result_fragment_Yeoreumcheol_Jeonwon();
        result_fragment_yeoreumcheol_ict = new Result_fragment_Yeoreumcheol_Ict();


        getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_yeoreumcheol_gyetong).commit();

        TabLayout tabs = findViewById(R.id.tabs);

        tabs.addTab(tabs.newTab().setText("계통운영센터 점검결과"));
        tabs.addTab(tabs.newTab().setText("전력제어설비 점검결과"));
        tabs.addTab(tabs.newTab().setText("무선통신설비 점검결과"));
        tabs.addTab(tabs.newTab().setText("광통신설비 점검결과"));
        tabs.addTab(tabs.newTab().setText("비상통신설비 점검결과"));
        tabs.addTab(tabs.newTab().setText("전력구통신 점검결과"));
        tabs.addTab(tabs.newTab().setText("전원 및 공조설비 점검결과"));
        tabs.addTab(tabs.newTab().setText("ICT실 점검결과"));

        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);



        int selectedTab = getIntent().getIntExtra("SELECTED_TAB", 0);

        if (selectedTab == 0) {
            tabs.getTabAt(0).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_yeoreumcheol_gyetong).commit();
        } else if (selectedTab == 1) {
            tabs.getTabAt(1).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_yeoreumcheol_jeonryeok).commit();
        } else if (selectedTab == 2) {
            tabs.getTabAt(2).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_yeoreumcheol_museon).commit();
        } else if (selectedTab == 3) {
            tabs.getTabAt(3).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_yeoreumcheol_gwang).commit();
        } else if (selectedTab == 4) {
            tabs.getTabAt(4).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_yeoreumcheol_bisang).commit();
        } else if (selectedTab == 5) {
            tabs.getTabAt(5).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_yeoreumcheol_jeonryeokgu).commit();
        }else if (selectedTab == 6) {
            tabs.getTabAt(6).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_yeoreumcheol_jeonwon).commit();
        }else if (selectedTab == 7) {
            tabs.getTabAt(7).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_yeoreumcheol_ict).commit();
        }



        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭 : " + position);


                Fragment selected = null;
                if (position == 0) {
                    selected = result_fragment_yeoreumcheol_gyetong;
                } else if (position == 1) {
                    selected = result_fragment_yeoreumcheol_jeonryeok;
                } else if (position == 2) {
                    selected = result_fragment_yeoreumcheol_museon;
                } else if (position == 3) {
                    selected = result_fragment_yeoreumcheol_gwang;
                } else if (position == 4) {
                    selected = result_fragment_yeoreumcheol_bisang;
                } else if (position == 5) {
                    selected = result_fragment_yeoreumcheol_jeonryeokgu;
                } else if (position == 6) {
                    selected = result_fragment_yeoreumcheol_jeonwon;
                } else if (position == 7) {
                    selected = result_fragment_yeoreumcheol_ict;
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
