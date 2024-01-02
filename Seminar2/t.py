import matplotlib.pyplot as plt

# Data
input_sizes = [10, 100, 1000, 10000, 100000]
average_times_array_list = [0, 0, 0, 16, 379]
average_times_array_list_iterator = [0, 0, 10, 77, 4410]
average_times_custom_linked_list = [0, 0, 20, 657, 80854]
average_times_linked_list_iterator = [0, 19, 18, 536, 94506]

# Plotting
plt.figure(figsize=(10, 6))
plt.plot(input_sizes, average_times_array_list, label="JosephusArrayList", marker="o")
plt.plot(
    input_sizes,
    average_times_array_list_iterator,
    label="JosephusArrayListIterator",
    marker="o",
)
plt.plot(
    input_sizes,
    average_times_custom_linked_list,
    label="JosephusCustomLinkedList",
    marker="o",
)
plt.plot(
    input_sizes,
    average_times_linked_list_iterator,
    label="JosephusLinkedListIterator",
    marker="o",
)

# Labeling
plt.xlabel("Input Size")
plt.ylabel("Time (ms)")
plt.title("Performance Comparison of Different Josephus Algorithms")
plt.xscale("linear")
plt.yscale("log")
plt.legend()
plt.grid(True)
plt.show()
