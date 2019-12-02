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

import java.io.IOException;

/**
 * The abstract class {@code ContentHandler} is the superclass
 * of all classes that read an {@code Object} from a
 * {@code URLConnection}.
 * <p>
 * An application does not generally call the
 * {@code getContent} method in this class directly. Instead, an
 * application calls the {@code getContent} method in class
 * {@code URL} or in {@code URLConnection}.
 * The application's content handler factory (an instance of a class that
 * implements the interface {@code ContentHandlerFactory} set
 * up by a call to {@code setContentHandler}) is
 * called with a {@code String} giving the MIME type of the
 * object being received on the socket. The factory returns an
 * instance of a subclass of {@code ContentHandler}, and its
 * {@code getContent} method is called to create the object.
 * <p>
 * If no content handler could be found, URLConnection will
 * look for a content handler in a user-defineable set of places.
 * By default it looks in sun.net.www.content, but users can define a
 * vertical-bar delimited set of class prefixes to search through in
 * addition by defining the java.content.handler.pkgs property.
 * The class name must be of the form:
 * <pre>
 *     {package-prefix}.{major}.{minor}
 * e.g.
 *     YoyoDyne.experimental.text.plain
 * </pre>
 * If the loading of the content handler class would be performed by
 * a classloader that is outside of the delegation chain of the caller,
 * the JVM will need the RuntimePermission "getClassLoader".
 *
 * @author  James Gosling
 * @see     java.net.ContentHandler#getContent(java.net.URLConnection)
 * @see     java.net.ContentHandlerFactory
 * @see     java.net.URL#getContent()
 * @see     java.net.URLConnection
 * @see     java.net.URLConnection#getContent()
 * @see     java.net.URLConnection#setContentHandlerFactory(java.net.ContentHandlerFactory)
 * @since   JDK1.0
 */

/**
 * 该抽象类是所有从URLConnection中读取对象数据的顶级类
 * 应用一般不直接调用该类的getContent方法
 * 一般会在URL类和URLConnection类中进行调用getContent方法
 * 应用的内容处理工厂（通过调用setContentHandler设置的完成ContentHandlerFactory接口的类）通过给出从套接字中接收到的对象MIME字段类型的字符串来调用
 * 该工厂将会返回一个ContentHandler类的子类实例并且getContent方法将会创建一个对象
 * 如果没有找到内容处理实体，URLConnection将会在用户自定义的地方寻找内容处理程序
 * 默认情况下，他会在sun.net.www.content中寻找，不过用户也可以定义一个以垂线分隔的类前缀集合，
 * 也可以通过定义 java.content.handler.pkgs 属性以供搜索。类名称必须具有以下形式：
 *      {package-prefix}.{major}.{minor}
 * 例如，
 *      YoyoDyne.experimental.text.plain
 * 如果由调用方委托链外部的加载器执行内容处理程序类的加载，则 JVM 需要 RuntimePermission "getClassLoader"。
 */
abstract public class ContentHandler {
    /**
     * Given a URL connect stream positioned at the beginning of the
     * representation of an object, this method reads that stream and
     * creates an object from it.
     *
     * @param      urlc   a URL connection.
     * @return     the object read by the {@code ContentHandler}.
     * @exception  IOException  if an I/O error occurs while reading the object.
     */
    /**
     * 通过位于对象表示形式开头的 URL 连接流，则此方法读取该流并根据其创建对象
     * @param urlc
     * @return
     * @throws IOException
     */
    abstract public Object getContent(URLConnection urlc) throws IOException;

    /**
     * Given a URL connect stream positioned at the beginning of the
     * representation of an object, this method reads that stream and
     * creates an object that matches one of the types specified.
     *
     * The default implementation of this method should call getContent()
     * and screen the return type for a match of the suggested types.
     *
     * @param      urlc   a URL connection.
     * @param      classes      an array of types requested
     * @return     the object read by the {@code ContentHandler} that is
     *                 the first match of the suggested types.
     *                 null if none of the requested  are supported.
     * @exception  IOException  if an I/O error occurs while reading the object.
     * @since 1.3
     */
    /**
     * 1.通过位于对象表示形式开头的 URL 连接流，则此方法读取该流并根据其创建对象（限制返回类型，必须为指定类型之一
     *
     *
     * 这两个类可能大家看着会比较云里雾里，到底什么是Content呢？Content是做什么的呢？
     * 其实单纯讲定义可能大家听不下去，我举个例子
     * 不知道大家是否看过http的报文头，里面有一项Content-Type字段，标识下面的报文内容类型
     *
     * 这个Content实际就是报文的内容，而Java这里抽象成了实际的对象。
     * 这两个类实际是定义了内容句柄，方便调用和获取至于这两个类中的MIME关键字，这个关键字是早年的邮件协议的扩展协议，
     * 因为当时只有ASCII码，只能传输英文、数字字段和一些特殊字符，到了现在就不够用了。但人们并没有舍弃使用ASCII码，
     * 而是将其他类型数据通过特定的编码方式转化为ASCII码，这种编码方式的协议就是MIME。
     *
     *
     * @param urlc
     * @param classes
     * @return
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public Object getContent(URLConnection urlc, Class[] classes) throws IOException {
        Object obj = getContent(urlc);

        for (int i = 0; i < classes.length; i++) {
          if (classes[i].isInstance(obj)) {
                return obj;
          }
        }
        return null;
    }

}
