function num = dial(str)
num = '';
for ch = str
    if ch >= '0' && ch <= '9'
        num = strcat(num,ch);
    elseif ch >= 'A' && ch <= 'Z'
        num = strcat(num,map(ch));
    else
        num = uint64(0);
        return ;
    end
end
num = uint64(str2double(num));
end

        
    
    






function num = map(ch)
m = '22233344455566677778889999';
num = m(ch - 'A' + 1);
end


