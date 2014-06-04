package com.zzxhdzj.douban.modules;

import com.google.gson.annotations.SerializedName;
import com.zzxhdzj.douban.api.RespType;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 1:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class Resp {
    public boolean status;
    public int r;
    private String msg;
    @SerializedName("err_msg")
    private String errMsg;
    public String warning;
    public String getMessage(RespType respType){
        if(respType.equals(RespType.R)){
            return errMsg;
        }else  return msg;
    }
    public String getCode(RespType respType){
        if(respType.equals(RespType.R)){
            return r+"";
        }else return status?"1":"0";
    }

    public void setMsg(RespType respType,String msg) {
        if(respType.equals(RespType.R)){
            this.errMsg = msg;
        }else  this.msg = msg;
    }
}
