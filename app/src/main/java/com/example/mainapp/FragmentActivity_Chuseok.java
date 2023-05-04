package com.example.mainapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class FragmentActivity_Chuseok extends AppCompatActivity {




    Toolbar toolbar;

    Fragment_Chuseok_Boan fragment_chuseok_boan;
    Fragment_Chuseok_Jeonwon fragment_chuseok_jeonwon;
    Fragment_Chuseok_Bisang fragment_chuseok_bisang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_chuseok);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        fragment_chuseok_boan = new Fragment_Chuseok_Boan();
        fragment_chuseok_jeonwon = new Fragment_Chuseok_Jeonwon();
        fragment_chuseok_bisang = new Fragment_Chuseok_Bisang();



        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_chuseok_boan).commit();

        TabLayout tabs = findViewById(R.id.tabs);


        tabs.addTab(tabs.newTab().setText("보안감시설비"));
        tabs.addTab(tabs.newTab().setText("전원설비ㆍICT실"));
        tabs.addTab(tabs.newTab().setText("비상통신설비"));


        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);



        int selectedTab = getIntent().getIntExtra("SELECTED_TAB", 0);

        if (selectedTab == 0) {
            tabs.getTabAt(0).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_chuseok_boan).commit();
        } else if (selectedTab == 1) {
            tabs.getTabAt(1).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_chuseok_jeonwon).commit();
        } else if (selectedTab == 2) {
            tabs.getTabAt(2).select();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_chuseok_bisang).commit();
        }



        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭 : " + position);


                Fragment selected = null;
                if (position == 0) {
                    selected = fragment_chuseok_boan;
                } else if (position == 1) {
                    selected = fragment_chuseok_jeonwon;
                } else if (position == 2) {
                    selected = fragment_chuseok_bisang;
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
