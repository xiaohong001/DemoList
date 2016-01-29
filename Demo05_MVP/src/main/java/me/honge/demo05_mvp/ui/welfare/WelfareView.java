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
import android.widget.TextView;

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

    private boolean isLoading = false;
    private TextView tvTips;

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

        tvTips = (TextView) findViewById(R.id.tvTips);
        tvTips.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setShowFooter(false);
                post(new Runnable() {
                    @Override
                    public void run() {
                        isLoading = true;
                        srlLayout.setRefreshing(true);
                        presenter.refresh();
                    }
                });
            }
        });

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
                    && adapter.isShowFooter()&&!isLoading) {
                presenter.loadMore();
                isLoading = true;
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
        adapter.setShowFooter(false);
        if (isFirst) {
            post(new Runnable() {
                @Override
                public void run() {
                    isLoading = true;
                    srlLayout.setRefreshing(true);
                    presenter.refresh();
                }
            });
            isFirst = false;
        }
    }

    @Override
    public void onRefresh() {
        if (!isLoading) {
            srlLayout.setRefreshing(true);
            presenter.refresh();
            isLoading = true;
        }
    }

    @Override
    public void setRefreshComplete() {
        isLoading = false;
        srlLayout.setRefreshing(false);
    }

    @Override
    public void setContent(ArrayList<GoodsModel> content, boolean needClear) {
        isLoading = false;
        adapter.setShowFooter(true);
        adapter.setWelfares(content, needClear);
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount() == 0){
            showTipsView("暂时没有数据哦~");
        }else {
            hideTipsView();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        isLoading = false;
        presenter.detachView();
        KLog.e("onDetachedFromWindow");
        super.onDetachedFromWindow();
    }

    @Override
    public void showTipsView(String tip) {
        tvTips.setVisibility(VISIBLE);
        rvContent.setVisibility(GONE);
        tvTips.setText(tip);
        isLoading = false;
        adapter.setShowFooter(false);
    }

    @Override
    public void hideTipsView() {
        tvTips.setVisibility(GONE);
        rvContent.setVisibility(VISIBLE);
    }

}
