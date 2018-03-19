function total = spiral_diag_sum(n)

total = 1;
accumulation = 2;
tmp = total;

for i=2:floor(n/2)+1
    for j=1:4
        tmp = tmp + accumulation;
        total = total + tmp;
    end
    
    accumulation = accumulation + 2;
end

end

        
        
    