package rpc;

import Service.IUserService;
import entity.User;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @author lpx .
 * @create 2020-03-08-14:43 .
 * @description .
 */
public class stub {

    public static IUserService getStub() {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Socket socket = new Socket("172.28.208.1", 8000);
                OutputStream ops = socket.getOutputStream();
                //java自带的序列化协议 Serializable
                ObjectOutputStream oops = new ObjectOutputStream(ops);

                String methodName = method.getName();
                Class[] parameterTypes = method.getParameterTypes();

                oops.writeUTF(methodName);
                oops.writeObject(parameterTypes);
                oops.writeObject(args);
                oops.flush();

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                int id = dis.readInt();
                String name = dis.readUTF();
                User user = new User(id, name);
                user.setRemark(dis.readUTF());

                oops.close();
                socket.close();
                return user;
            }
        };
        Object o = Proxy.newProxyInstance(IUserService.class.getClassLoader(), new Class[]{IUserService.class}, invocationHandler);
        return (IUserService) o;
    }

}
