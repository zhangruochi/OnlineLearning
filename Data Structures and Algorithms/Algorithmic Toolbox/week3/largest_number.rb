#!/usr/bin/env ruby
# by Andronik Ordian


def isGreaterOrEqual(digit, maxDigit)
    
    max,min,flag = digit.size >= maxDigit.size ? [digit,maxDigit,true] : [maxDigit,digit,false]

    #如果flag 是 0， max为digit
    #if max.size == digit.size
    #    flag = 0
    #end

    #循环补齐
    if min == ""
        min = " " * max.size
    else    
        (max.size - min.size).times do |index|
            min += min[index%min.size]
        end
    end

    p [max,min,flag]

    max.size.times do |index|
        if max[index] == min[index]
            next
        
        elsif max[index] > min[index]
            #if flag
            #    return true
            #else
            #    return false
            #end
            return flag    

        else
            #if flag
            #    return false
            #else
            #    return true
            #end
            return !flag    
        end

    end

    return flag

end

def isGreaterOrEqual(digit, maxDigit)
    
    return digit + maxDigit >= maxDigit + digit ?  true : false

end



def largest_number(a)
    result = ""
    until a.empty?
        maxDigit = ""
        max_index = 0
        a.each_with_index do |digit,index|
            if isGreaterOrEqual(digit,maxDigit)
                maxDigit = digit
                max_index = index
            end    
        end
        #p maxDigit

        result += maxDigit
        a.delete_at max_index  
    end
    result
end

if __FILE__ == $0
  a = STDIN.read.split().drop(1)
  #a = ["9","4","6","1","9","21","2"]
  #a = %w(23 39 92)
  #a = %w(2 8 2 3 6 4 1 1 10 6 3 3 6 1 3 8 4 6 1 10 8 4 10 4 1 3 2 3 2 6 1 5 2 9 8 5 10 8 7 9 6 4 2 6 3 8 8 9 8 2 9 10 3 10 7 5 7 1 7 5 1 4 7 6 1 10 5 4 8 4 2 7 8 1 1 7 4 1 1 9 8 6 5 9 9 3 7 6 3 10 8 10 7 2 5 1 1 9 9 5)
  #a = %w(202 20) 
  #puts isGreaterOrEqual(232,23) 
  puts largest_number(a)
end