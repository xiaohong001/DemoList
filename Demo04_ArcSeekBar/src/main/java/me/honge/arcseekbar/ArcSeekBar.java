package me.honge.arcseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by honge on 15/12/15.
 */
public class ArcSeekBar extends View {
    private int realWidth;// 宽
    private int realHeight;// 高
    private int centerX;
    private int centerY;
    private RectF arcRect;
    private int radius;// 半径
    private Paint bgPaint;//
    private Paint mPaint;//
    private float bgStartAngle;
    private float bgSweepAngle = 100f;
    private float mStartAngle;
    private float mSweepAngle;
    private int strokeWide = 4;
    private int max = 100;
    private int progress = 0;
    private int minSweepAngle = 2;

    /**
     * 显示模式，显示在右边
     */
    public static final int GRAVITY_RIGHT = 1;
    /**
     * 显示模式，显示在下边
     */
    public static final int GRAVITY_BOTTOM = 2;

    private int gravity = GRAVITY_BOTTOM;

    /**
     * 色块填充模式，经过的全部填充
     */
    public static final int FILL_TYPE_ABSOLUTE = 1;
    /**
     * 色块填充模式，相对的一小段
     */
    public static final int FILL_TYPE_RELATIVE = 2;

    private int fillType = FILL_TYPE_ABSOLUTE;
    private int bgColor = Color.parseColor("#BF595558");
    private int progressColor = Color.parseColor("#ff9407");

    private Drawable mThumbDrawable;
    private int mThumbHeight = 0;
    private int mThumbWidth = 0;
    private int[] mThumbNormal = null;
    private int[] mThumbPressed = null;
    private double thumbX = -1;
    private double thumbY = -1;


    private OnSeekBarChangeListener mOnSeekBarChangeListener = null;

    public interface OnSeekBarChangeListener {

        void onProgressChanged(int progress);

        void onStartTrackingTouch();

        void onStopTrackingTouch();
    }

    public ArcSeekBar(Context context) {
        super(context);
        init(context, null);
    }

    public ArcSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ArcSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.ArcSeekBar);
            mThumbDrawable = a.getDrawable(R.styleable.ArcSeekBar_progress_thumb);
            mThumbWidth = mThumbDrawable.getIntrinsicWidth();
            mThumbHeight = mThumbDrawable.getIntrinsicHeight();
            mThumbNormal = new int[]{-android.R.attr.state_focused,
                    -android.R.attr.state_pressed, -android.R.attr.state_selected,
                    -android.R.attr.state_checked};
            mThumbPressed = new int[]{android.R.attr.state_focused,
                    android.R.attr.state_pressed, android.R.attr.state_selected,
                    android.R.attr.state_checked};
            bgColor = a.getColor(R.styleable.ArcSeekBar_bgColor, bgColor);
            progressColor = a.getColor(R.styleable.ArcSeekBar_progressColor, progressColor);
            strokeWide = a.getInteger(R.styleable.ArcSeekBar_strokeWide, strokeWide);
            fillType = a.getInt(R.styleable.ArcSeekBar_fillType, fillType);
            max = a.getInt(R.styleable.ArcSeekBar_max, max);
            progress = a.getInt(R.styleable.ArcSeekBar_progress, progress);
            gravity = a.getInt(R.styleable.ArcSeekBar_gravity, gravity);
            bgSweepAngle = a.getFloat(R.styleable.ArcSeekBar_sweepAngle, gravity == GRAVITY_RIGHT ? 60f : 100f);

            a.recycle();
        }


        bgPaint = new Paint();
        bgPaint.setColor(bgColor);
        bgPaint.setStrokeWidth(strokeWide);
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeJoin(Paint.Join.ROUND);
        bgPaint.setStrokeCap(Paint.Cap.ROUND); //设置圆角

        mPaint = new Paint();
        mPaint.setColor(progressColor);
        mPaint.setStrokeWidth(strokeWide + 1);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND); //设置圆角

        arcRect = new RectF();

        if (gravity == GRAVITY_RIGHT) {
            bgStartAngle = 90 - bgSweepAngle / 2;
        } else if (gravity == GRAVITY_BOTTOM) {
            bgStartAngle = 180 - bgSweepAngle / 2;
        }
        compute();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        realWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        realHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        radius = ((realWidth < realHeight ? realWidth : realHeight) - 1 * strokeWide) / 2;

        centerX = realWidth / 2;
        centerY = realHeight / 2;

        arcRect.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(arcRect, bgStartAngle - 90, bgSweepAngle, false, bgPaint);

        canvas.drawArc(arcRect, mStartAngle - 90, mSweepAngle, false, mPaint);

        drawThumb(canvas);


    }

    private void drawThumb(Canvas canvas) {
        if (mThumbDrawable != null) {
            float endAngle = mStartAngle + mSweepAngle - 90;
            thumbX = centerX + radius * Math.cos(endAngle * 3.14 / 180);
            thumbY = centerY + radius * Math.sin(endAngle * 3.14 / 180);
//            canvas.drawCircle((float) x1, (float) y1, 10, bgPaint);
            mThumbDrawable.setBounds((int) (thumbX - mThumbWidth / 2), (int) (thumbY - mThumbHeight / 2), (int) (thumbX + mThumbWidth / 2), (int) (thumbY + mThumbHeight / 2));
            canvas.save();
            canvas.rotate(endAngle - 90, (float) thumbX, (float) thumbY);
            mThumbDrawable.draw(canvas);
            canvas.restore();
        }
    }

    private boolean onTouchThumb(float x, float y) {
        if (mThumbDrawable != null && mThumbDrawable.getBounds().contains(Math.round(x), Math.round(y))) {
            return true;
        }
        return false;
    }

