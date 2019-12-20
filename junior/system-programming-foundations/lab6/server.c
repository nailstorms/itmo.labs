#include "server.h"

static int socket_fd;

void handle_signals(int signo)
{
    switch (signo)
    {
    case SIGINT:
        close(socket_fd);
        _exit(EXIT_SUCCESS);
        break;
    case SIGPIPE:
        break;
    }
}

void return_error(char *buf1, char *buf2, int client_fd)
{
    char str[CLIENT_BUFSIZE + 32];
    int t = sprintf(str, "%s: %s\n", buf1, buf2);
    write(client_fd, str, t);
    write(client_fd, "\0\0", 2);
}

int fork_clients()
{
    int client_fd, read_str, str_temp;
    char buf[CLIENT_BUFSIZE], str[CLIENT_BUFSIZE + 32];
    DIR *dir;
    struct dirent *content;
    memset((char *)&buf, 0, CLIENT_BUFSIZE);

    while (1)
    {
        client_fd = accept(socket_fd, NULL, NULL);
        if (client_fd < 0)
        {
            if (errno != EWOULDBLOCK)
            {
                perror("accept");
                return ACCEPT_ERR;
            }
            continue;
        }

        while (1)
        {
            read_str = 0;
            do
            {
                read_str += read(client_fd, buf + read_str, CLIENT_BUFSIZE);
                if (read_str == -1 && errno != EWOULDBLOCK)
                {
                    perror("read");
                    close(client_fd);
                    return READ_ERR;
                }
            } while (read_str <= 0 || !((buf[read_str - 1] == '\n') || (buf[read_str - 1] == '\r')));

            while (buf[read_str - 1] == '\n')
                read_str--;
            if (buf[read_str - 1] == '\r')
                read_str--;
            buf[read_str] = '\0';

            if ((dir = opendir(buf)) == NULL)
            {
                return_error(buf, strerror(errno), client_fd);
                continue;
            }

            str_temp = sprintf(str, "%s contents:\n", buf);
            write(client_fd, str, str_temp);
            while (1)
            {
                errno = 0;
                content = readdir(dir);
                if (content == NULL)
                {
                    if (errno == 0)
                    {
                        write(client_fd, "\0\0", 2);
                        break;
                    }
                    else
                        continue;
                }
                str_temp = sprintf(str, "\t%s\n", content->d_name);
                write(client_fd, str, str_temp);
            }
            write(client_fd, "\0\0", 2);
            closedir(dir);
        }
    }
}

int main()
{
    int i, opt_val = 1;
    struct sockaddr_in sock_addr;
    struct sigaction action;

    signal(SIGPIPE, SIG_IGN);

    if ((socket_fd = socket(AF_INET, SOCK_STREAM, 0)) == -1)
    {
        perror("socket");
        return SOCKET_ERR;
    }
    if (setsockopt(socket_fd, SOL_SOCKET, SO_REUSEADDR, (const void *)&opt_val, sizeof(opt_val)) == -1)
    {
        perror("setsockopt");
        return SETSOCKOPT_ERR;
    }

    action.sa_handler = handle_signals;

    sock_addr.sin_family = AF_INET;
    sock_addr.sin_addr.s_addr = INADDR_ANY;
    sock_addr.sin_port = htons(PORT);

    if (bind(socket_fd, (struct sockaddr *)&sock_addr, sizeof(sock_addr)) == -1)
    {
        perror("bind");
        return BIND_ERR;
    }

    if (listen(socket_fd, 17) == -1)
    {
        perror("listen");
        return LISTEN_ERR;
    }

    printf("Server started.\n");

    for (i = 0; i < POOL_COUNT; i++)
    {
        switch (fork())
        {
        case -1:
            perror("fork");
            exit(FORK_ERR);
            break;
        case 0:
            sigaction(SIGINT, &action, NULL);
            fork_clients();
            break;
        }
    }

    while (waitpid(-1, NULL, 0) > 0);

    return EXIT_SUCCESS;
}
