#Uses python3

import sys
from collections import deque

def number_of_components(adj):

    def walk(node):
        component = set([node])
        queue = deque([node])
        while queue:
            cur = queue.pop()
            for node in adj[cur]:
                if node not in component:
                    queue.append(node)
                    component.add(node)
        return component


    result = 0
    visited = set()

    for node in range(len(adj)):
        if node not in visited:
            component = walk(node)
            visited = visited.union(component)
            result += 1

    return result

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
    print(number_of_components(adj))
