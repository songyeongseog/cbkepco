package com.example.mainapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


// 해빙기 내 설비 선택화면 (activity_selecetpage_1.xml)
public class SubActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecetpage_1);


        Button sub1_btn1 = (Button) findViewById(R.id.sub1_btn1);
        sub1_btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent sub1_intent = new Intent(getApplicationContext(), FragmentActivity1.class);   // ★ 자바 클래스 수정해야함
                startActivity(sub1_intent);
            }
        });


        // 뒤로가기 버튼(MainActivity 이동) --> 처음 설비점검 선택하는 화면
        Button sub1_btn5 = (Button) findViewById(R.id.sub1_btn5);
        sub1_btn5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent5);
            }
        });


        // 점검결과 송부 버튼
        Button sub1_btn6 = (Button) findViewById(R.id.sub1_btn6);
        sub1_btn6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(getBaseContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Log.d("Subactivity 가져오깅", String.valueOf(savedInstanceState));

                dbHelper.deleteData("해빙기");
            }
        });

    }
}
