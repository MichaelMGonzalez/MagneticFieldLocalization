//sensor_width= 6.10; //~6.11
//sensor_height=4.39; //~4.40
//sensor_depth=4.65;  //~4.66

sensor_width=  6.11;  //~6.11
sensor_height= 4.40;  //~4.40
sensor_depth=  4.66;  //~4.66


difference() {
	Sensor(1.3);
	translate( [ 0, 1, 0 ] )
	Sensor();
	translate( [0,-2,0] )
	Sensor(.7);
}
module Sensor(s=1) { 
	cube( [s*sensor_width, s*sensor_height, s*sensor_depth ], center = true );
}