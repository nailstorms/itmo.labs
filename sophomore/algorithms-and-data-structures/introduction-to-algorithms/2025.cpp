#include <iostream>

using namespace std;

const int MAX_nk = 10000;
const short MAX_T = 10;

int battle_count (int n, int k) {
    int mas[k], e = n%k, res = 0;
    for (int i = 0; i < k; i++)
        mas[i] = n/k;
    while (e)
        mas[k-(e--)-1]++;
    for (int i = 0; i < k; i++) {
        for (int j = i + 1; j < k; j++)
            res += mas[i] * mas[j];
    }
    return res;
}

int main() {
    short T;
    cin >> T;
    if (T > MAX_T) return 0;

    int n, k, mas_res[T];
    for (int i = 0; i < T; i++) {
        cin >> n >> k;
        if (n < k || n > MAX_nk || k > MAX_nk) return 0;
        mas_res[i] = battle_count(n,k);
    }

    for (int i = 0; i < T; i++)
        cout << mas_res[i] << endl;
    return 0;
}
