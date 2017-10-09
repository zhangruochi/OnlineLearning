# Uses python3
import sys


def fibonacci_partial_sum_naive_slow(from_, to):
    sum = 0
    current = 0
    #sequence = []
    next = 1

    for i in range(to + 1):
        if i >= from_:
            sum += current
            #sequence.append(sum % 10)

        current, next = next, current + next

    # print(sequence)
    return sum % 10


def fibonacci_partial_sum_naive(from_, to):
    digit_sequence = []

    prev = -1
    current = -1
    sum = 0

    flag = 0

    for _ in range(to + 1):
        # fibonacci sequence
        if _ < 2:
            prev, current = current, _
            continue
        else:
            prev, current = current, (current + prev) % 10

        # print("hello")
        if _ < from_:
            continue

        elif _ >= from_ and _ < from_ + 2:
            sum = (sum + current) % 10
            digit_sequence.append(sum)

        else:
            sum = (sum + current) % 10
            if sum == digit_sequence[flag]:
                flag += 1
                if flag == 2:
                    digit_sequence = digit_sequence[:-1]
                    break
            else:
                flag = 0

            digit_sequence.append(sum)

    # print(digit_sequence)
    return digit_sequence[(to - from_ + 1) % len(digit_sequence) - 1]


import random
random.seed(7)


def stree_test():

    pairs_seq = [(random.randint(50, 200), random.randint(20000, 30000))
                 for n in range(100)]

    for pair in pairs_seq:
        result1 = fibonacci_partial_sum_naive_slow(pair[0], pair[1])
        result2 = fibonacci_partial_sum_naive(pair[0], pair[1])

        print(result1, result2)

        if result1 != result2:
            print("error")
            break


def time_test():
    pairs_seq = [(random.randint(5000000000, 20000000000), random.randint(200000000000000000, 300000000000000000))
                 for n in range(100)]

    for pair in pairs_seq:
        result1 = fibonacci_partial_sum_naive(pair[0], pair[1])
        print(result1)


if __name__ == '__main__':
    #stree_test()
    #input = sys.stdin.read()
    #from_, to = map(int, input.split())
    #from_ =  121321312
    #to = 567712322222222222
    #print(fibonacci_partial_sum_naive_slow(from_, to))
    #print(fibonacci_partial_sum_naive(from_, to))
