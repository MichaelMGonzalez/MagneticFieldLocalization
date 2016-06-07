from numpy import linspace
import random as rand
import json
import pickle
space = "\t" 
nl = "\n"
n_fmt = "{0:.2f}"
class PIDNode:
    def __init__(self, p, i, d):
        self.p = p
        self.i = i
        self.d = d
	self.times = []
	self.edges = []
	self.north = None
	self.east  = None
	self.south = None
	self.west  = None
    def __str__(self):
        rv  = "P: " + str(self.p) + space
        rv += "I: " + str(self.i) + space
        rv += "D: " + str(self.d) + space
	return rv
    def point_repr(self):
        return  "("+str( n_fmt.format(self.p) + "," + n_fmt.format(self.d))+")" 
    def get_min_edge( self ):
        return min( self.edges, key=lambda n: n.weight )
    def add_edge( self, node, pos ):
        self.edges.append( node )
	if   pos == "N": self.north = node
	elif pos == "E": self.east  = node
	elif pos == "S": self.south = node
	elif pos == "W": self.west  = node
    def get_weight( self, edge_pos ):
	node = None
	rv   = ""
	if   edge_pos == "N": node = self.north 
	elif edge_pos == "E": node = self.east  
	elif edge_pos == "S": node = self.south 
	elif edge_pos == "W": node = self.west  
	if node: rv = n_fmt.format( node.weight )
	return rv
    def dump( self ):
        rv  =  { 
	         "p"     : self.p,
	         "i"     : self.i,
	         "d"     : self.d,
	         "times" : self.times
	       }
	if self.north: rv["q_n"] = self.north.weight
	if self.south: rv["q_s"] = self.south.weight
	if self.east : rv["q_e"] = self.east.weight
	if self.west : rv["q_w"] = self.west.weight
	return rv
    def load( self, d ):
        self.p = d["p"]
        self.i = d["i"]
        self.d = d["d"]
	if "q_n" in d: self.north.weight = d["q_n"]
	if "q_e" in d: self.east.weight  = d["q_e"]
	if "q_s" in d: self.south.weight = d["q_s"]
	if "q_w" in d: self.west.weight  = d["q_w"]

        
class Edge:
    def __init__(self, other, weight=0):
        self.other = other 
	self.weight = weight
    def __lt__(self, other):
        return self.weight < other.weight
    def __gt__(self, other):
        return self.weight > other.weight
    def __str__(self):
        return "Weight: " + str(self.weight)
class SearchSpace:
    #def dump( self ):
    def __init__(self, res=7, p_min=1, p_max=3, d_min=-2, d_max=2):
        self.res = res
	self.space = []
        ps = linspace( p_min, p_max, res)
        ds = linspace( d_min, d_max, res)
	self.create_nodes(ps,ds)
	self.create_adjacency_list()
	self.active = self.space[0][0]
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
	        if r > 0   : self.space[r][c].add_edge( 
		Edge( self.space[r-1][c] ), "N")
	        if c > 0   : self.space[r][c].add_edge(  
		Edge( self.space[r][c-1] ), "W")
	        if r < res : self.space[r][c].add_edge(  
		Edge( self.space[r+1][c] ), "S" )
	        if c < res : self.space[r][c].add_edge(  
		Edge( self.space[r][c+1] ), "E" )
    def get_random_node( self ):
        r = int(rand.uniform(0, self.res))
        c = int(rand.uniform(0, self.res))
	return self.space[r][c]
    def dump( self ):
	rv = { "Resolution" : self.res,
	       "Nodes"      : [] }
        for r in self.space:
	    for c in r:
	        rv["Nodes"].append( c.dump() )
        return json.dumps(rv)
    def dump_to_file( self, f ):
        dump = self.dump()
	fd = open(f, "w")
	fd.write(dump)
	fd.close()
    def load_from_file( self, f):
	fd = open(f)
        j = json.load(fd)
	self.__init__(res=j["Resolution"])
	nodes = j["Nodes"]
	nodes.sort( key=lambda node: node["p"])
	idx = 0
	for r in self.space:
	    for c in r:
	        c.load( nodes[idx ] ) 
		idx += 1
    def __str__( self ):
        rv = "\tPID Space\tResolution: " + str(self.res) + "\tActive Node: " + self.active.point_repr() + nl
	for r in self.space:
	    top = "|"
	    mid = "|"
	    bot = "|"
	    for c in r:
	        ws = c.get_weight("W") + " " + c.point_repr() + " " + c.get_weight("E") + " "
		spaces = (len(ws)/2 - 2) * " "
		top += spaces + c.get_weight("N") + spaces
		mid += ws
		bot += spaces + c.get_weight("S") + spaces
	        #acc += str(len(c.edges))
	    #rv += acc + "\n"
	    rv += top + nl + mid + "|" + nl + bot + nl + len(mid)*"-" + "|" + nl
	return "|" + (len(mid)-1)*"-"+ "|"  + nl + rv

if __name__ == "__main__":
    s = SearchSpace(res=6)
    encoder = json.JSONEncoder()
    #print encoder.default() 
    n = s.space[0][0] 
    s.load_from_file( "test.json" ) 
    #s.dump_to_file( "test.json" ) 
    print s
