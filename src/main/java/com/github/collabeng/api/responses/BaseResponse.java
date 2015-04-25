package com.github.collabeng.api.responses;

import java.util.Date;

/**
 * Created by paul.smout on 20/02/2015.
 */
public class BaseResponse {
    private long serverTime = new Date().getTime();

    public long getServerTime() {
        return serverTime;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "serverTime=" + serverTime +
                '}';
    }
}
