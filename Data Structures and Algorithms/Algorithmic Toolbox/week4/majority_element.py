# Uses python3
import sys
from collections import defaultdict
import random

def get_majority_element(a, n):
    num_dict = defaultdict(int)
    for num in a:
        num_dict[num] += 1
        if num_dict[num] > (n // 2):
            return 0
    return -1       


def test():
    n = 100
    array = [random.randint(1,100) for i in range(50)] + [random.randint(1,100)] * 51
    print(get_majority_element(array,n))

if __name__ == '__main__':
    #test()

    input = sys.stdin.read()
    n, *a = list(map(int, input.split()))
    if get_majority_element(a, n) != -1:
        print(1)
    else:
        print(0)
    