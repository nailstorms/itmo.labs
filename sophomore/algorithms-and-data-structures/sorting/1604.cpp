#include <bits/stdc++.h>

using namespace std;

bool comp(pair<int, int> a, pair<int, int> b) {
    return a.second > b.second;
}

int main() {
    int n, count = 0;
    cin >> n;
    deque<pair<int, int>> p;
    for (int i = 0; i < n; i++) {
        pair<int, int> temp;
        cin >> temp.second;
        temp.first = i + 1;
        count += temp.second;
        p.push_back(temp);
    }

    sort(p.begin(), p.end(), comp);

    while (count--) {
        cout << p[0].first << " ";
        p[0].second--;
        if (p[1].second != 0) {
            cout << p[1].first << " ";
            p[1].second--;
        }

        int j = 1, k = 2;
        while (p[j].second < p[k].second) {
            int t = p[j].second;
            p[j].second = p[k].second;
            p[k].second = t;
            t = p[j].first;
            p[j].first = p[k].first;
            p[k].first = t;
            k++;
            j++;
            if (k == n) {
                break;
            }
        }
        j = 0, k = 1;
        while (p[j].second < p[k].second) {
            int t = p[j].second;
            p[j].second = p[k].second;
            p[k].second = t;
            t = p[j].first;
            p[j].first = p[k].first;
            p[k].first = t;
            k++;
            j++;
            if (k == n) {
                break;
            }
        }

        if (p[0].second <= 0)
            break;

        if (!p.empty()) {
            if (p[0].second <= 0) {
                if (p[1].second > 0) p.pop_front();
                else break;
            }
        }
    }
    return 0;
}