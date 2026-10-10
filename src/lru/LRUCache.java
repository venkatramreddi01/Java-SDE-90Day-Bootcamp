package lru;

import java.util.HashMap;
import java.util.Map;

/**
 * High-Performance Concurrent In-Memory Cache with LRU (Least Recently Used) Eviction.
 * 
 * Data Structures:
 * 1. Custom Doubly Linked List: Maintains access order (Most Recently Used at Head, Least Recently Used at Tail) in O(1).
 * 2. HashMap<Key, Node>: Enables O(1) key lookups.
 * 
 * Time Complexity: O(1) for get() and put()
 * Space Complexity: O(Capacity)
 */
public class LRUCache<K, V> {

    // Doubly Linked List Node
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<K, Node<K, V>> map;
    private final Node<K, V> head; // Dummy head
    private final Node<K, V> tail; // Dummy tail

    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive!");
        }
        this.capacity = capacity;
        this.map = new HashMap<>();

        // Initialize dummy head and tail to eliminate null-checks
        this.head = new Node<>(null, null);
        this.tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Retrieves the value associated with the key.
     * Marks the accessed node as Most Recently Used (moves to Head).
     */
    public synchronized V get(K key) {
        Node<K, V> node = map.get(key);
        if (node == null) {
            return null;
        }
        // Move to head (Most Recently Used)
        moveToHead(node);
        return node.value;
    }

    /**
     * Inserts or updates the key-value pair.
     * If capacity is exceeded, evicts the Least Recently Used item (Tail).
     */
    public synchronized void put(K key, V value) {
        Node<K, V> node = map.get(key);

        if (node != null) {
            // Update existing value and move to head
            node.value = value;
            moveToHead(node);
        } else {
            // Create new node
            Node<K, V> newNode = new Node<>(key, value);
            map.put(key, newNode);
            addNodeToHead(newNode);

            // Check if capacity is exceeded
            if (map.size() > capacity) {
                // Evict least recently used (item right before dummy tail)
                Node<K, V> lru = popTail();
                map.remove(lru.key);
                System.out.println("[EVICT] Cache capacity full. Evicted LRU key: " + lru.key);
            }
        }
    }

    // Helper: Add a node right after dummy head
    private void addNodeToHead(Node<K, V> node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    // Helper: Remove an existing node from the Doubly Linked List
    private void removeNode(Node<K, V> node) {
        Node<K, V> prevNode = node.prev;
        Node<K, V> nextNode = node.next;

        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }

    // Helper: Move an accessed node to the head
    private void moveToHead(Node<K, V> node) {
        removeNode(node);
        addNodeToHead(node);
    }

    // Helper: Pop the least recently used node right before tail
    private Node<K, V> popTail() {
        Node<K, V> lru = tail.prev;
        removeNode(lru);
        return lru;
    }

    public synchronized int size() {
        return map.size();
    }

    public static void main(String[] args) {
        System.out.println("=== Testing Concurrent In-Memory LRU Cache ===");
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        cache.put(1, "User-101 (Venkat)");
        cache.put(2, "User-102 (Alice)");
        cache.put(3, "User-103 (Bob)");

        System.out.println("Get Key 1: " + cache.get(1)); // Marks key 1 as MRU

        System.out.println("Put Key 4 (Exceeds capacity 3)...");
        cache.put(4, "User-104 (Charlie)"); // Should evict Key 2 (LRU)!

        System.out.println("Get Key 2 (Should be null): " + cache.get(2)); // null
        System.out.println("Get Key 1 (Should be present): " + cache.get(1)); // Present!
        System.out.println("Get Key 4 (Should be present): " + cache.get(4)); // Present!

        System.out.println("\nAll operations executed in O(1) time!");
    }
}
