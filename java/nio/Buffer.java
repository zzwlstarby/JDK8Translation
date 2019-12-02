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

package java.nio;

import java.util.Spliterator;

/**
 * A container for data of a specific primitive type.
 *
 * <p> A buffer is a linear, finite sequence of elements of a specific
 * primitive type.  Aside from its content, the essential properties of a
 * buffer are its capacity, limit, and position: </p>
 *
 * <blockquote>
 *
 *   <p> A buffer's <i>capacity</i> is the number of elements it contains.  The
 *   capacity of a buffer is never negative and never changes.  </p>
 *
 *   <p> A buffer's <i>limit</i> is the index of the first element that should
 *   not be read or written.  A buffer's limit is never negative and is never
 *   greater than its capacity.  </p>
 *
 *   <p> A buffer's <i>position</i> is the index of the next element to be
 *   read or written.  A buffer's position is never negative and is never
 *   greater than its limit.  </p>
 *
 * </blockquote>
 *
 * <p> There is one subclass of this class for each non-boolean primitive type.
 *
 *
 * <h2> Transferring data </h2>
 *
 * <p> Each subclass of this class defines two categories of <i>get</i> and
 * <i>put</i> operations: </p>
 *
 * <blockquote>
 *
 *   <p> <i>Relative</i> operations read or write one or more elements starting
 *   at the current position and then increment the position by the number of
 *   elements transferred.  If the requested transfer exceeds the limit then a
 *   relative <i>get</i> operation throws a {@link BufferUnderflowException}
 *   and a relative <i>put</i> operation throws a {@link
 *   BufferOverflowException}; in either case, no data is transferred.  </p>
 *
 *   <p> <i>Absolute</i> operations take an explicit element index and do not
 *   affect the position.  Absolute <i>get</i> and <i>put</i> operations throw
 *   an {@link IndexOutOfBoundsException} if the index argument exceeds the
 *   limit.  </p>
 *
 * </blockquote>
 *
 * <p> Data may also, of course, be transferred in to or out of a buffer by the
 * I/O operations of an appropriate channel, which are always relative to the
 * current position.
 *
 *
 * <h2> Marking and resetting </h2>
 *
 * <p> A buffer's <i>mark</i> is the index to which its position will be reset
 * when the {@link #reset reset} method is invoked.  The mark is not always
 * defined, but when it is defined it is never negative and is never greater
 * than the position.  If the mark is defined then it is discarded when the
 * position or the limit is adjusted to a value smaller than the mark.  If the
 * mark is not defined then invoking the {@link #reset reset} method causes an
 * {@link InvalidMarkException} to be thrown.
 *
 *
 * <h2> Invariants </h2>
 *
 * <p> The following invariant holds for the mark, position, limit, and
 * capacity values:
 *
 * <blockquote>
 *     <tt>0</tt> <tt>&lt;=</tt>
 *     <i>mark</i> <tt>&lt;=</tt>
 *     <i>position</i> <tt>&lt;=</tt>
 *     <i>limit</i> <tt>&lt;=</tt>
 *     <i>capacity</i>
 * </blockquote>
 *
 * <p> A newly-created buffer always has a position of zero and a mark that is
 * undefined.  The initial limit may be zero, or it may be some other value
 * that depends upon the type of the buffer and the manner in which it is
 * constructed.  Each element of a newly-allocated buffer is initialized
 * to zero.
 *
 *
 * <h2> Clearing, flipping, and rewinding </h2>
 *
 * <p> In addition to methods for accessing the position, limit, and capacity
 * values and for marking and resetting, this class also defines the following
 * operations upon buffers:
 *
 * <ul>
 *
 *   <li><p> {@link #clear} makes a buffer ready for a new sequence of
 *   channel-read or relative <i>put</i> operations: It sets the limit to the
 *   capacity and the position to zero.  </p></li>
 *
 *   <li><p> {@link #flip} makes a buffer ready for a new sequence of
 *   channel-write or relative <i>get</i> operations: It sets the limit to the
 *   current position and then sets the position to zero.  </p></li>
 *
 *   <li><p> {@link #rewind} makes a buffer ready for re-reading the data that
 *   it already contains: It leaves the limit unchanged and sets the position
 *   to zero.  </p></li>
 *
 * </ul>
 *
 *
 * <h2> Read-only buffers </h2>
 *
 * <p> Every buffer is readable, but not every buffer is writable.  The
 * mutation methods of each buffer class are specified as <i>optional
 * operations</i> that will throw a {@link ReadOnlyBufferException} when
 * invoked upon a read-only buffer.  A read-only buffer does not allow its
 * content to be changed, but its mark, position, and limit values are mutable.
 * Whether or not a buffer is read-only may be determined by invoking its
 * {@link #isReadOnly isReadOnly} method.
 *
 *
 * <h2> Thread safety </h2>
 *
 * <p> Buffers are not safe for use by multiple concurrent threads.  If a
 * buffer is to be used by more than one thread then access to the buffer
 * should be controlled by appropriate synchronization.
 *
 *
 * <h2> Invocation chaining </h2>
 *
 * <p> Methods in this class that do not otherwise have a value to return are
 * specified to return the buffer upon which they are invoked.  This allows
 * method invocations to be chained; for example, the sequence of statements
 *
 * <blockquote><pre>
 * b.flip();
 * b.position(23);
 * b.limit(42);</pre></blockquote>
 *
 * can be replaced by the single, more compact statement
 *
 * <blockquote><pre>
 * b.flip().position(23).limit(42);</pre></blockquote>
 *
 *
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

/**
 * 值得注意的是Buffer及其子类都不是线程安全的。
 *
 * 1.Java NIO中的Buffer用于和NIO通道进行交互。数据是从通道读入缓冲区，从缓冲区写入到通道中的。
 *
 * 2.缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，并提供了一组方法，用来方便的访问该块内存。
 * 为了理解Buffer的工作原理，需要熟悉它的三个属性:
 * capacity:
 * .position
 * .limit
 * position和limit的含义取决于Buffer处在读模式还是写模式。不管Buffer处在什么模式，capacity的含义总是一样的。
 *
 * capacity:作为一个内存块，Buffer有一个固定的大小值，也叫"capacity"。你只能往里写capacity个byte、long，char等类型。
 * 一旦Buffer满了，需要将其清空（通过读数据或者清除数据）才能继续写数据往里写数据。
 *
 * position
 * 当你写数据到Buffer中时，position表示当前的位置。初始的position值为0。
 * 当一个byte、long等数据写到Buffer后，position会向前移动到下一个可插入数据的Buffer单元。
 * position最大可为capacity –1当读取数据时，也是从某个特定位置读。
 *
 * 当将Buffer从写模式切换到读模式，position会被重置为0。当从Buffer的position处读取数据时，
 * position向前移动到下一个可读的位置。
 *
 *
 * limit
 * 在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。写模式下，limit等于Buffer的capacity。
 * 当切换Buffer到读模式时，limit表示你最多能读到多少数据。因此，当切换Buffer到读模式时，limit会被设置成写模式下的position值。
 * 换句话说，你能读到之前写入的所有数据，即limit被设置成已写数据的数量，这个值在写模式下就是position。
 *
 *
 *
 *
 *
 * 3.使用Buffer读写数据一般遵循以下四个步骤：
 * 1）写入数据到Buffer，一般有可以从Channel读取到到缓冲区中，也可以调用put方法写入。
 *
 * 2）调用flip()方法，切换数据模式。
 * flip方法将Buffer从写模式切换到读模式。调用flip()方法会将position设回0，并将limit设置成之前position的值。
 * 换句话说，position现在用于标记读的位置，limit表示之前写进了多少个byte、char等现在能读取多少个byte、char等。
 *
 *
 *
 *
 * 3）从Buffer中读取数据，一般从缓冲区读取数据写入到通道中，也可以调用get方法读取。
 *
 * 4）调用clear()方法或者compact()方法清空缓冲区。
 *
 * 当向buffer写入数据时，buffer会记录下写了多少数据。一旦要读取数据，需要通过flip()方法将Buffer从写模式切换到读模式。
 *
 * 在读模式下，可以读取之前写入到buffer的所有数据。一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入。
 *
 * 有两种方式能清空缓冲区：
 * 1）clear()方法会清空整个缓冲区。
 *
 * 2）compact()方法只会清除已经读过的数据。任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区
 *
 *
 * 写数据到Buffer有两种方式:
 * 1）从Channel写到Buffer。
 * 2）通过Buffer的put()方法写到Buffer里。
 * 从Channel写到Buffer的例子:
 * int bytesRead = inChannel.read(buf); // 从Channel(通道)读取到Buffer(缓冲区)中
 *
 * 通过put方法写Buffer的例子:
 * buf.put(127);
 * put方法有很多版本，允许你以不同的方式把数据写入到Buffer中。
 * 例如，写到一个指定的位置，或者把一个字节数组写入到Buffer。
 *
 * 从Buffer中读取数据有两种方式:
 * 1）从Buffer读取数据到Channel。
 *
 * 2）使用get()方法从Buffer中读取数据。
 *
 * 从Buffer读取数据到Channel的例子:
 * // 从Buffer中读取数据写入到通道中
 * int bytesWritten = inChannel.write(buf);
 *
 * 使用get()方法从Buffer中读取数据的例子:
 * byte aByte = buf.get();
 * get方法有很多版本，允许你以不同的方式从Buffer中读取数据。
 * 例如，从指定position读取，或者从Buffer中读取数据到字节数组。
 *
 *
 * 一旦读完Buffer中的数据，需要让Buffer准备好再次被写入。可以通过clear()或compact()方法来完成。
 * 如果调用的是clear()方法，position将被设回0，limit被设置成capacity的值。换句话说，Buffer被清空了。
 * Buffer中的数据并未清除，只是这些标记告诉我们可以从哪里开始往Buffer里写数据。如果Buffer中有一些未读的数据，
 * 调用clear()方法，数据将“被遗忘”，意味着不再有任何标记会告诉你哪些数据被读过，哪些还没有。
 *
 *
 * 如果Buffer中仍有未读的数据，且后续还需要这些数据，但是此时想要先写些数据，那么使用compact()方法。
 * compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。
 * limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据。
 *
 *
 *
 *
 *
 */
