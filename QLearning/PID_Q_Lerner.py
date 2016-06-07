space = "\t" 
from numpy import linspace
class PIDNode:
    def __init__(self, p, i, d):
        self.p = p
        self.i = i
        self.d = d
	self.edges = []
    def __str__(self):
        rv  = "P: " + str(self.p) + space
        rv += "I: " + str(self.i) + space
        rv += "D: " + str(self.d) + space
	return rv
    def point_repr(self):
        r = "{0:.2f}"
        return "[" + str( r.format(self.p) + " " + r.format(self.d)) +"]"
class Edge:
    def __init__(self, other):
        self.other = other 
class SearchSpace:
    def __init__(self, res=11, p_min=0, p_max=3, d_min=-2, d_max=2):
        self.res = res
	self.space = []
        ps = linspace( p_min, p_max, res)
        ds = linspace( d_min, d_max, res)
	self.create_nodes(ps,ds)
	self.create_adjacency_list()
    def create_nodes(self, ps, ds):
	r = 0
        for p in ps: 
	    self.space.append( [] )
            for d in ds: 
		self.space[r].append( PIDNode( p, 0, d ) )
	    r += 1
    def create_adjacency_list( self ):
	res = self.res - 1
        for r in range(len( self.space ) ):
	    for c in range(len( self.space[r] ) ):
	        if r > 0   : self.space[r][c].edges.append( 
		Edge( self.space[r-1][c] ) )
	        if c > 0   : self.space[r][c].edges.append(  
		Edge( self.space[r][c-1] ) )
	        if r < res : self.space[r][c].edges.append(  
		Edge( self.space[r+1][c] ) )
	        if c < res : self.space[r][c].edges.append(  
		Edge( self.space[r][c+1] ) )
    def __str__( self ):
        rv = "PID Space:\n"
	for r in self.space:
	    acc = ""
	    for c in r:
	        #acc += c.point_repr()
	        acc += str(len(c.edges))
	    rv += acc + "\n"
	return rv

if __name__ == "__main__":
    print SearchSpace()
