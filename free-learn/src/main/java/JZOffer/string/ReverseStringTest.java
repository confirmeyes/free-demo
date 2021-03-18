package JZOffer.string;

import org.junit.Test;

/**
 * @author WIN10 .
 * @create 2021-03-09-17:27 .
 * @description .
 */
public class ReverseStringTest {

    @Test
    public void test() {
        String str = "abcd";
        System.out.println(solve(str));
    }


    private String solve(String str) {
        // write code here
        StringBuffer sb = new StringBuffer();
        char[] strChar = str.toCharArray();
        for (int i = str.length() - 1; i >= 0; i--) {
            sb.append(strChar[i]);
        }
        return new String(sb);
    }
}
