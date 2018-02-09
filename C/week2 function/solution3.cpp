/*
描述

有一个小型的报账系统，它有如下功能：

（1）统计每个人所报发票的总钱数

（2）统计每类发票的总钱数

将此系统简化为如下：假设发票类别共有A、B、C三种;一共有三个人，ID分别为1、2、3。

输入
1 5 A 1.0 A 2.0 C 1.0 B 1.0 C 1
3 3 B 1 C 2 C 1
2 4 B 1 A 1 C 1 A 1


系统输入包含三行，每行第一个数为人员ID（整型，1或2或3），第二个数为发票总张数(张数不超过100)，之后是多个发票类别（字符型，A或B或C）和相应发票金额（单进度浮点型,不超过1000.0）。

输出

输出包含六行，前三行为每人（按ID由小到大输出）所报发票总钱数（保留两位小数），后三行为每类发票的总钱数（保留两位小数）。
1 6.00
2 4.00
3 4.00
A 5.00
B 3.00
C 6.00

*/

#include "stdio.h"

int main(int argc, char const *argv[])
{
    float one = 0.0, two = 0.0, three = 0.0;
    float labelA = 0.0, labelB = 0.0, labelC = 0.0;
    int id = 0;
    //printf("fuck!\n");
    
    for(int j=0;j<3;j++){
        
        scanf("%d",&id);
            
        if(id == 1){
            int times = 0;
            scanf("%d",&times);
            for(int i=0;i<times;i++){
                char label;
                float num;
                getchar();//换行符
                scanf("%c",&label);
                scanf("%f",&num);
                one += num;

                if(label == 'A')
                    labelA += num;
                else if(label == 'B')
                    labelB += num;
                else 
                    labelC += num;     
            }
            
        }
        else if(id == 2){
            int times = 0;
            scanf("%d",&times);
            for(int i=0;i<times;i++){
                char label;
                float num;
                getchar();//换行符
                scanf("%c",&label);
                scanf("%f",&num);
                two += num;

                if(label == 'A')
                    labelA += num;
                else if(label == 'B')
                    labelB += num;
                else 
                    labelC += num;     
            }
        }
        else if(id == 3){

            int times = 0;
            scanf("%d",&times);
            for(int i=0;i<times;i++){
                char label;
                float num;
                getchar();//换行符
                scanf("%c",&label);
                scanf("%f",&num);

                three += num;
                if(label == 'A')
                    labelA += num;
                else if(label == 'B')
                    labelB += num;
                else 
                    labelC += num;     
            }    
        }
    }

    printf("1 %.2f\n",one);
    printf("2 %.2f\n",two);
    printf("3 %.2f\n",three);

    printf("A %.2f\n",labelA );
    printf("B %.2f\n",labelB );
    printf("C %.2f\n",labelC );

    return 0;
}

