package com.example.mainapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ResultDrawView extends View {
    private Path mPath;
    private Paint mPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;


    public ResultDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPath = new Path();

        // 그리기 속성 설정
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 그림이 그려진 Bitmap을 Canvas에 그려줍니다.
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        // 현재 Path를 그려줍니다.
        canvas.drawPath(mPath, mPaint);
    }

    // Touch Event를 처리합니다.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                mCanvas.drawPath(mPath, mPaint);
                mPath.reset();
                break;
        }
        invalidate();

        return true;
    }

    // Bitmap을 생성하고 현재 그려진 그림을 저장합니다.
    public Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);

        return bitmap;
    }

    // 그림을 초기화합니다.
    public void clear() {
        mBitmap.eraseColor(Color.TRANSPARENT);
        invalidate();
    }

    // CustomView의 크기가 변경될 때마다 Bitmap을 다시 생성합니다.
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }


}
