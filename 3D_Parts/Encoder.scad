use <LBracket.scad>;
printer_error = .03;

sensor_width = 6.10 + printer_error; //~6.11
sensor_height= 4.39 + printer_error; //~4.40
sensor_depth = 4.65 + printer_error;  //~4.66

bracket_width = sensor_width / 4;

encasing_scale = 1.3;

FullEncoder_IR();

module FullEncoder_IR() {
    union() {
        SensorBox();
        SensorBracket();
        SensorBracket(-1);
    }
}
module SensorBox() {
    difference() {
        Sensor(encasing_scale);
        translate( [ 0, 1, 0 ] )
        Sensor();
        translate( [0,-2,0] )
        Sensor(.7);
    }
}
module Sensor(s=1) { 
	cube( [s*sensor_width, s*sensor_height, s*sensor_depth ], center = true );
}

module SensorBracket( side=1 ) {
    translate( placeLBracket(side) )
        rotate( 90 )
        LBracket(bracket_width, l1=sensor_width, 
                    l2 = encasing_scale * sensor_height, center=true);
}

function neg(x) = -1 * x;
function placeLBracket( side ) = [ side * 
                             sensor_width  / (2 * encasing_scale),
                             sensor_depth  / -1,
                             encasing_scale * sensor_depth  
                             * neg(encasing_scale) ];