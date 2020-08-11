package stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WIN10 .
 * @create 2020-08-11-16:01 .
 * @description .
 */
public class LambdaDemo {

    @Test
    public void listTest() {
        List<String> stringList = Arrays.asList("sql", "java", "php", "network");
        stringList.sort((a, b) -> b.length() - a.length());
        stringList.forEach(System.out::println);
    }

    @Test
    public void listTest2() {
        List<String> stringList = Arrays.asList("sql", "java", "php", "network");
        List<String> collect = stringList.stream().filter(str -> str.length() > 3).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }
}
