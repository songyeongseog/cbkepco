package com.example.mainapp;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class DataCursorAdapter_result extends RecyclerView.Adapter<DataCursorAdapter_result.DataViewHolder> {

    private DataViewHolder mViewHolder;

    private Context mContext;
    private Cursor mCursor;

    private DBHelper mDBHelper;


    private SQLiteDatabase db;


    public DataCursorAdapter_result(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        mDBHelper = new DBHelper(context);

//        db = mDBHelper.getWritableDatabase();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.recyclerview_item_result, parent, false);
        return new DataViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        // 커서에서 데이터를 가져와서 뷰에 설정
        String subarea = mCursor.getString(mCursor.getColumnIndexOrThrow("subarea"));

        holder.subareaTextView.setText(subarea);


        // 하위 리사이클러뷰 어댑터 설정
//        List<Cursor> cursorList = new ArrayList<>();
//        cursorList.add(mDBHelper.getDataSub1_1());
//        cursorList.add(mDBHelper.getDataSub1_2());
        Cursor subCursor = mDBHelper.getDataSub1_2();

//        // 중분류 아이디 가져오기
//        int subcategoryId = mCursor.getInt(mCursor.getColumnIndexOrThrow("id"));
//
//        // subcategoryId를 기준으로 item 테이블에서 데이터 가져오기
//        Cursor itemCursor = mDBHelper.getItemsBySubcategoryId(subcategoryId);


        DataCursorAdapter_result_sub subareaAdapter = new DataCursorAdapter_result_sub(mContext, subCursor);
        holder.subareaRecyclerView.setAdapter(subareaAdapter);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    // ViewHolder 클래스
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView subareaTextView;
        public RecyclerView subareaRecyclerView;

        // 컬럼 조회 뷰홀더

        public DataViewHolder(View itemView) {
            super(itemView);
            subareaTextView = itemView.findViewById(R.id.subarea_result);
            subareaRecyclerView = itemView.findViewById(R.id.recyclerview_result_sub);
            subareaRecyclerView.setLayoutManager(new LinearLayoutManager(subareaRecyclerView.getContext()));


        }
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
