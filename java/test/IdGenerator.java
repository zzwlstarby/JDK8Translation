package java.test;

public class IdGenerator {
 public static void main(String[] args) {

  //这里两个参数都是1，表示1号机房和1号电脑【在分布式系统中，每个电脑知道自己所在的机房和编号】

    IdGenerator ig = new IdGenerator(1,1);

    //循环生成

    long result = -1;

    for(int i = 0;i<10;i++) {

      result = ig.nextId();

      System.out.println("id length=" + String.valueOf(result).length() + ", id=" +result+" , "+Long.toBinaryString(result));

    }
    String bainary = "101110000100001101011110110011100011110100000100001000000000000";
     System.out.println("64 length=" + bainary.length());

  }
      //定义属性 [机器码10位，如何分配成 机房码和电脑码，做为属性，这里默认都是5]

              private final long dataCenterBits = 5L; //机房码的位数

  private final long computerBits = 5L; //电脑码的位数

  //最后的序列码，默认从0开始

          private long sequence = 0L;

  //记录执行的最后时间，以毫秒为单位，默认初始化为-1L

          private long lastTimeStamp = -1L;



  //因为要做二进制运算，我们需要定义如下属性来记录每个部份所在的位置的偏移量

          private final long sequenceBits = 12; //序号占用12位

  private final long computerIdShift = sequenceBits; //电脑码的偏移量

  private final long dataCenterIdShift = computerIdShift + computerBits; //机房码的偏移量

  private final long timeStampShift = dataCenterIdShift + dataCenterBits; //时间戳的偏移量



  //根据机房码的位数，来计算出机房码最大值

          private final long MAX_DATA_CENTER = -1L ^ (-1L << dataCenterBits); //相当于 11111， 也就是 31

              private final long MAX_COMPUTER = -1L ^ (-1L << computerBits); //同上

  private final long SEQUENCE_MASK = -1L ^ (-1L << sequenceBits); // 为防止序列号溢出而准备的掩码，相当于 11111111 111



  //定义属性

          private long computerId; //电脑的id 【在分布式系统中，记录这个雪花号是由哪一台电脑生成的】

  private long dataCenterId; //机房的id 【在分布式系统中，记录这个雪花号是由哪一个中心机房里的电脑生成的】

  //构造

          public IdGenerator(long computerId, long dataCenterId) {

    //对参数的有效性进行判断，由于机房码和电脑码都是5位，所以，它们的值最大都不能超过31

    if(computerId > MAX_COMPUTER || computerId < 0) {

      throw new IllegalArgumentException(String.format("电脑编号不能大于 %d 或者小于 %d \n",MAX_COMPUTER,0));

    }

    if(dataCenterId > MAX_DATA_CENTER || dataCenterId < 0) {

      throw new IllegalArgumentException(String.format("机房编号不能大于 %d 或者小于 %d\n",MAX_DATA_CENTER, 0));

    }

    //赋值

    this.computerId = computerId;

    this.dataCenterId = dataCenterId;

  }

  /***************

   * 核心方法，利用雪花算法来获取一个唯一性的ID

   * @return

   */

          public synchronized long nextId() {

    //1.获取当前的系统时间

    long currTime = getCurrentTime();

    //2. 判断是否在同一个时间内的请求

    if(currTime == lastTimeStamp) {

      //2.1 sequence 要增1, 但要预防sequence超过 最大值4095，所以要 与 SEQUENCE_MASK 按位求与

      sequence = (sequence + 1) & SEQUENCE_MASK;

      //2.2 进一步判断，如果在同一个毫秒内，sequence达到了4096【1 0000 0000 0000】，则lastTime时间戳必需跳入下一个时间，因为同一个毫秒内

      //sequence只能产生4096个【0-4095】，当超过时，必需跳入下一个毫秒

      // 【此情况极少出现，但不可不防，这意味着1个毫秒内，JVM要执行此方法达到4096次，我这个电脑执行远达不到。】

      if(sequence == 0) {

        currTime = unitNextTime();

      }

    } else {

      //如果不是与lastTime一样，则表示进入了下一个毫秒，则sequence重新计数

      sequence = 0L;

    }

    //3. 把当前时间赋值给 lastTime, 以便下一次判断是否处在同一个毫秒内

    lastTimeStamp = currTime;

    //4. 依次把各个部门求出来并通过逻辑或 拼接起来

    return (this.lastTimeStamp << timeStampShift) |       //把当前系统时间 左移22位

        (this.dataCenterId << dataCenterIdShift) | //把机房编号 左移17位

        (this.computerId << computerIdShift) |   //把计算机号编号左移 12位

        this.sequence;                 //最后的序列号占12位，无需移动

  }

  /***************************************************

   * 等待毫秒数进入下一个时间

   * @return

   */

          private long unitNextTime() {

    //1.再次获取系统时间

    long timestamp = getCurrentTime();

    //2. 判断 lastTime与currentTime是否一样

    while(timestamp <= lastTimeStamp) {

      //2.1 继续获取系统时间，直到上面的条件不成立为止

      timestamp = getCurrentTime();

    }

    //3. 返回

    return timestamp;

  }

  /*******************************************************

   * 用来获取当前的系统时间，以毫秒为单位

   * @return

   */

          private long getCurrentTime() {

    return System.currentTimeMillis();

  }
}
