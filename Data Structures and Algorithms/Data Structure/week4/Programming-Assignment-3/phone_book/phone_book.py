# python3


class Query:
    def __init__(self, query):
        self.type = query[0]
        self.number = int(query[1])
        if self.type == 'add':
            self.name = query[2]


def read_queries():
    n = int(input())
    return [Query(input().split()) for i in range(n)]


def write_responses(result):
    print('\n'.join(result))


def process_queries(queries):
    result = []
    # Keep list of all existing (i.e. not deleted yet) contacts.
    contacts = {}
    for cur_query in queries:
        if cur_query.type == 'add':
            contacts[cur_query.number] = cur_query.name
        elif cur_query.type == 'del':
            try:
                del contacts[cur_query.number]
            except:
                pass    
        else:
            if cur_query.number not in contacts:
                result.append("not found")
            else:
                result.append(contacts[cur_query.number])    
    return result


if __name__ == '__main__':
    write_responses(process_queries(read_queries()))

"""
12
add 911 police 
add 76213 Mom 
add 17239 Bob 
find 76213
find 910
find 911
del 910
del 911
find 911
find 76213
add 76213 daddy 
find 76213
"""