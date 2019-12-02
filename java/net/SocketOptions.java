/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.lang.annotation.Native;

/**
 * Interface of methods to get/set socket options.  This interface is
 * implemented by: <B>SocketImpl</B> and  <B>DatagramSocketImpl</B>.
 * Subclasses of these should override the methods
 * of this interface in order to support their own options.
 * 获取设置套接字选项的方法的接口。该接口有实现类：<B> SocketImpl </ B>和<B> DatagramSocketImpl </ B>。
 * 这些的子类应重写此接口的方法以支持其自己的选项。
 * <P>
 * The methods and constants which specify options in this interface are
 * for implementation only.  If you're not subclassing SocketImpl or
 * DatagramSocketImpl, <B>you won't use these directly.</B> There are
 * type-safe methods to get/set each of these options in Socket, ServerSocket,
 * DatagramSocket and MulticastSocket.
 *
 * 在此接口中指定选项的方法和常量仅用于实现。如果您不是子类SocketImpl或DatagramSocketImpl的子类，
 * 则您将不会直接使用它们。</ B>在Socket，ServerSocket，DatagramSocket和MulticastSocket中，
 * 有种类型安全的方法来获取/设置每个选项。
 * <P>
 * @author David Brown
 */

/**
 * 此接口中包含了一些本地native的一些tcp常量
 * 获取或者设置与某个套接字关联的选 项。选项可能存在于多层协议中，它们总会出现在最上面的套接字层。当操作套接字选项时，选项位于的层和选项的名称必须给出
 * 。为了操作套接字层的选项，应该 将层的值指定为SOL_SOCKET。为了操作其它层的选项，控制选项的合适协议号必须给出。
 * 例如，为了表示一个选项由TCP协议解析，层应该设定为协议 号TCP。
 */
public interface SocketOptions {

    /**
     * Enable/disable the option specified by <I>optID</I>.  If the option
     * is to be enabled, and it takes an option-specific "value",  this is
     * passed in <I>value</I>.  The actual type of value is option-specific,
     * and it is an error to pass something that isn't of the expected type:
     * <BR><PRE>
     * SocketImpl s;
     * ...
     * s.setOption(SO_LINGER, new Integer(10));
     *    // OK - set SO_LINGER w/ timeout of 10 sec.
     * s.setOption(SO_LINGER, new Double(10));
     *    // ERROR - expects java.lang.Integer
     *</PRE>
     * If the requested option is binary, it can be set using this method by
     * a java.lang.Boolean:
     * <BR><PRE>
     * s.setOption(TCP_NODELAY, new Boolean(true));
     *    // OK - enables TCP_NODELAY, a binary option
     * </PRE>
     * <BR>
     * Any option can be disabled using this method with a Boolean(false):
     * <BR><PRE>
     * s.setOption(TCP_NODELAY, new Boolean(false));
     *    // OK - disables TCP_NODELAY
     * s.setOption(SO_LINGER, new Boolean(false));
     *    // OK - disables SO_LINGER
     * </PRE>
     * <BR>
     * For an option that has a notion of on and off, and requires
     * a non-boolean parameter, setting its value to anything other than
     * <I>Boolean(false)</I> implicitly enables it.
     * <BR>
     * Throws SocketException if the option is unrecognized,
     * the socket is closed, or some low-level error occurred
     * <BR>
     * @param optID identifies the option
     * @param value the parameter of the socket option
     * @throws SocketException if the option is unrecognized,
     * the socket is closed, or some low-level error occurred
     * @see #getOption(int)
     */

    public void
        setOption(int optID, Object value) throws SocketException;

