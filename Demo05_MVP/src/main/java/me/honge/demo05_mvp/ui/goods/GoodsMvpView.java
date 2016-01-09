package me.honge.demo05_mvp.ui.goods;

import java.util.ArrayList;

import me.honge.demo05_mvp.data.model.GoodsModel;
import me.honge.demo05_mvp.ui.base.MvpView;

/**
 * Created by hong on 16/1/7.
 */
public interface GoodsMvpView extends MvpView{
    void setRefreshComplete();
    void setContent(ArrayList<GoodsModel> content, boolean needClear);
    void showErrorView();
    void openWithBrowser(String url);
}
