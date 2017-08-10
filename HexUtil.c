#include <string.h>
#include <stdlib.h>
#include "ipc.h"

#define OPEN 1
#define CLOSE 2

#define MAX_PATH_LEN 32767

int main(int argc, char const *argv[]) {
    if (argc > 1){
        Connection* conn = connectionCreate("HexEditor", CONN_TYPE_SUB);
        char* data;
        
        if (strcmp(argv[1], "open") == 0) {
            size_t len = 1;
            int i;
            for (i = 2; i < argc; i++) {
                char* abspath = malloc(MAX_PATH_LEN);
                realpath(argv[i], abspath);
                len += strlen(abspath) + 1;
            }
            data = malloc(len);
            data[0] = OPEN;
            
            size_t pos = 1;
            for (i = 2; i < argc; i++) {
                char* abspath = malloc(MAX_PATH_LEN);
                realpath(argv[i], abspath);
                memcpy(data + pos, abspath, strlen(abspath) + 1);
                pos += strlen(abspath) + 1;
            }
            
            Message* msg = messageCreate(data, len);
            messageSetSubject(msg, "com.mittudev.HexEditor.CommandLineUtil");
            connectionSend(conn, msg);
            messageDestroy(msg);
        }else if (strcmp(argv[1], "close") == 0) {
        	size_t len = 1;
            int i;
            for (i = 2; i < argc; i++) {
                char* abspath = malloc(MAX_PATH_LEN);
                realpath(argv[i], abspath);
                len += strlen(abspath) + 1;
            }
            data = malloc(len);
            data[0] = CLOSE;
            
            size_t pos = 1;
            for (i = 2; i < argc; i++) {
                char* abspath = malloc(MAX_PATH_LEN);
                realpath(argv[i], abspath);
                memcpy(data + pos, abspath, strlen(abspath) + 1);
                pos += strlen(abspath) + 1;
            }
            
            Message* msg = messageCreate(data, len);
            messageSetSubject(msg, "com.mittudev.HexEditor.CommandLineUtil");
            connectionSend(conn, msg);
            messageDestroy(msg);
        }else{
            data[0] = NULL;
            Message* msg = messageCreate(data, 1);
            messageSetSubject(msg, "com.mittudev.HexEditor.CommandLineUtil");
            connectionSend(conn, msg);
            messageDestroy(msg);
        }
        
        free(data);
        usleep(500);
        connectionDestroy(conn);
    }
    
    return 0;
}
