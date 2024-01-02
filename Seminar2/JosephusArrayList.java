import java.util.ArrayList;
import java.util.List;

public class JosephusArrayList {
    public static int runAlgorithm(int n, int m) {
        List<Integer> people = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            people.add(i);
        }

        int index = 0;
        while (people.size() > 1) {
            index = (index + m) % people.size();
            people.remove(index);
        }

        return people.get(0);
    }

    public static void main(String[] args) {
        // get the time before
        long start = System.currentTimeMillis();
        System.out.println(runAlgorithm(7, 2)); // Example usage
        // get the time after
        long end = System.currentTimeMillis();
        // print the time difference
        System.out.println("Time taken: " + (end - start) + "ms");
    }
}
