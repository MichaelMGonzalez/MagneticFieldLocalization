import serial
import os
import struct
import json
import sys
import time
import random as r
class SerialComm:
    def __init__(self, port_name=None, file_path = "SerialMsgHeader.json"):        
        # Load config file
	if port_name is None:
	    if os.name == "nt": port_name = "COM5"
	    #else: port_name = "/dev/ttyACM0"
	    else: port_name = "/dev/ttyUSB0"
        json_obj = json.load(open(file_path))
	self.config = json_obj
        self.v_to_msg = dict( [int(v["value"]),v["name"]] for v in json_obj["msgs"] )
        self.msg_to_type = dict( [v["name"],v["type"]] for v in json_obj["msgs"] )
        self.msg_to_v = dict( [v["name"],v["value"]] for v in json_obj["msgs"] )
        self.msg_sent = struct.pack( "c", chr([ v["value"] for v in json_obj["msgs"] if v["name"] == "MSG_SENT" ][0]  & 255) )
        self.baud_rate = int([ o["value"] for o in json_obj["setup"] if o["name"] == "BAUD_RATE" ][0])
        self.communicator = serial.Serial(port_name, self.baud_rate)
    def close(self):
        self.communicator.close()
    # Currently only will read a 4 byte floating point value
    def read(self):
        if self.communicator.inWaiting >=6: 
            f_b = ord(self.communicator.read())
            #print f_b
            if f_b in self.v_to_msg:
		msg = self.v_to_msg[f_b]
		t = None
		if self.msg_to_type[msg] == "float":
		    t = "f"
                    bs = self.communicator.read(size=4)
		if self.msg_to_type[msg] == "int":
		    t = "h"
                    bs = self.communicator.read(size=2)
                sanity_byte = self.communicator.read()
                if t and sanity_byte == self.msg_sent: 
                    return self.v_to_msg[f_b], struct.unpack(t,bs)[0]
    # Write a message
    def write(self,m_type,val):
	byte_vals = []
	msg = struct.pack("c", chr( m_type & 255 ) )
        if type( val ) == float:
            byte_vals = bytes(struct.pack("f", val ))
	self.write_byte( msg )
	#self.write_byte( struct.pack("c", chr(0) ))
	for b in byte_vals:
	    self.write_byte( b )
	#self.write_byte( struct.pack("c", chr(0) ))
	self.write_byte( self.msg_sent )
    def write_byte( self, b ):
        self.communicator.write(b)
	self.sleep()
    # Pause communicator for t seconds 
    def sleep( self, t=.005 ):
        time.sleep(t) 


if __name__ == "__main__":
    port_name = None
    if os.name == "nt":
        print "What is the serial port?"
        port_name = "COM" + raw_input("Port Name: ")
        print "Trying to connect to:", port_name

    comm = SerialComm(port_name=port_name)

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
        comm.write(msgs[i],r.gauss(0, 10.0))
        i = (i + 1) % len(msgs)
    exit()
