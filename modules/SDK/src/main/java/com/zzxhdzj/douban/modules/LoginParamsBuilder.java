package com.zzxhdzj.douban.modules;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginParamsBuilder {
    private static LoginParams loginParams = new LoginParams();
    private LoginParamsBuilder() {
    }

    public static LoginParamsBuilder aLoginParams() {
        return new LoginParamsBuilder();
    }

    public LoginParamsBuilder withRemember(String remember) {
        loginParams.setRemember(remember);
        return this;
    }

    public LoginParamsBuilder withSource(String source) {
        loginParams.setSource(source);
        return this;
    }

    public LoginParamsBuilder withCaptcha(String captcha) {
        loginParams.setCaptcha(captcha);
        return this;
    }

    public LoginParamsBuilder withLoginMail(String loginMail) {
        loginParams.setLoginMail(loginMail);
        return this;
    }

    public LoginParamsBuilder withPassword(String password) {
        loginParams.setPassword(password);
        return this;
    }

    public LoginParamsBuilder withCaptchaId(String captchaId) {
        loginParams.setCaptchaId(captchaId);
        return this;
    }

    public LoginParams build() {
        return loginParams;
    }
}
