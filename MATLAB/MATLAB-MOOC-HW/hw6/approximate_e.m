function [total,k] = approximate_e(delta)
tmp = 1;
k = 1;
total = 1;  %0!

while true
    
    tmp = tmp*k;    
    total = total + 1/tmp;
    
    if abs(total - exp(1)) <= delta
        break
    end
    
    k = k+1;
end

    

    
