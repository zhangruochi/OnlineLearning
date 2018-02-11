/*
描述

输入一串长度不超过500个符号的字符串，输出在串中出现第2多的英语字母(大小写字母认为相同)和次数（如果串中有其它符号，则忽略不考虑）。如果有多个字母的次数都是第2多，则按串中字母出现的顺序输出第1个。

例 ab&dcAab&c9defgb

这里，a 和 b都出现3次，c和d都出现2次，e、f 和 g 各出现1次，其中的符号&和9均忽略不考虑。因此，出现第2多的应该是 c 和 d，但是 d 开始出现的位置在 c 的前面，因此，输出为

D+d:2

(假定在字符串中，次数第2多的字母总存在)

输入

一个字符串

输出

大写字母+小写字母:个数

*/

#include <stdio.h>
#include <iostream>
#include <string.h>

using namespace std;

int main()
{
    int count1[26] = { 0 };
    char a[1000];
    cin.getline(a, 1000);
    //cout << isChar((a + 2));
    //cout << strlen(a)<<endl;
    for (int i = 0; i < strlen(a);++i)
    {
        if (a[i] >= 65 && a[i] <= 90)       //upper character
            count1[a[i] - 65]++;
        else if (a[i] >= 97 && a[i] <= 122)     //lower character to upper character
        {
            count1[a[i] - 97]++;
            a[i] -= 32;
        }
    }
    int firstMax = 0, secondMax = 0;
    for (int i = 0; i < 26;++i)
    {
        if (count1[firstMax] <= count1[i])      //找最大
            firstMax = i;                   
    }
    for (int i = 0;i < 26;++i)
    {
        if (count1[i] == count1[firstMax])          //最大清零
            count1[i] = 0;
    }
    for (int i = 0;i < 26;++i)
    {
        if (count1[secondMax] <= count1[i])         //找第二大（最大已经清零所以第二大其实是最大）
            secondMax = i;                  
    }
    int num = count1[secondMax];            
    for (int i = 0;i < 26;++i)
    {
        if (count1[i] == count1[secondMax])         //第二大用500标记
            count1[i] = 500;
    }
    for (int i = 0;i < strlen(a);++i)
    {
        if (count1[a[i] - 65] == 500)               //找到第一个第二大的，输出
        {
            //printf("%c+%c:%d", a[i], a[i] + 32, num); 
            cout<<char(a[i])<<"+"<<char(a[i]+32)<<":"<<num;
            return 0;
        }
    }
    return 0;
}