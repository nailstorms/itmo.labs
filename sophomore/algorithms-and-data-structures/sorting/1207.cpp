#include <iostream>
#include <cmath>

#define PI_1207 3.14159265358979323846264338327950288

using namespace std;

int min_count, min_x = INT_MAX;
size_t n;

struct dot {
    int x;
    int y;
    double vector_angle;
    int number;
};

int compare(const void *v1, const void *v2) {
    const dot *p1 = (dot *) v1;
    const dot *p2 = (dot *) v2;
    return p1->vector_angle > p2->vector_angle ? 1 : -1;
}

int main() {
    cin >> n;
    dot d[n];
    for (int i = 0; i < n; ++i) {
        cin >> d[i].x >> d[i].y;
        if (d[i].x < min_x) {
            min_x = d[i].x;
            min_count = i + 1;
        }
        d[i].number = i + 1;
    }

    for (dot &d_it: d) {
        if (d_it.number == min_count) {
            d_it.vector_angle = INT_MIN;
            continue;
        }
        if (d_it.x - d[min_count].x == 0) {
            d_it.vector_angle = (d_it.y > d[min_count].y) ? 90 : -90;
            continue;
        }
        double radians = atan2((double) (d_it.y - d[min_count].y), (double) (d_it.x - d[min_count].x));
        d_it.vector_angle = radians * 180.0 / PI_1207;
    }

    qsort(d, n, sizeof(dot), compare);

    cout << d[0].number << " " << d[n / 2].number;
    return 0;
}
