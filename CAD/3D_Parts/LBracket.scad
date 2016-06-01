module LBracket( d, l1=1,w1=1,l2=false,w2=false, center=false ) {
    union() {
        l2 = l2 == false ? l1 : l2;
        w2 = w2 == false ? w1 : w2;
        trans = center ? [ l1/2 - .5, 0, l2/2 ] : [ 0, 0, 0 ] ;
        
        cube( [l1,d,w1 ], center=center );
        translate( trans )
        cube( [w2,d,l2 ], center=center );
    }
}

LBracket( 1, l1=2, center=false );