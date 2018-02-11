/*
描述

脱氧核糖核酸（DNA）由两条互补的碱基链以双螺旋的方式结合而成。而构成DNA的碱基共有4种，
分别为腺瞟呤（A）、鸟嘌呤（G）、胸腺嘧啶（T）和胞嘧啶（C）。我们知道，
在两条互补碱基链的对应位置上，腺瞟呤总是和胸腺嘧啶配对，鸟嘌呤总是和胞嘧啶配对。
你的任务就是根据一条单链上的碱基序列，给出对应的互补链上的碱基序列。

输入
5
ATATGGATGGTGTTTGGCTCTG
TCTCCGGTTGATT
ATATCTTGCGCTCTTGATTCGCATATTCT
GCGTTTCGTTGCAA
TTAACGCACAACCTAGACTT
第一行是一个正整数n，表明共有n条要求解的碱基链。

以下共有n行，每行用一个字符串表示一条碱基链。这个字符串只含有大写字母A、T、G、C，分别表示腺瞟呤、胸腺嘧啶、鸟嘌呤和胞嘧啶。每条碱基链的长度都不超过255。

输出
TATACCTACCACAAACCGAGAC
AGAGGCCAACTAA
TATAGAACGCGAGAACTAAGCGTATAAGA
CGCAAAGCAACGTT
AATTGCGTGTTGGATCTGAA
共有n行，每行为一个只含有大写字母A、T、G、C的字符串。分别为与输入的各碱基链互补的碱基链。

*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int cont_str(char *s)
{
    int i = 0;      
    while ( s[i++] != '\0')
        ;
    return i;
}

int main(int argc, char const *argv[])
{
    int num,count;
    char line[256];
    char result[256];

    scanf("%d",&num);
    getchar();
    
    for(int i=0;i<num;i++){
        gets(line);
        count = 0;

        while(line[count]!='\0'){

            switch(line[count]){
                case 'A': 
                result[count] = 'T';
                break;

                case 'T':
                result[count] = 'A';
                break;

                case 'G':
                result[count] = 'C';
                break;

                case 'C':
                result[count] = 'G';
                break;

                default: exit(0);
            }
            count ++;  
        }
        result[count] = '\0';
        printf("%s\n",result);   
    }

    return 0;
}