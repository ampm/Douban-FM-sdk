package com.zzxhdzj.http;

import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/30/13
 * Time: 12:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApiResponseTest {

    @Test
    public void isSuccess_shouldReturnTrueIfResponseCodeIsInThe200Range(){
        assertThat(new TestApiResponse(200).isSuccess(), equalTo(true));
        assertThat(new TestApiResponse(201).isSuccess(), equalTo(true));
        assertThat(new TestApiResponse(299).isSuccess(), equalTo(true));
    }

    @Test
    public void isSuccess_shouldReturnFalseIfResponseCodeIsIn500Range() throws Exception {
        assertThat(new TestApiResponse(500).isSuccess(), equalTo(false));
        assertThat(new TestApiResponse(501).isSuccess(), equalTo(false));
    }

    @Test
    public void isUnauthorized_shouldReturnTrueIfResponseCodeIs401() throws Exception {
        assertThat(new TestApiResponse(401).isUnauthorized(), equalTo(true));
    }
    private class TestApiResponse extends ApiResponse{
        public TestApiResponse(int responseCode) {
            super(responseCode);
        }

        @Override
        public void consumeResponse(InputStream responseBody) throws Exception {
        }
    }
}
