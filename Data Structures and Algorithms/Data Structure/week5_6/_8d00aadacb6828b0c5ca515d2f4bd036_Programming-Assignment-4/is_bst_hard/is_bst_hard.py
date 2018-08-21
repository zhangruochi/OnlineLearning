#!/usr/bin/python3

import sys, threading

sys.setrecursionlimit(10**7) # max depth of recursion
threading.stack_size(2**25)  # new thread will get stack of such size

def IsBinarySearchTree(tree, i, minval, maxval):
    if len(tree) == 0:
        return True
    value = tree[i][0]
    left_ret = right_ret = True
    if tree[i][1] != -1:
        left = tree[tree[i][1]][0]
        if left <= minval or left >= value:
            return False
        else:
            left_ret = IsBinarySearchTree(tree, tree[i][1], minval, min(value, maxval))
    if tree[i][2] != -1:
        right = tree[tree[i][2]][0]
        if right > maxval or right < value:
            return False
        else:
            if value > minval:
                minval = value - 1
            right_ret = IsBinarySearchTree(tree, tree[i][2], minval, maxval)
    return left_ret and right_ret



def main():

  nodes = int(sys.stdin.readline().strip())

  tree = []
  for i in range(nodes):
    tree.append(list(map(int, sys.stdin.readline().strip().split())))

  if IsBinarySearchTree(tree, 0, float("-infinity"), float("infinity")):
    print("CORRECT")
  else:
    print("INCORRECT")

threading.Thread(target=main).start()