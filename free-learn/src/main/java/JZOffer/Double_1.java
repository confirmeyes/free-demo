package JZOffer;

import javax.swing.tree.TreeNode;

/**
 * @author lpx .
 * @create 2020-03-09-11:43 .
 * @description .
 */
public class Double_1 {

    public static void main(String[] args) {

        //Double解析

        String text = "199";
        try {
            text = text.concat(".5");
            double decimal = Double.parseDouble(text);
            System.out.println("Double解析字符串后：" + decimal);
            text = Double.toString(decimal);
            System.out.println("格式化不变："+text);
            double doubleValue = Double.valueOf(text).doubleValue();
            int status = (int) Math.ceil(doubleValue);
            System.out.println(status);
        } catch (NumberFormatException num) {
            num.printStackTrace();
        }

    }
}
