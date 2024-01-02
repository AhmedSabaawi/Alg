public class QuadraticProbingHashTable {
    private Integer[] hashTable;
    private int size;

    public QuadraticProbingHashTable(int size) {
        this.size = size;
        hashTable = new Integer[size];
    }

    public void insert(int key) {
        int index = key % size;
        int i = 1;
        while (hashTable[index] != null) {
            index = (index + i * i) % size;
            i++;
        }
        hashTable[index] = key;
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            System.out.println("Index " + i + ": " + hashTable[i]);
        }
    }

    public QuadraticProbingHashTable rehash() {
        QuadraticProbingHashTable newHashTable = new QuadraticProbingHashTable(this.size * 2);
        for (Integer key : this.hashTable) {
            if (key != null) {
                newHashTable.insert(key);
            }
        }
        return newHashTable;
    }

}
