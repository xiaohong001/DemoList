package me.honge.demo05_mvp.ui.main;

import javax.inject.Inject;

import me.honge.demo05_mvp.R;
import me.honge.demo05_mvp.data.DataManger;
import me.honge.demo05_mvp.ui.base.BasePresenter;

/**
 * Created by hong on 16/1/4.
 */
public class MainPresenter extends BasePresenter<MainMvpView> {
    private final DataManger dataManger;
    private int lastSelect;

    @Inject
    public MainPresenter(DataManger dataManger) {
        this.dataManger = dataManger;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }


    public void show(int id) {
        if (lastSelect != id) {
            lastSelect = id;
            switch (id) {
                case R.id.menu_goods:
                    getMvpView().showGoods();
                    break;
                case R.id.menu_welfare:
                    getMvpView().showWelfare();
                    break;
            }
        }
    }

}
