# python3

import sys
import threading
sys.setrecursionlimit(10**7)  # max depth of recursion
threading.stack_size(2**27)  # new thread will get stack of such size


class Node:
    def __init__(self, name):
        self.name = name
        self.childs = []

    def __repr__(self):
        return "{}:{}".format(self.name, self.childs)

    def add_child(self, node):
        self.childs.append(node)


class TreeHeight:
    def read(self):
        self.n = int(sys.stdin.readline())
        self.parent = list(map(int, sys.stdin.readline().split()))
        #self.n = 5
        #self.parent = [-1,0,4,0,3]
        self.nodes = [Node(i) for i in range(self.n)]
        self.root = self.creat_child_tree()
        #print(self.nodes)
        #print(self.root)

    def creat_child_tree(self):
        root = None
        for child, parent in enumerate(self.parent):
            if parent == -1:
                root = child
                continue
            self.nodes[parent].add_child(child)

        return root

    def compute_height(self):
        # Replace this code with a faster implementation
        max_depth = 0
        parent_queue = [self.root]
        child_queue = []


        while parent_queue:
            #print(parent_queue)
            out = parent_queue.pop(0)
            for child in self.nodes[out].childs:
                child_queue.append(child)

            #print(child_queue)    
            if len(parent_queue) == 0:
                parent_queue = child_queue
                child_queue = []
                max_depth += 1  

            #print("====")      

        return max_depth


def main():
    tree = TreeHeight()
    tree.read()
    print(tree.compute_height())


threading.Thread(target=main).start()
