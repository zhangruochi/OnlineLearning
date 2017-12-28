#!/usr/bin/env python3

# info
# -name   : zhangruochi
# -email  : zrc720@gmail.com


import random
import time
import copy


def partition(array, l, r):
    j = l
    for i in range(l+1, r+1):
        if array[i] <= array[l]:
            j += 1
            array[j], array[i] = array[i], array[j]
    array[l], array[j] = array[j], array[l]
    return j


def quick_sort(array, l, r):
    if l >= r:
        return

    pivot = partition(array, l, r)
    quick_sort(array, l, pivot - 1)
    quick_sort(array, pivot + 1, r)

    return array


#随机版本的快排 
def random_quick_sort(array,l,r):
    if l >= r:
        return 

    random_spot = random.randint(l,r) 
    array[l],array[random_spot] = array[random_spot],array[l]

    pivot = partition(array,l,r)
    random_quick_sort(array,l,pivot-1)
    random_quick_sort(array,pivot+1,r)

    return array


def sort_test():
    n = 10000
    ascend = list(range(n))
    descend = list(range(n))[::-1]
    random_array = [random.randint(0,n) for x in range(n)]
    special = [500] * (n-1000) + [random.randint(0,n) for x in range(1000)];random.shuffle(special)

    for array in [ascend,descend,random_array,special]:
        start = time.time()
        random_quick_sort(array,0,len(array)-1)
        end = time.time()
        #print(array[0:100])
        print("using time: {}".format(end - start))
        print("")


if __name__ == '__main__':
    #sort_test()
    a = [2, 5, 6, 6, 6, 6, 7, 8, 6, 6, 3, 9]
    random_quick_sort(a,0,len(a)-1)
    print(a)


