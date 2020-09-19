#include <iostream>
#include <vector>

using namespace std;

int main() {
    int n;
    cin >> n;
    vector<int> balls;
    int max_ball = 0;
    for (int i = 0; i < n; ++i) {
        int stuck_out_ball;
        cin >> stuck_out_ball;
        if (stuck_out_ball > max_ball) {
            for (int j = max_ball + 1; j < stuck_out_ball; j++) {
                balls.push_back(j);
            }
            max_ball = stuck_out_ball;
        } else {
            if (stuck_out_ball == balls.back()){
                balls.pop_back();
            } else {
                cout << "Cheater";
                return 0;
            }
        }
    }
    cout << "Not a proof";
    return 0;
}