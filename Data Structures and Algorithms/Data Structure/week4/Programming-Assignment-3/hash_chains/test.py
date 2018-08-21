def hash_func(s):
        ans = 0
        for c in reversed(s):
            ans = (ans * 263 + ord(c)) % 1000000007
        return ans % 5


print(hash_func("world"))  
print(hash_func("luck"))      