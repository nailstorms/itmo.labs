#include <iostream>
#include <map>
#include <fstream>
#include <cmath>

using namespace std;

struct symbol {     
    char character;
    int count;
    long double prob;
    long double left_bound;
    long double right_bound;

    symbol(char c) : character(c) {
        count = 1;
    }
};

map<char, symbol> symbols;
string str;
int size = 0;

void read_from_file(string file_path) {
    ifstream in(file_path);
    int sum = 0;                                         
    if (in.is_open()) {                                  
        while (!in.eof()) {
            char c = in.get();
            auto iter = symbols.find(c);
            if (iter != symbols.end()) {                  
                iter->second.count++;
            } else {
                symbol s = symbol(c);                     
                symbols.insert({c, s});
            }
            sum++;
            size++;
        }
        for (auto &symbol : symbols) {                   
            symbol.second.prob = (double) symbol.second.count / (double) sum;
        }
    } else {
        cout << "File not found.";
    }
    size--;
    in.close();
}

void read_string(string filename) {
    ifstream in(filename);
    if (in.is_open()) {
        getline(in, str);
    }
    in.close();
}

void make_segments() {
    double courser = 0;
    for (auto &s : symbols) {
        s.second.left_bound = courser;
        courser += s.second.prob;
        s.second.right_bound = courser;
    }
}

double code(long double left_bound, long double right_bound, char *character) {
    auto it = symbols.find(*character);
    long double new_left_bound = left_bound + (right_bound - left_bound) * it->second.left_bound;
    long double new_right_bound = left_bound + (right_bound - left_bound) * it->second.right_bound;
    printf("%c [%.53Lf; %.53Lf) -> [%.53Lf; %.53Lf)\n", *character, left_bound, right_bound, new_left_bound, new_right_bound);
    character++;
    if (*character) {
        return code(new_left_bound, new_right_bound, character);
    } else {
        return (new_left_bound + new_right_bound) / 2;
    }
}

string decode(long double code, string res) {
    for (auto & character : symbols) {
        if (code >= character.second.left_bound && code < character.second.right_bound) {
            res += character.first;
            code = (code - character.second.left_bound) / (character.second.right_bound - character.second.left_bound);
            size--;
            if (!size) {
                return res;
            }
            return decode(code, res);
        }
    }
}

double calc_entropy(){
    double res = 0;
    for (auto &item : symbols) {
        res -= item.second.prob * log2(item.second.prob);
    }
    res *= str.size();
    return res;
}

int main() {
    cout << "Enter path to file\n";
    string file_path = "test.txt";
    cin >> file_path;
    read_from_file(file_path);
    read_string(file_path);
    make_segments();
    long double res = code(0, 1, &str[0]);
    cout << "Encoded string: \"" << str << "\"\n";
    cout << "Result: ";
    printf("%.53Lf\n", res);
    cout << "Decoded string: " << decode(res, "") << "\n";
    cout << "Compression: " << calc_entropy() / sizeof(res) << "\n";
    return 0;
}