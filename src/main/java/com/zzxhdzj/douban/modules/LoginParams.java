package com.zzxhdzj.douban.modules;

public class LoginParams {

    private String remember;
    private String source;
    private String captcha;
    private String loginMail;
    private String password;

    private LoginParams(String remember, String source, String captcha, String loginMail, String password) {
        this.remember = remember;
        this.source = source;
        this.captcha = captcha;
        this.loginMail = loginMail;
        this.password = password;
    }

    public static LoginParams createLoginParams(String remember, String source, String captcha, String loginMail, String password) {
        return new LoginParams(remember, source, captcha, loginMail, password);
    }

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
}