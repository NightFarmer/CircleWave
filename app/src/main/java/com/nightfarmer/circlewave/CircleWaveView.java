package com.nightfarmer.circlewave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by zhangfan on 16-8-8.
 */
public class CircleWaveView extends View {


    private Paint paint;

    private float maxWaveRadius = 300;

    private long waveTime = 2000;

    private int waveRate = 4;

    private float[] waveList;
    private int centerX;
    private int centerY;

    public CircleWaveView(Context context) {
        this(context, null);
    }

    public CircleWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        waveList = new float[waveRate];
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(waveTime);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();

                for (int i = 0; i < waveList.length; i++) {
                    float v = value - i * 1.0f / waveRate;
                    if (v < 0 && waveList[i] > 0) {
                        v += 1;
                    }
                    waveList[i] = v;
                }

                invalidate();
            }
        });
        valueAnimator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Float waveRadius : waveList) {
            paint.setColor(Color.argb((int) (255 * (1 - waveRadius)), 60, 114, 236));
            canvas.drawCircle(centerX, centerY, waveRadius * maxWaveRadius, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight()/2;
    }
}
