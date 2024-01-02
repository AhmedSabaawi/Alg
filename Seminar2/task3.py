"""Implement an address book using a LinkedList to store the contacts.
Each contact shall be represented by one node in the linked list and shall store the
name and address of the contact. The linked list must be implemented by the student
manually, i.e. it is not allowed to use Javas build in implementations.
The linked list must have at least the following functionality:
1) Add node
2) Remove node
3) Get node using index
The program must use all of the above functionality and must print the complete list
of contacts to the screen using a loop"""


class Node:
    def __init__(self, name, address):
        self.name = name
        self.address = address
        self.next = None


class LinkedList:
    def __init__(self):
        self.head = None

    def add_node(self, name, address):
        new_node = Node(name, address)
        if self.head is None:
            self.head = new_node
            return
        last_node = self.head
        while last_node.next:
            last_node = last_node.next
        last_node.next = new_node

    def remove_node(self, index):
        if index == 0:
            self.head = self.head.next
            return
        last_node = self.head
        for i in range(index - 1):
            last_node = last_node.next
        last_node.next = last_node.next.next

    def get_node(self, index):
        last_node = self.head
        for i in range(index):
            last_node = last_node.next
        return last_node

    def print_list(self):
        last_node = self.head
        while last_node:
            print(last_node.name, last_node.address)
            last_node = last_node.next


my_list = LinkedList()
my_list.add_node("John", "Street 1")
my_list.add_node("Mary", "Street 2")
my_list.add_node("Bob", "Street 3")
my_list.print_list()
print()
my_list.remove_node(1)

my_list.print_list()
print()

print(my_list.get_node(1).name)
print(my_list.get_node(1).address)
print()
