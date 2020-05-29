package jmh;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author lpx .
 * @create 2020-03-03-16:15 .
 * @description .
 */

@BenchmarkMode(Mode.Throughput) // 吞吐量
@OutputTimeUnit(TimeUnit.MILLISECONDS) // 结果所使用的时间单位
@State(Scope.Thread) // 每个测试线程分配一个实例
@Fork(2) // Fork进行的数目
@Warmup(iterations = 4) // 先预热4轮
@Measurement(iterations = 10) // 进行10轮测试
public class ParallelStream {

    static List<Integer> nums = new ArrayList<>();

    static {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) nums.add(10000 + random.nextInt(1000));
    }

    public static void foreach() {
        nums.forEach(n -> isPrime(n));
    }

    public static void parallel() {
        nums.parallelStream().forEach(ParallelStream::isPrime);
    }

    static boolean isPrime(int num) {
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