    /**
     * Fetch the value of an option.
     * Binary options will return java.lang.Boolean(true)
     * if enabled, java.lang.Boolean(false) if disabled, e.g.:
     * <BR><PRE>
     * SocketImpl s;
     * ...
     * Boolean noDelay = (Boolean)(s.getOption(TCP_NODELAY));
     * if (noDelay.booleanValue()) {
     *     // true if TCP_NODELAY is enabled...
     * ...
     * }
     * </PRE>
     * <P>
     * For options that take a particular type as a parameter,
     * getOption(int) will return the parameter's value, else
     * it will return java.lang.Boolean(false):
     * <PRE>
     * Object o = s.getOption(SO_LINGER);
     * if (o instanceof Integer) {
     *     System.out.print("Linger time is " + ((Integer)o).intValue());
     * } else {
     *   // the true type of o is java.lang.Boolean(false);
     * }
     * </PRE>
     *
     * @param optID an {@code int} identifying the option to fetch
     * @return the value of the option
     * @throws SocketException if the socket is closed
     * @throws SocketException if <I>optID</I> is unknown along the
     *         protocol stack (including the SocketImpl)
     * @see #setOption(int, java.lang.Object)
     */
    public Object getOption(int optID) throws SocketException;

    /**
     * The java-supported BSD-style options.
     */

    /**
     * Disable Nagle's algorithm for this connection.  Written data
     * to the network is not buffered pending acknowledgement of
     * previously written data.
     *<P>
     * Valid for TCP only: SocketImpl.
     *
     * @see Socket#setTcpNoDelay
     * @see Socket#getTcpNoDelay
     */

    /**
     *  0. 小缓冲写如延迟值。如果为0，则禁用了TCP对于小缓冲区操作的Nagle算法。
     *  如果需要启动该算法则需要把该值设置为非0
     *
     *  1.有趣的是，这8个选项除了第一个没在SO前缀外，其他7个选项都以SO作为前缀。
     *  其实这个SO就是Socket Option的缩写；因此，在Java中约定所有以SO为前缀
     *  的常量都表示Socket选项；当然，也有例外，如TCP_NODELAY.在Socket类中
     *  为每一个选项提供了一对get和set方法，分别用来获得和设置这些选项。
     *
     *
     *  2.在默认情况下，客户端向服务器发送数据时，会根据数据包的大小决定是否立即发送。
     *  当数据包中的数据很少时，如只有1个字节，而数据包的头却有几十个字节（IP头+TCP头）时，
     *  系统会在发送之前先将较小的包合并到软大的包后，一起将数据发送出去。在发送下一个数据包时，
     *  系统会等待服务器对前一个数据包的响应，当收到服务器的响应后，再发送下一个数据包，
     *  这就是所谓的Nagle算法；在默认情况下，Nagle算法是开启的。
     *   这种算法虽然可以有效地改善网络传输的效率，但对于网络速度比较慢，而且对实现性的要求
     *   比较高的情况下（如游戏、Telnet等），使用这种方式传输数据会使得客户端有明显的停顿现象。
     *   因此，最好的解决方案就是需要Nagle算法时就使用它，不需要时就关闭它。而使用setTcpToDelay
     *   正好可以满足这个需求。当使用setTcpNoDelay（true）将Nagle算法关闭后，客户端每发送一次数据
     *   ，无论数据包的大小都会将这些数据发送出去。
     *
     *   3.禁用纳格算法，将数据立即发送出去。纳格算法是以减少封包传送量来增进TCP/IP网络的效能，当我们调用下面代码
     */
    @Native public final static int TCP_NODELAY = 0x0001;

    /**
     * Fetch the local address binding of a socket (this option cannot
     * be "set" only "gotten", since sockets are bound at creation time,
     * and so the locally bound address cannot be changed).  The default local
     * address of a socket is INADDR_ANY, meaning any local address on a
     * multi-homed host.  A multi-homed host can use this option to accept
     * connections to only one of its addresses (in the case of a
     * ServerSocket or DatagramSocket), or to specify its return address
     * to the peer (for a Socket or DatagramSocket).  The parameter of
     * this option is an InetAddress.
     * <P>
     * This option <B>must</B> be specified in the constructor.
     * <P>
     * Valid for: SocketImpl, DatagramSocketImpl
     *
     * @see Socket#getLocalAddress
     * @see DatagramSocket#getLocalAddress
     */

