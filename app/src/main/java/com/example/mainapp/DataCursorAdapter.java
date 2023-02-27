package com.example.mainapp;



import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.security.PrivateKey;
import java.util.List;


public class DataCursorAdapter extends RecyclerView.Adapter<DataCursorAdapter.DataViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    private SQLiteDatabase db;

    public DataCursorAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;

        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.recyclerview_item, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        // 커서가 데이터를 가리키도록 이동
//        mCursor.moveToPosition(position);
//
//        // 데이터에서 값을 가져와서 뷰에 설정
////        String result = mCursor.getString(mCursor.getColumnIndex("result"));
////        holder.radioButton1.setText(result);
//




        // 커서에서 데이터를 가져와서 뷰에 설정
        String mainarea = mCursor.getString(mCursor.getColumnIndexOrThrow("mainarea"));
        String subarea = mCursor.getString(mCursor.getColumnIndexOrThrow("subarea"));
        String detailarea = mCursor.getString(mCursor.getColumnIndexOrThrow("detailarea"));
        String list = mCursor.getString(mCursor.getColumnIndexOrThrow("list"));
        String result = mCursor.getString(mCursor.getColumnIndexOrThrow("result"));
        String edittext = mCursor.getString(mCursor.getColumnIndexOrThrow("edittext"));
        String im = mCursor.getString(mCursor.getColumnIndexOrThrow("image"));

        holder.detailareaTextView.setText(detailarea);
        holder.listTextView.setText(list);

        // 라디오 버튼 클릭 리스너
        holder.radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("result", holder.radioButton1.getText().toString());

                db.update("checklist", values,
                        "mainarea=? AND subarea=? AND detailarea=? AND list=?",
                        new String[] {mainarea, subarea, detailarea, list});

                Log.d("양호버튼", String.valueOf(values));
                Log.d("양호버튼 값", String.valueOf(db.update("checklist", values,
                        "mainarea=? AND subarea=? AND detailarea=? AND list=?",
                        new String[] {mainarea, subarea, detailarea, list})));
                Log.d("라디오버튼 1 인스턴스",String.valueOf(db));

            }
        });

        holder.radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("result", holder.radioButton2.getText().toString());
                db.update("checklist", values,
                        "mainarea=? AND subarea=? AND detailarea=? AND list=?",
                        new String[]{mainarea, subarea, detailarea, list});
            }
        });

            holder.radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("result", holder.radioButton3.getText().toString());
                db.update("checklist", values,
                        "mainarea=? AND subarea=? AND detailarea=? AND list=?",
                        new String[] {mainarea, subarea, detailarea, list});
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

                    CustomDialog dialog = new CustomDialog((Activity) mContext, mainarea, subarea, detailarea, list);

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
        public TextView detailareaTextView;
        public TextView listTextView;
        public RadioButton radioButton1;
        public RadioButton radioButton2;
        public RadioButton radioButton3;

        public Button btn;

        //// 컬럼 조회 뷰홀더

        public DataViewHolder(View itemView) {
            super(itemView);
            detailareaTextView = itemView.findViewById(R.id.detailarea1);
            listTextView = itemView.findViewById(R.id.list1);
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
}



// 다이어로그 호출코드
//
//    public void showDialog(Activity activity) {
//        final CustomDialog dialog = new CustomDialog(activity);
//        dialog.show();
//    }
