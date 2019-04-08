package com.yucong.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;


public class ForkJoinUser extends RecursiveTask<List<String[]>> {

    private static final long serialVersionUID = 1L;

    private List<User> userList;
    private int yuzhi;
    private int start;
    private int end;

    public static void main(String[] args) throws Exception {
        List<User> userList = new ArrayList<>();
        User user = null;
        for (int i = 0; i < 20000; i++) {
            user = new User();
            user.setId(i);
            user.setName("tom--" + i);
            user.setAge(20);
            userList.add(user);
        }
        System.out.println("集合创建完成。。。");
        Thread.sleep(2000);

        /**
         * 这个for循环的业务和1到10000的累加业务一样简单，单线程会更快，如果每个业务都有1毫秒消耗，那么forjoin会更快
         */
        System.out.println("----------");
        long s = System.currentTimeMillis();
        List<String[]> list = new ArrayList<String[]>();
        String[] arrs = null;
        for (User u : userList) {
            arrs = new String[3];
            arrs[0] = String.valueOf(u.getId());
            arrs[1] = String.valueOf(u.getName());
            arrs[2] = String.valueOf(u.getAge());
            list.add(arrs);
            TimeUnit.MILLISECONDS.sleep(1);
        }
        System.out.println("长度: " + list.size() + "  耗时：  " + (System.currentTimeMillis() - s));
        System.out.println("----------");

        Thread.sleep(2000);

        System.out.println("==========");
        long a = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(5); // 创建线程池
        ForkJoinUser task = new ForkJoinUser(userList, 0, userList.size()); // 实例化对象
        pool.submit(task); // 提交任务
        List<String[]> l = task.join(); // 阻塞线程获取结果
        System.out.println("长度: " + l.size() + "  耗时：" + (System.currentTimeMillis() - a));
        System.out.println("==========");
    }

    /**
     * 要处理的集合userList；要处理数据的第一个索引，如果是第一个就是 0；要处理集合的长度
     * 
     * @param userList
     * @param start
     * @param end
     */
    public ForkJoinUser(List<User> userList, int start, int end) {
        this.userList = userList;
        this.start = start;
        this.end = end;

        int a = userList.size();
        int b = Runtime.getRuntime().availableProcessors();
        this.yuzhi = (a % b == 0 ? a / b : a / b + 1);
    }

    @Override
    protected List<String[]> compute() {
        List<ForkJoinUser> tasks = new ArrayList<>();
        List<String[]> result = new ArrayList<String[]>();

        if ((this.end - this.start) <= this.yuzhi) {
            String[] arrs = null;
            for (int i = this.start; i < this.end; i++) {
                arrs = new String[3];
                arrs[0] = String.valueOf(this.userList.get(i).getId());
                arrs[1] = String.valueOf(this.userList.get(i).getName());
                arrs[2] = String.valueOf(this.userList.get(i).getAge());
                result.add(arrs);
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // System.out.println("=====: " + result.size());
        } else {
            int mid = (this.start + this.end) / 2;
            ForkJoinUser left = new ForkJoinUser(this.userList, this.start, mid);
            ForkJoinUser right = new ForkJoinUser(this.userList, mid, this.end);
            tasks.add(left);
            tasks.add(right);
        }

        if (!tasks.isEmpty()) {
            for (ForkJoinUser test : invokeAll(tasks)) {
                result.addAll(test.join());
            }
        }
        return result;
    }

}
