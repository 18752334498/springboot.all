package com.yucong.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yucong.AllApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AllApp.class)
public class EventTest {
    private static final String SERVER = "localhost:2181";
    private final int SESSION_TIMEOUT = 30000;
    private final int CONNECTION_TIMEOUT = 5000;
    private CuratorFramework client = null;

    @Before
    public void init() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(SERVER, SESSION_TIMEOUT, CONNECTION_TIMEOUT, retryPolicy);
        client.start();
    }

    /**
     * <li>PathChildrenCacheListener：监听指定节点 的子节点的创建、删除、更新</li>
     */
    @Test
    public void pathChildrenCacheListener() throws Exception {
        PathChildrenCache childrenCache = new PathChildrenCache(client, "/test", true);
        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                System.out.println("开始进行事件分析:-----");
                ChildData data = event.getData();
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED : " + data.getPath() + "  数据:" + data.getData().toString());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED : " + data.getPath() + "  数据:" + data.getData().toString());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED : " + data.getPath() + "  数据:" + data.getData().toString());
                        break;
                    case INITIALIZED:
                        System.out.println("INITIALIZED : " + data.getPath() + "  数据:" + data.getData().toString());
                        break;
                    default:
                        System.out.println("创建一个节点。。。");
                        break;
                }
                System.out.println();
            }
        });

        System.out.println("Register zk watcher successfully!");
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

        // 创建一个节点
        client.create().orSetData().withMode(CreateMode.PERSISTENT).forPath("/test", "test".getBytes());

        client.create().orSetData().withMode(CreateMode.EPHEMERAL).forPath("/test/node01", "enjoy".getBytes());
        Thread.sleep(2000);
        client.create().orSetData().withMode(CreateMode.EPHEMERAL).forPath("/test/node02", "deer".getBytes());
        Thread.sleep(2000);
        client.create().orSetData().withMode(CreateMode.EPHEMERAL).forPath("/test/node02", "demo".getBytes());
        Thread.sleep(2000);
        client.delete().forPath("/test/node02");
        Thread.sleep(2000);
    }

    /**
     * <li>监听指定节点的创建、更新、删除</li>
     */
    @Test
    public void nodeCacheListenter() throws Exception {

        // 设置节点的cache
        final NodeCache nodeCache = new NodeCache(client, "/test", false);// 是否对数据进行压缩

        // buildInitial: 初始化的时候获取node的值并且缓存
        nodeCache.start(true);
        if (nodeCache.getCurrentData() != null) {
            System.out.println("节点的初始化数据为：" + new String(nodeCache.getCurrentData().getData()));
        } else {
            System.out.println("节点初始化数据为空。。。");
        }

        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("the test node is change and result is :");
                System.out.println("path : " + nodeCache.getCurrentData().getPath());
                System.out.println("data : " + new String(nodeCache.getCurrentData().getData()));
                System.out.println("stat : " + nodeCache.getCurrentData().getStat());
            }
        });
        nodeCache.start();

        // 如果zk没有/test节点，监听正常进行，到下面会创建/test节点

        client.create().orSetData().withMode(CreateMode.PERSISTENT).forPath("/test", "test".getBytes());
        Thread.sleep(2000);
        client.create().orSetData().withMode(CreateMode.PERSISTENT).forPath("/test", "enjoy".getBytes());
        Thread.sleep(2000);
        client.delete().forPath("/test");
    }

    /**
     * <li>TreeCache：PathChildrenCache和NodeCache的“合体”，既能够监听自身节点的变化、也能够监听子节点的变化</li>
     */
    @Test
    public void TestListenterTwoThree() throws Exception {
        // 设置节点的cache
        TreeCache treeCache = new TreeCache(client, "/test");
        // 设置监听器和处理过程
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                ChildData data = event.getData();
                if (data != null) {
                    switch (event.getType()) {
                        case NODE_ADDED:
                            System.out.println("NODE_ADDED : " + data.getPath() + "  数据:" + new String(data.getData()));
                            break;
                        case NODE_REMOVED:
                            System.out.println("NODE_REMOVED : " + data.getPath() + "  数据:" + new String(data.getData()));
                            break;
                        case NODE_UPDATED:
                            System.out.println("NODE_UPDATED : " + data.getPath() + "  数据:" + new String(data.getData()));
                            break;

                        default:
                            break;
                    }
                } else {
                    System.out.println("data is null : " + event.getType());
                }
            }
        });
        treeCache.start();

        // 创建一个节点
        client.create().orSetData().withMode(CreateMode.PERSISTENT).forPath("/test", "test".getBytes());

        Thread.sleep(2000);
        client.create().orSetData().withMode(CreateMode.EPHEMERAL).forPath("/test/node01", "enjoy".getBytes());
        Thread.sleep(2000);
        client.create().orSetData().withMode(CreateMode.EPHEMERAL).forPath("/test/node01", "deer".getBytes());

        Thread.sleep(2000);
        client.create().orSetData().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/test/node02/node02_2", "deer".getBytes());

        Thread.sleep(2000);
    }
}
