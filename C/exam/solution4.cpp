#include <iostream>
using namespace std;
struct point {//用以记录每一个点
    int x;//横坐标
    int y;//纵坐标
};
int main() {
    int n;
    cin >> n;
    point points[100];//从来接受所有的点
    for (int i = 0; i < n; i++) 
        cin >> points[i].x >> points[i].y;
    point maxs[100];//用来保存所有的极大点
    int k = 0;
    for (int i = 0; i < n; i++) {
        bool max = true;//初始化这个点没有被支配
        for (int j = 0; j < n; j++) {
            if (j== i)
                continue;
            else if (((points[j].x >= points[i].x)&&(points[j].y >= points[i].y)) == true)//判断是否被支配，如果支配，则将max改为假
                max = false;
        }
        if (max == true) {//把没有被支配的点保存下来
            maxs[k] = points[i];
            k++;
        }
    }
    k--;//因为之前最后一次多了一个k++,所以这里-回来
    for (int i = 0; i < k + 1; i++) {//排序
        for (int j = i+1; j < k + 1; j++) {
            if (maxs[i].x > maxs[j].x) {
                point temp = maxs[i];
                maxs[i] = maxs[j];
                maxs[j] = temp;
            }
            else if (maxs[i].x == maxs[j].x&&maxs[i].y > maxs[j].y) {
                point temp = maxs[i];
                maxs[i] = maxs[j];
                maxs[j] = temp;
            }
        }
    }
    for (int i = 0; i < k+1 ; i++) {//输出，最后一次没有，
        cout << '(' << maxs[i].x << ',' << maxs[i].y << ')';
        if (i == k)
            cout << endl;
        else
            cout << ',';
    }
    return 0;
}