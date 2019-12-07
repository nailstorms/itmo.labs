#include <fcntl.h>
#include <signal.h>
#include "../server.h"

info_t* server_info;
int fd;

int main () {
		 
    errno = 0;
    if ((fd = open(MMAP_FILENAME, O_RDWR, 0)) < 0) {
       fprintf(stderr, "Server data is not available right now. Please try again later.\n");
       return 1;
    }

    errno = 0;
    if ((server_info = (info_t*) mmap(NULL, sizeof(info_t), PROT_READ, MAP_SHARED, fd, 0)) == MAP_FAILED) {
       fprintf(stderr, "Error while attempting to mmap.\n");
       return 1;
    }
	
	printf("\nUsing memory mapped file (task 3, client).\n");

    printf("\nPID: %i,\nGID: %i,\nUID: %i\n", server_info->pid, server_info->gid, server_info->uid);
    printf("Working time: %li seconds\n", server_info->run_time);
    printf("Average server load time:\n \t For last minute - %.3lf,\n \tFor the last 5 minutes - %.3lf,\n \tFor the last 15 minutes - %.3lf\n", 
		   server_info->avg_load[0], server_info->avg_load[1], server_info->avg_load[2]);
    return 0;
}