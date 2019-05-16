    // zkcli 连接默认zookeeper服务器
    // zkcli -server ip:port 连接指定的zookeeper服务器
    // create -s -e path data [acl] 创建节点，-s表示顺序，-e表示临时，默认是持久节点，acl缺省表示不做任何权限限制
    // ls path [watch] 显示path下的节点，不递归显示,watch注册监听，命令行可忽视
    // ls2 path 显示当前节点下的节点和当前节点的属性信息
    // get path [watch] 获取path的属性信息和数据内容
    // set path data [version] 更新path的数据内容，version是做类似cas的功能的对应dataversion，命令行可忽略
    // delete path [version] 删除节点，不能递归删除，只能删除叶子节点
    
参考博客
https://www.jianshu.com/p/eec133595c68