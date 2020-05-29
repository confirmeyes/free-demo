package JZOffer.DataStructure;

/**
 * @author lpx .
 * @create 2020-03-09-11:43 .
 * @description .
 */
public class ListNode_1 {

    //反转链表
    //输入一个链表，反转链表后，输出新链表的表头

    public static void main(String[] args) {
        ListNode_2.ListNode node4 = new ListNode_2.ListNode(44, null);
        ListNode_2.ListNode node3 = new ListNode_2.ListNode(33, node4);
        ListNode_2.ListNode node2 = new ListNode_2.ListNode(22, node3);
        ListNode_2.ListNode head = new ListNode_2.ListNode(11, node2);

        ListNode_2.ListNode listNode = ReverseList(head);
    }

    public static ListNode_2.ListNode ReverseList(ListNode_2.ListNode head) {

        // 判断链表为空或长度为1的情况
        if(head == null || head.next == null){
            return head;
        }
        // 当前节点的前一个节点
        ListNode_2.ListNode pre = null;
        // 当前节点的下一个节点
        ListNode_2.ListNode next = null;
        while (head != null){
            //    1.找到下一个节点
            next = head.next;
            //    2.进行指针反转，当前节点指向前一个节点
            head.next = pre;
            //    3.设置pre前一个节点设为当前head节点
            pre = head;
            //    4.把head移到下一个节点
            head = next;
        }
        return pre;
    }

}
