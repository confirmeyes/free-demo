### mysql 执行 sql步骤

- client
- server  ---> 解析器 ---> 抽象语法树 ---> 优化器 （Rbo 基于规则， Cbo 基于成本）
- 执行器 （与存储引擎交互）
- 存储引擎

#### 磁盘预读 最小单位页 大小是4K ， 预读N4K

### 性能监控

sql执行各步骤执行时间

#### show profile                                                                                                   

```mysql
set profiling=1;

查询所有 Query_ID
show profiles;

all：显示所有性能信息
show profile all for query n

block io：显示块io操作的次数
show profile block io for query n

context switches：显示上下文切换次数，被动和主动
show profile context switches for query n

cpu：显示用户cpu时间、系统cpu时间
show profile cpu for query n

IPC：显示发送和接受的消息数量
show profile ipc for query n

page faults：显示页错误数量
show profile page faults for query n

source：显示源码中的函数名称与位置
show profile source for query n

swaps：显示swap的次数
show profile swaps for query n
```

#### performance_schema

#### 使用show processlist 

- 查看连接的线程个数，来观察是否有大量线程处于不正常的状态或者其他不正常的特征
- host表示操作的主机
- db表示操作的数据库
- command表示当前状态
  - sleep：线程正在等待客户端发送新的请求
  - query：线程正在执行查询或正在将结果发送给客户端
  - locked：在mysql的服务层，该线程正在等待表锁
  - analyzing and statistics：线程正在收集存储引擎的统计信息，并生成查询的执行计划
  - Copying to tmp table：线程正在执行查询，并且将其结果集都复制到一个临时表中
  - sorting result：线程正在对结果集进行排序
  - sending data：线程可能在多个状态之间传送数据，或者在生成结果集或者向客户端返回数据



### 数据类型的优化

- varchar根据实际内容长度保存数据
- char固定长度的字符串
  1. 最大长度：255
  2. 会自动删除末尾的空格
  3. 检索效率、写效率 会比varchar高，以空间换时间
  4. 应用场景 存储长度波动不大的数据，如：md5摘要
  5. 存储短字符串、经常更新的字符串
- datetime
  - 占用8个字节 、可保存到毫秒 、 可保存时间范围大

- timestamp
  - 占用4个字节、 时间范围：1970-01-01到2038-01-19、 
  - 精确到秒、 采用整形存储、 自动更新timestamp列的值
- date
  - 占用的字节数比使用字符串、datetime、int存储要少，使用date类型只需要3个字节
  - 使用date类型还可以利用日期时间函数进行日期之间的计算
  - date类型用于保存1000-01-01到9999-12-31之间的日期

### 索引



### 存储引擎



### 执行计划



