#include <iostream>
using namespace std;
int main() {
    int t;
    cin >> t;
    for (int i = 0; i < t; i++) {
        int m = 0, n = 0;
        cin >> m >> n;
        int shuzu[100][100];//矩阵最大100*100
        int sum = 0;
        for (int j = 0; j < m; j++) {
            for (int o = 0; o < n; o++) {
                cin >> shuzu[j][o];//输入数组数值
                if (j == 0 || j == m - 1)//先算首行，末行
                    sum += shuzu[j][o];
                else if(o==0||o==n-1)//else 之后再用if 找除了四个角落之外的首列，末列
                    sum += shuzu[j][o];
            }
        }
        cout << sum << endl;
    }
    return 0;
}