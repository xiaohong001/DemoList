package me.honge.demo05_mvp.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.honge.demo05_mvp.data.model.GoodsResult;
import me.honge.demo05_mvp.data.remote.APIService;
import rx.Observable;

/**
 * 所有获取数据的管理类
 * 单例模式
 */
@Singleton
public class DataManger {
    private final APIService apiService;

    @Inject
    public DataManger(APIService apiService) {
        this.apiService = apiService;
    }

    public Observable<GoodsResult> getGoods(String tag,int limit,int page){
        return apiService.getGoods(tag,limit,page);
    }
}
