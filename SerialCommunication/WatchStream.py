from Communicator import *
import json
import os
if __name__ == "__main__":
    arduino = SerialComm()
    state = {}
    while True:
        v = arduino.read()
	if v:
	    # Has state changed?
	    if v[0] in state and v[1] == state[v[0]]: continue
	    state[v[0]] = v[1] 
	    if os.name != "nt":
	        os.system("clear")
		print "\nRobot Readings\n"
		print "BAUD RATE: ", arduino.baud_rate, "\n"
	        for s in sorted(state): print s, state[s]

