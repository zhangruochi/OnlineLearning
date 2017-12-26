# python3

import random


class Worker:

    def __init__(self, thread, time):
        self.thread = thread
        self.next_time = time

    def __repr__(self):
        return "worker({},{})".format(self.thread, self.next_time)


class JobQueue:

    def read_data(self):
        self.num_workers, m = map(int, input().split())
        self.jobs = list(map(int, input().split()))
        #self.num_workers = 2
        #m = 5
        #self.jobs = [1,2,3,4,5]
        self.worker_heap = [Worker(i, 0) for i in range(self.num_workers)]

        assert m == len(self.jobs)

    def write_response(self):
        for i in range(len(self.start_time)):
            print(self.next_worker[i], self.start_time[i])

    def siftDown(self, i):
        min_index = i

        left_child = i * 2
        if left_child <= len(self.worker_heap):
            if self.worker_heap[left_child - 1].next_time < self.worker_heap[min_index - 1].next_time:
                min_index = left_child
            elif self.worker_heap[left_child - 1].next_time == self.worker_heap[min_index - 1].next_time and self.worker_heap[left_child - 1].thread < self.worker_heap[min_index - 1].thread:
                min_index = left_child

        right_child = i * 2 + 1
        if right_child <= len(self.worker_heap):
            if self.worker_heap[right_child - 1].next_time < self.worker_heap[min_index - 1].next_time:
                min_index = right_child
            elif self.worker_heap[right_child - 1].next_time == self.worker_heap[min_index - 1].next_time and self.worker_heap[right_child - 1].thread < self.worker_heap[min_index - 1].thread:
                min_index = right_child

        if min_index != i:
            self.worker_heap[i - 1], self.worker_heap[min_index -
                                                      1] = self.worker_heap[min_index - 1], self.worker_heap[i - 1]
            self.siftDown(min_index)

    def siftUp(self, i):
        while i > 1:
            if self.worker_heap[i // 2 - 1].next_time > self.worker_heap[i - 1].next_time:
                self.worker_heap[i // 2 - 1], self.worker_heap[i - 1] = self.worker_heap[i - 1], self.worker_heap[i // 2 - 1]

            elif self.worker_heap[i // 2 - 1].next_time == self.worker_heap[i - 1].next_time and self.worker_heap[i // 2 - 1].thread > self.worker_heap[i - 1].thread:
                print(self.worker_heap[i // 2 - 1].thread,self.worker_heap[i - 1].thread)
                print(self.worker_heap[i // 2 - 1].next_time,self.worker_heap[i - 1].next_time)
                self.worker_heap[i // 2 - 1], self.worker_heap[i - 1] = self.worker_heap[i - 1], self.worker_heap[i // 2 - 1]
            else:
                break    

            i = i // 2

    def pop_worker(self):
        worker = self.worker_heap[0]
        # print(self.worker_heap)
        self.worker_heap[0], self.worker_heap[-1] = self.worker_heap[-1], self.worker_heap[0]
        self.worker_heap.pop()
        self.siftDown(1)
        return worker

    def push_worker(self, worker):
        self.worker_heap.append(worker)
        self.siftUp(self.num_workers)

    def assign_jobs(self):
        # TODO: replace this code with a faster algorithm.
        self.next_worker = []
        self.start_time = []

        for job in self.jobs:
            new_worker = self.pop_worker()
            self.next_worker.append(new_worker.thread)
            self.start_time.append(new_worker.next_time)
            new_worker.next_time += job
            self.push_worker(new_worker)


    def solve(self):
        self.read_data()
        self.assign_jobs()
        self.write_response()


if __name__ == '__main__':
    job_queue = JobQueue()
    job_queue.solve()
