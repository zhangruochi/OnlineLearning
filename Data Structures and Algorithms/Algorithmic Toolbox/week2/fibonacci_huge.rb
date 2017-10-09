#!/usr/bin/env ruby
# by Ruochi Zhang

def fib_huge(n, m)
  remainder_sequence = [0,1]
  
  prev = 0
  current = 1

  return sequence[n] if n < 2

  (1...n).each do |i|
    prev,current = current,current + prev
    remainder = current % m

    if remainder == 1 && remainder_sequence[i] == 0
        remainder_sequence.pop
        break
    else
        remainder_sequence.push remainder
        prev_remainder = remainder
    end
  end

  #p remainder_sequence
  remainder_sequence[n % remainder_sequence.size]
end

if __FILE__ == $0
  a, b = gets.split().map(&:to_i)
  #a,b = 99999999999999999,100000
  puts "#{fib_huge(a, b)}"
end