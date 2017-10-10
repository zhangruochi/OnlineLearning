#!/usr/bin/env ruby
# by Ruochi Zhang


def get_change(n)
    num = 0
    denominations = [10,5,1]
    
    denominations.each do |deno|
        return num if n <= 0 

        index = 1
        while true
            break if index * deno > n
            index += 1
        end
        n = n - (index-1) * deno
        num += (index-1)
    end 

    num 
end  

if __FILE__ == $0
  n = gets.to_i
  puts "#{get_change(n)}"   
end
