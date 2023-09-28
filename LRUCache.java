import java.util.HashMap;

class LRUCache {
    private class DLinkNode {
        int key;
        int val;
        DLinkNode prev;
        DLinkNode next;
    }

    private void add(DLinkNode node) {
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    public void remove(DLinkNode node) {
        DLinkNode prev = node.prev;
        DLinkNode next = node.next;
        prev.next = next;
        next.prev = prev;
    }
    //refresh in the cache as it gets used
    //remove it from original position
    //move to closer to head, less likely to be deleted
    public void moveToHead(DLinkNode node) {
        remove(node);
        add(node);
    }

    //remove and return the node right before tail
    public DLinkNode removeTail() {
        DLinkNode node = tail.prev;
        remove(node);
        return node;
    }

    DLinkNode head;
    DLinkNode tail;
    HashMap<Integer, DLinkNode> cache;
    int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        head = new DLinkNode();
        head.prev = null;

        tail = new DLinkNode();
        tail.next = null;

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkNode node = cache.get(key);
        if(node == null){
            return -1; // should raise exception here.
        }
        moveToHead(node);   //just access it, so refresh its position
        return node.val;
    }


    public void put(int key, int value) {
        DLinkNode node = cache.get(key);

        if (node == null) {
            DLinkNode replace = new DLinkNode();
            replace.key = key;
            replace.val = value;
            cache.put(key, replace);
            add(replace);
            //remove the last node from both the linkednode and cache
            if (cache.size() > capacity) {
                DLinkNode tail = removeTail();
                cache.remove(tail.key);
            }
        }
        else {
            node.val = value;
            moveToHead(node);
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */