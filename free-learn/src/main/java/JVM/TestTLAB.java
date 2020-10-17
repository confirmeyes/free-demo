package JVM;

/**
 * @author WIN10 .
 * @create 2020-10-15-15:48 .
 * @description .
 */

//-XX:-DoEscapeAnalysis -XX:-EliminateAllocations -XX:-UseTLAB -Xlog:c5_gc*
// 逃逸分析 标量替换 线程专有对象分配

public class TestTLAB {

    class User {
        int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    void alloc(int i) {
        new User(i, "name " + i);
    }

    public static void main(String[] args) {
        TestTLAB t = new TestTLAB();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000_0000; i++) t.alloc(i);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        //默认 571
        //去掉 逃逸分析 标量替换 线程专有对象分配 914  -XX:-DoEscapeAnalysis -XX:-EliminateAllocations -XX:-UseTLAB
        //去掉 线程本地分配 729
        //去掉 逃逸分析 676
        //去掉 标用替换 677
        //加上 逃逸分析 标量替换 线程专有对象分配 575 -XX:+DoEscapeAnalysis -XX:+EliminateAllocations -XX:+UseTLAB
        //只有 线程专有对象分配 565 -XX:+UseTLAB
        //只有 标量替换 583 -XX:+EliminateAllocations
        //只有 逃逸分析 566 -XX:+DoEscapeAnalysis
    }
}
