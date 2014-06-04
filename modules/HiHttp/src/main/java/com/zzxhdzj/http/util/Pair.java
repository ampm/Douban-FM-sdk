package com.zzxhdzj.http.util;

public class Pair<Req, RespCallback> {
    public Req mReq;
    public RespCallback mRespCallback;

    public Pair(Req req, RespCallback respCallback) {
        this.mReq = req;
        this.mRespCallback = respCallback;
    }

    public static <Req, RespCallback>  Pair<Req, RespCallback>  of(Req req, RespCallback respCallback) {
        return new Pair<Req, RespCallback>(req, respCallback);
    }
}
