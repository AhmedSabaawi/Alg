import java.util.ArrayList;
import java.util.List;

public class MinHeap {
    private List<Integer> heap;

    public MinHeap() {
        heap = new ArrayList<>();
    }

    public List<Integer> getHeap() {
        return new ArrayList<>(heap);
    }

    public void insert(int value) {
        heap.add(value);
        int current = heap.size() - 1;

        while (current > 0 && heap.get(current) < heap.get(parentIndex(current))) {
            swap(current, parentIndex(current));
            current = parentIndex(current);
        }
    }

    public void remove() {
        if (heap.isEmpty()) {
        }
        if (heap.size() == 1) {
            heap.remove(0);
        }

        // int minValue = heap.get(0);
        // heap.set(0, heap.remove(heap.size() - 1));
        // sinkDown(0);
        // return minValue;
    }

    private void sinkDown(int index) {
        int minIndex = index;

        while (true) {
            int leftChildIndex = leftChildIndex(index);
            int rightChildIndex = rightChildIndex(index);

            if (leftChildIndex < heap.size() && heap.get(leftChildIndex) < heap.get(minIndex)) {
                minIndex = leftChildIndex;
            }

            if (rightChildIndex < heap.size() && heap.get(rightChildIndex) < heap.get(minIndex)) {
                minIndex = rightChildIndex;
            }

            if (minIndex != index) {
                swap(index, minIndex);
                index = minIndex;
            } else {
                return;
            }
        }
    }

    private int leftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int rightChildIndex(int index) {
        return 2 * index + 2;
    }

    private int parentIndex(int index) {
        return (index - 1) / 2;
    }

    // find value in tree
    public boolean search(int value) {
        return heap.contains(value);
    }

    private int minValue(int index) {
        int minValue = heap.get(index);
        int leftChildIndex = leftChildIndex(index);
        int rightChildIndex = rightChildIndex(index);

        if (leftChildIndex < heap.size()) {
            minValue = Math.min(minValue, minValue(leftChildIndex));
        }

        if (rightChildIndex < heap.size()) {
            minValue = Math.min(minValue, minValue(rightChildIndex));
        }

        return minValue;
    }

    private void swap(int index1, int index2) {
        int temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }
}
