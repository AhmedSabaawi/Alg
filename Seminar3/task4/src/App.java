import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        int SizeOfTree = 71;
        int[] nmberList = getNumber(SizeOfTree);
        TreeNode root = null;
        AVLTree avlTree = new AVLTree();
        for (int i = 0; i < SizeOfTree; i++) {
            root = avlTree.insert(root, nmberList[i]);
        }

        BST bst = new BST();
        for (int i = 0; i < SizeOfTree; i++) {
            bst.insert(nmberList[i]);
        }
        MinHeap minHeap = new MinHeap();
        for (int i = 0; i < SizeOfTree; i++) {
            minHeap.insert(nmberList[i]);
        }
        RBTree rbTree = new RBTree();
        for (int i = 0; i < SizeOfTree; i++) {
            rbTree.insert(nmberList[i]);
        }

        // conunt the time of inser and delete and serch for the number 50 in each tree
        long startTime = System.nanoTime();
        root = avlTree.insert(root, 50);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Time of insert in AVLTree: " + duration + " nanoseconds");

        startTime = System.nanoTime();
        root = avlTree.deleteMin(root);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Time of delete in AVLTree: " + duration + " nanoseconds");

        startTime = System.nanoTime();
        avlTree.search(root, root.val);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Time of search in AVLTree: " + duration + " nanoseconds");

        startTime = System.nanoTime();
        bst.insert(50);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Time of insert in BST: " + duration + " nanoseconds");

        startTime = System.nanoTime();
        bst.deleteMin();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Time of delete in BST: " + duration + " nanoseconds");

        startTime = System.nanoTime();
        bst.search(50);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Time of search in BST: " + duration + " nanoseconds");

        startTime = System.nanoTime();
        minHeap.insert(50);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Time of insert in MinHeap: " + duration + " nanoseconds");

        startTime = System.nanoTime();
        minHeap.remove();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Time of delete in MinHeap: " + duration + " nanoseconds");

        startTime = System.nanoTime();
        minHeap.search(50);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Time of search in MinHeap: " + duration + " nanoseconds");

        startTime = System.nanoTime();
        rbTree.insert(50);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Time of insert in RBTree: " + duration + " nanoseconds");

        startTime = System.nanoTime();
        rbTree.deleteMin();
        ;
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Time of delete in RBTree: " + duration + " nanoseconds");

        startTime = System.nanoTime();
        rbTree.search(50);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Time of search in RBTree: " + duration + " nanoseconds");

        // write the result of each tree in a file
        File file = new File("Result.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("AVLTree: ");
        bw.newLine();
        bw.write("Insert: " + duration + " nanoseconds");
        bw.newLine();
        bw.write("Delete: " + duration + " nanoseconds");
        bw.newLine();
        bw.write("Search: " + duration + " nanoseconds");
        bw.newLine();
        bw.write("BST: ");
        bw.newLine();
        bw.write("Insert: " + duration + " nanoseconds");
        bw.newLine();
        bw.write("Delete: " + duration + " nanoseconds");
        bw.newLine();
        bw.close();
        fw.close();

    }// main

    public static int[] getNumber(int size) throws IOException {
        int[] returnList = new int[size];
        File file = new File("Data.txt");

        Scanner sc = new Scanner(file);

        for (int i = 0; i < size; i++) {
            // read line from file and add the nmber to the list
            returnList[i] = sc.nextInt();
        }
        sc.close();
        return returnList;
    }// getNumber

}// App