public abstract class Buffer {

    /**
     * The characteristics of Spliterators that traverse and split elements
     * maintained in Buffers.
     */
    static final int SPLITERATOR_CHARACTERISTICS =
        Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.ORDERED;

    // Invariants: mark <= position <= limit <= capacity

    //一个临时存放的位置下标。调用mark()会将mark设为当前的position的值，
    // 以后调用reset()会将position属性设置为mark的值。mark的值总是小于等于position的值，
    // 如果将position的值设的比mark小，当前的mark值会被抛弃掉。这些属性总是满足以下条件：
    private int mark = -1;//标记位置，reset时需要
    //读/写操作的当前下标。当使用buffer的相对位置进行读/写操作时，读/写会从这个下标进行，并在操作完成后，buffer会更新下标的值。
    private int position = 0;
    //在Buffer上进行的读写操作都不能越过这个下标。当写数据到buffer中时，limit一般和capacity相等，当读数据时，limit代表buffer中有效数据的长度。
    private int limit;
    //这个Buffer最多能放多少数据。capacity一般在buffer被创建的时候指定。
    private int capacity;

    // Used only by direct buffers
    // NOTE: hoisted here for speed in JNI GetDirectBufferAddress
    long address;

    // Creates a new buffer with the given mark, position, limit, and capacity,
    // after checking invariants.
    //
    Buffer(int mark, int pos, int lim, int cap) {       // package-private
        if (cap < 0)
            throw new IllegalArgumentException("Negative capacity: " + cap);
        this.capacity = cap;
        limit(lim);
        position(pos);
        if (mark >= 0) {
            if (mark > pos)
                throw new IllegalArgumentException("mark > position: ("
                                                   + mark + " > " + pos + ")");
            this.mark = mark;
        }
    }

