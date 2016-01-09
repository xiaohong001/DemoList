package me.honge.arcseekbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private ArcSeekBar arcSeekBar;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.arcSeekBar = (ArcSeekBar) findViewById(R.id.asB);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                i = i+1;
                if (i == 100){
                    removeMessages(0);
                    arcSeekBar.setProgress(i);
                    arcSeekBar.setPadding(0,0,0,0);
                }else {
                    arcSeekBar.setProgress(i);
                }

                this.sendEmptyMessageDelayed(0,100);
            }
        };
        handler.sendEmptyMessageDelayed(0,100);

        TextView tvText = (TextView) findViewById(R.id.tvText);
        tvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: " );
            }
        });
    }
}
