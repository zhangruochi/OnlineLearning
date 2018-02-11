#include <iostream>
using namespace std;
int main() {
    int a, b, c;
    char d, e;//用来吃掉，
    cin >> a >> d >> b >> e >> c;//因为这道题只能输出一个运算符号，所以必须if else这样套下来
    //否则会出现2，2，4 同时输出+和*的情况，导致错误
    if (a + b == c) {
        cout << '+'<<endl;
    }
    else {
        if (a - b == c)
            cout << '-' << endl;
        else {
            if (a * b == c)
                cout << '*' << endl;
            else {
                if (a / b == c)
                    cout << '/' << endl;
                else {
                    if (a % b == c)
                        cout << '%' << endl;
                    else
                        cout << "error" << endl;
                }
            }
        }
    }
    return 0;
}