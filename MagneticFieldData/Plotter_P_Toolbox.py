import csv
import sys
import numpy as np
import matplotlib.pyplot as plt

if len( sys.argv ) == 1:
    print "Please enter the csv file you want to plot!"
    sys.exit(0)

sets = []
for arg in sys.argv[1:]:
    with open( arg ) as csvfile:
        reader = csv.reader(csvfile)
        points = [ c for c in reader]
        sets.append(points)

#print points
for points in sets:
    ints = [ p[4] for p in points[1:] ]
    ts   = [ p[0] for p in points [1:] ]
#xs = range( len( points ) )
    plt.plot(ts, ints)

plt.ylabel("Magnetic Field Intensities in micro Teslas")    
plt.xlabel("Time in seconds")    
plt.show()
