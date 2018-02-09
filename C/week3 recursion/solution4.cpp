/*
描述

在某个字符串（长度不超过100）中有左括号、右括号和大小写字母；规定（与常见的算数式子一样）
任何一个左括号都从内到外与在它右边且距离最近的右括号匹配。写一个程序，
找到无法匹配的左括号和右括号，输出原来字符串，并在下一行标出不能匹配的括号。
不能匹配的左括号用"$"标注,不能匹配的右括号用"?"标注.

输入
((ABCD(x)
)(rttyy())sss)(

输入包括多组数据，每组数据一行，包含一个字符串，只包含左右括号和大小写字母，字符串长度不超过100

注意：cin.getline(str,100)最多只能输入99个字符！

输出
((ABCD(x)
$$
)(rttyy())sss)(
?            ?$
对每组输出数据，!!!输出两行，第一行包含原始输入字符!!!，第二行由"$","?"和空格组成，
"$"和"?"表示与之对应的左括号和右括号不能匹配。
*/

#include <stdio.h>
#include <iostream>
#include <stack>
#include <vector>
#include <memory.h> 
using namespace std;
int main(){
    char input[101];
    stack<char> buf;
    vector<int> leftIndex;//记录堆栈中剩余的左括号所在的下标
    while (scanf("%s", input) != EOF){
        //清空堆栈
        while (!buf.empty()){
            buf.pop();
        }
        //清空Vector
        leftIndex.clear();

        char aOutput[101];//记录输出
        //初始化输出缓冲
        memset(aOutput, ' ', sizeof(aOutput));
        aOutput[100] = '\0';

        for (int i = 0; i < 101; i++){
            if (input[i] == '('){
                buf.push('(');
                leftIndex.push_back(i);
            }
            else if (input[i] == ')'){
                if (!buf.empty()){
                    buf.pop();
                    leftIndex.pop_back();

                }
                else{
                    aOutput[i] = '?';
                }
            }
            else if (input[i] == '\0'){
                aOutput[i] = '\0';
                break;
            }
            else
            {
                continue;
            }
        }

        while (!buf.empty()){

            aOutput[leftIndex.back()] = '$';
            leftIndex.pop_back();
            buf.pop();
        }
        printf("%s\n", input);
        printf("%s\n", aOutput);
    
    }
    return 0;

}