function percent = zero_stat(matrix)
shape = size(matrix);
all_sum = shape(1)*shape(2);
partial_sum = sum(sum(matrix,1));
percent = (all_sum - partial_sum)/all_sum*100;
end