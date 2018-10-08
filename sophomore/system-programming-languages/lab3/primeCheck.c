#include <stdio.h>
#include <stdlib.h>
#include "lab3Library.h"

int primeCheck (int x) {
    if (x<2)
        return 1;
    for (int i = 2; i*i <= x; i++)
        if (x%i==0) return 1;
    return 0;
}
