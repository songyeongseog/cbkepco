package com.example.mainapp;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class DataCursorAdapter_result_sub_sub extends RecyclerView.Adapter<DataCursorAdapter_result_sub_sub.DataViewHolder> {

    private DataViewHolder mViewHolder;

    private Context mContext;
    private Cursor mCursor;
    private DBHelper mDBHelper;


    private SQLiteDatabase db;

    public DataCursorAdapter_result_sub_sub(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;

        mDBHelper = new DBHelper(context);
//        db = dbHelper.getWritableDatabase();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.recyclerview_item_result_sub_sub, parent, false);
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

        holder.listTextView.setText(list);
        holder.resultTextView.setText(result);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    // ViewHolder 클래스
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView listTextView;
        public TextView resultTextView;

        // 컬럼 조회 뷰홀더

        public DataViewHolder(View itemView) {
            super(itemView);
            listTextView = itemView.findViewById(R.id.list_result);
            resultTextView = itemView.findViewById(R.id.result_result);

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
