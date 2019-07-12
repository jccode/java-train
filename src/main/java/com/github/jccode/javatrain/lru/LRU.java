package com.github.jccode.javatrain.lru;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 双向链表 + HashMap
 *
 * 1. 访问节点时,将数据移除,放到链表头,这样保证链表最尾就是最久未使用的.删除时,只要把尾部删掉即可.
 * 2. 为了使访问/删除在O(1)的时间内,用一个HashMap存储着Key和节点的映射,以便在O(1)时间内获取到节点.
 */
public class LRU<K, V> {

    private static class Node<K, V> {
        final static Node EMPTY = new Node(null, null);

        Node prev;
        Node next;
        K key;
        V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int size;
    private Node head;
    private Node tail;
    private Map<K, Node<K, V>> map;

    public LRU(int size) {
        this.map = new HashMap<>(size * 4 / 3);
        this.size = size;

        this.initializeHeadAndTail();
    }

    private void initializeHeadAndTail() {
        this.head = Node.EMPTY;
        this.tail = Node.EMPTY;

        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    public V get(K key) {
        Node<K, V> node = map.get(key);
        if (node == null) return null;

        removeNode(node);
        prependNode(node);
        return node.value;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) { // remove if present
            removeNode(map.get(key));
        }
        Node<K, V> node = new Node<>(key, value);
        prependNode(node);
        ensureCapacity();
    }

    private void ensureCapacity() {
        if (map.size() > this.size) { // remove last element
            map.remove(tail.prev.key);
            Node lastNode = tail.prev.prev;
            lastNode.next = tail;
            tail.prev = lastNode;
        }
    }

    private void prependNode(Node<K, V> node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        map.put(node.key, node);
    }

    private void removeNode(Node<K, V> node) {
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
        map.remove(node.key);
    }

    public Set<K> keys() {
        return map.keySet();
    }

    @Override
    public String toString() {
        return map == null ? "[]" : map.keySet().toString();
    }
}
