#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

int parents[1000], set_members_count[1000];
vector<pair< int, pair< int, int > >> hubs_n_cables;
vector<pair< int, int >> ans;

int find_parent(int v){
    if(parents[v] == v) return v;
    return parents[v] = find_parent(parents[v]);
}

void join_sets(int a, int b){
    a = find_parent(a);
    b = find_parent(b);
    if(set_members_count[a] < set_members_count[b]) swap(a, b);
    parents[b] = a;
    set_members_count[a] += set_members_count[b];
}

void init_disjoint_set(int n){
    for(int i = 1; i <= n; i++){
        parents[i] = i;
        set_members_count[i] = 1;
    }
}

int main()
{
    ios_base::sync_with_stdio(false); cin.tie(nullptr); cout.tie(nullptr);

    int n, m;
    cin >> n >> m;
    init_disjoint_set(n);
    for(int i = 1; i <= m; i++){
        int hub_from, hub_to, length;
        cin >> hub_from >> hub_to >> length;
        hubs_n_cables.push_back({length, {hub_from, hub_to}});
    }

    sort(hubs_n_cables.begin(), hubs_n_cables.end());

    int max_length = 0;

    for(int i = 0; i < m; i++){
        int a = hubs_n_cables[i].second.first;
        int b = hubs_n_cables[i].second.second;
        int len = hubs_n_cables[i].first;
        if(find_parent(a) != find_parent(b)){
            max_length = max(max_length, len);
            join_sets(a, b);
            ans.push_back({a, b});
        }
    }

    n--;
    cout << max_length << '\n' << n << '\n';

    for(int i = 0; i < n; i++) {
        cout << ans[i].first << " " << ans[i].second << '\n';
    }
    return 0;
}