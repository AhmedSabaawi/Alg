import java.util.LinkedList;
import java.util.Queue;

enum Color {
    RED, BLACK
}

class Node {
    int val;
    Color color;
    Node left, right, parent;

    Node(int val) {
        this.val = val;
        this.color = Color.RED;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    Node uncle() {
        if (this.parent == null || this.parent.parent == null) {
            return null;
        }
        if (this.parent.isOnLeft()) {
            return this.parent.parent.right;
        } else {
            return this.parent.parent.left;
        }
    }

    boolean isOnLeft() {
        return this == this.parent.left;
    }

    Node sibling() {
        if (this.parent == null) {
            return null;
        }
        if (this.isOnLeft()) {
            return this.parent.right;
        } else {
            return this.parent.left;
        }
    }

    void moveDown(Node newParent) {
        if (this.parent != null) {
            if (this.isOnLeft()) {
                this.parent.left = newParent;
            } else {
                this.parent.right = newParent;
            }
        }
        newParent.parent = this.parent;
        this.parent = newParent;
    }

    boolean hasRedChild() {
        return (this.left != null && this.left.color == Color.RED) ||
                (this.right != null && this.right.color == Color.RED);
    }
}

public class RBTree {
    private Node root;

    RBTree() {
        this.root = null;
    }

    private void leftRotate(Node x) {
        Node newParent = x.right;
        if (x == this.root) {
            this.root = newParent;
        }
        x.moveDown(newParent);
        x.right = newParent.left;
        if (newParent.left != null) {
            newParent.left.parent = x;
        }
        newParent.left = x;
    }

    private void rightRotate(Node x) {
        Node newParent = x.left;
        if (x == this.root) {
            this.root = newParent;
        }
        x.moveDown(newParent);
        x.left = newParent.right;
        if (newParent.right != null) {
            newParent.right.parent = x;
        }
        newParent.right = x;
    }

    private void swapColors(Node x1, Node x2) {
        Color temp = x1.color;
        x1.color = x2.color;
        x2.color = temp;
    }

    private void swapValues(Node u, Node v) {
        int temp = u.val;
        u.val = v.val;
        v.val = temp;
    }

    private void fixRedRed(Node x) {
        if (x == this.root) {
            x.color = Color.BLACK;
            return;
        }

        Node parent = x.parent, grandparent = parent.parent, uncle = x.uncle();
        if (parent.color != Color.BLACK) {
            if (uncle != null && uncle.color == Color.RED) {
                parent.color = Color.BLACK;
                uncle.color = Color.BLACK;
                grandparent.color = Color.RED;
                fixRedRed(grandparent);
            } else {
                if (parent.isOnLeft()) {
                    if (x.isOnLeft()) {
                        swapColors(parent, grandparent);
                    } else {
                        leftRotate(parent);
                        swapColors(x, grandparent);
                    }
                    rightRotate(grandparent);
                } else {
                    if (x.isOnLeft()) {
                        rightRotate(parent);
                        swapColors(x, grandparent);
                    } else {
                        swapColors(parent, grandparent);
                    }
                    leftRotate(grandparent);
                }
            }
        }
    }

    private Node successor(Node x) {
        Node temp = x;
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp;
    }

    private Node BSTreplace(Node x) {
        if (x.left != null && x.right != null) {
            return successor(x.right);
        }
        if (x.left == null && x.right == null) {
            return null;
        }
        if (x.left != null) {
            return x.left;
        } else {
            return x.right;
        }
    }

