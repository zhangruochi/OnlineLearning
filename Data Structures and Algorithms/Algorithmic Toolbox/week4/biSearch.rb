#!/usr/bin/env ruby

#info
#-name   : zhangruochi
#-email  : zrc720@gmail.com



def biSearch(array,low,high,k)
    if high < low
        return "not found"
    end
    
    mid = low + (high - low) / 2

    if array[mid] == k
        return mid
    elsif array[mid] > k  
        return biSearch(array,low,mid-1,k)
    else
        return biSearch(array,mid+1,high,k)         
    end
end  


#返回插入的位置
def biSearch(array,low,high,k)
    if high < low
        return 0 if array[0] > k
        return high + 1 if array[high] < k
    end
    
    mid = low + (high - low) / 2

    if array[mid] == k
        return mid

    elsif array[mid] > k 
        return biSearch(array,low,mid-1,k)

    else
        return biSearch(array,mid+1,high,k)  
     
    end
end  





a = [1,2,3,4,5,6,8,10]
p biSearch(a,0,a.size-1,11)
