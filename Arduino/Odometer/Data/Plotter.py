import csv
import sys
import numpy as np
import matplotlib.pyplot as plt

if len( sys.argv ) == 1:
    print "Please enter the csv file you want to plot!"
    sys.exit(0)

points = []
with open( sys.argv[1] ) as csvfile:
    reader = csv.reader(csvfile)
    points = [ int(c[1]) for c in reader]

print points
xs = range( len( points ) )
plt.plot(xs, points)
plt.show()