    private void deleteNode(Node v) {
        Node u = BSTreplace(v);
        boolean uvBlack = (u == null || u.color == Color.BLACK) && (v.color == Color.BLACK);
        Node parent = v.parent;

        if (u == null) {
            if (v == this.root) {
                this.root = null;
            } else {
                if (uvBlack) {
                    fixDoubleBlack(v);
                } else {
                    if (v.sibling() != null) {
                        v.sibling().color = Color.RED;
                    }
                }
                if (v.isOnLeft()) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
        } else {
            if (v.left == null || v.right == null) {
                if (v == this.root) {
                    v.val = u.val;
                    v.left = v.right = null;
                } else {
                    if (v.isOnLeft()) {
                        parent.left = u;
                    } else {
                        parent.right = u;
                    }
                    u.parent = parent;
                    if (uvBlack) {
                        fixDoubleBlack(u);
                    } else {
                        u.color = Color.BLACK;
                    }
                }
            } else {
                swapValues(u, v);
                deleteNode(u);
            }
        }
    }

    private void fixDoubleBlack(Node x) {
        if (x == this.root) {
            return;
        }

        Node sibling = x.sibling(), parent = x.parent;
        if (sibling == null) {
            fixDoubleBlack(parent);
        } else {
            if (sibling.color == Color.RED) {
                parent.color = Color.RED;
                sibling.color = Color.BLACK;
                if (sibling.isOnLeft()) {
                    rightRotate(parent);
                } else {
                    leftRotate(parent);
                }
                fixDoubleBlack(x);
            } else {
                if (sibling.hasRedChild()) {
                    if (sibling.left != null && sibling.left.color == Color.RED) {
                        if (sibling.isOnLeft()) {
                            sibling.left.color = sibling.color;
                            sibling.color = parent.color;
                            rightRotate(parent);
                        } else {
                            sibling.left.color = parent.color;
                            rightRotate(sibling);
                            leftRotate(parent);
                        }
                    } else {
                        if (sibling.isOnLeft()) {
                            sibling.right.color = parent.color;
                            leftRotate(sibling);
                            rightRotate(parent);
                        } else {
                            sibling.right.color = sibling.color;
                            sibling.color = parent.color;
                            leftRotate(parent);
                        }
                    }
                } else {
                    sibling.color = Color.RED;
                    if (parent.color == Color.BLACK) {
                        fixDoubleBlack(parent);
                    } else {
                        parent.color = Color.BLACK;
                    }
                }
            }
        }
    }

    private void levelOrder(Node x) {
        if (x == null) {
            return;
        }

        Queue<Node> q = new LinkedList<>();
        q.add(x);

        while (!q.isEmpty()) {
            Node curr = q.poll();
            System.out.print(curr.val + " ");

            if (curr.left != null) {
                q.add(curr.left);
            }
            if (curr.right != null) {
                q.add(curr.right);
            }
        }
    }

    private void inorder(Node x) {
        if (x == null) {
            return;
        }
        inorder(x.left);
        System.out.print(x.val + " ");
        inorder(x.right);
    }

    void insert(int n) {
        Node newNode = new Node(n);
        if (this.root == null) {
            newNode.color = Color.BLACK;
            this.root = newNode;
        } else {
            Node temp = search(n);
            if (temp.val == n) {
                return;
            }
            newNode.parent = temp;
            if (n < temp.val) {
                temp.left = newNode;
            } else {
                temp.right = newNode;
            }
            fixRedRed(newNode);
        }
    }

    void deleteByVal(int n) {
        if (this.root == null) {
            return;
        }
        Node v = search(n);
        if (v.val != n) {
            System.out.println("No node found to delete with value: " + n);
            return;
        }
        deleteNode(v);
    }

    void printInOrder() {
        System.out.println("Inorder:");
        if (this.root == null) {
            System.out.println("Tree is empty");
        } else {
            inorder(this.root);
        }
        System.out.println();
    }

    void printLevelOrder() {
        System.out.println("Level order:");
        if (this.root == null) {
            System.out.println("Tree is empty");
        } else {
            levelOrder(this.root);
        }
        System.out.println();
    }

    // serach for the number in tree
    Node search(int n) {
        Node temp = this.root;
        while (temp != null) {
            if (n < temp.val) {
                if (temp.left == null) {
                    break;
                } else {
                    temp = temp.left;
                }
            } else if (n == temp.val) {
                break;
            } else {
                if (temp.right == null) {
                    break;
                } else {
                    temp = temp.right;
                }
            }
        }

        return temp;
    }

    // deleet min value in tree
    void deleteMin() {
        if (this.root == null) {
            System.out.println("Tree is empty");
        } else {
            Node temp = this.root;
            while (temp.left != null) {
                temp = temp.left;
            }
            deleteNode(temp);
        }
    }

}
