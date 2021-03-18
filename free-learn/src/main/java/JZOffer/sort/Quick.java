package JZOffer.sort;

import org.junit.Test;

/**
 * @author WIN10 .
 * @create 2021-03-09-16:21 .
 * @description .
 */
public class Quick {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     * 将给定数组排序
     */

    @Test
    public void quickSort() {

        int[] arr = {1, 5, 4, 3, 8, 9};
        System.out.println(arr.length);
        quickSort(arr, 0, arr.length - 1);
        for (int value : arr){
            System.out.print(value + "  ");
        }
    }

    private void quickSort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int standard = arr[start];
        int i = start;
        int j = end;
        while (i < j) {
            while (j > i && arr[j] > standard) {
                j--;
            }
            while (i < j && arr[i] <= standard) {
                i++;
            }
            swap(arr, i, j);
        }
        swap(arr, i, start);

        quickSort(arr, start, i - 1);
        quickSort(arr, i + 1, end);
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

