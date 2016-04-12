package me.honge.demo08_animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv).setOnClickListener(listener);
        findViewById(R.id.tv1).setOnClickListener(listener);
        findViewById(R.id.tv2).setOnClickListener(listener);
        findViewById(R.id.tv3).setOnClickListener(listener);
        findViewById(R.id.tv4).setOnClickListener(listener);
        findViewById(R.id.tv5).setOnClickListener(listener);
        findViewById(R.id.tv6).setOnClickListener(listener);
        findViewById(R.id.tv7).setOnClickListener(listener);

        ListView lv = (ListView) findViewById(R.id.lv);
//        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_layoutanimation_item);
//        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
//        layoutAnimationController.setDelay(0.5f);
//        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
//        lv.setLayoutAnimation(layoutAnimationController);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                new String[]{"1", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2"});
        lv.setAdapter(adapter);

//        Intent intent = new Intent(this,MainActivity.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);

//        ObjectAnimator.ofFloat(tv, "translationX", 100).start();
//        ValueAnimator colorAnim = ObjectAnimator.ofInt(tv,"backgroundColor",0xffff8080,0x8080ff);
//        colorAnim.setDuration(3000);
//        colorAnim.setEvaluator(new ArgbEvaluator());
//        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
//        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
//        colorAnim.start();

//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(
//                ObjectAnimator.ofFloat(tv, "translationX", 100),
//                ObjectAnimator.ofFloat(tv, "rotationX", 0, 180),
//                ObjectAnimator.ofFloat(tv, "scaleX", 1, 0.5f));
//        set.setDuration(5000).start();

//        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animator);
//        set.setTarget(tv);
//        set.setDuration(3000);
//        set.start();


    }

    private Animation animation;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv:
                    v.clearAnimation();
                    animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim);
                    v.startAnimation(animation);
                    break;
                case R.id.tv1:
                    v.clearAnimation();
                    animation = new Rotate3dAnimation(20, 90, 20, 20, 1, true);
                    animation.setDuration(2000);
                    v.startAnimation(animation);
                    break;
                case R.id.tv2:
                    v.clearAnimation();
                    v.setBackgroundResource(R.anim.animation_drawable);
                    AnimationDrawable animationDrawable = (AnimationDrawable) v.getBackground();
                    animationDrawable.start();
                    break;
                case R.id.tv3:
                    v.clearAnimation();
                    ViewWrapper wrapper = new ViewWrapper(v);
                    ObjectAnimator.ofInt(wrapper, "width", 500).setDuration(5000).start();
                    break;
                case R.id.tv4:
                    v.clearAnimation();
                    doAnimator(v, v.getWidth(), 500);
                    break;
                case R.id.tv5:
                    v.clearAnimation();
                    ObjectAnimator.ofFloat(v, "translationX", 100).start();
                    break;
                case R.id.tv6:
                    v.clearAnimation();
                    ValueAnimator colorAnim = ObjectAnimator.ofInt(v, "backgroundColor", 0xffff8080, 0x8080ff);
                    colorAnim.setDuration(3000);
                    colorAnim.setEvaluator(new ArgbEvaluator());
                    colorAnim.setRepeatCount(ValueAnimator.INFINITE);
                    colorAnim.setRepeatMode(ValueAnimator.REVERSE);
                    colorAnim.start();
                    break;
                case R.id.tv7:
                    v.clearAnimation();
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(
                            ObjectAnimator.ofFloat(v, "translationX", 100),
                            ObjectAnimator.ofFloat(v, "rotationX", 0, 180),
                            ObjectAnimator.ofFloat(v, "scaleX", 1, 0.5f));
                    set.setDuration(5000).start();
                    break;
            }
        }
    };

    private void doAnimator(final View target, final int start, final int to) {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator intEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                Log.e("TAG", "onAnimationUpdate: " + currentValue);
                float fraction = animation.getAnimatedFraction();
                target.getLayoutParams().width = intEvaluator.evaluate(fraction, start, to);
                target.requestLayout();
            }
        });
        animator.setDuration(5000).start();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }

    class ViewWrapper {
        private View mTarget;

        public ViewWrapper(View mTarget) {
            this.mTarget = mTarget;
        }

        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }
    }
}
