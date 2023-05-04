package com.example.mainapp;



import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DataCursorAdapter_Activity_result_sub_sub extends RecyclerView.Adapter<DataCursorAdapter_Activity_result_sub_sub.DataViewHolder> {

    private DataViewHolder mViewHolder;

    private Context mContext;
    private Cursor mCursor;
    private DBHelper mDBHelper;
    private SQLiteDatabase db;



    public DataCursorAdapter_Activity_result_sub_sub(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;

        mDBHelper = new DBHelper(context);
        db = mDBHelper.getWritableDatabase();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.activity_item_result_sub_sub, parent, false);
        return new DataViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        // 커서에서 데이터를 가져와서 뷰에 설정
        String list = mCursor.getString(mCursor.getColumnIndexOrThrow("list"));
        String result = mCursor.getString(mCursor.getColumnIndexOrThrow("result"));
        String edittext = mCursor.getString(mCursor.getColumnIndexOrThrow("edittext"));

        holder.listTextView.setText(list);
        holder.resultTextView.setText(result);
        holder.editTextView.setText(edittext);

        // 텍스트 값에 따라 색상을 지정
        if (result != null) {
            if (result.equals("양호")) {
                holder.resultTextView.setTextColor(Color.BLUE);
            } else if (result.equals("불량")) {
                holder.resultTextView.setTextColor(Color.RED);
            } else if (result.equals("해당없음")) {
                holder.listTextView.setTextColor(Color.LTGRAY);
                holder.resultTextView.setTextColor(Color.LTGRAY);
                holder.editTextView.setTextColor(Color.LTGRAY);
            }
        }else if (result == null){

        }





    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    // ViewHolder 클래스
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView listTextView;
        public TextView resultTextView;
        public TextView editTextView;


        // 컬럼 조회 뷰홀더

        public DataViewHolder(View itemView) {
            super(itemView);
            listTextView = itemView.findViewById(R.id.list_result);
            resultTextView = itemView.findViewById(R.id.result_result);
            editTextView = itemView.findViewById(R.id.result_result_edit);


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
