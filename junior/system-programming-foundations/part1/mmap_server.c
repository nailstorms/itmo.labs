#include <fcntl.h>
#include <signal.h>
#include "../server.h"

info_t* server_info;
int fd;

void init_server(info_t* server_info) {
    server_info->pid = getpid();
    server_info->uid = getuid();
    server_info->gid = getgid();
    time(&server_info->start_time);
    getloadavg(server_info->avg_load, 3);
}

void update_info(info_t* server_info) {
    getloadavg(server_info->avg_load, 3);
    time_t time_now;
    time(&time_now);
    server_info->run_time = time_now - server_info->start_time;
}

void shutdown_server() {
	puts("\nShutting down.");
	munmap(server_info, sizeof(info_t));
	close(fd);
	remove(MMAP_FILENAME);
	exit(1);
}

int main() {

	signal(SIGINT, shutdown_server);
	
	errno = 0;
	if ((fd = open(MMAP_FILENAME, O_RDWR | O_CREAT, PERM)) < 0) {
        fprintf(stderr, "Cannot open file for memory map.\n");
        return 1;
   }

    errno = 0;
    if (truncate(MMAP_FILENAME, sizeof(info_t)) < 0) {
        fprintf(stderr, "Unable to truncate file for memory map.\n");
        return 1;
    }
	
    errno = 0;
    if ((server_info = (info_t*) mmap(NULL, sizeof(info_t), PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0)) == MAP_FAILED) { 
        fprintf(stderr, "Error while attempting to mmap.\n");
        return 1;
	}

	printf("Using memory mapped file (task 3, server).\n");
	
    init_server(server_info);

    while(1) {
        update_info(server_info);
        sleep(1);
    }
	
    return 0;
}