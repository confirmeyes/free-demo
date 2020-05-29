package JZOffer.DataStructure;

/**
 * @author WIN10 .
 * @create 2020-05-19-16:26 .
 * @description .
 */
public class ListNode_5 {

    //输入一个链表，输出该链表中倒数第k个结点。

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val, ListNode node) {
            this.val = val;
            this.next = node;
        }
    }

    public static void main(String[] args) {
        //1,{1,2,3,4,5}

        ListNode node5 = new ListNode(5, null);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);

        System.out.println(FindKthToTail(node1, 0).val);
    }

    private static ListNode FindKthToTail(ListNode head, int k) {

        ListNode slow, fast = head;
        if (head == null || k == 0) {
            return null;
        }
        for (int x = 1; x < k; x++) {
            if (fast.next == null) {
                return null;
            }
            if (x == k) {
                fast = fast;
            } else {
                fast = fast.next;
            }
        }
        slow = head;
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;

    }


}
