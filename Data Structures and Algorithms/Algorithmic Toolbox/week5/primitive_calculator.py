# Uses python3
import sys

"""
def optimal_sequence(n):
    memory = {1: 0}

    for i in range(2, n+1):
        min_i = float("inf")
        for j in range(1, i):
            if j + 1 == i or j * 2 == i or j * 3 == i:
                if(memory[j] + 1) < min_i:
                    min_i = memory[j] + 1
        memory[i] = min_i
    print(memory)    
    return memory[n]
"""


def optimal_sequence(n):
    memory = {1: 0}
    operations = [0]
    result = [n]

    for i in range(2, n + 1):
        min_i = float("inf")
        opt = ""
        if i - 1 in memory:
            memory[i] = memory[i - 1] + 1
            opt = 1
        if i / 2 in memory and memory[i // 2] + 1 < memory[i]:
            memory[i] = memory[i / 2] + 1
            opt = 2
        if i / 3 in memory and memory[i // 3] + 1 < memory[i]:
            memory[i] = memory[i / 3] + 1
            opt = 3
        operations.append(opt)
    
    times = memory[n]    
    for i in range(memory[n]):
        if operations[n - 1] == 1:
            n = n - 1
        elif operations[n - 1] == 2:
            n = n // 2
        elif operations[n - 1] == 3:
            n = n // 3
        result.insert(0,n)

    return result  
    

"""
def test(n):
    print(optimal_sequence(n))


test(96234)

"""
input = sys.stdin.read()
n = int(input)
sequence = list(optimal_sequence(n))
print(len(sequence) - 1)
for x in sequence:
    print(x, end=' ')
