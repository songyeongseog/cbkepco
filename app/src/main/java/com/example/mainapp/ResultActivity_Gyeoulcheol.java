package com.example.mainapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class ResultActivity_Gyeoulcheol extends AppCompatActivity {


    Toolbar toolbar;

    Result_fragment_Gyeoulcheol_Jeonryeok result_fragment_gyeoulcheol_jeonryeok;
    Result_fragment_Gyeoulcheol_Jeonryeokgu result_fragment_gyeoulcheol_jeonryeokgucheol_jeonryeokgu;
    Result_fragment_Gyeoulcheol_Bisang result_fragment_gyeoulcheol_bisangcheol_bisang;
    Result_fragment_Gyeoulcheol_Boan result_fragment_gyeoulcheol_boan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_gyeoulcheol);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        result_fragment_gyeoulcheol_jeonryeok = new Result_fragment_Gyeoulcheol_Jeonryeok();
        result_fragment_gyeoulcheol_jeonryeokgucheol_jeonryeokgu = new Result_fragment_Gyeoulcheol_Jeonryeokgu();
        result_fragment_gyeoulcheol_bisangcheol_bisang = new Result_fragment_Gyeoulcheol_Bisang();
        result_fragment_gyeoulcheol_boan = new Result_fragment_Gyeoulcheol_Boan();



        getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_gyeoulcheol_jeonryeok).commit();

        TabLayout tabs = findViewById(R.id.tabs);

        tabs.addTab(tabs.newTab().setText("전력제어설비 점검결과"));
        tabs.addTab(tabs.newTab().setText("전력구통신 점검결과"));
        tabs.addTab(tabs.newTab().setText("비상통신설비 점검결과"));
        tabs.addTab(tabs.newTab().setText("보안감시설비 점검결과"));


        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);



        int selectedTab = getIntent().getIntExtra("SELECTED_TAB", 0);

        if (selectedTab == 0) {
            tabs.getTabAt(0).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_gyeoulcheol_jeonryeok).commit();
        } else if (selectedTab == 1) {
            tabs.getTabAt(1).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_gyeoulcheol_jeonryeokgucheol_jeonryeokgu).commit();
        } else if (selectedTab == 2) {
            tabs.getTabAt(2).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_gyeoulcheol_bisangcheol_bisang).commit();
        } else if (selectedTab == 3) {
            tabs.getTabAt(3).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_gyeoulcheol_boan).commit();
        }



        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭 : " + position);


                Fragment selected = null;
                if (position == 0) {
                    selected = result_fragment_gyeoulcheol_jeonryeok;
                } else if (position == 1) {
                    selected = result_fragment_gyeoulcheol_jeonryeokgucheol_jeonryeokgu;
                } else if (position == 2) {
                    selected = result_fragment_gyeoulcheol_bisangcheol_bisang;
                } else if (position == 3) {
                    selected = result_fragment_gyeoulcheol_boan;
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
