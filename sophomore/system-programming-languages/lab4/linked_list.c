#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include "linked_list.h"

struct linked_list* list_create(int e)
{
    struct linked_list *new_list = (struct linked_list*)malloc(sizeof(struct linked_list));
    new_list->element = e;
    new_list->previous = NULL;
    new_list->next = NULL;
    return new_list;
}

void list_add_front(struct linked_list** list, int e)
{
    if (*list == NULL)
    {
        *list = list_create(e);
        return;
    }
    struct linked_list *new_list = (struct linked_list*)malloc(sizeof(struct linked_list));
    new_list->element = e;
    new_list->previous = NULL;
    struct linked_list* tmp_list = *list;
    while(tmp_list->previous != NULL)
    {
        tmp_list = tmp_list->previous;
    }
    new_list->next = tmp_list;
    tmp_list->previous = new_list;
}

void list_add_back(struct linked_list** list, int e)
{
    if (*list == NULL)
    {
        *list = list_create(e);
        return;
    }
    struct linked_list *new_list = (struct linked_list*)malloc(sizeof(struct linked_list));
    new_list->element = e;
    new_list->next = NULL;
    new_list->previous = *list;
    (*list)->next = new_list;
    *list = new_list;
}


struct linked_list* list_node_at(struct linked_list const* list, int index)
{
    int i = 0;
    if (list == NULL)
    {
        return NULL;
    }
    while (list->previous != NULL)
    {
        list = list->previous;
    }
    while (i != index)
    {
        ++i;
        list = list->next;
        if (list == NULL)
        {
            return NULL;
        }
    }
    return list;
}

void list_free(struct linked_list** list)
{
    while (*list != NULL)
    {
        struct linked_list* previous = (*list)->previous;
        free(*list);
        (*list) = previous;
    }
}

int list_length(struct linked_list const* list)
{
    int i = 0;
    while (list != NULL)
    {
        struct linked_list* previous = list->previous;
        list = previous;
        ++i;
    }
    return i;
}

int list_get(struct linked_list const* list, int index)
{
    struct linked_list* ret = list_node_at(list, index);
    return ret == NULL ? 0 : ret->element;
}

int list_sum(struct linked_list const* list)
{
    int sum = 0;
    while (list != NULL)
    {
        sum += list->element;
        list = list->previous;
    }
    return sum;
}

void list_show(struct linked_list const* list)
{
    if (list == NULL)
    {
        printf("List is empty\n\n");
        return;
    }
    while (list->previous != NULL)
    {
        list = list->previous;
    }
    printf("List: ");
    while (list != NULL)
    {
        printf("%d ", list->element);
        list = list->next;
    }
    printf("\n\n");
}

bool load(struct linked_list** list, const char* filename)
{
    int e;
    FILE* in = fopen(filename, "r");
    if (in == NULL)
    {
        return false;
    }
    while (fscanf(in, "%d", &e) != EOF)
    {
        list_add_back(list, e);
    }
    fclose(in);
    return true;
}

bool save(struct linked_list* list, const char* filename)
{
    FILE* out = fopen(filename, "w+");
    if (out == NULL)
    {
        return false;
    }
    while (list != NULL)
    {
        fprintf(out, "%d ", list->element);
        list = list->previous;
    }
    fclose(out);
    return true;
}

bool serialize(struct linked_list* list, const char* filename)
{
    FILE* out = fopen(filename, "w+");
    if (out == NULL)
    {
        return false;
    }
    size_t sz = list_length(list);
    int i;
    int* buf = (int*)malloc(sz*sizeof(int));
    for (i = 0; i < sz; ++i)
    {
        buf[i] = list->element;
        list = list->previous;
    }
    fwrite(buf, sz, sizeof(int), out);
    fclose(out);
    return true;
}

bool deserialize(struct linked_list** list, const char* filename)
{
    FILE* in = fopen(filename, "r");
    if (in == NULL)
    {
        return false;
    }
    fseek(in, 0, SEEK_END);
    long sz = ftell(in);
    int i;
    rewind(in);

    int* buf = (int*)malloc(sz* sizeof(int));
    fread(buf, sz/sizeof(int), sizeof(int), in);

    for(i = 0; i < sz/ sizeof(int); ++i)
    {
        list_add_front(list, buf[i]);
    }
    fclose(in);
    return true;
}
