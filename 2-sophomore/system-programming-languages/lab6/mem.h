#ifndef __MEM_ALLOC_H_
#define __MEM_ALLOC_H_

#include <stddef.h>
#include <stdbool.h>
#include <unistd.h>

#define MEM_START ((void*) 0x04040000)
#define MEM_INIT_SIZE ((size_t)(sysconf(_SC_PAGESIZE)))

#define CHUNK_ALIGN 8
#define MEM_MIN_SIZE (sizeof(mem) + CHUNK_ALIGN)

#pragma pack(push, 1)
typedef struct mem {
  struct mem* next;
  size_t capacity;
} mem;
#pragma pack(pop)

void* mem_init();

void* mem_alloc(size_t requested_size);

void mem_free(void* ptr);

#endif