//    boolean isTouch = false;
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if (onTouchThumb(event.getX(), event.getY())) {
//                    isTouch = true;
//                    if (mOnSeekBarChangeListener!=null){
//                        mOnSeekBarChangeListener.onStartTrackingTouch();
//                    }
//                    return true;
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (isTouch){
//                    seekto(event.getX(),event.getY());
//                    return true;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                if (isTouch){
//                    isTouch = false;
//                    if (mOnSeekBarChangeListener!=null){
//                        mOnSeekBarChangeListener.onStopTrackingTouch();
//                    }
//                }
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
//
//    private  void  seekto(float x, float y){
//        double radian = Math.atan2(y - centerY, x
//                - centerX);
//        double startRadians = Math.toRadians(bgStartAngle);
//        double endRadians = Math.toRadians(bgStartAngle + bgSweepAngle);
//        if (radian < startRadians) {
//            radian = startRadians;
//        } else if (radian > endRadians) {
//            radian = endRadians;
//        }
//
//        mSweepAngle = Math.round(Math.toDegrees(radian));
//
//    }


    public void changeGravity(int gravity) {
        if (this.gravity == gravity) {
            return;
        }
        if (gravity == GRAVITY_RIGHT) {
            bgStartAngle = 90 - bgSweepAngle / 2;
            this.gravity = gravity;
        } else if (gravity == GRAVITY_BOTTOM) {
            bgStartAngle = 180 - bgSweepAngle / 2;
            this.gravity = gravity;
        } else {
            return;
        }

        compute();
        invalidate();
    }

    public void changeFillType(int fillType) {
        if (this.fillType == fillType) {
            return;
        }
        if (fillType == FILL_TYPE_ABSOLUTE) {
            this.fillType = fillType;
        } else if (fillType == FILL_TYPE_RELATIVE) {
            this.fillType = fillType;
        } else {
            return;
        }
        compute();
        invalidate();
    }


    /**
     * 计算显示的位置
     */
    private void compute() {

        //显示在 右边的时候是从上往下
        //显示在下边的时候是从左往右
        if (gravity == GRAVITY_RIGHT) {
            if (fillType == FILL_TYPE_ABSOLUTE) {
                mStartAngle = bgStartAngle;
                mSweepAngle = (progress / (float) max) * bgSweepAngle;
            } else if (fillType == FILL_TYPE_RELATIVE) {
                mStartAngle = (progress / (float) max) * bgSweepAngle + bgStartAngle;
                float tmp = (1 / (float) max) * bgSweepAngle;
                mSweepAngle = tmp < minSweepAngle ? minSweepAngle : tmp;
                tmp = (mStartAngle - mSweepAngle);
                mStartAngle = tmp > bgStartAngle ? tmp : bgStartAngle;
                mStartAngle = tmp < (bgStartAngle + bgSweepAngle - mSweepAngle) ? tmp
                        : (bgStartAngle + bgSweepAngle - mSweepAngle);
            }
        } else if (gravity == GRAVITY_BOTTOM) {

            if (fillType == FILL_TYPE_ABSOLUTE) {
                mStartAngle = bgStartAngle + bgSweepAngle;
                mSweepAngle = -(progress / (float) max) * bgSweepAngle;
            } else if (fillType == FILL_TYPE_RELATIVE) {
                mStartAngle = -(progress / (float) max) * bgSweepAngle + bgStartAngle + bgSweepAngle;
                float tmp = (1 / (float) max) * bgSweepAngle;
                mSweepAngle = tmp < minSweepAngle ? minSweepAngle : tmp;
                tmp = mStartAngle;
                mStartAngle = tmp > bgStartAngle ? tmp : bgStartAngle;
                mStartAngle = tmp < (bgStartAngle + bgSweepAngle + mSweepAngle) ? tmp
                        : (bgStartAngle + bgSweepAngle + mSweepAngle);
            }

        }

    }


    public void setMax(int max) {
        this.max = max;
        this.progress = 0;
    }

    public int getMax() {
        return max;
    }

    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > max) {
            progress = max;
        }
        if (this.progress != progress) {
            this.progress = progress;
            compute();
            invalidate();
        }

    }

    public int getProgress() {
        return progress;
    }
}
