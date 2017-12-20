# python3

import sys


class Bracket:
    def __init__(self, bracket_type, position):
        self.bracket_type = bracket_type
        self.position = position

    def Match(self, c):
        if self.bracket_type == '[' and c == ']':
            return True
        if self.bracket_type == '{' and c == '}':
            return True
        if self.bracket_type == '(' and c == ')':
            return True
        return False


if __name__ == "__main__":
    text = sys.stdin.read()
    #text = "}"
    opening_brackets_stack = []

    for i, next in enumerate(text):
        if next == '(' or next == '[' or next == '{':
            opening_brackets_stack.append(Bracket(next, i + 1))
            pass

        if next == ')' or next == ']' or next == '}':
            if len(opening_brackets_stack) == 0:
                print(i+1)
                exit(0)
            else:    
                item = opening_brackets_stack.pop()
                if not item.Match(next):
                    print(i + 1)
                    exit(0)

    # Printing answer, write your code here
    if len(opening_brackets_stack) != 0:
        print(opening_brackets_stack.pop().position)
        exit(0)

    print("Success")
