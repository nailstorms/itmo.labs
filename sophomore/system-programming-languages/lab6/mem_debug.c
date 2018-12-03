#include "mem_debug.h"
#include "mem.h"
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>

void memalloc_debug_struct_info(void *p, char const *name, FILE *f, bool print_bytes)
{
    mem *chunk = (mem *) ((uint8_t *) p - sizeof(mem));
    size_t isAllocated = chunk->capacity & 1;
    fprintf(f, "%s:\n", name);
    fprintf(f, "capacity: %lu, is allocated: %lu, next: %p", chunk->capacity & -2, isAllocated, chunk->next);

    if (print_bytes)
    {
        fprintf(f, "\n  Elements: ");
        for (int i = 0; i < (chunk->capacity & -2); ++i)
        {
            uint8_t elem = *((uint8_t *) chunk + sizeof(mem) + i * sizeof(uint8_t));
            fprintf(f, "%d ", elem);
        }
    }

    fprintf(f, "\n\n");
}

