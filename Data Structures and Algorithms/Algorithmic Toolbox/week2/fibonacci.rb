#!/usr/bin/env ruby
# by Ruochi Zhang

=begin
def calc_fib_slow(n)
  return n if n <= 1
  # slow
  # fix me
  calc_fib(n - 1) + calc_fib(n - 2)
end
=end


def clac_fib(n)
    return 0 if n == 0
    
    sequence = [0,1]
    (1...n).each do |i|
        sequence.push(sequence[i] + sequence[i-1])
    end
    sequence.last
end



if __FILE__ == $0
  n = gets.to_i
  puts "#{clac_fib(n)}"
  #puts "#{calc_fib_slow(n)}"
end
