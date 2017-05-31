package com.test.algorithm;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Auther hinsteny
 * @Desc
 * @Date 2017-05-30
 * @copyright: 2017 All rights reserved.
 */
public class BinaryTree {

    private final static Logger logger = LoggerFactory.getLogger(BinaryTree.class);

    static class Tree {
        private Integer number;//node number
        private String name;// node name
        private Tree left;// left subTree
        private Tree right;//right subTree

        public Tree() {
        }

        public Tree(int number) {
            this.number = number;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Tree getLeft() {
            return left;
        }

        public void setLeft(Tree left) {
            this.left = left;
        }

        public Tree getRight() {
            return right;
        }

        public void setRight(Tree right) {
            this.right = right;
        }

        /**
         * Build binary-tree Recursive
         *
         * @param node
         * @param data
         */
        public void buildTree(Tree node, int data) {
            if (node.number == null) {
                node.setNumber(data);
            } else if (data < node.number) {
                if (node.left == null) {
                    node.left = new Tree(data);
                } else {
                    buildTree(node.left, data);
                }
            } else {
                if (node.right == null) {
                    node.right = new Tree(data);
                } else {
                    buildTree(node.right, data);
                }
            }
        }
    }

    private static Set<Integer> buildNUmberSet(int count) {
        Set<Integer> numbers = new HashSet<>(count);
        int max = count * 10 + 1;
        Random random = new Random();
        while (numbers.size() < count) {
            numbers.add(random.nextInt(max));
        }
        return numbers;
    }

    /**
     * Create one binary tree with tree nodes number
     *
     * @param nodeNum
     */
    public static Tree createBinaryTree(int nodeNum) {
        Set<Integer> numbers = buildNUmberSet(nodeNum);
        Iterator<Integer> iterator = numbers.iterator();
        Tree tree = new Tree();
        while (iterator.hasNext()) {
            tree.buildTree(tree, iterator.next());
        }
        return tree;
    }

//    public static void main(String[] args) {
//        Tree tree = createBinaryTree(50);
//
//    }

//    public static void breadthSearchRecursive(Tree tree){
//        if (tree == null) return;
//        logger.error("Tree number is: {}", tree.getNumber());
//        _breadthSearchRecursive(tree);
//    }
//
//    private static void _breadthSearchRecursive(Tree tree){
//        if (tree == null) return;
//        if (tree.getLeft() != null){
//            logger.error("Tree number is: {}", tree.getLeft().getNumber());
//        }
//        if (tree.getRight() != null){
//            logger.error("Tree number is: {}", tree.getRight().getNumber());
//        }
//        if (tree.getLeft() != null){
//            _breadthSearchRecursive(tree.getLeft());
//        }
//        if (tree.getRight() != null){
//            _breadthSearchRecursive(tree.getRight());
//        }
//    }

    public static void breadthSearchNotRecursive(Tree tree){
        if (tree == null) return;
        Deque<Tree> treeQueue = new ArrayDeque();
        treeQueue.addLast(tree);
        while (!treeQueue.isEmpty()){
            tree = treeQueue.poll();
            logger.error("Tree number is: {}", tree.getNumber());
            if (tree.getLeft() != null){
                treeQueue.add(tree.getLeft());
            }
            if (tree.getRight() != null){
                treeQueue.add(tree.getRight());
            }
        }
    }

    public static void depthSearchRecursive(Tree tree){
        if (tree == null) return;
        logger.error("Tree number is: {}", tree.getNumber());
        if (tree.getLeft() != null)
            depthSearchRecursive(tree.getLeft());
        if (tree.getRight() != null)
            depthSearchRecursive(tree.getRight());

    }

    public static void depthSearchNotRecursive(Tree tree){
        if (tree == null) return;
        Stack<Tree> treeStack = new Stack<>();
        treeStack.add(tree);
        while (!treeStack.isEmpty()){
            tree = treeStack.pop();
            logger.error("Tree number is: {}", tree.getNumber());
            if (tree.getRight() != null){
                treeStack.push(tree.getRight());
            }
            if (tree.getLeft() != null){
                treeStack.push(tree.getLeft());
            }
        }
    }

    @Test
    public void BreadthSearchRecursive () {
        Tree tree = createBinaryTree(10);
        //广度递归遍历二叉树
//        breadthSearchRecursive(tree);
        logger.error("==============================================");
        //广度非递归遍历二叉树
        breadthSearchNotRecursive(tree);
    }

    @Test
    public void DepthSearchRecursive () {
        Tree tree = createBinaryTree(10);
        //深度递归遍历二叉树
        depthSearchRecursive(tree);
        logger.error("**********************************************");
        //深度非递归遍历二叉树
        depthSearchNotRecursive(tree);
    }

}
