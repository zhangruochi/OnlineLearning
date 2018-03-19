function S = simple_stats(N)
mean_n = mean(N,2);
median_n = median(N,2);
min_n = min(N,[],2);
max_n = max(N,[],2);

S = [mean_n, median_n, min_n, max_n];
end
