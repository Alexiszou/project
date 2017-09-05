package com.elftree.mall.utils;

import com.orhanobut.logger.Logger;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zouhongzhi on 2017/8/28.
 */
public class MD5UtilTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void str2MD5Offset() throws Exception {
        int j=0;
        for(int i=0;i<10;i++){
            j = i+1;
        }
        String test = "test";
        String str = MD5Util.str2MD5Offset(test);
        Logger.d(str);
        assertNotNull("test",str);

    }

}