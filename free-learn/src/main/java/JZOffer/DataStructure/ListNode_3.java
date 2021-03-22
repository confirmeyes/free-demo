package JZOffer.DataStructure;


/**
 * @author WIN10 .
 * @create 2020-04-28-15:37 .
 * @description .
 */
public class ListNode_3 {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val, ListNode node) {
            this.val = val;
            this.next = node;
        }
    }


    // 在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，
    // 重复的结点不保留，返回链表头指针。 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5

    public static void main(String[] args) {
        ListNode node5 = new ListNode(33, null);
        ListNode node4 = new ListNode(33, node5);
        ListNode node3 = new ListNode(22, node4);
        ListNode node2 = new ListNode(11, node3);
        ListNode node1 = new ListNode(11, node2);

        ListNode listNode = deleteDuplication(node1);
        System.out.println(listNode.val);


    }

    private static ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null || pHead.next == null) {
            return pHead;
        }
        // 自己构建辅助头结点
        ListNode head = new ListNode(Integer.MIN_VALUE, pHead);
        ListNode pre = head;
        ListNode next = head.next;
        while (next != null) {
            if (next.next != null && next.val == next.next.val) {
                //如果相同 一直向前比对
                while (next.next != null && next.val == next.next.val) {
                    next = next.next;
                }
                //退出循环，重复的结点不保留，进入下一个节点
                pre.next = next.next;
            } else {
                //没有重复， 进入下一个节点
                pre = next;
                next = next.next;
            }
        }
        return head.next;
    }


   /* private static ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null || pHead.next == null) {
            return pHead;
        }
        // 自己构建辅助头结点
        ListNode head = new ListNode(Integer.MIN_VALUE, pHead);
        ListNode pre = head;
        ListNode cur = head.next;
        while (cur != null) {
            if(cur.next != null && cur.next.val == cur.val){
                // 相同结点一直前进
                while(cur.next != null && cur.next.val == cur.val){
                    cur = cur.next;
                }
                // 退出循环时，cur 指向重复值，也需要删除，而 cur.next 指向第一个不重复的值
                // cur 继续前进
                cur = cur.next;
                // pre 连接新结点
                pre.next = cur;
            }else{
                pre = cur;
                cur = cur.next;
            }
        }
        return head.next;
    }*/
}
