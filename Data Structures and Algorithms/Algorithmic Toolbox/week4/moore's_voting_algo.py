# Uses python3
import sys
from collections import defaultdict
import random

def get_majority_element(a, n):
    print(a)
    max_index = 0
    count = 1
    for i in range(1,n):
        if a[max_index] == a[i]:
            count += 1
        else:
            count -= 1  
        if count == 0:
            max_index = i
            count = 1


    count == 0
    for num in a:
        if num == a[max_index]:
            count += 1

    print(max_index)        
    if count <= (n//2):
        return -1

    return a[max_index]                    

def test():
    n = 100
    array = [random.randint(1,100) for i in range(50)] + [random.randint(1,100)] * 51
    print(get_majority_element(array,n))

if __name__ == '__main__':

    input = sys.stdin.read()
    n, *a = list(map(int, input.split()))
    if get_majority_element(a, n) != -1:
        print(1)
    else:
        print(0)
    