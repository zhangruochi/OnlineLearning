#!/usr/bin/env ruby

#info
#-name   : zhangruochi
#-email  : zrc720@gmail.com

def binary_search(x, a)
    return binary_index(a,0,a.size-1,x)
end


def binary_index(array,low,high,key)
    if high < low 
        return -1
    end
    mid = low + (high - low)/2
    if array[mid] == key
        return mid
    elsif array[mid] > key
        return binary_index(array,low,mid - 1,key)
    else 
        return binary_index(array,mid + 1,high, key)
    end
end

=begin
def test()
    array = (1..1000).to_a
    k = 100
    puts binary_search(k,array)
end
test
=end


if __FILE__ == $0    
  data = STDIN.read.split().map(&:to_i)
  n = data[0]
  a = data[1..n]
  m = data[n+1]
  data[n+2, m].each { |b| print("#{binary_search(b, a)} ") }
  puts ""
end
