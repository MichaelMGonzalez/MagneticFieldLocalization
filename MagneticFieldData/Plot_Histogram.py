import csv
import sys
import numpy as np
import matplotlib.pyplot as plt
import math as m

bin_size = 32




def show_hist(points):
    plt.hist( points, bin_size)
    plt.xlabel("Magnetic Field Intensities in micro Teslas")
    plt.ylabel("Sample count within bin")
    plt.title("Histogram of the Magnetic Field Intensities at rest")
    plt.suptitle("Sample size: n = " + str(len(points )) + "\n" + "Bin Size: " + str(bin_size))
    plt.show()

    
if __name__ == "__main__":
    if len( sys.argv ) == 1:
        print "Please enter the csv file you want to plot!"
        sys.exit(0)

    points = []
    with open( sys.argv[1] ) as csvfile:
        reader = csv.reader(csvfile)
        points = [ float(c[2] ) for c in reader]
    #print (points)
    xs = range( len( points ) )
    show_hist(points)