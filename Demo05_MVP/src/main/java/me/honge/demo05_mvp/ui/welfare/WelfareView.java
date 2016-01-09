package me.honge.demo05_mvp.ui.welfare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.socks.library.KLog;

import java.util.ArrayList;

import javax.inject.Inject;

import me.honge.demo05_mvp.R;
import me.honge.demo05_mvp.data.model.GoodsModel;
import me.honge.demo05_mvp.ui.base.BaseActivity;
import me.honge.demo05_mvp.ui.base.MvpView;
import me.honge.demo05_mvp.ui.goods.GoodsAdapter;

/**
 * Created by hong on 16/1/6.
 */
public class WelfareView extends FrameLayout implements WelfareMvpView, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView rvContent;
    private SwipeRefreshLayout srlLayout;
    @Inject
    WelfareAdapter adapter;

    @Inject
    WelfarePresenter presenter;
    private boolean isFirst = true;

    public WelfareView(Context context) {
        super(context);
        ((BaseActivity) context).getComponent().inject(this);
    }

    public WelfareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((BaseActivity) context).getComponent().inject(this);
    }

    public WelfareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ((BaseActivity) context).getComponent().inject(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        srlLayout = (SwipeRefreshLayout) findViewById(R.id.srlLayout);
        srlLayout.setOnRefreshListener(this);

        rvContent = (RecyclerView) findViewById(R.id.rvContent);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContent.setLayoutManager(layoutManager);
        rvContent.setHasFixedSize(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            rvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        adapter.setOnGoodsClickListener(new WelfareAdapter.OnGoodsClickListener() {
            @Override
            public void onClick(View view, GoodsModel goodsModel) {
                showImage(goodsModel);
                KLog.e(goodsModel.url);
            }
        });
        rvContent.setAdapter(adapter);
        rvContent.setOnScrollListener(listener);

    }

    private void showImage(GoodsModel goodsModel) {
        Intent intent = new Intent(getContext(),WelfareDetailActivity.class);
        intent.putExtra(WelfareDetailActivity.WELFARE_DETAIL_KEY, goodsModel.url);
        getContext().startActivity(intent);
    }

    private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapter.getItemCount()
                    && adapter.isShowFooter()) {
                presenter.loadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            KLog.e(lastVisibleItem);
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.attachView(this);
        if (isFirst) {
            onRefresh();
            isFirst = false;
        }
    }

    @Override
    public void onRefresh() {
        srlLayout.setRefreshing(true);
        presenter.refresh();
    }

    @Override
    public void setRefreshComplete() {
        srlLayout.setRefreshing(false);
    }

    @Override
    public void setContent(ArrayList<GoodsModel> content, boolean needClear) {
        adapter.setShowFooter(true);
        adapter.setWelfares(content, needClear);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorView() {
        adapter.setShowFooter(false);
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.detachView();
        KLog.e("onDetachedFromWindow");
        super.onDetachedFromWindow();
    }


}
