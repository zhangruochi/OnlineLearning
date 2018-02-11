/*
描述

给定一个row行col列的整数数组array，要求从array[0][0]元素开始，按从左上到右下的对角线顺序遍历整个数组。

输入
3 4
1 2 4 7
3 5 8 10
6 9 11 12
输入的第一行上有两个整数，依次为row和col。

余下有row行，每行包含col个整数，构成一个二维整数数组。

（注：输入的row和col保证0 < row < 100, 0 < col < 100）

输出

按遍历顺序输出每个整数。每个整数占一行。

*/

#include <stdio.h>
#include <stdlib.h>

void get_matrix(int **, int,int);
void print_matrix(int **,int,int);

int main(int argc, char const *argv[])
{
    int row,column;
    int **matrix;
    scanf("%d %d",&row,&column);
    matrix = (int **)malloc(sizeof(int*)*row);
    get_matrix(matrix,row,column);
    print_matrix(matrix,row,column);

    return 0;
}


void print_matrix(int **matrix,int row,int column){
    int c;

    //对角线的条数为 row+column-1
    for(int i=0;i<row+column-1;i++){
        //列的起始点
        c = i;
        //遍历所有的行
        for(int j=0;j<row;j++,c--){
            //打印可见范围的数
            if(c<column && c>=0 )
                printf("%d\n",*(*(matrix+j)+c));
        }
    }    
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
