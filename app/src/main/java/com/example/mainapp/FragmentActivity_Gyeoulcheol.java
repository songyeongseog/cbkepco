package com.example.mainapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class FragmentActivity_Gyeoulcheol extends AppCompatActivity {




    Toolbar toolbar;

    Fragment_Gyeoulcheol_Jeonryeok fragment_gyeoulcheol_jeonryeok;
    Fragment_Gyeoulcheol_Jeonryeokgu fragment_gyeoulcheol_jeonryeokgu;
    Fragment_Gyeoulcheol_Bisang fragment_gyeoulcheol_bisang;
    Fragment_Gyeoulcheol_Boan fragment_gyeoulcheol_boan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_gyeoulcheol);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        fragment_gyeoulcheol_jeonryeok = new Fragment_Gyeoulcheol_Jeonryeok();
        fragment_gyeoulcheol_jeonryeokgu = new Fragment_Gyeoulcheol_Jeonryeokgu();
        fragment_gyeoulcheol_bisang = new Fragment_Gyeoulcheol_Bisang();
        fragment_gyeoulcheol_boan = new Fragment_Gyeoulcheol_Boan();



        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_gyeoulcheol_jeonryeok).commit();

        TabLayout tabs = findViewById(R.id.tabs);


        tabs.addTab(tabs.newTab().setText("전력제어설비"));
        tabs.addTab(tabs.newTab().setText("전력구통신"));
        tabs.addTab(tabs.newTab().setText("비상통신설비"));
        tabs.addTab(tabs.newTab().setText("보안감시설비"));


        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);



        int selectedTab = getIntent().getIntExtra("SELECTED_TAB", 0);

        if (selectedTab == 0) {
            tabs.getTabAt(0).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_gyeoulcheol_jeonryeok).commit();
        } else if (selectedTab == 1) {
            tabs.getTabAt(1).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_gyeoulcheol_jeonryeokgu).commit();
        } else if (selectedTab == 2) {
            tabs.getTabAt(2).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_gyeoulcheol_bisang).commit();
        } else if (selectedTab == 3) {
            tabs.getTabAt(3).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_gyeoulcheol_boan).commit();
        }



        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭 : " + position);


                Fragment selected = null;
                if (position == 0) {
                    selected = fragment_gyeoulcheol_jeonryeok;
                } else if (position == 1) {
                    selected = fragment_gyeoulcheol_jeonryeokgu;
                } else if (position == 2) {
                    selected = fragment_gyeoulcheol_bisang;
                } else if (position == 3) {
                    selected = fragment_gyeoulcheol_boan;
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
