#include <stdio.h>
#include <stdlib.h>
#include "bmputil.h"

struct image* rotate_90cw(struct image const* original)
{
    int row;
    int col;

    struct image* result = (struct image*)malloc(sizeof(struct image));
    result->width = original->height;
    result->height = original->width;
    result->data = (struct pixel*)malloc(original->height*original->width* sizeof(struct pixel));

    for (row = 0; row < original->height; row++)
    {
        for (col = 0; col < original->width; col++)
        {
            result->data[((original->width - 1 - col) * original->height) + row] = original->data[row*original->width + col];
        }
    }
    return result;
}

