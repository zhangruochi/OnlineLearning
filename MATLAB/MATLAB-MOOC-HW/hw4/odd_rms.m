function result = odd_rms(nn)
sequence = 1:2:nn*2-1;
result = sqrt(mean(sequence.^2));
end

    