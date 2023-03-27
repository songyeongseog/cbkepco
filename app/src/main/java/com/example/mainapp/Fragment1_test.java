package com.example.mainapp;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Fragment1_test extends Fragment {


    private RecyclerView mRecyclerView;
    private DataCursorAdapter_result mAdapter;
    private DBHelper mDBHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1_test, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView_result);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mDBHelper = new DBHelper(getContext());

        // 데이터베이스를 열고 커서를 가져옴
        Cursor cursor = mDBHelper.getDataMain1();


        // 어댑터를 생성하고 커서를 전달하여 데이터를 로드
        mAdapter = new DataCursorAdapter_result(getContext(), cursor);
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


