#include <iostream>
#include <cmath>
#include <algorithm>

using namespace std;

int alphabet_freq[53];

int alphabet_id(char a) {
    if (a <= 'Z') return a - 'A';
    else {
        if (a == '_') return 26;
        else return a - 'a' + 27;
    }
}

int main() {
    int k, sum = 0;
    string s;
    cin >> k >> s;
    int rev_tran_vec[s.size()];

    for (char i : s) {
        alphabet_freq[alphabet_id(i)]++;
    }

    for (int &i : alphabet_freq) {
        sum = sum + i;
        i = sum - i;
    }

    for (int i = 0; i < s.size(); i++) {
        int id = alphabet_id(s[i]);
        int position = alphabet_freq[id];
        rev_tran_vec[position] = i;
        alphabet_freq[id]++;
    }

    int x = rev_tran_vec[k-1];
    for (int i = 0; i < s.size(); i++) {
        cout << s[x];
        x = rev_tran_vec[x];
    }
    cout << endl;
    return 0;
}