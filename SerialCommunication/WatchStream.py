from Communicator import *
import json
import os
import time
import atexit


stable_factor = 40
stable_c = 0

if __name__ == "__main__":
    arduino = SerialComm()
    atexit.register(arduino.close)
    state = {}
    inf = float("inf")
    start_time = time.time()
    r_o_f = inf
    l_o_f = inf
    r_t = start_time
    l_t = start_time
    dt = 1
    def print_state():
	if os.name != "nt":
	    os.system("clear")
	    print "\nRobot Readings\n"
	    print "BAUD RATE: ", arduino.baud_rate, "\n"
	    for s in sorted(state): print s, state[s]
	    print "\nOscillation Factor:\n"
	    print "Right Wheel Oscillation Factor:", r_o_f
	    print "Left Wheel Oscillation Factor:", l_o_f
	    print "Product Factor:", p_f 
	    print "Time:", time.time() - start_time

    try:
      while True:
        v = arduino.read()
	p_f = (l_o_f * r_o_f) 
	if v:
	    msg,val = v
            t = time.time()
	    # Has state changed?
	    if msg in state and val == state[msg]: continue
	    if str(msg) == "REPORTING_R_CONTROL" and msg in state:
		dt = t - r_t 
		r_t = t
	        r_o_f = (float(val) - float(state[msg]))/dt
	    if str(msg) == "REPORTING_L_CONTROL" and msg in state:
		dt = t - l_t 
		l_t = t
	        l_o_f = (float(val) - float(state[msg]))/dt
	    state[msg] = val
	    print_state()
    except KeyboardInterrupt:
      print "Exiting..."
    finally:
      arduino.close()
