package lab1.collection;

import java.util.LinkedList;

public class HashTable {
    private LinkedList<HashNode>[] bucket;

    public HashTable(int bucketSize) {
        this.bucket = new LinkedList[bucketSize];
        for (int i = 0; i < this.bucket.length; i++) { // for all elements in table...
            this.bucket[i] = new LinkedList<>(); // each element of the hash table is initialized to an empty linked list of HashNodes
        }
    }

    public boolean hashInsert(HashNode node) {
        int hashResult = this.hashGetNodeBucketId(node); // this is the hash function
        return this.bucket[hashResult].add(node); //A HashNode is added at the end of the LinkedList
    }

    public HashNode hashSearch(String key) {
        int hashResult = (this.bucket.length != 1) ? Math.abs(key.hashCode() % this.bucket.length) : 0;
        int sizeOfLL = this.bucket[hashResult].size();
        HashNode node;

        for (int i = 0; i < sizeOfLL; i++) {
            node = this.bucket[hashResult].get(i);
            if (key.equals(node.getKey())) {
                return node;
            }
        }
        return null;
    }

    public int hashGetNodeBucketId(HashNode node) {
        return (this.bucket.length != 1) ? Math.abs(node.getKey().hashCode() % this.bucket.length) : 0;
    }

    public boolean hashDelete(HashNode node) {
        int hashResult = this.hashGetNodeBucketId(node);
        return this.bucket[hashResult].remove(node);
    }

    public void hashClear() {
        for (LinkedList<HashNode> hashNodes : this.bucket) {
            hashNodes.clear();
        }
    }

}
