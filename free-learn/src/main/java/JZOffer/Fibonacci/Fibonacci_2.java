package JZOffer.Fibonacci;

/**
 * @author lpx .
 * @create 2020-03-09-11:43 .
 * @description .
 */
public class Fibonacci_2 {

    //一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
    //本质上是 斐波那契数列

    public static void main(String[] args) {
        System.out.println(JumpFloor(3));

    }
    //1-1 2-2 3-3 4-5

    private static int JumpFloor(int target) {
        int a = 1, b = 1;
        while (target-- > 0) {
            a = a + b;
            b = a - b;
        }
        return b;
    }
}
