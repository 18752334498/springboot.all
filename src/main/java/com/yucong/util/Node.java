package com.yucong.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Node {

    private String key;
    private String value;
    private String pid;
    private List<Node> child;

    public Node(String key, String value, String pid) {
        super();
        this.key = key;
        this.value = value;
        this.pid = pid;
    }

    public static void main(String[] args) {
        List<Node> list = new ArrayList<>();

        // Node node1 = new Node("1", "江苏省", "-1");
        Node node2 = new Node("101", "淮安市", "1");
        Node node3 = new Node("102", "南京市", "1");
        Node node4 = new Node("10101", "涟水县", "101");
        Node node5 = new Node("10102", "洪泽县", "101");
        Node node8 = new Node("10103", "楚州区", "101");
        Node node6 = new Node("10201", "栖霞区", "102");
        Node node7 = new Node("10202", "玄武区", "102");
        Node node9 = new Node("10203", "雨花区", "102");
        // list.add(node1);
        list.add(node2);
        list.add(node3);
        list.add(node4);
        list.add(node5);
        list.add(node6);
        list.add(node7);
        list.add(node8);
        list.add(node9);

        TreeBulid bulid = new TreeBulid(list);
        List<Node> tree = bulid.buildListTree();
        System.out.println(JSON.toJSONString(tree, SerializerFeature.PrettyFormat));

        List<Node> parse = parse(tree);
        System.out.println(JSON.toJSONString(parse, SerializerFeature.PrettyFormat));
    }

    private static List<Node> parse(List<Node> tree) {
        for (Node node : tree) {
            node.setKey(node.getValue());
            if (CollectionUtils.isNotEmpty(node.getChild())) {
                parse(node.getChild());
            }
        }
        return tree;
    }

}


@Data
@AllArgsConstructor
@NoArgsConstructor
class TreeBulid {

    private List<Node> nodes = new ArrayList<>();

    /**
     * 构建JSON树形结构
     * 
     * @return
     */
    public List<Node> buildListTree() {
        List<Node> nodeTree = buildTree();
        return nodeTree;
    }

    /**
     * 构建树形结构
     */
    public List<Node> buildTree() {
        List<Node> treeNodes = new ArrayList<Node>();
        List<Node> rootNodes = getRootNodes();
        for (Node rootNode : rootNodes) {
            buildChildNodes(rootNode);
            treeNodes.add(rootNode);
        }
        return treeNodes;
    }

    /**
     * 递归子节点
     */
    public void buildChildNodes(Node node) {
        List<Node> children = getChildNodes(node);
        if (!children.isEmpty()) {
            for (Node child : children) {
                buildChildNodes(child);
            }
            node.setChild(children);
        }
    }

    /**
     * 获取父节点下所有的子节点
     */
    public List<Node> getChildNodes(Node pnode) {
        List<Node> childNodes = new ArrayList<Node>();
        for (Node n : nodes) {
            if (pnode.getKey().equals(n.getPid())) {
                childNodes.add(n);
            }
        }
        return childNodes;
    }

    /**
     * 判断是否为根节点
     */
    public boolean rootNode(Node node) {
        boolean isRootNode = true;
        for (Node n : nodes) {
            if (node.getPid().equals(n.getKey())) {
                isRootNode = false;
                break;
            }
        }
        return isRootNode;
    }

    /**
     * 获取集合中所有的根节点
     */
    public List<Node> getRootNodes() {
        List<Node> rootNodes = new ArrayList<Node>();
        for (Node n : nodes) {
            if (rootNode(n)) {
                rootNodes.add(n);
            }
        }
        return rootNodes;
    }

}
