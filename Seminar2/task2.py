from queue import Queue


class QueueUsingStacks:
    def __init__(self):
        # Initialize two stacks
        self.stack1 = []
        self.stack2 = []

    def enqueue(self, item):
        # Add an item to the queue
        self.stack1.append(item)

    def dequeue(self):
        # Remove an item from the queue
        if not self.stack2:
            # Transfer elements from stack1 to stack2 if stack2 is empty
            while self.stack1:
                self.stack2.append(self.stack1.pop())
        if self.stack2:
            return self.stack2.pop()
        else:
            return "Queue is empty"

    def peek(self):
        # Get the front element of the queue
        if not self.stack2:
            # Transfer elements from stack1 to stack2 if stack2 is empty
            while self.stack1:
                self.stack2.append(self.stack1.pop())
        if self.stack2:
            return self.stack2[-1]
        else:
            return "Queue is empty"

    def is_empty(self):
        # Check if the queue is empty
        return len(self.stack1) == 0 and len(self.stack2) == 0


class QueueWithOneStack:
    def __init__(self):
        self.stack = []

    def enqueue(self, item):
        self.stack.append(item)

    def dequeue(self):
        if len(self.stack) == 0:
            raise IndexError("Queue is empty")

        # Pop an item from the stack
        item = self.stack.pop()

        # If the stack is empty, return the popped item
        if len(self.stack) == 0:
            return item

        # Recursively pop the remaining items
        dequeued_item = self.dequeue()

        # Push the popped item back to the stack
        self.stack.append(item)

        # Return the dequeued item
        return dequeued_item


class StackWithTwoQueues:
    def __init__(self):
        self.queue1 = Queue()
        self.queue2 = Queue()

    def push(self, x):
        # Push x to queue1
        self.queue1.put(x)

    def pop(self):
        # Move all elements except the last from queue1 to queue2
        while self.queue1.qsize() > 1:
            self.queue2.put(self.queue1.get())

        # The last element in queue1 is the top of the stack
        top = self.queue1.get()

        # Swap the roles of the two queues
        self.queue1, self.queue2 = self.queue2, self.queue1

        return top

    def top(self):
        # Similar to pop, but we put the last element back into queue1
        while self.queue1.qsize() > 1:
            self.queue2.put(self.queue1.get())

        top = self.queue1.get()
        self.queue2.put(top)

        self.queue1, self.queue2 = self.queue2, self.queue1

        return top

    def empty(self):
        return self.queue1.empty() and self.queue2.empty()


class StackWithOneQueue:
    def __init__(self):
        self.queue = []

    def push(self, x):
        # Add the element to the queue
        self.queue.append(x)
        # Rotate the queue so that the newly added element is at the front
        for _ in range(len(self.queue) - 1):
            self.queue.append(self.queue.pop(0))

    def pop(self):
        # Remove and return the top element (which is the front of the queue)
        if not self.empty():
            return self.queue.pop(0)
        raise IndexError("Pop from empty stack")

    def top(self):
        # Return the top element without removing it
        if not self.empty():
            return self.queue[0]
        raise IndexError("Peek from empty stack")

    def empty(self):
        # Check if the stack is empty
        return len(self.queue) == 0


q = StackWithOneQueue()
q.push(1)
q.push(2)
q.push(3)
q.push(4)
q.pop()
q.reorder()
# q.reorder()
q.pop()
q.push(4)
q.pop()
q.push(5)

# q = QueueWithOneStack()
# q.enqueue(1)
# q.enqueue(2)
# q.enqueue(3)
# q.dequeue()
# q.enqueue(4)
# q.dequeue()
# q.enqueue(5)


# # Example usage
# queue = QueueUsingStacks()
# queue.enqueue(1)
# queue.enqueue(2)
# queue.enqueue(3)

# # Test operations
# first_item = queue.dequeue()  # Should return 1
# queue.enqueue(5)
# second_item = queue.dequeue()  # Should return 2

# queue.enqueue(4)
# thread_item = queue.dequeue()  # Should return 3

# peek_item = queue.peek()  # Should return 2
# is_empty = queue.is_empty()  # Should return False

# first_item, peek_item, is_empty
