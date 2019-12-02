/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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
 * A channel that can read bytes.
 *
 * <p> Only one read operation upon a readable channel may be in progress at
 * any given time.  If one thread initiates a read operation upon a channel
 * then any other thread that attempts to initiate another read operation will
 * block until the first operation is complete.  Whether or not other kinds of
 * I/O operations may proceed concurrently with a read operation depends upon
 * the type of the channel. </p>
 *
 *
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

/**
 * 可以读取字节的通道
 *
 * 1.如果一个Channel类实现了ReadableByteChannel接口，则表示其是可读的，可以调用read()方法读取；
 * 2.在可读的通道中，一个进程只能有一个读操作。如果当前线程正在读通道，其他尝试
 *  读通道的线程，必须等待正在读痛的线程完成。
 * 3.在任何给定时间，可能只在可读通道上进行一次读取操作。如果一个线程在通道上启动了读取操作*，那么其他任何尝试启动另一个读取操作的线程将*阻塞，直到第一个操作完成。其他类型的I / O操作是否可以与读取操作同时进行取决于通道的类型
 */
public interface ReadableByteChannel extends Channel {

    /**
     * Reads a sequence of bytes from this channel into the given buffer.
     *
     * <p> An attempt is made to read up to <i>r</i> bytes from the channel,
     * where <i>r</i> is the number of bytes remaining in the buffer, that is,
     * <tt>dst.remaining()</tt>, at the moment this method is invoked.
     *
     * <p> Suppose that a byte sequence of length <i>n</i> is read, where
     * <tt>0</tt>&nbsp;<tt>&lt;=</tt>&nbsp;<i>n</i>&nbsp;<tt>&lt;=</tt>&nbsp;<i>r</i>.
     * This byte sequence will be transferred into the buffer so that the first
     * byte in the sequence is at index <i>p</i> and the last byte is at index
     * <i>p</i>&nbsp;<tt>+</tt>&nbsp;<i>n</i>&nbsp;<tt>-</tt>&nbsp;<tt>1</tt>,
     * where <i>p</i> is the buffer's position at the moment this method is
     * invoked.  Upon return the buffer's position will be equal to
     * <i>p</i>&nbsp;<tt>+</tt>&nbsp;<i>n</i>; its limit will not have changed.
     *
     * <p> A read operation might not fill the buffer, and in fact it might not
     * read any bytes at all.  Whether or not it does so depends upon the
     * nature and state of the channel.  A socket channel in non-blocking mode,
     * for example, cannot read any more bytes than are immediately available
     * from the socket's input buffer; similarly, a file channel cannot read
     * any more bytes than remain in the file.  It is guaranteed, however, that
     * if a channel is in blocking mode and there is at least one byte
     * remaining in the buffer then this method will block until at least one
     * byte is read.
     *
     * <p> This method may be invoked at any time.  If another thread has
     * already initiated a read operation upon this channel, however, then an
     * invocation of this method will block until the first operation is
     * complete. </p>
     *
     * @param  dst
     *         The buffer into which bytes are to be transferred
     *
     * @return  The number of bytes read, possibly zero, or <tt>-1</tt> if the
     *          channel has reached end-of-stream
     *
     * @throws  NonReadableChannelException
     *          If this channel was not opened for reading
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes this channel
     *          while the read operation is in progress
     *
     * @throws  ClosedByInterruptException
     *          If another thread interrupts the current thread
     *          while the read operation is in progress, thereby
     *          closing the channel and setting the current thread's
     *          interrupt status
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */

    /**
     * 返回读取的字节数，可能为零，如果通道已到达流末尾，则为 -1
     *
     * 1.从通道中读取字节序列，写到缓存中，即从通道中读取数据到缓冲区中。
     * 2.只能读取缓冲剩余空间容量的字节序列到缓存。
     * 3.一个读操作也许不能填充缓存，实际也许没有读取任何字节。是否能够填充和读取字节，
     *      依赖于通道的状态。一个非阻塞的通道不能读取大于socket输入缓冲区容量的字节数；相似地，
     *      一个文件通道不能读取大于文件字节大小的字节。如果通道为阻塞模式，则至少有一个字节在通道的socket
     *      输入缓存区中可用，如果没有read方法阻塞到至少有一个字节可用。
     *
     * 4.此方法可以在任何时候调用。如果其他线程已经执行一个读操作，则当前读操作阻塞到其他
     *      线程执行完读操作。
     *
     * @param dst
     * @return
     * @throws IOException
     */

    public int read(ByteBuffer dst) throws IOException;

}
