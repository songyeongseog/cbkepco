package com.example.mainapp;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class DataCursorAdapter_result_sub extends RecyclerView.Adapter<DataCursorAdapter_result_sub.DataViewHolder> {

    private DataViewHolder mViewHolder;

    private Context mContext;
    private Cursor mCursor;
    private DBHelper mDBHelper;


    private SQLiteDatabase db;

    public DataCursorAdapter_result_sub(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;

        mDBHelper = new DBHelper(context);
//        db = dbHelper.getWritableDatabase();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.recyclerview_item_result_sub, parent, false);
        return new DataViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        // 커서에서 데이터를 가져와서 뷰에 설정
        String detailarea = mCursor.getString(mCursor.getColumnIndexOrThrow("detailarea"));
        holder.detailareaTextView.setText(detailarea);


        Cursor sub2_Cursor = mDBHelper.getDataSub1_3();


        DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
        holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    // ViewHolder 클래스
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView detailareaTextView;
        public RecyclerView sub2_areaRecyclerView;


        // 컬럼 조회 뷰홀더

        public DataViewHolder(View itemView) {
            super(itemView);
            detailareaTextView = itemView.findViewById(R.id.detailarea_result);
            sub2_areaRecyclerView = itemView.findViewById(R.id.recyclerview_result_sub_sub);
            sub2_areaRecyclerView.setLayoutManager(new LinearLayoutManager(sub2_areaRecyclerView.getContext()));
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
