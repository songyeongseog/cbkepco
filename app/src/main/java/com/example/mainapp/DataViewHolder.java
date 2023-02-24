package com.example.mainapp;

import android.content.ContentValues;
import android.util.Log;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


public class DataViewHolder extends RecyclerView.ViewHolder {

    public TextView mainareaTextView, subareaTextView, detailareaTextView, listTextView, resultTextView, editTextView, imView;




    public DataViewHolder(View itemView) {
        super(itemView);
//        mainareaTextView = (TextView) itemView.findViewById(R.id.mainarea1);
//        subareaTextView = (TextView) itemView.findViewById(R.id.subarea1);
        detailareaTextView = (TextView) itemView.findViewById(R.id.detailarea1);
        listTextView = (TextView) itemView.findViewById(R.id.list1);
//        resultTextView = (TextView) itemView.findViewById(R.id.result1);
//        editTextView = (TextView) itemView.findViewById(R.id.edittext1);
//        imView = (TextView) itemView.findViewById(R.id.image1);

    }
}



//public class DataViewHolder extends RecyclerView.ViewHolder {
//    // View 변수 선언
//    public TextView mainarea1View;
//    public TextView subarea1View;
//    public TextView detailarea1View;
//    public TextView list1View;
//    public TextView result1View;
//    public TextView edittext1View;
//    public TextView image1View;
//
//    public DataViewHolder(View itemView) {
//        super(itemView);
//        // View 초기화
//        mainarea1View = itemView.findViewById(R.id.mainarea1);
//        subarea1View = itemView.findViewById(R.id.subarea1);
//        detailarea1View = itemView.findViewById(R.id.detailarea1);
//        list1View = itemView.findViewById(R.id.list1);
//        result1View = itemView.findViewById(R.id.result1);
//        edittext1View = itemView.findViewById(R.id.edittext1);
//        image1View = itemView.findViewById(R.id.image1);
//    }
//}
