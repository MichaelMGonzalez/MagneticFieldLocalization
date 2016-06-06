import csv
import sys
#import numpy as np
import matplotlib.pyplot as plt



# Right Rising Edge
r_r_edge = 1007
r_f_edge =  992

# Left Rising Edge
l_r_edge = 923
l_f_edge = 900

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



print "Left Spoke Hits: ", l_is

print "Right Spoke Hits: ", r_is
#plot()


