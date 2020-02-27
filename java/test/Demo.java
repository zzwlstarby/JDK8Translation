package java.test;


public class Demo {

    public static void main(String[] args) {
        long id = IDS.uniqueID();
        System.out.println("id:" + id);
        System.out.println("id length:" + String.valueOf(id).length());
        String b =  Long.toBinaryString(id);
        System.out.println("二进制:" + b +", 长度=" + b.length());
    }
}
