# Uses python3
import sys


def optimal_weight(W, w):
    value = [[0] * (W + 1) for i in range(len(w) + 1)]
    max_value = 0
    #print(value)
    for i in range(1, len(w) + 1):  # 每个物品的质量
        for j in range(1, W + 1):  # 总质量
            # 不包含wi
            value[i][j] = value[i - 1][j]
            # 包含wi
            if j >= w[i - 1]:
                val = value[i - 1][j - w[i - 1]] + w[i - 1]
                if val > value[i][j]:
                    value[i][j] = val

            if value[i][j] > max_value:
                max_value = value[i][j]
    #print(value)
    #print(max_value)
    return max_value


def test():
    W = 10
    w = [1, 4, 8]
    optimal_weight(W, w)


if __name__ == '__main__':
    #test()
    input = sys.stdin.read()
    W, n, *w = list(map(int, input.split()))
    print(optimal_weight(W, w))
