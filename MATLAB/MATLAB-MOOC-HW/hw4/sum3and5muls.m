function result = sum3and5muls(n)
nums = 1:n;
index_3 = 3:3:n;
index_5 = 5:5:n;
index_15 = 15:15:n;
result = sum(nums(index_3)) + sum(nums(index_5)) - sum(nums(index_15));
end


