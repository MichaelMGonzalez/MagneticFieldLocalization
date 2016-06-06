import serial
import os
import struct


port_name = ""
if os.name == "nt":
    print "What is the serial port?"
    port_name = "COM" + raw_input("Port Name: ")
    print "Trying to connect to:", port_name
else: print "OS NOT SUPPORTED"

communicator = serial.Serial(port_name, 9600)

print "Would you like to read or write? (r/w)"
state = None
while not state:
    user_input = raw_input(">> ").lower()
    if user_input in ["r", "read"]:
        state = "reading"
    elif user_input in ["w", "write"]:
        state = "writing"
    else:
        print "Invalid mode. Please either select read or write (r/w)"

        
print "You have selected: ", state
while state == "reading":
    if communicator.in_waiting >= 4: 
         
        bs = communicator.read(size=4)
        #print bs
        print struct.unpack("f",bs)[0]
exit()