#include <iostream>

using namespace std;

int n, k;
int fenwick_tree_arr[100000];

int sum(int x) {
    int s = 0;
    for(; x >= 0; x = (x & (x + 1)) - 1){
        s += fenwick_tree_arr[x];
    }
    return s;
}

int segment_sum(int l, int r) {
    return sum(r) - sum(l - 1);
}

void update_status(int x, int status) {
    for(; x < n; x = (x | (x + 1))){
        fenwick_tree_arr[x] += status;
    }
}

int fetch_next_deserter(int l_bound) {
    int deserter_num = k;
    while (deserter_num > 0) {
        int r_bound = (l_bound + deserter_num) % n;
        l_bound = (l_bound + 1) % n;
        if (l_bound <= r_bound)
            deserter_num = segment_sum(l_bound, r_bound);
        else
            deserter_num = segment_sum(l_bound, n - 1) + segment_sum(0, r_bound);
        l_bound = r_bound;
    }
    return l_bound;
}


int main() {
    cin >> n >> k;
    if (n == 1) {
        cout << n;
        return 0;
    }
    cout << k << " ";
    int x = k - 1;
    for (int i = 1; i < n; i++) {
        update_status(x, 1);
        x = fetch_next_deserter(x);
        cout << x + 1 << " ";
    }
    return 0;
}