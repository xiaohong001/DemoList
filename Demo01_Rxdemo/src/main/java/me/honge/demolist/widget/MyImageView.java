package me.honge.demolist.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by hong on 15/12/31.
 */
public class MyImageView extends View {
    private ArrayList<Drawable> drawables;
    private int width;
    private int height;
    private int rowCount = 4;
    private int itemWidth;

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        itemWidth = width / rowCount;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("TAG", "onDraw: " + drawables.size());
        if (drawables == null || drawables.size() == 0) {
            return;
        }
        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = 0;
        int y = Math.round(drawables.size() / rowCount);
        Drawable drawable;
        for (int x = 0; x < y; x++) {
            int count = Math.min(((x + 1) * rowCount), drawables.size()) % 4;
            count = (count == 0 ? 4 : count);
            for (int row = 0; row < count; row++) {
                left = getPaddingLeft() + itemWidth * row;
                right = left + itemWidth;
                top = getPaddingTop() + itemWidth * x;
                bottom = top + itemWidth;
                drawable = drawables.get(x * rowCount + row);
                drawable.setBounds(left, top, right, bottom);
                drawable.draw(canvas);
            }
        }

    }

    public void setDrawable(Drawable drawable) {
        if (drawables == null) {
            drawables = new ArrayList<>();
        }
        drawables.add(drawable);

        postInvalidate();
    }
}
