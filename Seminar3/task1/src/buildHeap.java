
// import the package 
import java.util.Arrays;

public class buildHeap {
    // instance variables
    private int[] heap;
    private int size;

    // constructor
    public buildHeap() {
        heap = new int[10];
        size = 0;
    }

    // methods
    public void buildHeap(int[] array) {
        heap = Arrays.copyOf(array, array.length * 2);
        size = array.length;
        for (int i = size / 2; i >= 0; i--) {
            siftDown(i);
        }
    }

    private void swap(int[] heap, int index, int parent) {
        int temp = heap[index];
        heap[index] = heap[parent];
        heap[parent] = temp;
    }

    private void siftDown(int index) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int min = index;
        if (left < size && heap[left] < heap[min]) {
            min = left;
        }
        if (right < size && heap[right] < heap[min]) {
            min = right;
        }
        if (min != index) {
            swap(heap, index, min);
            siftDown(min);
        }
    }

    public void add(int value) {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
        heap[size] = value;
        size++;
        int index = size - 1;
        int parent = (index - 1) / 2;
        while (index > 0 && heap[index] < heap[parent]) {
            swap(heap, index, parent);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    public int remove() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        int value = heap[0];
        heap[0] = heap[size - 1];
        size--;
        if (size > 0) {
            siftDown(0);
        }
        return value;
    }

    // traversing
    public void inOrderTraversal() {
        inOrderTraversal(0);
        System.out.println();
    }

    private void inOrderTraversal(int index) {
        if (index >= size) {
            return;
        }
        inOrderTraversal(index * 2 + 1);
        System.out.print(heap[index] + " ");
        inOrderTraversal(index * 2 + 2);
    }

    public void preOrderTraversal() {
        preOrderTraversal(0);
        System.out.println();
    }

    private void preOrderTraversal(int index) {
        if (index >= size) {
            return;
        }
        System.out.print(heap[index] + " ");
        preOrderTraversal(index * 2 + 1);
        preOrderTraversal(index * 2 + 2);
    }

    public void postOrderTraversal() {
        postOrderTraversal(0);
        System.out.println();
    }

    private void postOrderTraversal(int index) {
        if (index >= size) {
            return;
        }
        postOrderTraversal(index * 2 + 1);
        postOrderTraversal(index * 2 + 2);
        System.out.print(heap[index] + " ");
    }

    public void levelOrderTraversal() {
        // print in level order
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();

    }

    // display the tree of the heap as tree in vertical order

    public void display() {
        int level = 0;
        int index = 0;
        while (index < size) {
            int end = (int) Math.pow(2, level) + index;
            for (int i = index; i < end && i < size; i++) {
                System.out.print(heap[i] + " ");
            }
            System.out.println();
            index = end;
            level++;
        }
    }

}
