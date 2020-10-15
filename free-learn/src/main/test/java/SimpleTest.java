import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author WIN10 .
 * @create 2020-07-31-16:52 .
 * @description .
 */
public class SimpleTest {

    @Test
    void simple() {
        int value = HttpStatus.OK.value();
        System.out.println(value);
        Assertions.assertEquals(value, 200, "断言200");
    }

    public static void main(String[] args) {
        List<String> stringList1 = new ArrayList<>();
        List<String> stringList2 = new ArrayList<>();
        List<String> stringList3 = new ArrayList<>();


        stringList1.add("1");
        stringList1.add("1");
        stringList1.add("1");
        stringList1.add("1");
        stringList2.add("2");
        stringList2.add("2");
        stringList2.add("2");
        stringList2.add("2");

        stringList3.add("456");
        stringList3.add("789");

       // List<Stream<String>> collect = Stream.of(stringList1.stream(), stringList2.stream(), stringList3.stream()).collect(Collectors.toList());
        List<String> collectMap = Stream.concat(stringList1.stream(), stringList2.stream()).collect(Collectors.toList());
        List<String> collect = Stream.concat(stringList3.stream(), collectMap.stream()).collect(Collectors.toList());
        for (String str : collect) {
            System.out.println(str);
        }
    }
}
