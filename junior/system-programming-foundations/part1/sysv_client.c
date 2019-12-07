#include "../server.h"

info_t* server_info;

int main () {
	
    key_t key = ftok("sysv_key", PROJ_ID);
		 
    errno = 0;
    int memory = shmget(key, sizeof(info_t), SHM_RDONLY);
    if (memory < 0) {
        fprintf(stderr, "Server data is not available right now. Please try again later.\n");
        return 1;
    }
    if ((server_info = shmat(memory, NULL,0)) == NULL) {
        fprintf(stderr, "Error while attempting to shmat.\n");
        return 1;
    }
	
	printf("\nUsing shared memory (task 1, client).\n");

    printf("\nPID: %i,\nGID: %i,\nUID: %i\n", server_info->pid, server_info->gid, server_info->uid);
    printf("Server works: %li seconds\n", server_info->run_time);
    printf("Average load system time:\n \t1 minute - %.3lf,\n \t5 minutes - %.3lf,\n \t15 minutes - %.3lf\n", 
		   server_info->avg_load[0], server_info->avg_load[1], server_info->avg_load[2]);
    return 0;
}