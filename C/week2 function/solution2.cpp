/*
描述

我国有4大淡水湖。

A说：洞庭湖最大，洪泽湖最小，鄱阳湖第三。

B说：洪泽湖最大，洞庭湖最小，鄱阳湖第二，太湖第三。

C说：洪泽湖最小，洞庭湖第三。

D说：鄱阳湖最大，太湖最小，洪泽湖第二，洞庭湖第三。

已知这4个湖的大小均不相等，4个人每人仅答对一个，

请编程按照鄱阳湖、洞庭湖、太湖、洪泽湖的顺序给出他们的大小排名。

输入

无。

输出
{1,2,3,4}
输出为4行，第1行为鄱阳湖的大小名次，从大到小名次分别表示为1、2、3、4；第2、3、4行分别为洞庭湖、太湖、洪泽湖的大小名次
*/

#include "stdio.h"
int main(){

    int arrayA[] = {3,1,2,4};
    int arrayB[] = {2,4,3,1};
    int arrayC[] = {0,3,0,4};
    int arrayD[] = {1,3,4,2};

    int perm[4];

    int count = 0;
    for(int i=1;i<=4;i++){

        for(int j=1;j<=4;j++){
            for(int m=1;m<=4;m++){
                for(int n=1;n<=4;n++){
                    if(i!=j&&i!=m&&i!=n&&j!=m&&j!=n&&m!=n){
                        count = 0;

                        perm[0] = i;
                        perm[1] = j;
                        perm[2] = m;
                        perm[3] = n;
                        
                        for(int k=0;k<4;k++){
                            if(perm[k] == arrayA[k]){
                                count++;
                            }
                            if(perm[k] == arrayB[k]){
                                count++;
                            }
                            if(perm[k] == arrayC[k]){
                                count++;
                            }
                            if(perm[k] == arrayD[k]){
                                count++;
                            }
                        }
                        //printf("%d,%d,%d,%d,%d\n",count,i,j,m,n);

                        if(count == 4){
                            for(int k=0;k<4;k++){
                                printf("%d\n",perm[k]);
                            }
                            return 0;
                        }
                    }
                }
            }
        }
        
    }
    
    return 0;
}