    /**
     * Returns this buffer's capacity.
     *
     * @return  The capacity of this buffer
     */
    public final int capacity() {
        return capacity;
    }

    /**
     * Returns this buffer's position.
     *
     * @return  The position of this buffer
     */
    public final int position() {
        return position;
    }

    /**
     * Sets this buffer's position.  If the mark is defined and larger than the
     * new position then it is discarded.
     *
     * @param  newPosition
     *         The new position value; must be non-negative
     *         and no larger than the current limit
     *
     * @return  This buffer
     *
     * @throws  IllegalArgumentException
     *          If the preconditions on <tt>newPosition</tt> do not hold
     */
    public final Buffer position(int newPosition) {
        if ((newPosition > limit) || (newPosition < 0))
            throw new IllegalArgumentException();
        position = newPosition;
        if (mark > position) mark = -1;
        return this;
    }

    /**
     * Returns this buffer's limit.
     *
     * @return  The limit of this buffer
     */
    public final int limit() {
        return limit;
    }

    /**
     * Sets this buffer's limit.  If the position is larger than the new limit
     * then it is set to the new limit.  If the mark is defined and larger than
     * the new limit then it is discarded.
     *
     * @param  newLimit
     *         The new limit value; must be non-negative
     *         and no larger than this buffer's capacity
     *
     * @return  This buffer
     *
     * @throws  IllegalArgumentException
     *          If the preconditions on <tt>newLimit</tt> do not hold
     */
    public final Buffer limit(int newLimit) {
        if ((newLimit > capacity) || (newLimit < 0))
            throw new IllegalArgumentException();
        limit = newLimit;
        if (position > limit) position = limit;
        if (mark > limit) mark = -1;
        return this;
    }

