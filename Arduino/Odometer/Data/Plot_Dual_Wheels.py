import csv
import sys
#import numpy as np
import matplotlib.pyplot as plt
from Plot_Histogram import *
import math as m


# Right Rising Edge
r_r_edge = 1017
r_f_edge =  992

# Left Rising Edge
l_r_edge = 934
l_f_edge = 915

if len( sys.argv ) == 1:
    print "Please enter the csv file you want to plot!"
    sys.exit(0)

points = []
with open( sys.argv[1] ) as csvfile:
    reader = csv.reader(csvfile)
    points = [ c for c in reader]

    
def convert_reading( r ):
    return int(r)
    
left_vals  = [ convert_reading(v[0]) for v in points ]   
right_vals = [ convert_reading(v[1]) for v in points ]   
times      = [ int(t[2]) / 1000000.0 for t in points ]

    
def count_odom_hits(data,rising, falling):
    i = 0
    count = 0
    rising_events = []
    while i < len(data):
        while data[i] < rising:  
            i += 1
            if i >= len(data): return count, rising_events
        count += 1
        rising_events.append(i)
        while data[i] > falling: 
            i += 1
            if i >= len(data): return count, rising_events

    



def plot():
#xs = range( len( points ) )
    plt.plot(times, left_vals, label="Left Wheel")
    plt.plot(times, right_vals, label="Right Wheel")
    plt.ylabel("Sensor readings in Volts")
    plt.xlabel("Time in seconds")
    plt.show()



l_hits, l_is = count_odom_hits( left_vals, l_r_edge, l_f_edge)
r_hits, r_is = count_odom_hits( right_vals, r_r_edge, r_f_edge)

l_times = [ times[i] for i in l_is ]
r_times = [ times[i] for i in r_is ]

def extract_rpm( ts ):
    sig_is = [ i for i in range(len(ts)) if i != 0 and i % 5 == 0 ]
    last_stamp = ts[0]
    rpm = []
    for i in range(1,len(ts)):
    #for i in sig_is:
        rpm.append( 60.0 / ( (ts[i] - last_stamp) ) / 5  )
        last_stamp = ts[i]
    return rpm

def rpm_to_rad_p_s( t ):
    return 2*(m.pi) * t/60.0
def rpm_to_v( t ):
    return rpm_to_rad_p_s(t) * .068/2


l_rpm = extract_rpm(l_times)
r_rpm = extract_rpm(r_times)
print "Left Spoke Hits: ", l_rpm
show_hist(map( rpm_to_v,  l_rpm))
show_hist(map( rpm_to_v,  r_rpm))

#print "Right Spoke Hits: ", 
#extract_rpm(r_times)
plot()


