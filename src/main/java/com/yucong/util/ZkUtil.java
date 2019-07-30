package com.yucong.util;

import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.BoundedExponentialBackoffRetry;
import org.springframework.util.StringUtils;

public class ZkUtil {

    private CuratorFramework client = null;

    public ZkUtil(String zkUrl, String zkHome) {

        RetryPolicy retryPolicy = new BoundedExponentialBackoffRetry(2000, 3, 3);
        Builder builder = CuratorFrameworkFactory.builder().connectString(zkUrl).sessionTimeoutMs(10000).retryPolicy(retryPolicy);
        if (!StringUtils.isEmpty(zkUrl)) {
            builder = builder.namespace(zkHome);
        }

        client = builder.build();
        client.start();
    }

    /**
     * @Description: 获取节点数据
     */
    public String findData(String node) throws Exception {
        if (client.checkExists().forPath(node) == null) {
            return "";
        }
        byte[] data = client.getData().forPath(node);
        return new String(data, "gbk");// 在zooInspector添加汉字，这里要gbk接收
    }

    /**
     * @Description: 获取节点子节点
     */
    public List<String> findChildren(String node) throws Exception {
        if (client.checkExists().forPath(node) == null) {
            return null;
        }
        List<String> children = client.getChildren().forPath(node);
        return children;
    }

    /**
     * @Description: 修改节点数据
     */
    public void updateData(String path, String data) throws Exception {
        if (client.checkExists().forPath(path) == null) {
            return;
        }
        client.setData().forPath(path, data.getBytes());
    }

    /**
     * @Description: 级联删除节点
     */
    public void deleteNode(String node) throws Exception {
        if (client.checkExists().forPath(node) == null) {
            return;
        }
        client.delete().guaranteed().deletingChildrenIfNeeded().forPath(node);
    }


    // 添加监听
    public void addDataWatcher(String node, final ZkDataOp op) {
        try {
            final NodeCache cache = new NodeCache(client, node);
            cache.start(true);
            cache.getListenable().addListener(new NodeCacheListener() {
                public void nodeChanged() throws Exception {
                    op.process(cache);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 添加监听回调事件，事件主业务
    public interface ZkDataOp {
        void process(NodeCache nodeCache) throws Exception;
    }

    public void LoopExecuteTask(final String path, final CallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InterProcessMutex lock = new InterProcessMutex(client, path);
                while (true) {
                    try {
                        lock.acquire();
                        callBack.process();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            lock.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public interface CallBack {
        void process();
    }

}
