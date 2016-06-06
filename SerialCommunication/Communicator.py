import serial
import os
import struct
import json
import sys

file_path = "SerialMsgHeader.json"

f = open(file_path)
json_obj = json.load(f)

port_name = ""
if os.name == "nt":
    print "What is the serial port?"
    port_name = "COM" + raw_input("Port Name: ")
    print "Trying to connect to:", port_name
else: print "OS NOT SUPPORTED"

value_to_msg_map = dict( [int(v["value"]),v["name"]] for v in json_obj["msgs"] )
msg_sent = struct.pack( "c", chr([ v["value"] for v in json_obj["msgs"] if v["name"] == "MSG_SENT" ][0]  & 255) )
baud_rate = int([ o["value"] for o in json_obj["setup"] if o["name"] == "BAUD_RATE" ][0])


communicator = serial.Serial(port_name, baud_rate)

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
    if communicator.in_waiting >=6: 
        f_b = ord(communicator.read())
        #print f_b
        if f_b in value_to_msg_map:
            bs = communicator.read(size=4)
            sanity_byte = communicator.read()
            if sanity_byte == msg_sent: 
                print value_to_msg_map[f_b], struct.unpack("f",bs)[0]
exit()