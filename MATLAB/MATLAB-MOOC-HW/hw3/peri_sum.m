function result = peri_sum(A)
shape = size(A);
result = sum(A(1,:)) + sum(A(shape(1),:)) + sum(A(:,1)) + sum(A(:,shape(2)))...
    - A(1,1) - A(1,shape(2)) - A(shape(1),1) - A(shape(1),shape(2));
end
