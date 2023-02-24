package com.example.mainapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecetpage);

        // main_btn1(해빙기) 클릭 시 해빙기 액티비티로 이동(해빙기 액티비티명 : SubActivity_selecetpage_1)
        Button main_btn1 = (Button) findViewById(R.id.main_btn1);
                main_btn1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(getApplicationContext(), SubActivity1.class);
                                startActivity(intent1);
                    }
                });
    }
}