#include <stdio.h>
#include "mem.h"

#ifndef __MEM_ALLOC_DEBUG_H_
#define __MEM_ALLOC_DEBUG_H_

void memalloc_debug_struct_info(void *p, char const *name, FILE *f, bool print_bytes);

#endif
