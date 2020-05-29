

### ES底层读写原理？倒排索引原理?



- 写数据过程
  1、客户端选择一个node发送请求过去，这个node就是coordinating node（协调节点）
  2、coordinating node 对document进行路由，将请求转发给对应的node（有primary shard）
  3、实际的node上的primary shard 处理请求，然后将数据同步到replica node。
  4、coordinating node如果发现 primary node和所有replica node都搞定之后，就返回响应结果给客户端。

- 读数据过程
  可以通过doc id 来查询，会根据doc id进行hash，判断出来当时把doc id分配到了哪个shard上面去，从那个shard去查询。
  1、客户端发送请求到任意一个node，成为coordinate node
  2、coordinate node 对doc id进行哈希路由，将请求转发到对应node，此时会使用round-robin随机轮询算法，在primary shard 以及其所有replica中随机选择一个，让读请求负载均衡。
  3、接收请求的node返回document给coordinate node。
  4、coordinate node返回document给客户端。


- 倒排索引 ：关键字分词

