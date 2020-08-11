package socket;

import entity.User;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author WIN10 .
 * @create 2020-08-10-11:24 .
 * @description .
 */
public class Client1 {

    public static void main(String[] args) throws Exception {

        Socket client = new Socket("localhost", 1111);
        OutputStream outputStream = client.getOutputStream();

        // 1. 使用DataOutputStream writeUTF 进行String传输
        //DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        //dataOutputStream.writeUTF("hello");

        // 2. outputStream直接write byte[]
        //outputStream.write("hello".getBytes());

        // 3. 传输对象数据 ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        User user = new User(1, "111");
        objectOutputStream.writeObject(user);


        //---------------------服务端返回数据----------------------------------

        InputStream inputStream = client.getInputStream();
        byte[] bytes = new byte[1024];
        int length = inputStream.read(bytes);
        System.out.println("服务端的响应数据是：" + new String(bytes, 0, length));

        //关闭流操作
        // dataOutputStream.close();
        objectOutputStream.close();
        inputStream.close();
        outputStream.close();
        client.close();

    }
}
