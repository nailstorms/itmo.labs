package lab1.collection;

import java.util.LinkedList;

public class HashTable {
    private LinkedList<HashNode>[] table;

    public HashTable(int bucketSize) {
        this.table = new LinkedList[bucketSize];
        for (int i = 0; i < this.table.length; i++) { // for all elements in table...
            this.table[i] = new LinkedList<>(); // each element of the hash table is initialized to an empty linked list of HashNodes
        }
    }

    public boolean hashInsert(HashNode node) {
        int hashResult = (this.table.length != 1) ? Math.abs(node.getKey().hashCode() % this.table.length) : 0; // this is the hash function
        return this.table[hashResult].add(node); //A HashNode is added at the end of the LinkedList
    }

    public HashNode hashSearch(String key) {
        int hashResult = (this.table.length != 1) ? Math.abs(key.hashCode() % this.table.length) : 0;
        int sizeOfLL = this.table[hashResult].size();
        HashNode node;

        for (int i = 0; i < sizeOfLL; i++) {
            node = this.table[hashResult].get(i);
            if (key.equals(node.getKey())) {
                return node;
            }
        }
        return null;
    }

    public boolean hashDelete(HashNode node) {
        int hashResult = (this.table.length != 1) ? Math.abs(node.getKey().hashCode() % this.table.length) : 0;
        return this.table[hashResult].remove(node);
    }

    public void hashClear() {
        for (LinkedList<HashNode> hashNodes : this.table) {
            hashNodes.clear();
        }
    }

}
