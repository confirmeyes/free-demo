import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lpx .
 * @create 2020-03-18-10:41 .
 * @description .
 */
public class userTest {

    public static void main(String[] args) {

        List<user> userList = new ArrayList<>();

        userList.add(new user(1, 108.546, 68.125));
        userList.add(new user(2, 105.546, 68.125));
        userList.add(new user(3, 106.546, 68.125));

        //List<String> strings = userList.stream().map(user -> "POINT(" + user.getLa() + " " + user.getLo() + ")").collect(Collectors.toList());

        //userList.forEach(user -> user.setLoa("POINT(" + user.getLa() + " " + user.getLo() + ")"));

        userList.stream().peek(user -> user.setLoa("POINT(" + user.getLa() + " " + user.getLo() + ")")).collect(Collectors.toList());

        userList.forEach(user -> System.out.println(user.toString()));

        for (int j = 0; j < 100; j++) {
            System.out.println( (int) ((Math.random() * 9 + 1) * 100000));
        }
    }
}
