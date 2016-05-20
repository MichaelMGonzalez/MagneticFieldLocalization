printer_error = .03;

motor_width  = 42.18;
motor_depth  = 13.90;
motor_height = 22.81;

casing_scale  = 1.14;
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
	}


}
module Motor(error=0,size=1) {
    cube( [size*motor_width+error, size*motor_depth+error, size*motor_height+error ], center=true );
}

