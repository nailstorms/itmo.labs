#include <stdio.h>
#include <limits.h>
#include "linked_list.h"
#include "functions.h"

int square(int x)
{
    return x*x;
}

int cubic(int x)
{
    return x*x*x;
}

int sum(int x, int y)
{
    return x+y;
}

int maximal(int x, int y)
{
    return x>y ? x : y;
}

int minimal(int x, int y)
{
    return x<y ? x : y;
}

int module(int x)
{
    return abs(x);
}

int pow_2(int x)
{
    return x*2;
}

void print_space(int x)
{
    printf("%d ", x);
}

void print_newline(int x)
{
    printf("\n%d", x);
}


int main()
{
    int digit;
    int mode;
    struct linked_list *list = NULL;
    struct linked_list *map_list = NULL;

    puts("Enter 1 to read from stdin, 2 to read from \"list.txt\"");
    scanf("%d",&mode);
    switch(mode)
    {
    case 1:
    {
        while (scanf("%d", &digit) != EOF)
        {
            list_add_back(&list, digit);
        }
        break;
    }
    case 2:
    {
        if (load(&list, "list.txt"))
            puts("List imported successfully.");
        else
        {
            puts("Error reading from file. Please try again.");
            return 0;
        }
        break;
    }
    default:
    {
        puts("No such option. Please try again.");
        return 0;
    }
    }

    list_show(list);
    printf("\nElement amount: %d\n", list_length(list));
    printf("Sum of elements: %d\n", list_sum(list));

    printf("\n\nElement #3:\n%d", list_get(list, 3));

    puts("\n\n\n..testing output with foreach...\n\nWith spaces:");
    foreach(list, print_space);
    puts("\n\nWith new lines:");
    foreach(list, print_newline);


    puts("\n\n\n...testing map...\n\nSquares of the elements:");
    map_list = map(list, square);
    foreach(map_list, print_space);
    puts("\n\nCubics of the elements:");
    map_list = map(list, cubic);
    foreach(map_list, print_space);


    puts("\n\n\n...testing foldl...\n");
    printf("Sum of the elements: %d\n", foldl(0, list, sum));
    printf("Maximal element: %d\n", foldl(INT_MIN, list, maximal));
    printf("Minimal element: %d\n", foldl(INT_MAX, list, minimal));


    puts("\n\n...testing map_mut...\n\nConverting every element to its absolute value:");
    map_mut(list, module);
    foreach(list, print_space);


    puts("\n\n\n...testing iterate...\n\nPowers of 2:");
    map_list = iterate(1, 10, pow_2);
    foreach(map_list, print_space);


    puts("\n\n\n...testing save...\n");
    if (save(list, "rewritten_list.txt"))
    {
        puts("Saving to \"rewritten_list.txt\" successful.");
    }
    else
    {
        puts("Error saving to \"rewritten_list.txt\".");
    }

    puts("\n\n...testing serialization...\n");
    if (serialize(list, "serialized"))
    {
        puts("Serialization successful.");
    }
    else
    {
        puts("Error while serializing.");
    }
    list_free(&list);

    puts("\n\n...testing deserialization...\n");
    if (deserialize(&list, "serialized"))
    {
        puts("Deserialization successful.\n");
    }
    else
    {
        puts("Error while deserializing.\n");
    }
    puts("Serialization/deserialization result: ");
    foreach(list, print_space);

    puts("\n\nClearing list...");
    list_free(&list);
    list_show(list);

    /*

    FILE * input_file;
    input_file = fopen("list.txt", "r");
    int e;
    struct linked_list *list = NULL;
    while (fscanf(input_file, "%d", &e) != EOF)
    {
        list_add_back(&list, e);
    }
    list_show(list);

    printf("\n\nElement #3:\n%d", list_get(list, 3));

    printf("\n\ndeleting list...");
    list_free(&list);
    list_show(list);

    */

    return 0;
}
