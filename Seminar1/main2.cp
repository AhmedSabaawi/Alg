#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "readFileIntoArray.h"
#include "Sort.h"
#include "binarySearch.h"
const char *strategyNames[] = {"First Element", "RANDOM_ELEMENT", "Median of Three"};

int main_sort();
int main_search();
const char *getStrategyName(enum PivotStrategy strategy)
{
    switch (strategy)
    {
    case FIRST_ELEMENT:
        return "First Element";
    case RANDOM_ELEMENT:
        return "Random Element"; // Assuming this is an enum value
    case MEDIAN_OF_THREE:
        return "Median of Three";
    default:
        return "Unknown Strategy";
    }
}

#define sort

#ifdef sort

int main()
{
    while (1)
    {
        printf("1- Sort\n");
        printf("2- Search\n");
        printf("3- Exit\n");
        int choice;
        scanf("%d", &choice);
        switch (choice)
        {
        case 1:
            main_sort();
            break;
        case 2:
            main_search();
            break;
        case 3:
            return 0;
        default:
            printf("Wrong choice\n");
        }
    }
}

int main_sort()
{
    FILE *outputFile = fopen("output.txt", "w");
    if (outputFile == NULL)
    {
        printf("Error opening file!\n");
        return 1;
    }

    int inputSizes[19];
    int numInputSizes = sizeof(inputSizes) / sizeof(inputSizes[0]);

    printf("1- Automatic input\n");
    printf("2- Manual input\n");
    int choice;
    scanf("%d", &choice);
    if (choice == 1)
    {
        // Preset sizes for automatic input
        const int autoInputSizes[] = {50000, 100000, 150000, 200000, 250000, 300000, 350000, 400000, 450000, 500000, 550000, 600000, 650000, 700000, 750000, 800000, 850000, 900000, 950000};
        memcpy(inputSizes, autoInputSizes, sizeof(autoInputSizes));
    }
    else if (choice == 2)
    {
        printf("Enter the number of input sizes\n");
        scanf("%d", &numInputSizes);
        printf("Enter the input sizes\n");
        for (int i = 0; i < numInputSizes; i++)
        {
            scanf("%d", &inputSizes[i]);
        }
    }
    else
    {
        printf("Wrong choice\n");
    }

    const int numRuns = 10;
    fprintf(outputFile, "Input Size\tStrategy\t\tMethod\t\tRun Time\n");
    fprintf(outputFile, "--------------------------------------------------------------------------------------\n");

    // #pragma omp parallel for
    for (int sizeIndex = 0; sizeIndex < numInputSizes; sizeIndex++)
    {
        int inputSize = inputSizes[sizeIndex];
        int *originalArray = (int *)malloc(inputSize * sizeof(int));

        readFileIntoArray("Data.txt", &originalArray, inputSize);

        for (enum PivotStrategy strategy = FIRST_ELEMENT; strategy <= MEDIAN_OF_THREE; strategy++)
        {
            double totalTimeRecursive = 0, totalTimeIterative = 0;

            for (int run = 0; run < numRuns; run++)
            {

                double timeRecursiveQ = timeOfSortingQ(originalArray, inputSize, strategy, 'R');
                double timeIterativeQ = timeOfSortingQ(originalArray, inputSize, strategy, 'I');
                totalTimeRecursive += timeRecursiveQ;
                totalTimeIterative += timeIterativeQ;
                // Write run times side by side on the same line
                fprintf(outputFile, "%-12d%-20s%-15s%-15f%-15s%-15f\n",
                        inputSize, getStrategyName(strategy), "", totalTimeRecursive / numRuns, "", totalTimeIterative / numRuns);

                printf("%-12d%-20s%-15s%-15f%-15s%-15f\n",
                       inputSize, getStrategyName(strategy), "", totalTimeRecursive / numRuns, "", totalTimeIterative / numRuns);
            }

            // Write average times
            fprintf(outputFile, "--------------------------------------------------------------------------------------\n");
            fprintf(outputFile, "%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, strategy, "", totalTimeRecursive / numRuns, "", totalTimeIterative / numRuns);
            fprintf(outputFile, "\n");
            // Write average times
            printf("--------------------------------------------------------------------------------------\n");
            printf("%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, strategy, "", totalTimeRecursive / numRuns, "", totalTimeIterative / numRuns);
            printf("\n");
        }

        double totalTimeInsertSortIterative, totalTimeInsertSortRecursive = 0;
        for (int run = 0; run < numRuns; run++)
        {
            double timeInsertSortRecursive = timeOfSortingI(originalArray, inputSize, 'R');
            double timeInsertSortIterative = timeOfSortingI(originalArray, inputSize, 'I');
            totalTimeInsertSortRecursive += timeInsertSortRecursive;
            totalTimeInsertSortIterative += timeInsertSortIterative;

            // fprintf(outputFile, "%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, "insertionSort", "Recursive", timeInsertSortRecursive, "Iterative", timeInsertSortIterative);
            // printf("%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, "insertionSort", "Recursive", timeInsertSortRecursive, "Iterative", timeInsertSortIterative);
        }
        // Write average times
        fprintf(outputFile, "--------------------------------------------------------------------------------------\n");
        fprintf(outputFile, "%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, "Average inseration Recursive", "", totalTimeInsertSortRecursive / numRuns, "Iterative", totalTimeInsertSortIterative / numRuns);
        fprintf(outputFile, "\n");

        printf("--------------------------------------------------------------------------------------\n");
        printf("%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, "Average ", "inseration Recursive", totalTimeInsertSortRecursive / numRuns, "Iterative", totalTimeInsertSortIterative / numRuns);
        printf("\n");

        fprintf(outputFile, "\n");
        printf("\n");

        free(originalArray);
    }

    // ...

    fclose(outputFile);
    return 0;
}

int main_search()
{
    // read the sored number from the file

    int inputSize = 1000000;
    int to_find = 50;
    int low = 0;
    int high = inputSize - 1;

    int *originalArray = (int *)malloc(inputSize * sizeof(int));
    readFileIntoArray("SortedData.txt", &originalArray, inputSize);

    clock_t startInsertSortIterative = clock();

    binarySearch(originalArray, low, high, to_find);
    clock_t endInsertSortIterative = clock();
    double timeInsertSortIterative = (double)(endInsertSortIterative - startInsertSortIterative) / CLOCKS_PER_SEC;
    printf("\nTime For Seraching .... %f\n", timeInsertSortIterative);
    return 0;
}
#endif

#ifdef Sort_save_file

int main()
{

    int inputSize = 1000000;
    int *originalArray = (int *)malloc(inputSize * sizeof(int));
    readFileIntoArray("Data.txt", &originalArray, inputSize);

    quickSortIterative(originalArray, 0, inputSize - 1, MEDIAN_OF_THREE);

    FILE *outputFile = fopen("SortedData.txt", "w");
    if (outputFile == NULL)
    {
        printf("Error opening file!\n");
        return 1;
    }
    // wirte the sorted number into the file
    for (int i = 0; i < inputSize; i++)
    {
        fprintf(outputFile, "%d\n", originalArray[i]);
    }
}

#endif