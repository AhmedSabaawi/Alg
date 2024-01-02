
import java.io.*;
import java.util.*;

public class App {

    private enum PivotStrategy {
        FIRST_ELEMENT, RANDOM_ELEMENT, MEDIAN_OF_THREE
    }

    public static void main(String[] args) {
        String outputFileName = "output.txt";
        int[] inputSizes = { 10000, 50000, 100000 }; // Example size, can be adjusted
        int numRuns = 5;

        try (PrintWriter outputFile = new PrintWriter(new FileWriter(outputFileName))) {
            outputFile.println("Input Size    Strategy            Method         Time          ");
            outputFile
                    .println("--------------------------------------------------------------------------------------");

            for (int sizeIndex = 0; sizeIndex < inputSizes.length; sizeIndex++) {
                int inputSize = inputSizes[sizeIndex];
                int[] originalArray = new int[inputSize];

                int count = readFileIntoArray("Data.txt", originalArray, inputSize);
                if (count != inputSize) {
                    // Handle error or adjust logic as needed
                }
                for (PivotStrategy strategy : PivotStrategy.values()) {
                    double totalTimeRecursive = 0, totalTimeIterative = 0, totalTimeInsertionIterative = 0,
                            totalTimeInsertionRecursive = 0, timeQuickSortRecursive = 0, timeQuickSortIterative = 0;
                    for (int run = 0; run < numRuns; run++) {
                        int[] arrayCopyIR = Arrays.copyOf(originalArray, originalArray.length);
                        int[] arrayCopyII = Arrays.copyOf(originalArray, originalArray.length);
                        double timeInsertSortRecursive = 0, timeInsertSortIterative = 0;

                        // Recursive QuickSort
                        int[] arrayCopyR = Arrays.copyOf(originalArray, originalArray.length);
                        long startRecursive = System.currentTimeMillis();
                        quickSortRecursive(arrayCopyR, 0, inputSize - 1, strategy);
                        long endRecursive = System.currentTimeMillis();
                        totalTimeRecursive += (endRecursive - startRecursive) / 1000.0;
                        timeQuickSortRecursive = (endRecursive - startRecursive) / 1000.0;
                        // Iterative QuickSort
                        int[] arrayCopyI = Arrays.copyOf(originalArray, originalArray.length);
                        long startIterative = System.currentTimeMillis();
                        quickSortIterative(arrayCopyI, 0, inputSize - 1, strategy);
                        long endIterative = System.currentTimeMillis();
                        timeQuickSortIterative = (endIterative - startIterative) / 1000.0;
                        totalTimeIterative += (endIterative - startIterative) / 1000.0;

                        // Recursive Insertion Sort
                        try {
                            long startInsertionRecursive = System.currentTimeMillis();
                            insertionSortRecursive(arrayCopyIR, inputSize);
                            long endInsertionRecursive = System.currentTimeMillis();
                            timeInsertSortRecursive = (endInsertionRecursive - startInsertionRecursive) / 1000.0;
                            totalTimeInsertionRecursive += timeInsertSortRecursive;
                        } catch (StackOverflowError e) {
                            timeInsertSortRecursive = -1;
                            // outputFile.println("Stack Overflow occurred during Recursive Insertion
                            // Sort.");
                        }

                        // Iterative Insertion Sort
                        long startInsertionIterative = System.currentTimeMillis();
                        insertionSortIterative(arrayCopyII, 0, inputSize - 1);
                        long endInsertionIterative = System.currentTimeMillis();
                        timeInsertSortIterative = (endInsertionIterative - startInsertionIterative) / 1000.0;
                        totalTimeInsertionIterative += timeInsertSortIterative;

                        // Output individual run times
                        outputFile.printf("%-12d%-10s%-10s%-15s%-15f%-15s%-15f\n", inputSize, "QuickSort",
                                PivotStrategy.values()[strategy.ordinal()],
                                "Recursive", timeQuickSortRecursive, "Iterative", timeQuickSortIterative);

                        System.out.printf("%-12d%-10s%-10s%-15s%-15f%-15s%-15f\n", inputSize, "QuickSort",
                                strategy.toString(),
                                "Recursive",
                                timeQuickSortRecursive, "Iterative", timeQuickSortIterative);

                        // Output individual run times
                        outputFile.printf("%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, "insertionSort", "Recursive",
                                timeInsertSortRecursive, "Iterative", timeInsertSortIterative);
                        System.out.printf("%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, "insertionSort", "Recursive",
                                timeInsertSortRecursive, "Iterative", timeInsertSortIterative);
                    }

                    // Write average times
                    outputFile.println(
                            "--------------------------------------------------------------------------------------");
                    outputFile.printf("%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, "Average Recursive", "",
                            totalTimeInsertionRecursive / numRuns, "Iterative", totalTimeInsertionIterative / numRuns);
                    outputFile.println();

                    System.out.println(
                            "--------------------------------------------------------------------------------------");
                    System.out.printf("%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, "Average", "Recursive",
                            totalTimeInsertionRecursive / numRuns, "Iterative", totalTimeInsertionIterative / numRuns);
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.out.println("Error opening file!");
            e.printStackTrace();
        }
    }

    public static int readFileIntoArray(String filename, int[] array, int maxNumbersToRead) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            int count = 0;
            while (scanner.hasNextInt() && count < maxNumbersToRead) {
                array[count++] = scanner.nextInt();
            }
            return count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void quickSortRecursive(int[] arr, int low, int high, PivotStrategy strategy) {
        if (low < high) {
            int pi = partition(arr, low, high, strategy);
            quickSortRecursive(arr, low, pi - 1, strategy);
            quickSortRecursive(arr, pi + 1, high, strategy);
        }
    }

    public static void quickSortIterative(int[] arr, int low, int high, PivotStrategy strategy) {
        Stack<Integer> stack = new Stack<>();
        stack.push(low);
        stack.push(high);

        while (!stack.isEmpty()) {
            high = stack.pop();
            low = stack.pop();

            int pi = partition(arr, low, high, strategy);

            if (pi - 1 > low) {
                stack.push(low);
                stack.push(pi - 1);
            }

            if (pi + 1 < high) {
                stack.push(pi + 1);
                stack.push(high);
            }
        }
    }

    private static int partition(int[] arr, int low, int high, PivotStrategy strategy) {
        int pivotIndex;
        switch (strategy) {
            case RANDOM_ELEMENT:
                pivotIndex = low + new Random().nextInt(high - low);
                break;
            case MEDIAN_OF_THREE:
                pivotIndex = medianOfThree(arr, low, high);
                break;
            default:
                pivotIndex = low;
        }
        swap(arr, pivotIndex, high);
        int pivot = arr[high];
        int i = low;

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, high);
        return i;
    }

    private static int medianOfThree(int[] arr, int low, int high) {
        int mid = (low + high) / 2;
        if (arr[mid] < arr[low])
            swap(arr, mid, low);
        if (arr[high] < arr[low])
            swap(arr, high, low);
        if (arr[mid] < arr[high])
            swap(arr, mid, high);
        return mid;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void insertionSortIterative(int[] arr, int low, int high) {
        for (int i = low + 1; i <= high; ++i) {
            int key = arr[i];
            int j = i - 1;

            while (j >= low && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    public static void insertionSortRecursive(int[] arr, int n) {
        if (n <= 1) {
            return;
        }
        insertionSortRecursive(arr, n - 1);
        int last = arr[n - 1];
        int j = n - 2;

        while (j >= 0 && arr[j] > last) {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = last;
    }
}
