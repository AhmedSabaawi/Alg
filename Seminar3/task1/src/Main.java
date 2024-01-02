import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int[] values = { 10, 12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11, 13, 2 };

        // insert item one at a time
        // System.out.println("Insert item one at a time");
        // task_A(values);
        // System.out.println("---------------------");

        // insert item all at once
        // System.out.println("Insert item all at once");
        // task_B(values);
        // System.out.println("---------------------");

        // task_C(values);

        task_E();
    }

    public static void task_A(int[] values) {
        Heap heap = new Heap();
        for (int value : values) {
            heap.add(value);
        }
        // heap.display();

    }

    public static void task_B(int[] values) {
        buildHeap heap = new buildHeap();
        heap.buildHeap(values);
        // heap.display();

    }

    public static void task_C(int[] values) {
        Heap heap = new Heap();
        for (int value : values) {
            heap.add(value);
        }

        // all four traversing strategies: in- order, pre-order, post-order,
        // level-order.

        buildHeap heap2 = new buildHeap();
        heap2.buildHeap(values);
        // insert item all at once

        System.out.println("with heep");
        System.out.println("In-order traversal");
        heap.inOrderTraversal();
        System.out.println("---------------------");

        System.out.println("Pre-order traversal");
        heap.preOrderTraversal();
        System.out.println("---------------------");

        System.out.println("Post-order traversal");
        heap.postOrderTraversal();
        System.out.println("---------------------");

        System.out.println("Level-order traversal");
        heap.levelOrderTraversal();
        System.out.println("---------------------");

        System.out.println("with buildHeap");
        System.out.println("In-order traversal");
        heap2.inOrderTraversal();
        System.out.println("---------------------");

        System.out.println("Pre-order traversal");
        heap2.preOrderTraversal();
        System.out.println("---------------------");

        System.out.println("Post-order traversal");
        heap2.postOrderTraversal();
        System.out.println("---------------------");

        System.out.println("Level-order traversal");
        heap2.levelOrderTraversal();
        System.out.println("---------------------");

    }

    public static void task_D() {
        int[] values = { 10, 100, 1000, 10000, 100000, 1000000 };
        // read from file
        try {
            long[] sumTimes = new long[values.length];

            for (int repeat = 0; repeat < 10; repeat++) {
                for (int i = 0; i < values.length; i++) {
                    Scanner scanner = new Scanner(new File("Data.txt"));
                    int[] temp = new int[values[i]];
                    for (int j = 0; j < values[i]; j++) {
                        temp[j] = scanner.nextInt();
                    }
                    scanner.close();

                    long startTime = System.nanoTime();
                    task_A(temp);
                    long endTime = System.nanoTime();
                    long duration = endTime - startTime;

                    // Add time to corresponding sum
                    sumTimes[i] += duration;
                }
            }

            // Print the average times
            System.out.println("--------------------- --------------------- ---------------------");
            for (int i = 0; i < values.length; i++) {
                long average = sumTimes[i] / 10; // Since the loop is repeated 10 times
                System.out.println("Average (one at the time ) time for input size " + values[i] + ": " + average
                        + " nanoseconds");
            }

            for (int repeat = 0; repeat < 10; repeat++) {
                for (int i = 0; i < values.length; i++) {
                    Scanner scanner = new Scanner(new File("Data.txt"));
                    int[] temp = new int[values[i]];
                    for (int j = 0; j < values[i]; j++) {
                        temp[j] = scanner.nextInt();
                    }
                    scanner.close();

                    long startTime = System.nanoTime();
                    task_B(temp);
                    long endTime = System.nanoTime();
                    long duration = endTime - startTime;

                    // Add time to corresponding sum
                    sumTimes[i] += duration;
                }
            }

            // Print the average times
            System.out.println("--------------------- --------------------- ---------------------");
            for (int i = 0; i < values.length; i++) {
                long average = sumTimes[i] / 10; // Since the loop is repeated 10 times
                System.out.println("Average (all at the time ) time for input size " + values[i] + ": " + average
                        + " nanoseconds");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void task_E() {
        int[] values = { 10, 100, 1000, 10000, 100000, 1000000 };
        try {
            long[] sumInsertTimes = new long[values.length];
            long[] sumDeleteTimes = new long[values.length];

            for (int repeat = 0; repeat < 10; repeat++) {
                for (int i = 0; i < values.length; i++) {
                    Scanner scanner = new Scanner(new File("Data.txt"));
                    int[] temp = new int[values[i]];
                    for (int j = 0; j < values[i]; j++) {
                        temp[j] = scanner.nextInt();
                    }
                    scanner.close();

                    // Insertion
                    long startInsertTime = System.nanoTime();
                    Heap heap = new Heap();
                    for (int value : temp) {
                        heap.add(value);
                    }
                    long endInsertTime = System.nanoTime();
                    sumInsertTimes[i] += endInsertTime - startInsertTime;

                    // Deletion
                    long startDeleteTime = System.nanoTime();
                    // Assume task_B is the deletion method, modify as needed
                    for (int j = 0; j < values[i]; j++) {
                        heap.remove();
                    }
                    long endDeleteTime = System.nanoTime();
                    sumDeleteTimes[i] += endDeleteTime - startDeleteTime;
                }
            }

            // Print average times
            System.out.println("--------------------- --------------------- ---------------------");
            for (int i = 0; i < values.length; i++) {
                long averageInsert = sumInsertTimes[i] / 10;
                long averageDelete = sumDeleteTimes[i] / 10;
                System.out.println("Average time for insertion at input size " + values[i] + ": " + averageInsert
                        + " nanoseconds");
                System.out.println(
                        "Average time for deletion at input size " + values[i] + ": " + averageDelete + " nanoseconds");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
