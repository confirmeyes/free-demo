### mysql 执行 sql步骤

- **client**
- **server  ---> 连接器 --> 解析器 ---> 抽象语法树 ---> 优化器 （Rbo 基于规则， Cbo 基于成本）**
- **执行器 （与存储引擎交互）**
- **存储引擎**

#### 磁盘预读 最小单位页 大小是4K ， 预读N4K

#### innodb 默认预读16K，4页

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

- InnoDB ,  MyISAM底层 B+tree

- **MyISAM 的 B+tree 数据与索引分开存储，B+树中存储的是实际数据所在的地址**

- InnoDB 的 B+tree 数据与索引一起存储

  

---

- 在mysql中，只有memory的存储引擎显式支持哈希索引
- 哈希索引基于哈希表的实现，只有精确匹配索引所有列的查询才有效 ，范围查询无效

#### 使用索引注意事项

- 通配符%不能放在前面，不然索引失效
- 匹配范围
- 全匹配
- 精确匹配

#### 索引数据结构演变过程

- 二叉树 （ **容易倾斜 ， 节点过深** ）

- 平衡二叉树 （  **最长子树与最短子树高度差不能超过1** ，节点过深 ）

  - 必须进行旋转 ， 1-N次旋转 ，旋转浪费时间
  - 插入删除效率极低 ，查询效率高

- 红黑树  （ **旋转 + 变色**  ，减少旋转次数+变色 ）

  - 红黑树是对平衡二叉树的优化，提升了插入删除效率 ， 但是也损失了查询效率
  - **最长子树不超过最短子树高度2倍即可**

  - **变色（减少旋转次数） ：任何一个单个分支中 ， 不能连续出现两个红色节点**
  - **根 到 所有树路径，所有路径中黑色节点的数量一致**

- -------------------有且仅有两个节点，数据多后 ， 节点过深 ， IO次数增多-------------------------------------------

- B树

- B+树 优化了B树，数据只存储在叶子节点 ， 非叶子节点存储Key

#### 避免哈希冲突 ---> 编写优秀的哈希算法

#### mysql默认会给唯一字段建立索引，主键是唯一且非空字段

#### 主键索引

#### 唯一索引

#### 普通索引

#### 全文索引 （ text字段 ）

#### 组合索引

- **组合索引中 范围查询放在前面，后面索引失效**

### 面试索引

- 回表
  - 普通索引叶子节点放的是主键 ， 通过主键回表查询其他字段
- 覆盖索引 （ 执行计划中Extra提示 usring index ）
  - 不需要回表 ， 直接查出需要数据 比如：查询id
- 最左匹配
  - **组合索引**的适配 最左匹配
- 索引下推
  - 在存储引擎处理**组合索引**时，将where后多个条件一起过滤，不用在server端进行过滤，减少IO

- 索引合并
  - 若单独建立索引，高版本会在优化器中进行合并
- 页合并
- 页分裂

---

- order by 利用索引扫描进行排序， Extra中 index ， 索引如果不能覆盖查询全部列 就会出现每扫描一列就回表查询对应的行，回表增加随机IO次数
- using filesort 文件排序，Extra中 Null为使用了索引排序
- 索引默认是升序排序 ， 一个SQL中 同时出现desc 和 asc 的话， 索引order by失效

### 存储引擎

- InnoDB  MyISAM   Memory

### 执行计划

#### Cardinality 基数

- OLAP OLTP

### 慢查询日记

