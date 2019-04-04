#include <iostream>
#include <algorithm>

using namespace std;

int main() {
    long long n, res = 0;
    cin >> n;
    long long house_x[n], house_y[n];
    for (long long i = 0; i < n; i++)
        cin >> house_x[i] >> house_y[i];

    sort(house_x, house_x + n);
    sort(house_y, house_y + n);

    for (long long i = 0; i < n-1; i++) {
        res += (house_x[i+1] - house_x[i]) * (i + 1) * (n - i - 1);
        res += (house_y[i+1] - house_y[i]) * (i + 1) * (n - i - 1);
    }
    res /= (n*(n-1))/2;
    cout << res << endl;
    return 0;
}