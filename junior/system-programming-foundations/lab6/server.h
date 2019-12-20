/* ---------------- header files ---------------- */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <dirent.h>
#include <errno.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/un.h>
#include <sys/wait.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <signal.h>

/* ---------------- value macros ---------------- */
#define POOL_COUNT 16
#define PORT 8644
#define CLIENT_BUFSIZE 2048

/* ---------------- error codes ---------------- */
#define SOCKET_ERR -1
#define SETSOCKOPT_ERR -2
#define BIND_ERR -3
#define LISTEN_ERR -4
#define FORK_ERR -5
#define ACCEPT_ERR -6
#define READ_ERR -7

/* ---------------- functions ---------------- */
void handle_signals(int signo);
int fork_clients();
void return_error();
