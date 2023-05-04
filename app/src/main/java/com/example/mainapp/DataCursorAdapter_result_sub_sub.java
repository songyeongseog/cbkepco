package com.example.mainapp;



import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;
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
        db = mDBHelper.getWritableDatabase();
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
        String mainarea = mCursor.getString(mCursor.getColumnIndexOrThrow("mainarea"));
        String subarea = mCursor.getString(mCursor.getColumnIndexOrThrow("subarea"));
        String detailarea = mCursor.getString(mCursor.getColumnIndexOrThrow("detailarea"));
        String list = mCursor.getString(mCursor.getColumnIndexOrThrow("list"));
//        String result = mCursor.getString(mCursor.getColumnIndexOrThrow("result"));


        holder.listTextView.setText(list);
//        holder.resultTextView.setText(result);

        holder.radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();

                db.beginTransaction();  // 데이터 변경 작업 수행
                try{
                    ContentValues values = new ContentValues();

                    values.put("result", holder.radioButton1.getText().toString()); // 양호 버튼 클릭 시 텍스트값 result 컬럼에 저장
                    values.put("date", convertTimeToDateTime(currentTime)); // 현재 시간 DateTime 형식으로 저장 및 date 컬럼에 저장

                    db.update("checklist", values,
                            "mainarea=? AND subarea=? AND detailarea=? AND list=?",
                            new String[] {mainarea, subarea, detailarea, list});
                    db.setTransactionSuccessful();  // 변경 내용 커밋
                } finally {
                    db.endTransaction();    // 트래젝션 종료
//                    db.close();
                }
            }
        });

        holder.radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();


                db.beginTransaction();  // 데이터 변경 작업 수행
                try{
                    ContentValues values = new ContentValues();

                    values.put("result", holder.radioButton2.getText().toString()); // 불량 버튼 클릭 시 텍스트값 result 컬럼에 저장
                    values.put("date", convertTimeToDateTime(currentTime)); // 현재 시간 DateTime 형식으로 저장 및 date 컬럼에 저장

                    db.update("checklist", values,
                            "mainarea=? AND subarea=? AND detailarea=? AND list=?",
                            new String[] {mainarea, subarea, detailarea, list});
                    db.setTransactionSuccessful();  // 변경 내용 커밋

                } finally {
                    db.endTransaction();    // 트래젝션 종료
//                    db.close();
                }
            }
        });

        holder.radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();

                db.beginTransaction();  // 데이터 변경 작업 수행
                try{
                    ContentValues values = new ContentValues();
                    values.put("result", holder.radioButton3.getText().toString()); // 해당없음 버튼 클릭 시 텍스트값 result 컬럼에 저장
                    values.put("date", convertTimeToDateTime(currentTime)); // 현재 시간 DateTime 형식으로 저장 및 date 컬럼에 저장

                    db.update("checklist", values,
                            "mainarea=? AND subarea=? AND detailarea=? AND list=?",
                            new String[] {mainarea, subarea, detailarea, list});
                    db.setTransactionSuccessful();  // 변경 내용 커밋
                } finally {
                    db.endTransaction();    // 트래젝션 종료
//                    db.close();
                }
            }
        });

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("클릭", "했습니다");
                showDialog();
            }

            private void showDialog() {

                if (mContext != null) {
                    CustomDialog dialog = new CustomDialog((Activity) mContext, mContext, mainarea, subarea, detailarea, list);
                    dialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    // ViewHolder 클래스
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView listTextView;
        public TextView resultTextView;
        public RadioButton radioButton1;
        public RadioButton radioButton2;
        public RadioButton radioButton3;
        public Button btn;

        // 컬럼 조회 뷰홀더

        public DataViewHolder(View itemView) {
            super(itemView);
            listTextView = itemView.findViewById(R.id.list_result);
            resultTextView = itemView.findViewById(R.id.result_result);

            radioButton1 = itemView.findViewById(R.id.radiobtn1);
            radioButton2 = itemView.findViewById(R.id.radiobtn2);
            radioButton3 = itemView.findViewById(R.id.radiobtn3);

            btn = itemView.findViewById(R.id.btn);

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

    // 밀리초 단위 시간 값을 DATETIME 형식으로 변환하는 메소드
    private String convertTimeToDateTime(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(time);
        return dateFormat.format(date);
    }
}
