#include <stdio.h>
#include <stdlib.h>
#include "lab3Library.h"

extern int x[], y[];

int main()
{
    size_t axis_n;
    printf("Enter the number of axis: ");
    scanf("%d", &axis_n);

    int x[axis_n], y[axis_n];

    printf("Enter two vectors, they will be contained in separate arrays\nVector a: ");
    for (size_t i = 0; i<axis_n; i++)
        scanf("%d", &x[i]);


    printf("Vector b: ");
    for (size_t i = 0; i<axis_n; i++)
        scanf("%d", &y[i]);

    printf("The scalar product: %d\n\n", scalarProduct(x,y,sizeof(x)/sizeof(x[0])));

    int p, check = 0;
    printf("Enter a number for a prime check: ");
    while (!check)
    {
        if(scanf("%d", &p))
        {
            if (p<0)
                printf("Input is lesser than 0. Please try again.\n");
            else
            {
                check++;
                printf("Is prime: ");
                primeCheck(p) ? printf("No\n") : printf("Yes\n");
            }
        }
        else
            printf("Input is NaN. Please try again.\n");
    }

    return 0;
}
