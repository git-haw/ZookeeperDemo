package top.haw358.zookeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by Administrator on 2016-12-10.
 */
public class ZookeeperDemo {

    public static void main(String[] args) {
        // 创建一个与服务器的连接
        ZooKeeper zk = new ZooKeeper("localhost:" + ClientBase.CLIENT_PORT, ClientBase.CONNECTION_TIMEOUT,
                new Watcher() {
                    // 监控所有被触发的事件
                    public void process(WatchedEvent event) {
                        System.out.println("已经触发了" + event.getType() + "事件！");
                    }
                });
        // 创建一个目录节点
        zk.create(ClientBase.TEST_ROOT_PATH, "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 创建一个子目录节点
        zk.create(ClientBase.TEST_ROOT_PATH + "/testChildPathOne", "testChildDataOne".getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData(ClientBase.TEST_ROOT_PATH, false, null)));
        // 取出子目录节点列表
        System.out.println(zk.getChildren(ClientBase.TEST_ROOT_PATH, true));
        // 修改子目录节点数据
        zk.setData(ClientBase.TEST_ROOT_PATH + "/testChildPathOne", "modifyChildDataOne".getBytes(), -1);
        System.out.println("目录节点状态：[" + zk.exists(ClientBase.TEST_ROOT_PATH, true) + "]");
        // 创建另外一个子目录节点
        zk.create(ClientBase.TEST_ROOT_PATH + "/testChildPathTwo", "testChildDataTwo".getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData(ClientBase.TEST_ROOT_PATH + "/testChildPathTwo", true, null)));
        // 删除子目录节点
        zk.delete(ClientBase.TEST_ROOT_PATH + "/testChildPathTwo", -1);
        zk.delete(ClientBase.TEST_ROOT_PATH + "/testChildPathOne", -1);
        // 删除父目录节点
        zk.delete(ClientBase.TEST_ROOT_PATH, -1);
        // 关闭连接
        zk.close();
    }
}
