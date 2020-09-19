#include <sys/socket.h>
#include <sys/un.h>
#include "../../server.h"

#define SOCKET_PATH "/tmp/lab5-socket"

info_t* server_info;

int main () {

    server_info = malloc(sizeof(info_t));

    int sock_fd;
    if ((sock_fd = socket(AF_UNIX, SOCK_STREAM, 0)) < 0) {
        perror("Error while trying to create socket.");
        return 1;
    }

    struct sockaddr_un saddr;
    memset(&saddr, 0, sizeof(saddr));
    saddr.sun_family = AF_UNIX;
    strncpy(saddr.sun_path, SOCKET_PATH, sizeof(saddr.sun_path)-1);
    unsigned int saddr_size = sizeof(struct sockaddr_un);

    if (connect(sock_fd, (const struct sockaddr *) &saddr, saddr_size) < 0) {
         fprintf(stderr, "Server is not available right now. Please try again later.\n");
         return 1;
    }
    if (read(sock_fd, server_info, sizeof(info_t)) < 0) 
        perror("read");

    printf("\nUsing sockets. \n");

    printf("\nPID: %i,\nGID: %i,\nUID: %i\n", server_info->pid, server_info->gid, server_info->uid);
    printf("Working time: %li seconds\n", server_info->run_time);
    printf("Average server load time:\n \t For last minute - %.3lf,\n \tFor the last 5 minutes - %.3lf,\n \tFor the last 15 minutes - %.3lf\n",
           server_info->avg_load[0], server_info->avg_load[1], server_info->avg_load[2]);

    close(sock_fd);

    return 0;
}