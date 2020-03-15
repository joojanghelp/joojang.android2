package com.joojang.bookfriend.api;

/**
 * Created by jh on 2017-12-07.
 */

public interface RetroCallback<T> {
    void onError(Throwable t);
    void onSuccess(int code, T receiveData);
}
