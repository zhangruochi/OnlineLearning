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

def lcm(a, b)
  a*b / gcd(a,b)
end

if __FILE__ == $0
  a, b = gets.split().map(&:to_i)
  puts "#{lcm(a, b)}"
end
