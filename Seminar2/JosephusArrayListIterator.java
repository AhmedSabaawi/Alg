import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JosephusArrayListIterator {
    public static int runAlgorithm(int n, int m) {
        List<Integer> people = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            people.add(i);
        }

        int count = 0;
        while (people.size() > 1) {
            Iterator<Integer> iterator = people.iterator();
            while (iterator.hasNext()) {
                iterator.next();
                if (count++ == m) {
                    iterator.remove();
                    count = 0;
                    if (people.size() == 1) {
                        break; // Exit early if only one person remains
                    }
                }
            }
        }

        return people.isEmpty() ? -1 : people.get(0); // Return -1 if the list is empty
    }

    public static void main(String[] args) {
        // gte the time before
        long start = System.currentTimeMillis();
        System.out.println(runAlgorithm(10000, 100)); // Example usage
        // get the time after
        long end = System.currentTimeMillis();
        // print the time difference
        System.out.println("Time taken: " + (end - start) + "ms");

    }
}
