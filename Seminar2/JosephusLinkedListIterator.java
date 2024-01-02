import java.util.LinkedList;
import java.util.ListIterator;

public class JosephusLinkedListIterator {
    public static int runAlgorithm(int n, int m) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }

        int count = 0;
        while (list.size() > 1) {
            ListIterator<Integer> iterator = list.listIterator();
            while (iterator.hasNext()) {
                iterator.next();
                if (count++ == m) {
                    iterator.remove();
                    count = 0;
                    if (list.size() == 1) {
                        break; // Exit early if only one person remains
                    }
                }
            }
        }

        return list.getFirst(); // The last remaining element
    }

    public static void main(String[] args) {
        // gte the time before
        long start = System.currentTimeMillis();
        System.out.println("Survivor's position: " + runAlgorithm(5, 0));
        // get the time after
        long end = System.currentTimeMillis();
        // print the time difference
        System.out.println("Time taken: " + (end - start) + "ms");
    }
}
