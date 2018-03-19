function indexes = small_elements(V)
indexes = [];
shape = size(V);
for c = 1:shape(2)
    for r = 1:shape(1)
        if V(r,c) < r*c
            indexes = [indexes;[r c]];
        end
    end
end

            