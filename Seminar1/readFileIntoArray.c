#include <stdio.h>
#include <stdlib.h>

#define CHUNK_SIZE 1 // Adjust based on your needs

// Function to read numbers from file into an array
int readFileIntoArray(const char *filename, int **array, int maxNumbersToRead)
{
    FILE *file = fopen(filename, "r");
    if (file == NULL)
    {
        perror("Error opening file");
        return -1;
    }

    *array = malloc(CHUNK_SIZE * sizeof(int));
    if (*array == NULL)
    {
        perror("Error allocating memory");
        fclose(file);
        return -1;
    }

    int count = 0, capacity = CHUNK_SIZE;
    int number;

    while (count < maxNumbersToRead && fscanf(file, "%d", &number) == 1)
    {
        if (count == capacity)
        {
            capacity += CHUNK_SIZE;
            int *temp = realloc(*array, capacity * sizeof(int));
            if (temp == NULL)
            {
                perror("Error reallocating memory");
                free(*array);
                fclose(file);
                return -1;
            }
            *array = temp;
        }
        (*array)[count++] = number;
    }

    fclose(file);
    return count;
}
