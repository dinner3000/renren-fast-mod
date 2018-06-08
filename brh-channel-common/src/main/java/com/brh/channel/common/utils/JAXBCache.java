/**
 * 版权所有：版权所有(C) 2014-2024
 * 公    司：北京帮付宝网络科技有限公司
 * JAXBContextHolder
 * 创建时间：2016/7/27 14:04
 * 作者：张波(工号：AB045179)
 * 功能描述：
 * 版本：1.0.0
 */
package com.brh.channel.common.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class JAXBCache {
    private static final JAXBCache instance = new JAXBCache();
    private final ConcurrentMap<String, JAXBContext> contextCache = new ConcurrentHashMap<>();

    public JAXBCache() {
    }

    public static JAXBCache instance() {
        return instance;
    }

    JAXBContext getJAXBContext(Class<?> clazz) throws JAXBException {
        JAXBContext context = contextCache.get(clazz.getName());
        if (context == null) {
            context = JAXBContext.newInstance(clazz);
            contextCache.putIfAbsent(clazz.getName(), context);
        }
        return context;
    }
}
