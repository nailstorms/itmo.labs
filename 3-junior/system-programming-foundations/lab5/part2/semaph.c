#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <semaphore.h>
#include <pthread.h>
#include <unistd.h>

sem_t semaphore;
volatile char sym_arr[26] = "abcdefghijklmnopqrstuvwxyz";

void* change_case() {
    while (1) {
        if (sem_wait(&semaphore) < 0) 
            perror("sem_wait for semaphore in change_case.");
        unsigned short i = 0;
        char temp_ch;
        while (sym_arr[i] != '\0') {
            temp_ch = sym_arr[i];
            if (temp_ch >= 'A' && temp_ch <= 'Z')
                sym_arr[i] = (char) (sym_arr[i] + 32);
            else if (temp_ch >= 'a' && temp_ch <= 'z')
                sym_arr[i] = (char) (sym_arr[i] - 32);
            i++;
        }
        sleep(1);
        printf("Current array: %s\n", sym_arr);
        if (sem_post(&semaphore) < 0) 
            perror("sem_post for semaphore in change_case.");
        sleep(1);
    }
}

void* reverse() {
    while (1) {
        if (sem_wait(&semaphore) < 0) 
            perror("sem_wait for semaphore in reverse.");
        unsigned short i = 0;
        char temp_ch;
        for (i = 0; i < 13; i++) {
            temp_ch = sym_arr[i];
            sym_arr[i] = sym_arr[25 - i];
            sym_arr[25 - i] = temp_ch;
        }
        sleep(1);
        printf("Current array: %s\n", sym_arr);
        if (sem_post(&semaphore) < 0) 
            perror("sem_post for semaphore in reverse.");
        sleep(1);
    }
}

int main () {

    pthread_t thread1;
    pthread_t thread2;

    if (sem_init(&semaphore, 0, 1) != 0) 
        perror("sem_init for semaphore in main.");

    if (pthread_create(&thread1, NULL, change_case, NULL) != 0 ||
    pthread_create(&thread2, NULL, reverse, NULL) != 0) {
        fprintf(stderr, "Error while creating threads.");
        return 1;
    }

    pthread_join(thread1, NULL);
    pthread_join(thread2, NULL);

    sem_destroy(&semaphore);

    return 0;
}