function month = year2016(m)
    if ~isscalar(m) || m < 1 || m > 12 || m ~= floor(m)
        month = [];
        return;
    end
    days = [31 29 31 30 31 30 31 31 30 31 30 31];
    ms = {'January'; 'February'; 'March'; 'April'; 'May'; 'June'; ...
           'July'; 'August'; 'September'; 'October'; 'November'; 'December'}; 
    ds = {'Sun'; 'Mon'; 'Tue'; 'Wed'; 'Thu'; 'Fri'; 'Sat'};
   
    month_bias = sum(days(1:m-1));
    bias = 4;
    
    for i=1:days(m)
        month(i).month = ms{m};
        month(i).date = i;
        month(i).day = ds{rem(bias+rem(month_bias+i,7),7)+1};
    end
    
end

    