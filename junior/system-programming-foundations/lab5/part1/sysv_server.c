#include <signal.h>
#include "../server.h"

info_t* server_info;
int memory;

void init_server(info_t* server_info) {
    server_info->pid = getpid();
    server_info->uid = getuid();
    server_info->gid = getgid();
    time(&server_info->start_time);
    getloadavg(server_info->avg_load,3);
}

void update_info(info_t* server_info) {
    getloadavg(server_info->avg_load,3);
    time_t time_now;
    time(&time_now);
    server_info->run_time = time_now - server_info->start_time;
}

void shutdown_server() {
	puts("\nShutting down.");
	shmdt(server_info);
	shmctl(memory, IPC_RMID, NULL);
	exit(1);
}

int main() {

	signal(SIGINT,shutdown_server);
	
	key_t key = ftok("sysv_key", PROJ_ID);
		 
    errno = 0;
    memory = shmget(key, sizeof(info_t), IPC_CREAT | PERM);
	
    if (memory < 0) {
        fprintf(stderr, "Error while attempting to shmget.\n");
        return 1;
    }
    if ((server_info = shmat(memory, NULL, 0)) == NULL) {
        fprintf(stderr, "Error while attempting to shmat.\n");
        return 1;
    }

	printf("Using shared memory (task 1, server).\n");
	
    init_server(server_info);

    while(1) {
        update_info(server_info);
        sleep(1);
    }
	
    return 0;
}