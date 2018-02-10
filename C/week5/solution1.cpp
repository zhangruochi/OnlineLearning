/*
描述

输入一个整数矩阵，计算位于矩阵边缘的元素之和。所谓矩阵边缘的元素，就是第一行和最后一行的元素以及第一列和最后一列的元素。

输入
2
4 4
1 1 1 1
0 0 0 0
1 0 1 0
0 0 0 0
3 3
3 4 1
3 7 1
2 0 1
第一行为整数k，表示有k组数据。

每组数据有多行组成，表示一个矩阵：

第一行分别为矩阵的行数m和列数n（m < 100，n < 100），两者之间以空格分隔。

接下来输入的m行数据中，每行包含n个整数，整数之间以空格作为间隔。

输出
5
15
输出对应矩阵的边缘元素和，一个一行。
*/

#include <stdio.h>
#include <stdlib.h>

void get_matrix(int **, int,int);
int get_value(int **,int,int);

int main(int argc, char const *argv[])
{
    int times;
    int row,column;
    int **matrix;

    scanf("%d",&times);
    for(int i=0;i<times;i++){
        scanf("%d %d",&row,&column);
        //printf("%d %d\n",row,column);
        matrix = (int**)malloc(sizeof(int*)*row);
        get_matrix(matrix,row,column);
        printf("%d\n",get_value(matrix,row,column));
    }
    return 0;
}

void get_matrix(int **matrix,int row, int column){

    for(int i=0;i<row;i++){
        matrix[i] = (int*)malloc(sizeof(int)*column);
        for(int j=0;j<column;j++){
              scanf("%d",*(matrix+i)+j);
        }
    }
    /*
    for(int i=0;i<row;i++){
        for(int j=0;j<column;j++){
              printf("%d\n",*(*(matrix+i)+j));
        }
    }
    exit(0);
    */
}

int get_value(int **matrix, int row, int column){
    int corner;
    int sum = 0;
    corner = **matrix + *(*matrix+column-1) + **(matrix+row-1) + *(*(matrix+row-1)+column-1);
    
    //第一行
    for(int i=0;i<column;i++){
        sum = sum + *(*matrix+i);
    }
    //第一列
    for(int i=0;i<row;i++){
        sum = sum+ **(matrix+i);
    }
    //最后一行
    for(int i=0;i<column;i++){
        sum = sum + *(*(matrix+row-1)+i);
    }
    //最后一列
    for(int i=0;i<row;i++){
        sum = sum + *(*(matrix+i)+column-1);
    }
    sum -= corner;
    
    return sum;
}













