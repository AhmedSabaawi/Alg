import JosephusArrayList;
import JosephusArrayListIterator;
import JosephusCustomLinkedList;
import JosephusLinkedListIterator;

public class task4 {
    public static void main(String[] args) {
        // Array of 10 different values to test
        int[] testValues = { 10, 100, 1000, 10000, 100000 };

        // Process each algorithm separately
        processAlgorithm("JosephusArrayList", testValues);
        processAlgorithm("JosephusArrayListIterator", testValues);
        processAlgorithm("JosephusCustomLinkedList", testValues);
        processAlgorithm("JosephusLinkedListIterator", testValues);
    }

    private static void processAlgorithm(String algorithmName, int[] testValues) {
        // Print header for each algorithm
        System.out.println("\\n" + algorithmName + ":");
        System.out.printf("%-30s%-10s%-10s \n", "Algorithm", "Input", "Time(ms)");
        long totalExecutionTime = 0;

        // Test each value
        for (int value : testValues) {
            long averageTimeForValue = 0;

            // Repeat 10 times for each value
            for (int i = 0; i < 10; i++) {
                long timeTaken = timeAlgorithm(algorithmName, value);
                averageTimeForValue += timeTaken;
                totalExecutionTime += timeTaken;
            }

            averageTimeForValue /= 10;
            System.out.printf("Average time for value %d: %d ms \n", value, averageTimeForValue);
        }

        long averageTime = totalExecutionTime / (testValues.length * 10);
        System.out.printf("Average execution time for %s: %d ms \n", algorithmName, averageTime);
    }

    private static long timeAlgorithm(String algorithmName, int value) {
        long start = System.nanoTime();

        // Execute the algorithm
        switch (algorithmName) {
            case "JosephusArrayList":
                JosephusArrayList.runAlgorithm(value, value);
                break;
            case "JosephusArrayListIterator":
                JosephusArrayListIterator.runAlgorithm(value, value);
                break;
            case "JosephusCustomLinkedList":
                JosephusCustomLinkedList.runAlgorithm(value, value);
                break;
            case "JosephusLinkedListIterator":
                JosephusLinkedListIterator.runAlgorithm(value, value);
                break;
            default:
                return 0;
        }

        long timeTaken = System.nanoTime() - start;
        System.out.printf("%-30s%-10d%-10d \n", algorithmName, value, timeTaken);
        return timeTaken;
    }

    // Assuming methods like runAlgorithm(int m, int n) are defined in each class
}
