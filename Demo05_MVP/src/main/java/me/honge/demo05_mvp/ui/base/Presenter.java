package me.honge.demo05_mvp.ui.base;

/**
 * Created by hong on 16/1/4.
 */
public interface Presenter<V extends  MvpView> {
    void attachView(V mvpView);
    void detachView();
}
