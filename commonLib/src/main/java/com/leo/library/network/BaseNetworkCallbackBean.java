package com.leo.library.network;

/**
 * Created by leo on 2017/2/17.
 */

public class BaseNetworkCallbackBean<T> {
    private int http_code;
    private String http_msg;

    public int getHttp_code() {
        return http_code;
    }

    public void setHttp_code(int http_code) {
        this.http_code = http_code;
    }

    public String getHttp_msg() {
        return http_msg;
    }

    public void setHttp_msg(String http_msg) {
        this.http_msg = http_msg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    private T content;
}
