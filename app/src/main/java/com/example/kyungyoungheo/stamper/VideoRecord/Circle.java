package com.example.kyungyoungheo.stamper.VideoRecord;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.kyungyoungheo.stamper.R;

public class Circle extends View {
    private static final int START_ANGLE_POINT = 270;


    private final Paint paint;
    private final RectF rect;

    private float angle;
    private float circleWidth;
    private float circleHeight;
    private float strokeWidth;


    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleBtn);
        circleWidth = a.getDimension(R.styleable.CircleBtn_cb_width, circleWidth);
        circleHeight = a.getDimension(R.styleable.CircleBtn_cb_height, circleHeight);
        strokeWidth = a.getDimension(R.styleable.CircleBtn_cb_stroke,strokeWidth);



        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        //Circle color
        paint.setColor(Color.RED);


        //size 200x200 example
        rect = new RectF(strokeWidth, strokeWidth, circleHeight + strokeWidth, circleWidth + strokeWidth);
        //rect = new RectF(, 0, circleHeight + 0, circleWidth + 0);

        //Initial Angle (optional, it can be zero)
        angle = 0;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

}