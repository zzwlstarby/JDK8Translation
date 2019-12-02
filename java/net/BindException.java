/*
 * Copyright (c) 1996, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * Signals that an error occurred while attempting to bind a
 * socket to a local address and port.  Typically, the port is
 * in use, or the requested local address could not be assigned.
 *
 * @since   JDK1.1
 */

/**
 * 表示尝试将套接字绑定到本地地址和端口时发生错误。通常，端口正在使用*，
 * 或者无法分配请求的本地地址。
 */
public class BindException extends SocketException {
    private static final long serialVersionUID = -5945005768251722951L;

    /**
     * Constructs a new BindException with the specified detail
     * message as to why the bind error occurred.
     * A detail message is a String that gives a specific
     * description of this error.
     * @param msg the detail message
     */
    public BindException(String msg) {
        super(msg);
    }

    /**
     * Construct a new BindException with no detailed message.
     */
    public BindException() {}
}