    @Native public final static int SO_BINDADDR = 0x000F;

    /** Sets SO_REUSEADDR for a socket.  This is used only for MulticastSockets
     * in java, and it is set by default for MulticastSockets.
     * <P>
     * Valid for: DatagramSocketImpl
     */

    /**
     * 发送缓冲区的大小，单位字节
     *
     * 1.其实就是端口重用，为什么要使用这个属性是因为操作系统在关闭连接的时候端口并不是马上释放掉，
     * 因为可能还有一些数据没有接受完成，所以再下一次再绑定该端口的时候会导致失败，它的使用场景
     * 主要就是在服务器程序关闭后马上启动一个新的服务的时候，这个时候需要设置该属性，否则很大几
     * 率会导致绑定端口失败。
     *  接受缓冲区的大小，单位字节
     * 2.允许一个程序在多个实例对同一个端口进行绑定。
     * 如果你定义个SO_REUSEADDR，只定义一个套接字在一个端口上进行监听，如果服务器出现意外而导致没有将这个端口释放，
     * 那么服务器重新启动后，你还可以用这个端口，因为你已经规定可以重用了，如果你没定义的话，你就会得到提示，ADDR已在使用中。
     * 用在多播的时候，也经常使用SO_REUSEADDR，也是为了防止机器出现意外，导致端口没有释放，而使重启后的绑定失败～
     *
     * 3.当接收方通过socket close方法关闭socket时，如果网络上还有发送到这个socket数据，底层socket不会立即释放本地端口，
     * 而是等待一段时间，确保接收到了网络上发送过来的延迟数据，然后在释放端口。socket接收到延迟数据后，不会对这些数据作任何处理。
     * socket接收延迟数据目的是确保这些数据不会被其他碰巧绑定到同样的端口的新进程收到。
     * 客户端一般采用随机端口，因此出现两个客户端绑定到同样的端口可能性不大，而服务器端都是使用固定端口，当服务器端程序关闭后，
     * 有可能他的端口还会被占用一段时间，如果此时立刻在此主机上重启服务器程序，由于服务器端口被占用，使得服务器程序无法绑定改端口，启动失败。
     * 为了确保一个进程关闭socket后，即使它还没释放端口，同一主机上的其他进程可以立刻重用该端口，可以调用socket的setReuseAddress(true)
     * 需要注意的是setReuseAddress(boolean on)方法必须在socket还未绑定到一个本地端口之前调用，否则无效
     *
     * 4.TCP发送缓存区和接收缓存区,默认是8192(8K)，一般情况下足够了，而且就算你增加了发送缓存区，对方没有增加它对应的接收缓冲，
     * 那么在TCP三握手时，最后确定的最大发送窗口还是双方最小的那个缓冲区，就算你无视，发了更多的数据，那么多出来的数据也会被丢弃。
     * 除非双方都协商好。这可以减少传输数据的次数，提高传输效率
     */
    @Native public final static int SO_REUSEADDR = 0x04;

    /**
     * Sets SO_BROADCAST for a socket. This option enables and disables
     * the ability of the process to send broadcast messages. It is supported
     * for only datagram sockets and only on networks that support
     * the concept of a broadcast message (e.g. Ethernet, token ring, etc.),
     * and it is set by default for DatagramSockets.
     * @since 1.4
     */

    /**
     * 1.用于开启或关闭广播地址上组播功能，当然这个也要在网络环境支持。
     * 2.此项是启动和禁用发送广播消息的处理能力，它仅用于数据报套接字和支持广播消息概念的网络上。默认情况为datagramSocket设置此选项
     */
    @Native public final static int SO_BROADCAST = 0x0020;

    /** Set which outgoing interface on which to send multicast packets.
     * Useful on hosts with multiple network interfaces, where applications
     * want to use other than the system default.  Takes/returns an InetAddress.
     * <P>
     * Valid for Multicast: DatagramSocketImpl
     *
     * @see MulticastSocket#setInterface(InetAddress)
     * @see MulticastSocket#getInterface()
     */

