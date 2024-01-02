#include <stdio.h>
#include <stdlib.h>
// #include <omp.h>
#include <string.h>
#include <time.h>
#include "readFileIntoArray.h"
#include "Sort.h"
#include "binarySearch.h"

const char *strategyNames[] = {"First Element", "RANDOM_ELEMENT", "Median of Three"};

int main_Sort();
int main_Search();

#define SORT
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
            main_Sort();
            break;
        case 2:
            main_Search();
            break;
        case 3:
            return 0;
        default:
            printf("Wrong choice\n");
        }
    }
}

#ifdef SORT
// create funcation the return the time for sorting and tacke the funcation as a parameter

int main_Sort()
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
                fprintf(outputFile, "%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, strategyNames[strategy], "Recursive", timeRecursiveQ, "Iterative", timeIterativeQ);
                printf("%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, strategyNames[strategy], "Recursive", timeRecursiveQ, "Iterative", timeIterativeQ);
            }

            // Write average times
            fprintf(outputFile, "--------------------------------------------------------------------------------------\n");
            fprintf(outputFile, "%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, "Average", "", totalTimeRecursive / numRuns, "", totalTimeIterative / numRuns);
            fprintf(outputFile, "\n");
            // Write average times
            printf("--------------------------------------------------------------------------------------\n");
            printf("%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, "Average", "", totalTimeRecursive / numRuns, "", totalTimeIterative / numRuns);
            printf("\n");
        }

        double totalTimeInsertSortIterative, totalTimeInsertSortRecursive = 0;
        // #pragma omp parallel for
        for (int run = 0; run < numRuns; run++)
        {
            double timeInsertSortRecursive = timeOfSortingI(originalArray, inputSize, 'R');
            double timeInsertSortIterative = timeOfSortingI(originalArray, inputSize, 'I');
            totalTimeInsertSortRecursive += timeInsertSortRecursive;
            totalTimeInsertSortIterative += timeInsertSortIterative;

            fprintf(outputFile, "%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, "insertionSort", "Recursive", timeInsertSortRecursive, "Iterative", timeInsertSortIterative);
            printf("%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, "insertionSort", "Recursive", timeInsertSortRecursive, "Iterative", timeInsertSortIterative);
        }
        // Write average times
        fprintf(outputFile, "--------------------------------------------------------------------------------------\n");
        fprintf(outputFile, "%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, "Average Recursive", "", totalTimeInsertSortRecursive / numRuns, "Iterative", totalTimeInsertSortIterative / numRuns);
        fprintf(outputFile, "\n");

        printf("--------------------------------------------------------------------------------------\n");
        printf("%-12d%-20s%-15s%-15f%-15s%-15f\n", inputSize, "Average ", "Recursive", totalTimeInsertSortRecursive / numRuns, "Iterative", totalTimeInsertSortIterative / numRuns);
        printf("\n");

        fprintf(outputFile, "\n");
        printf("\n");

        free(originalArray);
    }

    // ...

    fclose(outputFile);
    return 0;
}

int main_Search()
{
    int inputSize = 1000000;                                  // Fixed array size
    int searchCounts[] = {100, 1000, 10000, 100000, 1000000}; // Different numbers of searches
    int numSearches;

    srand(time(NULL)); // Seed for random number generation

    int *originalArray = (int *)malloc(inputSize * sizeof(int));
    readFileIntoArray("SortedData.txt", &originalArray, inputSize);

    for (int s = 0; s < sizeof(searchCounts) / sizeof(searchCounts[0]); s++)
    {
        numSearches = searchCounts[s];

        double totalTime = 0.0;
        int foundCount = 0;

        for (int i = 0; i < numSearches; i++)
        {
            int to_find;
            if (i % 2 == 0)
            {
                // Generate a number that will be found
                to_find = rand() % 101; // Random number between 0-100
            }
            else
            {
                // Generate a number that will NOT be found
                to_find = 101 + rand() % 100; // Random number between 101-200
            }

            clock_t start = clock();
            int found = binarySearch(originalArray, 0, inputSize - 1, to_find);
            clock_t end = clock();

            totalTime += (double)(end - start) / CLOCKS_PER_SEC;
            foundCount += (found != -1);
        }

        printf("--------------------------------------------------------------------------------------\n");
        printf("Number of searches: %d\n", numSearches);
        printf("Average search time: %lf seconds\n", totalTime / numSearches);
        printf("Number of successful searches: %d out of %d\n", foundCount, numSearches);
    }

    free(originalArray);
    return 0;
}
#endif

#ifdef Sort_save_file

    int
    main()
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
