package com.zzxhdzj.douban.api;

import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.http.mock.TestApiGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 1:18 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)
public class BaseGatewayTestCase {
    protected TestApiGateway apiGateway;
    protected Douban douban;

    @Before
    public void setUp() {
        apiGateway = new TestApiGateway();
        douban = new Douban();
    }
    @Test
    public void test(){

    }
}
