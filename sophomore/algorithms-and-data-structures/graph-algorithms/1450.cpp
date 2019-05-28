#include <iostream>
#include <algorithm>
#include <vector>
#include <deque>

using namespace std;

int max_profits[500];
vector< pair< int, int > > graph[500];

int main()
{
    ios_base::sync_with_stdio(false); cin.tie(nullptr); cout.tie(nullptr);

    int n, m, f, s;
    cin >> n >> m;
    for(int i = 1; i <= m; i++){
        int a, b, c;
        cin >> a >> b >> c;
        graph[a].push_back({b, c});
    }
    cin >> s >> f;

    deque< pair< int, int > > mpcq;
    mpcq.push_back({0, s});
    while(!mpcq.empty()){

        int station_from = mpcq.front().second;
        mpcq.pop_front();

        for(auto station : graph[station_from]){

            int station_to = station.first;
            int potential_profit = station.second;

            if(max_profits[station_from] + potential_profit > max_profits[station_to]){
                max_profits[station_to] = max_profits[station_from] + potential_profit;
                mpcq.push_back({-max_profits[station_to], station_to});
            }

        }
    }

    if(max_profits[f] == 0)
        cout << "No solution";
    else
        cout << max_profits[f];
    return 0;
}