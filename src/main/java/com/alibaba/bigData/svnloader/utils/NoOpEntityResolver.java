package com.alibaba.bigData.svnloader.utils;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-1-14
 * Time: 下午4:32
 * DOM4J内部类
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