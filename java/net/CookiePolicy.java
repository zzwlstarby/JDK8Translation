/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.net;

/**
 * CookiePolicy implementations decide which cookies should be accepted
 * and which should be rejected. Three pre-defined policy implementations
 * are provided, namely ACCEPT_ALL, ACCEPT_NONE and ACCEPT_ORIGINAL_SERVER.
 *
 * <p>See RFC 2965 sec. 3.3 and 7 for more detail.
 *
 * @author Edward Wang
 * @since 1.6
 */

/**
 * 1.完成CookiePolicy接口的类可以选择哪些cookie可以被接收、哪些cookie应该被拒绝
 * 2.完成该接口的类提供了三种预先设置的策略状态：ACCEPT_ALL，ACCEPT_NONE和ACCEPT_ORIGINAL_SERVER
 *
 * 3.该类是应用在用户端应对服务端请求设置cookie事件的应对政策。对客户端接收到服务端设置cookie的
 * 请求时需要通过该类对该请求进行回应判定，可根据不同的策略可以限制主机对客户端的信息追踪（不能完全限制），
 * 同时对信息安全有一些帮助（其他站点无法恶意访问cookie，因为你根本就没设……角度清奇2333）。
 */
public interface CookiePolicy {
    /**
     * One pre-defined policy which accepts all cookies.
     */
    /**
     * 接收全部cookie的预定义策略
     */
    public static final CookiePolicy ACCEPT_ALL = new CookiePolicy(){
        public boolean shouldAccept(URI uri, HttpCookie cookie) {
            return true;
        }
    };

    /**
     * One pre-defined policy which accepts no cookies.
     */
    /**
     * 拒绝全部cookie的策略
     */
    public static final CookiePolicy ACCEPT_NONE = new CookiePolicy(){
        public boolean shouldAccept(URI uri, HttpCookie cookie) {
            return false;
        }
    };

    /**
     * One pre-defined policy which only accepts cookies from original server.
     */
    /**
     * 仅接受来自原始服务器cookie的策略
     */
    public static final CookiePolicy ACCEPT_ORIGINAL_SERVER  = new CookiePolicy(){
        public boolean shouldAccept(URI uri, HttpCookie cookie) {
            if (uri == null || cookie == null)
                return false;
            ////进行来源匹配判定，判定cookie来源和URI来源
            return HttpCookie.domainMatches(cookie.getDomain(), uri.getHost());
        }
    };


    /**
     * Will be called to see whether or not this cookie should be accepted.
     *
     * @param uri       the URI to consult accept policy with
     * @param cookie    the HttpCookie object in question
     * @return          {@code true} if this cookie should be accepted;
     *                  otherwise, {@code false}
     */
    /**
     * 通过现在的cookie和请求的URI（资源标识符）判定是否应该接收该cookie
     * @param uri
     * @param cookie
     * @return
     */
    public boolean shouldAccept(URI uri, HttpCookie cookie);
}
