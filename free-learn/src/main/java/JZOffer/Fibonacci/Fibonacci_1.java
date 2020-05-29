package JZOffer.Fibonacci;

/**
 * @author lpx .
 * @create 2020-03-09-11:43 .
 * @description .
 */
public class Fibonacci_1 {

    //菲波那契数列--JAVA实现

    public static void main(String[] args) {
        System.out.println(Fibonacci(6));
        System.out.println(Fibonacci2(3));
    }

    // 1.递归法
    // 斐波那契数列的标准公式为：F(1)=1，F(2)=1, F(n)=F(n-1)+F(n-2)（n>=3，n∈N*）
    //时间复杂度：  空间复杂度：
    private static int Fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return Fibonacci(n - 1) + Fibonacci(n - 2);
    }

    // 2.避免递归造成的调用栈消耗
    private static int Fibonacci2(int n) {
        int a = 0;
        int b = 1;
        // n 次循环
        while (n-- > 0) {
            b = a + b;
            a = b - a;
        }
        return a;
    }
}
