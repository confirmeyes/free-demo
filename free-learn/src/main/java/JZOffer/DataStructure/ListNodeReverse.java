package JZOffer.DataStructure;

/**
 * @author WIN10 .
 * @create 2021-03-09-16:24 .
 * @description .
 */
public class ListNodeReverse {

    //反转链表
    //输入一个链表，反转链表后，输出新链表的表头

    public static void main(String[] args) {
        ListNode node4 = new ListNode(44);
        ListNode node3 = new ListNode(33);
        ListNode node2 = new ListNode(22);
        ListNode head = new ListNode(11);
        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        //{11 , 22 , 33 , 44}

        ListNode listNode = ReverseList(head);
        System.out.println(listNode.toString());
    }

    static ListNode ReverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = null;
        ListNode next = null;
        while (head.next != null) {
            //保存头结点的 next || next = 22
            next = head.next;
            //反转节点 设置前置节点-->头结点的 next
            head.next = pre;
            //头节点存入pre
            pre = head;
            //头结点的 next 变为 头节点
            head = next;
        }
        head.next = pre;
        return head;
    }

}
