function not_a = move_me(v,a)

shape = size(v);
not_a = zeros(1,shape(2));

%disp([length,nargin]);

if nargin < 2
    a = 0;
end

last = shape(2);
first = 1
for i= 1:shape(2)
    if v(i) ~= a
        not_a(first) = v(i);
        first = first + 1;
    else
        not_a(last) = a;
        last = last - 1;
    end
end

end

    