### mysql 执行 sql步骤

- client
- server  ---> 解析器 ---> 抽象语法树 ---> 优化器 （Rbo 基于规则， Cbo 基于成本）
- 执行器 （与存储引擎交互）
- 存储引擎



- 磁盘预读 最小单位页 大小是4K ， 预读N4K

  

### 性能监控

sql执行各步骤执行时间

- profiles                                                                                                   

```mysql
set profiling=1;

查询所有 Query_ID
show profiles;
```

- performance_schema

- 使用show processlist 查看连接的线程个数，来观察是否有大量线程处于不正常的状态或者其他不正常的特征

### 执行计划

