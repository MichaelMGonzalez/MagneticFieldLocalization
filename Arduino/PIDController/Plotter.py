import csv
import sys
import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

use_min = False
if len( sys.argv ) == 1:
    print "Please enter the csv file you want to plot!"
    sys.exit(0)

with open( sys.argv[1] ) as csvfile:
    reader = csv.reader(csvfile, delimiter=',')
    points = [ c for c in reader ]
    xs     = [ float(p[0]) for p in points ]
    ys     = [ float(p[1]) for p in points ]
    zs     = [   float(p[2]) - 2 for p in points ]


#print (xs), (ys), (zs)
def filter_by_min( min_collection, collection):
    min_val = min(min_collection)
    def is_min(e): return e <= min_val * 1.1 
    rv = [ collection[i] for i in range( len(min_collection) ) if is_min(min_collection[i]) ]
    return rv
if use_min:
    xs = filter_by_min( zs, xs )
    ys = filter_by_min( zs, ys )
    zs = filter_by_min( zs, zs )

fig = plt.figure()
ax = fig.add_subplot( 111, projection='3d')
ax.set_xlabel('P')
ax.set_ylabel('D')
ax.set_zlabel('Time in seconds for control signal to converge')
ax.scatter(xs, ys, zs)
plt.show()
