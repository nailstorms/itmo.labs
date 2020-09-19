#include <stdio.h>
#include <stdlib.h>
#include "lab3Library.h"


int scalar_product (int a[], int b[], size_t s) {
    int result = 0;
    for (size_t i = 0; i < s; i++)
        result += a[i]*b[i];
    return result;
}
