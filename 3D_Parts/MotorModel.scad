printer_error = .03;

motor_width  = 42.18;
motor_depth  = 13.90;
motor_height = 22.81;

casing_scale  = 1.1;
casing_width  = casing_scale * motor_width;
casing_height = casing_scale * casing_scale * motor_height;
casing_depth  = 6;


MotorCase(error=printer_error);
//Motor();

module MotorCase(error=0) {

	difference() {
		cube( [casing_width+error, casing_depth+error, casing_height+error ], center=true );
		Motor(error=error);
		translate( [0,0,-1*casing_height/2] )
		Motor(error=error,size=.74);	
        Cutout();
		
	}



}

module Cutout(error=0) {
	translate( [0,0, motor_height/2] )
		cube([ casing_width,.7, 1], center=true );

		translate( [0,casing_depth/-2, (casing_height/casing_scale)/2] )
		cube([ motor_width,5, 1], center=true );

		translate( [0,casing_depth/2, (casing_height/casing_scale)/2] )
		cube([ motor_width,5, 1], center=true );
		
		// Side decals
      for(i = [motor_height/-2 : 2 : motor_height/2 ])
		translate([0,0,i])
		cube([ casing_width*2+error, .7, 1 ],center=true );
}

module Motor(error=0,size=1) {
    cube( [size*motor_width+error, size*motor_depth+error, size*motor_height+error ], center=true );
}

