function out = integerize(A)

small = min(A(:));
big = max(A(:));

if intmax('int8') >= big && small >= intmin('int8')
    out = 'int8';
elseif intmax('int16') >= big && small >= intmin('int16')
    out = 'int16';
elseif intmax('int32') >= big && small >= intmin('int32')
    out = 'int32';
elseif intmax('int64') >= big && small >= intmin('int64')
    out = 'int64';   
else
    out = 'NONE';
end    