package test.java.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;

public class DatagramSocketTest {

    private static CountDownLatch latch = new CountDownLatch(1);


    public static void main(String[] args) throws Exception{
        DatagramSocketTest test = new DatagramSocketTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                latch.countDown();
                test.udpRecive();
            }
        }).start();

        latch.await();

        test.udpSend();
    }


    public void udpSend() {
        System.out.println("开始发送udpSend");
        DatagramSocket ds = null;
        try {
            //创建inetAddress对象封装接收端的地址
            InetAddress inet = InetAddress.getByName("192.168.1.3");
            //2.创建DatagramSocket对象，数据包的发送对象
            ds = new DatagramSocket();
            for (int i = 0; i < 100; i++) {
                String str = new String("hello wrold:" + i);
                //1.创建数据包对象，封装要发送的数据，接收端的ip，端口号
                byte[] bytes = str.getBytes();
                DatagramPacket dp = new DatagramPacket(bytes, bytes.length,inet,6000);
                //3.调用ds的send方法，发送数据包
                ds.send(dp);
            }

            //4.释放资源
            System.out.println("发送udpSend结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(ds != null) {
                ds.close();
            }
        }
    }


    public void udpRecive() {
        System.out.println("开始启动udpRevice");
        DatagramSocket socket = null;
        try {
            //创建数据包传输对象DatagramSocket绑定端口号
            socket = new DatagramSocket(6000);
            //创建字节数组来接收发过来的数据
            byte[] bytes = new byte[1024];
            //创建数据包对象DatagramPacket
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
            //调用数据包传输对象的receive方法来接收数据
            socket.receive(dp);

            //拆包
            //获取数据长度
            int length = dp.getLength();
            //获取客户端ip地址
            String ip = dp.getAddress().getHostAddress();
            //获取端口号
            int port = dp.getPort();
            System.out.println("客户端ip地址为："+ip+",端口号为："+port+",发送的内容为："+new String(bytes,0,length));


            socket.receive(dp);
           //拆包
            //获取数据长度
            int length1 = dp.getLength();
            //获取客户端ip地址
            String ip1 = dp.getAddress().getHostAddress();
            //获取端口号
            int port1 = dp.getPort();
            System.out.println("客户端ip地址为："+ip+",端口号为："+port+",发送的内容为："+new String(bytes,0,length));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(socket != null) {
                socket.close();
            }
        }
    }
}
