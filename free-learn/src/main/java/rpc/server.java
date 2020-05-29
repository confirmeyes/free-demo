package rpc;

import Service.IUserService;
import ServiceImpl.userServiceImpl;
import entity.User;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lpx .
 * @create 2020-03-08-14:43 .
 * @description .
 */
public class server {

    private static boolean running = true;

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8000);

        while (running) {
            Socket socket = serverSocket.accept();
            process(socket);
            socket.close();
        }
        serverSocket.close();
    }

    private static void process(Socket s) throws Exception {

        InputStream inputStream = s.getInputStream();
        OutputStream outputStream = s.getOutputStream();
        //接收序列化输入流
        ObjectInputStream oops = new ObjectInputStream(inputStream);
        //输出数据流
        DataOutputStream dos = new DataOutputStream(outputStream);

        String methodName = oops.readUTF();
        Class[] parameterTypes = (Class[]) oops.readObject();
        Object[] args = (Object[]) oops.readObject();

        IUserService service = new userServiceImpl();
        //反射
        Method method = service.getClass().getMethod(methodName,parameterTypes);
        User user = (User) method.invoke(service,args);

        dos.writeInt(user.getId());
        dos.writeUTF(user.getName());
        dos.writeUTF("远程方法调用成功！！");
        dos.flush();
    }
}
