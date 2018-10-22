#include <stdbool.h>
#include <stdlib.h>
#include "linked_list.h"
#include "functions.h"

void foreach(struct linked_list const* list, void (*func)(int))
{
    while (list->previous != NULL)
    {
        list = list-> previous;
    }

    while (list != NULL)
    {
        func(list->element);
        list = list->next;
    }
}

struct linked_list* map(struct linked_list const* list, int (*func)(int))
{
    struct linked_list* new_list = NULL;
    while (list != NULL)
    {
        list_add_front(&new_list, func(list->element));
        list = list->previous;
    }
    return new_list;
}

int foldl(int rax, struct linked_list const* list, int (*func)(int, int))
{
    while (list != NULL)
    {
        rax = func(rax, list->element);
        list = list->previous;
    }
    return rax;
}

void map_mut(struct linked_list* list, int (*func)(int))
{
    while (list != NULL)
    {
        list->element = func(list->element);
        list = list->previous;
    }
}

struct linked_list* iterate(int s, size_t n, int (*f)(int))
{
    struct linked_list* list = NULL;
    size_t i;
    for(i = 0; i < n; ++i){
        list_add_front(&list, s);
        s = f(s);
    }
    return list;
}
