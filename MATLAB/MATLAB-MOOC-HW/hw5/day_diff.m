function diff = day_diff(month1, day1, month2, day2)
daysOfEveryMonth = [31,28,31,30,31,30,31,31,30,31,30,31];

if ~isscalar(month1) || ~isscalar(day1) || month1 ~= fix(month1) || day1 ~= fix(day1)  || month1>12 ||month1 < 1 || day1 > daysOfEveryMonth(month1) || day1 < 1||...
        ~isscalar(month2) || ~isscalar(day2) || month2 ~= fix(month2) || day2 ~= fix(day2) || month2>12 || month2 < 1 || day2 > daysOfEveryMonth(month2) || day2 < 1
    diff = -1;
else
    if month2 > month1
        diff = sum( daysOfEveryMonth(month1:month2-1)) - day1 + day2;
    elseif month2 < month1
        diff = sum( daysOfEveryMonth(month2:month1-1)) - day2 + day1;
    else
        diff = abs(day2 - day1);
    end
end

end



        

    



    