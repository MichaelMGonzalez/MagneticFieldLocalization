#include <iostream>
#include "PIDController.h"
#include "util.hpp"
#include <chrono>
#include <ctime>
#define INCR (1.0/32.0)
#define DEST 1724

#define P_MIN 0
#define P_MAX 3
#define D_RANGE 2
#define TEST_ITTR 2000

using namespace std;
int testPID(float p, float i, float d) {
    Timer timer;
    PIDController controller = PIDController(p,i,d);
    controller.dest = DEST;
    float val = 1;
    int idx = 1;
    timer.begin_timer();
    long long t = 0;
    for( idx = 1; idx < TEST_ITTR; idx++ ) {
	auto delta_time = timer.end_timer();
	t += delta_time;
        val += controller.update( val, t );
	timer.begin_timer();
	if( ((int)val) + 1 == (int)DEST ) break;
    }
    //cout << "Reached Value after " << idx << " iterations" << endl;
    return t;
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
