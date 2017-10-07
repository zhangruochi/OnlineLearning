#python3

def fast_method(a, n):
    max_indice_1 = -1
    for i in range(n):
        if max_indice_1 == -1 or a[i] > a[max_indice_1]:
            max_indice_1 = i

    max_indice_2 = -1
    for j in range(n):
        if j != max_indice_1 and (max_indice_2 == -1 or a[j] > a[max_indice_2]):
            max_indice_2 = j

    #print(max_indice_1, max_indice_2)

    return a[max_indice_1] * a[max_indice_2]

n = int(input())
a = [int(x) for x in input().split()]
assert(len(a) == n)
result = fast_method(a,n)
print(result)







"""
def slow_method(a, n):
    result = 0
    for i in range(0, n):
        for j in range(i + 1, n):
            if a[i] * a[j] > result:
                result = a[i] * a[j]
    return result


import random

def stress_test():
    random.seed(10)

    while(True):
        # the number of sequence
        n = random.randint(1000,10000) * 2
        # the sequence
        a = [random.randint(0, 10) * 10 for _ in range(n)]

        #print(n)
        #print(a)

        result_fast = fast_method(a, n)
        result_slow = slow_method(a, n)

        if result_fast == result_slow:
            print("result_fast: {}, result_slow: {}".format(
                result_fast, result_slow))
        else:
            print("result_fast: {}, result_slow: {}".format(
                result_fast, result_slow))
            break

        print("")


if __name__ == '__main__':
    stress_test()
"""