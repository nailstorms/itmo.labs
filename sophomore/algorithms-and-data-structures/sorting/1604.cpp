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
        temp.first = i+1;
        count += temp.second;
        p.push_back(temp);
    }

    sort(p.begin(), p.end(), comp);

    for (int i = 0; count; count--) {
        if(p.empty()) break;
        if(p.size() == 1) {
            cout << p[i].first << " ";
            p[i].second--;
            if (p[i].second == 0) p.pop_front();
        } else {
            cout << p[i].first << " ";
            p[i].second--;
            cout << p[i+1].first << " ";
            p[i+1].second--;
            if (p[i].second == 0) p.pop_front();
            if (p[i+1].second == 0) {
                pair<int, int> temp = p[i];
                p.pop_front(); p.pop_front();
                p.push_front(temp);
            }
        }
    }
    return 0;
}