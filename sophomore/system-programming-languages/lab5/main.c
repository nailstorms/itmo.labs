#include "bmputil.h"
#include "bmpio.h"

int main()
{

    struct image* image_beg = (struct image*) malloc (sizeof(struct image));
    switch(read_picture("test1.bmp", image_beg))
    {
        case READ_INVALID_BITS:
            puts("Incorrect data");
        case READ_INVALID_HEADER:
            puts("Incorrect file header");
        case READ_FILE_NOT_FOUND:
            puts("Error while trying to read file");
        case READ_OK:
            puts("Reading successful");
    }



    struct image* image_rot = rotate_90cw(image_beg);
    switch(write_picture("test90.bmp", image_rot))
    {
        case WRITE_FILENAME_NOT_FOUND:
            puts("Output file not found");
        case WRITE_IMAGE_NOT_FOUND:
            puts("No data to output");
        case WRITE_ERROR:
            puts("Output error");
        case WRITE_OK:
            puts("Writing successful");
    }


    return 0;
}
