package com.alibaba.bigData.ibatisparser.utils;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-1-14
 * Time: 下午4:32
 * To change this template use File | Settings | File Templates.
 */

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;

/**
 * 内部函数
 */
public class NoOpEntityResolver implements EntityResolver {

    @Override
    public InputSource resolveEntity(String publicId, String systemId) {
        return new InputSource(new ByteArrayInputStream("".getBytes()));
    }

}