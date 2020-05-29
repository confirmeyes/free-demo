package JZOffer.Fibonacci;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lpx .
 * @create 2020-03-09-11:43 .
 * @description .
 */
public class Fibonacci_3 {


    //一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。


    public static void main(String[] args) {


        System.out.println(jumpFloorII(2));

        //System.out.println(2>>2);

    }


    public static int jumpFloorII(int number) {

        //位移运算符， <<  左移运算  转换为二进制数后，左移，高位舍弃，低位空位补零
        //           >>  右移运算                  右移，低位舍弃，高位空位补零
        //           >>> 不带符号的位移运算

        //f(n) = f(n-1) + f(n-2) + f(1);
        //f(n-1) =  f(n-2) + f(1);

        //f(n) = 2f(n-1);
        //1-1 2-2 3-4 4-8

        return number <= 0 ? 0 : 1 << (number - 1);
    }

}
