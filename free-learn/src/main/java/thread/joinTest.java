package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lpx .
 * @create 2019-12-18-17:11 .
 * @description .
 */
public class joinTest extends Thread {

    private String fight;

    private double price;

    private static List<String> infoList = new ArrayList<>();

    private joinTest(int name, String fight, double price) {
        super(String.valueOf(name));
        this.fight = fight;
        this.price = price;
    }

    @Override
    public void run() {
        infoList.add(getName() + "==" + fight + "==" + price);
    }

    private List<String> queryInfo() {
        return infoList;
    }


    public static void main(String[] args) {

        List<joinTest> threadList = new ArrayList<>();

        List<String> result = new ArrayList<>();


            threadList = IntStream.range(1, 4).mapToObj(xxx ->
                    new joinTest(xxx, "空客", 106.3)
            ).collect(Collectors.toList());



        threadList.forEach(Thread::start);

        threadList.forEach(f -> {
            try {
                f.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        threadList.stream().map(joinTest::queryInfo).forEach(strings -> {
            System.out.println(""+strings);
        });
       /* System.out.println("=====================================================");
        result.forEach(s -> {
            System.out.println(s);
        });*/

    }


}
