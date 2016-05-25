#include <iostream>
#include "PIDController.h"
#include <chrono>
#include <ctime>
#define INCR (1.0/64.0)
#define DEST 100

#define P_MIN 0
#define P_MAX 3
#define D_RANGE 2
#define TEST_ITTR 2000

using namespace std;
int testPID(float p, float i, float d) {
    PIDController controller = PIDController(p,i,d);
    controller.dest = DEST;
    float val = 1;
    int idx = 1;
    for( idx = 1; idx < TEST_ITTR; idx++ ) {
        val += controller.update( val, idx );
	if( ((int)val) + 1 == (int)DEST ) break;
    }
    //cout << "Reached Value after " << idx << " iterations" << endl;
    return idx;
}

int main() {
    for( float p = P_MIN; p < P_MAX; p += INCR ) {
        for( float d = -1 * D_RANGE; d < D_RANGE; d += INCR ) {
            int v = testPID( p, 0, d);
	    //cout << "P: " << p << " D: " << d << "Num: " << v << endl;
	    cout << p << "," << d << "," << v << "\n";
        }
    }
    return 0;
}
