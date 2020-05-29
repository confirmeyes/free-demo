package JVM;


/**
 * @author WIN10 .
 * @create 2020-05-09-15:31 .
 * @description .
 */
public class FieldsTest {

    private Integer id;

    private Integer id() {
        return id;
    }

    public static void main(String[] args) {
        new FieldsTest().id();
    }

}
