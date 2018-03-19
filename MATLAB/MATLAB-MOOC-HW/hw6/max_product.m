function [product, ind] = max_product(v,n)
len = length(v);
product = -Inf;
if n>len
    product = 0; 
    ind = -1;
else
    for i=1:len-n+1
        tmp = prod(v(i:i+n-1));
        if tmp > product
            product = tmp;
            ind = i;
        end
    end
end
end


    