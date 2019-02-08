#Uses python3

import sys
from collections import dequeue

def distance(adj, s, t):
    path = 0
    queue = dequeue([s])
    visited = set()

    while queue:
        cur = queue.pop()
        path += 1
        if cur == t:
            return path

        for node in adj[cur]:
            if node not in visited:
                queue.append(node)
                visited.add(node)

    return -1

if __name__ == '__main__':
    input = sys.stdin.read()
    data = list(map(int, input.split()))
    n, m = data[0:2]
    data = data[2:]
    edges = list(zip(data[0:(2 * m):2], data[1:(2 * m):2]))
    adj = [[] for _ in range(n)]
    for (a, b) in edges:
        adj[a - 1].append(b - 1)
        adj[b - 1].append(a - 1)
    s, t = data[2 * m] - 1, data[2 * m + 1] - 1
    print(distance(adj, s, t))
