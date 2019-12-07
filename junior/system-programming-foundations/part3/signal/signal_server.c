#include <signal.h>
#include "../../server.h"

info_t* server_info;

void server_init (info_t* server_info) {
    server_info->pid = getpid();
    server_info->uid = getuid();
    server_info->gid = getgid();
    time(&server_info->start_time);
    getloadavg(server_info->avg_load,3);
}

void update_info (info_t* server_info) {
    getloadavg(server_info->avg_load,3);
    time_t time_now;
    time(&time_now);
    server_info->run_time = time_now - server_info->start_time;
}

int set_signal_action(int sig, void (*sig_handler)(int)) {
    struct sigaction action;
	sigset_t mask;
    action.sa_handler = sig_handler;
    action.sa_flags = 0;
	action.sa_mask  = mask;
    sigemptyset(&mask);
    if (sigaction(sig, &action, NULL) == 0) 
        return 0; 
    else 
        return 1;
}

void get_pid() { printf("PID: %i\n", server_info->pid); }
void get_gid() { printf("GID: %i\n", server_info->gid); }
void get_uid() { printf("UID: %i\n", server_info->uid); }
void get_work_time() { printf("Working time: %li seconds\n", server_info->run_time); }
void get_avg_load() { printf("Average server load time:\n \t For last minute - %.3lf,\n \tFor the last 5 minutes - %.3lf,\n \tFor the last 15 minutes - %.3lf\n",
                          server_info->avg_load[0], server_info->avg_load[1], server_info->avg_load[2]); }

int handle_signals() {
	if (set_signal_action(SIGHUP,  get_pid) != 0) return 1;
    if (set_signal_action(SIGINT,  get_uid) != 0) return 1;
    if (set_signal_action(SIGTERM, get_gid) != 0) return 1;
    if (set_signal_action(SIGUSR1, get_work_time) != 0) return 1;
    if (set_signal_action(SIGUSR2, get_avg_load) != 0) return 1;
	return 0;
}

int main() {

    server_info = malloc(sizeof(info_t));
    server_init(server_info);
	
	if (handle_signals() != 0) { 
        puts("Error while attempting to set signal handlers."); 
        return 1; 
    }
	
    printf("Server started.\n");

    while(1) {
        update_info(server_info);
        sleep(1);
    }

    return 0;
}