    /**
     * Sets this buffer's mark at its position.
     *
     * @return  This buffer
     */

    /**
     * 通过调用Buffer.mark()方法，可以标记Buffer中的一个特定position。
     *之后可以通过调用Buffer.reset()方法恢复到这个position。例如：
     * buffer.mark();
     * //调用buffer读写方法，下面通过调用reset恢复到调用前的position位置。
     *buffer.reset(); // set position back to mark
     *
     * @return
     */
    public final Buffer mark() {
        mark = position;
        return this;
    }

    /**
     * Resets this buffer's position to the previously-marked position.
     *
     * <p> Invoking this method neither changes nor discards the mark's
     * value. </p>
     *
     * @return  This buffer
     *
     * @throws  InvalidMarkException
     *          If the mark has not been set
     */
    public final Buffer reset() {
        int m = mark;
        if (m < 0)
            throw new InvalidMarkException();
        position = m;
        return this;
    }

    /**
     * Clears this buffer.  The position is set to zero, the limit is set to
     * the capacity, and the mark is discarded.
     *
     * <p> Invoke this method before using a sequence of channel-read or
     * <i>put</i> operations to fill this buffer.  For example:
     *
     * <blockquote><pre>
     * buf.clear();     // Prepare buffer for reading
     * in.read(buf);    // Read data</pre></blockquote>
     *
     * <p> This method does not actually erase the data in the buffer, but it
     * is named as if it did because it will most often be used in situations
     * in which that might as well be the case. </p>
     *
     * @return  This buffer
     */
    /**
     * 把position设为0，把limit设为capacity，一般在把数据写入Buffer前调用。
     * @return
     */
    public final Buffer clear() {
        position = 0;
        limit = capacity;
        mark = -1;
        return this;
    }