    /**
     * 这个主要是在机器上有多个网络接口（应该是网卡驱动）中选择需要网卡发送多播包。
     * 其实这个只需要用bind方法指定IP就可以做到了。
     */
    @Native public final static int IP_MULTICAST_IF = 0x10;

    /** Same as above. This option is introduced so that the behaviour
     *  with IP_MULTICAST_IF will be kept the same as before, while
     *  this new option can support setting outgoing interfaces with either
     *  IPv4 and IPv6 addresses.
     *
     *  NOTE: make sure there is no conflict with this
     * @see MulticastSocket#setNetworkInterface(NetworkInterface)
     * @see MulticastSocket#getNetworkInterface()
     * @since 1.4
     */
    @Native public final static int IP_MULTICAST_IF2 = 0x1f;

    /**
     * This option enables or disables local loopback of multicast datagrams.
     * This option is enabled by default for Multicast Sockets.
     * @since 1.4
     */

    /**
     * 此选项启用或禁用多播数据报的本地回送。
     */
    @Native public final static int IP_MULTICAST_LOOP = 0x12;

    /**
     * This option sets the type-of-service or traffic class field
     * in the IP header for a TCP or UDP socket.
     * @since 1.4
     */

    @Native public final static int IP_TOS = 0x3;

    /**
     * Specify a linger-on-close timeout.  This option disables/enables
     * immediate return from a <B>close()</B> of a TCP Socket.  Enabling
     * this option with a non-zero Integer <I>timeout</I> means that a
     * <B>close()</B> will block pending the transmission and acknowledgement
     * of all data written to the peer, at which point the socket is closed
     * <I>gracefully</I>.  Upon reaching the linger timeout, the socket is
     * closed <I>forcefully</I>, with a TCP RST. Enabling the option with a
     * timeout of zero does a forceful close immediately. If the specified
     * timeout value exceeds 65,535 it will be reduced to 65,535.
     * <P>
     * Valid only for TCP: SocketImpl
     *
     * @see Socket#setSoLinger
     * @see Socket#getSoLinger
     */

    /**
     * 关闭一个连接前等待未发送的数据发送完毕所经过的秒数。如果该值为0，则禁用了该属性
     *
     * 1.由于在调用了close()方法之后，程序默认会检查是否还有数据没有接受或者发送完，
     * 有的话是需要将这些数据处理完再关闭释放端口。但是人的忍耐是有限度的，不能守夜
     * 的大叔要关门了你们还在他妈的加班，所以必须有超时时间，到了时间就强制都赶出门。
     * 2.在Java Socket中，当我们调用Socket的close方法时，默认的行为是当底层网卡所有数据都发送完毕后，关闭连接
     * 通过setSoLinger方法，我们可以修改close方法的行为
     * 1，setSoLinger(true, 0)
     * 当网卡收到关闭连接请求后，无论数据是否发送完毕，立即发送RST包关闭连接
     *
     * 2，setSoLinger(true, delay_time)
     * 当网卡收到关闭连接请求后，等待delay_time
     * 如果在delay_time过程中数据发送完毕，正常四次挥手关闭连接
     * 如果在delay_time过程中数据没有发送完毕，发送RST包关闭连接
     *
     *
     * 3.此选项指定函数close对面向连接的协议如何操作（如TCP）。内核缺省close操作是立即返回，
     * 如果有数据残留在套接口缓冲区中则系统将试着将这些数据发送给对方。
     * 注：大致意思就是说SO_LINGER选项用来设置当调用closesocket时是否马上关闭socket；
     * 具体的描述如下：
     * 1、若设置了SO_LINGER（亦即linger结构中的l_onoff域设为非零），并设置了零超时间隔，则closesocket()不被阻塞立即执行，不论是否有排队数据未发送或未被确认。这种关闭方式称为“强制”或“失效”关闭，因为套接口的虚电路立即被复位，且丢失了未发送的数据。在远端的recv()调用将以WSAECONNRESET出错。
     *
     * 2、若设置了SO_LINGER并确定了非零的超时间隔，则closesocket()调用阻塞进程，直到所剩数据发送完毕或超时。这种关闭称为“优雅”或“从容”关闭。请注意如果套接口置为非阻塞且SO_LINGER设为非零超时，则closesocket()调用将以WSAEWOULDBLOCK错误返回。
     *
     * 3、若在一个流类套接口上设置了SO_DONTLINGER（也就是说将linger结构的l_onoff域设为零），则closesocket()调用立即返回。但是，如果可能，排队的数据将在套接口关闭前发送。请注意，在这种情况下WINDOWS套接口实现将在一段不确定的时间内保留套接口以及其他资源，这对于想用所以套接口的应用程序来说有一定影响。
     */
    @Native public final static int SO_LINGER = 0x0080;

