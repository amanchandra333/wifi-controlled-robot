import time
import sys
import socket
from threading import *

connected = False
# Set up socket connection for recieving and sending data to the server
serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#Bind to any incoming request at port 8000
host = "0.0.0.0"
port = 8000
print (host)
print (port)
serversocket.bind((host, port))
#Thread class to recieve data and implement methods
class client(Thread):
    def __init__(self, socket, address):
 	Thread.__init__(self)
        self.sock = socket
        self.addr = address
        self.start()

    def run(self):
	print "connected"
        connected = True
        while 1:
 	    pos = self.sock.recv(1024)
            if pos == 'F':
                print "recieved F"
                #do your stuff here
            elif pos == 'B':
                print "recieved B"
                #...
            else :
                print (pos)
                #...
            self.sock.send("recieved"+'\n')


def main():
    serversocket.listen(5)
    print ('server started and listening')
    while not connected:
        clientsocket, address = serversocket.accept()
        client(clientsocket, address)
	
    while connected:
        time.sleep(3)

main()
