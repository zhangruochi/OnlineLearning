function subarray = top_right(N,n)
shape = size(N);
subarray = N(1:n,shape(2)-n+1:end);

end
