public class App {
    public static void main(String[] args) {
        int[] values = { 4371, 1323, 6173, 4199, 4344, 9679, 1989 };

        SeparateChainingHashTable sch = new SeparateChainingHashTable(10);
        LinearProbingHashTable lph = new LinearProbingHashTable(10);
        QuadraticProbingHashTable qph = new QuadraticProbingHashTable(10);

        for (int val : values) {
            sch.insert(val);
            lph.insert(val);
            qph.insert(val);
        }

        System.out.println("Separate Chaining Hash Table:");
        sch.display();

        System.out.println("\nLinear Probing Hash Table:");
        lph.display();

        System.out.println("\nQuadratic Probing Hash Table:");
        qph.display();

        System.out.println("-------------------------------- -------------------------------");
        System.out.println("\nRehashing Linear Probing Hash Table:");
        // Path: task2/src/App.java
        SeparateChainingHashTable schh = new SeparateChainingHashTable(10);
        for (int val : values) {
            schh.insert(val);
        }

        System.out.println("Original Separate Chaining Hash Table:");
        schh.display();

        schh = schh.rehash();
        System.out.println("\nRehashed Separate Chaining Hash Table:");
        schh.display();
    }
}
