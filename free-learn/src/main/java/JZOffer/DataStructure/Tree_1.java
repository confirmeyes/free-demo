package JZOffer.DataStructure;

import java.util.Arrays;

/**
 * @author lpx .
 * @create 2020-03-09-11:43 .
 * @description .
 */
public class Tree_1 {

    //输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。
    // 假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
    // 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。

    //前序遍历：｛1.访问根节点 2.左节点 3.右节点｝
    //中序遍历：｛1.左节点 2.访问根节点 3.右节点｝
    //后序遍历：｛1.左节点 2.右节点 3.访问根节点｝
    // 根据前序遍历根节点：1 ，左节点：247 , 右节点：5386

    //数具有数组的查询优势（二分查找法,时间复杂度O(logN)），链表的插入优势（时间复杂度O(1)）,链表查询需要遍历，时间复杂度O(N)

    public static void main(String[] args) {
        TreeNode treeNode = reConstructBinaryTree(pre, in);
        System.out.println("----中序------");
        midForeach(treeNode);
        System.out.println("----前序------");
        preForeach(treeNode);
        System.out.println("----后序------");
        postForeach(treeNode);
    }

    private static int[] pre = {1, 2, 4, 7, 3, 5, 6, 8};
    private static int[] in = {4, 7, 2, 1, 5, 3, 8, 6};

    //247 472

    /**
     * 重建二叉树
     *
     * @param pre
     * @param in
     * @return
     */
    private static TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        if (pre.length == 0 || in.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(pre[0]);
        // 在中序中找到前序的根
        for (int i = 0; i < in.length; i++) {
            if (in[i] == pre[0]) {
                // 左子树，注意 copyOfRange 函数，左闭右开
                root.left = reConstructBinaryTree(
                        Arrays.copyOfRange(pre, 1, i + 1), Arrays.copyOfRange(in, 0, i)
                );
                // 右子树，注意 copyOfRange 函数，左闭右开
                root.right = reConstructBinaryTree(Arrays.copyOfRange(
                        pre, i + 1, pre.length), Arrays.copyOfRange(in, i + 1, in.length)
                );
                break;
            }
        }
        return root;
    }


    /**
     * 二叉树前序遍历
     *
     * @param treeNode
     */
    private static void preForeach(TreeNode treeNode) {
        if (treeNode != null) {
            System.out.print(treeNode.val);
            preForeach(treeNode.left);
            preForeach(treeNode.right);
        }
    }


    /**
     * 二叉树中序遍历
     *
     * @param treeNode
     */
    private static void midForeach(TreeNode treeNode) {
        if (treeNode != null) {
            midForeach(treeNode.left);
            System.out.print(treeNode.val);
            midForeach(treeNode.right);
        }
    }


    /**
     * 二叉树后序遍历
     *
     * @param treeNode
     */
    private static void postForeach(TreeNode treeNode) {
        if (treeNode != null) {
            postForeach(treeNode.left);
            postForeach(treeNode.right);
            System.out.print(treeNode.val);
        }
    }


}
