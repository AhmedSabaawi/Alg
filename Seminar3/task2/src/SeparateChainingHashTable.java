import java.util.LinkedList;

public class SeparateChainingHashTable {
    private LinkedList<Integer>[] hashTable;
    private int size;

    public SeparateChainingHashTable(int size) {
        this.size = size;
        hashTable = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            hashTable[i] = new LinkedList<>();
        }
    }

    public void insert(int key) {
        int index = key % size;
        hashTable[index].add(key);
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            System.out.print("Chain " + i + ": ");
            for (int key : hashTable[i]) {
                System.out.print(key + " -> ");
            }
            System.out.println("null");
        }
    }

    public SeparateChainingHashTable rehash() {
        SeparateChainingHashTable newHashTable = new SeparateChainingHashTable(this.size * 2);
        for (LinkedList<Integer> chain : this.hashTable) {
            if (chain != null) {
                for (int key : chain) {
                    newHashTable.insert(key);
                }
            }
        }
        return newHashTable;
    }
}
