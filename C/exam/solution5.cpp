#include <iostream>
using namespace std;

int Row, Column;
char map[101][101] = {'\0'};
int f(int x, int y)//起点坐标x,y
{
    if (x<0 || y<0 || x>=Row || y>=Column)//起点不在迷宫内
    {
        return -1;//记为无穷大，不可能实现
    }
    if (map[x][y] == '#')//此点不可作为起点
    {
        return -1;
    }
    else if (map[x][y] == 'T')//起点即为终点
    {
        return 0;
    }
    else
    {
        map[x][y] = '#';//将起点置为不可走，以免陷入无限循环
        int Step[4];
        Step[0] = f(x-1, y);    
        Step[1] = f(x+1, y);
        Step[2] = f(x, y-1);
        Step[3] = f(x, y+1);
        for (int i = 0; i < 3; i++)//从小到大排序
        {
            for (int j = i+1; j < 4; j++)
            {
                if (Step[i]>Step[j])
                {
                    int tmp = Step[i];
                    Step[i] = Step[j];
                    Step[j] = tmp;
                }
            }
        }
        int min;        
        for (int i = 0; i < 4; i++)//找出最小值
        {
            if (Step[i] != -1)
            {
                min = Step[i];
                map[x][y] = '.';//恢复起点
                return min+1;//返回所需最少部数
            }
        }       
    }
    return -1;
}

int main()
{   
    cin >> Row >> Column;   
    int x,y;
    for(int i = 0; i < Row; i++)
    {
        for (int j = 0; j < Column; j ++)
        {
            cin >> map[i][j];
            if (map[i][j] == 'S')//找出起点
            {
                x = i;
                y = j;
            }
        }
    }   
    int MinStep = f(x, y);
    cout << MinStep << endl;
    return 0;
}