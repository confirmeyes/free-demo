package zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author lpx .
 * @create 2019-12-13-10:13 .
 * @description .
 */
public class zkClusterTest {

    private Logger log = LoggerFactory.getLogger(zkClusterTest.class);

    @Test
    public void create() throws Exception {
        //重试策略 初始6S 重试2次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(6000, 2);

        CuratorFramework cf = CuratorFrameworkFactory.builder()
                .connectString("192.168.88.135")
                .sessionTimeoutMs(9000)
                .retryPolicy(retryPolicy)
                .build();

        cf.start();
        System.err.println("-------------- { zkClient 启动！！！！ } --------------------- ");

        //  建立节点
        //  cf.create().forPath("/name", "lpx".getBytes());
        //  支持多级目录同时创建 指定节点类型（不加withMode默认为持久类型节点）、路径、数据内容
          //cf.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/name/age", "22".getBytes());

        //cf.setData().forPath("/name","LPX".getBytes());
        //cf.delete().forPath("/name/age");

        //获取值
        String name = new String(cf.getData().forPath("/name"));
        //获取子节点
        List<String> stringList = cf.getChildren().forPath("/name");
        System.err.println("{ /name/age节点的值 } " + name);
        for (String str : stringList) {
            System.err.println("{ /name的子节点 } " + str);
        }
        //是否存在指定节点 null表示不存在
        Stat stat = cf.checkExists().forPath("/name/age");
        System.err.println("{ 是否存在指定节点 } " + stat);
        cf.close();

    }
}
