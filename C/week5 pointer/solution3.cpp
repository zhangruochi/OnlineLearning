/*

描述

给一段英文短文，单词之间以空格分隔（每个单词包括其前后紧邻的标点符号）。请将短文重新排版，要求如下：

每行不超过80个字符；每个单词居于同一行上；在同一行的单词之间以一个空格分隔；行首和行尾都没有空格。

输入

第一行是一个整数n，表示英文短文中单词的数目. 其后是n个以空格分隔的英文单词（单词包括其前后紧邻的标点符号，且每个单词长度都不大于40个字母）。

输出

排版后的多行文本，每行文本字符数最多80个字符，单词之间以一个空格分隔，每行文本首尾都没有空格。


*/

#include <iostream>
#include <cstring>
using namespace std;
int main()
{
    char words[1000][40]; //用于保存每一个单词
    int wordsLen[1000]; // 记录每一个单词的长度
    int n; // 需要处理的单词总数
    cin >> n;
    cin.get();//cin之后要用cin.get()读取换行符，一开始没注意到错误在这里，还是基础不牢靠
    for (int i = 0; i < n;i++) //输入单词数据，处理后得到每个单词的长度
    {
        char temp;
        for (int j = 0; j < 40;j++) {
            cin.get(temp);              
                if (temp != ' '&&temp != '\n') {
                    words[i][j] = temp;
                    
                }
                else
                {
                    wordsLen[i] = j;
                    break;
                }
            }
        
    }
    //先输出第一个单词
    int length = wordsLen[0];
    for (int j = 0; j < wordsLen[0]; j++)
        cout << words[0][j];
    for (int i = 1; i < n; i++)
    {
        //如果该单词，连同前面的一个空格加入后不换行，则输出空格和该单词
        if (length + 1 + wordsLen[i] <= 80)
        {
            length = length + 1 + wordsLen[i];
            cout << ' ';
            for (int j = 0; j < wordsLen[i]; j++)
                cout << words[i][j];
        }
        else//如果该单词，连同前面的一个空格加入后换行，则输出回车和该单词，另外重置本行现有长度为单词长度
        {
            cout << endl;
            length = wordsLen[i];
            for (int j = 0; j < wordsLen[i]; j++)
                cout << words[i][j];
        }
    }
    return 0;
}