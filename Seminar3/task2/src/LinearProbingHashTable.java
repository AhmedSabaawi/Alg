public class LinearProbingHashTable {
    private Integer[] hashTable;
    private int size;

    public LinearProbingHashTable(int size) {
        this.size = size;
        hashTable = new Integer[size];
    }

    public void insert(int key) {
        int index = key % size;
        while (hashTable[index] != null) {
            index = (index + 1) % size;
        }
        hashTable[index] = key;
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            System.out.println("Index " + i + ": " + hashTable[i]);
        }
    }

    public LinearProbingHashTable rehash() {
        LinearProbingHashTable newHashTable = new LinearProbingHashTable(this.size * 2);
        for (Integer key : this.hashTable) {
            if (key != null) {
                newHashTable.insert(key);
            }
        }
        return newHashTable;
    }
}
