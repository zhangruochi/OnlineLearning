/*
描述

在一个m×n的山地上，已知每个地块的平均高程，请求出所有山顶所在的地块（所谓山顶，就是其地块平均高程不比其上下左右相邻的四个地块每个地块的平均高程小的地方)。

输入

第一行是两个整数，表示山地的长m（5≤m≤20）和宽n（5≤n≤20）。

其后m行为一个m×n的整数矩阵，表示每个地块的平均高程。每行的整数间用一个空格分隔。

输出

输出所有上顶所在地块的位置。每行一个。按先m值从小到大，再n值从小到大的顺序输出。
*/

#include<iostream>
using namespace std;
int main() {
    int m, n;
    cin >> m >> n;
    int mount[20][20];
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            cin >> mount[i][j];
        }
    }
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (((mount[i][j] >= mount[i-1][j]&&i-1>=0)||(i-1<0))&& ((mount[i][j] >= mount[i + 1][j] && i + 1 <= m-1) || (i + 1>m-1))&& ((mount[i][j] >= mount[i][j-1] && j - 1 >= 0) || (j - 1<0))&& ((mount[i][j] >= mount[i][j+1] && j + 1 <= n-1) || (j + 1>n-1)))//逻辑关系的运算，重点是对边框处的处理
                cout << i << ' ' << j << endl;
        }
    }
    return 0;
}