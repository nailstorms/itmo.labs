#ifndef SERVER_H
#define SERVER_H

#include <stdio.h>
#include <unistd.h>
#include <time.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/mman.h>

#define PERM 0644
#define PROJ_ID 13
#define MSG_CLIENT 1
#define MSG_SERVER 2
#define MMAP_FILENAME "mmap_file"

typedef struct info_t {
    pid_t pid;
    uid_t uid;
    gid_t gid;
    time_t start_time;
    time_t run_time;
    double avg_load[3];
} info_t;

typedef struct msg_t {
	long mtype;
	char mtext[sizeof(info_t)];
} msg_t;

#endif
