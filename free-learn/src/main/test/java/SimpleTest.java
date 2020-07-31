import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 * @author WIN10 .
 * @create 2020-07-31-16:52 .
 * @description .
 */
public class SimpleTest {

    @Test
    void simple(){
        int value = HttpStatus.OK.value();
        System.out.println(value);
        Assertions.assertEquals(value,200,"断言200");
    }
}
