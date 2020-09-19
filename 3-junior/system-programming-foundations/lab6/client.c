#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <netdb.h>
#include <string.h>
#include <sys/un.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdlib.h>

#define PORT 8644
#define CLIENT_BUFSIZE 255

int main(int argc, char *argv[])
{
    int i, r_len, socket_fd;
    char buf[CLIENT_BUFSIZE];
    struct sockaddr_in sock_addr;
    struct hostent *server;

    if (argc < 3)
    {
        fprintf(stderr, "Usage: %s server_name [directories...]\n", argv[0]);
        return -1;
    }
    for (i = 2; i < argc; i++)
    {
        if ((socket_fd = socket(AF_INET, SOCK_STREAM, 0)) < 0)
        {
            perror("socket");
            return EXIT_FAILURE;
        }

        server = gethostbyname(argv[1]);
        if ((server = gethostbyname(argv[1])) == NULL)
        {
            perror("gethostbyname");
            return EXIT_FAILURE;
        }

        memset((char *)&sock_addr, 0, sizeof(sock_addr));
        sock_addr.sin_family = AF_INET;
        memcpy((char *)&sock_addr.sin_addr.s_addr, (char *)server->h_addr, server->h_length);
        sock_addr.sin_port = htons(PORT);

        if (connect(socket_fd, (struct sockaddr *)&sock_addr, sizeof(sock_addr)) == -1)
        {
            perror("connect");
            return -4;
        }

        write(socket_fd, argv[i], strlen(argv[i]));
        write(socket_fd, "\n", 1);

        while ((r_len = read(socket_fd, buf, CLIENT_BUFSIZE)) > 0)
        {
            write(STDOUT_FILENO, buf, r_len);
            if (buf[r_len - 1] == 0 && buf[r_len - 2] == 0)
            {
                break;
            }
        }
        if (r_len < 0)
        {
            perror("read");
        }
    }
    close(socket_fd);
    return 0;
}
