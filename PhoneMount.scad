width = 40;
height = 15;
screw_offset_x = .35 * width;
screw_offset_y = .25 * height;
screw_radius = 1.016;
screw_height = 3;
res=20;

phone_width = 70.5;
phone_depth = 6.8;
phone_height = 143.4;

casing_wiggle = 5;
casing_depth = phone_depth + casing_wiggle; 
casing_width = phone_width + casing_wiggle; 
casing_height = phone_height * .8;

screw_mount_placement =  ( .95 * phone_width ) / 2;




union() {
    rotate(90) 
    BaseStation();
    BaseStation();
    Support();
    rotate(180)
    Support();
    
    translate( [.8*casing_width/2,0,0] ) 
    HorizontalSupport();
    translate( [-.8*casing_width/2,0,0] )
    rotate(180)
    HorizontalSupport();
}

translate( [0,0, 65 ] ) {  
    DrawCase();  
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
    VerticalSupport(casing_height*1.07);
    translate([0,0,height/2])
    cube([20,casing_depth ,height], center=true);
}

module BaseStation() {
    union () {
    cube( [screw_mount_placement * 2, casing_depth, 1] , center=true);
    ScrewMount( screw_mount_placement,0,0);
    ScrewMount( -1 * screw_mount_placement,0,0);
    }
}


module DrawPhone( v = [0,0,0], s=1) {
        translate(v)
        cube( [s*phone_width , s*phone_depth, s*phone_height ], center = true );
}

module DrawCase() {
        y_off = 18;
        difference() {
            rotate(90)
            cube( [ casing_depth, casing_width, casing_height  ], center = true );
            DrawPhone([0,0,y_off]);
            DrawPhone([0,5,y_off], s=.8);
            DrawPhone([0,-5,y_off], s=.55);
            DrawPhone([0,0,-30], s=.5);
            
        }
        
}

module Screw(x,y,z) {
    translate( [x,y,z] )
    cylinder( screw_height, screw_radius, screw_radius, $fn=res, center = true );
}


module ScrewMount(x,y,z) {
    translate( [x,y,z] )
    rotate( 90 ) // Rotate for placement
    // Subtract screw holes from base
    difference()  {
        // Draw base
        cube( [width,height,1], center = true );
        // Draw screw holes
        Screw(screw_offset_x, screw_offset_y, 0);
        Screw(-1 * screw_offset_x, screw_offset_y, 0);
        Screw(-1 * screw_offset_x, -1 * screw_offset_y, 0);
        Screw(screw_offset_x, -1 * screw_offset_y, 0);
    
    }
}


module prism(l, w, h){
       translate([ -1 * l/2, -1 *  w/2,0])
       polyhedron(
               points=[[0,0,0], [l,0,0], [l,w,0], [0,w,0], [0,w,h], [l,w,h]],
               faces=[[0,1,2,3],[5,4,3,2],[0,4,5,1],[0,3,4],[5,2,1]]
               );
       

 }