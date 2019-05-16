package com.yucong.curator;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 可重入排他锁（可以多次获得锁不会被阻塞，释放时也需释放多把锁）
 * 
 */
public class InterProcessMutexTest implements Runnable {
    private InterProcessMutex lock;// 可重入锁实现类
    private String lockPAth = "/lock/shareLock";
    private int i;
    private String clientName;
    public static final String ZOOKEEPERSTRING = "localhost:2181";

    public InterProcessMutexTest(CuratorFramework client, String clientName) {
        lock = new InterProcessMutex(client, lockPAth);
        this.clientName = clientName;
    }

    public void run() {
        try {
            Thread.sleep((new java.util.Random().nextInt(2000)));
            lock.acquire(); // 增加第一把锁
            if (lock.isAcquiredInThisProcess()) {
                System.out.println(clientName + " 获得锁");
                i++;
            }
            lock.acquire(); // 增加第二把锁
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.release();
                System.out.println(clientName + "释放第一把锁");
                lock.release();
                System.out.println(clientName + "释放第二把锁");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZOOKEEPERSTRING, new ExponentialBackoffRetry(1000, 3));
        client.start();
        // 启动100个线程进行测试
        for (int i = 0; i < 100; i++) {
            InterProcessMutexTest sharedReentrantLock = new InterProcessMutexTest(client, "第" + i + "个客户端：");
            Thread thread = new Thread(sharedReentrantLock);
            thread.start();
        }
    }

}
