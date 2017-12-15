# Uses python3
import sys
import random


# complexity nlogn

def get_majority_element(a):

    if len(a) == 1:
        return a[0]

    ele1 = get_majority_element(a[:len(a) // 2])
    ele2 = get_majority_element(a[len(a) // 2:])

    if ele1 == -1 and ele2 == -1:
        return -1

    if ele1 > 0 and ele2 > 0:
        tmp1 = judge(a, ele1)
        tmp2 = judge(a, ele2)
        return tmp1 if tmp1 > tmp2 else tmp2

    if ele1 > 0 and ele2 == -1:
        return judge(a, ele1)

    if ele2 > 0 and ele1 == -1:
        return judge(a, ele2)

    return "error"


def judge(a, k):
    count = 0
    for num in a:
        if k == num:
            count += 1

    if count > len(a) // 2:
        return k
    return -1

# test


def test():
    n = 100
    array = [random.randint(1, 100)
             for i in range(100)] + [random.randint(1, 100)] * 51
    print(get_majority_element(array))


if __name__ == '__main__':
    # test()

    input = sys.stdin.read()
    n, *a = list(map(int, input.split()))
    #a = [1, 3, 2, 2, 1, 1, 1, 2, 1]
    if get_majority_element(a) != -1:
        print(1)
    else:
        print(0)
