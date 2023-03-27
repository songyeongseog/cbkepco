package com.example.mainapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecetpage);

        // main_btn1(해빙기) 클릭 시 해빙기 액티비티로 이동(해빙기 액티비티명 : SubActivity_selecetpage_1)
        ImageButton main_btn1 = (ImageButton) findViewById(R.id.main_btn1);
        main_btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), SubActivity1.class);
                startActivity(intent1);
            }
        });


        ImageButton main_btn2 = (ImageButton) findViewById(R.id.main_btn2);
        main_btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), SubActivity2.class);
                startActivity(intent2);
            }
        });

        ImageButton main_btn3 = (ImageButton) findViewById(R.id.main_btn3);
        main_btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), SubActivity3.class);
                startActivity(intent3);
            }
        });

        ImageButton main_btn4 = (ImageButton) findViewById(R.id.main_btn4);
        main_btn4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent4);
            }
        });
    }
}