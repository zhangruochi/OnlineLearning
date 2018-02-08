/*
编程题＃5：细菌实验分组

注意： 总时间限制: 1000ms 内存限制: 65536kB

描述

有一种细菌分为A、B两个亚种，它们的外在特征几乎完全相同，仅仅在繁殖能力上有显著差别，A亚种繁殖能力非常强，
B亚种的繁殖能力很弱。在一次为时一个 小时的细菌繁殖实验中，实验员由于疏忽把细菌培养皿搞乱了，
请你编写一个程序，根据实验结果，把两个亚种的培养皿重新分成两组。

输入
5
1 10 3456
2 10 5644
3 10 4566
4 20 234
5 20 232

输入有多行，第一行为整数n（n≤100），表示有n个培养皿。

其余n行，每行有三个整数，分别代表培养皿编号，试验前细菌数量，试验后细菌数量。

输出
3
1
3
2
2
5
4
输出有多行：

第一行输出A亚种培养皿的数量，其后每行输出A亚种培养皿的编号，按繁殖率升序排列。

然后一行输出B亚种培养皿的数量，其后每行输出B亚种培养皿的编号，也按繁殖率升序排列。


提示

亚种内部细菌繁殖能力差异 远远小于 亚种之间细菌繁殖能力差异。

也就是说，亚种间任何两组细菌的繁殖率之差都比亚种内部两组细菌的繁殖率之差大。

*/



#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#define EPSILON 0.000001


void swap(float array[], int i, int j){
    float tmp;
    tmp = array[j];
    array[j] = array[i];
    array[i] = tmp;
}

void bubble(float array[],int n){
    bool flag;
    while(1){
        flag = 1;
        for(int i=0; i<n-1;i++){
            if(array[i] > array[i+1]){
                swap(array,i,i+1);
                flag = 0;
            }
        }

        if(flag)
            break;
        
        n = n-1;
    }
}

void printArray(float array[],int n){
    for(int i=0;i<n;i++){
        printf("%f ",array[i]);
    }
}


int main(){
    //int array[] = {5,3,4,2,1};
    //int n = 5;
    //bubble(array,n);
    //printArray(array,n);
    int boxes;
    float a,b;

    int position = 0;

    scanf("%d",&boxes);
    float record[boxes*2];
    float reproductiveRate[boxes];
    
    for(int i=0;i<boxes;i++){
        scanf("%f %f %f",&record[i*2],&a,&b);
        record[i*2+1] = b/a;
        reproductiveRate[i] = b/a;
    }

    bubble(reproductiveRate,boxes);
    //printArray(reproductiveRate,boxes);
    //printf("\n");
    //printArray(record,boxes*2);
    //printf("\n");


    for(int i=0;i<boxes-1;i++){
        if((reproductiveRate[i+1] - reproductiveRate[i] > reproductiveRate[boxes] - reproductiveRate[i+1]) &&
            (reproductiveRate[i+1] - reproductiveRate[i] > reproductiveRate[i] - reproductiveRate[0])){
            position = i;
        }
    }
    
    printf("%d\n", boxes-(position+1));
    for(int i=position+1;i<boxes;i++){
        for(int j=1; j<boxes*2; j=j+2){
            //printf("%f,%f\n",record[j],reproductiveRate[i] );
            if(fabs(record[j]-reproductiveRate[i])<EPSILON){
                printf("%d\n",(int)record[j-1]);
                break;
            }
        }
    }
    
    printf("%d\n", position+1);
    for(int i=0;i<position+1;i++){
        for(int j=1; j<boxes*2; j=j+2){
            //printf("%f,%f\n",record[j],reproductiveRate[i] );
            if(fabs(record[j]-reproductiveRate[i])<EPSILON){
                printf("%d\n",(int)record[j-1]);
                break;
            }
        }
    }


    return 0;
}







