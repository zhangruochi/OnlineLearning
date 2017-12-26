# python3


class HeapBuilder:
    def __init__(self):
        self._swaps = []
        self._data = []

    def ReadData(self):
        n = int(input())
        self._data = [int(s) for s in input().split()]
        #n = 5
        #self._data = [5,4,3,2,1]
        self.size = n
        assert n == len(self._data)

    def WriteResponse(self):
        print(len(self._swaps))
        for swap in self._swaps:
            print(swap[0], swap[1])

    def siftDown(self, i):
        min_index = i

        left_child = i * 2
        if left_child <= self.size and self._data[left_child - 1] < self._data[min_index - 1]:
            min_index = left_child

        right_child = i * 2 + 1
        if right_child <= self.size and self._data[right_child - 1] < self._data[min_index - 1]:
            min_index = right_child

        if min_index != i:
            self._data[i - 1],self._data[min_index - 1] = self._data[min_index - 1],self._data[i - 1]
            self._swaps.append((i-1,min_index-1))
            self.siftDown(min_index)

    def GenerateSwaps(self):
        # The following naive implementation just sorts
        # the given sequence using selection sort algorithm
        # and saves the resulting sequence of swaps.
        # This turns the given array into a heap,
        # but in the worst case gives a quadratic number of swaps.
        #
        # TODO: replace by a more efficient implementation
        for i in range(len(self._data) // 2, 0, -1):
            self.siftDown(i)

    def Solve(self):
        self.ReadData()
        self.GenerateSwaps()
        self.WriteResponse()

if __name__ == '__main__':
    heap_builder = HeapBuilder()
    heap_builder.Solve()
