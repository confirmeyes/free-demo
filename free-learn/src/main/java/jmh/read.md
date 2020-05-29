

### 官网

<http://openjdk.java.net/projects/code-tools/jmh/>

## JMH中的基本概念

1. Warmup 预热，由于JVM中对于特定代码会存在优化（本地化），预热对于测试结果很重要

2. Mesurement 总共执行多少次测试

3. Timeout

4. Threads 线程数，由fork指定

5. Benchmark mode 基准测试的模式

6. Benchmark 测试哪一段代码



   #### 由于用到了注解，打开运行程序注解配置

   compiler -> Annotation Processors -> Enable Annotation Processing