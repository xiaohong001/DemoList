package me.honge.demo05_mvp.ui.welfare;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.boycy815.pinchimageview.PinchImageView;
import com.bumptech.glide.Glide;
import com.socks.library.KLog;

import me.honge.demo05_mvp.R;
import me.honge.demo05_mvp.data.model.GoodsModel;
import me.honge.demo05_mvp.ui.base.BaseActivity;

/**
 * Created by hong on 16/1/7.
 */
public class WelfareDetailActivity extends BaseActivity {
    PinchImageView ivImage;
    public static final String WELFARE_DETAIL_KEY = "welfare_detail_key";
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare_detail);
        ivImage = (PinchImageView) findViewById(R.id.ivImage);

        url = getIntent().getStringExtra(WELFARE_DETAIL_KEY);
        KLog.e(url);
        Glide.with(this).load(url).into(ivImage);
    }
}
