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
 * Signals that an error occurred while attempting to connect a
 * socket to a remote address and port.  Typically, the connection
 * was refused remotely (e.g., no process is listening on the
 * remote address/port).
 *
 * @since   JDK1.1
 */

/**
 * *表示尝试将套接字连接到远程地址和端口时发生错误。通常，连接被远程拒绝
 * （例如，没有进程在远程地址/端口上监听）。
 */
public class ConnectException extends SocketException {
    private static final long serialVersionUID = 3831404271622369215L;

    /**
     * Constructs a new ConnectException with the specified detail
     * message as to why the connect error occurred.
     * A detail message is a String that gives a specific
     * description of this error.
     * @param msg the detail message
     */
    public ConnectException(String msg) {
        super(msg);
    }

    /**
     * Construct a new ConnectException with no detailed message.
     */
    public ConnectException() {}
}
