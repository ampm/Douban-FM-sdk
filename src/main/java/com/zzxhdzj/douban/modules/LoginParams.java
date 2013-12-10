package com.zzxhdzj.douban.modules;

import java.util.HashMap;

public class LoginParams {

    private String remember = "on";
    private String source = "radio";
    private String captcha;
    private String loginMail;
    private String password;
    private String captchaId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginParams that = (LoginParams) o;

        if (remember != null ? !remember.equals(that.remember) : that.remember != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (captcha != null ? !captcha.equals(that.remember) : that.captcha != null) return false;
        if (loginMail != null ? !loginMail.equals(that.loginMail) : that.loginMail != null) return false;
        return !(password != null ? !password.equals(that.password) : that.password != null);

    }

    @Override
    public int hashCode() {
        int result = remember != null ? remember.hashCode() : 0;
        result = 31 * result
                + (source != null ? source.hashCode() : 0)
                + (captcha != null ? captcha.hashCode() : 0)
                + (loginMail != null ? loginMail.hashCode() : 0)
                + (password != null ? password.hashCode() : 0);
        return result;
    }

    public HashMap<String, String> toParamsMap() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("remember", remember);
        params.put("source", source);
        params.put("captcha_solution", captcha);
        params.put("alias", loginMail);
        params.put("form_password", password);
        params.put("captcha_id", captchaId);
        return params;
    }

    public void setRemember(String remember) {
        this.remember = remember;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public void setLoginMail(String loginMail) {
        this.loginMail = loginMail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }
}