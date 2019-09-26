# python3
import sys

NA = -1

class Trie:
    def __init__(self):
        self.trie = {}

    def add_pattern(self,pattern):
        trie = self.trie
        pattern = pattern.strip()
        for char in pattern:
            trie = trie.setdefault(char,{})
        trie["#"] = "#"

    def maching(self,text,i):
        trie = self.trie

        for char in text[i:]:
            if char not in trie:
                return False
            else:
                trie = trie[char]

            if "#" in trie:
                return True

        return False

    def __repr__(self):
        return str(self.trie)


def solve(text, n, patterns):

    trie = Trie()
    res = []

    for pattern in patterns:
        trie.add_pattern(pattern)

    print(trie)

    for i in range(len(text)):
        if trie.maching(text,i):
            res.append(i)

    return res

# text = sys.stdin.readline ().strip ()
# n = int (sys.stdin.readline ().strip ())
# patterns = []
# for i in range (n):
# 	patterns += [sys.stdin.readline ().strip ()]

text = "ACATA"
patterns = ["AT","A","AG"]
n = 2

ans = solve(text, n, patterns)

# sys.stdout.write (' '.join (map (str, ans)) + '\n')
print(ans)