    /**
     * Flips this buffer.  The limit is set to the current position and then
     * the position is set to zero.  If the mark is defined then it is
     * discarded.
     *
     * <p> After a sequence of channel-read or <i>put</i> operations, invoke
     * this method to prepare for a sequence of channel-write or relative
     * <i>get</i> operations.  For example:
     *
     * <blockquote><pre>
     * buf.put(magic);    // Prepend header
     * in.read(buf);      // Read data into rest of buffer
     * buf.flip();        // Flip buffer
     * out.write(buf);    // Write header + data to channel</pre></blockquote>
     *
     * <p> This method is often used in conjunction with the {@link
     * java.nio.ByteBuffer#compact compact} method when transferring data from
     * one place to another.  </p>
     *
     * @return  This buffer
     */

    /**
     * 1把limit设为当前position，把position设为0，一般在从Buffer读出数据前调用。
     *
     * buffer中的flip方法涉及到bufer中的Capacity,Position和Limit三个概念。其中Capacity在读写模式下都是固定的，就是我们分配的缓冲大小,
     * Position类似于读写指针，表示当前读(写)到什么位置,Limit在写模式下表示最多能写入多少数据，此时和Capacity相同，
     * 在读模式下表示最多能读多少数据，此时和缓存中的实际数据大小相同。
     * 在写模式下调用flip方法，那么limit就设置为了position当前的值(即当前写了多少数据),postion会被置为0，
     * 以表示读操作从缓存的头开始读。也就是说调用flip之后，读写指针指到缓存头部，并且设置了最多只能读出之前写入的数据长度
     * (而不是整个缓存的容量大小)。
     * @return
     */
    public final Buffer flip() {
        limit = position;
        position = 0;
        mark = -1;
        return this;
    }

    /**
     * Rewinds this buffer.  The position is set to zero and the mark is
     * discarded.
     *
     * <p> Invoke this method before a sequence of channel-write or <i>get</i>
     * operations, assuming that the limit has already been set
     * appropriately.  For example:
     *
     * <blockquote><pre>
     * out.write(buf);    // Write remaining data
     * buf.rewind();      // Rewind buffer
     * buf.get(array);    // Copy data into array</pre></blockquote>
     *
     * @return  This buffer
     */

    /**
     * 1.把position设为0，limit不变，一般在把数据重写入Buffer前调用。
     *
     * Buffer对象有可能是只读的，这时，任何对该对象的写操作都会触发一个ReadOnlyBufferException。
     * isReadOnly()方法可以用来判断一个Buffer是否只读
     *
     * Buffer.rewind()将position设回0，所以你可以重读Buffer中的所有数据。
     * limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。
     * @return
     */
    public final Buffer rewind() {
        position = 0;
        mark = -1;
        return this;
    }

    /**
     * Returns the number of elements between the current position and the
     * limit.
     *
     * @return  The number of elements remaining in this buffer
     */
    public final int remaining() {
        return limit - position;
    }

    /**
     * Tells whether there are any elements between the current position and
     * the limit.
     *
     * @return  <tt>true</tt> if, and only if, there is at least one element
     *          remaining in this buffer
     */
    public final boolean hasRemaining() {
        return position < limit;
    }

    /**
     * Tells whether or not this buffer is read-only.
     *
     * @return  <tt>true</tt> if, and only if, this buffer is read-only
     */
    public abstract boolean isReadOnly();

    /**
     * Tells whether or not this buffer is backed by an accessible
     * array.
     *
     * <p> If this method returns <tt>true</tt> then the {@link #array() array}
     * and {@link #arrayOffset() arrayOffset} methods may safely be invoked.
     * </p>
     *
     * @return  <tt>true</tt> if, and only if, this buffer
     *          is backed by an array and is not read-only
     *
     * @since 1.6
     */
    public abstract boolean hasArray();

