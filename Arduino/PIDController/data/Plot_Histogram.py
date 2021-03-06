import csv
import sys
import numpy as np
import matplotlib.pyplot as plt
import math as m

bin_size = 5




def show_hist(points):
    plt.hist( points, bin_size)
    plt.xlabel("Velocity as m/s")
    plt.ylabel("Probability")
    plt.title("Histogram of the time required for a full motor revolution")
    plt.suptitle("Sample size: n = " + str(len(points )) + "\n" + "Bin Size: " + str(bin_size))
    plt.show()

    
if __name__ == "__main__":
    if len( sys.argv ) == 1:
        print "Please enter the csv file you want to plot!"
        sys.exit(0)

    points = []
    with open( sys.argv[1] ) as csvfile:
        reader = csv.reader(csvfile)
        points = [ float(c[0]) for c in reader]
    print (points)
    xs = range( len( points ) )
    show_hist(points)
