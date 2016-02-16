package me.honge.demo06_drawable;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View vLevel = findViewById(R.id.vLevel);
        vLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LevelListDrawable levelListDrawable = (LevelListDrawable) v.getBackground();
                levelListDrawable.setLevel(levelListDrawable.getLevel() == 0 ? 1 : 0);
            }
        });

        View vTransition = findViewById(R.id.vTransition);
        vTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionDrawable transitionDrawable = (TransitionDrawable) v.getBackground();
                transitionDrawable.startTransition(1000);
            }
        });

        View vScale = findViewById(R.id.vScale);
        vScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleDrawable scaleDrawable = (ScaleDrawable) v.getBackground();
                scaleDrawable.setLevel(1);
            }
        });

        View vClip = findViewById(R.id.vClip);
        vClip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipDrawable clipDrawable = (ClipDrawable) v.getBackground();
                clipDrawable.setLevel(4000);
            }
        });

        CustomerDrawable customerDrawable = new CustomerDrawable();
        View vCustomer = findViewById(R.id.vCustomer);
        vCustomer.setBackground(customerDrawable);
    }
}
