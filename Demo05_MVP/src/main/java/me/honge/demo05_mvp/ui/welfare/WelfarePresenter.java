package me.honge.demo05_mvp.ui.welfare;

import com.socks.library.KLog;

import java.util.ArrayList;

import javax.inject.Inject;

import me.honge.demo05_mvp.data.DataManger;
import me.honge.demo05_mvp.utils.commons.Constans;
import me.honge.demo05_mvp.data.model.GoodsModel;
import me.honge.demo05_mvp.data.model.GoodsResult;
import me.honge.demo05_mvp.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hong on 16/1/6.
 */
public class WelfarePresenter extends BasePresenter<WelfareView> {
    private final DataManger dataManger;
    private int index = 1;
    private int limit = 10;
    private Subscription subscription;
    private String tag = Constans.TAG_WEALFARE;

    @Inject
    public WelfarePresenter(DataManger dataManger) {
        this.dataManger = dataManger;
    }

    public void loadMore() {
        index++;
        getGoods(false);
    }

    public void refresh() {
        index = 1;
        getGoods(true);
    }

    private void getGoods(final boolean isRefresh) {
        KLog.e();
        checkViewAttach();
        if (null != subscription && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = dataManger.getGoods(tag, limit, index)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<GoodsResult, ArrayList<GoodsModel>>() {
                    @Override
                    public ArrayList<GoodsModel> call(GoodsResult goodsResult) {
                        return goodsResult.results;
                    }
                })
                .subscribe(new Subscriber<ArrayList<GoodsModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e();
                        getMvpView().showErrorView();
                    }

                    @Override
                    public void onNext(ArrayList<GoodsModel> goodsModels) {
                        KLog.e();
                        if (isRefresh) {
                            getMvpView().setContent(goodsModels, true);
                            getMvpView().setRefreshComplete();
                        } else {
                            getMvpView().setContent(goodsModels, false);
                        }

                    }
                })
        ;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (null != subscription) {
            subscription.unsubscribe();
        }
    }
}
