# Uses python3
import sys

"""
def fibonacci_sum_naive_slow(n):
    if n <= 1:
        return n

    previous = 0
    current = 1
    sum = 1

    for _ in range(n - 1):
        previous, current = current, previous + current
        sum += current

    return sum % 10



def feel(n):
    if n <= 1:
        return n

    prev = 0
    current = 1
    sum = 1   
    sum_sequence = [0,1]

    for _ in range(n - 1):  
        prev,current = current, current + prev
        sum += current
        sum_sequence.append(sum % 10)  

    return sum_sequence    
"""


def fibonacci_sum_naive(n):
    digit_sequence = [0, 1]

    prev = 0
    current = 1
    sum = 1

    flag = 0

    for _ in range(n - 1):
        prev, current = current, current + prev
        sum += current
        last_digit = sum % 10

        if last_digit == 0 or last_digit == 1:
            flag += 1
            if flag == 2:
                break
        else:
            flag = 0

        digit_sequence.append(last_digit)
    print(digit_sequence)    
    return digit_sequence[n % len(digit_sequence)]


"""
import random

def stree_test():
    n_seq = [random.randint(0,1000) for x in range(100)]

    for n in n_seq:
        result_1 = fibonacci_sum_naive_slow(n)
        result_2 = fibonacci_sum_naive(n)

        print(n,result_1,result_2)

        if result_1 != result_2:
            print("fail at n = {}, result_1 = {}, result_2 = {}".format(n,result_1, result_2))
"""



if __name__ == '__main__':

    #stree_test()
    #input = sys.stdin.read()
    #n = int(input)
    n = 1000
    print(fibonacci_sum_naive(n))

