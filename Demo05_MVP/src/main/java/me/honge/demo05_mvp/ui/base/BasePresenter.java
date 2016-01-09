package me.honge.demo05_mvp.ui.base;

/**
 * Created by hong on 16/1/4.
 */
public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        mMvpView =  mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttach(){
        return mMvpView != null;
    }

    public T getMvpView(){
        return mMvpView;
    }

    public void checkViewAttach(){
        if (!isViewAttach()){
            new RuntimeException("操作数据之前必须先attachView");
        }
    }
}
