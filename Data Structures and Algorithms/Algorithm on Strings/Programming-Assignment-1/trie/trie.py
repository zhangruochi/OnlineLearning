#Uses python3
import sys

# Return the trie built from patterns
# in the form of a dictionary of dictionaries,
# e.g. {0:{'A':1},1:{'T':2,'G':3,'C':4}
# where the key of the external dictionary is
# the node ID (integer), and the internal dictionary
# contains all the trie edges outgoing from the corresponding
# node, and the keys are the letters on those edges, and the
# values are the node IDs to which these edges lead.
def build_trie(patterns):
    tree = dict()
    root = node = 0
    tree.setdefault(root,{})
    for string in patterns:
        cur_node = root
        for char in string:
            if char in tree[cur_node]:
                cur_node = tree[cur_node][char]
            else:
                node += 1
                tree.setdefault(node,{})
                tree[cur_node][char] = node
                cur_node = node
    # print(tree)
    return tree


if __name__ == '__main__':
    patterns = sys.stdin.read().split()[1:]
    # patterns = ["AT","AG","AC"]
    tree = build_trie(patterns)
    for node in tree:
        for c in tree[node]:
            print("{}->{}:{}".format(node, tree[node][c], c))
