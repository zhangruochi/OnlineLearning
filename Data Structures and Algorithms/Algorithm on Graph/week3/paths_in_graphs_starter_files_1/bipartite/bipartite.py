#Uses python3

import sys
import queue

def bipartite(adj):
    
    start = 0
    node_colors = [0] * len(adj) 
    
    my_queue = queue.Queue()
    my_queue.put((start,1))
    node_colors[start] = 1

    while not my_queue.empty():
        cur,color = my_queue.get()
        for node in adj[cur]:
            if node_colors[node] == 0:
                node_colors[node] = -color
                my_queue.put((node,node_colors[node]))
            if node_colors[node] == color:
                return 0

    return 1






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
    print(bipartite(adj))
