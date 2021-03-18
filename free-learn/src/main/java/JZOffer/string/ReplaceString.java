package JZOffer.string;

/**
 * @author lpx .
 * @create 2020-03-09-11:43 .
 * @description .
 */
public class ReplaceString {

    //给定一个字符串，将其中的空格替换为%20

    public static void main(String[] args) {
        StringBuffer string = new StringBuffer(" wo ");
        //System.out.println(replaceSpace(string));
        System.out.println(replaceSpaceByForeach(string));
    }

    //String方法实现
    private static String replaceSpace(StringBuffer str) {
        return str.toString().replaceAll(" ", "%20");
    }

    private static String replaceSpaceByForeach(StringBuffer str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == ' ') {
                stringBuffer.append("%20");
            } else {
                stringBuffer.append(ch);
            }
        }
        return stringBuffer.toString();
    }

}
