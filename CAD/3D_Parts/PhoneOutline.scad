use <PhoneMount.scad>;

cut_dim = [100,50,67];
difference() {
	FullStand();
	cube( cut_dim, true );
	translate( [0,0,70] )
	cube( cut_dim, true );
}