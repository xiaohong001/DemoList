package me.honge.arcseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CircleSeekBar extends View {
	private final String TAG = "CircleSeekBar";

	private Context mContext = null;
	private int padding = 15;
	private final int STARTANGLE = 30;
	private final int SWEEPANGLE = 180 - 2 * STARTANGLE;
	private final int FINGERSIZE = 50;
	private double LIMITHEIGHT = 0;
	private int mOldVisableState = -1;
	private boolean mIsFirst = false;
	private Handler mHandler = new Handler();
	private boolean mNeedGetPosition = false;

	private Drawable mThumbDrawable = null;
	private int mThumbHeight = 0;
	private int mThumbWidth = 0;
	private int[] mThumbNormal = null;
	private int[] mThumbPressed = null;

	private int mSeekBarMax = 0;
	private Paint mSeekBarBackgroundPaint = null;
	private Paint mSeekbarProgressPaint = null;
	private RectF mArcRectF = null;

	private boolean mIsShowProgressText = false;
	private Paint mProgressTextPaint = null;
	private int mProgressTextSize = 0;

	private double mSeekBarSize = 0;
	private double mSeekBarRadius = 0;
	private double mSeekBarCenterX = 0;
	private double mSeekBarCenterY = 0;
	private double mThumbX = 0;
	private double mThumbY = 0;

	private float mSeekBarDegree = 0;
	private int mCurrentProgress = 0;

	private OnSeekBarChangeListener mOnSeekBarChangeListener = null;

	public interface OnSeekBarChangeListener {

		void onProgressChanged(int progress);

		void onStartTrackingTouch();

		void onStopTrackingTouch();
	}

	public CircleSeekBar(Context context) {
		this(context, null);
	}

	public CircleSeekBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initViewAttrs(attrs);
		initViewDefault();
		mArcRectF = new RectF();
		mIsFirst = true;
	}

	private void initViewAttrs(AttributeSet attrs) {
		TypedArray localTypedArray = mContext.obtainStyledAttributes(attrs,
				R.styleable.CircleSeekBar);

		mThumbDrawable = localTypedArray
				.getDrawable(R.styleable.CircleSeekBar_android_thumb);
		mThumbWidth = mThumbDrawable.getIntrinsicWidth();
		mThumbHeight = mThumbDrawable.getIntrinsicHeight();

		mThumbNormal = new int[] { -android.R.attr.state_focused,
				-android.R.attr.state_pressed, -android.R.attr.state_selected,
				-android.R.attr.state_checked };
		mThumbPressed = new int[] { android.R.attr.state_focused,
				android.R.attr.state_pressed, android.R.attr.state_selected,
				android.R.attr.state_checked };

		float progressWidth = localTypedArray.getDimension(
				R.styleable.CircleSeekBar_progress_width, 5);
		int progressBackgroundColor = localTypedArray.getColor(
				R.styleable.CircleSeekBar_progress_background, Color.GRAY);
		int progressFrontColor = localTypedArray.getColor(
				R.styleable.CircleSeekBar_progress_front, Color.BLUE);
		mSeekBarMax = localTypedArray.getInteger(
				R.styleable.CircleSeekBar_progress_max, 100);

		mSeekbarProgressPaint = new Paint();
		mSeekBarBackgroundPaint = new Paint();

		mSeekbarProgressPaint.setColor(progressFrontColor);
		mSeekBarBackgroundPaint.setColor(progressBackgroundColor);

		mSeekbarProgressPaint.setAntiAlias(true);
		mSeekBarBackgroundPaint.setAntiAlias(true);

		mSeekbarProgressPaint.setStyle(Paint.Style.STROKE);
		mSeekBarBackgroundPaint.setStyle(Paint.Style.STROKE);

		mSeekbarProgressPaint.setStrokeWidth(progressWidth);
		mSeekBarBackgroundPaint.setStrokeWidth(progressWidth);

		mIsShowProgressText = localTypedArray.getBoolean(
				R.styleable.CircleSeekBar_show_progress_text, false);
		int progressTextStroke = (int) localTypedArray.getDimension(
				R.styleable.CircleSeekBar_progress_text_stroke_width, 5);
		int progressTextColor = localTypedArray.getColor(
				R.styleable.CircleSeekBar_progress_text_color, Color.GREEN);
		mProgressTextSize = (int) localTypedArray.getDimension(
				R.styleable.CircleSeekBar_progress_text_size, 50);

		mProgressTextPaint = new Paint();
		mProgressTextPaint.setColor(progressTextColor);
		mProgressTextPaint.setAntiAlias(true);
		mProgressTextPaint.setStrokeWidth(progressTextStroke);
		mProgressTextPaint.setTextSize(mProgressTextSize);

		localTypedArray.recycle();
	}

	private void initViewDefault() {
		mThumbDrawable = null;
		mThumbWidth = 0;
		mThumbHeight = 0;

		mThumbNormal = new int[] { -android.R.attr.state_focused,
				-android.R.attr.state_pressed, -android.R.attr.state_selected,
				-android.R.attr.state_checked };
		mThumbPressed = new int[] { android.R.attr.state_focused,
				android.R.attr.state_pressed, android.R.attr.state_selected,
				android.R.attr.state_checked };

		float progressWidth = 5;
		int progressBackgroundColor = Color.GRAY;
		int progressFrontColor = Color.BLUE;
		mSeekBarMax = 100;

		mSeekbarProgressPaint = new Paint();
		mSeekBarBackgroundPaint = new Paint();

		mSeekbarProgressPaint.setColor(progressFrontColor);
		mSeekBarBackgroundPaint.setColor(progressBackgroundColor);

		mSeekbarProgressPaint.setAntiAlias(true);
		mSeekBarBackgroundPaint.setAntiAlias(true);

		mSeekbarProgressPaint.setStyle(Paint.Style.STROKE);
		mSeekBarBackgroundPaint.setStyle(Paint.Style.STROKE);

		mSeekbarProgressPaint.setStrokeWidth(progressWidth);
		mSeekBarBackgroundPaint.setStrokeWidth(progressWidth);

		mIsShowProgressText = false;
		int progressTextStroke = 5;
		int progressTextColor = Color.GREEN;
		mProgressTextSize = 50;

		mProgressTextPaint = new Paint();
		mProgressTextPaint.setColor(progressTextColor);
		mProgressTextPaint.setAntiAlias(true);
		mProgressTextPaint.setStrokeWidth(progressTextStroke);
		mProgressTextPaint.setTextSize(mProgressTextSize);
	}

	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		if (mIsFirst && View.VISIBLE == visibility
				&& View.VISIBLE == mOldVisableState) {
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					requestLayout();
					postInvalidate();
				}
			}, 500);
			mIsFirst = false;
		}
		mOldVisableState = visibility;
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		mSeekBarSize = getWidth() > getHeight() ? getHeight() : getWidth();

		mSeekBarCenterX = getWidth() / 2;
		mSeekBarCenterY = getHeight() / 2;

		mSeekBarRadius = mSeekBarSize / 2 - padding;

		int left = (int) (mSeekBarCenterX - mSeekBarRadius);
		int right = (int) (mSeekBarCenterX + mSeekBarRadius);
		int top = (int) (mSeekBarCenterY - mSeekBarRadius);
		int bottom = (int) (mSeekBarCenterY + mSeekBarRadius);

		mArcRectF.set(left, top, right, bottom);

		LIMITHEIGHT = Math.sin((STARTANGLE * Math.PI / 180)) * mSeekBarRadius
				+ getHeight() / 2 - FINGERSIZE;
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		if (mNeedGetPosition) {
			if (!setThumbPosition(Math.toRadians(STARTANGLE + mSeekBarDegree))) {
				super.onDraw(canvas);
				return;
			}
			mNeedGetPosition = false;
		}
		canvas.drawArc(mArcRectF, STARTANGLE, SWEEPANGLE, false,
				mSeekBarBackgroundPaint);
		canvas.drawArc(mArcRectF, STARTANGLE, mSeekBarDegree, false,
				mSeekbarProgressPaint);
		drawThumbBitmap(canvas);
		drawProgressText(canvas);

		super.onDraw(canvas);
	}

	private void drawThumbBitmap(Canvas canvas) {
		if (null != mThumbDrawable) {
			int left = (int) (mThumbX - mThumbWidth / 2);
			int right = left + mThumbWidth;
			int top = (int) (mThumbY - mThumbHeight / 2);
			int bottom = top + mThumbHeight;
			mThumbDrawable.setBounds(left, top, right, bottom);

			mThumbDrawable.draw(canvas);
		}
	}

	private void drawProgressText(Canvas canvas) {
		if (true == mIsShowProgressText) {
			float textWidth = mProgressTextPaint.measureText(""
					+ mCurrentProgress);
			canvas.drawText("" + mCurrentProgress, (float) mSeekBarCenterX
					- textWidth / 2,
					(float) (mSeekBarCenterY + mSeekBarRadius / 2),
					mProgressTextPaint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float eventX = event.getX();
		float eventY = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (null != mOnSeekBarChangeListener) {
				mOnSeekBarChangeListener.onStartTrackingTouch();
			}
			seekTo(eventX, eventY, false);
			break;

		case MotionEvent.ACTION_MOVE:
			seekTo(eventX, eventY, false);
			break;

		case MotionEvent.ACTION_UP:
			if (null != mOnSeekBarChangeListener) {
				mOnSeekBarChangeListener.onStopTrackingTouch();
			}
			seekTo(eventX, eventY, true);
			break;
		}
		return true;
	}

	private void seekTo(float eventX, float eventY, boolean isUp) {
		if (true == isPointOnThumb(eventX, eventY) && false == isUp) {
			if (null != mThumbDrawable) {
				mThumbDrawable.setState(mThumbPressed);
			}

			double radian = Math.atan2(eventY - mSeekBarCenterY, eventX
					- mSeekBarCenterX);
			double startRadians = Math.toRadians(STARTANGLE);
			double endRadians = Math.toRadians(STARTANGLE + SWEEPANGLE);
			if (radian < startRadians) {
				radian = startRadians;
			} else if (radian > endRadians) {
				radian = endRadians;
			}
			setThumbPosition(radian);

			mSeekBarDegree = (float) Math.round(Math.toDegrees(radian));
			mSeekBarDegree -= STARTANGLE;
			if (mSeekBarDegree < 0) {
				mSeekBarDegree = 0;
			} else if (mSeekBarDegree > SWEEPANGLE) {
				mSeekBarDegree = SWEEPANGLE;
			}
			mCurrentProgress = mSeekBarMax
					- (int) (mSeekBarMax * mSeekBarDegree / SWEEPANGLE);
			if (null != mOnSeekBarChangeListener) {
				mOnSeekBarChangeListener.onProgressChanged(mCurrentProgress);
			}
			invalidate();
		} else {
			if (null != mThumbDrawable) {
				mThumbDrawable.setState(mThumbNormal);
			}
			invalidate();
		}
	}

	private boolean isPointOnThumb(float eventX, float eventY) {
		if (eventY < LIMITHEIGHT) {
			return false;
		}
		double distance = Math.sqrt(Math.pow(eventX - mSeekBarCenterX, 2)
				+ Math.pow(eventY - mSeekBarCenterY, 2));
		if (distance < mSeekBarRadius + FINGERSIZE
				&& distance > mSeekBarRadius - FINGERSIZE) {
			return true;
		}
		return false;
	}

	private boolean setThumbPosition(double radian) {
		mThumbX = mSeekBarCenterX + mSeekBarRadius * Math.cos(radian);
		mThumbY = mSeekBarCenterY + mSeekBarRadius * Math.sin(radian);
		if (0 == mSeekBarCenterX || 0 == mSeekBarCenterY) {
			return false;
		}
		return true;
	}

	public synchronized void setProgress(int progress) {
		if (0 == mSeekBarMax) {
			return;
		}
		if (progress > mSeekBarMax) {
			progress = mSeekBarMax;
		}
		if (progress < 0) {
			progress = 0;
		}
		mCurrentProgress = progress;
		mSeekBarDegree = SWEEPANGLE * (mSeekBarMax - progress) / mSeekBarMax;
		mNeedGetPosition = true;
	}

	public synchronized int getProgress() {
		return mCurrentProgress;
	}

	public synchronized void setProgressMax(int max) {
		mSeekBarMax = max;
	}

	public synchronized int getProgressMax() {
		return mSeekBarMax;
	}

	public void setProgressThumb(int thumbId) {
		mThumbDrawable = mContext.getResources().getDrawable(thumbId);
		mThumbWidth = mThumbDrawable.getIntrinsicWidth();
		mThumbHeight = mThumbDrawable.getIntrinsicHeight();
	}

	public void setProgressWidth(int width) {
		mSeekbarProgressPaint.setStrokeWidth(width);
		mSeekBarBackgroundPaint.setStrokeWidth(width);
	}

	public void setProgressBackgroundColor(int color) {
		mSeekBarBackgroundPaint.setColor(color);
	}

	public void setProgressFrontColor(int color) {
		mSeekbarProgressPaint.setColor(color);
	}

	public void setProgressTextColor(int color) {
		mProgressTextPaint.setColor(color);
	}

	public void setProgressTextSize(int size) {
		mProgressTextPaint.setTextSize(size);
	}

	public void setProgressTextStrokeWidth(int width) {
		mProgressTextPaint.setStrokeWidth(width);
	}

	public void setIsShowProgressText(boolean isShow) {
		mIsShowProgressText = isShow;
	}

	public void setOnSeekBarChangeListener(
			OnSeekBarChangeListener onSeekBarChangeListener) {
		mOnSeekBarChangeListener = onSeekBarChangeListener;
	}
}