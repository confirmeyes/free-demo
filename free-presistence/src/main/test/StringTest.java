/**
 * @author WIN10 .
 * @create 2020-05-07-14:44 .
 * @description .
 */
public class StringTest {
    public static void main(String[] args) {
        ///method(null);
        String a = "-63";
        System.out.println(Integer.parseInt(a));


    }

    public static void method(String param) {
        switch (param) {
            // 肯定不是进入这里
            case "sth":
                System.out.println("it's sth");
                break;
            // 也不是进入这里
            case "null":
                System.out.println("it's null");
                break;
            // 也不是进入这里
            default:
                System.out.println("default");
        }
    }
}
