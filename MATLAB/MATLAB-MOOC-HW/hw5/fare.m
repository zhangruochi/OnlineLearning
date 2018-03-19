function money = fare(distance,age)
money = 0;
if distance <= 0
    error('distance must be greater than 0');
elseif distance < 1
    money = 2;
elseif distance <= 10
    money = 2 + round(distance-1)*0.25;
else
    money = 2 + 9*0.25 + round(distance - 10)*0.1;
end

if age <= 18 || age >= 60
    money = money * 0.8;
end

end


