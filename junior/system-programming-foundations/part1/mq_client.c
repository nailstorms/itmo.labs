#include <string.h>
#include "../server.h"

info_t* server_info;
msg_t msg;

int main () {

    key_t key = ftok("mq_key", PROJ_ID);

    errno = 0;
    int message = msgget(key, 0);
    if (message < 0) {
        fprintf(stderr, "Server data is not available right now. Please try again later.\n");
        return 1;
    }

    server_info = malloc(sizeof(info_t));
    msg.mtype = MSG_SERVER;

    if (msgsnd(message, &msg, sizeof(msg_t), 0) == -1) {
        fprintf(stderr, "Error while attempting to msgsnd.\n");
        return 1;
    }

    if (msgrcv(message, &msg, sizeof(msg_t), MSG_CLIENT, 0) < 0) {
        fprintf(stderr, "Error while attempting to msgrcv.\n");
        return 1;
    }

    memcpy(server_info, msg.mtext, sizeof(info_t));

    printf("\nUsing message_queue (task 2, client).\n");

    printf("\nPID: %i,\nGID: %i,\nUID: %i\n", server_info->pid, server_info->gid, server_info->uid);
    printf("Working time: %li seconds\n", server_info->run_time);
    printf("Average server load time:\n \t For last minute - %.3lf,\n \tFor the last 5 minutes - %.3lf,\n \tFor the last 15 minutes - %.3lf\n"
           server_info->avg_load[0], server_info->avg_load[1], server_info->avg_load[2]);

    return 0;
}