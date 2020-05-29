package JZOffer.DataStructure;

import java.util.Stack;

/**
 * @author lpx .
 * @create 2020-03-09-11:43 .
 * @description .
 */
public class Stack_1 {

    //利用两个栈实现队列
    // ｛栈：先进后出｝ ｛队列：先进先出｝

    static Stack<Integer> stack1 = new Stack<Integer>();
    static Stack<Integer> stack2 = new Stack<Integer>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            push(i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(pop());
        }
    }

    //入队列
    private static void push(Integer integer) {
        stack1.push(integer);
    }

    //弹出队列
    private static int pop() {
        //当栈2为空，且栈1不为空时
        //栈1弹出并压入栈2再弹出
        if (stack2.isEmpty()) {
            while (stack1.size() != 0) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }
}
