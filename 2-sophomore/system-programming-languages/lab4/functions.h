#include <stdlib.h>
#ifndef __LAB_4_SOME_FUNCTIONS_
#define __LAB_4_SOME_FUNCTIONS_

/**
 *  foreach - performs a specified function on each element of the list
 *  Input data:
 *  list - pointer to a list of integers
 *  func - pointer to a desired function
 */
void foreach(struct linked_list const* list, void (*func)(int));

/**
 *  map - performs a specified function on each element of the list and creates a new list from it
 *  Input data:
 *  list - pointer to a list of integers
 *  func - pointer to a desired function
 *
 *  Output data:
 *  struct linked_list* - pointer to the new list
 */
struct linked_list* map(struct linked_list const* list, int (*func)(int));

/**
 *  foldl - performs a specified function on the value of accumulator and the each next element of the list
 *  Input data:
 *  rax - original value of accumulator
 *  list - pointer to a list of integers
 *  func - pointer to a desired function
 *
 *  Output data:
 *  int - resulting value of accumulator
 */
int foldl(int rax, struct linked_list const* list, int (*func)(int, int));

/**
 *  map_mut - changes each element of the list by performing a function on it
 *  Input data:
 *  list - pointer to a list of integers
 *  func - pointer to a desired function
 */
void map_mut(struct linked_list* list, int (*func)(int));

/**
 *  iterate - generates a list of a specified length as [s, f(s), f(f(s))...]
 *  Input data:
 *  s - first value of a list
 *  n - number of elements in a list
 *  f - pointer to a desired function
 *
 *  Output data:
 *  struct linked_list* - pointer to the new list
 */
struct linked_list* iterate(int s, size_t n, int (*f)(int));



#endif // __LAB_4_SOME_FUNCTIONS_

