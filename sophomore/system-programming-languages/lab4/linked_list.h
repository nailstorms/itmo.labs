#ifndef __LAB_4_LINKED_INTEGER_LIST_
#define __LAB_4_LINKED_INTEGER_LIST_

#include <stdbool.h>
/**
 *  linked_list - a C linked list implementation
 *  consists of an unidentified number of integers
 *  besides integers themselves, also contains pointers to a previous (if any) or next (if any) integer
 */
struct linked_list {
    int element;
    struct linked_list* previous;
    struct linked_list* next;
};

/**
 *  list_create - creates a linked list with one integer
 *  Input data:
 *  e - first element of the list
 *
 *  Output data:
 *  struct linked_list* - pointer to the list
 */
struct linked_list* list_create(int e);

/**
 *  list_add_front - adds an integer to the head of the list
 *  Input data:
 *  list - pointer to the list
 *  e - appended integer
 */
void list_add_front(struct linked_list** list, int e);

/**
 *  list_add_back - adds an integer to the end of the list
 *  Input data:
 *  list - pointer to the list
 *  e - appended integer
 */
void list_add_back(struct linked_list** list, int e);

/**
 *  list_node_at - returns a pointer to the corresponding node
 *  Input data:
 *  list - pointer to the list
 *  index - index of the desired integer
 *
 *  Output data:
 *  struct linked_list* - pointer to the element (if exists, else NULL)
 */
struct linked_list* list_node_at(struct linked_list const* list, int index);

/**
 *  list_free - frees the memory allocated to all elements of the list
 *  Input data:
 *  list - pointer to the list
 */
void list_free(struct linked_list** list);

/**
 *  list_length - computes the length of the list
 *  Input data:
 *  list - pointer to the list
 *
 *  Output data:
 *  int - length of the list
 */
int list_length(struct linked_list const* list);

/**
 *  list_get - gets an element by its index
 *  Input data:
 *  list - pointer to the list
 *  index - index of the desired integer
 *
 *  Output data:
 *  int - value of integer or 0 if index is out of bounds
*/
int list_get(struct linked_list const* list, int index);

/**
 *  list_sum - gets an element by its index
 *  Input data:
 *  list - pointer to the list
 *
 *  Output data:
 *  int - sum of integers
 */
int list_sum(struct linked_list const* list);

/**
 *  list_show - prints all elements of the list to stdout
 *  Input data:
 *  list - pointer to the list
 */
void list_show(struct linked_list const* list);


/**
 *  load - loads linked list of integers from a file
 *  Input data:
 *  list - pointer to the list
 *  filename - file name
 *
 *  Returns true if reading from file was completed successfully,
 *  otherwise returns false.
 */
bool load(struct linked_list** list, const char* filename);

/**
 *  save - saves linked list of integers to a file
 *  Input data:
 *  list - pointer to the list
 *  filename - file name
 *
 *  Returns true if saving to file was completed successfully,
 *  otherwise returns false.
 */
bool save(struct linked_list* list, const char* filename);

/**
 *  serialize - saves linked list of integers to a binary file
 *  Input data:
 *  list - pointer to the list
 *  filename - file name
 *
 *  Returns true if saving to file was completed successfully,
 *  otherwise returns false.
 */
bool serialize(struct linked_list* list, const char* filename);

/**
 *  deserialize - loads linked list of integers from a binary file
 *  Input data:
 *  list - pointer to the list
 *  filename - file name
 *
 *  Returns true if reading from file was completed successfully,
 *  otherwise returns false.
 */
bool deserialize(struct linked_list** list, const char* filename);
#endif // __LAB_4_LINKED_INTEGER_LIST_
