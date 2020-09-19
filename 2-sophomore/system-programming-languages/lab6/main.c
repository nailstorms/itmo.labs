#include <stdio.h>
#include <stdint.h>
#include <stdbool.h>
#include "mem.h"
#include "mem_debug.h"

int main()
{
    uint8_t *malloc_first = mem_alloc(32 * sizeof(uint8_t));
    for (int i = 0; i < 32; ++i) {
        malloc_first[i] = (uint8_t)i;
    }
    memalloc_debug_struct_info(malloc_first, "First memory allocation - mem_alloc(32 * sizeof(uint8_t))", stdout, true);

    uint8_t *malloc_second = mem_alloc(10000 * sizeof(uint8_t));
    memalloc_debug_struct_info(malloc_second, "Second memory allocation - mem_alloc(10000 * sizeof(uint8_t))", stdout, false);

    uint8_t *malloc_third = mem_alloc(1 * sizeof(uint8_t));
    memalloc_debug_struct_info(malloc_third, "Third memory allocation - mem_alloc(1 * sizeof(uint8_t))", stdout, true);

    mem_free(malloc_first);
    mem_free(malloc_second);
    mem_free(malloc_third);

    return 0;
}
