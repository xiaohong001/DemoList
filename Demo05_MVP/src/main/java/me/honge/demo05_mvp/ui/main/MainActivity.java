package me.honge.demo05_mvp.ui.main;

import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import javax.inject.Inject;

import me.honge.demo05_mvp.R;
import me.honge.demo05_mvp.utils.commons.Constans;
import me.honge.demo05_mvp.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainPresenter mainPresenter;

    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView nvMenu;
    private FrameLayout flContent;
    private View goodsView;
    private View welfareView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_main);
        init();
        mainPresenter.attachView(this);
    }

    private void init() {
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, getToolbar(), 0, 0);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        nvMenu = (NavigationView) findViewById(R.id.nvMenu);
        nvMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mainPresenter.show(item.getItemId());
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        flContent = (FrameLayout) findViewById(R.id.flContent);
        showGoods();
    }

    @Override
    protected void onDestroy() {
        mainPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void showGoods() {
        flContent.removeAllViews();
        if (null == goodsView) {
            LayoutInflater inflater = LayoutInflater.from(this);
            goodsView = inflater.inflate(R.layout.layout_goods, flContent, false);
        }
        flContent.addView(goodsView);
        getToolbar().setTitle(Constans.STR_GANK);
    }

    @Override
    public void showWelfare() {
        flContent.removeAllViews();
        if (null == welfareView) {
            LayoutInflater inflater = LayoutInflater.from(this);
            welfareView = inflater.inflate(R.layout.layout_welfare, flContent, false);
        }
        flContent.addView(welfareView);
        getToolbar().setTitle(Constans.TAG_WEALFARE);
    }
}
