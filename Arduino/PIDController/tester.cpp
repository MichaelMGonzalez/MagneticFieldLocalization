#include <iostream>
#include "PIDController.h"
#include "util.hpp"
#include <chrono>
#include <ctime>
#define INCR (1.0/64.0)
#define DEST 999
#define CONVERGED_THRESHOLD 20

#define P_MIN .1
#define P_MAX .5
#define D_RANGE 2
#define TEST_ITTR 9000

using namespace std;
int testPID(float p, float i, float d) {
    Timer timer;
    PIDController controller = PIDController(p,i,d);
    controller.dest = DEST;
    float val = 1;
    int idx = 1;
    timer.begin_timer();
    long long t = 0;
    int cnt = 0;
    for( idx = 1; idx < TEST_ITTR; idx++ ) {
	auto delta_time = timer.end_timer();
	t += delta_time;
        val += controller.update( val, t );
	timer.begin_timer();
	if( ((int)val) + 1 == (int)DEST ) {
	    if( cnt == CONVERGED_THRESHOLD ) {break;}
	    else {cnt++;}
	}
	else cnt = 0;
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
