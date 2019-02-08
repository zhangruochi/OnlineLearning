#Uses python3

import sys

def toposort(adj):
    in_degress = dict((u,0) for u in range(len(adj)))
    for u,nodes in enumerate(adj):
        for v in nodes:
            in_degress[v] +=1

    Q = [u for u in range(len(adj)) if in_degress[u] == 0]

    order = []
    
    while Q:
        u = Q.pop()
        order.append(u)
        for v in adj[u]:
            in_degress[v] -= 1
            if in_degress[v] == 0:
                Q.append(v)
    return order

if __name__ == '__main__':
    input = sys.stdin.read()
    data = list(map(int, input.split()))
    n, m = data[0:2]
    data = data[2:]
    edges = list(zip(data[0:(2 * m):2], data[1:(2 * m):2]))
    adj = [[] for _ in range(n)]
    for (a, b) in edges:
        adj[a - 1].append(b - 1)

    order = toposort(adj)
    for x in order:
        print(x + 1, end=' ')

