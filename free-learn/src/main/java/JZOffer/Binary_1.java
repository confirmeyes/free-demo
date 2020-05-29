package JZOffer;

/**
 * @author lpx .
 * @create 2020-03-09-11:43 .
 * @description .
 */
public class Binary_1 {

    //输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。

    //15--> 8421 --> 1111 , 当15-1时，变为1110, 1110&1111 = 1110, 刚好最右边少一个1,
    //利用此原理：n&(n-1) = 二进制最右位减一个1 计算二进制中1的个数

    public static void main(String[] args) {

        //-3 --> -11 , 补码：30个1 + 01 ， 一共31个1
        System.out.println(NumberOf1(-3));

    }

    public static int NumberOf1(int n) {

        int x = 0;

        while (n != 0) {
            n = n & (n - 1);
            x++;
        }

        return x;
    }
}
