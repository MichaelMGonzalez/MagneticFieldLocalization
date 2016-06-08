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
    #accel_xs = plt.plot(ts, xs, label="Xs")
    #plt.plot(ts, ys)
    #accel_zs = plt.plot(ts, zs, label="Zs")
    
    # Integration range
    time_range = range(len(ts[1:]))
    integration_values = [(ts[i]-ts[i-1]) for i in time_range]
    # Calculate velocities
    v_xs = [0] + [ xs[i] * integration_values[i-1] for i in time_range  ]
    v_zs = [0] + [ zs[i] * integration_values[i-1] for i in time_range ]
    # Calculate displacement
    d_xs = [0] + [ v_xs[i] * integration_values[i-1] for i in time_range ]
    d_zs = [0] + [ v_zs[i] * integration_values[i-1] for i in time_range ]
    
    plt.plot(ts, v_xs, label="X Velocity")
    plt.plot(ts, v_zs, label="Z Velocity")
    plt.plot(ts, d_xs, label="X Displacement")
    plt.plot(ts, d_zs, label="Z Displacement")
    
    plt.legend()
    
plt.show()
