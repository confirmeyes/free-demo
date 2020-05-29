package zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;


/**
 * @author lpx .
 * @create 2019-12-13-16:03 .
 * @description .
 */
public class zkWatchTest {

    /**
     * 指定节点
     *
     * @throws Exception
     */
    @Test
    public void watchNode() throws Exception {

        CuratorFramework zkClient = getZkClient();

        NodeCache nodeCache = new NodeCache(zkClient, "/name/age", false);//第三个参数：是否压缩
        nodeCache.start(true);

        nodeCache.getListenable().addListener(() -> {
            System.out.println("路径为：" + nodeCache.getCurrentData().getPath());
            System.out.println("数据为：" + new
                    String(nodeCache.getCurrentData().getData()));
            System.out.println("状态为：" + nodeCache.getCurrentData().getStat());
        });

        Thread.sleep(3000);
        zkClient.setData().forPath("/name/age", "25".getBytes());
        Thread.sleep(3000);
        zkClient.close();
    }

    /**
     * 该节点下子节点的监听
     *
     * @throws Exception
     */
    @Test
    public void watchChildNode() throws Exception {

        CuratorFramework zkClient = getZkClient();
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, "/name", true);
        pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        pathChildrenCache.getListenable().addListener((curatorFramework, event) -> {
            System.err.println("节点数据变化,类型:" + event.getType() + ",路径:" + event.getData().getPath());
            System.err.println("节点数据" + pathChildrenCache.getCurrentData());
        });

        Thread.sleep(3000);
        zkClient.create().creatingParentContainersIfNeeded().forPath("/name/top1", "1.8".getBytes());
        Thread.sleep(3000);
        zkClient.create().forPath("/name/top2", "1.9".getBytes());
        Thread.sleep(3000);
        zkClient.create().forPath("/name/top3", "2.0".getBytes());
        Thread.sleep(3000);
        zkClient.setData().forPath("/name/top2", "1.95".getBytes());
        Thread.sleep(3000);
        zkClient.delete().forPath("/name/top3");
        Thread.sleep(3000);
        zkClient.close();

    }

    /**
     * 该节点下所有子节点的监听
     *
     * @throws Exception
     */
    @Test
    public void watchAllChildNode() throws Exception {

        CuratorFramework zkClient = getZkClient();
        TreeCache treeCache = new TreeCache(zkClient, "/name");
        treeCache.start();
        treeCache.getListenable().addListener((curatorFramework, event) -> System.err.println("节点数据变化,类型:" + event.getType() + ",路径:" + event.getData().getPath()));

        zkClient.setData().forPath("/name/top1", "1.90".getBytes());
        Thread.sleep(3000);
        zkClient.setData().forPath("/name/top2", "2.0".getBytes());
        Thread.sleep(3000);
        zkClient.setData().forPath("/name/top2/tree", "大树".getBytes());
        zkClient.close();
    }

    @Test
    public void clear() throws Exception {
        CuratorFramework zkClient = getZkClient();
        zkClient.delete().forPath("/name/top1");
        zkClient.delete().forPath("/name/top2");
    }

    private static CuratorFramework getZkClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(6000, 2);
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                .connectString("192.168.88.135")
                .sessionTimeoutMs(9000)
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
        return zkClient;
    }

}
