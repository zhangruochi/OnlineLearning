/*
描述

某个科室的病房分为重症和普通，只有当病人的疾病严重程度超过了入住重症病房的最低严重值，才可以安排入住重症病房。

现在要求设计一个程序，给病人安排好病房。疾病的严重程度用0到10来表示，0表示小毛病，10表示非常严重。

输入
10 7.55
006 6.5
005 8.0
004 3.5
009 8.5
011 7.0
043 9.5
003 5.0
103 6.0
112 4.0
118 9.0

第一行输入病人的个数m(m < 50)，以及安排住入重症病房的最低严重值a

紧接着m行，每行表示病人编号（三个位，用0补齐）及其疾病的严重程度（浮点数，1位小数）。

每个病人的疾病严重程度都不一样。

输出
043 9.5
118 9.0
009 8.5
005 8.0
要求按照病人的严重程度输出住在重症病房里的病人的编号

注意：

如果当前所有病人的严重程度并不满足住在重症病房里，则输出“None.”（不包括引号）

*/

#include <iostream>
#include <iomanip>
using namespace std;

struct Patient {
    int IDnum;
    float level;
}p[51]; //定义结构体用于存放病人信息

int main()
{
    int m, num=0;
    float a;
    cin >> m >> a;
    Patient temp;
    for (int i = 0; i < m; i++) {
        cin >> p[i].IDnum >> p[i].level;
        if (p[i].level > a) num++;

    }
    if (num == 0) {
        cout << "None.";
        return 0;
    }

    for (int i = 0; i < m - 1; i++) {    //对所有病人，根据其病的严重程度排序
        for (int j = 0; j < m - i - 1; j++) {
            if (p[j].level < p[j + 1].level) {
                temp = p[j];
                p[j] = p[j + 1];
                p[j + 1] = temp;
            }
        }
    }

    for (int i = 0; i < num; i++) {
        cout.fill('0');
        cout.width(3);
        cout << p[i].IDnum << " ";
        cout << setiosflags(ios::fixed) << setprecision(1) << p[i].level << endl;
    }

        return 0;
    
}


