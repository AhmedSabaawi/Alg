class Node {
    int value;
    Node next;

    public Node(int value) {
        this.value = value;
        this.next = null;
    }
}

class CustomLinkedList {
    Node head;

    public CustomLinkedList() {
        this.head = null;
    }

    public void add(int value) {
        if (head == null) {
            head = new Node(value);
            head.next = head; // Making it circular
        } else {
            Node current = head;
            while (current.next != head) {
                current = current.next;
            }
            Node newNode = new Node(value);
            newNode.next = head;
            current.next = newNode;
        }
    }

    // make the remove remove the current node
    public void remove(Node current) {
        if (current == head) {
            head = head.next;
        }
        Node prev = head;
        while (prev.next != current) {
            prev = prev.next;
        }
        prev.next = prev.next.next;
    }

    // public void remove(Node prev) {
    // if (prev.next == head) {
    // head = head.next;
    // }
    // prev.next = prev.next.next;
    // }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        if (head == null)
            return 0;
        int count = 1;
        Node current = head;
        while (current.next != head) {
            count++;
            current = current.next;
        }
        return count;
    }
}

public class JosephusCustomLinkedList {
    public static int runAlgorithm(int n, int m) {
        CustomLinkedList list = new CustomLinkedList();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }

        // get the size of the list
        int index = 0;
        while (list.size() > 1) {
            index = (index + m) % list.size();
            Node courent = list.head;

            int i = 0;
            while (i != index) {
                courent = courent.next;
                i++;
            }
            list.remove(courent);
        }

        return list.head.value;
    }

    public static void main(String[] args) {
        System.out.println(runAlgorithm(10, 6)); // Example usage
    }
}
