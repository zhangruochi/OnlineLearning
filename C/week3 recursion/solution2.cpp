/*
描述

所谓角谷猜想，是指对于任意一个正整数，如果是奇数，则乘3加1，如果是偶数，则除以2，得到的结果再按照上述规则重复处理，最终总能够得到1。如，假定初始整数为5，计算过程分别为16、8、4、2、1。

程序要求输入一个整数，将经过处理得到1的过程输出来。
*/

#include <stdio.h>

void Kakutani(int);

int main(int argc, char const *argv[])
{   
    int number;
    scanf("%d",&number);
    Kakutani(number);
    printf("End");
    
    return 0;
}

void Kakutani(int number){
    if(number == 1)
        return ;
    else{
        int result;
        if(number%2 == 0){
            result = number/2;
            printf("%d/2=%d\n",number,result);
            Kakutani(result);
        }else{
            result = number*3+1;
            printf("%d*3+1=%d\n",number,result);
            Kakutani(result);
        }
    }
    return ;
}