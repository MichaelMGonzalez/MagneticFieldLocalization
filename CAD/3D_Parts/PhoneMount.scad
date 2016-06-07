/**
 * Filename: PhoneMount.scad
 * Description: Bracket designed to mount a Samsung Galaxy S6 onto a tiny
 *              mobile robot
 */
include <MakerBotSettings.scad>
use <ScrewMount.scad>;




// Dimensions
phone_width = 71.17   + global_correction;
phone_depth = 7.15    + global_correction;
phone_height = 143.4 + global_correction;

casing_wiggle = 5 + global_correction;
casing_depth = phone_depth + casing_wiggle; 
casing_width = phone_width + casing_wiggle + global_correction; 
casing_height = phone_height * .3 + global_correction;

screw_mount_placement =  ( .95 * (70.5   + global_correction) ) / 2;

FullStand();

// Uncomment the below line to get the 2d projection
//projection( cut=true)
module FullStand() {
	union() {
		// Draw primary stand
		//rotate(90) 
		//BaseStation();
		BaseStation();

		// Draw triangular supports
		Support();
		rotate(180)
			Support();

		// Draw vertical supports
		translate( [.8*casing_width/2,0,0] ) 
			HorizontalSupport();
		translate( [-.8*casing_width/2,0,0] )
			rotate(180)
			HorizontalSupport();

		// Draw the phone case itself
		translate( [0,0, 40 ] ) {  
			DrawCase();  
		}
	}
}

//translate( [0,0,80] )
//DrawPhone();

// Tringular Support
module Support( l=11,w=35,h=25) {
	translate( [ 0,  -.69 * screw_mount_placement, 0 ] )
		prism(l,w,h);
}

module VerticalSupport( height=1 ) {
	width = casing_depth * 1.2;
	translate( [7,width/-2,0] )
		cube( [3,width,height]);
}

module HorizontalSupport(height=10) {
	VerticalSupport(casing_height*2);
	translate([0,0,height])
		cube([20,casing_depth ,height*2], center=true);
}

module BaseStation() {
	union () {
		difference() {
		cube( [screw_mount_placement * 2, casing_depth, 3] , center=true);
		scale([.63,1,1])
		cube( [screw_mount_placement * 2, casing_depth, 3] , center=true);
		}
		ScrewMount( screw_mount_placement,0,0);
		ScrewMount( -1 * screw_mount_placement,0,0);
	}
}


module DrawPhone( v = [0,0,0], s=1) {
	translate(v)
		cube( [s*phone_width , s*phone_depth, s*phone_height ], center = true );
}

module DrawCase() {
	y_off = 60;
	difference() {
		rotate(90)
			cube( [ casing_depth, casing_width, casing_height  ], center = true );
		DrawPhone([0,0,y_off]);
		DrawPhone([0,5,y_off], s=.8);
		DrawPhone([0,-5,y_off], s=.55);
		DrawPhone([0,0,-30], s=.5);

	}

}





module prism(l, w, h){
	translate([ -1 * l/2, -1 *  w/2,0])
		polyhedron(
				points=[[0,0,0], [l,0,0], [l,w,0], [0,w,0], [0,w,h], [l,w,h]],
				faces=[[0,1,2,3],[5,4,3,2],[0,4,5,1],[0,3,4],[5,2,1]]
			  );


}
