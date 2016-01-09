package me.honge.demo05_mvp.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;

import me.honge.demo05_mvp.App;
import me.honge.demo05_mvp.R;
import me.honge.demo05_mvp.injection.component.ActivityComponent;
import me.honge.demo05_mvp.injection.component.DaggerActivityComponent;
import me.honge.demo05_mvp.injection.module.ActivityModule;
import me.honge.demo05_mvp.ui.main.MainActivity;

/**
 * Created by hong on 16/1/4.
 */
public class BaseActivity extends AppCompatActivity {
    private ActivityComponent mActivityComponent;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        KLog.e();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initToolBar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initToolBar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        initToolBar();
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.realToolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            if (this instanceof MainActivity) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public ActivityComponent getComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(App.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (this instanceof MainActivity) {

                } else {
                    // finish();
                    onBackPressed();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
