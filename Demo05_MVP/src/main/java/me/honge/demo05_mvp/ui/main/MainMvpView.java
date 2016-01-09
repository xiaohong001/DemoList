package me.honge.demo05_mvp.ui.main;

import java.util.ArrayList;
import java.util.List;

import me.honge.demo05_mvp.data.model.GoodsResult;
import me.honge.demo05_mvp.ui.base.MvpView;

/**
 * Created by hong on 16/1/4.
 */
public interface MainMvpView extends MvpView {
    void showGoods();
    void showWelfare();
}
