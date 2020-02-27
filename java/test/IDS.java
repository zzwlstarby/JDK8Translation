package java.test;

import com.google.common.collect.ImmutableList;
import com.infra.tools.*;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class IDS implements Constant {

    private static final IdWorker idWorker;

    static {
        int workerId = Math.abs(NetUtil.getLocalMac().hashCode()) % 31;
        int dataCenterId = (int) ((Math.abs(NetUtil.getLocalHost().hashCode()) + SystemUtil.getPid()) % 31);
        idWorker = new IdWorker(workerId, dataCenterId, workerId + dataCenterId);
//        idWorker = new IdWorker();
    }

    /**
     * 秒
     *
     * @param n
     * @return
     */
    public static synchronized Long secondId(int n) {
        Long ret = null;

        LOCK.lock();
        try {
            ret = System.currentTimeMillis();
            ThreadUtil.SET_SECONDS(n);
        } finally {
            LOCK.unlock();
        }
        return ret;
    }

    /**
     * 毫秒
     *
     * @param n
     * @return
     */
    public static synchronized Long millisecondsId(int n) {
        Long ret = null;

        LOCK.lock();
        try {
            ret = System.currentTimeMillis();
            ThreadUtil.SET_MILLISECONDS(n);
        } finally {
            LOCK.unlock();
        }
        return ret;
    }

    /**
     * 纳秒
     *
     * @param n
     * @return
     */
    public static synchronized Long microsecondId(int n) {
        Long ret = null;

        LOCK.lock();
        try {
            ret = System.currentTimeMillis();
            ThreadUtil.SET_NANOSECONDS(n);
        } finally {
            LOCK.unlock();
        }
        return ret;
    }

    /**
     * 微秒
     *
     * @param n
     * @return
     */
    public static synchronized Long millisecondId(int n) {
        Long ret = null;

        LOCK.lock();
        try {
            ret = System.currentTimeMillis();
            ThreadUtil.SET_MICROSECONDS(n);
        } finally {
            LOCK.unlock();
        }
        return ret;
    }

    /**
     * 分钟
     *
     * @param n
     * @return
     */
    public static synchronized Long millisId(int n) {
        Long ret = null;

        LOCK.lock();
        try {
            ret = System.currentTimeMillis();
            ThreadUtil.SET_MINUTES(n);
        } finally {
            LOCK.unlock();
        }
        return ret;
    }

    /**
     * 返回随机ID.
     *
     * @return
     */
    public static long randomId() {
        return random.nextLong();
    }

    public static String randomLength(List<?> list, int length) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < length; i++) {
            str.append(list.get(random.nextInt(list.size())));
        }
        return str.toString();

    }

    /**
     * 返回随机名称, prefix字符串+5位随机数字.
     *
     * @param prefix
     * @return
     */
    public static String randomName(String prefix) {
        return prefix + random.nextInt(10000);
    }

    /**
     * 从输入list中随机返回一个对象.
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T randomOne(List<T> list) {
        Collections.shuffle(list);
        return list.get(0);
    }

    /**
     * 从输入list中随机返回随机个对象.
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> randomSome(List<T> list) {
        int size = random.nextInt(list.size());
        if (size == 0) {
            size = 1;
        }
        return randomSome(list, size);
    }

    /**
     * 从输入list中随机返回n个对象.
     *
     * @param list
     * @param n
     * @param <T>
     * @return
     */
    public static <T> List<T> randomSome(List<T> list, int n) {
        Collections.shuffle(list);
        return list.subList(0, n);
    }

    /**
     * @return
     */
    public static String dataAppendRandom() {
        StringBuffer param = new StringBuffer(yymmdd.format(new Date()));
        param.append(randomCharNUmber(6));
        return param.toString();
    }

    /**
     * 基于Base62编码的SecureRandom随机生成bytes.
     *
     * @param length
     * @return
     */
    public static String randomBase62(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return EncodeUtils.encodeBase62(randomBytes);
    }

    /**
     * @return
     */
    public static Boolean randomBoolean() {
        return random.nextBoolean();
    }

    /**
     * @param length
     * @return
     */
    public static String randomChar(int length) {

        return randomLength(CHARS_EQUENCE, length);
    }

    /**
     * @param length
     * @return
     */
    public static String randomCharNUmber(int length) {

        return randomLength(CHAR_NUMBER_SEQUENCE, length);
    }

    /**
     * @return
     */
    public static Double randomDouble() {
        return random.nextDouble();
    }

    /**
     * @param immu
     * @param length
     * @return
     */
    public static String randomLength(ImmutableList<Character> immu, int length) {
        StringBuffer sb = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            sb.append(immu.get(randomtInt(immu.size())));
        }
        return sb.toString();
    }

    /**
     * 使用SecureRandom随机生成Long.
     *
     * @return
     */
    public static Long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * @param length
     * @return
     */
    public static String randomNumber(int length) {

        return randomLength(NUMBER_SEQUENCE, length);
    }

    /**
     * @return
     */
    public static Float randomtFloat() {

        return random.nextFloat();
    }

    public static IntStream randomtInts(long streamSize, int randomNumberOrigin, int randomNumberBound) {
        return random.ints(streamSize, randomNumberOrigin, randomNumberBound);

    }

    public static LongStream randomtLongs(long streamSize, long randomNumberOrigin, long randomNumberBound) {
        return random.longs(streamSize, randomNumberOrigin, randomNumberBound);

    }

    public static DoubleStream randomtDoubles(long streamSize, double randomNumberOrigin, double randomNumberBound) {
        return random.doubles(streamSize, randomNumberOrigin, randomNumberBound);

    }

    /**
     * @return
     */
    public static Float randomtFloat(int max) {

        return randomtInt(max) + randomtFloat();
    }

    /**
     * @return
     */
    public static Integer randomtInt() {
        return random.nextInt();
    }

    /**
     * @param max
     * @return
     */
    public static Integer randomtInt(int max) {
        return random.nextInt(max);
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     *
     * @return
     */
    public static String uuid2() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * UUID TO HashCode
     *
     * @return
     */
    public static Integer uuidHashCode() {
        return System.identityHashCode(uuid2());
    }

    /**
     * @return 全局ID（Mac+IP+时间）
     */
    public static Long uniqueID() {
        return idWorker.getId();
    }

    /**
     * 生成6位随机字母数字组合
     *
     * @param length
     * @return
     */
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();
        //length为几位密码 
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字  
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }


//    public static void main(String[] args) {
//        long id = IDS.uniqueID();
//        System.out.println("id = " + id);
//
//        String mac = SystemUtil.getMac();
//        System.out.println(mac);
//    }


}
