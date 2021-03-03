import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author WIN10 .
 * @create 2021-03-03-15:49 .
 * @description .
 */
public class ArrayTest {


    //（1） 任给一个数组，其中只有一个元素是单独出现，其他是成对出现，输出单独的元素。
    // 例如： 输入： {2，2，1，1，4，4，7}
    // 输出：7

    @Test
    public void ArrayTest_1() {

        int[] provide = {2, 2, 1, 1, 4, 7};
        Map<Integer, Integer> map = new HashMap<>();

        for (int index : provide) {
            if (map.keySet().contains(index)) {
                map.put(index, map.get(index) + 1);
            } else {
                map.put(index, 1);
            }
        }
        for (Integer intValue : map.keySet()) {
            if (map.get(intValue) == 1) {
                System.out.println(intValue);
            }
        }

    }
}
