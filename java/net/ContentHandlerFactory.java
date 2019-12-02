/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * This interface defines a factory for content handlers. An
 * implementation of this interface should map a MIME type into an
 * instance of {@code ContentHandler}.
 * <p>
 * This interface is used by the {@code URLStreamHandler} class
 * to create a {@code ContentHandler} for a MIME type.
 *
 * @author  James Gosling
 * @see     java.net.ContentHandler
 * @see     java.net.URLStreamHandler
 * @since   JDK1.0
 */

/**
 * ContentHandlerFactory类无引入包和继承类
 * 该接口声明了内容处理程序工厂
 * 此接口的实现应该将 MIME 类型映射到 ContentHandler 的实例中。
 * URLStreamHandler 类使用此接口创建 MIME 类型的 ContentHandler。
 */
public interface ContentHandlerFactory {
    /**
     * Creates a new {@code ContentHandler} to read an object from
     * a {@code URLStreamHandler}.
     *
     * @param   mimetype   the MIME type for which a content handler is desired.

     * @return  a new {@code ContentHandler} to read an object from a
     *          {@code URLStreamHandler}.
     * @see     java.net.ContentHandler
     * @see     java.net.URLStreamHandler
     */

    /**
     * 创建新的ContentHandler类从URLStreamHandler中读取实例
     * @param mimetype
     * @return
     */
    ContentHandler createContentHandler(String mimetype);
}
