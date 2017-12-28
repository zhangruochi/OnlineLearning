# Uses python3
import sys
import random
import time


def partition3(array, l, r):
    j = l
    for i in range(l+1, r+1):
        if array[i] <= array[l]:
            j += 1
            array[j], array[i] = array[i], array[j]
    array[l], array[j] = array[j], array[l] 

    count = 1
    k = j-1
    while k >= l:
        if array[k] != array[j]:
            break
        else:
            k -= 1
            count += 1
    
    return j,count



def randomized_quick_sort(a, l, r):
    if l >= r:
        return
    k = random.randint(l, r)
    a[l], a[k] = a[k], a[l]
    m,count = partition3(a, l, r)
    randomized_quick_sort(a, l, m - count)
    randomized_quick_sort(a, m + 1, r)


def sort_test():
    n = 1000000
    ascend = list(range(n))
    descend = list(range(n))[::-1]
    random_array = [random.randint(0, n) for x in range(n)]
    special = [500] * (n - 1000) + [random.randint(0, n) for x in range(1000)]
    random.shuffle(special)

    for array in [ascend, descend, random_array, special]:
        start = time.time()
        randomized_quick_sort(array, 0, len(array) - 1)
        end = time.time()
        # print(array[0:100])
        print("using time: {}".format(end - start))
        print("")


# sort_test()


if __name__ == '__main__':
    
    #sort_test()
    #a = [2, 5, 6, 6, 6, 6, 7, 8, 6, 6, 3, 9]
    #randomized_quick_sort(a,0,len(a)-1)
    #print(a)


    
    input = sys.stdin.read()
    n, *a = list(map(int, input.split()))
    randomized_quick_sort(a, 0, n - 1)
    for x in a:
        print(x, end=' ')
        
