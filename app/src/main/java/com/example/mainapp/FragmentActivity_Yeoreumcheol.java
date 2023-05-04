package com.example.mainapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class FragmentActivity_Yeoreumcheol extends AppCompatActivity {




    Toolbar toolbar;

    Fragment_Yeoreumcheol_Gyetong fragment_yeoreumcheol_gyetong;
    Fragment_Yeoreumcheol_Jeonryeok fragment_yeoreumcheol_jeonryeok;
    Fragment_Yeoreumcheol_Museon fragment_yeoreumcheol_museon;
    Fragment_Yeoreumcheol_Gwang fragment_yeoreumcheol_gwang;
    Fragment_Yeoreumcheol_Bisang fragment_yeoreumcheol_bisang;
    Fragment_Yeoreumcheol_Jeonryeokgu fragment_yeoreumcheol_jeonryeokgu;
    Fragment_Yeoreumcheol_Jeonwon fragment_yeoreumcheol_jeonwon;
    Fragment_Yeoreumcheol_Ict fragment_yeoreumcheol_ict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_yeoreumcheol);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        fragment_yeoreumcheol_gyetong = new Fragment_Yeoreumcheol_Gyetong();
        fragment_yeoreumcheol_jeonryeok = new Fragment_Yeoreumcheol_Jeonryeok();
        fragment_yeoreumcheol_museon = new Fragment_Yeoreumcheol_Museon();
        fragment_yeoreumcheol_gwang = new Fragment_Yeoreumcheol_Gwang();
        fragment_yeoreumcheol_bisang = new Fragment_Yeoreumcheol_Bisang();
        fragment_yeoreumcheol_jeonryeokgu = new Fragment_Yeoreumcheol_Jeonryeokgu();
        fragment_yeoreumcheol_jeonwon = new Fragment_Yeoreumcheol_Jeonwon();
        fragment_yeoreumcheol_ict = new Fragment_Yeoreumcheol_Ict();


        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_yeoreumcheol_gyetong).commit();

        TabLayout tabs = findViewById(R.id.tabs);


        tabs.addTab(tabs.newTab().setText("계통운영센터"));
        tabs.addTab(tabs.newTab().setText("전력제어설비"));
        tabs.addTab(tabs.newTab().setText("무선통신설비"));
        tabs.addTab(tabs.newTab().setText("광통신설비"));
        tabs.addTab(tabs.newTab().setText("비상통신설비"));
        tabs.addTab(tabs.newTab().setText("전력구통신"));
        tabs.addTab(tabs.newTab().setText("전원 및 공조설비"));
        tabs.addTab(tabs.newTab().setText("ICT실"));

        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);



        int selectedTab = getIntent().getIntExtra("SELECTED_TAB", 0);

        if (selectedTab == 0) {
            tabs.getTabAt(0).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_yeoreumcheol_gyetong).commit();
        } else if (selectedTab == 1) {
            tabs.getTabAt(1).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_yeoreumcheol_jeonryeok).commit();
        } else if (selectedTab == 2) {
            tabs.getTabAt(2).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_yeoreumcheol_museon).commit();
        } else if (selectedTab == 3) {
            tabs.getTabAt(3).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_yeoreumcheol_gwang).commit();
        }else if (selectedTab == 4) {
            tabs.getTabAt(4).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_yeoreumcheol_bisang).commit();
        }else if (selectedTab == 5) {
            tabs.getTabAt(5).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_yeoreumcheol_jeonryeokgu).commit();
        }else if (selectedTab == 6) {
            tabs.getTabAt(6).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_yeoreumcheol_jeonwon).commit();
        }else if (selectedTab == 7) {
            tabs.getTabAt(7).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_yeoreumcheol_ict).commit();
        }



        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭 : " + position);


                Fragment selected = null;
                if (position == 0) {
                    selected = fragment_yeoreumcheol_gyetong;
                } else if (position == 1) {
                    selected = fragment_yeoreumcheol_jeonryeok;
                } else if (position == 2) {
                    selected = fragment_yeoreumcheol_museon;
                } else if (position == 3) {
                    selected = fragment_yeoreumcheol_gwang;
                } else if (position == 4) {
                    selected = fragment_yeoreumcheol_bisang;
                } else if (position == 5) {
                    selected = fragment_yeoreumcheol_jeonryeokgu;
                } else if (position == 6) {
                    selected = fragment_yeoreumcheol_jeonwon;
                } else if (position == 7) {
                    selected = fragment_yeoreumcheol_ict;
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
