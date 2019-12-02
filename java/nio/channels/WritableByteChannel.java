/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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
import java.nio.ByteBuffer;


/**
 * A channel that can write bytes.
 *可以写入字节的通道。
 * <p> Only one write operation upon a writable channel may be in progress at
 * any given time.  If one thread initiates a write operation upon a channel
 * then any other thread that attempts to initiate another write operation will
 * block until the first operation is complete.  Whether or not other kinds of
 * I/O operations may proceed concurrently with a write operation depends upon
 * the type of the channel. </p>
 *
 * 从缓冲区中写数据到通道中
 *
 * 1.在可写通道上只能同时进行一个写操作 在任何给定的时间。如果一个线程在通道上启动写操作
 * 如果其他线程试图发起另一个写操作，则会直到第一个操作完成。
 * 不管是不是其他种类的I/O操作可以与所依赖的写操作并发进行通道的类型
 *
 * 2.如果一个Channel类实现了WritableByteChannel接口，则表示其是可写的，可以调用write()方法写入；
 *
 * 3.如果一个Channel类同时实现了ReadableByteChannel接口和WritableByteChannel接口则为双向通道，如果只实现其中一个，则为单向通道；
 * 如ByteChannel就是一个双向通道，实际上ByteChannel接口本身并不定义新的API方法，它是一个聚集了所继承的多个接口，并重新命名的便捷接口；
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public interface WritableByteChannel
    extends Channel
{

    /**
     * Writes a sequence of bytes to this channel from the given buffer.
     * 从给定缓冲区中的字节序列写入此通道。
     *
     * <p> An attempt is made to write up to <i>r</i> bytes to the channel,
     * where <i>r</i> is the number of bytes remaining in the buffer, that is,
     * <tt>src.remaining()</tt>, at the moment this method is invoked.
     *  尝试将r字节写到通道，其中r是缓冲区中剩余的字节数，就是说
     *
     * <p> Suppose that a byte sequence of length <i>n</i> is written, where
     * <tt>0</tt>&nbsp;<tt>&lt;=</tt>&nbsp;<i>n</i>&nbsp;<tt>&lt;=</tt>&nbsp;<i>r</i>.
     * This byte sequence will be transferred from the buffer starting at index
     * <i>p</i>, where <i>p</i> is the buffer's position at the moment this
     * method is invoked; the index of the last byte written will be
     * <i>p</i>&nbsp;<tt>+</tt>&nbsp;<i>n</i>&nbsp;<tt>-</tt>&nbsp;<tt>1</tt>.
     * Upon return the buffer's position will be equal to
     * <i>p</i>&nbsp;<tt>+</tt>&nbsp;<i>n</i>; its limit will not have changed.
     *
     * <p> Unless otherwise specified, a write operation will return only after
     * writing all of the <i>r</i> requested bytes.  Some types of channels,
     * depending upon their state, may write only some of the bytes or possibly
     * none at all.  A socket channel in non-blocking mode, for example, cannot
     * write any more bytes than are free in the socket's output buffer.
     *
     * <p> This method may be invoked at any time.  If another thread has
     * already initiated a write operation upon this channel, however, then an
     * invocation of this method will block until the first operation is
     * complete. </p>
     *
     * @param  src
     *         The buffer from which bytes are to be retrieved
     *
     * @return The number of bytes written, possibly zero
     *
     * @throws  NonWritableChannelException
     *          If this channel was not opened for writing
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes this channel
     *          while the write operation is in progress
     *
     * @throws  ClosedByInterruptException
     *          If another thread interrupts the current thread
     *          while the write operation is in progress, thereby
     *          closing the channel and setting the current thread's
     *          interrupt status
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */

    /**
     * 从指定的缓冲区中写数据到通道中
     * @param src
     * @return
     * @throws IOException
     */
    public int write(ByteBuffer src) throws IOException;

}
