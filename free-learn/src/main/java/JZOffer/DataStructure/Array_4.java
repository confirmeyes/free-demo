package JZOffer.DataStructure;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author WIN10 .
 * @create 2020-05-20-17:30 .
 * @description .
 */
public class Array_4 {

    // 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
    // 所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。

    public static void main(String[] args) {
        int[] array = {1, 5, 2, 4, 6, 9, 7, 8};
        int[] result = reOrderArray(array);
        for (int x : result) {
            System.out.print(" " + x);
        }
    }

    private static int[] reOrderArray(int[] array) {

        //队列 先进先出 , 空间换时间
        Queue<Integer> oddQueue = new LinkedList<Integer>();
        Queue<Integer> evenQueue = new LinkedList<Integer>();
        for (int x : array) {
            if (checkData(x)) {
                oddQueue.offer(x);
            } else {
                evenQueue.offer(x);
            }
        }
        int[] result = new int[8];
        int i = 0;
        while (!oddQueue.isEmpty()) {
            result[i++] = oddQueue.poll();
        }
        while (!evenQueue.isEmpty()) {
            result[i++] = evenQueue.poll();
        }

        return result;

    }

    private static boolean checkData(int data) {
        //位运算 奇数true 偶数false
        if ((data & 1) == 1) {
            return true;
        }
        return false;
    }

}
