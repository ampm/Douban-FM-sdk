package com.zzxhdzj.http;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 * !!WARNING:"请勿添加业务相关代码"!!
 */
public interface ApiResponseCallbacks<T extends ApiResponse> {
    /**
     * http success:http resp code between 200 and 300 and Consume response body rightly.
     *
     * @param response
     * @throws Exception
     */
    public void onSuccess(T response) throws Exception;

    /**
     * 请求发送失败
     *
     * @param response
     */
    public void onRequestFailure(T response);


    public void onProcessFailure(T response);
    /**
     * callback过程发生异常，一般为调用方出错
     */
    public void onCallbackFailure(T response);
    public void onComplete();

    public void onStart();

    /**
     * 业务出错，不符合接口预期：自行约定错误码
     * @param response
     */
    public void onBizFailure(T response);
}
