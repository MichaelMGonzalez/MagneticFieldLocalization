include <MakerBotSettings.scad>
// Screw Mounts
width = 40;
height = 15;
res=20; // Cylinder resolution
screw_offset_x = .35 * width;
screw_offset_y = .25 * height;
screw_radius = 1.25 + global_correction;
screw_height = 3;
module ScrewMount(x,y,z) {
	translate( [x,y,z] )
		rotate( 90 ) // Rotate for placement
		// Subtract screw holes from base
		difference()  {
			// Draw base
			cube( [width,height,3], center = true );
			// Draw screw holes
			Screw(screw_offset_x, screw_offset_y, 0);
			Screw(-1 * screw_offset_x, screw_offset_y, 0);
			Screw(-1 * screw_offset_x, -1 * screw_offset_y, 0);
			Screw(screw_offset_x, -1 * screw_offset_y, 0);
		}
}
module Screw(x,y,z) {
	translate( [x,y,z] )
		cylinder( screw_height, screw_radius, screw_radius, $fn=res, center = true );
}
ScrewMount(0,0,0);
