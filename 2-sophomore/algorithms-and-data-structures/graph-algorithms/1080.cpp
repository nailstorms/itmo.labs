#include <iostream>
#include <vector>

using namespace std;

int colors[100];
vector<int> graph[100];
bool bad_border = false;
int n;

void paint(int st, int color) {

    colors[st] = color;
    for (int country : graph[st]) {
        if (bad_border)
            return;
        if (colors[country] == -1) {
            if (color == 0)
                paint(country, 1);
            if (color == 1)
                paint(country, 0);
        } else if (colors[country] == color)
            bad_border = true;
    }

}

int main() {
    cin >> n;
    fill(colors, colors + 100, -1);

    for (int i = 0; i < n; ++i) {
        int leaf;
        while (cin >> leaf && leaf != 0) {
            graph[i].push_back(leaf - 1);
            graph[leaf - 1].push_back(i);
        }
    }

    for (int i = 0; i < n; ++i) {
        if (colors[i] == -1) {
            paint(i, 0);
        }
    }

    if (bad_border) {
        cout << "-1";
        return 0;
    } else
        for (int i = 0; i < n; i++)
            cout << colors[i];

    return 0;
}