package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ScoreProgressView extends View {

    private int maxScore =180;
    private int currentScore;
    private int progressColor = Color.GREEN;

    public ScoreProgressView(Context context) {
        super(context);
        init();
    }

    public ScoreProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScoreProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Initialization code, if any
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
        invalidate();
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int viewWidth = getWidth();
        int viewHeight = getHeight();
        int centerX = viewWidth / 2;
        int centerY = viewHeight / 2;

        int strokeWidth = 50;
        RectF oval = new RectF(strokeWidth, strokeWidth, viewWidth - strokeWidth, viewHeight - strokeWidth);

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawOval(oval, paint);

        paint.setColor(progressColor);
        float sweepAngle = (float) currentScore / maxScore * 360;
        canvas.drawArc(oval, -90, sweepAngle, false, paint);
    }
}