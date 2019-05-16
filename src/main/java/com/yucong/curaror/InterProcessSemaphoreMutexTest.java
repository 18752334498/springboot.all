package com.yucong.curaror;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 不可重入排他锁（只能获得一次锁，使用完后释放）
 * 
 */
public class InterProcessSemaphoreMutexTest implements Runnable {
    private InterProcessSemaphoreMutex lock;// 不可重入锁
    private String lockPAth = "/lock/shareLock";
    private int i;
    private String clientName;
    public static final String ZOOKEEPERSTRING = "localhost:2181";

    public InterProcessSemaphoreMutexTest(CuratorFramework client, String clientName) {
        lock = new InterProcessSemaphoreMutex(client, lockPAth);
        this.clientName = clientName;
    }

    public void run() {
        try {
            Thread.sleep((new java.util.Random().nextInt(2000)));
            lock.acquire();
            if (lock.isAcquiredInThisProcess()) {
                System.out.println(clientName + ": 获得锁");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.release();
                System.out.println(clientName + ": 释放锁...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZOOKEEPERSTRING, new ExponentialBackoffRetry(1000, 3));
        client.start();
        for (int i = 0; i < 100; i++) {
            InterProcessSemaphoreMutexTest sharedReentrantLock = new InterProcessSemaphoreMutexTest(client, "第" + i + "个客户端：");
            Thread thread = new Thread(sharedReentrantLock);
            thread.start();
        }
    }

}
