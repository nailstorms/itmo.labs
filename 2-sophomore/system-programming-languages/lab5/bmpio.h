#ifndef __LAB5_BMPIO_H_
#define __LAB5_BMPIO_H_

#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include "bmpmain.h"

enum bmp_read_code_t
{
    READ_OK = 0,
    READ_FILE_NOT_FOUND,
    READ_INVALID_BITS,
    READ_INVALID_HEADER,
};

enum bmp_write_code_t
{
    WRITE_OK = 0,
    WRITE_ERROR,
    WRITE_IMAGE_NOT_FOUND,
    WRITE_FILENAME_NOT_FOUND,
};


enum bmp_read_code_t read_picture(char const* filename, struct image* new_image);
enum bmp_write_code_t write_picture(char const* filename, struct image const* image);

#endif
