#!/usr/bin/env ruby
# by Ruochi Zhang

def gcd(a, b)
    a,b = b,a if a < b
    while b != 0
        remainder = a % b
        a = b
        b = remainder
    end
    a
end




if __FILE__ == $0
  a, b = gets.split().map(&:to_i)
  puts "#{gcd(a, b)}"
end