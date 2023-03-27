package com.example.mainapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class FragmentActivity1 extends AppCompatActivity {




    Toolbar toolbar;

    Fragment1 fragment1;        // 해빙기 보안감시설비 탭 변수 선언 (가져오기)
    Fragment2 fragment2;        // 해빙기 무선통신설비 탭 변수 선언 (가져오기)
    Fragment3 fragment3;        // 해빙기 광전송장치 탭 변수 선언 (가져오기)
    Fragment4 fragment4;        // 해빙기  ICT실 탭 변수 선언 (가져오기)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        fragment1 = new Fragment1();    // 해빙기 보안감시설비 탭
        fragment2 = new Fragment2();    // 해빙기 무선통신설비 탭
        fragment3 = new Fragment3();    // 해빙기 광전송장치 탭
        fragment4 = new Fragment4();    // 해빙기  ICT실 탭

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("보안감시설비"));
        tabs.addTab(tabs.newTab().setText("무선통신설비"));
        tabs.addTab(tabs.newTab().setText("광전송장치"));
        tabs.addTab(tabs.newTab().setText("ICT실"));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭 : " + position);

                Fragment selected = null;
                if (position == 0) {
                    selected = fragment1;
                } else if (position == 1) {
                    selected = fragment2;
                } else if (position == 2) {
                    selected = fragment3;
                } else if (position == 3) {
                    selected = fragment4;
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
