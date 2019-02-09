#Uses python3

import sys
import queue
from heapq import *

def distance(adj, cost, s, t):
    #write your code here
    q,visited, mins  = [(0,s)], set(), {s:0}
    while q:
        (c,v1) = heappop(q)
        if v1 not in visited:
            visited.add(v1)

        if v1 == t:
            return c

        for c_,v2 in zip(cost[v1],adj[v1]):
            if v2 in visited:
                continue
            prev = mins.get(v2,float("inf"))
            new_cost = c + c_
            if prev > new_cost:
                mins[v2] = new_cost
                heappush(q,(new_cost,v2))

    return -1


if __name__ == '__main__':
    input = sys.stdin.read()
    data = list(map(int, input.split()))
    n, m = data[0:2]
    data = data[2:]
    edges = list(zip(zip(data[0:(3 * m):3], data[1:(3 * m):3]), data[2:(3 * m):3]))
    data = data[3 * m:]
    adj = [[] for _ in range(n)]
    cost = [[] for _ in range(n)]
    for ((a, b), w) in edges:
        adj[a - 1].append(b - 1)
        cost[a - 1].append(w)
    s, t = data[0] - 1, data[1] - 1
    
    print(distance(adj, cost, s, t))
