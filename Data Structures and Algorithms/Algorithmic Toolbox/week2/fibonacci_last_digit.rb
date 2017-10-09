#!/usr/bin/env ruby
# by Ruochi Zhang


def fib_last_digit(n)
    sequence = [0,1]
    (1...n).each do |i|
        sequence.push (sequence[i] + sequence[i-1]) % 10
    end
    p sequence
    sequence.last
end


=begin
require_relative "fibonacci.rb"

def raw(n)
    clac_fib_fast(n).to_s[-1]
end
=end






if __FILE__ == $0
  #n = gets.to_i
  n = 100
  puts "#{fib_last_digit(n)}"
end