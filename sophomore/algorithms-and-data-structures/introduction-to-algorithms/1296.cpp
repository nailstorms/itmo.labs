#include <iostream>
#include <vector>

using namespace std;

int n;
int temp = 0;
int max_res = 0;

void calc(int dig) {
    temp += dig;
    if(temp >= max_res) max_res = temp;
    if(temp < 0) temp = 0;
}

int main() {
    int dig;
    cin >> n;
    while(n>0 && cin>>dig) {
        calc(dig);
        n--;
    }
    cout << max_res;
    return 0;
}
