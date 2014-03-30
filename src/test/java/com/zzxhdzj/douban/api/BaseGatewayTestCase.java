package com.zzxhdzj.douban.api;

import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.mock.TestApiGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 1:18 AM
 * To change this template use File | Settings | File Templates.
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public abstract class BaseGatewayTestCase {
    protected TestApiGateway apiGateway;
    protected Douban douban;
    public Callback badCallback;

    @Before
    public void setUp() {
        apiGateway = new TestApiGateway();
        douban = new Douban(Robolectric.application.getApplicationContext());
        Constants.UNIT_TEST =true;
        badCallback = new Callback() {
            @Override
            public void onSuccess() {
                throw new RuntimeException();
            }
        };
    }

}
