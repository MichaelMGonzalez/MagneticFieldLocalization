from Communicator import *
from PID_Q_Lerner import *
import json
import os
import time
import atexit


t_fmt = "{00:g}"
inf = float("inf")
stable_factor = 40
timeout = 13
timeout_penalty = 100
count_until = 2

class PIDLerner:
    def __init__(self, learning_factor = .1):
        self.arduino = SerialComm()
	self.log_file = "data_log_r10.json"
        self.state = {}
	self.ittr = 0
	self.reset()
	self.global_start = time.time()
	self.times = []
	self.learning_space = SearchSpace(res = 10 )
	if os.path.exists( self.log_file ):
	    self.learning_space.load_from_file( self.log_file )
	self.curr_node = self.learning_space.get_random_node()
	self.get_next_node()
	self.l_f = learning_factor
	self.best_time = inf
	self.best_t_coord = None
    def reset(self):
        self.stable_t = inf
        self.sim_start = time.time()
        self.r_o_f = inf
        self.l_o_f = inf
	self.p_f   = inf
        self.r_t = self.sim_start
        self.l_t = self.sim_start
	# Reset Arduino
	self.arduino.write("STOP", None)
	time.sleep(.2)
        self.arduino.write("MOVE_FORWARD", None)
        self.state["REPORTING_STATE"] = 0
	#for k in self.state: self.state[k] = inf
    def print_state(self):
	if os.name != "nt":
	    os.system("clear")
	    global_t = int(time.time() - self.global_start)
	    s = str(global_t % 60).zfill(2)
	    m = str((global_t / 60)%60).zfill(2)
	    h = str(global_t / 3600) 
	    print "\nUptime:", str( h + ":" + m + ":" + s )
	    print "\nIteration:", str(self.ittr)
	    t = time.time() - self.sim_start
	    s_t = t - self.stable_t
	    if s_t == t: s_t = 0
	    print "\nRobot Readings\n"
	    print "BAUD RATE: ", self.arduino.baud_rate, "\n"
	    for s in sorted(self.state): print s, self.state[s]
	    print "\nOscillation Factor:\n"
	    print "Right Wheel Oscillation Factor:", self.r_o_f
	    print "Left Wheel Oscillation Factor:", self.l_o_f
	    print "Product Factor:", self.p_f 
	    print "Stable for",  s_t, "seconds"
	    print "Time:", t 
	    #print "Times Collected:", self.times
	    #print self.learning_space
	    print "\nBest Time", self.best_time
	    print "Best Time Observed at: ", str( self.best_t_coord )

    def set_new_pd_vals( self, msg_delay=.3 ):
	self.reset()
        n = self.curr_node 
        self.arduino.write("SET_P", float(n.p))
	time.sleep(msg_delay)
        self.arduino.write("SET_D", float(n.d))
	time.sleep(msg_delay)
    def q_update( self, reward ):
	self.ittr += 1
	self.curr_node.times.append(reward)
        e = self.prev_edge
	e.weight = ( 1.0 - self.l_f ) * e.weight 
	e.weight += self.l_f * reward 
	self.get_next_node()
	self.set_new_pd_vals()
	self.print_state()
	self.learning_space.dump_to_file( self.log_file )
    def get_next_node( self ):
        self.prev_edge = self.curr_node.get_min_edge()
	self.curr_node = self.prev_edge.other
    def check_threshold( self, t ):
	# Has the threshold been reached?
        if abs(self.p_f) < stable_factor:
   	    if not self.stable_t: self.stable_t = t
	    if t-self.stable_t > count_until: 
	        self.times.append( t )
		if t < self.best_time:
		    self.best_time = t
		    self.best_t_coord = self.curr_node
		self.q_update(t)
	else: self.stable_t = 0 
    def run_lerner(self):
      has_run = False
      try:
	  state = self.state
          while True:
	    self.learning_space.active = self.curr_node
            t = time.time() - self.sim_start
	    if t > timeout: self.q_update( timeout_penalty ) 
	    self.p_f = (self.l_o_f * self.r_o_f) 
	    self.check_threshold(t)
	    v = None
	    if self.arduino.communicator.inWaiting():
                v = self.arduino.read()
	    if v:
	        if not has_run:
		    self.set_new_pd_vals()
		    self.arduino.write("MOVE_FORWARD", None)
		    has_run = True
	        msg,val = v
	        # Has state changed?
	        if msg in state and val == state[msg]: continue
	        if str(msg) == "REPORTING_R_CONTROL" and msg in state:
		    dt = t - self.r_t 
		    self.r_t = t
	            self.r_o_f = (float(val) - float(state[msg]))/dt
	        if str(msg) == "REPORTING_L_CONTROL" and msg in state:
		    dt = t - self.l_t 
		    self.l_t = t
	            self.l_o_f = (float(val) - float(state[msg]))/dt
	        state[msg] = val
	        self.print_state()
      except KeyboardInterrupt:
          print "Exiting..."
      finally:
	  self.arduino.write("STOP", None)
          self.arduino.close()
	  self.learning_space.dump_to_file( self.log_file )
if __name__ == "__main__":
    lerner = PIDLerner()
    lerner.run_lerner()
