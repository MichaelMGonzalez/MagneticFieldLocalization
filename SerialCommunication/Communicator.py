import serial
import os
import struct
import json
import sys
import time
class SerialComm:
    def __init__(self, port_name="COM5", file_path = "SerialMsgHeader.json"):        
        # Load config file
        json_obj = json.load(open(file_path))
        self.v_to_msg = dict( [int(v["value"]),v["name"]] for v in json_obj["msgs"] )
        self.msg_sent = struct.pack( "c", chr([ v["value"] for v in json_obj["msgs"] if v["name"] == "MSG_SENT" ][0]  & 255) )
        self.baud_rate = int([ o["value"] for o in json_obj["setup"] if o["name"] == "BAUD_RATE" ][0])
        self.communicator = serial.Serial(port_name, self.baud_rate)
    def read(self):
        if self.communicator.in_waiting >=6: 
            f_b = ord(self.communicator.read())
        #print f_b
            if f_b in self.v_to_msg:
                bs = self.communicator.read(size=4)
                sanity_byte = self.communicator.read()
                if sanity_byte == self.msg_sent: 
                    print self.v_to_msg[f_b], struct.unpack("f",bs)[0]
    def write(self,type,val):
        self.communicator.write( struct.pack("c", chr( type & 255 ) ))


if __name__ == "__main__":
    port_name = ""
    if os.name == "nt":
        print "What is the serial port?"
        port_name = "COM" + raw_input("Port Name: ")
        print "Trying to connect to:", port_name
    else: print "OS NOT SUPPORTED"

    comm = SerialComm()

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
        comm.read()
    i = 0
    msgs = [ 2 ** p for p in range(8) ]
    while state == "writing":
        comm.write(msgs[7-i],i)
        i = (i + 1) % len(msgs)
        time.sleep(.01)
    exit()