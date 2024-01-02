#ifndef SORT_H
#define SORT_H
enum PivotStrategy
{
    FIRST_ELEMENT,
    RANDOM_ELEMENT,
    MEDIAN_OF_THREE
};
#define MAX_SIZE 1000000 // Define maximum size for the stack in iterative quicksort

double timeOfSortingQ(int *array, int inputSize, enum PivotStrategy strategy, char mode);
double timeOfSortingI(int *array, int inputSize, char mode);
void quickSortRecursive(int arr[], int low, int high, enum PivotStrategy strategy);
void quickSortIterative(int arr[], int low, int high, enum PivotStrategy strategy);
int partition(int arr[], int low, int high, enum PivotStrategy strategy);
void swap(int *a, int *b);
int medianOfThree(int arr[], int low, int high);

void insertionSortIterative(int arr[], int low, int high);
int insertionSortRecursive(int arr[], int n);

#endif //
