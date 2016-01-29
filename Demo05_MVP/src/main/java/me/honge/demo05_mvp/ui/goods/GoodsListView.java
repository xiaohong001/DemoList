package me.honge.demo05_mvp.ui.goods;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by hong on 16/1/6.
 */
public class GoodsListView extends FrameLayout implements GoodsMvpView, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView rvContent;
    private TextView tvTips;
    private SwipeRefreshLayout srlLayout;
    @Inject
    GoodsAdapter adapter;

    @Inject
    GoodsPresenter presenter;
    private boolean isFirst = true;

    private boolean isLoading = false;

    public GoodsListView(Context context) {
        super(context);
        ((BaseActivity) context).getComponent().inject(this);
    }

    public GoodsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((BaseActivity) context).getComponent().inject(this);
    }

    public GoodsListView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        adapter.setOnGoodsClickListener(new GoodsAdapter.OnGoodsClickListener() {
            @Override
            public void onClick(View view, GoodsModel goodsModel) {
                presenter.openWithBrowser(goodsModel);
            }
        });
        rvContent.setAdapter(adapter);
        rvContent.setOnScrollListener(listener);

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
        if (isFirst) {
//            presenter.loadMore();
            adapter.setShowFooter(false);
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
        adapter.setAndroidGoods(content, needClear);
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount() == 0){
            showTipsView("暂时没有数据哦~");
        }else {
            hideTipsView();
        }
    }

    @Override
    public void openWithBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        getContext().startActivity(intent);
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
