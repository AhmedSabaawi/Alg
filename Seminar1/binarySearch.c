#include <stdio.h>
int binarySearch(int arr[], int low, int high, int x)
{
    // checking if there are elements in the subarray
    if (high >= low)
    {

        // calculating mid point
        int mid = low + (high - low) / 2;

        // If the element is present at the middle itself
        if (arr[mid] == x)
        {
            printf("Found at index %d", mid);
            return mid;
        }
        // If element is smaller than mid, then it can only
        // be present in left subarray
        if (arr[mid] > x)
        {
            return binarySearch(arr, low, mid - 1, x);
        }

        // Else the element can only be present in right
        // subarray
        return binarySearch(arr, mid + 1, high, x);
    }

    // We reach here when element is not present in array
    printf("Not found");
    return 0;
}