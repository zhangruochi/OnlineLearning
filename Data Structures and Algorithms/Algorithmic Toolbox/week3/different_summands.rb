#!/usr/bin/env ruby
# by Andronik Ordian

def optimal_summands(n)
    summand = []
    l = 1
    while true
        return summand << n if n <= 2*l
        summand << l
        n = n - l
        l += 1
    end
end



if __FILE__ == $0
  n = gets.to_i
  #n = 8
  summands = optimal_summands(n)
  puts "#{summands.size}"
  puts "#{summands.join(' ')}"
end