    /** Set a timeout on blocking Socket operations:
     * <PRE>
     * ServerSocket.accept();
     * SocketInputStream.read();
     * DatagramSocket.receive();
     * </PRE>
     *
     * <P> The option must be set prior to entering a blocking
     * operation to take effect.  If the timeout expires and the
     * operation would continue to block,
     * <B>java.io.InterruptedIOException</B> is raised.  The Socket is
     * not closed in this case.
     *
     * <P> Valid for all sockets: SocketImpl, DatagramSocketImpl
     *
     * @see Socket#setSoTimeout
     * @see ServerSocket#setSoTimeout
     * @see DatagramSocket#setSoTimeout
     */

    /**
     * 1.选项必须在进入阻塞操作前设置才能生效。
     *
     * 2.可以通过这个选项来设置读取数据超时。当输入流的read方法被阻塞时，如果设置timeout（timeout的单位是毫秒），
     * 那么系统在等待了timeout毫秒后会抛出一个InterruptedIOException例外。在抛出例外后，输入流并未关闭，
     * 你可以继续通过read方法读取数据。
     *
     * 如果将timeout设为0，就意味着read将会无限等待下去，直到服务端程序关闭这个Socket.这也是timeout的默认值。
     * 如下面的语句将读取数据超时设为30秒：
     * socket1.setSoTimeout(30 * 1000);
     *
     * 当底层的Socket实现不支持SO_TIMEOUT选项时，这两个方法将抛出SocketException例外。不能将timeout设为负数，
     * 否则setSoTimeout方法将抛出IllegalArgumentException例外。
     */
    @Native public final static int SO_TIMEOUT = 0x1006;

    /**
     * Set a hint the size of the underlying buffers used by the
     * platform for outgoing network I/O. When used in set, this is a
     * suggestion to the kernel from the application about the size of
     * buffers to use for the data to be sent over the socket. When
     * used in get, this must return the size of the buffer actually
     * used by the platform when sending out data on this socket.
     *
     * Valid for all sockets: SocketImpl, DatagramSocketImpl
     *
     * @see Socket#setSendBufferSize
     * @see Socket#getSendBufferSize
     * @see DatagramSocket#setSendBufferSize
     * @see DatagramSocket#getSendBufferSize
     */

    /**
     * 这个发送缓冲区不要与nagle的缓冲区大小认为是同一个东西，其实nagle的最大缓冲区是有MTU来决定的。
     * 其实这个属性在windows平台下是设置了内核缓冲区的大小，linux下我还不知道。
     * 在win平台上该值默认为8K，这个值设置过小的话会导致数据包发送非常频繁，
     */
    @Native public final static int SO_SNDBUF = 0x1001;

    /**
     * Set a hint the size of the underlying buffers used by the
     * platform for incoming network I/O. When used in set, this is a
     * suggestion to the kernel from the application about the size of
     * buffers to use for the data to be received over the
     * socket. When used in get, this must return the size of the
     * buffer actually used by the platform when receiving in data on
     * this socket.
     *
     * Valid for all sockets: SocketImpl, DatagramSocketImpl
     *
     * @see Socket#setReceiveBufferSize
     * @see Socket#getReceiveBufferSize
     * @see DatagramSocket#setReceiveBufferSize
     * @see DatagramSocket#getReceiveBufferSize
     */

