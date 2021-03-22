import org.junit.Test;

/**
 * @author WIN10 .
 * @create 2021-03-08-17:33 .
 * @description .
 */
public class IntegerTest {


    @Test
    public void test() {

        //对象间比较是 对比内存地址
        Integer a = new Integer(3);
        Integer b = 3; // 将3自动装箱成Integer类型
        int c = 3;
        System.out.println(a == b); // false 两个引用没有引用同一对象

        // true a自动拆箱成int类型再和c比较
        System.out.println(a == c); // true
        System.out.println(b == c); // true

        Integer a1 = 127;
        Integer b1 = 127;
        System.out.println(a1 == b1); // true

        Integer a2 = 129;
        Integer b2 = 129;
        System.out.println(a2 == b2); // true
    }
}
