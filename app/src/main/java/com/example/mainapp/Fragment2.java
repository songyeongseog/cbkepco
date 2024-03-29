package com.example.mainapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.animation.ImageMatrixProperty;

import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends Fragment {


    private RecyclerView mRecyclerView;
    private DataCursorAdapter mAdapter;
    private DBHelper mDBHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment2, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDBHelper = new DBHelper(getContext());

//        // 데이터베이스를 열고 커서를 가져옴
//        Cursor cursor = mDBHelper.getData2();
//
//        Log.d("getData1()", String.valueOf(mDBHelper.getData2()));
//        Log.d("프래그먼트", String.valueOf(cursor));
//
//        // 어댑터를 생성하고 커서를 전달하여 데이터를 로드
//
//        mAdapter = new DataCursorAdapter(getContext(), cursor);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // DB 연결 해제
//        mDBHelper.close();
    }
}


