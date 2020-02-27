/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.channels;

import java.io.IOException;
import java.io.Closeable;


/**
 * A nexus for I/O operations.
 *
 * <p> A channel represents an open connection to an entity such as a hardware
 * device, a file, a network socket, or a program component that is capable of
 * performing one or more distinct I/O operations, for example reading or
 * writing.
 *
 * 通道表示到实体(如硬件)的开放连接设备、文件、网络套接字或程序组件,能够
 * 执行一个或多个不同的I/O操作，例如读取或写。
 *
 * <p> A channel is either open or closed.  A channel is open upon creation,
 * and once closed it remains closed.  Once a channel is closed, any attempt to
 * invoke an I/O operation upon it will cause a {@link ClosedChannelException}
 * to be thrown.  Whether or not a channel is open may be tested by invoking
 * its {@link #isOpen isOpen} method.
 *
 * 通道要么是打开的，要么是关闭的。通道在创建时打开，一旦关闭，它就会一直关闭。一旦通道关闭，任何尝试
 * 调用I/O操作将导致{@link ClosedChannelException}被抛出去。通道是否打开可以通过调用{@link #isOpen isOpen}方法来测试
 *
 * <p> Channels are, in general, intended to be safe for multithreaded access
 * as described in the specifications of the interfaces and classes that extend
 * and implement this interface.
 *  通常，通道用于多线程访问是安全的 如扩展的接口和类的规范所述并实现此接口。
 *
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

/**
 * Channel是一个对象，我们对数据的读写都不会直接与Channel接触，而是通过中间的缓冲区buffer。Channel在NIO中就像是Stream在传统IO中，
 * 只不过他们的区别是Channel是双向的（unix模型的底层系统通道也是双向的），可读，可写，可同时读写，而Stream是单向的，一个Stream只能
 * 是读或者写。
 */
public interface Channel extends Closeable {

    /**
     * Tells whether or not this channel is open.
     *：表示该通道是否打开。
     * @return <tt>true</tt> if, and only if, this channel is open
     */
    public boolean isOpen();

    /**
     * Closes this channel.
     *关闭这个通道。
     * <p> After a channel is closed, any further attempt to invoke I/O
     * operations upon it will cause a {@link ClosedChannelException} to be
     * thrown.
     *通道关闭后，调用I/O的任何进一步尝试对它的操作将导致抛出{@link ClosedChannelException}。
     * <p> If this channel is already closed then invoking this method has no
     * effect.
     *
     * <p> This method may be invoked at any time.  If some other thread has
     * already invoked it, however, then another invocation will block until
     * the first invocation is complete, after which it will return without
     * effect. </p>
     *  这个方法可以在任何时候调用。如果其他线程有已经调用了它，但是，另一个调用将阻塞，直到第一次调用完成后，它将返回结果。
     * @throws  IOException  If an I/O error occurs
     */
    public void close() throws IOException;

}
