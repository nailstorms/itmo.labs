#include <stdint.h>
#include <sys/mman.h>
#include <assert.h>
#include "mem.h"

static uint8_t *mem_start = NULL;

void* mem_init()
{
    mem_start = mmap(MEM_START, MEM_INIT_SIZE, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS | MAP_FIXED, 0, 0);
    ((mem*) mem_start)->next = NULL;
    ((mem*) mem_start)->capacity =  MEM_INIT_SIZE  - sizeof(mem);
    return (void*) mem_start;
}

void coalesce_free_chunks(mem *ptr)
{
    assert(ptr != NULL);
    assert((ptr->capacity & 1) == 0);
    while (ptr->next != NULL && ((ptr->next)->capacity & 1) == 0)
    {
        if (ptr->next != (mem *) ((uint8_t *) ptr + sizeof(mem) + ptr->capacity))
            return;

        ptr->capacity += ptr->next->capacity + sizeof(mem);
        ptr->next = ptr->next->next;
    }
}

static void allocate_chunk(mem *last, size_t size)
{
    assert(last != NULL);
    assert(size != 0);

    void *heap_end = (uint8_t *) last + last->capacity + sizeof(mem);

    size_t add_size = size + 2 * MEM_INIT_SIZE - (size % MEM_INIT_SIZE);
    void *add_memory = mmap(heap_end, add_size, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS | MAP_FIXED, 0, 0);

    if (add_memory != MAP_FAILED && (last->capacity & 1) == 0)
    {
        last->capacity += add_size;
        return;
    }

    if (add_memory == MAP_FAILED)
        add_memory = mmap(heap_end, add_size, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS, 0, 0);

    mem *to_head = (mem*) add_memory;
    to_head->next = NULL;
    to_head->capacity = add_size;
    last->next = to_head;
}

static void split_chunk(mem *mem_chunk, size_t size)
{
    assert(mem_chunk != NULL);
    assert((mem_chunk->capacity & 1) == 0);
    assert(size != 0);
    assert(mem_chunk->capacity >= size);
    size_t remainder = mem_chunk->capacity - size;
    if (remainder >= MEM_MIN_SIZE)
    {
        mem *right_chunk = (mem *) (((uint8_t *) mem_chunk) + sizeof(mem) + size);
        right_chunk->capacity = remainder - sizeof(mem);
        right_chunk->next = mem_chunk->next;
        mem_chunk->next = right_chunk;
        mem_chunk->capacity -= remainder;
    }
}

void* mem_alloc(size_t requested_size)
{
    if (requested_size == 0)
        return NULL;
    if (requested_size % CHUNK_ALIGN != 0)
        requested_size += CHUNK_ALIGN - (requested_size % CHUNK_ALIGN);

    if (mem_start == NULL)
        mem_init();
    mem *mem_chunk = (mem *) mem_start;

    while (1)
    {
        if ((mem_chunk->capacity & 1) == 0)
        {
            coalesce_free_chunks(mem_chunk);
            if (mem_chunk->capacity >= requested_size)
                break;
        }

        if (mem_chunk->next == NULL)
            allocate_chunk(mem_chunk, requested_size);
        else
            mem_chunk = mem_chunk->next;
    }

    split_chunk(mem_chunk, requested_size);
    mem_chunk->capacity = mem_chunk->capacity | 1;
    return (void *) (mem_chunk + 1);
}

void mem_free(void* ptr)
{
    mem *mem_chunk = (mem *) ((uint8_t *) ptr - sizeof(mem));
    assert((mem_chunk->capacity & 1) == 1 && "Memory is already freed");

    mem_chunk->capacity = mem_chunk->capacity & -2;
    coalesce_free_chunks(mem_chunk);
}
