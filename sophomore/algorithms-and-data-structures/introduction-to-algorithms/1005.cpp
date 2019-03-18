#include <iostream>
#include <vector>

using namespace std;

int min_weight = 100000;
vector<int> stones;
int n;

int calc_diff(int n, int diff) {
    if (n < 0) min_weight = min(min_weight, abs(diff));
    else {
        calc_diff(n - 1, diff + stones[n]);
        calc_diff(n - 1, diff - stones[n]);
    }
    return min_weight;
}

int main() {
    int temp;
    cin >> n;
    for (int i = 0; i < n; ++i) {
        cin >> temp;
        stones.push_back(temp);
    }
    calc_diff(n - 1, 0);
    cout << min_weight;
    return 0;
}
