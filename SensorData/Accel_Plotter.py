import csv
import sys
import numpy as np
import matplotlib.pyplot as plt

if len( sys.argv ) == 1:
    print "Please enter the csv file you want to plot!"
    sys.exit(0)

sets = []
print len(sys.argv)
for arg in sys.argv[1:]:
    with open( arg ) as csvfile:
        reader = csv.reader(csvfile)
        points = [ c for c in reader]
        sets.append(points)

#print points
for points in sets:
    xs = [ float(p[0]) for p in points  ]
    #ys = [ p[1] for p in points  ]
    zs = [ float(p[2]) for p in points  ]
    ts = [ float(p[3])/1000000000.0 for p in points  ]
    accel_xs = plt.plot(ts, xs, label="Acceleration in X m/s^2")
    #plt.plot(ts, ys)
    accel_zs = plt.plot(ts, zs, label="Acceleration in Z m/s^2")
    
    # Integration range
    time_range = range(1,len(ts))
    integration_values = [(ts[i]-ts[i-1]) for i in time_range]
    # Calculate velocities
    v_xs = [0 for t in ts] 
    for i in time_range:
        v_xs[i] = v_xs[i-1] + xs[i] * integration_values[i-1]
    v_zs = [0 for t in ts] 
    for i in time_range:
        v_zs[i] = v_zs[i-1] + zs[i] * integration_values[i-1]
    # Calculate displacement
    d_xs = [0 for t in ts] 
    for i in time_range:
        d_xs[i] = d_xs[i-1] + v_xs[i] * integration_values[i-1]
    d_zs = [0 for t in ts] 
    for i in time_range:
        d_zs[i] = d_zs[i-1] + v_zs[i] * integration_values[i-1]
    
    plt.plot(ts, v_xs, label="Velocity in X m/s")
    plt.plot(ts, v_zs, label="Velocity in Z m/s")
    plt.plot(ts, d_xs, label="Displacement in X m")
    plt.plot(ts, d_zs, label="Displacement in Z m")
    
    plt.legend()
    
plt.show()