    /**
     * 1.这个接受缓冲区和SO_SNDBUF相反，当大传输量的时候则需要大一点的缓冲区这样可以提高传输速度，
     * 但是对应信息量非常小的则可以使用小一点的缓冲区确保数据的及时处理。
     *
     * 2.先明确一个概念：每个TCP socket在内核中都有一个发送缓冲区和一个接收缓冲区，
     * TCP的全双工的工作模式以及TCP的滑动窗口便是依赖于这两个独立的buffer以及此buffer的填充状态。
     * 接收缓冲区把数据缓存入内核，应用进程一直没有调用read进行读取的话，此数据会一直缓存在相应socket
     * 的接收缓冲区内。再啰嗦一点，不管进程是否读取socket，对端发来的数据都会经由内核接收并且缓存到socket的内核接收缓冲区之中。
     * read所做的工作，就是把内核缓冲区中的数据拷贝到应用层用户的buffer里面，仅此而已。进程调用send发送的数据的时候，
     * 最简单情况（也是一般情况），将数据拷贝进入socket的内核发送缓冲区之中，然后send便会在上层返回。换句话说，
     * send返回之时，数据不一定会发送到对端去（和write写文件有点类似），send仅仅是把应用层buffer的数据拷贝进socket的内核发送buffer中。
     * 后续我会专门用一篇文章介绍read和send所关联的内核动作。每个UDP socket都有一个接收缓冲区，没有发送缓冲区，
     * 从概念上来说就是只要有数据就发，不管对方是否可以正确接收，所以不缓冲，不需要发送缓冲区。
     *
     * 接收缓冲区被TCP和UDP用来缓存网络上来的数据，一直保存到应用进程读走为止。
     * 对于TCP，如果应用进程一直没有读取，buffer满了之后，
     * 发生的动作是：通知对端TCP协议中的窗口关闭。这个便是滑动窗口的实现。保证TCP套接口接收缓冲区不会溢出，从而保证了TCP是可靠传输。
     * 因为对方不允许发出超过所通告窗口大小的数据。 这就是TCP的流量控制，如果对方无视窗口大小而发出了超过窗口大小的数据，则接收方TCP将丢弃它。
     * UDP：当套接口接收缓冲区满时，新来的数据报无法进入接收缓冲区，此数据报就被丢弃。UDP是没有流量控制的；快的发送者可以很容易地就淹没慢的接收者，
     * 导致接收方的UDP丢弃数据报。
     * 以上便是TCP可靠，UDP不可靠的实现。
     * 这两个选项就是来设置TCP连接的两个buffer尺寸的
     */
    @Native public final static int SO_RCVBUF = 0x1002;

    /**
     * When the keepalive option is set for a TCP socket and no data
     * has been exchanged across the socket in either direction for
     * 2 hours (NOTE: the actual value is implementation dependent),
     * TCP automatically sends a keepalive probe to the peer. This probe is a
     * TCP segment to which the peer must respond.
     * One of three responses is expected:
     * 1. The peer responds with the expected ACK. The application is not
     *    notified (since everything is OK). TCP will send another probe
     *    following another 2 hours of inactivity.
     * 2. The peer responds with an RST, which tells the local TCP that
     *    the peer host has crashed and rebooted. The socket is closed.
     * 3. There is no response from the peer. The socket is closed.
     *
     * The purpose of this option is to detect if the peer host crashes.
     *
     * Valid only for TCP socket: SocketImpl
     *
     * @see Socket#setKeepAlive
     * @see Socket#getKeepAlive
     */

