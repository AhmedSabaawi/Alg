#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "readFileIntoArray.h"
#include <time.h>
#include "Sort.h"

int nmnberfrecursion = 0;
int MAX_RECURSION = 304083;
int MAX_SIZER = 150000;
// 524083

double timeOfSortingQ(int *array, int inputSize, enum PivotStrategy strategy, char mode)
{
    // make copy of
    int *arrayCopyR = (int *)malloc(inputSize * sizeof(int));
    memcpy(arrayCopyR, array, inputSize * sizeof(int));

    clock_t startRecursive = clock();
    if (mode == 'I')
    {
        quickSortIterative(arrayCopyR, 0, inputSize - 1, strategy);
    }
    else
    {
        quickSortRecursive(arrayCopyR, 0, inputSize - 1, strategy);
    }
    clock_t endRecursive = clock();
    double timeRecursive = (double)(endRecursive - startRecursive) / CLOCKS_PER_SEC;
    free(arrayCopyR);

    return timeRecursive;
}

double timeOfSortingI(int *array, int inputSize, char mode)
{
    // make copy of
    int *arrayCopyR = (int *)malloc(inputSize * sizeof(int));
    memcpy(arrayCopyR, array, inputSize * sizeof(int));

    clock_t startRecursive = clock();
    if (mode == 'I')
    {
        insertionSortIterative(arrayCopyR, 0, inputSize - 1);
    }
    else
    {

        int cheek = insertionSortRecursive(arrayCopyR, inputSize - 1);
        if (cheek == -1)
        {
            double timeRecursive = 0;
            free(arrayCopyR);
            return timeRecursive;
        }
    }
    clock_t endRecursive = clock();
    double timeRecursive = (double)(endRecursive - startRecursive) / CLOCKS_PER_SEC;
    free(arrayCopyR);
    return timeRecursive;
}

void quickSortRecursive(int arr[], int low, int high, enum PivotStrategy strategy)
{
    if (low < high)
    {
        int pi = partition(arr, low, high, strategy);

        // Recursively sort elements before and after partition
        quickSortRecursive(arr, low, pi - 1, strategy);
        quickSortRecursive(arr, pi + 1, high, strategy);
    }
}

void quickSortIterative(int arr[], int low, int high, enum PivotStrategy strategy)
{
    int stack[MAX_SIZE];
    int top = -1;
    stack[++top] = low;
    stack[++top] = high;

    while (top >= 0)
    {
        high = stack[top--];
        low = stack[top--];
        int pi = partition(arr, low, high, strategy);

        if (pi - 1 > low)
        {
            stack[++top] = low;
            stack[++top] = pi - 1;
        }
        if (pi + 1 < high)
        {
            stack[++top] = pi + 1;
            stack[++top] = high;
        }
    }
}

int partition(int arr[], int low, int high, enum PivotStrategy strategy)
{
    int pivotIndex;

    if (strategy == MEDIAN_OF_THREE)
        pivotIndex = medianOfThree(arr, low, high);
    else if (strategy == FIRST_ELEMENT)
        pivotIndex = low;
    else if (strategy == RANDOM_ELEMENT)
    {
        srand(time(NULL));                            // Initialize random seed
        pivotIndex = low + rand() % (high - low + 1); // Generate a random number within the range
    }
    swap(&arr[pivotIndex], &arr[high]);

    int pivot = arr[high];
    int i = low - 1;
    for (int j = low; j <= high - 1; j++)
    {
        if (arr[j] <= pivot)
        {
            i++;
            swap(&arr[i], &arr[j]);
        }
    }
    swap(&arr[i + 1], &arr[high]);
    return i + 1;
}

void swap(int *a, int *b)
{
    int temp = *a;
    *a = *b;
    *b = temp;
}

int medianOfThree(int arr[], int low, int high)
{
    int mid = low + (high - low) / 2;
    if (arr[low] > arr[mid])
        swap(&arr[low], &arr[mid]);
    if (arr[low] > arr[high])
        swap(&arr[low], &arr[high]);
    if (arr[mid] > arr[high])
        swap(&arr[mid], &arr[high]);
    return mid;
}

void insertionSortIterative(int arr[], int low, int high)
{
    for (int i = low + 1; i <= high; i++)
    {
        int key = arr[i];
        int j = i - 1;
        while (j >= low && arr[j] > key)
        {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = key;
    }
}

int insertionSortRecursive(int arr[], int n)
{
    // Base case and check if RAM
    if (n <= 1)
    {
        return 0;
    }
    if (n > MAX_SIZER)
    {

        return -1;
    }

    // Sort first n-1 elements
    insertionSortRecursive(arr, n - 1);

    // Insert last element at its correct position
    // in sorted array.
    int last = arr[n - 1];
    int j = n - 2;

    /* Move elements of arr[0..i-1], that are
    greater than key, to one position ahead
    of their current position */
    while (j >= 0 && arr[j] > last)
    {
        arr[j + 1] = arr[j];
        j--;
    }
    arr[j + 1] = last;
    return 0;
}