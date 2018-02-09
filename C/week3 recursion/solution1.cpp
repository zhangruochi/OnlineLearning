/*

描述

输入一个句子（一行），将句子中的每一个单词翻转后输出。

输入
hello    world.
只有一行，为一个字符串，不超过500个字符。单词之间以空格隔开。所谓单词指的是所有不包含空格的连续的字符。

这道题请用cin.getline输入一行后再逐个单词递归处理。

输出
olleh    .dlrow
翻转每一个单词后的字符串，单词之间的空格需与原文一致。

*/

#include <stdio.h>


void swap_word(char *,int left,int right);


int main(int argc, char const *argv[])
{   

    /*
    char word[14] = {'h','e','l','l','o',' ',' ',' ',' ','w','o','r','l','d'};
    swap_word(word,9,13);
    for(int i=0;i<14;i++)
        printf("%c \n",word[i]);
    */

    
    char line[501];
    gets(line);
    int right;
    int tmp_left=0;

    for(int left=0; left<501;left++){
        if(line[left] == '\0')
            break;
        else{
            if(line[left] == ' ')
                continue;
            else{
                tmp_left = left;
                while(line[left] != ' ' && line[left] != '\0')
                    left++;
                right = left-1;
                swap_word(line,tmp_left,right);
            }

        }

    }
    printf("%s",line);

    return 0;
    }

void swap_word(char *word,int left, int right){
    char tmp; 
    int len = right - left + 1;
    //printf("%s\n",word);
    for(int i=left; i< left+len/2; i++){
        tmp = word[i];
        word[i] = word[right];
        word[right] = tmp;
        right --;
        //printf("%d %s\n",i,word);
    }
}



