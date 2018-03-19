function result = holiday(month,day)

if month == 1 && day == 1
    result = true;
elseif month == 7 && day == 4
    result = true;
elseif month == 12 && day == 25
    result = true;
elseif month == 12 && day == 31
    result = true;
else
    result = false;
end
end

