/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

import java.io.InputStream;
import java.util.Map;
import java.util.List;
import java.io.IOException;

/**
 * Represent channels for retrieving resources from the
 * ResponseCache. Instances of such a class provide an
 * InputStream that returns the entity body, and also a
 * getHeaders() method which returns the associated response headers.
 *
 * @author Yingxian Wang
 * @since 1.5
 */

/**
 * 相当于从ResponseCache中取回资源的途径
 *
 * 该类的实例会提供一个输入流并且完整返回该输入流
 *
 * 并且也会提供getHeaders方法
 *
 * 该方法会返回相关的响应头
 *
 * CacheRequest 和CacheResponse这两个类都是针对缓存操作的，本身说明中和应用和网络操作关系不大（当然可以用在网络上，只是在任何方面都可以用得上，感觉封到IO里可能好些），
 * 不是很懂为什么要封在net包。该类主要是对高速缓存的资源管理。
 */
public abstract class CacheResponse {

    /**
     * Returns the response headers as a Map.
     *
     * @return An immutable Map from response header field names to
     *         lists of field values. The status line has null as its
     *         field name.
     * @throws IOException if an I/O error occurs
     *            while getting the response headers
     */
    /**
     * 以map方式返回响应头
     * @return
     * @throws IOException
     */
    public abstract Map<String, List<String>> getHeaders() throws IOException;

    /**
     * Returns the response body as an InputStream.
     *
     * @return an InputStream from which the response body can
     *         be accessed
     * @throws IOException if an I/O error occurs while
     *         getting the response body
     */
    /**
     * 返回从缓存输入的输入流
     * @return
     * @throws IOException
     */
    public abstract InputStream getBody() throws IOException;
}
