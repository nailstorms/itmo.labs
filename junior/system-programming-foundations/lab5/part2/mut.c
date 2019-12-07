#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <unistd.h>

volatile char sym_arr[26] = "abcdefghijklmnopqrstuvwxyz";
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
unsigned long main_interval;
unsigned long cc_interval;
unsigned long rev_interval;

void* change_case() {
    while (1) {
        usleep(cc_interval);
        unsigned short i = 0;
        char temp_char;
        if (pthread_mutex_lock(&mutex) != 0) 
            perror("Lock for mutex in change_case.");
        while (sym_arr[i] != '\0') {
            temp_char = sym_arr[i];
            if (temp_char >= 'A' && temp_char <= 'Z')
                sym_arr[i] = (char) (sym_arr[i] + 32);
            else if (temp_char >= 'a' && temp_char <= 'z')
                sym_arr[i] = (char) (sym_arr[i] - 32);
            i++;
        }
        if (pthread_mutex_unlock(&mutex) != 0) 
            perror("Unlock for mutex in change_case.");
    }
}

void* reverse() {
    while (1) {
        usleep(rev_interval);
        unsigned short i = 0;
        char temp_ch;
        if (pthread_mutex_lock(&mutex) != 0) 
            perror("Lock for mutex in reverse.");
        for (i = 0; i < 13; i++) {
            temp_ch = sym_arr[i];
            sym_arr[i] = sym_arr[25 - i];
            sym_arr[25 - i] = temp_ch;
        }
        if (pthread_mutex_unlock(&mutex) != 0) 
            perror("Unlock for mutex in reverse.");
    }
}

unsigned long parse_ulong(const char* arg) {
	char *arg_end;
	unsigned long num; 
	if (arg[0] == '-') {
		puts("Can not supply negative numbers. Please try again.");
		exit(1);
	}
	num = strtoul(arg, &arg_end, 10);
	if (*arg_end != '\0') {
		puts("NaN-argument. Please try again.");
		exit(1);
	}
	return num;
}

int main (int argc, const char* argv[]) {

    if (argc != 4) {
        puts("usage: mut [main_interval] [cc_interval] [rev_interval]");
        return 0;
    }

    main_interval = parse_ulong(argv[1]);
    cc_interval = parse_ulong(argv[2]);
    rev_interval = parse_ulong(argv[3]);

    pthread_t thread1;
    pthread_t thread2;

    pthread_mutex_init(&mutex, NULL);

    if (pthread_create(&thread1, NULL, change_case, NULL) != 0 ||
            pthread_create(&thread2, NULL, reverse, NULL) != 0) {
        fprintf(stderr, "Error while creating threads.");
        return 1;
    }

    while (1) {
        usleep(main_interval);
        if (pthread_mutex_lock(&mutex) != 0) 
            perror("Lock for mutex in main thread.");
        printf("Array: %s\n", sym_arr);
        if (pthread_mutex_unlock(&mutex) != 0) 
            perror("Unlock for mutex in main.");
    }

        return 0;
}