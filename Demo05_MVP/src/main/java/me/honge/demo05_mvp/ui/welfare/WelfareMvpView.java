package me.honge.demo05_mvp.ui.welfare;

import java.util.ArrayList;

import me.honge.demo05_mvp.data.model.GoodsModel;
import me.honge.demo05_mvp.ui.base.MvpView;

/**
 * Created by hong on 16/1/7.
 */
public interface WelfareMvpView extends MvpView {
    void setContent(ArrayList<GoodsModel> content, boolean needClear);
    void showErrorView();
    void setRefreshComplete();
}
