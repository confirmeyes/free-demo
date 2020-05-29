package JZOffer.DataStructure;

import java.util.ArrayList;

/**
 * @author lpx .
 * @create 2020-03-09-11:43 .
 * @description .
 */
public class ListNode_2 {

    //输入一个链表，按链表从尾到头的顺序返回一个ArrayList。

    private static ArrayList<Integer> list = new ArrayList<Integer>();

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val, ListNode node) {
            this.val = val;
            this.next = node;
        }
    }


    public static void main(String[] args) {
        ListNode node4 = new ListNode(44, null);
        ListNode node3 = new ListNode(33, node4);
        ListNode node2 = new ListNode(22, node3);
        ListNode node1 = new ListNode(11, node2);

        ArrayList<Integer> resultList = printListFromTailToHead(node1);
        for (Integer integer : resultList) {
            System.out.println(integer);
        }
    }

    //用递归方法 尾端的元素第一个进行 add，则头部在list的最后面
    private static ArrayList<Integer> printListFromTailToHead(ListNode listNode) {

        if (listNode != null) {
            printListFromTailToHead(listNode.next);
            list.add(listNode.val);
        }
        return list;
    }

    //用list.add(index,value); 链表的尾端元素取到，放入list的0索引处。
    /*public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {

        ListNode temp = listNode;
        while (temp != null) {
            list.add(0, temp.val);
            temp = temp.next;
        }
        return list;
    }*/

}


