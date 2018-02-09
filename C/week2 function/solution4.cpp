/*
描述

甲壳虫的《A day in the life》和《Tomorrow never knows》脍炙人口，如果告诉你a day in the life,真的会是tomorrow never knows?相信学了计概之后这个不会是难题，现在就来实现吧。

读入一个格式为yyyy-mm-dd的日期（即年－月－日），输出这个日期下一天的日期。可以假定输入的日期不早于1600-01-01，也不晚于2999-12-30。

输入
2010-07-05
输入仅一行，格式为yyyy-mm-dd的日期。

输出

输出也仅一行，格式为yyyy-mm-dd的日期
2010-07-06
样例输入
*/

/*
#include <stdio.h>

int main(int argc, char const *argv[])
{
    int year,month,day;
    int flag = 0;
    scanf("%d-%d-%d",&year,&month,&day);
    if(month==1 ||month == 3 || month ==5 || month==7 || month==8 || month==10 || month==12 ){
        if(day+1 <=31)
            day = day+1;
        else{
            flag = 1;
            day = (day+1)%31;
        }

    }else if(month==2){
        if(day+1<=28)
            day = day+1;
        else{
            flag = 1;
            day = (day+1)%28;
        }

    }else{

        if(day+1 <=30)
            day = day+1;
        else{
            flag = 1;
            day = (day+1)%30;
        }
    }

    if((month+flag) <=12){
        month = month+flag;
    }else{
        year = year+1;
        month = (month+flag)%12;
    }

    
    printf("%d-%02d-%02d",year,month,day);

    return 0;
}
*/

#include<stdio.h>

int main()
{
    int y,m,d;
    scanf("%d-%d-%d",&y,&m,&d);
    int f=1;
    if(m==1||m==3||m==5||m==7||m==8||m==10)
    {
        if(d>=31)
        {
            d=1;
            m++;
            f=0;//f的作用就是在判断了一种就不用再判断了，因为这个if中最大月份只有10，++之后也只有11，即不用再判断月份是否大于12
        }
        else
            d++;
    }
    if(m==12&&f)
    {
        if(d>=31)
        {
            m=1;
            y++;
            d=1;
        }
        else
            d++;
    }
    if(m==2&&f)
    {
        if((y%4==0&&y%100!=0)||y%400==0)
        {
            if(d>=29)
            {
                d=1;
                m++;
            }
            else
                d++;
        }   
        else
        {
            if(d>=28)
            {
                d=1;
                m++;
            }
            else
                d++;
        }
    }
    if((m==4||m==6||m==9||m==11)&&f)
    {   
        if(d>=30)
        {
            d=1;
            m++;
        }
        else
            d++;
    }
    printf("%d-%02d-%02d",y,m,d);
}


