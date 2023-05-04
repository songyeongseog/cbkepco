package com.example.mainapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Fragment_Yeoreumcheol_Bisang extends Fragment {


    private RecyclerView mRecyclerView;
    private DataCursorAdapter_result mAdapter;
    private DBHelper mDBHelper;
    private Result_fragment_Haebinggi_Boan result_fragment_haebinggi_boan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView_result);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        Button btn3 = (Button) view.findViewById(R.id.frgmentBtn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sub2_intent = new Intent(getActivity(), ResultActivity_Yeoreumcheol.class);
                sub2_intent.putExtra("SELECTED_TAB", 0);
                startActivity(sub2_intent);

            }
        });


        mDBHelper = new DBHelper(getContext());

        // 데이터베이스를 열고 커서를 가져옴
        Cursor cursor = mDBHelper.getDataYeouremcheolBisang();


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


