package org.choviwu.top.qg.util;

import java.net.Proxy;

/**
 * @author chovi.wu
 * @Description
 * @create 2021-10-19 9:31
 */
public class ProxyThreadLocal {

    private static ThreadLocal<Proxy> proxyThreadLocal = new ThreadLocal<>();

    public static void set(Proxy proxy) {
        proxyThreadLocal.set(proxy);
    }

    public static Proxy get() {
        return proxyThreadLocal.get();
    }

    public static void clear(){
        proxyThreadLocal.remove();
    }
}
