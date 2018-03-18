function [index_matrix] = even_index(M)
shape = size(M);
index_matrix = M(2:2:shape(1),2:2:shape(2));
end

