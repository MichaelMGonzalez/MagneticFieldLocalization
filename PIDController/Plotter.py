import csv
import sys
import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

if len( sys.argv ) == 1:
    print "Please enter the csv file you want to plot!"
    sys.exit(0)

with open( sys.argv[1] ) as csvfile:
    reader = csv.reader(csvfile, delimiter=',')
    points = [ c for c in reader if float(c[2]) < 10]
    xs     = [ float(p[0]) for p in points ]
    ys     = [ float(p[1]) for p in points ]
    zs     = [   int(p[2]) for p in points ]


#print (xs), (ys), (zs)
fig = plt.figure()
ax = fig.add_subplot( 111, projection='3d')
ax.set_xlabel('P')
ax.set_ylabel('D')
ax.set_zlabel('T')
ax.scatter(xs, ys, zs)
plt.show()
