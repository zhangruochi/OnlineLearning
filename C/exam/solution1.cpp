/*
描述

输入二个正整数m 和 k，其中1 < m < 100000，1 < k <5 ，判断m 能否被19整除，且恰好含有k个3，如果满足条件，则输出YES，否则，输出NO。

例如，输入：

43833 3

满足条件，输出YES

如果输入

39331 3

尽管有3个3，但不能被19整除，也不满足条件，应输出NO

输入

m 和 k 的值，空格间隔

输出

满足条件时输出 YES，不满足时输出 NO


*/

#include<iostream>
using namespace std;
int main() {
    int m, k;
    int weishu[] = { 10000,1000,100,10,1 };//利用了递归的思想，然后用数组进行解决
    cin >> m >> k;
    int number=0;
    int temp=m;
    for (int i = 0; i < 5; i++) {
        if (temp / weishu[i] == 3)
            number++;
        temp = temp%weishu[i];
    }
    if (m % 19 == 0 && number == k)
        cout << "YES" << endl;
    else
        cout << "NO" << endl;
    return 0;
}