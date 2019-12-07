#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <unistd.h>

volatile char sym_arr[26] = "abcdefghijklmnopqrstuvwxyz";
pthread_rwlock_t rwlock = PTHREAD_RWLOCK_INITIALIZER;
unsigned long main_interval;
unsigned long cc_interval;
unsigned long rev_interval;
unsigned long cap_interval;

void* change_case() {
    while (1) {
        usleep(cc_interval);
        unsigned short i = 0;
        char temp_ch;
        if (pthread_rwlock_wrlock(&rwlock) != 0) 
            perror("Write lock for rwlock in change_case.");
        while (sym_arr[i] != '\0') {
            temp_ch = sym_arr[i];
            if (temp_ch >= 'A' && temp_ch <= 'Z')
                sym_arr[i] = (char) (sym_arr[i] + 32);
            else if (temp_ch >= 'a' && temp_ch <= 'z')
                sym_arr[i] = (char) (sym_arr[i] - 32);
            i++;
        }
        if (pthread_rwlock_unlock(&rwlock) != 0) 
            perror("Unlock for rwlock in change_case.");
    }
}

void* reverse() {
    while (1) {
        usleep(rev_interval);
        unsigned short i = 0;
        char temp_ch;
        if (pthread_rwlock_wrlock(&rwlock) != 0) 
            perror("Write lock for rwlock in reverse.");
        for (i = 0; i < 13; i++) {
            temp_ch = sym_arr[i];
            sym_arr[i] = sym_arr[25 - i];
            sym_arr[25 - i] = temp_ch;
        }
        if (pthread_rwlock_unlock(&rwlock) != 0) 
            perror("Unlock for rwlock in reverse.");
    }
}

void* count_capitals() {
    while (1) {
        usleep(cap_interval);
        unsigned short i = 0;
        unsigned int count = 0;
        if (pthread_rwlock_rdlock(&rwlock) != 0) 
            perror("Read lock for rwlock in count_capitals.");
        for (i = 0; i < 26; i++) {
            if(sym_arr[i] >= 'A' && sym_arr[i] <= 'Z')
                count++;
        }
        printf("Capitals count: %d\n", count);
        if (pthread_rwlock_unlock(&rwlock) != 0) 
            perror("Unlock for rwlock in count_capitals.");
    }
}

unsigned long parse_ulong(const char* arg) {
    char *arg_end;
    unsigned long num = strtoul(arg, &arg_end, 10);
    if (*arg_end != '\0') {
        puts("NaN-argument. Please try again.");
        exit(1);
    }
    if (num <= 0) {
        puts("Can not supply negative numbers. Please try again.");
        exit(1);
    }
    return num;
}

int main (int argc, const char* argv[]) {

    if (argc != 5) {
        puts("usage: rlock [main_interval] [cc_interval] [rev_interval] [cap_interval]");
        return 0;
    }

    main_interval = parse_ulong(argv[1]);
    cc_interval = parse_ulong(argv[2]);
    rev_interval = parse_ulong(argv[3]);
    cap_interval = parse_ulong(argv[4]);

    pthread_t thread1;
    pthread_t thread2;
    pthread_t thread3;

    if (pthread_create(&thread1, NULL, change_case, NULL) != 0 ||
            pthread_create(&thread2, NULL, reverse, NULL) != 0 ||
            pthread_create(&thread3, NULL, count_capitals, NULL) != 0) {
        fprintf(stderr, "Error while creating threads.");
        return 1;
    }

    while (1) {
        usleep(main_interval);
        if (pthread_rwlock_rdlock(&rwlock) != 0) 
            perror("Read lock for rwlock in main thread.");
        printf("Array: %s\n", sym_arr);
        if (pthread_rwlock_unlock(&rwlock) != 0) 
            perror("Unlock for rwlock in main thread.");
    }

    return 0;
}