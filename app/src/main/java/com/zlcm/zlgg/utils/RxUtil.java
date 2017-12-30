package com.zlcm.zlgg.utils;

import com.zlcm.zlgg.model.http.exception.SysException;
import com.zlcm.zlgg.model.http.response.ZLResponse;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/8/19 0019.
 * Rx帮助类，统一返回结果集
 */

public class RxUtil {

    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper(){
        return new FlowableTransformer<T,T>(){

            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<ZLResponse<T>,T> handleZL(){
        return new FlowableTransformer<ZLResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<ZLResponse<T>> upstream) {
                return upstream.flatMap(new Function<ZLResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(@NonNull ZLResponse<T> tWeatherResponse) throws Exception {
                        if (tWeatherResponse.getCode() == 200){
                            return createData(tWeatherResponse.getData());
                        }else {
                            return Flowable.error(new SysException(tWeatherResponse.getCode()));
                        }
                    }
                });
            }
        };
    }

    public static FlowableTransformer<ZLResponse,ZLResponse> handleZLState(){
        return new FlowableTransformer<ZLResponse, ZLResponse>() {
            @Override
            public Flowable apply(Flowable<ZLResponse> upstream) {
                return upstream.flatMap(new Function<ZLResponse, Flowable<ZLResponse>>() {
                    @Override
                    public Flowable<ZLResponse> apply(@NonNull final ZLResponse zlResponse) throws Exception {
                        if (zlResponse.getCode() == 200){
                            return createData(zlResponse);
                        }else {
                            return Flowable.error(new SysException(zlResponse.getCode()));
                        }
                    }
                });
            }
        };

    }


    /**
     * 生成Flowable
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
