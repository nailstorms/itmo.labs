#include <string.h>
#include <signal.h>
#include "../server.h"

info_t* server_info;
int message;

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
	msgctl(message, IPC_RMID, NULL); 
	exit(1);
}

int main() {

    signal(SIGINT, shutdown_server);

    key_t key = ftok("mq_key", PROJ_ID);

    errno = 0;
    message = msgget(key, IPC_CREAT | PERM);
    if (message < 0) {
        fprintf(stderr,"Cannot create queue.\n");
        return 1;
    }

    server_info = malloc(sizeof(info_t));
    init_server(server_info);

    printf("Using message queue (task 2, server).\n");

    while(1) {
		
        msg_t msg;

        if (msgrcv(message, &msg, sizeof(msg_t), MSG_SERVER, 0) < 0) {
            fprintf(stderr, "Did not receive any text.\n");
        } else {
			update_info(server_info);
            memcpy(msg.mtext, server_info, sizeof(info_t));
            msg.mtype = MSG_CLIENT;
            if (msgsnd(message, &msg, sizeof(msg_t), 0) == -1) {
                fprintf(stderr, "Error while trying to send the message.\n");
            }
        }
        sleep(1);
    }
	
    return 0;
}