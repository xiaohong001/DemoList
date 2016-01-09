package me.honge.demo05_mvp.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.honge.demo05_mvp.data.model.GoodsResult;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by hong on 16/1/5.
 */
public interface APIService {
    String ROOT = "http://gank.avosapps.com/api/";

    @GET("data/Android/{limit}/{page}")
    Observable<GoodsResult> getAndroidGoods(@Path("limit") int limit, @Path("page") int page);

    @GET("data/iOS/{limit}/{page}")
    Observable<GoodsResult> getIOSGoods(@Path("limit") int limit, @Path("page") int page);

    @GET("data/all/{limit}/{page}")
    Observable<GoodsResult> getAllGoods(@Path("limit") int limit, @Path("page") int page);

    @GET("data/福利/{limit}/{page}")
    Observable<GoodsResult> getWelfareGoods(@Path("limit") int limit, @Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<GoodsResult> getDayGoods(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    @GET("data/{tag}/{limit}/{page}")
    Observable<GoodsResult> getGoods(@Path("tag") String tag, @Path("limit") int limit, @Path("page") int page);


    class Creator {
        public static APIService newAPIService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(APIService.class);
        }
    }

}
