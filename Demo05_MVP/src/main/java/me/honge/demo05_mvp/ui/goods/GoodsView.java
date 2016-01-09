package me.honge.demo05_mvp.ui.goods;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.socks.library.KLog;

import java.util.ArrayList;

import me.honge.demo05_mvp.R;
import me.honge.demo05_mvp.utils.commons.Constans;

/**
 * Created by hong on 16/1/6.
 */
public class GoodsView extends FrameLayout {
    TabLayout tabLayout;
    ViewPager viewPager;
    MyAdapter adapter;

    public GoodsView(Context context) {
        super(context);
    }

    public GoodsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GoodsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        KLog.e();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.addTab(tabLayout.newTab().setText(Constans.TAG_ANDROID));
        tabLayout.addTab(tabLayout.newTab().setText(Constans.TAG_IOS));
        adapter = new MyAdapter();
        GoodsListView AndroidGoods = (GoodsListView) inflater.inflate(R.layout.layout_goods_list, null);
        adapter.addView(AndroidGoods, Constans.TAG_ANDROID);
        AndroidGoods.presenter.setTag(Constans.TAG_ANDROID);
        GoodsListView iOSGoods = (GoodsListView) inflater.inflate(R.layout.layout_goods_list, null);
        iOSGoods.presenter.setTag(Constans.TAG_IOS);
        adapter.addView(iOSGoods, Constans.TAG_IOS);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static class MyAdapter extends PagerAdapter {
        ArrayList<View> views = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        public void addView(View view, String title) {
            views.add(view);
            titles.add(title);
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            container.removeViewAt(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
