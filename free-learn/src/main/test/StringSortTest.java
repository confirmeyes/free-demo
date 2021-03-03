import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author WIN10 .
 * @create 2021-03-03-16:17 .
 * @description .
 */
public class StringSortTest {

    // 华为OD 机试题库 https://smartsi.blog.csdn.net/category_9264174_2.html

    // 给一个只包含大写英文字母的字符串S，要求你给出S重新排列的所有不同的排列数
    // 如：S是ABA，则不同的排列有三种 ABA AAB BAA 三种
    // 3*2*1 / 2*1*1 / 1
    // ABCDEFGHHA 907200种


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入字符串：");
        while (true) {
            char[] chars = scanner.nextLine().toCharArray();

            Map<Character, Integer> map = new HashMap<Character, Integer>();
            for (char ch : chars) {
                if (map.keySet().contains(ch)) {
                    map.put(ch, map.get(ch) + 1);
                } else {
                    map.put(ch, 1);
                }
            }
            System.out.println(JSONObject.toJSONString(map));

            int allSort = SortOne(chars.length);
            for (char key : map.keySet()) {
                allSort = allSort / SortOne(map.get(key));
            }
            System.out.println(allSort);

        }
    }

    static int SortOne(int charsnum) {
        if (charsnum == 1) {
            return 1;
        }
        return charsnum * SortOne(charsnum - 1);
    }

}
