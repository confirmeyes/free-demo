package JVM;

/**
 * @author lpx .
 * @create 2020-04-20-17:39 .
 * @description .
 */


public class T03_SizeOfAnObject {


    public static void main(String[] args) {

       // System.out.println(ObjectSizeAgent.sizeOf(new Object()));
       //System.out.println(ObjectSizeAgent.sizeOf(new int[]{}));
       System.out.println(ObjectSizeAgent.sizeOf(new P()));
    }

    private static class P {
        //8 _markword
        //4 _oop指针
        int id;         //4
        String name;    //4
        int age;        //4

        byte b1;        //1
        byte b2;        //1

        Object o;       //4
        byte b3;        //1

    }
}