    /**
     * 1.如果在 2 个小时（注：实际值与实现有关）内在任意方向上都没有跨越套接字交换数据，
     * 则 TCP 会自动将 keepalive 探头发送到同位体。tcp协议级别保活机制 超时时间
     * 保持连接的特性。如果该值为0，则禁用了保持连接的特性。如果要启动该特性则要把该值设置为非0
     *
     * 2.当建立TCP链接后，如果应用程序或者上层协议一直不发送数据，或者隔很长一段时间才发送数据，
     * 当链接很久没有数据报文传输时就需要通过keepalive机制去确定对方是否在线，链接是否需要继续保持。
     * 当超过一定时间没有发送数据时，TCP会自动发送一个数据为空的报文给对方，如果对方回应了报文，
     * 说明对方在线，链接可以继续保持，如果对方没有报文返回，则在重试一定次数之后认为链接丢失，就不会释放链接。
     * 控制对闲置连接的检测机制，链接闲置达到7200秒，就开始发送探测报文进行探测。
     * net.ipv4.tcp_keepalive_time：单位秒，表示发送探测报文之前的链接空闲时间，默认为7200。
     * net.ipv4.tcp_keepalive_intvl：单位秒，表示两次探测报文发送的时间间隔，默认为75。
     * net.ipv4.tcp_keepalive_probes：表示探测的次数，默认9次。
     *
     * 3.保持连接检测对方主机是否崩溃，避免（服务器）永远阻塞于TCP连接的输入。
     * 设置该选项后，如果2小时内在此套接口的任一方向都没有数据交换，TCP就自动给对方 发一个保持存活探测分节(keepalive probe)。这是一个对方必须响应的TCP分节.它会导致以下三种情况：
     * 1、对方接收一切正常：以期望的ACK响应，2小时后，TCP将发出另一个探测分节。
     * 2、对方已崩溃且已重新启动：以RST响应。套接口的待处理错误被置为ECONNRESET，套接 口本身则被关闭。
     * 3、对方无任何响应：源自berkeley的TCP发送另外8个探测分节，相隔75秒一个，试图得到一个响应。在发出第一个探测分节11分钟15秒后若仍无响应就放弃。
     * 套接口的待处理错误被置为ETIMEOUT，套接口本身则被关闭。如ICMP错误是“host unreachable(主机不可达)”，说明对方主机并没有崩溃，
     * 但是不可达，这种情况下待处理错误被置为 EHOSTUNREACH。
     *
     *
     * 4.keepalive不是说TCP的常连接，当我们作为服务端，一个客户端连接上来，如果设置了keeplive为true，
     * 当对方没有发送任何数据过来，超过一个时间(看系统内核参数配置)，那么我们这边会发送一个ack探测包发到对方，
     * 探测双方的TCP/IP连接是否有效(对方可能断点，断网)，在Linux好像这个时间是75秒。如果不设置，那么客户端宕机时，
     * 服务器永远也不知道客户端宕机了，仍然保存这个失效的连接。
     *
     */
    @Native public final static int SO_KEEPALIVE = 0x0008;

    /**
     * When the OOBINLINE option is set, any TCP urgent data received on
     * the socket will be received through the socket input stream.
     * When the option is disabled (which is the default) urgent data
     * is silently discarded.
     *
     * @see Socket#setOOBInline
     * @see Socket#getOOBInline
     */

    /**
     *如果这个Socket选项打开，可以通过Socket类的sendUrgentData方法向服务器发送一个单字节的数据。
     * 这个单字节数据并不经过输出缓冲区，而是立即发出。虽然在客户端并不是使用OutputStream向服务器发送数据，
     * 但在服务端程序中这个单字节的数据是和其它的普通数据混在一起的。因此，在服务端程序中并不知道由客户端发过来的数据是由
     * OutputStream还是由sendUrgentData发过来的。下面是sendUrgentData方法的声明：
     * public void sendUrgentData(int data) throws IOException
     * 虽然sendUrgentData的参数data是int类型，但只有这个int类型的低字节被发送，其它的三个字节被忽略。
     */
    @Native public final static int SO_OOBINLINE = 0x1003;
}
