#!/usr/bin/env ruby
# by Andronik Ordian

def max_dot_product(a, b)
    a.sort.zip(b.sort).inject(0) {|sum,item| sum+item[0]*item[1]}
end

#p max_dot_product([23],[39])


if __FILE__ == $0

  data = STDIN.read.split().map(&:to_i)
  n = data[0]  
  a, b = data[1..n], data[n+1..2*n]
  puts "#{max_dot_product(a, b)}"
 
end