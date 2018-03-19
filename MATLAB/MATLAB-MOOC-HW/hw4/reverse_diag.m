function reverse_diag_matrix = reverse_diag(n)
reverse_diag_matrix = zeros(n);
index = n:n-1:n*n-1;
reverse_diag_matrix(index) = 1;
end



    