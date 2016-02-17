package me.honge.demo08_animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by hong on 16/2/16.
 */
public class Rotate3dAnimation extends Animation {
    private final float mFromDegree;
    private final float mToDegree;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private Camera mCamera;

    public Rotate3dAnimation(float mFromDegree, float mToDegree, float mCenterX, float mCenterY, float mDepthZ, boolean mReverse) {
        this.mFromDegree = mFromDegree;
        this.mToDegree = mToDegree;
        this.mCenterX = mCenterX;
        this.mCenterY = mCenterY;
        this.mDepthZ = mDepthZ;
        this.mReverse = mReverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegree = mFromDegree;
        float degree = fromDegree + ((mToDegree - fromDegree)) * interpolatedTime;
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;
        final Matrix matrix = t.getMatrix();
        camera.save();
        if (mReverse) {
            camera.translate(0, 0, mDepthZ * interpolatedTime);
        } else {
            camera.translate(0, 0, mDepthZ * (1 - interpolatedTime));
        }
        camera.rotateY(degree);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