    /**
     * Returns the array that backs this
     * buffer&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> This method is intended to allow array-backed buffers to be
     * passed to native code more efficiently. Concrete subclasses
     * provide more strongly-typed return values for this method.
     *
     * <p> Modifications to this buffer's content will cause the returned
     * array's content to be modified, and vice versa.
     *
     * <p> Invoke the {@link #hasArray hasArray} method before invoking this
     * method in order to ensure that this buffer has an accessible backing
     * array.  </p>
     *
     * @return  The array that backs this buffer
     *
     * @throws  ReadOnlyBufferException
     *          If this buffer is backed by an array but is read-only
     *
     * @throws  UnsupportedOperationException
     *          If this buffer is not backed by an accessible array
     *
     * @since 1.6
     */
    public abstract Object array();

    /**
     * Returns the offset within this buffer's backing array of the first
     * element of the buffer&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> If this buffer is backed by an array then buffer position <i>p</i>
     * corresponds to array index <i>p</i>&nbsp;+&nbsp;<tt>arrayOffset()</tt>.
     *
     * <p> Invoke the {@link #hasArray hasArray} method before invoking this
     * method in order to ensure that this buffer has an accessible backing
     * array.  </p>
     *
     * @return  The offset within this buffer's array
     *          of the first element of the buffer
     *
     * @throws  ReadOnlyBufferException
     *          If this buffer is backed by an array but is read-only
     *
     * @throws  UnsupportedOperationException
     *          If this buffer is not backed by an accessible array
     *
     * @since 1.6
     */
    public abstract int arrayOffset();

    /**
     * Tells whether or not this buffer is
     * <a href="ByteBuffer.html#direct"><i>direct</i></a>.
     *
     * @return  <tt>true</tt> if, and only if, this buffer is direct
     *
     * @since 1.6
     */
    public abstract boolean isDirect();


    // -- Package-private methods for bounds checking, etc. --

    /**
     * Checks the current position against the limit, throwing a {@link
     * BufferUnderflowException} if it is not smaller than the limit, and then
     * increments the position.
     *
     * @return  The current position value, before it is incremented
     */
    final int nextGetIndex() {                          // package-private
        if (position >= limit)
            throw new BufferUnderflowException();
        return position++;
    }

    final int nextGetIndex(int nb) {                    // package-private
        if (limit - position < nb)
            throw new BufferUnderflowException();
        int p = position;
        position += nb;
        return p;
    }

    /**
     * Checks the current position against the limit, throwing a {@link
     * BufferOverflowException} if it is not smaller than the limit, and then
     * increments the position.
     *
     * @return  The current position value, before it is incremented
     */
    final int nextPutIndex() {                          // package-private
        if (position >= limit)
            throw new BufferOverflowException();
        return position++;
    }

    final int nextPutIndex(int nb) {                    // package-private
        if (limit - position < nb)
            throw new BufferOverflowException();
        int p = position;
        position += nb;
        return p;
    }

    /**
     * Checks the given index against the limit, throwing an {@link
     * IndexOutOfBoundsException} if it is not smaller than the limit
     * or is smaller than zero.
     */
    final int checkIndex(int i) {                       // package-private
        if ((i < 0) || (i >= limit))
            throw new IndexOutOfBoundsException();
        return i;
    }

    final int checkIndex(int i, int nb) {               // package-private
        if ((i < 0) || (nb > limit - i))
            throw new IndexOutOfBoundsException();
        return i;
    }

    final int markValue() {                             // package-private
        return mark;
    }

    final void truncate() {                             // package-private
        mark = -1;
        position = 0;
        limit = 0;
        capacity = 0;
    }

    final void discardMark() {                          // package-private
        mark = -1;
    }

    static void checkBounds(int off, int len, int size) { // package-private
        if ((off | len | (off + len) | (size - (off + len))) < 0)
            throw new IndexOutOfBoundsException();
    }

}
