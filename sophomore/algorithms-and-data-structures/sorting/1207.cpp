#include <iostream>
#include <cmath>

#define PI_1207 3.14159265358979323846264338327950288

using namespace std;

int min_count, min_x = INT_MAX;
size_t n;

struct dot {
    int x;
    int y;
    double angle;
    int num;
};

int comp(const void *v1, const void *v2) {
    const dot *p1 = (dot *) v1;
    const dot *p2 = (dot *) v2;
    return p1->angle > p2->angle ? 1 : -1;
}

int main() {
    cin >> n;
    dot d[n];
    for (int i = 0; i < n; ++i) {
        cin >> d[i].x >> d[i].y;
        if (d[i].x < min_x) {
            min_x = d[i].x;
            min_count = i;
        }
        d[i].num = i;
    }

    for (dot &d_it: d) {
        if (d_it.num == min_count) {
            d_it.angle = INT_MIN;
            continue;
        }
        if (d_it.x - d[min_count].x == 0) {
            d_it.angle = (d_it.y > d[min_count].y) ? 90 : -90;
            continue;
        }
        double radians = atan2((double) (d_it.y - d[min_count].y), (double) (d_it.x - d[min_count].x));
        d_it.angle = radians * 180.0 / PI_1207;
    }

    qsort(d, n, sizeof(dot), comp);

    cout << d[0].num + 1 << " " << d[n / 2].num + 1;
    return 0;
}
