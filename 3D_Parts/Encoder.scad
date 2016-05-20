
use <MotorModel.scad>;
printer_error = .03;

sensor_width  = 6.10 + printer_error; //~6.11
sensor_height = 4.39 + printer_error; //~4.40
sensor_depth  = 4.65 + printer_error;  //~4.66

bracket_width = sensor_width / 4;

encasing_scale = 1.3;
encasing_height = sensor_height * encasing_scale;
encasing_width = sensor_width * encasing_scale;
encasing_depth = sensor_depth * encasing_scale;
FullEncoder_IR();



module FullEncoder_IR() {
    union() {
        SensorBox();
		  translate( [0,-3.5,-23])
		  	MotorCase(error=printer_error);
		  translate( [0,-.5*encasing_height, -1 * encasing_depth] )
rotate(90, [1,0,0] )
cube( [sensor_width,sensor_width,4], center = true );

translate([0,-.845*sensor_height, (.9-encasing_scale)]) rotate(180) SensorBox(.87);
    }
}
module SensorBox(size=1) {
    difference() {
        Sensor(encasing_scale*size);
        translate( [ 0, 1, 0 ] )
        Sensor();
        translate( [0,-2,0] )
        Sensor(.7*size);
    }
}
module Sensor(s=1) { 
	cube( [s*sensor_width, s*sensor_height, s*sensor_depth ], center = true );
}


function neg(x) = -1 * x;
function placeLBracket( side ) = [ side * 
                             sensor_width  / (2 * encasing_scale),
                             sensor_depth  / -1,
                             encasing_scale * sensor_depth  
                             * neg(encasing_scale) ];