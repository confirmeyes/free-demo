package socket;

import entity.User;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author WIN10 .
 * @create 2020-08-10-11:24 .
 * @description .
 */
public class Server1 {

    public static void main(String[] args) throws Exception {

        //服务端需要使用serversocket来开放本地的端口
        ServerSocket serverSocket = new ServerSocket(1111);
        //需要接受client传输过来的数据，需要定义socket对象
        Socket server = serverSocket.accept();
        InputStream inputStream = server.getInputStream();

        //1. 使用DataInputStream readUTF方法
        /*DataInputStream dataInputStream = new DataInputStream(inputStream);
        String str = dataInputStream.readUTF();
        System.out.println("客户端传输的数据是：" + str);*/

        //2. 使用inputStream read byte[]
        /*byte[] buf = new byte[1024];
        int length = inputStream.read(buf);
        System.out.println("客户端传输的数据是：" + new String(buf,0,length));*/

        // 3. 传输对象数据 ObjectOutputStream
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        User user = (User) objectInputStream.readObject();
        System.out.println("欢迎你："+user.getName());

        //截断输入流
        //server.shutdownInput();


        //---------------------返回客户端数据----------------------------------
        OutputStream outputStream = server.getOutputStream();
        outputStream.write("收到".getBytes());

        //关闭所有的流操作
        //dataInputStream.close();
        objectInputStream.close();
        inputStream.close();
        server.close();
        serverSocket.close();

    }
}
