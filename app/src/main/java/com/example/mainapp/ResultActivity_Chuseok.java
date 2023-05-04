package com.example.mainapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class ResultActivity_Chuseok extends AppCompatActivity {


    Toolbar toolbar;

    Result_fragment_Chuseok_Boan result_fragment_chuseok_boan;
    Result_fragment_Chuseok_Jeonwon result_fragment_chuseok_jeonwon;
    Result_fragment_Chuseok_Bisang result_fragment_chuseok_bisang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_chuseok);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        result_fragment_chuseok_boan = new Result_fragment_Chuseok_Boan();
        result_fragment_chuseok_jeonwon = new Result_fragment_Chuseok_Jeonwon();
        result_fragment_chuseok_bisang = new Result_fragment_Chuseok_Bisang();



        getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_chuseok_boan).commit();

        TabLayout tabs = findViewById(R.id.tabs);

        tabs.addTab(tabs.newTab().setText("보안감시설비 점검결과"));
        tabs.addTab(tabs.newTab().setText("전원설비 · ICT실 점검결과"));
        tabs.addTab(tabs.newTab().setText("비상통신설비 점검결과"));

        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);



        int selectedTab = getIntent().getIntExtra("SELECTED_TAB", 0);

        if (selectedTab == 0) {
            tabs.getTabAt(0).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_chuseok_boan).commit();
        } else if (selectedTab == 1) {
            tabs.getTabAt(1).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_chuseok_jeonwon).commit();
        } else if (selectedTab == 2) {
            tabs.getTabAt(2).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, result_fragment_chuseok_bisang).commit();
        }



        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭 : " + position);


                Fragment selected = null;
                if (position == 0) {
                    selected = result_fragment_chuseok_boan;
                } else if (position == 1) {
                    selected = result_fragment_chuseok_jeonwon;
                } else if (position == 2) {
                    selected = result_fragment_chuseok_bisang;
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
