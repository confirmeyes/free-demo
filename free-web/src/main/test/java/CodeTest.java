import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 * @author WIN10 .
 * @create 2020-07-31-17:54 .
 * @description .
 */
public class CodeTest {

    @Test
    void simple(){
        int value = HttpStatus.OK.value();
        System.out.println(value);
        Assertions.assertEquals(200,value,"断言201");
    }
}
